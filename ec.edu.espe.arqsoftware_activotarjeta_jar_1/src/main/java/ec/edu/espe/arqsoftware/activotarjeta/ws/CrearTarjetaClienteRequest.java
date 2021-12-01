//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 03:09:03 PM ECT 
//


package ec.edu.espe.arqsoftware.activotarjeta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tarjetaClienteRQ" type="{http://espe.edu.ec/arqsoftware/activotarjeta/ws}tarjetaClienteRQ"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tarjetaClienteRQ"
})
@XmlRootElement(name = "crearTarjetaClienteRequest")
public class CrearTarjetaClienteRequest {

    @XmlElement(required = true)
    protected TarjetaClienteRQ tarjetaClienteRQ;

    /**
     * Obtiene el valor de la propiedad tarjetaClienteRQ.
     * 
     * @return
     *     possible object is
     *     {@link TarjetaClienteRQ }
     *     
     */
    public TarjetaClienteRQ getTarjetaClienteRQ() {
        return tarjetaClienteRQ;
    }

    /**
     * Define el valor de la propiedad tarjetaClienteRQ.
     * 
     * @param value
     *     allowed object is
     *     {@link TarjetaClienteRQ }
     *     
     */
    public void setTarjetaClienteRQ(TarjetaClienteRQ value) {
        this.tarjetaClienteRQ = value;
    }

}
