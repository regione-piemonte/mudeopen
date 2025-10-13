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
 * <p>Java class for datiModificaStatoIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="datiModificaStatoIstanza"&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nuovoStato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroPraticaComunale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="annoPratica" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="responsabileProcedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiModificaStatoIstanza", propOrder = {
    "numeroIstanza",
    "nuovoStato",
    "oggetto",
    "testo",
    "numeroPraticaComunale",
    "annoPratica",
    "numeroProtocollo",
    "dataProtocollo",
    "responsabileProcedimento"
})
public class DatiModificaStatoIstanza {
    @XmlElement(required = true)
    protected String numeroIstanza;
    @XmlElement(required = true)
    protected String nuovoStato;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected String testo;
    protected String numeroPraticaComunale;
    protected Integer annoPratica;
    protected String numeroProtocollo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataProtocollo;
    protected String responsabileProcedimento;
    /**
     * Gets the value of the numeroIstanza property.
     * 
     *     
     */
    public String getNumeroIstanza() {
        return numeroIstanza;
    }
    /**
     * Sets the value of the numeroIstanza property.
     * 
     *     
     */
    public void setNumeroIstanza(String value) {
        this.numeroIstanza = value;
    }
    /**
     * Gets the value of the nuovoStato property.
     * 
     *     
     */
    public String getNuovoStato() {
        return nuovoStato;
    }
    /**
     * Sets the value of the nuovoStato property.
     * 
     *     
     */
    public void setNuovoStato(String value) {
        this.nuovoStato = value;
    }
    /**
     * Gets the value of the oggetto property.
     * 
     *     
     */
    public String getOggetto() {
        return oggetto;
    }
    /**
     * Sets the value of the oggetto property.
     * 
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }
    /**
     * Gets the value of the testo property.
     * 
     *     
     */
    public String getTesto() {
        return testo;
    }
    /**
     * Sets the value of the testo property.
     * 
     *     
     */
    public void setTesto(String value) {
        this.testo = value;
    }
    /**
     * Gets the value of the numeroPraticaComunale property.
     * 
     *     
     */
    public String getNumeroPraticaComunale() {
        return numeroPraticaComunale;
    }
    /**
     * Sets the value of the numeroPraticaComunale property.
     * 
     *     
     */
    public void setNumeroPraticaComunale(String value) {
        this.numeroPraticaComunale = value;
    }
    /**
     * Gets the value of the annoPratica property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getAnnoPratica() {
        return annoPratica;
    }
    /**
     * Sets the value of the annoPratica property.
     * 
     *     {@link Integer }
     *     
     */
    public void setAnnoPratica(Integer value) {
        this.annoPratica = value;
    }
    /**
     * Gets the value of the numeroProtocollo property.
     * 
     *     
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }
    /**
     * Sets the value of the numeroProtocollo property.
     * 
     *     
     */
    public void setNumeroProtocollo(String value) {
        this.numeroProtocollo = value;
    }
    /**
     * Gets the value of the dataProtocollo property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocollo() {
        return dataProtocollo;
    }
    /**
     * Sets the value of the dataProtocollo property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocollo(XMLGregorianCalendar value) {
        this.dataProtocollo = value;
    }
    /**
     * Gets the value of the responsabileProcedimento property.
     * 
     *     
     */
    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }
    /**
     * Sets the value of the responsabileProcedimento property.
     * 
     *     
     */
    public void setResponsabileProcedimento(String value) {
        this.responsabileProcedimento = value;
    }
}
