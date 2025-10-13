/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for inserisciNotificaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="inserisciNotificaResponse"&gt;
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserisciNotificaResponse", propOrder = {
    "result"
})
public class InserisciNotificaResponse {
    protected long result;
    /**
     * Gets the value of the result property.
     * 
     */
    public long getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     */
    public void setResult(long value) {
        this.result = value;
    }
}
