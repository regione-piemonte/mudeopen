/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per DettaglioDatiTerreno complex type.
 * 
 * 
 * &lt;complexType name="DettaglioDatiTerreno"&gt;
 *         &lt;element name="annoNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="annotazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="are" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="centiare" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="classe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codComuneBelfiore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataEfficacia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="denominatore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrPartitaSpeciale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrQualita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="edificialita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ettari" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fabbricatiPresenti" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="foglio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="infoSupplementariCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mappale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroDeduzioni" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numeroNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroPorzioni" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numeroRiserve" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="partita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="progNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="qualita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="redditoAgrario" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="redditoDominicale" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="subalterno" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioDatiTerreno", propOrder = {
    "annoNota",
    "annotazione",
    "are",
    "centiare",
    "classe",
    "codCausale",
    "codComuneBelfiore",
    "dataAggiornamento",
    "dataEfficacia",
    "dataRegistrazione",
    "denominatore",
    "descrCausale",
    "descrNota",
    "descrPartitaSpeciale",
    "descrQualita",
    "edificialita",
    "ettari",
    "fabbricatiPresenti",
    "foglio",
    "infoSupplementariCausale",
    "mappale",
    "numeroDeduzioni",
    "numeroNota",
    "numeroPorzioni",
    "numeroRiserve",
    "partita",
    "progNota",
    "qualita",
    "redditoAgrario",
    "redditoDominicale",
    "sezione",
    "subalterno",
    "tipoNota"
})
public class DettaglioDatiTerreno {

    @XmlElement(required = true, nillable = true)
    protected String annoNota;
    @XmlElement(required = true, nillable = true)
    protected String annotazione;
    @XmlElement(required = true, nillable = true)
    protected String are;
    @XmlElement(required = true, nillable = true)
    protected String centiare;
    @XmlElement(required = true, nillable = true)
    protected String classe;
    @XmlElement(required = true, nillable = true)
    protected String codCausale;
    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfiore;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String dataEfficacia;
    @XmlElement(required = true, nillable = true)
    protected String dataRegistrazione;
    @XmlElement(required = true, nillable = true)
    protected String denominatore;
    @XmlElement(required = true, nillable = true)
    protected String descrCausale;
    @XmlElement(required = true, nillable = true)
    protected String descrNota;
    @XmlElement(required = true, nillable = true)
    protected String descrPartitaSpeciale;
    @XmlElement(required = true, nillable = true)
    protected String descrQualita;
    @XmlElement(required = true, nillable = true)
    protected String edificialita;
    @XmlElement(required = true, nillable = true)
    protected String ettari;
    protected boolean fabbricatiPresenti;
    @XmlElement(required = true, nillable = true)
    protected String foglio;
    @XmlElement(required = true, nillable = true)
    protected String infoSupplementariCausale;
    @XmlElement(required = true, nillable = true)
    protected String mappale;
    protected int numeroDeduzioni;
    @XmlElement(required = true, nillable = true)
    protected String numeroNota;
    protected int numeroPorzioni;
    protected int numeroRiserve;
    @XmlElement(required = true, nillable = true)
    protected String partita;
    @XmlElement(required = true, nillable = true)
    protected String progNota;
    @XmlElement(required = true, nillable = true)
    protected String qualita;
    protected double redditoAgrario;
    protected double redditoDominicale;
    @XmlElement(required = true, nillable = true)
    protected String sezione;
    @XmlElement(required = true, nillable = true)
    protected String subalterno;
    @XmlElement(required = true, nillable = true)
    protected String tipoNota;

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
     * Recupera il valore della proprieta annotazione.
     * 
     *     
     */
    public String getAnnotazione() {
        return annotazione;
    }

    /**
     * Imposta il valore della proprieta annotazione.
     * 
     *     
     */
    public void setAnnotazione(String value) {
        this.annotazione = value;
    }

    /**
     * Recupera il valore della proprieta are.
     * 
     *     
     */
    public String getAre() {
        return are;
    }

    /**
     * Imposta il valore della proprieta are.
     * 
     *     
     */
    public void setAre(String value) {
        this.are = value;
    }

    /**
     * Recupera il valore della proprieta centiare.
     * 
     *     
     */
    public String getCentiare() {
        return centiare;
    }

    /**
     * Imposta il valore della proprieta centiare.
     * 
     *     
     */
    public void setCentiare(String value) {
        this.centiare = value;
    }

    /**
     * Recupera il valore della proprieta classe.
     * 
     *     
     */
    public String getClasse() {
        return classe;
    }

