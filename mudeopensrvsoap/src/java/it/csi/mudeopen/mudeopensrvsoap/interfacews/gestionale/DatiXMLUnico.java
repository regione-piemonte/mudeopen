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
 * <p>Java class for datiXMLUnico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="datiXMLUnico"&gt;
 *         &lt;element name="tracciatoXMLUnico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codiceVersioneUnico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiXMLUnico", propOrder = {
    "tracciatoXMLUnico",
    "codiceVersioneUnico"
})
public class DatiXMLUnico {
    @XmlElement(required = true)
    protected String tracciatoXMLUnico;
    @XmlElement(required = true)
    protected String codiceVersioneUnico;
    /**
     * Gets the value of the tracciatoXMLUnico property.
     * 
     *     
     */
    public String getTracciatoXMLUnico() {
        return tracciatoXMLUnico;
    }
    /**
     * Sets the value of the tracciatoXMLUnico property.
     * 
     *     
     */
    public void setTracciatoXMLUnico(String value) {
        this.tracciatoXMLUnico = value;
    }
    /**
     * Gets the value of the codiceVersioneUnico property.
     * 
     *     
     */
    public String getCodiceVersioneUnico() {
        return codiceVersioneUnico;
    }
    /**
     * Sets the value of the codiceVersioneUnico property.
     * 
     *     
     */
    public void setCodiceVersioneUnico(String value) {
        this.codiceVersioneUnico = value;
    }
}
