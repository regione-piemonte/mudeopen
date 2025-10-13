/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaIstanza"&gt;
 *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroFascicoloIntervento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="annoIntervento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="annoIstanza" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="numeroPraticaComunale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="annoPraticaComunale" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="statiIstanza" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *         &lt;element name="tipologieIstanza" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="intestatarioIstanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoIntestatarioIstanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaIstanza", propOrder = {
    "comune",
    "numeroFascicoloIntervento",
    "annoIntervento",
    "annoIstanza",
    "numeroPraticaComunale",
    "annoPraticaComunale",
    "statiIstanza",
    "tipologieIstanza",
    "intestatarioIstanza",
    "tipoIntestatarioIstanza",
    "indirizzo"
})
public class RicercaIstanza {
    @XmlElement(required = true)
    protected String comune;
    protected String numeroFascicoloIntervento;
    protected Integer annoIntervento;
    protected Integer annoIstanza;
    protected String numeroPraticaComunale;
    protected Integer annoPraticaComunale;
    @XmlElement(required = true)
    protected List<String> statiIstanza;
    @XmlElement(nillable = true)
    protected List<String> tipologieIstanza;
    protected String intestatarioIstanza;
    protected String tipoIntestatarioIstanza;
    protected String indirizzo;
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
     * Gets the value of the numeroFascicoloIntervento property.
     * 
     *     
     */
    public String getNumeroFascicoloIntervento() {
        return numeroFascicoloIntervento;
    }
    /**
     * Sets the value of the numeroFascicoloIntervento property.
     * 
     *     
     */
    public void setNumeroFascicoloIntervento(String value) {
        this.numeroFascicoloIntervento = value;
    }
    /**
     * Gets the value of the annoIntervento property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getAnnoIntervento() {
        return annoIntervento;
    }
    /**
     * Sets the value of the annoIntervento property.
     * 
     *     {@link Integer }
     *     
     */
    public void setAnnoIntervento(Integer value) {
        this.annoIntervento = value;
    }
    /**
     * Gets the value of the annoIstanza property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getAnnoIstanza() {
        return annoIstanza;
    }
    /**
     * Sets the value of the annoIstanza property.
     * 
     *     {@link Integer }
     *     
     */
    public void setAnnoIstanza(Integer value) {
        this.annoIstanza = value;
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
     * Gets the value of the annoPraticaComunale property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getAnnoPraticaComunale() {
        return annoPraticaComunale;
    }
    /**
     * Sets the value of the annoPraticaComunale property.
     * 
     *     {@link Integer }
     *     
     */
    public void setAnnoPraticaComunale(Integer value) {
        this.annoPraticaComunale = value;
    }
    /**
     * Gets the value of the statiIstanza property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statiIstanza property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getStatiIstanza().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStatiIstanza() {
        if (statiIstanza == null) {
            statiIstanza = new ArrayList<String>();
        }
        return this.statiIstanza;
    }
    /**
     * Gets the value of the tipologieIstanza property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipologieIstanza property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getTipologieIstanza().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTipologieIstanza() {
        if (tipologieIstanza == null) {
            tipologieIstanza = new ArrayList<String>();
        }
        return this.tipologieIstanza;
    }
    /**
     * Gets the value of the intestatarioIstanza property.
     * 
     *     
     */
    public String getIntestatarioIstanza() {
        return intestatarioIstanza;
    }
    /**
     * Sets the value of the intestatarioIstanza property.
     * 
     *     
     */
    public void setIntestatarioIstanza(String value) {
        this.intestatarioIstanza = value;
    }
    /**
     * Gets the value of the tipoIntestatarioIstanza property.
     * 
     *     
     */
    public String getTipoIntestatarioIstanza() {
        return tipoIntestatarioIstanza;
    }
    /**
     * Sets the value of the tipoIntestatarioIstanza property.
     * 
     *     
     */
    public void setTipoIntestatarioIstanza(String value) {
        this.tipoIntestatarioIstanza = value;
    }
    /**
     * Gets the value of the indirizzo property.
     * 
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    /**
     * Sets the value of the indirizzo property.
     * 
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }
}
