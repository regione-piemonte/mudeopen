/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per DettaglioDatiFabbricato complex type.
 * 
 * 
 * &lt;complexType name="DettaglioDatiFabbricato"&gt;
 *         &lt;element name="annoNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="annotazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="classe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codComuneBelfiore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="consistenza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataEfficacia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrCategoria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrPartitaSpeciale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="edificio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="flagClassamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="infoSupplementariCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="interno1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="interno2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lotto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroRiserve" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="partita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piano1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piano2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piano3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piano4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="progNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rendita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="scala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="superficie" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoNota" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="zonaCensuaria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioDatiFabbricato", propOrder = {
    "annoNota",
    "annotazione",
    "categoria",
    "classe",
    "codCausale",
    "codComuneBelfiore",
    "consistenza",
    "dataAggiornamento",
    "dataEfficacia",
    "dataRegistrazione",
    "descrCategoria",
    "descrCausale",
    "descrNota",
    "descrPartitaSpeciale",
    "edificio",
    "flagClassamento",
    "infoSupplementariCausale",
    "interno1",
    "interno2",
    "lotto",
    "numeroNota",
    "numeroRiserve",
    "partita",
    "piano1",
    "piano2",
    "piano3",
    "piano4",
    "progNota",
    "rendita",
    "scala",
    "sezione",
    "superficie",
    "tipoNota",
    "zonaCensuaria"
})
public class DettaglioDatiFabbricato {

    @XmlElement(required = true, nillable = true)
    protected String annoNota;
    @XmlElement(required = true, nillable = true)
    protected String annotazione;
    @XmlElement(required = true, nillable = true)
    protected String categoria;
    @XmlElement(required = true, nillable = true)
    protected String classe;
    @XmlElement(required = true, nillable = true)
    protected String codCausale;
    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfiore;
    @XmlElement(required = true, nillable = true)
    protected String consistenza;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String dataEfficacia;
    @XmlElement(required = true, nillable = true)
    protected String dataRegistrazione;
    @XmlElement(required = true, nillable = true)
    protected String descrCategoria;
    @XmlElement(required = true, nillable = true)
    protected String descrCausale;
    @XmlElement(required = true, nillable = true)
    protected String descrNota;
    @XmlElement(required = true, nillable = true)
    protected String descrPartitaSpeciale;
    @XmlElement(required = true, nillable = true)
    protected String edificio;
    @XmlElement(required = true, nillable = true)
    protected String flagClassamento;
    @XmlElement(required = true, nillable = true)
    protected String infoSupplementariCausale;
    @XmlElement(required = true, nillable = true)
    protected String interno1;
    @XmlElement(required = true, nillable = true)
    protected String interno2;
    @XmlElement(required = true, nillable = true)
    protected String lotto;
    @XmlElement(required = true, nillable = true)
    protected String numeroNota;
    protected int numeroRiserve;
    @XmlElement(required = true, nillable = true)
    protected String partita;
    @XmlElement(required = true, nillable = true)
    protected String piano1;
    @XmlElement(required = true, nillable = true)
    protected String piano2;
    @XmlElement(required = true, nillable = true)
    protected String piano3;
    @XmlElement(required = true, nillable = true)
    protected String piano4;
    @XmlElement(required = true, nillable = true)
    protected String progNota;
    @XmlElement(required = true, nillable = true)
    protected String rendita;
    @XmlElement(required = true, nillable = true)
    protected String scala;
    @XmlElement(required = true, nillable = true)
    protected String sezione;
    @XmlElement(required = true, nillable = true)
    protected String superficie;
    @XmlElement(required = true, nillable = true)
    protected String tipoNota;
    @XmlElement(required = true, nillable = true)
    protected String zonaCensuaria;

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
     * Recupera il valore della proprieta categoria.
     * 
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della proprieta categoria.
     * 
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
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
     * Recupera il valore della proprieta consistenza.
     * 
     *     
     */
    public String getConsistenza() {
        return consistenza;
    }

