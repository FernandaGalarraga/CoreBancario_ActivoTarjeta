/*
 * Copyright (c) 2021
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.distribuidas.tarjetacredito.service;

import ec.edu.espe.distribuidas.tarjetacredito.dao.TarjetaClienteRepository;
import ec.edu.espe.distribuidas.tarjetacredito.dao.TransaccionRepository;
import ec.edu.espe.distribuidas.tarjetacredito.dto.ClienteDto;
import ec.edu.espe.distribuidas.tarjetacredito.dto.ClienteTransaccionDto;
import ec.edu.espe.distribuidas.tarjetacredito.dto.CustomNotificationRQ;
import ec.edu.espe.distribuidas.tarjetacredito.exception.CreateException;
import ec.edu.espe.distribuidas.tarjetacredito.exception.FoundException;
import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransaccionService {

    private final TarjetaClienteRepository tarjetaClienteRepo;
    private final TransaccionRepository transaccionRepo;
    private final WebClienteService cliente;

    public TransaccionService(TarjetaClienteRepository tarjetaClienteRepo, TransaccionRepository transaccionRepo, WebClienteService cliente) {
        this.tarjetaClienteRepo = tarjetaClienteRepo;
        this.transaccionRepo = transaccionRepo;
        this.cliente = cliente;
    }

    public List<Transaccion> listarPorFecha(String codTarjetaCliente, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        LocalDateTime ldInicio = fechaInicio.atZone(ZoneId.of("America/Chicago")).toLocalDate().atStartOfDay();
        LocalDateTime ldFin = fechaFin.atZone(ZoneId.of("America/Chicago")).toLocalDate().atTime(23, 59, 59);
        Period diff = Period.between(
                fechaInicio.atZone(ZoneId.of("America/Chicago")).toLocalDate(),
                fechaFin.atZone(ZoneId.of("America/Chicago")).toLocalDate());
        log.info("Meses de diferencia {}:", diff.getMonths());
        log.info("Va a buscar transacciones desde: {} hasta: {}, de la cuenta {}", ldInicio, ldFin, codTarjetaCliente);
        if (ldInicio.isBefore(ldFin) && diff.getMonths() <= 6) {
            return this.transaccionRepo.findByCodigoTarjetaClienteAndFechaBetween(
                    codTarjetaCliente, ldInicio, ldFin);
        } else {
            log.error("Error en las fechas recibidas: fechaInicio: {}, fechaFin: {}", fechaInicio, fechaFin);
            throw new FoundException("Fechas inválidas, la fecha inicio no puede ser menor a la mayor");
        }
    }

    public List<Transaccion> obtenerMovimientosActuales(String codTarjetaCliente) {
        Calendar fechaFin = Calendar.getInstance();
        Calendar fechaInicio = Calendar.getInstance();
        Optional<TarjetaCliente> tarjetaClienteOpt = this.tarjetaClienteRepo.findById(codTarjetaCliente);
        if (tarjetaClienteOpt.isPresent()) {
            fechaFin.set(Calendar.DAY_OF_MONTH, tarjetaClienteOpt.get().getTarjetaCredito().getFechaCorte());
            fechaInicio.add(Calendar.MONTH, -1);
            fechaFin.add(Calendar.DAY_OF_WEEK, -1);
            fechaInicio.set(Calendar.DAY_OF_MONTH, tarjetaClienteOpt.get().getTarjetaCredito().getFechaCorte());
        }
        LocalDateTime ldInicio = fechaInicio.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate().atStartOfDay();
        LocalDateTime ldFin = fechaFin.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate().atTime(23, 59, 59);
        log.info("Va a buscar transacciones desde: {} hasta: {}, de la cuenta {}", ldInicio, ldFin, codTarjetaCliente);
        return this.transaccionRepo.findByCodigoTarjetaClienteAndFechaBetween(
                codTarjetaCliente, ldInicio, ldFin);
    }

    @Transactional
    public void crearTransaccion(Transaccion transaccion) {
        Optional<TarjetaCliente> tarjetaClienteOpt = this.tarjetaClienteRepo.findById(transaccion.getCodigoTarjetaCliente());
        if (tarjetaClienteOpt.isEmpty()) {
            throw new CreateException("No existe la tarjeta con codigo: " + transaccion.getCodigoTarjetaCliente());
        }

        if ("CON".equals(transaccion.getTipo()) || "DIF".equals(transaccion.getTipo())) {
            if (tarjetaClienteOpt.get().getCupoDisponible().compareTo(transaccion.getMonto()) == -1) {
                throw new CreateException("No tiene suficiente dinero para realizar esta operación");
            }
            tarjetaClienteOpt.get().setCupoTotal(tarjetaClienteOpt.get().getCupoTotal().subtract(transaccion.getMonto()));

        } else if ("PAG".equals(transaccion.getTipo())) {
            transaccion.setInteres(BigDecimal.ZERO);
            tarjetaClienteOpt.get().setCupoTotal(tarjetaClienteOpt.get().getCupoTotal().add(transaccion.getMonto()));
            this.cliente.depositar(ClienteTransaccionDto.buildTransaccion(transaccion, tarjetaClienteOpt.get()));
            log.info("{}:", ClienteTransaccionDto.buildTransaccion(transaccion, tarjetaClienteOpt.get()));
        } else {
            throw new CreateException("No existe el tipo de pago seleccionado");
        }

        ClienteDto clienteInfo = new ClienteDto();
        try {
            clienteInfo = this.cliente.readClient(tarjetaClienteOpt.get().getCodigoCliente());
            log.info("{}", clienteInfo);

            this.cliente.sendNotification(CustomNotificationRQ.buildTransaccion(clienteInfo, transaccion, tarjetaClienteOpt.get()));
        } catch (Exception e) {
            log.info(" error: {} {}", e.getMessage(), e.getStackTrace());
        }

        this.transaccionRepo.save(transaccion);
        this.tarjetaClienteRepo.save(tarjetaClienteOpt.get());
    }
}
