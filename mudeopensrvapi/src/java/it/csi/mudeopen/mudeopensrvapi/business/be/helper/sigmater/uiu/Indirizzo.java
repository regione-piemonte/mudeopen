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
 * <p>Classe Java per Indirizzo complex type.
 * 
 * 
 * &lt;complexType name="Indirizzo"&gt;
 *         &lt;element name="codBelfioreComune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroCivico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="toponimo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Indirizzo", propOrder = {
    "codBelfioreComune",
    "dataAggiornamento",
    "indirizzo",
    "numeroCivico",
    "toponimo"
})
public class Indirizzo {

    @XmlElement(required = true, nillable = true)
    protected String codBelfioreComune;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String numeroCivico;
    @XmlElement(required = true, nillable = true)
    protected String toponimo;

    /**
     * Recupera il valore della proprieta codBelfioreComune.
     * 
     *     
     */
    public String getCodBelfioreComune() {
        return codBelfioreComune;
    }

    /**
     * Imposta il valore della proprieta codBelfioreComune.
     * 
     *     
     */
    public void setCodBelfioreComune(String value) {
        this.codBelfioreComune = value;
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
     * Recupera il valore della proprieta indirizzo.
     * 
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprieta indirizzo.
     * 
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprieta numeroCivico.
     * 
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Imposta il valore della proprieta numeroCivico.
     * 
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Recupera il valore della proprieta toponimo.
     * 
     *     
     */
    public String getToponimo() {
        return toponimo;
    }

    /**
     * Imposta il valore della proprieta toponimo.
     * 
     *     
     */
    public void setToponimo(String value) {
        this.toponimo = value;
    }

}
