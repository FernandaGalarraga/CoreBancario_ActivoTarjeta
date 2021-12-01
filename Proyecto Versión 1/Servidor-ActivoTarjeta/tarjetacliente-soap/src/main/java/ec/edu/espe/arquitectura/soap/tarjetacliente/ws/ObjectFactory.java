//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 01:28:45 PM COT 
//


package ec.edu.espe.arquitectura.soap.tarjetacliente.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ec.edu.espe.arquitectura.soap.tarjetacliente.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ec.edu.espe.arquitectura.soap.tarjetacliente.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerPorNroTarjetaRequest }
     * 
     */
    public ObtenerPorNroTarjetaRequest createObtenerPorNroTarjetaRequest() {
        return new ObtenerPorNroTarjetaRequest();
    }

    /**
     * Create an instance of {@link ObtenerPorNroTarjetaResponse }
     * 
     */
    public ObtenerPorNroTarjetaResponse createObtenerPorNroTarjetaResponse() {
        return new ObtenerPorNroTarjetaResponse();
    }

    /**
     * Create an instance of {@link TarjetaClienteRS }
     * 
     */
    public TarjetaClienteRS createTarjetaClienteRS() {
        return new TarjetaClienteRS();
    }

    /**
     * Create an instance of {@link ObtenerActivoTarjetaClienteRequest }
     * 
     */
    public ObtenerActivoTarjetaClienteRequest createObtenerActivoTarjetaClienteRequest() {
        return new ObtenerActivoTarjetaClienteRequest();
    }

    /**
     * Create an instance of {@link ObtenerActivoTarjetaClienteResponse }
     * 
     */
    public ObtenerActivoTarjetaClienteResponse createObtenerActivoTarjetaClienteResponse() {
        return new ObtenerActivoTarjetaClienteResponse();
    }

    /**
     * Create an instance of {@link TarjetaActivoRS }
     * 
     */
    public TarjetaActivoRS createTarjetaActivoRS() {
        return new TarjetaActivoRS();
    }

    /**
     * Create an instance of {@link ListarMovimientosActualesRequest }
     * 
     */
    public ListarMovimientosActualesRequest createListarMovimientosActualesRequest() {
        return new ListarMovimientosActualesRequest();
    }

    /**
     * Create an instance of {@link ListarMovimientosActualesResponse }
     * 
     */
    public ListarMovimientosActualesResponse createListarMovimientosActualesResponse() {
        return new ListarMovimientosActualesResponse();
    }

    /**
     * Create an instance of {@link TransaccionRS }
     * 
     */
    public TransaccionRS createTransaccionRS() {
        return new TransaccionRS();
    }

}
