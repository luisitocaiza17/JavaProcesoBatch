
package com.qbe.cotizador.servicios.QBE.emisionWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tandi.servicios.wsqbelink package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Autenticar_QNAME = new QName("http://wsqbelink.servicios.tandi.com/", "autenticar1");
    private final static QName _EmisionGeneral_QNAME = new QName("http://wsqbelink.servicios.tandi.com/", "emisionGeneral1");
    private final static QName _EmisionGeneralResponse_QNAME = new QName("http://wsqbelink.servicios.tandi.com/", "emisionGeneralResponse1");
    private final static QName _AutenticarResponse_QNAME = new QName("http://wsqbelink.servicios.tandi.com/", "autenticarResponse1");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tandi.servicios.wsqbelink
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EmisionGeneralResponse }
     * 
     */
    public EmisionGeneralResponse createEmisionGeneralResponse() {
        return new EmisionGeneralResponse();
    }

    /**
     * Create an instance of {@link EmisionGeneral }
     * 
     */
    public EmisionGeneral createEmisionGeneral() {
        return new EmisionGeneral();
    }

    /**
     * Create an instance of {@link Autenticar }
     * 
     */
    public Autenticar createAutenticar() {
        return new Autenticar();
    }

    /**
     * Create an instance of {@link AutenticarResponse }
     * 
     */
    public AutenticarResponse createAutenticarResponse() {
        return new AutenticarResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Autenticar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsqbelink.servicios.tandi.com/", name = "autenticar1")
    public JAXBElement<Autenticar> createAutenticar(Autenticar value) {
        return new JAXBElement<Autenticar>(_Autenticar_QNAME, Autenticar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmisionGeneral }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsqbelink.servicios.tandi.com/", name = "emisionGeneral1")
    public JAXBElement<EmisionGeneral> createEmisionGeneral(EmisionGeneral value) {
        return new JAXBElement<EmisionGeneral>(_EmisionGeneral_QNAME, EmisionGeneral.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmisionGeneralResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsqbelink.servicios.tandi.com/", name = "emisionGeneralResponse1")
    public JAXBElement<EmisionGeneralResponse> createEmisionGeneralResponse(EmisionGeneralResponse value) {
        return new JAXBElement<EmisionGeneralResponse>(_EmisionGeneralResponse_QNAME, EmisionGeneralResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutenticarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsqbelink.servicios.tandi.com/", name = "autenticarResponse1")
    public JAXBElement<AutenticarResponse> createAutenticarResponse(AutenticarResponse value) {
        return new JAXBElement<AutenticarResponse>(_AutenticarResponse_QNAME, AutenticarResponse.class, null, value);
    }

}
