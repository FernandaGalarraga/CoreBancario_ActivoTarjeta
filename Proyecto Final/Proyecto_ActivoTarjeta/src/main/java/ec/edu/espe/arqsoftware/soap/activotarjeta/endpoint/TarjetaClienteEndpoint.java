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
import ec.edu.espe.arqsoftware.soap.activotarjeta.service.TarjetaClienteService;
import ec.edu.espe.arqsoftware.soap.activotarjeta.service.TransaccionService;
import static ec.edu.espe.arqsoftware.soap.activotarjeta.transform.TarjetaActivoRSTransform.buildTransformRS;
import static ec.edu.espe.arqsoftware.soap.activotarjeta.transform.TarjetaClienteRSTransform.buildTarjetaTransformRS;
import static ec.edu.espe.arqsoftware.soap.activotarjeta.transform.TransaccionRSTransform.buildTransaccionTransformRS;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.CrearTarjetaClienteRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.CrearTarjetaClienteResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ListarMovimientosActualesRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ListarMovimientosActualesResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ObtenerActivoTarjetaClienteRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ObtenerActivoTarjetaClienteResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ObtenerPorNroTarjetaRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ObtenerPorNroTarjetaResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ObtenerTodasRequest;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.ObtenerTodasResponse;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.TarjetaActivoRS;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.TarjetaClienteRS;
import ec.edu.espe.arqsoftware.soap.activotarjeta.ws.TransaccionRS;
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
public class TarjetaClienteEndpoint {

    private static final String NAMESPACE_URI = "http://espe.edu.ec/arqsoftware/soap/activotarjeta/ws";
    private final TarjetaClienteService service;
    private final TransaccionService transaccionService;

    @Autowired
    public TarjetaClienteEndpoint(TarjetaClienteService service, TransaccionService transaccionService) {
        this.service = service;
        this.transaccionService = transaccionService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearTarjetaClienteRequest")
    @ResponsePayload
    public CrearTarjetaClienteResponse crearTarjetaCliente(@RequestPayload CrearTarjetaClienteRequest request) throws CreateException {
        try {
            log.info("Se va a crear una tarjeta de credito con la siguiente informacion: {}", request);
            TarjetaCliente tarjeta = new TarjetaCliente();
            tarjeta.setCodigoTarjetaCredito(request.getTarjetaClienteRQ().getCodigoTarjeta());
            tarjeta.setCodigoCliente(request.getTarjetaClienteRQ().getCodigoCliente());
            tarjeta.setNombreTitular(request.getTarjetaClienteRQ().getNombreTitular());
            this.service.crearTarjetaCliente(tarjeta);
            CrearTarjetaClienteResponse response = new CrearTarjetaClienteResponse();
            response.setTarjetaActivoRS(buildTransformRS(tarjeta));
            return response;
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("Ocurrio un error al crear la tarjeta. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
            throw new CreateException("Error: " + exceptionMessage);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerPorNroTarjetaRequest")
    @ResponsePayload
    public ObtenerPorNroTarjetaResponse obtenerPorNroTarjeta(@RequestPayload ObtenerPorNroTarjetaRequest request) {
        log.info("Obteniendo tarjeta de credito por numero: {} esperando por resultados", request.getNumeroTarjeta());
        TarjetaCliente tarjeta = this.service.obtenerPorNumeroTarjeta(request.getNumeroTarjeta());
        try {
            if (tarjeta != null) {
                TarjetaClienteRS tarjetaRS = buildTarjetaTransformRS(tarjeta);
                ObtenerPorNroTarjetaResponse response = new ObtenerPorNroTarjetaResponse();
                response.setTarjetaClienteRS(tarjetaRS);
                return response;
            } else {
                throw new FoundException("El número de tarjeta: " + request.getNumeroTarjeta() + ", no existe");
            }
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
            throw new FoundException("Error: " + exceptionMessage);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerActivoTarjetaClienteRequest")
    @ResponsePayload
    ObtenerActivoTarjetaClienteResponse obtenerActivoTarjetaCliente(@RequestPayload ObtenerActivoTarjetaClienteRequest request) {
        try {
            log.info("Se va a obtener la información del cliente {}", request.getCodCliente());
            List<TarjetaCliente> tarjetaCliente = service.obtenerPorCodigoCliente(request.getCodCliente());
            if (tarjetaCliente.size() <= 0) {
                throw new FoundException("El cliente no cuenta con una tarjeta");
            } else {
                List<TarjetaActivoRS> tarjetaClienteRS = new ArrayList<>();
                for (TarjetaCliente c : tarjetaCliente) {
                    tarjetaClienteRS.add(buildTransformRS(c));
                }
                ObtenerActivoTarjetaClienteResponse response = new ObtenerActivoTarjetaClienteResponse();
                response.setTarjetaActivoRS(tarjetaClienteRS);
                return response;
            }

        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
            throw new FoundException("Error: " + exceptionMessage);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarMovimientosActualesRequest")
    @ResponsePayload
    ListarMovimientosActualesResponse listarMovimientosActuales(@RequestPayload ListarMovimientosActualesRequest request) {
        List<TarjetaCliente> tarjetas = this.service.obtenerTarjetaCliente();
        List<TarjetaClienteRS> tarjetasClienteRS = new ArrayList<>();
        for (TarjetaCliente t : tarjetas) {
            List<TransaccionRS> transaccionesRS = new ArrayList<>();
            try {
                List<Transaccion> transacciones = this.transaccionService.obtenerMovimientosActuales(t.getCodigo());
                log.info("Transacciones obtenidas {} con el codigo de tarjeta: {}",
                        transacciones.size(), t.getCodigo());
                transacciones.forEach(tr -> {
                    try {
                        transaccionesRS.add(buildTransaccionTransformRS(tr));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(TarjetaClienteEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                TarjetaClienteRS tarjetasRS = buildTarjetaTransformRS(t);
                tarjetasRS.getTransaccion().addAll(transaccionesRS);
                tarjetasClienteRS.add(tarjetasRS);

            } catch (Exception e) {
                String exceptionMessage = e.getMessage();
                log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
                log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getStackTrace());
                throw new FoundException("Error: " + exceptionMessage);
            }
        }
        ListarMovimientosActualesResponse response = new ListarMovimientosActualesResponse();
        response.setTarjetaClienteRS(tarjetasClienteRS);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerTodasRequest")
    @ResponsePayload
    public ObtenerTodasResponse obtenerTodas(@RequestPayload ObtenerTodasRequest request) throws RuntimeException {
        log.info("Obteniendo todas las tarjetas de credito esperando por resultados");
        List<TarjetaCliente> tarjetas = this.service.obtenerTarjetaCliente();
        try {
            List<TarjetaActivoRS> tarjetaClienteRS = new ArrayList<>();
            for (TarjetaCliente c : tarjetas) {
                tarjetaClienteRS.add(buildTransformRS(c));
            }
            ObtenerTodasResponse response = new ObtenerTodasResponse();
            response.getTarjetaActivoRS().addAll(tarjetaClienteRS);
            return response;
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
            throw new FoundException("Error: " + exceptionMessage);
        }
    }

}
