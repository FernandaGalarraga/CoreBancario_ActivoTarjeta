//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 03:43:20 PM COT 
//


package ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="tarjetaActivoRS" type="{http://espe.edu.ec/arquitectura/soap/tarjetacliente/ws}tarjetaActivoRS" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "tarjetaActivoRS"
})
@XmlRootElement(name = "obtenerActivoTarjetaClienteResponse")
public class ObtenerActivoTarjetaClienteResponse {

    protected List<TarjetaActivoRS> tarjetaActivoRS;

    /**
     * Gets the value of the tarjetaActivoRS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tarjetaActivoRS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarjetaActivoRS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TarjetaActivoRS }
     * 
     * 
     */
    public List<TarjetaActivoRS> getTarjetaActivoRS() {
        if (tarjetaActivoRS == null) {
            tarjetaActivoRS = new ArrayList<TarjetaActivoRS>();
        }
        return this.tarjetaActivoRS;
    }

}
