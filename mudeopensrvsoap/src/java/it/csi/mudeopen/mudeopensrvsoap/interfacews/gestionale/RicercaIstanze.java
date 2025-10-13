/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaIstanze complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaIstanze"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="filtro" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}ricercaIstanza" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaIstanze", propOrder = {
    "token",
    "filtro"
})
public class RicercaIstanze {
    protected String token;
    protected RicercaIstanza filtro;
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
     * Gets the value of the filtro property.
     * 
     *     {@link RicercaIstanza }
     *     
     */
    public RicercaIstanza getFiltro() {
        return filtro;
    }
    /**
     * Sets the value of the filtro property.
     * 
     *     {@link RicercaIstanza }
     *     
     */
    public void setFiltro(RicercaIstanza value) {
        this.filtro = value;
    }
}
