/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaDatiSintesiIstanzaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaDatiSintesiIstanzaResponse"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}datiSintesiIstanza" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaDatiSintesiIstanzaResponse", propOrder = {
    "result"
})
public class RicercaDatiSintesiIstanzaResponse {
    protected DatiSintesiIstanza result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link DatiSintesiIstanza }
     *     
     */
    public DatiSintesiIstanza getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link DatiSintesiIstanza }
     *     
     */
    public void setResult(DatiSintesiIstanza value) {
        this.result = value;
    }
}
