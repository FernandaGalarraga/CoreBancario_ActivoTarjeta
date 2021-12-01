/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    mafer - initial API and implementation and/or initial documentation
 */
package ec.edu.espe.arquitectura.soap.tarjetacliente.service;

import ec.edu.espe.arquitectura.soap.tarjetacliente.dao.TarjetaClienteRepository;
import ec.edu.espe.arquitectura.soap.tarjetacliente.dao.TarjetaCreditoRepository;
import ec.edu.espe.arquitectura.soap.tarjetacliente.dao.TransaccionRepository;
import ec.edu.espe.arquitectura.soap.tarjetacliente.exception.CreateException;
import ec.edu.espe.arquitectura.soap.tarjetacliente.exception.FoundException;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.TarjetaCliente;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.TarjetaCredito;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.Transaccion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TarjetaClienteService {

    private final TarjetaClienteRepository tarjetaClienteRepo;
    private final TarjetaCreditoRepository tarjetaCreditoRepo;
    private final TransaccionRepository transaccionRepo;

    public TarjetaClienteService(TarjetaClienteRepository tarjetaClienteRepo, TarjetaCreditoRepository tarjetaCreditoRepo, TransaccionRepository transaccionRepo) {
        this.tarjetaClienteRepo = tarjetaClienteRepo;
        this.tarjetaCreditoRepo = tarjetaCreditoRepo;
        this.transaccionRepo = transaccionRepo;
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
        } else {
            throw new RuntimeException("No se encontro ninguna tarjeta con el codigo: " + codTarjetaCliente);
        }
        LocalDateTime ldInicio = fechaInicio.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDate().atStartOfDay();
        LocalDateTime ldFin = fechaFin.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDate().atTime(23, 59, 59);
        log.info("Va a buscar transacciones desde: {} hasta: {}, de la cuenta {}", ldInicio, ldFin, codTarjetaCliente);
        log.info("Fecha Inicio: {}, Fecha Fin: ",fechaInicio,fechaFin);
        return this.transaccionRepo.findByCodigoTarjetaClienteAndFechaBetween(
                codTarjetaCliente, ldInicio, ldFin);
    }

    public List<TarjetaCliente> obtenerPorCodigoCliente(String codigo) {
        if (codigo != null) {
            List<TarjetaCliente> tarjetaCliente = this.tarjetaClienteRepo.findByCodigoClienteOrderByCodigoTarjetaCreditoAsc(codigo);
            tarjetaCliente.stream().filter(c -> ("INA".equals(c.getEstado()))).forEachOrdered(c -> {
                tarjetaCliente.remove(c);
            });
            return tarjetaCliente;

        } else {
            throw new FoundException("No se ha encontrado ningún movimiento con el código de tarjeta: " + codigo);
        }
    }

    public TarjetaCliente obtenerPorNumeroTarjeta(String nroTarjeta) {
        return this.tarjetaClienteRepo.findByNumeroTarjeta(nroTarjeta);
    }
    
    public List<TarjetaCliente> obtenerTarjetaCliente() {
        return this.tarjetaClienteRepo.findAll();
    }
}
