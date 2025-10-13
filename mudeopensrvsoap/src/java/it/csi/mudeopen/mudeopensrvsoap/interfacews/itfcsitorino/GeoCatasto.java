/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for geoCatasto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="geoCatasto"&gt;
 *         &lt;element name="foglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="particella" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="subalterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="flTipoCatasto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="flPersonalizzato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="chiaveCarotaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descFonteCatasto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geoCatasto", propOrder = {
    "foglio",
    "particella",
    "subalterno",
    "flTipoCatasto",
    "flPersonalizzato",
    "chiaveCarotaggio",
    "descFonteCatasto"
})
public class GeoCatasto {
    protected String foglio;
    protected String particella;
    protected String subalterno;
    protected String flTipoCatasto;
    protected String flPersonalizzato;
    protected String chiaveCarotaggio;
    protected String descFonteCatasto;
    /**
     * Gets the value of the foglio property.
     * 
     *     
     */
    public String getFoglio() {
        return foglio;
    }
    /**
     * Sets the value of the foglio property.
     * 
     *     
     */
    public void setFoglio(String value) {
        this.foglio = value;
    }
    /**
     * Gets the value of the particella property.
     * 
     *     
     */
    public String getParticella() {
        return particella;
    }
    /**
     * Sets the value of the particella property.
     * 
     *     
     */
    public void setParticella(String value) {
        this.particella = value;
    }
    /**
     * Gets the value of the subalterno property.
     * 
     *     
     */
    public String getSubalterno() {
        return subalterno;
    }
    /**
     * Sets the value of the subalterno property.
     * 
     *     
     */
    public void setSubalterno(String value) {
        this.subalterno = value;
    }
    /**
     * Gets the value of the flTipoCatasto property.
     * 
     *     
     */
    public String getFlTipoCatasto() {
        return flTipoCatasto;
    }
    /**
     * Sets the value of the flTipoCatasto property.
     * 
     *     
     */
    public void setFlTipoCatasto(String value) {
        this.flTipoCatasto = value;
    }
    /**
     * Gets the value of the flPersonalizzato property.
     * 
     *     
     */
    public String getFlPersonalizzato() {
        return flPersonalizzato;
    }
    /**
     * Sets the value of the flPersonalizzato property.
     * 
     *     
     */
    public void setFlPersonalizzato(String value) {
        this.flPersonalizzato = value;
    }
    /**
     * Gets the value of the chiaveCarotaggio property.
     * 
     *     
     */
    public String getChiaveCarotaggio() {
        return chiaveCarotaggio;
    }
    /**
     * Sets the value of the chiaveCarotaggio property.
     * 
     *     
     */
    public void setChiaveCarotaggio(String value) {
        this.chiaveCarotaggio = value;
    }
    /**
     * Gets the value of the descFonteCatasto property.
     * 
     *     
     */
    public String getDescFonteCatasto() {
        return descFonteCatasto;
    }
    /**
     * Sets the value of the descFonteCatasto property.
     * 
     *     
     */
    public void setDescFonteCatasto(String value) {
        this.descFonteCatasto = value;
    }
}
