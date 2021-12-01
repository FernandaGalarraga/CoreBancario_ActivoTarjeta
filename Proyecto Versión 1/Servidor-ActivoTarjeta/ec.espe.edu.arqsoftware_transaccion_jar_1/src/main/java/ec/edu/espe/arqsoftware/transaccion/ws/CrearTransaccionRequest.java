//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 07:29:02 PM ECT 
//


package ec.edu.espe.arqsoftware.transaccion.ws;

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
 *         &lt;element name="transaccionRQ" type="{http://espe.edu.ec/arqsoftware/transaccion/ws}transaccionRQ"/&gt;
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
    "transaccionRQ"
})
@XmlRootElement(name = "crearTransaccionRequest")
public class CrearTransaccionRequest {

    @XmlElement(required = true)
    protected TransaccionRQ transaccionRQ;

    /**
     * Obtiene el valor de la propiedad transaccionRQ.
     * 
     * @return
     *     possible object is
     *     {@link TransaccionRQ }
     *     
     */
    public TransaccionRQ getTransaccionRQ() {
        return transaccionRQ;
    }

    /**
     * Define el valor de la propiedad transaccionRQ.
     * 
     * @param value
     *     allowed object is
     *     {@link TransaccionRQ }
     *     
     */
    public void setTransaccionRQ(TransaccionRQ value) {
        this.transaccionRQ = value;
    }

}
