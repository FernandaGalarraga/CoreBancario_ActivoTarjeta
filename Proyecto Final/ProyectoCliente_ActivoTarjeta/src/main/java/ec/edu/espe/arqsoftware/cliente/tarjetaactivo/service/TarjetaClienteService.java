/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.cliente.tarjetaactivo.service;

import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.CrearTarjetaClienteRequest;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.CrearTarjetaClienteResponse;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ListarMovimientosActualesRequest;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ListarMovimientosActualesResponse;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerActivoTarjetaClienteRequest;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerActivoTarjetaClienteResponse;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerPorNroTarjetaRequest;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerPorNroTarjetaResponse;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerTodasRequest;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerTodasResponse;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TarjetaActivoRS;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TarjetaClienteRQ;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TarjetaClienteRS;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Service
public class TarjetaClienteService extends WebServiceGatewaySupport{
    @Value("${server.cliente.uri}/activotarjeta.wsdl")
    private String endpoint;
    
    public TarjetaActivoRS crearTarjetaCliente(TarjetaClienteRQ tarjetaCliente) throws DatatypeConfigurationException{
        
        CrearTarjetaClienteRequest request = new CrearTarjetaClienteRequest();
        
        request.setTarjetaClienteRQ(tarjetaCliente);
        
        CrearTarjetaClienteResponse response
                = (CrearTarjetaClienteResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        return response.getTarjetaActivoRS();
    }
    
    public TarjetaClienteRS obtenerPorNumeroTarjeta(String numero) throws DatatypeConfigurationException{
        ObtenerPorNroTarjetaRequest request = new ObtenerPorNroTarjetaRequest();
        request.setNumeroTarjeta(numero);
        ObtenerPorNroTarjetaResponse response = (ObtenerPorNroTarjetaResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        return response.getTarjetaClienteRS();
    }
    
    public List<TarjetaActivoRS> obtenerPorCodigoCliente(String codCliente) throws DatatypeConfigurationException{
        ObtenerActivoTarjetaClienteRequest request = new ObtenerActivoTarjetaClienteRequest();
        request.setCodCliente(codCliente);
        ObtenerActivoTarjetaClienteResponse response = (ObtenerActivoTarjetaClienteResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        return response.getTarjetaActivoRS();
    }
    
    public List<TarjetaClienteRS> obtenerMovimientosActuales() throws DatatypeConfigurationException{
        ListarMovimientosActualesRequest request = new ListarMovimientosActualesRequest();
        ListarMovimientosActualesResponse response = (ListarMovimientosActualesResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        return  response.getTarjetaClienteRS();
    }
    
    public ObtenerTodasResponse ObtenerTodas(ObtenerTodasRequest request ){
        ObtenerTodasResponse  response= (ObtenerTodasResponse ) getWebServiceTemplate()
                .marshalSendAndReceive(endpoint, request);
        return response;
    }
}
