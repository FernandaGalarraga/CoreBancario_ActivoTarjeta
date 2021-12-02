/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.soap.tarjetacliente.endpoint;

import ec.edu.espe.arquitectura.soap.tarjetacliente.exception.FoundException;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.TarjetaCliente;
import ec.edu.espe.arquitectura.soap.tarjetacliente.model.Transaccion;
import ec.edu.espe.arquitectura.soap.tarjetacliente.service.TarjetaClienteService;
import ec.edu.espe.arquitectura.soap.tarjetacliente.service.TransaccionService;
import static ec.edu.espe.arquitectura.soap.tarjetacliente.transform.TarjetaActivoRSTransform.buildTransformRS;
import static ec.edu.espe.arquitectura.soap.tarjetacliente.transform.TarjetaClienteRSTransform.buildTarjetaTransformRS;
import static ec.edu.espe.arquitectura.soap.tarjetacliente.transform.TransaccionRSTransform.buildTransaccionTransformRS;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.ListarMovimientosActualesRequest;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.ListarMovimientosActualesResponse;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.ObtenerActivoTarjetaClienteRequest;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.ObtenerActivoTarjetaClienteResponse;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.ObtenerPorNroTarjetaRequest;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.ObtenerPorNroTarjetaResponse;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.TarjetaActivoRS;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.TarjetaClienteRS;
import ec.edu.espe.arquitectura.soap.tarjetacliente.ws.TransaccionRS;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author Windows Boo
 */
@Endpoint
@Slf4j
public class TarjetaClienteEndpoint {

    private static final String NAMESPACE_URI = "http://espe.edu.ec/arquitectura/soap/tarjetacliente/ws";
    private final TarjetaClienteService service;
    private final TransaccionService transaccionService;

    public TarjetaClienteEndpoint(TarjetaClienteService service, TransaccionService transaccionService) {
        this.service = service;
        this.transaccionService = transaccionService;
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
                tarjetasRS.getTransaccionRS().addAll(transaccionesRS);
                tarjetasClienteRS.add(tarjetasRS);

            } catch (Exception e) {
                String exceptionMessage = e.getMessage();
                log.error("Ocurrio un error. {} - retorna badrequest - causado por: {}", e.getMessage(), exceptionMessage);
                throw new FoundException("Error: " + exceptionMessage);
            }
        }
        ListarMovimientosActualesResponse response = new ListarMovimientosActualesResponse();
        response.setTarjetaClienteRS(tarjetasClienteRS);
        return response;
    }
}
