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
 * <p>Java class for ruolo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ruolo"&gt;
 *         &lt;element name="codiceRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruolo", propOrder = {
    "codiceRuolo",
    "descrizioneRuolo"
})
public class Ruolo {
    @XmlElement(required = true)
    protected String codiceRuolo;
    @XmlElement(required = true)
    protected String descrizioneRuolo;
    /**
     * Gets the value of the codiceRuolo property.
     * 
     *     
     */
    public String getCodiceRuolo() {
        return codiceRuolo;
    }
    /**
     * Sets the value of the codiceRuolo property.
     * 
     *     
     */
    public void setCodiceRuolo(String value) {
        this.codiceRuolo = value;
    }
    /**
     * Gets the value of the descrizioneRuolo property.
     * 
     *     
     */
    public String getDescrizioneRuolo() {
        return descrizioneRuolo;
    }
    /**
     * Sets the value of the descrizioneRuolo property.
     * 
     *     
     */
    public void setDescrizioneRuolo(String value) {
        this.descrizioneRuolo = value;
    }
}
