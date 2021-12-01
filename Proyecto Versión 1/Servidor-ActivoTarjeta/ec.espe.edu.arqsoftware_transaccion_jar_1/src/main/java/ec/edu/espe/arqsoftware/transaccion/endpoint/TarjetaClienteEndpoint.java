/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.arqsoftware.transaccion.endpoint;

import ec.edu.espe.arqsoftware.transaccion.exception.CreateException;
import ec.edu.espe.arqsoftware.transaccion.exception.FoundException;
import ec.edu.espe.arqsoftware.transaccion.model.TarjetaCliente;
import ec.edu.espe.arqsoftware.transaccion.services.TarjetaClienteService;
import static ec.edu.espe.arqsoftware.transaccion.transform.TarjetaActivoRSTransform.buildTransformRS;
import ec.edu.espe.arqsoftware.transaccion.ws.ObtenerTodasRequest;
import ec.edu.espe.arqsoftware.transaccion.ws.ObtenerTodasResponse;
import ec.edu.espe.arqsoftware.transaccion.ws.TarjetaActivoRS;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author papic
 */
@Endpoint
@Slf4j
public class TarjetaClienteEndpoint {

    private static final String NAMESPACE_URI = "http://espe.edu.ec/arqsoftware/transaccion/ws";
    private final TarjetaClienteService service;

    @Autowired

    public TarjetaClienteEndpoint(TarjetaClienteService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerTodasRequest")
    @ResponsePayload
    public ObtenerTodasResponse obtenerTodas(@RequestPayload ObtenerTodasRequest request) {
        log.info("Obteniendo todas las tarjetas de credito esperando por resultados");
        List<TarjetaCliente> tarjetas = this.service.obtenerTarjetaCliente();
        try {
            List<TarjetaActivoRS> tarjetaClienteRS = new ArrayList<>();
            for (TarjetaCliente c : tarjetas) {
                tarjetaClienteRS.add(buildTransformRS(c));
            }

            ObtenerTodasResponse response = new ObtenerTodasResponse();
            response.setTarjetaActivoRS(tarjetaClienteRS);
            return response;
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
            throw new FoundException("Error: " + exceptionMessage);
        }
    }

}
