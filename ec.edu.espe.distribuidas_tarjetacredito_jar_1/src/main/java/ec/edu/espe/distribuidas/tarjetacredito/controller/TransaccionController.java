/*
 * Copyright (c) 2021 yazbe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.distribuidas.tarjetacredito.controller;

import ec.edu.espe.distribuidas.tarjetacredito.dto.Serializador;
import ec.edu.espe.distribuidas.tarjetacredito.dto.TransaccionRQ;
import ec.edu.espe.distribuidas.tarjetacredito.dto.TransaccionRS;
import ec.edu.espe.distribuidas.tarjetacredito.exception.FoundException;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import ec.edu.espe.distribuidas.tarjetacredito.service.TransaccionService;
import static ec.edu.espe.distribuidas.tarjetacredito.transform.TransaccionRSTransform.buildTransaccionTransformRS;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation(value = "Crear una transaccion de una tarjeta de un cliente",
            notes = "Inserción de una transaccion de una tarjeta de un cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. La transacción de credito se guardo correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. No se pudo guardar la transacción"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @PostMapping
    public ResponseEntity crearTransaccion(@RequestBody TransaccionRQ request) {
        try {
            LocalDateTime fechaTransaccion = LocalDateTime.now(ZoneId.of("America/Chicago")).withNano(0);
            log.info("Se va a crear una transaccion con la siguiente informacion: {}", request);
            Transaccion transaccion = new Transaccion();
            transaccion.setCodigoTarjetaCliente(request.getCodTarjetaCliente());
            transaccion.setDescripcion(request.getDescripcion());
            transaccion.setTipo(request.getTipo());
            transaccion.setMeses(request.getMeses());
            transaccion.setMonto(request.getMonto());
            transaccion.setInteres(request.getInteres());
            transaccion.setFecha(fechaTransaccion);

            this.service.crearTransaccion(transaccion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la transaccion. {} - retorna badrequest {}", e.getMessage(), e.getStackTrace());
            return Serializador.Error(e, "Error al crear una transacción, inténtelo de nuevo");
        }
    }

    @ApiOperation(value = "Listar transacciones por fecha",
            notes = "Listar transacciones entre dos fechas con formato yyyy-MM-dd")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Las transacciones se obtuvieron correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. El formato de fecha es: yyyy-MM-dd"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "{codTarjetaCliente}/{fechaInicio}/{fechaFin}")
    public ResponseEntity listarTransaccionesFechas(
            @PathVariable("codTarjetaCliente") String codTarjetaCliente,
            @PathVariable("fechaInicio") String fechaInicio,
            @PathVariable("fechaFin") String fechaFin) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fechaInicioD;
        LocalDateTime fechaFinD;
        try {
            fechaInicioD = LocalDate.parse(fechaInicio, sdf).atStartOfDay();
            fechaFinD = LocalDate.parse(fechaFin, sdf).atStartOfDay();
        } catch (Exception e) {
            return Serializador.Error(e, "Error en las fechas de inicio y fin");
        }
        List<TransaccionRS> transaccionesRS = new ArrayList<>();
        try {
            List<Transaccion> transacciones = this.service.listarPorFecha(codTarjetaCliente, fechaInicioD, fechaFinD);
            if (transacciones.size() <= 0) {
               throw new FoundException("No se ha encontrado ningún movimiento");
            } else {
                log.info("Transacciones obtenidas {} con los parametros: {} - {}",
                        transacciones.size(), fechaInicio, fechaFin);
                transacciones.forEach(t -> {
                    transaccionesRS.add(buildTransaccionTransformRS(t));
                });
            }
        } catch (Exception e) {
            return Serializador.Error(e, "Error al obtener las transacciones");
        }
        return ResponseEntity.ok(transaccionesRS);
    }

    @ApiOperation(value = "Listar últimos movimientos",
            notes = "Listar últimos movimientos antes de la fecha de corte")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Las transacciones se obtiene correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. El formato de fecha es: yyyy-MM-dd"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "/movimientosActuales/{codTarjetaCliente}")
    public ResponseEntity listarUltimasTransacciones(@PathVariable("codTarjetaCliente") String codTarjetaCliente) {
        List<TransaccionRS> transaccionesRS = new ArrayList<>();
        try {
            List<Transaccion> transacciones = this.service.obtenerMovimientosActuales(codTarjetaCliente);
            if (transacciones.size() <= 0) {
                throw new FoundException("No se ha encontrado ningún movimiento");
            } else {
                log.info("Transacciones obtenidas {} con la tarjeta de credito: {}",
                        transacciones.size(), codTarjetaCliente);
                transacciones.forEach(t -> {
                    transaccionesRS.add(buildTransaccionTransformRS(t));
                });
            }
        } catch (Exception e) {
            return Serializador.Error(e, "Error al obtener los últimos movimientos");
        }

        return ResponseEntity.ok(transaccionesRS);
    }

}
