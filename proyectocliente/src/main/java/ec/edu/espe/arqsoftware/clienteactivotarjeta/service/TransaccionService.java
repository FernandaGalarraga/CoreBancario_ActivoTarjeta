/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.clienteactivotarjeta.service;

import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.ListarTransaccionesFechasRequest;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.ListarTransaccionesFechasResponse;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.TransaccionRQ;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.TransaccionRS;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Service
public class TransaccionService extends WebServiceGatewaySupport{
    private String endpoint = "http://localhost:8090/ws/activotarjeta.wsdl";
    
    public List<TransaccionRS> listarPorFecha(String codigoTarjeta, String fechaInicio, String fechaFin) throws DatatypeConfigurationException{
        XMLGregorianCalendar fechaI = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(fechaInicio);
        XMLGregorianCalendar fechaF = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(fechaFin);
        TransaccionRQ transaccionRQ = new TransaccionRQ();
        transaccionRQ.setCodigoTarjetaCliente(codigoTarjeta);
        transaccionRQ.setFechaInicio(fechaI);
        transaccionRQ.setFechaFin(fechaF);
        
        ListarTransaccionesFechasRequest request = new ListarTransaccionesFechasRequest();
        request.setTransaccionRQ(transaccionRQ);
        
        ListarTransaccionesFechasResponse response
                = (ListarTransaccionesFechasResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        return response.getTransaccionRS();
    }
}
