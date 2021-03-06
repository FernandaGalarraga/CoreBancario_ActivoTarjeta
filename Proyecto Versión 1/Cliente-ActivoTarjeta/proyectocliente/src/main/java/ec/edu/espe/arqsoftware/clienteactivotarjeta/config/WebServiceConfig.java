/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.clienteactivotarjeta.config;

import ec.edu.espe.arqsoftware.clienteactivotarjeta.service.TarjetaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WebServiceConfig {
    
    @Autowired
    //private TransaccionService transaccion;
    private TarjetaClienteService tarjetaCliente;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl");
        return marshaller;
    }

    @Bean
    @Primary
    public TarjetaClienteService tarjetaServ(Jaxb2Marshaller marshaller) {
        tarjetaCliente.setMarshaller(marshaller);
        tarjetaCliente.setUnmarshaller(marshaller);
        return tarjetaCliente;
    }
    
    /*@Bean
    @Primary
    public TarjetaClienteService tarjetaClienteServ(Jaxb2Marshaller marshaller) {
        tarjetaCliente.setMarshaller(marshaller);
        tarjetaCliente.setUnmarshaller(marshaller);
        return tarjetaCliente;
    }*/
}
