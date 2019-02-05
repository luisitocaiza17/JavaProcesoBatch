/**
 * CoberturaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tandi.servicios.DTOs;

public class CoberturaDTO  implements java.io.Serializable {
    private boolean afectaMonto;

    private java.lang.String id;

    private boolean restaPrincipal;

    private boolean servicio;

    private double valorMonto;

    private double valorPrima;

    public CoberturaDTO() {
    }

    public CoberturaDTO(
           boolean afectaMonto,
           java.lang.String id,
           boolean restaPrincipal,
           boolean servicio,
           double valorMonto,
           double valorPrima) {
           this.afectaMonto = afectaMonto;
           this.id = id;
           this.restaPrincipal = restaPrincipal;
           this.servicio = servicio;
           this.valorMonto = valorMonto;
           this.valorPrima = valorPrima;
    }


    /**
     * Gets the afectaMonto value for this CoberturaDTO.
     * 
     * @return afectaMonto
     */
    public boolean isAfectaMonto() {
        return afectaMonto;
    }


    /**
     * Sets the afectaMonto value for this CoberturaDTO.
     * 
     * @param afectaMonto
     */
    public void setAfectaMonto(boolean afectaMonto) {
        this.afectaMonto = afectaMonto;
    }


    /**
     * Gets the id value for this CoberturaDTO.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this CoberturaDTO.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the restaPrincipal value for this CoberturaDTO.
     * 
     * @return restaPrincipal
     */
    public boolean isRestaPrincipal() {
        return restaPrincipal;
    }


    /**
     * Sets the restaPrincipal value for this CoberturaDTO.
     * 
     * @param restaPrincipal
     */
    public void setRestaPrincipal(boolean restaPrincipal) {
        this.restaPrincipal = restaPrincipal;
    }


    /**
     * Gets the servicio value for this CoberturaDTO.
     * 
     * @return servicio
     */
    public boolean isServicio() {
        return servicio;
    }


    /**
     * Sets the servicio value for this CoberturaDTO.
     * 
     * @param servicio
     */
    public void setServicio(boolean servicio) {
        this.servicio = servicio;
    }


    /**
     * Gets the valorMonto value for this CoberturaDTO.
     * 
     * @return valorMonto
     */
    public double getValorMonto() {
        return valorMonto;
    }


    /**
     * Sets the valorMonto value for this CoberturaDTO.
     * 
     * @param valorMonto
     */
    public void setValorMonto(double valorMonto) {
        this.valorMonto = valorMonto;
    }


    /**
     * Gets the valorPrima value for this CoberturaDTO.
     * 
     * @return valorPrima
     */
    public double getValorPrima() {
        return valorPrima;
    }


    /**
     * Sets the valorPrima value for this CoberturaDTO.
     * 
     * @param valorPrima
     */
    public void setValorPrima(double valorPrima) {
        this.valorPrima = valorPrima;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CoberturaDTO)) return false;
        CoberturaDTO other = (CoberturaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.afectaMonto == other.isAfectaMonto() &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            this.restaPrincipal == other.isRestaPrincipal() &&
            this.servicio == other.isServicio() &&
            this.valorMonto == other.getValorMonto() &&
            this.valorPrima == other.getValorPrima();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (isAfectaMonto() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        _hashCode += (isRestaPrincipal() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isServicio() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Double(getValorMonto()).hashCode();
        _hashCode += new Double(getValorPrima()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CoberturaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:DTOs.servicios.tandi.com", "CoberturaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("afectaMonto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "afectaMonto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("restaPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "restaPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorMonto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorMonto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorPrima");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorPrima"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
