/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaPaginataIstanze02Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaPaginataIstanze02Response"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}esitoRicercaPaginata02" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPaginataIstanze02Response", propOrder = {
    "result"
})
public class RicercaPaginataIstanze02Response {
    protected EsitoRicercaPaginata02 result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link EsitoRicercaPaginata02 }
     *     
     */
    public EsitoRicercaPaginata02 getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link EsitoRicercaPaginata02 }
     *     
     */
    public void setResult(EsitoRicercaPaginata02 value) {
        this.result = value;
    }
}
