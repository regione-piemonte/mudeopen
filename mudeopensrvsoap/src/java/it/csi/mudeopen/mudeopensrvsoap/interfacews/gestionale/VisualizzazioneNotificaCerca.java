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
 * <p>Java class for visualizzazioneNotificaCerca complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="visualizzazioneNotificaCerca"&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="identificativoNotifica" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visualizzazioneNotificaCerca", propOrder = {
    "numeroIstanza",
    "identificativoNotifica"
})
public class VisualizzazioneNotificaCerca {
    @XmlElement(required = true)
    protected String numeroIstanza;
    protected long identificativoNotifica;
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
     * Gets the value of the identificativoNotifica property.
     * 
     */
    public long getIdentificativoNotifica() {
        return identificativoNotifica;
    }
    /**
     * Sets the value of the identificativoNotifica property.
     * 
     */
    public void setIdentificativoNotifica(long value) {
        this.identificativoNotifica = value;
    }
}
