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
package ec.edu.espe.arqsoftware.activotarjeta.service;

import ec.edu.espe.arqsoftware.activotarjeta.dao.TransaccionRepository;
import ec.edu.espe.arqsoftware.activotarjeta.exception.FoundException;
import ec.edu.espe.arqsoftware.activotarjeta.model.Transaccion;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransaccionService {
    private final TransaccionRepository transaccionRepo;

    public TransaccionService(TransaccionRepository transaccionRepo) {
        this.transaccionRepo = transaccionRepo;
    }
    
    public List<Transaccion> listarPorFecha(String codTarjetaCliente, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        LocalDateTime ldInicio = fechaInicio.atZone(ZoneId.of("America/New_York")).toLocalDate().atStartOfDay();
        LocalDateTime ldFin = fechaFin.atZone(ZoneId.of("America/New_York")).toLocalDate().atTime(23, 59, 59);
        Period diff = Period.between(
                fechaInicio.atZone(ZoneId.of("America/New_York")).toLocalDate(),
                fechaFin.atZone(ZoneId.of("America/New_York")).toLocalDate());
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
}
