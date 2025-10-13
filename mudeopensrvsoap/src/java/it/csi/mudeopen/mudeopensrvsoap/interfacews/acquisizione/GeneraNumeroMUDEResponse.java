/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for generaNumeroMUDEResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="generaNumeroMUDEResponse"&gt;
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaNumeroMUDEResponse", propOrder = {
    "result"
})
public class GeneraNumeroMUDEResponse {
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
