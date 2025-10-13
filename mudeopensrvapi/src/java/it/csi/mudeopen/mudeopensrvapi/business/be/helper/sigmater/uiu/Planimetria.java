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
 * <p>Classe Java per Planimetria complex type.
 * 
 * 
 * &lt;complexType name="Planimetria"&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataPresentazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="formato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="idImmaginePiantina" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numScheda" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="scala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Planimetria", propOrder = {
    "dataAggiornamento",
    "dataPresentazione",
    "formato",
    "idImmaginePiantina",
    "nomeFile",
    "numScheda",
    "scala"
})
public class Planimetria {

    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String dataPresentazione;
    @XmlElement(required = true, nillable = true)
    protected String formato;
    protected long idImmaginePiantina;
    @XmlElement(required = true, nillable = true)
    protected String nomeFile;
    protected long numScheda;
    @XmlElement(required = true, nillable = true)
    protected String scala;

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
     * Recupera il valore della proprieta dataPresentazione.
     * 
     *     
     */
    public String getDataPresentazione() {
        return dataPresentazione;
    }

    /**
     * Imposta il valore della proprieta dataPresentazione.
     * 
     *     
     */
    public void setDataPresentazione(String value) {
        this.dataPresentazione = value;
    }

    /**
     * Recupera il valore della proprieta formato.
     * 
     *     
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Imposta il valore della proprieta formato.
     * 
     *     
     */
    public void setFormato(String value) {
        this.formato = value;
    }

    /**
     * Recupera il valore della proprieta idImmaginePiantina.
     * 
     */
    public long getIdImmaginePiantina() {
        return idImmaginePiantina;
    }

    /**
     * Imposta il valore della proprieta idImmaginePiantina.
     * 
     */
    public void setIdImmaginePiantina(long value) {
        this.idImmaginePiantina = value;
    }

    /**
     * Recupera il valore della proprieta nomeFile.
     * 
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della proprieta nomeFile.
     * 
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della proprieta numScheda.
     * 
     */
    public long getNumScheda() {
        return numScheda;
    }

    /**
     * Imposta il valore della proprieta numScheda.
     * 
     */
    public void setNumScheda(long value) {
        this.numScheda = value;
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

}
