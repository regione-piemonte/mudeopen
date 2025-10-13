/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for scaricoXMLUnicoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="scaricoXMLUnicoResponse"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}datiXMLUnico" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaricoXMLUnicoResponse", propOrder = {
    "result"
})
public class ScaricoXMLUnicoResponse {
    protected DatiXMLUnico result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link DatiXMLUnico }
     *     
     */
    public DatiXMLUnico getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link DatiXMLUnico }
     *     
     */
    public void setResult(DatiXMLUnico value) {
        this.result = value;
    }
}
