/*
 * Copyright (c) 2021
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.arquitectura.soap.tarjetacliente.service;

import ec.edu.espe.arquitectura.soap.tarjetacliente.dao.TarjetaClienteRepository;
import ec.edu.espe.arquitectura.soap.tarjetacliente.dao.TransaccionRepository;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.TarjetaCliente;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.Transaccion;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransaccionService {

    private final TarjetaClienteRepository tarjetaClienteRepo;
    private final TransaccionRepository transaccionRepo;

    public TransaccionService(TarjetaClienteRepository tarjetaClienteRepo, TransaccionRepository transaccionRepo) {
        this.tarjetaClienteRepo = tarjetaClienteRepo;
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
        }
        LocalDateTime ldInicio = fechaInicio.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate().atStartOfDay();
        LocalDateTime ldFin = fechaFin.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate().atTime(23, 59, 59);
        log.info("Va a buscar transacciones desde: {} hasta: {}, de la cuenta {}", ldInicio, ldFin, codTarjetaCliente);
        return this.transaccionRepo.findByCodigoTarjetaClienteAndFechaBetween(
                codTarjetaCliente, ldInicio, ldFin);
    }

}
