/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione package. 
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
    private final static QName _AutenticazioneMUDE_QNAME = new QName("http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", "autenticazioneMUDE");
    private final static QName _AutenticazioneMUDEResponse_QNAME = new QName("http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", "autenticazioneMUDEResponse");
    private final static QName _FruitoreNoComuniAssociatiException_QNAME = new QName("http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", "FruitoreNoComuniAssociatiException");
    private final static QName _SystemException_QNAME = new QName("http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", "SystemException");
    private final static QName _CodiceFiscaleObbligatorioException_QNAME = new QName("http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", "CodiceFiscaleObbligatorioException");
    private final static QName _FruitoreDisabilitatoException_QNAME = new QName("http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", "FruitoreDisabilitatoException");
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione
     * 
     */
    public ObjectFactory() {
    }
    /**
     * Create an instance of {@link AutenticazioneMUDE }
     * 
     */
    public AutenticazioneMUDE createAutenticazioneMUDE() {
        return new AutenticazioneMUDE();
    }
    /**
     * Create an instance of {@link AutenticazioneMUDEResponse }
     * 
     */
    public AutenticazioneMUDEResponse createAutenticazioneMUDEResponse() {
        return new AutenticazioneMUDEResponse();
    }
    /**
     * Create an instance of {@link FruitoreNoComuniAssociatiException }
     * 
     */
    public FruitoreNoComuniAssociatiException createFruitoreNoComuniAssociatiException() {
        return new FruitoreNoComuniAssociatiException();
    }
    /**
     * Create an instance of {@link SystemException }
     * 
     */
    public SystemException createSystemException() {
        return new SystemException();
    }
    /**
     * Create an instance of {@link CodiceFiscaleObbligatorioException }
     * 
     */
    public CodiceFiscaleObbligatorioException createCodiceFiscaleObbligatorioException() {
        return new CodiceFiscaleObbligatorioException();
    }
    /**
     * Create an instance of {@link FruitoreDisabilitatoException }
     * 
     */
    public FruitoreDisabilitatoException createFruitoreDisabilitatoException() {
        return new FruitoreDisabilitatoException();
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutenticazioneMUDE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", name = "autenticazioneMUDE")
    public JAXBElement<AutenticazioneMUDE> createAutenticazioneMUDE(AutenticazioneMUDE value) {
        return new JAXBElement<AutenticazioneMUDE>(_AutenticazioneMUDE_QNAME, AutenticazioneMUDE.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutenticazioneMUDEResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", name = "autenticazioneMUDEResponse")
    public JAXBElement<AutenticazioneMUDEResponse> createAutenticazioneMUDEResponse(AutenticazioneMUDEResponse value) {
        return new JAXBElement<AutenticazioneMUDEResponse>(_AutenticazioneMUDEResponse_QNAME, AutenticazioneMUDEResponse.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FruitoreNoComuniAssociatiException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", name = "FruitoreNoComuniAssociatiException")
    public JAXBElement<FruitoreNoComuniAssociatiException> createFruitoreNoComuniAssociatiException(FruitoreNoComuniAssociatiException value) {
        return new JAXBElement<FruitoreNoComuniAssociatiException>(_FruitoreNoComuniAssociatiException_QNAME, FruitoreNoComuniAssociatiException.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", name = "SystemException")
    public JAXBElement<SystemException> createSystemException(SystemException value) {
        return new JAXBElement<SystemException>(_SystemException_QNAME, SystemException.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodiceFiscaleObbligatorioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", name = "CodiceFiscaleObbligatorioException")
    public JAXBElement<CodiceFiscaleObbligatorioException> createCodiceFiscaleObbligatorioException(CodiceFiscaleObbligatorioException value) {
        return new JAXBElement<CodiceFiscaleObbligatorioException>(_CodiceFiscaleObbligatorioException_QNAME, CodiceFiscaleObbligatorioException.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FruitoreDisabilitatoException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://autenticazione.interfacews.mudesrvextsic.mude.csi.it/", name = "FruitoreDisabilitatoException")
    public JAXBElement<FruitoreDisabilitatoException> createFruitoreDisabilitatoException(FruitoreDisabilitatoException value) {
        return new JAXBElement<FruitoreDisabilitatoException>(_FruitoreDisabilitatoException_QNAME, FruitoreDisabilitatoException.class, null, value);
    }
}
