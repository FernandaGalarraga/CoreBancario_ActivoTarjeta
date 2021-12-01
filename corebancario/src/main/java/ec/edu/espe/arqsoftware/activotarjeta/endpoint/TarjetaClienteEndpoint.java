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

import ec.edu.espe.arqsoftware.activotarjeta.exception.CreateException;
import ec.edu.espe.arqsoftware.activotarjeta.model.TarjetaCliente;
import ec.edu.espe.arqsoftware.activotarjeta.service.TarjetaClienteService;
import static ec.edu.espe.arqsoftware.activotarjeta.transform.TarjetaClienteRSTransform.buildTarjetaTransformRS;
import ec.edu.espe.arqsoftware.activotarjeta.ws.CrearTarjetaClienteRequest;
import ec.edu.espe.arqsoftware.activotarjeta.ws.CrearTarjetaClienteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Slf4j
public class TarjetaClienteEndpoint {
    private static final String NAMESPACE_URI = "http://espe.edu.ec/arqsoftware/activotarjeta/ws";
    private final TarjetaClienteService service;
    
    @Autowired
    public TarjetaClienteEndpoint(TarjetaClienteService service) {
        this.service = service;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearTarjetaClienteRequest")
    @ResponsePayload
    public CrearTarjetaClienteResponse crearTarjetaCliente(@RequestPayload CrearTarjetaClienteRequest request) throws CreateException{
        try {
            log.info("Se va a crear una tarjeta de credito con la siguiente informacion: {}", request);
            TarjetaCliente tarjeta = new TarjetaCliente();
            tarjeta.setCodigoTarjetaCredito(request.getTarjetaClienteRQ().getCodigoTarjeta());
            tarjeta.setCodigoCliente(request.getTarjetaClienteRQ().getCodigoCliente());
            tarjeta.setNombreTitular(request.getTarjetaClienteRQ().getNombreTitular());
            this.service.crearTarjetaCliente(tarjeta);
            CrearTarjetaClienteResponse response = new CrearTarjetaClienteResponse();
            response.setTarjetaClienteRS(buildTarjetaTransformRS(tarjeta));
            return response;
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("Ocurrio un error al crear la tarjeta. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
            throw new CreateException("Error: " + exceptionMessage);
        }
    }
}
