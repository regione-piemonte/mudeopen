/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for traccXML complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="traccXML"&gt;
 *         &lt;element name="versioneTracciato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tracciatoXML" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "traccXML", propOrder = {
    "versioneTracciato",
    "tracciatoXML"
})
public class TraccXML {
    @XmlElement(required = true)
    protected String versioneTracciato;
    @XmlElement(required = true)
    protected String tracciatoXML;
    /**
     * Gets the value of the versioneTracciato property.
     * 
     *     
     */
    public String getVersioneTracciato() {
        return versioneTracciato;
    }
    /**
     * Sets the value of the versioneTracciato property.
     * 
     *     
     */
    public void setVersioneTracciato(String value) {
        this.versioneTracciato = value;
    }
    /**
     * Gets the value of the tracciatoXML property.
     * 
     *     
     */
    public String getTracciatoXML() {
        return tracciatoXML;
    }
    /**
     * Sets the value of the tracciatoXML property.
     * 
     *     
     */
    public void setTracciatoXML(String value) {
        this.tracciatoXML = value;
    }
}
