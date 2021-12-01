//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 01:28:45 PM COT 
//


package ec.edu.espe.arquitectura.soap.tarjetacliente.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Setter;


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
 *         &lt;element name="tarjetaClienteRS" type="{http://espe.edu.ec/arquitectura/soap/tarjetacliente/ws}tarjetaClienteRS" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tarjetaClienteRS"
})
@XmlRootElement(name = "listarMovimientosActualesResponse")
public class ListarMovimientosActualesResponse {

    protected List<TarjetaClienteRS> tarjetaClienteRS;

    /**
     * Gets the value of the tarjetaClienteRS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tarjetaClienteRS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarjetaClienteRS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TarjetaClienteRS }
     * 
     * 
     */
    public List<TarjetaClienteRS> getTarjetaClienteRS() {
        if (tarjetaClienteRS == null) {
            tarjetaClienteRS = new ArrayList<TarjetaClienteRS>();
        }
        return this.tarjetaClienteRS;
    }

}
