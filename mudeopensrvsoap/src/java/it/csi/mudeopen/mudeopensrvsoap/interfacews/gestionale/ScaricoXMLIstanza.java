/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for scaricoXMLIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="scaricoXMLIstanza"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identificativoIstanza" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}identificativoIstanza" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaricoXMLIstanza", propOrder = {
    "token",
    "identificativoIstanza"
})
public class ScaricoXMLIstanza {
    protected String token;
    protected IdentificativoIstanza identificativoIstanza;
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
     * Gets the value of the identificativoIstanza property.
     * 
     *     {@link IdentificativoIstanza }
     *     
     */
    public IdentificativoIstanza getIdentificativoIstanza() {
        return identificativoIstanza;
    }
    /**
     * Sets the value of the identificativoIstanza property.
     * 
     *     {@link IdentificativoIstanza }
     *     
     */
    public void setIdentificativoIstanza(IdentificativoIstanza value) {
        this.identificativoIstanza = value;
    }
}
