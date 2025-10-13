/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for invioIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="invioIstanza"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="istanzaExt" type="{http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/}istanzaExt" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invioIstanza", propOrder = {
    "token",
    "istanzaExt"
})
public class InvioIstanza {
    protected String token;
    protected IstanzaExt istanzaExt;
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
     * Gets the value of the istanzaExt property.
     * 
     *     {@link IstanzaExt }
     *     
     */
    public IstanzaExt getIstanzaExt() {
        return istanzaExt;
    }
    /**
     * Sets the value of the istanzaExt property.
     * 
     *     {@link IstanzaExt }
     *     
     */
    public void setIstanzaExt(IstanzaExt value) {
        this.istanzaExt = value;
    }
}
