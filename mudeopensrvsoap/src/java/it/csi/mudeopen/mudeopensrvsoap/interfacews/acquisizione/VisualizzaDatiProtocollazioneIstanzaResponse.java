/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for visualizzaDatiProtocollazioneIstanzaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="visualizzaDatiProtocollazioneIstanzaResponse"&gt;
 *         &lt;element name="result" type="{http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/}protocollazioneIstanzaExt" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visualizzaDatiProtocollazioneIstanzaResponse", propOrder = {
    "result"
})
public class VisualizzaDatiProtocollazioneIstanzaResponse {
    protected ProtocollazioneIstanzaExt result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link ProtocollazioneIstanzaExt }
     *     
     */
    public ProtocollazioneIstanzaExt getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link ProtocollazioneIstanzaExt }
     *     
     */
    public void setResult(ProtocollazioneIstanzaExt value) {
        this.result = value;
    }
}
