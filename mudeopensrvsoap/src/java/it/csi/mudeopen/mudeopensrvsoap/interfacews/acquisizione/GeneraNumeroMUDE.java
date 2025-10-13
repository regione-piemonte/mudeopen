/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for generaNumeroMUDE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="generaNumeroMUDE"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="generaNumeroMUDE" type="{http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/}generaNumeroMUDEType" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaNumeroMUDE", propOrder = {
    "token",
    "generaNumeroMUDE"
})
public class GeneraNumeroMUDE {
    protected String token;
    protected GeneraNumeroMUDEType generaNumeroMUDE;
    /**
     * Gets the value of the token property.
     * 
     *     
     */
    public String getToken() {
        return token;
    }
    /**
     * Sets the value of the token property.
     * 
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }
    /**
     * Gets the value of the generaNumeroMUDE property.
     * 
     *     {@link GeneraNumeroMUDEType }
     *     
     */
    public GeneraNumeroMUDEType getGeneraNumeroMUDE() {
        return generaNumeroMUDE;
    }
    /**
     * Sets the value of the generaNumeroMUDE property.
     * 
     *     {@link GeneraNumeroMUDEType }
     *     
     */
    public void setGeneraNumeroMUDE(GeneraNumeroMUDEType value) {
        this.generaNumeroMUDE = value;
    }
}
