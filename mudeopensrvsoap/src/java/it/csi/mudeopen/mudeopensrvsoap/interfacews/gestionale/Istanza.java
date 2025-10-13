/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * <p>Java class for istanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="istanza"&gt;
 *         &lt;element name="codModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroMude" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="speciePratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoIntestatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="intestatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sedime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="via" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="bis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="interno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="statoIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="professionista" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identificativoPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroIntervento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="xmlUnicoPresente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "istanza", propOrder = {
    "codModello",
    "numeroMude",
    "tipoIstanza",
    "speciePratica",
    "tipoIntestatario",
    "intestatario",
    "nome",
    "sedime",
    "via",
    "numeroCivico",
    "bis",
    "interno",
    "dataRicezione",
    "statoIstanza",
    "comune",
    "professionista",
    "identificativoPratica",
    "numeroIntervento",
    "xmlUnicoPresente"
})
public class Istanza {
    protected String codModello;
    @XmlElement(required = true)
    protected String numeroMude;
    @XmlElement(required = true)
    protected String tipoIstanza;
    protected String speciePratica;
    protected String tipoIntestatario;
    protected String intestatario;
    protected String nome;
    protected String sedime;
    protected String via;
    protected String numeroCivico;
    protected String bis;
    protected String interno;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRicezione;
    @XmlElement(required = true)
    protected String statoIstanza;
    protected String comune;
    protected String professionista;
    protected String identificativoPratica;
    @XmlElement(required = true)
    protected String numeroIntervento;
    protected Boolean xmlUnicoPresente;
    /**
     * Gets the value of the codModello property.
     * 
     *     
     */
    public String getCodModello() {
        return codModello;
    }
    /**
     * Sets the value of the codModello property.
     * 
     *     
     */
    public void setCodModello(String value) {
        this.codModello = value;
    }
    /**
     * Gets the value of the numeroMude property.
     * 
     *     
     */
    public String getNumeroMude() {
        return numeroMude;
    }
    /**
     * Sets the value of the numeroMude property.
     * 
     *     
     */
    public void setNumeroMude(String value) {
        this.numeroMude = value;
    }
    /**
     * Gets the value of the tipoIstanza property.
     * 
     *     
     */
    public String getTipoIstanza() {
        return tipoIstanza;
    }
    /**
     * Sets the value of the tipoIstanza property.
     * 
     *     
     */
    public void setTipoIstanza(String value) {
        this.tipoIstanza = value;
    }
    /**
     * Gets the value of the speciePratica property.
     * 
     *     
     */
    public String getSpeciePratica() {
        return speciePratica;
    }
    /**
     * Sets the value of the speciePratica property.
     * 
     *     
     */
    public void setSpeciePratica(String value) {
        this.speciePratica = value;
    }
    /**
     * Gets the value of the tipoIntestatario property.
     * 
     *     
     */
    public String getTipoIntestatario() {
        return tipoIntestatario;
    }
    /**
     * Sets the value of the tipoIntestatario property.
     * 
     *     
     */
    public void setTipoIntestatario(String value) {
        this.tipoIntestatario = value;
    }
    /**
     * Gets the value of the intestatario property.
     * 
     *     
     */
    public String getIntestatario() {
        return intestatario;
    }
    /**
     * Sets the value of the intestatario property.
     * 
     *     
     */
    public void setIntestatario(String value) {
        this.intestatario = value;
    }
    /**
     * Gets the value of the nome property.
     * 
     *     
     */
    public String getNome() {
        return nome;
    }
    /**
     * Sets the value of the nome property.
     * 
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }
    /**
     * Gets the value of the sedime property.
     * 
     *     
     */
    public String getSedime() {
        return sedime;
    }
    /**
     * Sets the value of the sedime property.
     * 
     *     
     */
    public void setSedime(String value) {
        this.sedime = value;
    }
    /**
     * Gets the value of the via property.
     * 
     *     
     */
    public String getVia() {
        return via;
    }
    /**
     * Sets the value of the via property.
     * 
     *     
     */
    public void setVia(String value) {
        this.via = value;
    }
    /**
     * Gets the value of the numeroCivico property.
     * 
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }
    /**
     * Sets the value of the numeroCivico property.
     * 
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }
    /**
     * Gets the value of the bis property.
     * 
     *     
     */
    public String getBis() {
        return bis;
    }
    /**
     * Sets the value of the bis property.
     * 
     *     
     */
    public void setBis(String value) {
        this.bis = value;
    }
    /**
     * Gets the value of the interno property.
     * 
     *     
     */
    public String getInterno() {
        return interno;
    }
    /**
     * Sets the value of the interno property.
     * 
     *     
     */
    public void setInterno(String value) {
        this.interno = value;
    }
    /**
     * Gets the value of the dataRicezione property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRicezione() {
        return dataRicezione;
    }
    /**
     * Sets the value of the dataRicezione property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRicezione(XMLGregorianCalendar value) {
        this.dataRicezione = value;
    }
    /**
     * Gets the value of the statoIstanza property.
     * 
     *     
     */
    public String getStatoIstanza() {
        return statoIstanza;
    }
    /**
     * Sets the value of the statoIstanza property.
     * 
     *     
     */
    public void setStatoIstanza(String value) {
        this.statoIstanza = value;
    }
    /**
     * Gets the value of the comune property.
     * 
     *     
     */
    public String getComune() {
        return comune;
    }
    /**
     * Sets the value of the comune property.
     * 
     *     
     */
    public void setComune(String value) {
        this.comune = value;
    }
    /**
     * Gets the value of the professionista property.
     * 
     *     
     */
    public String getProfessionista() {
        return professionista;
    }
    /**
     * Sets the value of the professionista property.
     * 
     *     
     */
    public void setProfessionista(String value) {
        this.professionista = value;
    }
    /**
     * Gets the value of the identificativoPratica property.
     * 
     *     
     */
    public String getIdentificativoPratica() {
        return identificativoPratica;
    }
    /**
     * Sets the value of the identificativoPratica property.
     * 
     *     
     */
    public void setIdentificativoPratica(String value) {
        this.identificativoPratica = value;
    }
    /**
     * Gets the value of the numeroIntervento property.
     * 
     *     
     */
    public String getNumeroIntervento() {
        return numeroIntervento;
    }
    /**
     * Sets the value of the numeroIntervento property.
     * 
     *     
     */
    public void setNumeroIntervento(String value) {
        this.numeroIntervento = value;
    }
    /**
     * Gets the value of the xmlUnicoPresente property.
     * 
     *     {@link Boolean }
     *     
     */
    public Boolean isXmlUnicoPresente() {
        return xmlUnicoPresente;
    }
    /**
     * Sets the value of the xmlUnicoPresente property.
     * 
     *     {@link Boolean }
     *     
     */
    public void setXmlUnicoPresente(Boolean value) {
        this.xmlUnicoPresente = value;
    }
}
