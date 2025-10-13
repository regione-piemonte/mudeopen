/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for scaricoXMLIstanzaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="scaricoXMLIstanzaResponse"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}datiXMLIstanza" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaricoXMLIstanzaResponse", propOrder = {
    "result"
})
public class ScaricoXMLIstanzaResponse {
    protected DatiXMLIstanza result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link DatiXMLIstanza }
     *     
     */
    public DatiXMLIstanza getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link DatiXMLIstanza }
     *     
     */
    public void setResult(DatiXMLIstanza value) {
        this.result = value;
    }
}
