
package com.qbe.cotizador.servicios.smartwork.workflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfQBE_Entidad complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfQBE_Entidad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="QBE_Entidad" type="{http://smartwork.com.ec/}QBE_Entidad" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfQBE_Entidad", propOrder = {
    "qbeEntidad"
})
public class ArrayOfQBEEntidad {

    @XmlElement(name = "QBE_Entidad", nillable = true)
    protected List<QBEEntidad> qbeEntidad;

    /**
     * Gets the value of the qbeEntidad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qbeEntidad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQBEEntidad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QBEEntidad }
     * 
     * 
     */
    public List<QBEEntidad> getQBEEntidad() {
        if (qbeEntidad == null) {
            qbeEntidad = new ArrayList<QBEEntidad>();
        }
        return this.qbeEntidad;
    }

	public void setQbeEntidad(List<QBEEntidad> qbeEntidad) {
		this.qbeEntidad = qbeEntidad;
	}
    
}
