/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for ricercaPaginataIstanzeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaPaginataIstanzeResponse"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}esitoRicercaPaginata" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPaginataIstanzeResponse", propOrder = {
    "result"
})
public class RicercaPaginataIstanzeResponse {
    protected EsitoRicercaPaginata result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link EsitoRicercaPaginata }
     *     
     */
    public EsitoRicercaPaginata getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link EsitoRicercaPaginata }
     *     
     */
    public void setResult(EsitoRicercaPaginata value) {
        this.result = value;
    }
}
