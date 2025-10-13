/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for datiXMLIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="datiXMLIstanza"&gt;
 *         &lt;element name="xmlIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiXMLIstanza", propOrder = {
    "xmlIstanza"
})
public class DatiXMLIstanza {
    @XmlElement(required = true)
    protected String xmlIstanza;
    /**
     * Gets the value of the xmlIstanza property.
     * 
     *     
     */
    public String getXmlIstanza() {
        return xmlIstanza;
    }
    /**
     * Sets the value of the xmlIstanza property.
     * 
     *     
     */
    public void setXmlIstanza(String value) {
        this.xmlIstanza = value;
    }
}
