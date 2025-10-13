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
 * <p>Java class for tipoNotifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="tipoNotifica"&gt;
 *         &lt;element name="codiceTipoNotifica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneTipoNotifica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoNotifica", propOrder = {
    "codiceTipoNotifica",
    "descrizioneTipoNotifica"
})
public class TipoNotifica {
    @XmlElement(required = true)
    protected String codiceTipoNotifica;
    @XmlElement(required = true)
    protected String descrizioneTipoNotifica;
    /**
     * Gets the value of the codiceTipoNotifica property.
     * 
     *     
     */
    public String getCodiceTipoNotifica() {
        return codiceTipoNotifica;
    }
    /**
     * Sets the value of the codiceTipoNotifica property.
     * 
     *     
     */
    public void setCodiceTipoNotifica(String value) {
        this.codiceTipoNotifica = value;
    }
    /**
     * Gets the value of the descrizioneTipoNotifica property.
     * 
     *     
     */
    public String getDescrizioneTipoNotifica() {
        return descrizioneTipoNotifica;
    }
    /**
     * Sets the value of the descrizioneTipoNotifica property.
     * 
     *     
     */
    public void setDescrizioneTipoNotifica(String value) {
        this.descrizioneTipoNotifica = value;
    }
}
