/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for visualizzazioneNotificaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="visualizzazioneNotificaResponse"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}notifica" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visualizzazioneNotificaResponse", propOrder = {
    "result"
})
public class VisualizzazioneNotificaResponse {
    protected Notifica result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link Notifica }
     *     
     */
    public Notifica getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link Notifica }
     *     
     */
    public void setResult(Notifica value) {
        this.result = value;
    }
}
