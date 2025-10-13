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
 * <p>Classe Java per IdentitaPersonaFisica complex type.
 * 
 * 
 * &lt;complexType name="IdentitaPersonaFisica"&gt;
 *         &lt;element name="codBelfioreComuneDiNascita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codBelfioreComuneDiRicerca" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentitaPersonaFisica", propOrder = {
    "codBelfioreComuneDiNascita",
    "codBelfioreComuneDiRicerca",
    "codFiscale",
    "cognome",
    "dataNascita",
    "nome",
    "sesso"
})
public class IdentitaPersonaFisica {

    @XmlElement(required = true, nillable = true)
    protected String codBelfioreComuneDiNascita;
    @XmlElement(required = true, nillable = true)
    protected String codBelfioreComuneDiRicerca;
    @XmlElement(required = true, nillable = true)
    protected String codFiscale;
    @XmlElement(required = true, nillable = true)
    protected String cognome;
    @XmlElement(required = true, nillable = true)
    protected String dataNascita;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    @XmlElement(required = true, nillable = true)
    protected String sesso;

    /**
     * Recupera il valore della proprieta codBelfioreComuneDiNascita.
     * 
     *     
     */
    public String getCodBelfioreComuneDiNascita() {
        return codBelfioreComuneDiNascita;
    }

    /**
     * Imposta il valore della proprieta codBelfioreComuneDiNascita.
     * 
     *     
     */
    public void setCodBelfioreComuneDiNascita(String value) {
        this.codBelfioreComuneDiNascita = value;
    }

    /**
     * Recupera il valore della proprieta codBelfioreComuneDiRicerca.
     * 
     *     
     */
    public String getCodBelfioreComuneDiRicerca() {
        return codBelfioreComuneDiRicerca;
    }

    /**
     * Imposta il valore della proprieta codBelfioreComuneDiRicerca.
     * 
     *     
     */
    public void setCodBelfioreComuneDiRicerca(String value) {
        this.codBelfioreComuneDiRicerca = value;
    }

    /**
     * Recupera il valore della proprieta codFiscale.
     * 
     *     
     */
    public String getCodFiscale() {
        return codFiscale;
    }

    /**
     * Imposta il valore della proprieta codFiscale.
     * 
     *     
     */
    public void setCodFiscale(String value) {
        this.codFiscale = value;
    }

    /**
     * Recupera il valore della proprieta cognome.
     * 
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprieta cognome.
     * 
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprieta dataNascita.
     * 
     *     
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta il valore della proprieta dataNascita.
     * 
     *     
     */
    public void setDataNascita(String value) {
        this.dataNascita = value;
    }

    /**
     * Recupera il valore della proprieta nome.
     * 
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprieta nome.
     * 
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprieta sesso.
     * 
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprieta sesso.
     * 
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

}
