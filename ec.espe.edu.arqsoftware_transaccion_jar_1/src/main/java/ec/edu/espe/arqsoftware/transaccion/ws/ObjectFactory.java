//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 07:29:02 PM ECT 
//


package ec.edu.espe.arqsoftware.transaccion.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ec.edu.espe.arqsoftware.transaccion.ws package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ec.edu.espe.arqsoftware.transaccion.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerTodasRequest }
     * 
     */
    public ObtenerTodasRequest createObtenerTodasRequest() {
        return new ObtenerTodasRequest();
    }

    /**
     * Create an instance of {@link ObtenerTodasResponse }
     * 
     */
    public ObtenerTodasResponse createObtenerTodasResponse() {
        return new ObtenerTodasResponse();
    }

    /**
     * Create an instance of {@link TarjetaActivoRS }
     * 
     */
    public TarjetaActivoRS createTarjetaActivoRS() {
        return new TarjetaActivoRS();
    }

    /**
     * Create an instance of {@link ListarUltimasTransaccionesRequest }
     * 
     */
    public ListarUltimasTransaccionesRequest createListarUltimasTransaccionesRequest() {
        return new ListarUltimasTransaccionesRequest();
    }

    /**
     * Create an instance of {@link ListarUltimasTransaccionesResponse }
     * 
     */
    public ListarUltimasTransaccionesResponse createListarUltimasTransaccionesResponse() {
        return new ListarUltimasTransaccionesResponse();
    }

    /**
     * Create an instance of {@link TransaccionRS }
     * 
     */
    public TransaccionRS createTransaccionRS() {
        return new TransaccionRS();
    }

    /**
     * Create an instance of {@link CrearTransaccionRequest }
     * 
     */
    public CrearTransaccionRequest createCrearTransaccionRequest() {
        return new CrearTransaccionRequest();
    }

    /**
     * Create an instance of {@link TransaccionRQ }
     * 
     */
    public TransaccionRQ createTransaccionRQ() {
        return new TransaccionRQ();
    }

    /**
     * Create an instance of {@link CrearTransaccionResponse }
     * 
     */
    public CrearTransaccionResponse createCrearTransaccionResponse() {
        return new CrearTransaccionResponse();
    }

}
