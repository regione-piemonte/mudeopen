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
 * <p>Classe Java per DettaglioDatiPorzione complex type.
 * 
 * 
 * &lt;complexType name="DettaglioDatiPorzione"&gt;
 *         &lt;element name="are" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="centiare" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="classe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descQualita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ettari" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="idPorzione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroDeduzioni" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="qualita" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="redditoAgrario" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="redditoDominicale" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioDatiPorzione", propOrder = {
    "are",
    "centiare",
    "classe",
    "dataAggiornamento",
    "descQualita",
    "ettari",
    "idPorzione",
    "numeroDeduzioni",
    "qualita",
    "redditoAgrario",
    "redditoDominicale"
})
public class DettaglioDatiPorzione {

    protected int are;
    protected int centiare;
    @XmlElement(required = true, nillable = true)
    protected String classe;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String descQualita;
    protected int ettari;
    @XmlElement(required = true, nillable = true)
    protected String idPorzione;
    protected int numeroDeduzioni;
    protected int qualita;
    protected double redditoAgrario;
    protected double redditoDominicale;

    /**
     * Recupera il valore della proprieta are.
     * 
     */
    public int getAre() {
        return are;
    }

    /**
     * Imposta il valore della proprieta are.
     * 
     */
    public void setAre(int value) {
        this.are = value;
    }

    /**
     * Recupera il valore della proprieta centiare.
     * 
     */
    public int getCentiare() {
        return centiare;
    }

    /**
     * Imposta il valore della proprieta centiare.
     * 
     */
    public void setCentiare(int value) {
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
     * Recupera il valore della proprieta descQualita.
     * 
     *     
     */
    public String getDescQualita() {
        return descQualita;
    }

    /**
     * Imposta il valore della proprieta descQualita.
     * 
     *     
     */
    public void setDescQualita(String value) {
        this.descQualita = value;
    }

    /**
     * Recupera il valore della proprieta ettari.
     * 
     */
    public int getEttari() {
        return ettari;
    }

    /**
     * Imposta il valore della proprieta ettari.
     * 
     */
    public void setEttari(int value) {
        this.ettari = value;
    }

    /**
     * Recupera il valore della proprieta idPorzione.
     * 
     *     
     */
    public String getIdPorzione() {
        return idPorzione;
    }

    /**
     * Imposta il valore della proprieta idPorzione.
     * 
     *     
     */
    public void setIdPorzione(String value) {
        this.idPorzione = value;
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
     * Recupera il valore della proprieta qualita.
     * 
     */
    public int getQualita() {
        return qualita;
    }

    /**
     * Imposta il valore della proprieta qualita.
     * 
     */
    public void setQualita(int value) {
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

}
