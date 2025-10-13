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
 * 
 * 
 *         &lt;element name="codComuneBelfiore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="foglio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mappale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sezioneUrbana" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="subalterno" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificativoCatastaleFabbricato", propOrder = {
    "codComuneBelfiore",
    "dataAggiornamento",
    "foglio",
    "mappale",
    "sezione",
    "sezioneUrbana",
    "subalterno"
})
public class IdentificativoCatastaleFabbricato {

    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfiore;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String foglio;
    @XmlElement(required = true, nillable = true)
    protected String mappale;
    @XmlElement(required = true, nillable = true)
    protected String sezione;
    @XmlElement(required = true, nillable = true)
    protected String sezioneUrbana;
    @XmlElement(required = true, nillable = true)
    protected String subalterno;

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
     * Recupera il valore della proprieta sezioneUrbana.
     * 
     *     
     */
    public String getSezioneUrbana() {
        return sezioneUrbana;
    }

    /**
     * Imposta il valore della proprieta sezioneUrbana.
     * 
     *     
     */
    public void setSezioneUrbana(String value) {
        this.sezioneUrbana = value;
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

}
