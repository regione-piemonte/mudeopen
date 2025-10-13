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
 * <p>Java class for datiXML complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="datiXML"&gt;
 *         &lt;element name="tracciatoXML" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="versione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneTipoTracciato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiXML", propOrder = {
    "tracciatoXML",
    "versione",
    "descrizioneTipoTracciato"
})
public class DatiXML {
    @XmlElement(required = true)
    protected String tracciatoXML;
    @XmlElement(required = true)
    protected String versione;
    @XmlElement(required = true)
    protected String descrizioneTipoTracciato;
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
    /**
     * Gets the value of the versione property.
     * 
     *     
     */
    public String getVersione() {
        return versione;
    }
    /**
     * Sets the value of the versione property.
     * 
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
    }
    /**
     * Gets the value of the descrizioneTipoTracciato property.
     * 
     *     
     */
    public String getDescrizioneTipoTracciato() {
        return descrizioneTipoTracciato;
    }
    /**
     * Sets the value of the descrizioneTipoTracciato property.
     * 
     *     
     */
    public void setDescrizioneTipoTracciato(String value) {
        this.descrizioneTipoTracciato = value;
    }
}
