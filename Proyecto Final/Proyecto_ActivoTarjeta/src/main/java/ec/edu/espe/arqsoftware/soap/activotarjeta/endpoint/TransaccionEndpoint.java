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
package ec.edu.espe.arqsoftware.soap.activotarjeta.endpoint;

import ec.edu.espe.arqsoftware.soap.activotarjeta.exception.CreateException;
import ec.edu.espe.arqsoftware.soap.activotarjeta.exception.FoundException;
import ec.edu.espe.arqsoftware.soap.activotarjeta.model.TarjetaCliente;
import ec.edu.espe.arqsoftware.soap.activotarjeta.model.Transaccion;
import ec.edu.espe.arqsoftware.soap.activotarjeta.service.TransaccionService;
import static ec.edu.espe.arqsoftware.soap.activotarjeta.transform.TransaccionRSTransform.buildTransaccionTransformRS;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.CrearTransaccionRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.CrearTransaccionResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ListarTransaccionesFechasRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ListarTransaccionesFechasResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ListarUltimasTransaccionesRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ListarUltimasTransaccionesResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.TarjetaClienteRS;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.TransaccionRS;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Slf4j
public class TransaccionEndpoint {

    private static final String NAMESPACE_URI = "http://espe.edu.ec/arqsoftware/soap/activotarjeta/ws";
    private final TransaccionService service;

    @Autowired
    public TransaccionEndpoint(TransaccionService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarTransaccionesFechasRequest")
    @ResponsePayload
    public ListarTransaccionesFechasResponse listarTransaccionesFechas(@RequestPayload ListarTransaccionesFechasRequest request) throws RuntimeException {
        String fechaInicio = request.getListarFechaRQ().getFechaInicio().toString();
        String fechaFin = request.getListarFechaRQ().getFechaFin().toString();
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fechaInicioD;
        LocalDateTime fechaFinD;
        try {
            fechaInicioD = LocalDate.parse(fechaInicio, sdf).atStartOfDay();
            fechaFinD = LocalDate.parse(fechaFin, sdf).atStartOfDay();
        } catch (Exception e) {
            throw new FoundException("Error en las fechas");
        }
        List<TransaccionRS> transaccionesRS = new ArrayList<>();
        try {
            List<Transaccion> transacciones = this.service.listarPorFecha(request.getListarFechaRQ().getCodigoTarjetaCliente(),
                    fechaInicioD,
                    fechaFinD);
            if (transacciones.size() <= 0) {
                log.error("No hay ninguna transaccion");
                throw new FoundException("No se ha encontrado ningún movimiento");
            } else {
                log.info("Transacciones obtenidas {} con los parametros: {} - {}",
                        transacciones.size(), request.getListarFechaRQ().getFechaInicio(), request.getListarFechaRQ().getFechaFin());
                transacciones.forEach(t -> {
                    try {
                        transaccionesRS.add(buildTransaccionTransformRS(t));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(TransaccionEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            ListarTransaccionesFechasResponse response = new ListarTransaccionesFechasResponse();
            response.setTransaccion(transaccionesRS);
            return response;
        } catch (Exception e) {
            log.info("{}", e.getStackTrace());
            throw new RuntimeException();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearTransaccionRequest")
    @ResponsePayload
    public CrearTransaccionResponse crearTransaccion(@RequestPayload CrearTransaccionRequest request) {
        try {
            LocalDateTime fechaTransaccion = LocalDateTime.now(ZoneId.of("America/New_York")).withNano(0);
            log.info("Se va a crear una transaccion con la siguiente informacion: {}", request);
            Transaccion transaccion = new Transaccion();
            transaccion.setCodigoTarjetaCliente(request.getTransaccionRQ().getCodTarjetaCliente());
            transaccion.setDescripcion(request.getTransaccionRQ().getDescripcion());
            transaccion.setTipo(request.getTransaccionRQ().getTipo());
            transaccion.setMeses(request.getTransaccionRQ().getMeses());
            transaccion.setMonto(request.getTransaccionRQ().getMonto());
            transaccion.setInteres(request.getTransaccionRQ().getInteres());
            transaccion.setFecha(fechaTransaccion);

            CrearTransaccionResponse response = new CrearTransaccionResponse();

            this.service.crearTransaccion(transaccion);
            return response;
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la transaccion. {} - retorna badrequest {}", e.getMessage(), e.getStackTrace());
            throw new CreateException("Error al crear una transacción, inténtelo de nuevo");
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarUltimasTransaccionesRequest")
    @ResponsePayload

    public ListarUltimasTransaccionesResponse listarUltimasTransacciones(@RequestPayload ListarUltimasTransaccionesRequest request) {
        List<TransaccionRS> transaccionesRS = new ArrayList<>();
        try {
            List<Transaccion> transacciones = this.service.obtenerMovimientosActuales(request.getCodTarjetaCliente());
            if (transacciones.size() <= 0) {
                throw new FoundException("No se ha encontrado ningún movimiento");
            } else {
                log.info("Transacciones obtenidas {} con la tarjeta de credito: {}",
                        transacciones.size(), request.getCodTarjetaCliente());
                transacciones.forEach(t -> {
                    try {
                        transaccionesRS.add(buildTransaccionTransformRS(t));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(TransaccionEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            ListarUltimasTransaccionesResponse response = new ListarUltimasTransaccionesResponse();
            response.setTransaccionRS(transaccionesRS);
            return response;
        } catch (Exception e) {
            log.error("Ocurrio un error. {} - retorna badrequest {}", e.getMessage(), e.getStackTrace());
            throw new FoundException("No se ha encontrado ningún movimiento");
        }

    }
}
