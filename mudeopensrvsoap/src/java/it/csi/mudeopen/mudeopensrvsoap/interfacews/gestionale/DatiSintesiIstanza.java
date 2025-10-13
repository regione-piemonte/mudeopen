/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for datiSintesiIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="datiSintesiIstanza"&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="speciePratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoIntestatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="professionista" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sedime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="bis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="interno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="statoIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="istanzaDiRiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroIntervento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="xmlUnicoPresente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiSintesiIstanza", propOrder = {
    "numeroIstanza",
    "tipoIstanza",
    "speciePratica",
    "tipoIntestatario",
    "cognome",
    "nome",
    "ragioneSociale",
    "professionista",
    "sedime",
    "descrizione",
    "numeroCivico",
    "bis",
    "interno",
    "comune",
    "statoIstanza",
    "istanzaDiRiferimento",
    "numeroIntervento",
    "xmlUnicoPresente"
})
public class DatiSintesiIstanza {
    @XmlElement(required = true)
    protected String numeroIstanza;
    @XmlElement(required = true)
    protected String tipoIstanza;
    protected String speciePratica;
    protected String tipoIntestatario;
    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    protected String ragioneSociale;
    @XmlElement(required = true)
    protected String professionista;
    protected String sedime;
    @XmlElement(required = true)
    protected String descrizione;
    protected String numeroCivico;
    protected String bis;
    protected String interno;
    @XmlElement(required = true)
    protected String comune;
    @XmlElement(required = true)
    protected String statoIstanza;
    protected String istanzaDiRiferimento;
    @XmlElement(required = true)
    protected String numeroIntervento;
    protected Boolean xmlUnicoPresente;
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
     * Gets the value of the cognome property.
     * 
     *     
     */
    public String getCognome() {
        return cognome;
    }
    /**
     * Sets the value of the cognome property.
     * 
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
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
     * Gets the value of the ragioneSociale property.
     * 
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    /**
     * Sets the value of the ragioneSociale property.
     * 
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
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
     * Gets the value of the descrizione property.
     * 
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }
    /**
     * Sets the value of the descrizione property.
     * 
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
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
     * Gets the value of the istanzaDiRiferimento property.
     * 
     *     
     */
    public String getIstanzaDiRiferimento() {
        return istanzaDiRiferimento;
    }
    /**
     * Sets the value of the istanzaDiRiferimento property.
     * 
     *     
     */
    public void setIstanzaDiRiferimento(String value) {
        this.istanzaDiRiferimento = value;
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
