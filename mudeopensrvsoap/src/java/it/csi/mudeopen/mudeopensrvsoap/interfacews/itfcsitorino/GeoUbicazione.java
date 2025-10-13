/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for geoUbicazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="geoUbicazione"&gt;
 *         &lt;element name="bis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="bisinterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descFonteUbicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="flPersonalizzato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="flPrincipale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idCivicoTopon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="idViaTopon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="interno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="interno2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="piano" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="scala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="secondario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sedime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geoUbicazione", propOrder = {
    "bis",
    "bisinterno",
    "cap",
    "descFonteUbicazione",
    "descVia",
    "flPersonalizzato",
    "flPrincipale",
    "idCivicoTopon",
    "idViaTopon",
    "interno",
    "interno2",
    "numCivico",
    "piano",
    "scala",
    "secondario",
    "sedime"
})
public class GeoUbicazione {
    protected String bis;
    protected String bisinterno;
    protected String cap;
    protected String descFonteUbicazione;
    protected String descVia;
    protected String flPersonalizzato;
    protected String flPrincipale;
    protected Integer idCivicoTopon;
    protected Integer idViaTopon;
    protected String interno;
    protected String interno2;
    protected String numCivico;
    protected String piano;
    protected String scala;
    protected String secondario;
    protected String sedime;
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
     * Gets the value of the bisinterno property.
     * 
     *     
     */
    public String getBisinterno() {
        return bisinterno;
    }
    /**
     * Sets the value of the bisinterno property.
     * 
     *     
     */
    public void setBisinterno(String value) {
        this.bisinterno = value;
    }
    /**
     * Gets the value of the cap property.
     * 
     *     
     */
    public String getCap() {
        return cap;
    }
    /**
     * Sets the value of the cap property.
     * 
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }
    /**
     * Gets the value of the descFonteUbicazione property.
     * 
     *     
     */
    public String getDescFonteUbicazione() {
        return descFonteUbicazione;
    }
    /**
     * Sets the value of the descFonteUbicazione property.
     * 
     *     
     */
    public void setDescFonteUbicazione(String value) {
        this.descFonteUbicazione = value;
    }
    /**
     * Gets the value of the descVia property.
     * 
     *     
     */
    public String getDescVia() {
        return descVia;
    }
    /**
     * Sets the value of the descVia property.
     * 
     *     
     */
    public void setDescVia(String value) {
        this.descVia = value;
    }
    /**
     * Gets the value of the flPersonalizzato property.
     * 
     *     
     */
    public String getFlPersonalizzato() {
        return flPersonalizzato;
    }
    /**
     * Sets the value of the flPersonalizzato property.
     * 
     *     
     */
    public void setFlPersonalizzato(String value) {
        this.flPersonalizzato = value;
    }
    /**
     * Gets the value of the flPrincipale property.
     * 
     *     
     */
    public String getFlPrincipale() {
        return flPrincipale;
    }
    /**
     * Sets the value of the flPrincipale property.
     * 
     *     
     */
    public void setFlPrincipale(String value) {
        this.flPrincipale = value;
    }
    /**
     * Gets the value of the idCivicoTopon property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getIdCivicoTopon() {
        return idCivicoTopon;
    }
    /**
     * Sets the value of the idCivicoTopon property.
     * 
     *     {@link Integer }
     *     
     */
    public void setIdCivicoTopon(Integer value) {
        this.idCivicoTopon = value;
    }
    /**
     * Gets the value of the idViaTopon property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getIdViaTopon() {
        return idViaTopon;
    }
    /**
     * Sets the value of the idViaTopon property.
     * 
     *     {@link Integer }
     *     
     */
    public void setIdViaTopon(Integer value) {
        this.idViaTopon = value;
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
     * Gets the value of the interno2 property.
     * 
     *     
     */
    public String getInterno2() {
        return interno2;
    }
    /**
     * Sets the value of the interno2 property.
     * 
     *     
     */
    public void setInterno2(String value) {
        this.interno2 = value;
    }
    /**
     * Gets the value of the numCivico property.
     * 
     *     
     */
    public String getNumCivico() {
        return numCivico;
    }
    /**
     * Sets the value of the numCivico property.
     * 
     *     
     */
    public void setNumCivico(String value) {
        this.numCivico = value;
    }
    /**
     * Gets the value of the piano property.
     * 
     *     
     */
    public String getPiano() {
        return piano;
    }
    /**
     * Sets the value of the piano property.
     * 
     *     
     */
    public void setPiano(String value) {
        this.piano = value;
    }
    /**
     * Gets the value of the scala property.
     * 
     *     
     */
    public String getScala() {
        return scala;
    }
    /**
     * Sets the value of the scala property.
     * 
     *     
     */
    public void setScala(String value) {
        this.scala = value;
    }
    /**
     * Gets the value of the secondario property.
     * 
     *     
     */
    public String getSecondario() {
        return secondario;
    }
    /**
     * Sets the value of the secondario property.
     * 
     *     
     */
    public void setSecondario(String value) {
        this.secondario = value;
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
}
