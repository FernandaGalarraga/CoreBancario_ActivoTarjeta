/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.cliente.tarjetacliente.config;

import ec.edu.espe.arquitectura.cliente.tarjetacliente.service.TarjetaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 *
 * @author Windows Boo
 */
@Configuration
public class WebServiceConfig {
    @Autowired
    private TarjetaClienteService tarjetaCliente;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl");
        return marshaller;
    }

    @Bean
    @Primary
    public TarjetaClienteService tarjetaServ(Jaxb2Marshaller marshaller) {
        tarjetaCliente.setMarshaller(marshaller);
        tarjetaCliente.setUnmarshaller(marshaller);
        return tarjetaCliente;
    }
}
