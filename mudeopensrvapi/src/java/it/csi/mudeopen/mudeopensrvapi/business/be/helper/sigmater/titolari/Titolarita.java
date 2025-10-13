/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per Titolarita complex type.
 * 
 * 
 * &lt;complexType name="Titolarita"&gt;
 *         &lt;element name="annoNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codComuneBelfioreTitolarita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codDiritto1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codDiritto2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codRegime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataEfficacia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrDiritto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrRegime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="infoSupplementariCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="partita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="progNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="quotaDenominatore" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="quotaNumeratore" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="soggettoFisico" type="{urn:sigalfsrvTitolari}SoggettoFisico"/&gt;
 *         &lt;element name="soggettoGiuridico" type="{urn:sigalfsrvTitolari}SoggettoGiuridico"/&gt;
 *         &lt;element name="soggettoRiferimentoRegime" type="{urn:sigalfsrvTitolari}IdentitaPersonaFisica"/&gt;
 *         &lt;element name="tipoNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="titolaritaNonCodificata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Titolarita", propOrder = {
    "annoNota",
    "codCausale",
    "codComuneBelfioreTitolarita",
    "codDiritto1",
    "codDiritto2",
    "codRegime",
    "dataAggiornamento",
    "dataEfficacia",
    "dataRegistrazione",
    "descrCausale",
    "descrDiritto",
    "descrNota",
    "descrRegime",
    "infoSupplementariCausale",
    "numeroNota",
    "partita",
    "progNota",
    "quotaDenominatore",
    "quotaNumeratore",
    "sezione",
    "soggettoFisico",
    "soggettoGiuridico",
    "soggettoRiferimentoRegime",
    "tipoNota",
    "titolaritaNonCodificata"
})
public class Titolarita {

    @XmlElement(required = true, nillable = true)
    protected String annoNota;
    @XmlElement(required = true, nillable = true)
    protected String codCausale;
    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfioreTitolarita;
    @XmlElement(required = true, nillable = true)
    protected String codDiritto1;
    @XmlElement(required = true, nillable = true)
    protected String codDiritto2;
    @XmlElement(required = true, nillable = true)
    protected String codRegime;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String dataEfficacia;
    @XmlElement(required = true, nillable = true)
    protected String dataRegistrazione;
    @XmlElement(required = true, nillable = true)
    protected String descrCausale;
    @XmlElement(required = true, nillable = true)
    protected String descrDiritto;
    @XmlElement(required = true, nillable = true)
    protected String descrNota;
    @XmlElement(required = true, nillable = true)
    protected String descrRegime;
    @XmlElement(required = true, nillable = true)
    protected String infoSupplementariCausale;
    @XmlElement(required = true, nillable = true)
    protected String numeroNota;
    @XmlElement(required = true, nillable = true)
    protected String partita;
    @XmlElement(required = true, nillable = true)
    protected String progNota;
    protected long quotaDenominatore;
    protected long quotaNumeratore;
    @XmlElement(required = true, nillable = true)
    protected String sezione;
    @XmlElement(required = true, nillable = true)
    protected SoggettoFisico soggettoFisico;
    @XmlElement(required = true, nillable = true)
    protected SoggettoGiuridico soggettoGiuridico;
    @XmlElement(required = true, nillable = true)
    protected IdentitaPersonaFisica soggettoRiferimentoRegime;
    @XmlElement(required = true, nillable = true)
    protected String tipoNota;
    @XmlElement(required = true, nillable = true)
    protected String titolaritaNonCodificata;

    /**
     * Recupera il valore della proprieta annoNota.
     * 
     *     
     */
    public String getAnnoNota() {
        return annoNota;
    }

    /**
     * Imposta il valore della proprieta annoNota.
     * 
     *     
     */
    public void setAnnoNota(String value) {
        this.annoNota = value;
    }

    /**
     * Recupera il valore della proprieta codCausale.
     * 
     *     
     */
    public String getCodCausale() {
        return codCausale;
    }

    /**
     * Imposta il valore della proprieta codCausale.
     * 
     *     
     */
    public void setCodCausale(String value) {
        this.codCausale = value;
    }

