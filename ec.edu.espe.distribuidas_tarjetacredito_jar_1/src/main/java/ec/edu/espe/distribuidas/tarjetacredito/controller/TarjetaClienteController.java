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
package ec.edu.espe.distribuidas.tarjetacredito.controller;

import ec.edu.espe.distribuidas.tarjetacredito.dto.TarjetaActivoRS;
import ec.edu.espe.distribuidas.tarjetacredito.dto.TarjetaClienteRQ;
import ec.edu.espe.distribuidas.tarjetacredito.dto.TarjetaClienteRS;
import ec.edu.espe.distribuidas.tarjetacredito.dto.TransaccionRS;
import ec.edu.espe.distribuidas.tarjetacredito.dto.Serializador;
import ec.edu.espe.distribuidas.tarjetacredito.exception.FoundException;
import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import ec.edu.espe.distribuidas.tarjetacredito.service.TarjetaClienteService;
import ec.edu.espe.distribuidas.tarjetacredito.service.TransaccionService;
import static ec.edu.espe.distribuidas.tarjetacredito.transform.TarjetaClienteRSTransform.buildTarjetaTransformRS;
import static ec.edu.espe.distribuidas.tarjetacredito.transform.TarjetaActivoRSTransform.buildTransformRS;
import static ec.edu.espe.distribuidas.tarjetacredito.transform.TransaccionRSTransform.buildTransaccionTransformRS;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/api/tarjetaCliente")
@Slf4j
public class TarjetaClienteController {

    private final TarjetaClienteService service;
    private final TransaccionService transaccionService;

    public TarjetaClienteController(TarjetaClienteService service, TransaccionService transaccionService) {
        this.service = service;
        this.transaccionService = transaccionService;
    }

    @ApiOperation(value = "Crear una tarjeta de credito de un cliente",
            notes = "Inserción de una tarjeta de credito")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. La tarjeta de credito se guardo correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. No se pudo guardar la tarjeta de credito"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @PostMapping
    public ResponseEntity crearTarjetaCliente(@RequestBody TarjetaClienteRQ request) {
        try {
            log.info("Se va a crear una tarjeta de credito con la siguiente informacion: {}", request);
            TarjetaCliente tarjeta = new TarjetaCliente();
            tarjeta.setCodigoTarjetaCredito(request.getCodigoTarjeta());
            tarjeta.setCodigoCliente(request.getCodigoCliente());
            tarjeta.setNombreTitular(request.getNombreTitular());
            this.service.crearTarjetaCliente(tarjeta);
            return ResponseEntity.ok(buildTransformRS(tarjeta));
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la tarjeta. {} - retorna badrequest", e.getMessage());
            return Serializador.Error(e, "Error, no se pudo crear la tarjeta de crédito, inténtelo nuevamente");
        }
    }

    @ApiOperation(value = "Listar movimientos de todos los cliente",
            notes = "Movimientos de las tarjetas de los clientes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Se encontraron movimientos"),
        @ApiResponse(code = 400, message = "Bad Request. No se encontro ningun movimiento"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "/ultimosMovimientos")
    public ResponseEntity listarMovimientosActuales() {
        List<TarjetaCliente> tarjetas = this.service.obtenerTarjetaCliente();
        List<TarjetaClienteRS> tarjetasClienteRS = new ArrayList<>();
        for (TarjetaCliente t : tarjetas) {
            List<TransaccionRS> transaccionesRS = new ArrayList<>();
            try {
                List<Transaccion> transacciones = this.transaccionService.obtenerMovimientosActuales(t.getCodigo());
                log.info("Transacciones obtenidas {} con el codigo de tarjeta: {}",
                        transacciones.size(), t.getCodigo());
                transacciones.forEach(tr -> {
                    transaccionesRS.add(buildTransaccionTransformRS(tr));
                });
                TarjetaClienteRS tarjetasRS = buildTarjetaTransformRS(t);
                tarjetasRS.setTransaccion(transaccionesRS);
                tarjetasClienteRS.add(tarjetasRS);
            } catch (Exception e) {
                return Serializador.Error(e, "Error, no se pudo obtener los últimos movimientos");
            }
        }
        return ResponseEntity.ok(tarjetasClienteRS);

    }

    @GetMapping(value = "/codcliente/{codCliente}")
    @ApiOperation(value = "Obtiene un tarjeta de credito cliente",
            notes = "Obtiene tarjetas de credito de un cliente de acuerdo a su codigo ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK, Cuando obtiene una tarjeta de credito cliente de acuerdo a su codigo"),
        @ApiResponse(code = 404, message = "No existe una tarjeta de credito del codigo del cliente enviado")
    })
    public ResponseEntity obtenerActivoTarjetaCliente(@PathVariable("codCliente") String codCliente) {
        try {
            log.info("Se va a obtener la información del cliente {}", codCliente);
            List<TarjetaCliente> tarjetaCliente = service.obtenerPorCodigoCliente(codCliente);
            if (tarjetaCliente.size() <= 0) {
                throw new FoundException("El cliente no cuenta con una tarjeta");
            } else {
                List<TarjetaActivoRS> tarjetaClienteRS = new ArrayList<>();
                for (TarjetaCliente c : tarjetaCliente) {
                    tarjetaClienteRS.add(buildTransformRS(c));
                }
                return ResponseEntity.ok(tarjetaClienteRS);
            }

        } catch (Exception e) {
            return Serializador.Error(e, "Error, no se encontró ningún producto");
        }
    }

    @ApiOperation(value = "Listar datos de la tarjeta por el numero",
            notes = "Datos de la tarjeta cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Se encontro la tarjeta del cliente"),
        @ApiResponse(code = 400, message = "Bad Request. No se encontro ninguna tarjeta"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "/nroTarjeta/{nroTarjeta}")
    public ResponseEntity obtenerPorNroTarjeta(@PathVariable("nroTarjeta") String nroTarjeta) {
        log.info("Obteniendo tarjeta de credito por numero: {} esperando por resultados", nroTarjeta);
        TarjetaCliente tarjeta = this.service.obtenerPorNumeroTarjeta(nroTarjeta);
        try {
            if (tarjeta != null) {
                TarjetaClienteRS tarjetaRS = buildTarjetaTransformRS(tarjeta);
                return ResponseEntity.ok(tarjetaRS);
            } else {
                throw new FoundException("El número de tarjeta: " + nroTarjeta + ", no existe");
            }
        } catch (Exception e) {
            return Serializador.Error(e, "Error, no se encontró ningun producto");
        }
    }

    @ApiOperation(value = "Listar todas las tarjetas",
            notes = "Datos de la tarjeta del cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Se encontro la tarjeta del cliente"),
        @ApiResponse(code = 400, message = "Bad Request. No se encontro ninguna tarjeta"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "/todas")
    public ResponseEntity obtenerTodas() {
        log.info("Obteniendo todas las tarjetas de credito esperando por resultados");
        List<TarjetaCliente> tarjetas = this.service.obtenerTarjetaCliente();
        try {
            List<TarjetaActivoRS> tarjetaClienteRS = new ArrayList<>();
            for (TarjetaCliente c : tarjetas) {
                tarjetaClienteRS.add(buildTransformRS(c));
            }

            return ResponseEntity.ok(tarjetaClienteRS);
        } catch (Exception e) {
            return Serializador.Error(e, "Error, no se encontró ningun producto");
        }
    }
}
