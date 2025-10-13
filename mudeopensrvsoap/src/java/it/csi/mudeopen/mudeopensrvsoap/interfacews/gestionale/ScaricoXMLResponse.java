/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for scaricoXMLResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="scaricoXMLResponse"&gt;
 *         &lt;element name="result" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}datiXML" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaricoXMLResponse", propOrder = {
    "result"
})
public class ScaricoXMLResponse {
    protected DatiXML result;
    /**
     * Gets the value of the result property.
     * 
     *     {@link DatiXML }
     *     
     */
    public DatiXML getResult() {
        return result;
    }
    /**
     * Sets the value of the result property.
     * 
     *     {@link DatiXML }
     *     
     */
    public void setResult(DatiXML value) {
        this.result = value;
    }
}
