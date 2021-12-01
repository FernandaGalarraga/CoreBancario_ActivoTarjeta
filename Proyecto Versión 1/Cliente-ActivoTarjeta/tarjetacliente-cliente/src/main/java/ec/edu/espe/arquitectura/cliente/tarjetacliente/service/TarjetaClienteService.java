/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.cliente.tarjetacliente.service;

import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.ListarMovimientosActualesRequest;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.ListarMovimientosActualesResponse;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.ObtenerActivoTarjetaClienteRequest;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.ObtenerActivoTarjetaClienteResponse;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.ObtenerPorNroTarjetaRequest;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.ObtenerPorNroTarjetaResponse;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.TarjetaActivoRS;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.TarjetaClienteRS;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 *
 * @author Windows Boo
 */
@Service
public class TarjetaClienteService extends WebServiceGatewaySupport{
    private String endpoint = "http://localhost:8090/ws/tarjetaCliente.wsdl";
    
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
}
