/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaPaginataIstanze03Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaPaginataIstanze03Response"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}esitoRicercaPaginata03" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPaginataIstanze03Response", propOrder = {
    "result"
})
public class RicercaPaginataIstanze03Response {
    protected EsitoRicercaPaginata03 result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link EsitoRicercaPaginata03 }
     *     
     */
    public EsitoRicercaPaginata03 getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link EsitoRicercaPaginata03 }
     *     
     */
    public void setResult(EsitoRicercaPaginata03 value) {
        this.result = value;
    }
}
