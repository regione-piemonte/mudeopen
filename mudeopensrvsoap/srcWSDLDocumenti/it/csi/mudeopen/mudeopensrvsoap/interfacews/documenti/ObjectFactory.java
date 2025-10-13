/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti package. 
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
    private final static QName _EstraiFileIstanza_QNAME = new QName("http://documenti.interfacews.mudesrvextsic.mude.csi.it/", "estraiFileIstanza");
    private final static QName _EstraiFileIstanzaResponse_QNAME = new QName("http://documenti.interfacews.mudesrvextsic.mude.csi.it/", "estraiFileIstanzaResponse");
    private final static QName _FileOutputNomeFile_QNAME = new QName("", "nomeFile");
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti
     * 
     */
    public ObjectFactory() {
    }
    /**
     * Create an instance of {@link EstraiFileIstanza }
     * 
     */
    public EstraiFileIstanza createEstraiFileIstanza() {
        return new EstraiFileIstanza();
    }
    /**
     * Create an instance of {@link EstraiFileIstanzaResponse }
     * 
     */
    public EstraiFileIstanzaResponse createEstraiFileIstanzaResponse() {
        return new EstraiFileIstanzaResponse();
    }
    /**
     * Create an instance of {@link FileOutput }
     * 
     */
    public FileOutput createFileOutput() {
        return new FileOutput();
    }
    /**
     * Create an instance of {@link SoapResponse }
     * 
     */
    public SoapResponse createSoapResponse() {
        return new SoapResponse();
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EstraiFileIstanza }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documenti.interfacews.mudesrvextsic.mude.csi.it/", name = "estraiFileIstanza")
    public JAXBElement<EstraiFileIstanza> createEstraiFileIstanza(EstraiFileIstanza value) {
        return new JAXBElement<EstraiFileIstanza>(_EstraiFileIstanza_QNAME, EstraiFileIstanza.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EstraiFileIstanzaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documenti.interfacews.mudesrvextsic.mude.csi.it/", name = "estraiFileIstanzaResponse")
    public JAXBElement<EstraiFileIstanzaResponse> createEstraiFileIstanzaResponse(EstraiFileIstanzaResponse value) {
        return new JAXBElement<EstraiFileIstanzaResponse>(_EstraiFileIstanzaResponse_QNAME, EstraiFileIstanzaResponse.class, null, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nomeFile", scope = FileOutput.class)
    public JAXBElement<String> createFileOutputNomeFile(String value) {
        return new JAXBElement<String>(_FileOutputNomeFile_QNAME, String.class, FileOutput.class, value);
    }
}
