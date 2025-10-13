/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for visualizzaDatiProtocollazioneIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="visualizzaDatiProtocollazioneIstanza"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identificativoIstanzaExt" type="{http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/}identificativoIstanzaExt" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visualizzaDatiProtocollazioneIstanza", propOrder = {
    "token",
    "identificativoIstanzaExt"
})
public class VisualizzaDatiProtocollazioneIstanza {
    protected String token;
    protected IdentificativoIstanzaExt identificativoIstanzaExt;
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
     * Gets the value of the identificativoIstanzaExt property.
     * 
     *     {@link IdentificativoIstanzaExt }
     *     
     */
    public IdentificativoIstanzaExt getIdentificativoIstanzaExt() {
        return identificativoIstanzaExt;
    }
    /**
     * Sets the value of the identificativoIstanzaExt property.
     * 
     *     {@link IdentificativoIstanzaExt }
     *     
     */
    public void setIdentificativoIstanzaExt(IdentificativoIstanzaExt value) {
        this.identificativoIstanzaExt = value;
    }
}
