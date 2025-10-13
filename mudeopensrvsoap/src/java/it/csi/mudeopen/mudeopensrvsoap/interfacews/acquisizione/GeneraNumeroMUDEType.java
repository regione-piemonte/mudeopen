/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for generaNumeroMUDEType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="generaNumeroMUDEType"&gt;
 *         &lt;element name="codiceIstatComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nuovoIntervento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaNumeroMUDEType", propOrder = {
    "codiceIstatComune",
    "nuovoIntervento"
})
public class GeneraNumeroMUDEType {
    protected String codiceIstatComune;
    protected String nuovoIntervento;
    /**
     * Gets the value of the codiceIstatComune property.
     * 
     *     
     */
    public String getCodiceIstatComune() {
        return codiceIstatComune;
    }
    /**
     * Sets the value of the codiceIstatComune property.
     * 
     *     
     */
    public void setCodiceIstatComune(String value) {
        this.codiceIstatComune = value;
    }
    /**
     * Gets the value of the nuovoIntervento property.
     * 
     *     
     */
    public String getNuovoIntervento() {
        return nuovoIntervento;
    }
    /**
     * Sets the value of the nuovoIntervento property.
     * 
     *     
     */
    public void setNuovoIntervento(String value) {
        this.nuovoIntervento = value;
    }
}