    /**
     * Recupera il valore della proprieta codComuneBelfioreTitolarita.
     * 
     *     
     */
    public String getCodComuneBelfioreTitolarita() {
        return codComuneBelfioreTitolarita;
    }

    /**
     * Imposta il valore della proprieta codComuneBelfioreTitolarita.
     * 
     *     
     */
    public void setCodComuneBelfioreTitolarita(String value) {
        this.codComuneBelfioreTitolarita = value;
    }

    /**
     * Recupera il valore della proprieta codDiritto1.
     * 
     *     
     */
    public String getCodDiritto1() {
        return codDiritto1;
    }

    /**
     * Imposta il valore della proprieta codDiritto1.
     * 
     *     
     */
    public void setCodDiritto1(String value) {
        this.codDiritto1 = value;
    }

    /**
     * Recupera il valore della proprieta codDiritto2.
     * 
     *     
     */
    public String getCodDiritto2() {
        return codDiritto2;
    }

    /**
     * Imposta il valore della proprieta codDiritto2.
     * 
     *     
     */
    public void setCodDiritto2(String value) {
        this.codDiritto2 = value;
    }

    /**
     * Recupera il valore della proprieta codRegime.
     * 
     *     
     */
    public String getCodRegime() {
        return codRegime;
    }

    /**
     * Imposta il valore della proprieta codRegime.
     * 
     *     
     */
    public void setCodRegime(String value) {
        this.codRegime = value;
    }

    /**
     * Recupera il valore della proprieta dataAggiornamento.
     * 
     *     
     */
    public String getDataAggiornamento() {
        return dataAggiornamento;
    }

    /**
     * Imposta il valore della proprieta dataAggiornamento.
     * 
     *     
     */
    public void setDataAggiornamento(String value) {
        this.dataAggiornamento = value;
    }

    /**
     * Recupera il valore della proprieta dataEfficacia.
     * 
     *     
     */
    public String getDataEfficacia() {
        return dataEfficacia;
    }

    /**
     * Imposta il valore della proprieta dataEfficacia.
     * 
     *     
     */
    public void setDataEfficacia(String value) {
        this.dataEfficacia = value;
    }

    /**
     * Recupera il valore della proprieta dataRegistrazione.
     * 
     *     
     */
    public String getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Imposta il valore della proprieta dataRegistrazione.
     * 
     *     
     */
    public void setDataRegistrazione(String value) {
        this.dataRegistrazione = value;
    }

    /**
     * Recupera il valore della proprieta descrCausale.
     * 
     *     
     */
    public String getDescrCausale() {
        return descrCausale;
    }

    /**
     * Imposta il valore della proprieta descrCausale.
     * 
     *     
     */
    public void setDescrCausale(String value) {
        this.descrCausale = value;
    }

    /**
     * Recupera il valore della proprieta descrDiritto.
     * 
     *     
     */
    public String getDescrDiritto() {
        return descrDiritto;
    }

    /**
     * Imposta il valore della proprieta descrDiritto.
     * 
     *     
     */
    public void setDescrDiritto(String value) {
        this.descrDiritto = value;
    }

    /**
     * Recupera il valore della proprieta descrNota.
     * 
     *     
     */
    public String getDescrNota() {
        return descrNota;
    }

    /**
     * Imposta il valore della proprieta descrNota.
     * 
     *     
     */
    public void setDescrNota(String value) {
        this.descrNota = value;
    }

    /**
     * Recupera il valore della proprieta descrRegime.
     * 
     *     
     */
    public String getDescrRegime() {
        return descrRegime;
    }

    /**
     * Imposta il valore della proprieta descrRegime.
     * 
     *     
     */
    public void setDescrRegime(String value) {
        this.descrRegime = value;
    }

    /**
     * Recupera il valore della proprieta infoSupplementariCausale.
     * 
     *     
     */
    public String getInfoSupplementariCausale() {
        return infoSupplementariCausale;
    }

    /**
     * Imposta il valore della proprieta infoSupplementariCausale.
     * 
     *     
     */
    public void setInfoSupplementariCausale(String value) {
        this.infoSupplementariCausale = value;
    }