    /**
     * Imposta il valore della proprieta consistenza.
     * 
     *     
     */
    public void setConsistenza(String value) {
        this.consistenza = value;
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
     * Recupera il valore della proprieta descrCategoria.
     * 
     *     
     */
    public String getDescrCategoria() {
        return descrCategoria;
    }

    /**
     * Imposta il valore della proprieta descrCategoria.
     * 
     *     
     */
    public void setDescrCategoria(String value) {
        this.descrCategoria = value;
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
     * Recupera il valore della proprieta edificio.
     * 
     *     
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Imposta il valore della proprieta edificio.
     * 
     *     
     */
    public void setEdificio(String value) {
        this.edificio = value;
    }

    /**
     * Recupera il valore della proprieta flagClassamento.
     * 
     *     
     */
    public String getFlagClassamento() {
        return flagClassamento;
    }

    /**
     * Imposta il valore della proprieta flagClassamento.
     * 
     *     
     */
    public void setFlagClassamento(String value) {
        this.flagClassamento = value;
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
     * Recupera il valore della proprieta interno1.
     * 
     *     
     */
    public String getInterno1() {
        return interno1;
    }

    /**
     * Imposta il valore della proprieta interno1.
     * 
     *     
     */
    public void setInterno1(String value) {
        this.interno1 = value;
    }

    /**
     * Recupera il valore della proprieta interno2.
     * 
     *     
     */
    public String getInterno2() {
        return interno2;
    }

    /**
     * Imposta il valore della proprieta interno2.
     * 
     *     
     */
    public void setInterno2(String value) {
        this.interno2 = value;
    }

    /**
     * Recupera il valore della proprieta lotto.
     * 
     *     
     */
    public String getLotto() {
        return lotto;
    }

    /**
     * Imposta il valore della proprieta lotto.
     * 
     *     
     */
    public void setLotto(String value) {
        this.lotto = value;
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
     * Recupera il valore della proprieta piano1.
     * 
     *     
     */
    public String getPiano1() {
        return piano1;
    }

    /**
     * Imposta il valore della proprieta piano1.
     * 
     *     
     */
    public void setPiano1(String value) {
        this.piano1 = value;
    }

    /**
     * Recupera il valore della proprieta piano2.
     * 
     *     
     */
    public String getPiano2() {
        return piano2;
    }

    /**
     * Imposta il valore della proprieta piano2.
     * 
     *     
     */
    public void setPiano2(String value) {
        this.piano2 = value;
    }

    /**
     * Recupera il valore della proprieta piano3.
     * 
     *     
     */
    public String getPiano3() {
        return piano3;
    }

    /**
     * Imposta il valore della proprieta piano3.
     * 
     *     
     */
    public void setPiano3(String value) {
        this.piano3 = value;
    }

    /**
     * Recupera il valore della proprieta piano4.
     * 
     *     
     */
    public String getPiano4() {
        return piano4;
    }

    /**
     * Imposta il valore della proprieta piano4.
     * 
     *     
     */
    public void setPiano4(String value) {
        this.piano4 = value;
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
     * Recupera il valore della proprieta rendita.
     * 
     *     
     */
    public String getRendita() {
        return rendita;
    }

    /**
     * Imposta il valore della proprieta rendita.
     * 
     *     
     */
    public void setRendita(String value) {
        this.rendita = value;
    }

    /**
     * Recupera il valore della proprieta scala.
     * 
     *     
     */
    public String getScala() {
        return scala;
    }

    /**
     * Imposta il valore della proprieta scala.
     * 
     *     
     */
    public void setScala(String value) {
        this.scala = value;
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
     * Recupera il valore della proprieta superficie.
     * 
     *     
     */
    public String getSuperficie() {
        return superficie;
    }

    /**
     * Imposta il valore della proprieta superficie.
     * 
     *     
     */
    public void setSuperficie(String value) {
        this.superficie = value;
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
     * Recupera il valore della proprieta zonaCensuaria.
     * 
     *     
     */
    public String getZonaCensuaria() {
        return zonaCensuaria;
    }

    /**
     * Imposta il valore della proprieta zonaCensuaria.
     * 
     *     
     */
    public void setZonaCensuaria(String value) {
        this.zonaCensuaria = value;
    }

}
