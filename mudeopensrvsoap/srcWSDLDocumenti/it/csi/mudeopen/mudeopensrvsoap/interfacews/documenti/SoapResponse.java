/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for soapResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="soapResponse"&gt;
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soapResponse", propOrder = {
    "esito"
})
@XmlSeeAlso({
    FileOutput.class
})
public class SoapResponse {
    protected String esito;
    /**
     * Gets the value of the esito property.
     * 
     *     
     */
    public String getEsito() {
        return esito;
    }
    /**
     * Sets the value of the esito property.
     * 
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }
}
