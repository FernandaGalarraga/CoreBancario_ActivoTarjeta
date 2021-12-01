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
 *         &lt;element name="transaccionRS" type="{http://espe.edu.ec/arqsoftware/transaccion/ws}transaccionRS"/&gt;
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
    "transaccionRS"
})
@XmlRootElement(name = "listarUltimasTransaccionesResponse")
public class ListarUltimasTransaccionesResponse {

    @XmlElement(required = true)
    protected TransaccionRS transaccionRS;

    /**
     * Obtiene el valor de la propiedad transaccionRS.
     * 
     * @return
     *     possible object is
     *     {@link TransaccionRS }
     *     
     */
    public TransaccionRS getTransaccionRS() {
        return transaccionRS;
    }

    /**
     * Define el valor de la propiedad transaccionRS.
     * 
     * @param value
     *     allowed object is
     *     {@link TransaccionRS }
     *     
     */
    public void setTransaccionRS(TransaccionRS value) {
        this.transaccionRS = value;
    }

}
