//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.29 at 12:19:51 AM COT 
//


package ec.edu.espe.arqsoftware.activotarjeta.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transaccionRQ" type="{http://espe.edu.ec/arqsoftware/activotarjeta/ws}transaccionRQ"/&gt;
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
@XmlRootElement(name = "listarTransaccionesFechasRequest")
public class ListarTransaccionesFechasRequest {

    @XmlElement(required = true)
    protected TransaccionRQ transaccionRQ;

    /**
     * Gets the value of the transaccionRQ property.
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
     * Sets the value of the transaccionRQ property.
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
