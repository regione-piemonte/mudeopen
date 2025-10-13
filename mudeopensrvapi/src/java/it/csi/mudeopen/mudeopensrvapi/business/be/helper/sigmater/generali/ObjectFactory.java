/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali package.
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

    private final static QName _Fault_QNAME = new QName("urn:sigalfsrvGenerali", "fault");
    private final static QName _Fault1_QNAME = new QName("urn:sigalfsrvGenerali", "fault1");
    private final static QName _Fault2_QNAME = new QName("urn:sigalfsrvGenerali", "fault2");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CercaElencoComuniPerCodIstatProvincia }
     * 
     */
    public CercaElencoComuniPerCodIstatProvincia createCercaElencoComuniPerCodIstatProvincia() {
        return new CercaElencoComuniPerCodIstatProvincia();
    }

    /**
     * Create an instance of {@link CercaElencoComuniPerCodIstatProvinciaResponse }
     * 
     */
    public CercaElencoComuniPerCodIstatProvinciaResponse createCercaElencoComuniPerCodIstatProvinciaResponse() {
        return new CercaElencoComuniPerCodIstatProvinciaResponse();
    }

    /**
     * Create an instance of {@link IdentificativoComune }
     * 
     */
    public IdentificativoComune createIdentificativoComune() {
        return new IdentificativoComune();
    }

    /**
     * Create an instance of {@link CSIException }
     * 
     */
    public CSIException createCSIException() {
        return new CSIException();
    }

    /**
     * Create an instance of {@link SystemException }
     * 
     */
    public SystemException createSystemException() {
        return new SystemException();
    }

    /**
     * Create an instance of {@link UnrecoverableException }
     * 
     */
    public UnrecoverableException createUnrecoverableException() {
        return new UnrecoverableException();
    }

    /**
     * Create an instance of {@link CercaElencoComuni }
     * 
     */
    public CercaElencoComuni createCercaElencoComuni() {
        return new CercaElencoComuni();
    }

    /**
     * Create an instance of {@link CercaElencoComuniResponse }
     * 
     */
    public CercaElencoComuniResponse createCercaElencoComuniResponse() {
        return new CercaElencoComuniResponse();
    }

    /**
     * Create an instance of {@link IsComuneAventeCodiceBelfioreAbilitatoSigmater }
     * 
     */
    public IsComuneAventeCodiceBelfioreAbilitatoSigmater createIsComuneAventeCodiceBelfioreAbilitatoSigmater() {
        return new IsComuneAventeCodiceBelfioreAbilitatoSigmater();
    }

    /**
     * Create an instance of {@link IsComuneAventeCodiceBelfioreAbilitatoSigmaterResponse }
     * 
     */
    public IsComuneAventeCodiceBelfioreAbilitatoSigmaterResponse createIsComuneAventeCodiceBelfioreAbilitatoSigmaterResponse() {
        return new IsComuneAventeCodiceBelfioreAbilitatoSigmaterResponse();
    }

    /**
     * Create an instance of {@link IsComuneAventeCodiceIstatAbilitatoSigmater }
     * 
     */
    public IsComuneAventeCodiceIstatAbilitatoSigmater createIsComuneAventeCodiceIstatAbilitatoSigmater() {
        return new IsComuneAventeCodiceIstatAbilitatoSigmater();
    }

    /**
     * Create an instance of {@link IsComuneAventeCodiceIstatAbilitatoSigmaterResponse }
     * 
     */
    public IsComuneAventeCodiceIstatAbilitatoSigmaterResponse createIsComuneAventeCodiceIstatAbilitatoSigmaterResponse() {
        return new IsComuneAventeCodiceIstatAbilitatoSigmaterResponse();
    }

    /**
     * Create an instance of {@link CercaElencoIdentificativiComunePerCodiceIstat }
     * 
     */
    public CercaElencoIdentificativiComunePerCodiceIstat createCercaElencoIdentificativiComunePerCodiceIstat() {
        return new CercaElencoIdentificativiComunePerCodiceIstat();
    }

    /**
     * Create an instance of {@link CercaElencoIdentificativiComunePerCodiceIstatResponse }
     * 
     */
    public CercaElencoIdentificativiComunePerCodiceIstatResponse createCercaElencoIdentificativiComunePerCodiceIstatResponse() {
        return new CercaElencoIdentificativiComunePerCodiceIstatResponse();
    }

    /**
     * Create an instance of {@link CercaElencoIdentificativiComunePerNomeComune }
     * 
     */
    public CercaElencoIdentificativiComunePerNomeComune createCercaElencoIdentificativiComunePerNomeComune() {
        return new CercaElencoIdentificativiComunePerNomeComune();
    }

    /**
     * Create an instance of {@link CercaElencoIdentificativiComunePerNomeComuneResponse }
     * 
     */
    public CercaElencoIdentificativiComunePerNomeComuneResponse createCercaElencoIdentificativiComunePerNomeComuneResponse() {
        return new CercaElencoIdentificativiComunePerNomeComuneResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CSIException }{@code >}
     * 
     *     Java instance representing xml element's value.

     *     the new instance of {@link JAXBElement }{@code <}{@link CSIException }{@code >}
     */
    @XmlElementDecl(namespace = "urn:sigalfsrvGenerali", name = "fault")
    public JAXBElement<CSIException> createFault(CSIException value) {
        return new JAXBElement<CSIException>(_Fault_QNAME, CSIException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}
     * 
     *     Java instance representing xml element's value.

     *     the new instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}
     */
    @XmlElementDecl(namespace = "urn:sigalfsrvGenerali", name = "fault1")
    public JAXBElement<SystemException> createFault1(SystemException value) {
        return new JAXBElement<SystemException>(_Fault1_QNAME, SystemException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnrecoverableException }{@code >}
     * 
     *     Java instance representing xml element's value.

     *     the new instance of {@link JAXBElement }{@code <}{@link UnrecoverableException }{@code >}
     */
    @XmlElementDecl(namespace = "urn:sigalfsrvGenerali", name = "fault2")
    public JAXBElement<UnrecoverableException> createFault2(UnrecoverableException value) {
        return new JAXBElement<UnrecoverableException>(_Fault2_QNAME, UnrecoverableException.class, null, value);
    }

}