    /**
     * Recupera il valore della proprieta numeroNota.
     * 
     *     
     */
    public String getNumeroNota() {
        return numeroNota;
    }

    /**
     * Imposta il valore della proprieta numeroNota.
     * 
     *     
     */
    public void setNumeroNota(String value) {
        this.numeroNota = value;
    }

    /**
     * Recupera il valore della proprieta partita.
     * 
     *     
     */
    public String getPartita() {
        return partita;
    }

    /**
     * Imposta il valore della proprieta partita.
     * 
     *     
     */
    public void setPartita(String value) {
        this.partita = value;
    }

    /**
     * Recupera il valore della proprieta progNota.
     * 
     *     
     */
    public String getProgNota() {
        return progNota;
    }

    /**
     * Imposta il valore della proprieta progNota.
     * 
     *     
     */
    public void setProgNota(String value) {
        this.progNota = value;
    }

    /**
     * Recupera il valore della proprieta quotaDenominatore.
     * 
     */
    public long getQuotaDenominatore() {
        return quotaDenominatore;
    }

    /**
     * Imposta il valore della proprieta quotaDenominatore.
     * 
     */
    public void setQuotaDenominatore(long value) {
        this.quotaDenominatore = value;
    }

    /**
     * Recupera il valore della proprieta quotaNumeratore.
     * 
     */
    public long getQuotaNumeratore() {
        return quotaNumeratore;
    }

    /**
     * Imposta il valore della proprieta quotaNumeratore.
     * 
     */
    public void setQuotaNumeratore(long value) {
        this.quotaNumeratore = value;
    }

    /**
     * Recupera il valore della proprieta sezione.
     * 
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Imposta il valore della proprieta sezione.
     * 
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

    /**
     * Recupera il valore della proprieta soggettoFisico.
     * 
     *     {@link SoggettoFisico }
     *     
     */
    public SoggettoFisico getSoggettoFisico() {
        return soggettoFisico;
    }

    /**
     * Imposta il valore della proprieta soggettoFisico.
     * 
     *     {@link SoggettoFisico }
     *     
     */
    public void setSoggettoFisico(SoggettoFisico value) {
        this.soggettoFisico = value;
    }

    /**
     * Recupera il valore della proprieta soggettoGiuridico.
     * 
     *     {@link SoggettoGiuridico }
     *     
     */
    public SoggettoGiuridico getSoggettoGiuridico() {
        return soggettoGiuridico;
    }

    /**
     * Imposta il valore della proprieta soggettoGiuridico.
     * 
     *     {@link SoggettoGiuridico }
     *     
     */
    public void setSoggettoGiuridico(SoggettoGiuridico value) {
        this.soggettoGiuridico = value;
    }

    /**
     * Recupera il valore della proprieta soggettoRiferimentoRegime.
     * 
     *     {@link IdentitaPersonaFisica }
     *     
     */
    public IdentitaPersonaFisica getSoggettoRiferimentoRegime() {
        return soggettoRiferimentoRegime;
    }

    /**
     * Imposta il valore della proprieta soggettoRiferimentoRegime.
     * 
     *     {@link IdentitaPersonaFisica }
     *     
     */
    public void setSoggettoRiferimentoRegime(IdentitaPersonaFisica value) {
        this.soggettoRiferimentoRegime = value;
    }

    /**
     * Recupera il valore della proprieta tipoNota.
     * 
     *     
     */
    public String getTipoNota() {
        return tipoNota;
    }

    /**
     * Imposta il valore della proprieta tipoNota.
     * 
     *     
     */
    public void setTipoNota(String value) {
        this.tipoNota = value;
    }

    /**
     * Recupera il valore della proprieta titolaritaNonCodificata.
     * 
     *     
     */
    public String getTitolaritaNonCodificata() {
        return titolaritaNonCodificata;
    }

    /**
     * Imposta il valore della proprieta titolaritaNonCodificata.
     * 
     *     
     */
    public void setTitolaritaNonCodificata(String value) {
        this.titolaritaNonCodificata = value;
    }

}
