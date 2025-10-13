/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaStatiIstanzaAmmessi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaStatiIstanzaAmmessi"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identificativo" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}ricercaStatiAmmessi" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaStatiIstanzaAmmessi", propOrder = {
    "token",
    "identificativo"
})
public class RicercaStatiIstanzaAmmessi {
    protected String token;
    protected RicercaStatiAmmessi identificativo;
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
     * Gets the value of the identificativo property.
     * 
     *     {@link RicercaStatiAmmessi }
     *     
     */
    public RicercaStatiAmmessi getIdentificativo() {
        return identificativo;
    }
    /**
     * Sets the value of the identificativo property.
     * 
     *     {@link RicercaStatiAmmessi }
     *     
     */
    public void setIdentificativo(RicercaStatiAmmessi value) {
        this.identificativo = value;
    }
}
