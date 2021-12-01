/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.clienteactivotarjeta.service;

import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.CrearTarjetaClienteRequest;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.CrearTarjetaClienteResponse;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.TarjetaClienteRQ;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.TarjetaClienteRS;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Service
public class TarjetaClienteService extends WebServiceGatewaySupport{
    private String endpoint = "http://localhost:8090/ws/activotarjeta.wsdl";
    
    public TarjetaClienteRS crearTarjetaCliente(TarjetaClienteRQ tarjetaCliente) throws DatatypeConfigurationException{
        
        CrearTarjetaClienteRequest request = new CrearTarjetaClienteRequest();
        
        request.setTarjetaClienteRQ(tarjetaCliente);
        
        CrearTarjetaClienteResponse response
                = (CrearTarjetaClienteResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        return response.getTarjetaClienteRS();
    }
}
