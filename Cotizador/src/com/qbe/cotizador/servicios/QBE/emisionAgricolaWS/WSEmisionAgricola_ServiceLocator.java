/**
 * WSEmisionAgricola_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.qbe.cotizador.servicios.QBE.emisionAgricolaWS;

public class WSEmisionAgricola_ServiceLocator extends org.apache.axis.client.Service implements com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricola_Service {

    public WSEmisionAgricola_ServiceLocator() {
    }


    public WSEmisionAgricola_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSEmisionAgricola_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSEmisionAgricolaPort
    private java.lang.String WSEmisionAgricolaPort_address = "http://192.168.12.179:8084/ensurance/WSEmisionAgricola";

    public java.lang.String getWSEmisionAgricolaPortAddress() {
        return WSEmisionAgricolaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSEmisionAgricolaPortWSDDServiceName = "WSEmisionAgricolaPort";

    public java.lang.String getWSEmisionAgricolaPortWSDDServiceName() {
        return WSEmisionAgricolaPortWSDDServiceName;
    }

    public void setWSEmisionAgricolaPortWSDDServiceName(java.lang.String name) {
        WSEmisionAgricolaPortWSDDServiceName = name;
    }

    public com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricola_PortType getWSEmisionAgricolaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSEmisionAgricolaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSEmisionAgricolaPort(endpoint);
    }

    public com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricola_PortType getWSEmisionAgricolaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricolaPortBindingStub _stub = new com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricolaPortBindingStub(portAddress, this);
            _stub.setPortName(getWSEmisionAgricolaPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSEmisionAgricolaPortEndpointAddress(java.lang.String address) {
        WSEmisionAgricolaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricola_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricolaPortBindingStub _stub = new com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricolaPortBindingStub(new java.net.URL(WSEmisionAgricolaPort_address), this);
                _stub.setPortName(getWSEmisionAgricolaPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSEmisionAgricolaPort".equals(inputPortName)) {
            return getWSEmisionAgricolaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://agricola.servicios.tandi.com/", "WSEmisionAgricola");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://agricola.servicios.tandi.com/", "WSEmisionAgricolaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSEmisionAgricolaPort".equals(portName)) {
            setWSEmisionAgricolaPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
