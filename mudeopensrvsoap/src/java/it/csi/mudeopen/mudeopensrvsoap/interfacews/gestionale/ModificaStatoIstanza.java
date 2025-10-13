/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for modificaStatoIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="modificaStatoIstanza"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="datiModifica" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}datiModificaStatoIstanza" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modificaStatoIstanza", propOrder = {
    "token",
    "datiModifica"
})
public class ModificaStatoIstanza {
    protected String token;
    protected DatiModificaStatoIstanza datiModifica;
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
     * Gets the value of the datiModifica property.
     * 
     *     {@link DatiModificaStatoIstanza }
     *     
     */
    public DatiModificaStatoIstanza getDatiModifica() {
        return datiModifica;
    }
    /**
     * Sets the value of the datiModifica property.
     * 
     *     {@link DatiModificaStatoIstanza }
     *     
     */
    public void setDatiModifica(DatiModificaStatoIstanza value) {
        this.datiModifica = value;
    }
}
