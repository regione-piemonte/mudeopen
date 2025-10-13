/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per IdentificativoComune complex type.
 * 
 * 
 * &lt;complexType name="IdentificativoComune"&gt;
 *         &lt;element name="codComuneBelfiore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codComuneIstat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codRegione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeComune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeRegione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="siglaProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificativoComune", propOrder = {
    "codComuneBelfiore",
    "codComuneIstat",
    "codProvincia",
    "codRegione",
    "nomeComune",
    "nomeProvincia",
    "nomeRegione",
    "siglaProvincia"
})
public class IdentificativoComune {

    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfiore;
    @XmlElement(required = true, nillable = true)
    protected String codComuneIstat;
    @XmlElement(required = true, nillable = true)
    protected String codProvincia;
    @XmlElement(required = true, nillable = true)
    protected String codRegione;
    @XmlElement(required = true, nillable = true)
    protected String nomeComune;
    @XmlElement(required = true, nillable = true)
    protected String nomeProvincia;
    @XmlElement(required = true, nillable = true)
    protected String nomeRegione;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvincia;

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
     * Recupera il valore della proprieta codComuneIstat.
     * 
     *     
     */
    public String getCodComuneIstat() {
        return codComuneIstat;
    }

    /**
     * Imposta il valore della proprieta codComuneIstat.
     * 
     *     
     */
    public void setCodComuneIstat(String value) {
        this.codComuneIstat = value;
    }

    /**
     * Recupera il valore della proprieta codProvincia.
     * 
     *     
     */
    public String getCodProvincia() {
        return codProvincia;
    }

    /**
     * Imposta il valore della proprieta codProvincia.
     * 
     *     
     */
    public void setCodProvincia(String value) {
        this.codProvincia = value;
    }

    /**
     * Recupera il valore della proprieta codRegione.
     * 
     *     
     */
    public String getCodRegione() {
        return codRegione;
    }

    /**
     * Imposta il valore della proprieta codRegione.
     * 
     *     
     */
    public void setCodRegione(String value) {
        this.codRegione = value;
    }

    /**
     * Recupera il valore della proprieta nomeComune.
     * 
     *     
     */
    public String getNomeComune() {
        return nomeComune;
    }

    /**
     * Imposta il valore della proprieta nomeComune.
     * 
     *     
     */
    public void setNomeComune(String value) {
        this.nomeComune = value;
    }

    /**
     * Recupera il valore della proprieta nomeProvincia.
     * 
     *     
     */
    public String getNomeProvincia() {
        return nomeProvincia;
    }

    /**
     * Imposta il valore della proprieta nomeProvincia.
     * 
     *     
     */
    public void setNomeProvincia(String value) {
        this.nomeProvincia = value;
    }

    /**
     * Recupera il valore della proprieta nomeRegione.
     * 
     *     
     */
    public String getNomeRegione() {
        return nomeRegione;
    }

    /**
     * Imposta il valore della proprieta nomeRegione.
     * 
     *     
     */
    public void setNomeRegione(String value) {
        this.nomeRegione = value;
    }

    /**
     * Recupera il valore della proprieta siglaProvincia.
     * 
     *     
     */
    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    /**
     * Imposta il valore della proprieta siglaProvincia.
     * 
     *     
     */
    public void setSiglaProvincia(String value) {
        this.siglaProvincia = value;
    }

}
