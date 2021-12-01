/*
 * Copyright (c) 2021 yazbe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.arqsoftware.clienteactivotarjeta.controller;

import ec.edu.espe.arqsoftware.clienteactivotarjeta.service.TransaccionService;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.TransaccionRS;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaccion")
@Slf4j
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @GetMapping(value = "{codTarjetaCliente}/{fechaInicio}/{fechaFin}")
    public ResponseEntity listarTransaccionesFechas(
            @PathVariable("codTarjetaCliente") String codTarjetaCliente,
            @PathVariable("fechaInicio") String fechaInicio,
            @PathVariable("fechaFin") String fechaFin) {
        try {
            List<TransaccionRS> transacciones = this.service.listarPorFecha(codTarjetaCliente, fechaInicio, fechaFin);
            if (transacciones.size() <= 0) {
               log.info("No se encontro ninguna fecha");
               return ResponseEntity.badRequest().build();
            } else {
                log.info("Transacciones obtenidas {} con los parametros: {} - {}",
                        transacciones.size(), fechaInicio, fechaFin);
                return ResponseEntity.ok(transacciones);
            }
            
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("{}", e.getStackTrace());
            return ResponseEntity.badRequest().build();
        }  
        
    }


}
