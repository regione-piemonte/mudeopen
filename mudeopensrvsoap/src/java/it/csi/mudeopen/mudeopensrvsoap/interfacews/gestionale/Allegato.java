/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for allegato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="allegato"&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegato", propOrder = {
    "numeroIstanza",
    "tipoAllegato",
    "nomeFile"
})
public class Allegato {
    @XmlElement(required = true)
    protected String numeroIstanza;
    protected String tipoAllegato;
    @XmlElement(required = true)
    protected String nomeFile;
    /**
     * Gets the value of the numeroIstanza property.
     * 
     *     
     */
    public String getNumeroIstanza() {
        return numeroIstanza;
    }
    /**
     * Sets the value of the numeroIstanza property.
     * 
     *     
     */
    public void setNumeroIstanza(String value) {
        this.numeroIstanza = value;
    }
    /**
     * Gets the value of the tipoAllegato property.
     * 
     *     
     */
    public String getTipoAllegato() {
        return tipoAllegato;
    }
    /**
     * Sets the value of the tipoAllegato property.
     * 
     *     
     */
    public void setTipoAllegato(String value) {
        this.tipoAllegato = value;
    }
    /**
     * Gets the value of the nomeFile property.
     * 
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }
    /**
     * Sets the value of the nomeFile property.
     * 
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }
}