    /**
     * Imposta il valore della proprieta classe.
     * 
     *     
     */
    public void setClasse(String value) {
        this.classe = value;
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
     * Recupera il valore della proprieta codComuneBelfiore.
     * 
     *     
     */
    public String getCodComuneBelfiore() {
        return codComuneBelfiore;
    }

    /**
     * Imposta il valore della proprieta codComuneBelfiore.
     * 
     *     
     */
    public void setCodComuneBelfiore(String value) {
        this.codComuneBelfiore = value;
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
     * Recupera il valore della proprieta denominatore.
     * 
     *     
     */
    public String getDenominatore() {
        return denominatore;
    }

    /**
     * Imposta il valore della proprieta denominatore.
     * 
     *     
     */
    public void setDenominatore(String value) {
        this.denominatore = value;
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
     * Recupera il valore della proprieta descrPartitaSpeciale.
     * 
     *     
     */
    public String getDescrPartitaSpeciale() {
        return descrPartitaSpeciale;
    }

    /**
     * Imposta il valore della proprieta descrPartitaSpeciale.
     * 
     *     
     */
    public void setDescrPartitaSpeciale(String value) {
        this.descrPartitaSpeciale = value;
    }

    /**
     * Recupera il valore della proprieta descrQualita.
     * 
     *     
     */
    public String getDescrQualita() {
        return descrQualita;
    }

    /**
     * Imposta il valore della proprieta descrQualita.
     * 
     *     
     */
    public void setDescrQualita(String value) {
        this.descrQualita = value;
    }

    /**
     * Recupera il valore della proprieta edificialita.
     * 
     *     
     */
    public String getEdificialita() {
        return edificialita;
    }

    /**
     * Imposta il valore della proprieta edificialita.
     * 
     *     
     */
    public void setEdificialita(String value) {
        this.edificialita = value;
    }

    /**
     * Recupera il valore della proprieta ettari.
     * 
     *     
     */
    public String getEttari() {
        return ettari;
    }

    /**
     * Imposta il valore della proprieta ettari.
     * 
     *     
     */
    public void setEttari(String value) {
        this.ettari = value;
    }

    /**
     * Recupera il valore della proprieta fabbricatiPresenti.
     * 
     */
    public boolean isFabbricatiPresenti() {
        return fabbricatiPresenti;
    }

    /**
     * Imposta il valore della proprieta fabbricatiPresenti.
     * 
     */
    public void setFabbricatiPresenti(boolean value) {
        this.fabbricatiPresenti = value;
    }

    /**
     * Recupera il valore della proprieta foglio.
     * 
     *     
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Imposta il valore della proprieta foglio.
     * 
     *     
     */
    public void setFoglio(String value) {
        this.foglio = value;
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
     * Recupera il valore della proprieta mappale.
     * 
     *     
     */
    public String getMappale() {
        return mappale;
    }

    /**
     * Imposta il valore della proprieta mappale.
     * 
     *     
     */
    public void setMappale(String value) {
        this.mappale = value;
    }

    /**
     * Recupera il valore della proprieta numeroDeduzioni.
     * 
     */
    public int getNumeroDeduzioni() {
        return numeroDeduzioni;
    }

    /**
     * Imposta il valore della proprieta numeroDeduzioni.
     * 
     */
    public void setNumeroDeduzioni(int value) {
        this.numeroDeduzioni = value;
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
     * Recupera il valore della proprieta numeroPorzioni.
     * 
     */
    public int getNumeroPorzioni() {
        return numeroPorzioni;
    }

    /**
     * Imposta il valore della proprieta numeroPorzioni.
     * 
     */
    public void setNumeroPorzioni(int value) {
        this.numeroPorzioni = value;
    }

    /**
     * Recupera il valore della proprieta numeroRiserve.
     * 
     */
    public int getNumeroRiserve() {
        return numeroRiserve;
    }

    /**
     * Imposta il valore della proprieta numeroRiserve.
     * 
     */
    public void setNumeroRiserve(int value) {
        this.numeroRiserve = value;
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
     * Recupera il valore della proprieta qualita.
     * 
     *     
     */
    public String getQualita() {
        return qualita;
    }

    /**
     * Imposta il valore della proprieta qualita.
     * 
     *     
     */
    public void setQualita(String value) {
        this.qualita = value;
    }

    /**
     * Recupera il valore della proprieta redditoAgrario.
     * 
     */
    public double getRedditoAgrario() {
        return redditoAgrario;
    }

    /**
     * Imposta il valore della proprieta redditoAgrario.
     * 
     */
    public void setRedditoAgrario(double value) {
        this.redditoAgrario = value;
    }

    /**
     * Recupera il valore della proprieta redditoDominicale.
     * 
     */
    public double getRedditoDominicale() {
        return redditoDominicale;
    }

    /**
     * Imposta il valore della proprieta redditoDominicale.
     * 
     */
    public void setRedditoDominicale(double value) {
        this.redditoDominicale = value;
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
     * Recupera il valore della proprieta subalterno.
     * 
     *     
     */
    public String getSubalterno() {
        return subalterno;
    }

    /**
     * Imposta il valore della proprieta subalterno.
     * 
     *     
     */
    public void setSubalterno(String value) {
        this.subalterno = value;
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

}
