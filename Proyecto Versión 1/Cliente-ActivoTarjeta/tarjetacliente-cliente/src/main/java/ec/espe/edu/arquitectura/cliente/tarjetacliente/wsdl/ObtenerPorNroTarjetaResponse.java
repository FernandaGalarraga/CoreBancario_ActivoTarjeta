//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 03:43:20 PM COT 
//


package ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl;

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
 *         &lt;element name="tarjetaClienteRS" type="{http://espe.edu.ec/arquitectura/soap/tarjetacliente/ws}tarjetaClienteRS"/&gt;
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
    "tarjetaClienteRS"
})
@XmlRootElement(name = "obtenerPorNroTarjetaResponse")
public class ObtenerPorNroTarjetaResponse {

    @XmlElement(required = true)
    protected TarjetaClienteRS tarjetaClienteRS;

    /**
     * Obtiene el valor de la propiedad tarjetaClienteRS.
     * 
     * @return
     *     possible object is
     *     {@link TarjetaClienteRS }
     *     
     */
    public TarjetaClienteRS getTarjetaClienteRS() {
        return tarjetaClienteRS;
    }

    /**
     * Define el valor de la propiedad tarjetaClienteRS.
     * 
     * @param value
     *     allowed object is
     *     {@link TarjetaClienteRS }
     *     
     */
    public void setTarjetaClienteRS(TarjetaClienteRS value) {
        this.tarjetaClienteRS = value;
    }

}
