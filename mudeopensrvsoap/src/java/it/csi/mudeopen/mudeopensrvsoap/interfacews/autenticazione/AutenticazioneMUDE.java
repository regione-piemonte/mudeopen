/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for autenticazioneMUDE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="autenticazioneMUDE"&gt;
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticazioneMUDE", propOrder = {
    "codiceFiscale"
})
public class AutenticazioneMUDE {
    protected String codiceFiscale;
    /**
     * Gets the value of the codiceFiscale property.
     * 
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    /**
     * Sets the value of the codiceFiscale property.
     * 
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }
}
