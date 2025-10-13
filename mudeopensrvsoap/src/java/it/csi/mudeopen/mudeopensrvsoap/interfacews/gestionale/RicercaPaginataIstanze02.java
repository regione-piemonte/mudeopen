/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaPaginataIstanze02 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaPaginataIstanze02"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="filtro" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}filtroRicercaPaginata" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPaginataIstanze02", propOrder = {
    "token",
    "filtro"
})
public class RicercaPaginataIstanze02 {
    protected String token;
    protected FiltroRicercaPaginata filtro;
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
     *     {@link FiltroRicercaPaginata }
     *     
     */
    public FiltroRicercaPaginata getFiltro() {
        return filtro;
    }
    /**
     * Sets the value of the filtro property.
     * 
     *     {@link FiltroRicercaPaginata }
     *     
     */
    public void setFiltro(FiltroRicercaPaginata value) {
        this.filtro = value;
    }
}
