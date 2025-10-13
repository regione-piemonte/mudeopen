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
 * <p>Classe Java per SoggettoFisico complex type.
 * 
 * 
 * &lt;complexType name="SoggettoFisico"&gt;
 *         &lt;element name="codComuneBelfioreNascita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codComuneBelfioreTitolarita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoFisico", propOrder = {
    "codComuneBelfioreNascita",
    "codComuneBelfioreTitolarita",
    "codFiscale",
    "cognome",
    "dataNascita",
    "nome",
    "sesso"
})
public class SoggettoFisico {

    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfioreNascita;
    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfioreTitolarita;
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
     * Recupera il valore della proprieta codComuneBelfioreNascita.
     * 
     *     
     */
    public String getCodComuneBelfioreNascita() {
        return codComuneBelfioreNascita;
    }

    /**
     * Imposta il valore della proprieta codComuneBelfioreNascita.
     * 
     *     
     */
    public void setCodComuneBelfioreNascita(String value) {
        this.codComuneBelfioreNascita = value;
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
