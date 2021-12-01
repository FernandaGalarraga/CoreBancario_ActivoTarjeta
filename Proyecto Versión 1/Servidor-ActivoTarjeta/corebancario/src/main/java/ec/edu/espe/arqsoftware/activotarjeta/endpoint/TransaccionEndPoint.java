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
package ec.edu.espe.arqsoftware.activotarjeta.endpoint;

import ec.edu.espe.arqsoftware.activotarjeta.exception.FoundException;
import ec.edu.espe.arqsoftware.activotarjeta.model.Transaccion;
import ec.edu.espe.arqsoftware.activotarjeta.service.TransaccionService;
import static ec.edu.espe.arqsoftware.activotarjeta.transform.TransaccionRSTransform.buildTransaccionTransformRS;
import ec.edu.espe.arqsoftware.activotarjeta.ws.ListarTransaccionesFechasRequest;
import ec.edu.espe.arqsoftware.activotarjeta.ws.ListarTransaccionesFechasResponse;
import ec.edu.espe.arqsoftware.activotarjeta.ws.TransaccionRS;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Slf4j
public class TransaccionEndPoint {
    private static final String NAMESPACE_URI = "http://espe.edu.ec/arqsoftware/soap/partidofutbol/ws";
    private final TransaccionService service;

    @Autowired
    public TransaccionEndPoint(TransaccionService service) {
        this.service = service;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarTransaccionesFechasRequest")
    @ResponsePayload
    public ListarTransaccionesFechasResponse listarTransaccionesFechas(@RequestPayload ListarTransaccionesFechasRequest request) throws RuntimeException{
        String fechaInicio = request.getTransaccionRQ().getFechaInicio().toString();
        String fechaFin = request.getTransaccionRQ().getFechaFin().toString();
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
            List<Transaccion> transacciones = this.service.listarPorFecha(request.getTransaccionRQ().getCodigoTarjetaCliente(),
                                                                          fechaInicioD, 
                                                                          fechaFinD);
            if (transacciones.size() <= 0) {
               log.error("No hay ninguna transaccion");
               throw new FoundException("No se ha encontrado ningún movimiento");
            } else {
                log.info("Transacciones obtenidas {} con los parametros: {} - {}",
                        transacciones.size(), request.getTransaccionRQ().getFechaInicio(), request.getTransaccionRQ().getFechaFin());
                transacciones.forEach(t -> {
                    try {
                        transaccionesRS.add(buildTransaccionTransformRS(t));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(TransaccionEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            ListarTransaccionesFechasResponse response = new ListarTransaccionesFechasResponse();
            response.setTransaccionRS(transaccionesRS);
            return response;
        } catch (Exception e) {
            log.info("{}" , e.getStackTrace());
            throw new RuntimeException();
        }
    }
}
