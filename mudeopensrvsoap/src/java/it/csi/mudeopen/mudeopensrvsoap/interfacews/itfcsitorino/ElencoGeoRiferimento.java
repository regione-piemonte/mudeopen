/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for elencoGeoRiferimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="elencoGeoRiferimento"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroMude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoGeoRiferimento", propOrder = {
    "token",
    "numeroMude"
})
public class ElencoGeoRiferimento {
    protected String token;
    protected String numeroMude;
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
     * Gets the value of the numeroMude property.
     * 
     *     
     */
    public String getNumeroMude() {
        return numeroMude;
    }
    /**
     * Sets the value of the numeroMude property.
     * 
     *     
     */
    public void setNumeroMude(String value) {
        this.numeroMude = value;
    }
}
