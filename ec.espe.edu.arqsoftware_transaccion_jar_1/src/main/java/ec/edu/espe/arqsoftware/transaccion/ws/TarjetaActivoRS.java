//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.11.29 a las 07:29:02 PM ECT 
//


package ec.edu.espe.arqsoftware.transaccion.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * <p>Clase Java para tarjetaActivoRS complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="tarjetaActivoRS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codTarjetaCliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codTarjetaCredito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codCliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="emisor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroTarjeta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cupoDisponible" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="cupoTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="fechaExpedicion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="fechaExpiracion" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tarjetaActivoRS", propOrder = {
    "codTarjetaCliente",
    "codTarjetaCredito",
    "codCliente",
    "emisor",
    "estado",
    "numeroTarjeta",
    "cupoDisponible",
    "cupoTotal",
    "fechaExpedicion",
    "fechaExpiracion"
})
@Builder(toBuilder=true)
@AllArgsConstructor(access= AccessLevel.PACKAGE)
@NoArgsConstructor(access= AccessLevel.PACKAGE)
@Setter(value= AccessLevel.PACKAGE)
@Getter
public class TarjetaActivoRS {

    @XmlElement(required = true)
    protected String codTarjetaCliente;
    @XmlElement(required = true)
    protected String codTarjetaCredito;
    @XmlElement(required = true)
    protected String codCliente;
    @XmlElement(required = true)
    protected String emisor;
    @XmlElement(required = true)
    protected String estado;
    @XmlElement(required = true)
    protected String numeroTarjeta;
    @XmlElement(required = true)
    protected BigDecimal cupoDisponible;
    @XmlElement(required = true)
    protected BigDecimal cupoTotal;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaExpedicion;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaExpiracion;

    /**
     * Obtiene el valor de la propiedad codTarjetaCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTarjetaCliente() {
        return codTarjetaCliente;
    }

    /**
     * Define el valor de la propiedad codTarjetaCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTarjetaCliente(String value) {
        this.codTarjetaCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad codTarjetaCredito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTarjetaCredito() {
        return codTarjetaCredito;
    }

    /**
     * Define el valor de la propiedad codTarjetaCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTarjetaCredito(String value) {
        this.codTarjetaCredito = value;
    }

    /**
     * Obtiene el valor de la propiedad codCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCliente() {
        return codCliente;
    }

    /**
     * Define el valor de la propiedad codCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCliente(String value) {
        this.codCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad emisor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmisor() {
        return emisor;
    }

    /**
     * Define el valor de la propiedad emisor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmisor(String value) {
        this.emisor = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroTarjeta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Define el valor de la propiedad numeroTarjeta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroTarjeta(String value) {
        this.numeroTarjeta = value;
    }

    /**
     * Obtiene el valor de la propiedad cupoDisponible.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCupoDisponible() {
        return cupoDisponible;
    }

    /**
     * Define el valor de la propiedad cupoDisponible.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCupoDisponible(BigDecimal value) {
        this.cupoDisponible = value;
    }

    /**
     * Obtiene el valor de la propiedad cupoTotal.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCupoTotal() {
        return cupoTotal;
    }

    /**
     * Define el valor de la propiedad cupoTotal.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCupoTotal(BigDecimal value) {
        this.cupoTotal = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaExpedicion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaExpedicion() {
        return fechaExpedicion;
    }

    /**
     * Define el valor de la propiedad fechaExpedicion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaExpedicion(XMLGregorianCalendar value) {
        this.fechaExpedicion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaExpiracion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * Define el valor de la propiedad fechaExpiracion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaExpiracion(XMLGregorianCalendar value) {
        this.fechaExpiracion = value;
    }

}
