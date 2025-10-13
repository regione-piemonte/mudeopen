/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for autenticazioneMUDEResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="autenticazioneMUDEResponse"&gt;
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticazioneMUDEResponse", propOrder = {
    "result"
})
public class AutenticazioneMUDEResponse {
    protected String result;
    /**
     * Gets the value of the result property.
     * 
     *     
     */
    public String getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }
}
