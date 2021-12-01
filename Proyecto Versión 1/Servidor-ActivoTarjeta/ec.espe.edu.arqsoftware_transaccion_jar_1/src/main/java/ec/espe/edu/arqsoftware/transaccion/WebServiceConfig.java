/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.espe.edu.arqsoftware.transaccion;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 *
 * @author papic
 */
@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "transaccion")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema transaccionSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TransaccionPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://espe.edu.ec/arqsoftware/transaccion/ws");
        wsdl11Definition.setSchema(transaccionSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema transaccionSchema() {
        return new SimpleXsdSchema(new ClassPathResource("transaccion.xsd"));
    }
    
    @Bean(name = "activotarjeta")
    public DefaultWsdl11Definition defaultWsdl11Definition1(XsdSchema tarjetaSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TarjetaPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://espe.edu.ec/arqsoftware/transaccion/ws");
        wsdl11Definition.setSchema(tarjetaSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema tarjetaSchema() {
        return new SimpleXsdSchema(new ClassPathResource("activotarjeta.xsd"));
    }
}
