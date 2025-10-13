/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for istanzaExt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="istanzaExt"&gt;
 *         &lt;element name="codIstatComune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipologiaIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroFascicoloIntervento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="speciePratica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="annoIntervento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="annoIstanza" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tipoIntestatario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="intestatarioCognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="intestatarioNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="intestatarioRagioneSociale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="professionistaCognome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="professionistaNome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ubicazioneSedime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ubicazioneDescVia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ubicazioneNumeroCivico" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ubicazioneBis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ubicazioneInterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="operePrecarie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroMudeIstanzaPrincipale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="compilanteCodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tracciatiXML" type="{http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/}traccXML" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="timestampInserimento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "istanzaExt", propOrder = {
    "codIstatComune",
    "tipologiaIstanza",
    "numeroIstanza",
    "numeroFascicoloIntervento",
    "speciePratica",
    "annoIntervento",
    "annoIstanza",
    "tipoIntestatario",
    "intestatarioCognome",
    "intestatarioNome",
    "intestatarioRagioneSociale",
    "professionistaCognome",
    "professionistaNome",
    "ubicazioneSedime",
    "ubicazioneDescVia",
    "ubicazioneNumeroCivico",
    "ubicazioneBis",
    "ubicazioneInterno",
    "operePrecarie",
    "numeroMudeIstanzaPrincipale",
    "compilanteCodiceFiscale",
    "tracciatiXML",
    "timestampInserimento"
})
public class IstanzaExt {
    @XmlElement(required = true)
    protected String codIstatComune;
    @XmlElement(required = true)
    protected String tipologiaIstanza;
    @XmlElement(required = true)
    protected String numeroIstanza;
    @XmlElement(required = true)
    protected String numeroFascicoloIntervento;
    @XmlElement(required = true)
    protected String speciePratica;
    protected int annoIntervento;
    protected int annoIstanza;
    @XmlElement(required = true)
    protected String tipoIntestatario;
    protected String intestatarioCognome;
    protected String intestatarioNome;
    protected String intestatarioRagioneSociale;
    @XmlElement(required = true)
    protected String professionistaCognome;
    @XmlElement(required = true)
    protected String professionistaNome;
    @XmlElement(required = true)
    protected String ubicazioneSedime;
    @XmlElement(required = true)
    protected String ubicazioneDescVia;
    protected int ubicazioneNumeroCivico;
    protected String ubicazioneBis;
    protected String ubicazioneInterno;
    protected String operePrecarie;
    protected String numeroMudeIstanzaPrincipale;
    @XmlElement(required = true)
    protected String compilanteCodiceFiscale;
    @XmlElement(nillable = true)
    protected List<TraccXML> tracciatiXML;
    @XmlElement(required = true)
    protected String timestampInserimento;
    /**
     * Gets the value of the codIstatComune property.
     * 
     *     
     */
    public String getCodIstatComune() {
        return codIstatComune;
    }
    /**
     * Sets the value of the codIstatComune property.
     * 
     *     
     */
    public void setCodIstatComune(String value) {
        this.codIstatComune = value;
    }
    /**
     * Gets the value of the tipologiaIstanza property.
     * 
     *     
     */
    public String getTipologiaIstanza() {
        return tipologiaIstanza;
    }
    /**
     * Sets the value of the tipologiaIstanza property.
     * 
     *     
     */
    public void setTipologiaIstanza(String value) {
        this.tipologiaIstanza = value;
    }
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
     * Gets the value of the annoIntervento property.
     * 
     */
    public int getAnnoIntervento() {
        return annoIntervento;
    }
    /**
     * Sets the value of the annoIntervento property.
     * 
     */
    public void setAnnoIntervento(int value) {
        this.annoIntervento = value;
    }
    /**
     * Gets the value of the annoIstanza property.
     * 
     */
    public int getAnnoIstanza() {
        return annoIstanza;
    }
    /**
     * Sets the value of the annoIstanza property.
     * 
     */
    public void setAnnoIstanza(int value) {
        this.annoIstanza = value;
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
     * Gets the value of the intestatarioCognome property.
     * 
     *     
     */
    public String getIntestatarioCognome() {
        return intestatarioCognome;
    }
    /**
     * Sets the value of the intestatarioCognome property.
     * 
     *     
     */
    public void setIntestatarioCognome(String value) {
        this.intestatarioCognome = value;
    }
    /**
     * Gets the value of the intestatarioNome property.
     * 
     *     
     */
    public String getIntestatarioNome() {
        return intestatarioNome;
    }
    /**
     * Sets the value of the intestatarioNome property.
     * 
     *     
     */
    public void setIntestatarioNome(String value) {
        this.intestatarioNome = value;
    }
    /**
     * Gets the value of the intestatarioRagioneSociale property.
     * 
     *     
     */
    public String getIntestatarioRagioneSociale() {
        return intestatarioRagioneSociale;
    }
    /**
     * Sets the value of the intestatarioRagioneSociale property.
     * 
     *     
     */
    public void setIntestatarioRagioneSociale(String value) {
        this.intestatarioRagioneSociale = value;
    }
    /**
     * Gets the value of the professionistaCognome property.
     * 
     *     
     */
    public String getProfessionistaCognome() {
        return professionistaCognome;
    }
    /**
     * Sets the value of the professionistaCognome property.
     * 
     *     
     */
    public void setProfessionistaCognome(String value) {
        this.professionistaCognome = value;
    }
    /**
     * Gets the value of the professionistaNome property.
     * 
     *     
     */
    public String getProfessionistaNome() {
        return professionistaNome;
    }
    /**
     * Sets the value of the professionistaNome property.
     * 
     *     
     */
    public void setProfessionistaNome(String value) {
        this.professionistaNome = value;
    }
    /**
     * Gets the value of the ubicazioneSedime property.
     * 
     *     
     */
    public String getUbicazioneSedime() {
        return ubicazioneSedime;
    }
    /**
     * Sets the value of the ubicazioneSedime property.
     * 
     *     
     */
    public void setUbicazioneSedime(String value) {
        this.ubicazioneSedime = value;
    }
    /**
     * Gets the value of the ubicazioneDescVia property.
     * 
     *     
     */
    public String getUbicazioneDescVia() {
        return ubicazioneDescVia;
    }
    /**
     * Sets the value of the ubicazioneDescVia property.
     * 
     *     
     */
    public void setUbicazioneDescVia(String value) {
        this.ubicazioneDescVia = value;
    }
    /**
     * Gets the value of the ubicazioneNumeroCivico property.
     * 
     */
    public int getUbicazioneNumeroCivico() {
        return ubicazioneNumeroCivico;
    }
    /**
     * Sets the value of the ubicazioneNumeroCivico property.
     * 
     */
    public void setUbicazioneNumeroCivico(int value) {
        this.ubicazioneNumeroCivico = value;
    }
    /**
     * Gets the value of the ubicazioneBis property.
     * 
     *     
     */
    public String getUbicazioneBis() {
        return ubicazioneBis;
    }
    /**
     * Sets the value of the ubicazioneBis property.
     * 
     *     
     */
    public void setUbicazioneBis(String value) {
        this.ubicazioneBis = value;
    }
    /**
     * Gets the value of the ubicazioneInterno property.
     * 
     *     
     */
    public String getUbicazioneInterno() {
        return ubicazioneInterno;
    }
    /**
     * Sets the value of the ubicazioneInterno property.
     * 
     *     
     */
    public void setUbicazioneInterno(String value) {
        this.ubicazioneInterno = value;
    }
    /**
     * Gets the value of the operePrecarie property.
     * 
     *     
     */
    public String getOperePrecarie() {
        return operePrecarie;
    }
    /**
     * Sets the value of the operePrecarie property.
     * 
     *     
     */
    public void setOperePrecarie(String value) {
        this.operePrecarie = value;
    }
    /**
     * Gets the value of the numeroMudeIstanzaPrincipale property.
     * 
     *     
     */
    public String getNumeroMudeIstanzaPrincipale() {
        return numeroMudeIstanzaPrincipale;
    }
    /**
     * Sets the value of the numeroMudeIstanzaPrincipale property.
     * 
     *     
     */
    public void setNumeroMudeIstanzaPrincipale(String value) {
        this.numeroMudeIstanzaPrincipale = value;
    }
    /**
     * Gets the value of the compilanteCodiceFiscale property.
     * 
     *     
     */
    public String getCompilanteCodiceFiscale() {
        return compilanteCodiceFiscale;
    }
    /**
     * Sets the value of the compilanteCodiceFiscale property.
     * 
     *     
     */
    public void setCompilanteCodiceFiscale(String value) {
        this.compilanteCodiceFiscale = value;
    }
    /**
     * Gets the value of the tracciatiXML property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tracciatiXML property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getTracciatiXML().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TraccXML }
     * 
     * 
     */
    public List<TraccXML> getTracciatiXML() {
        if (tracciatiXML == null) {
            tracciatiXML = new ArrayList<TraccXML>();
        }
        return this.tracciatiXML;
    }
    /**
     * Gets the value of the timestampInserimento property.
     * 
     *     
     */
    public String getTimestampInserimento() {
        return timestampInserimento;
    }
    /**
     * Sets the value of the timestampInserimento property.
     * 
     *     
     */
    public void setTimestampInserimento(String value) {
        this.timestampInserimento = value;
    }
}
