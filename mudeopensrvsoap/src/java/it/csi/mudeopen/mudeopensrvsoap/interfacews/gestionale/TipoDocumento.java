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
 * <p>Java class for tipoDocumento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="tipoDocumento"&gt;
 *         &lt;element name="codiceTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDocumento", propOrder = {
    "codiceTipoDocumento",
    "descrizioneTipoDocumento"
})
public class TipoDocumento {
    @XmlElement(required = true)
    protected String codiceTipoDocumento;
    @XmlElement(required = true)
    protected String descrizioneTipoDocumento;
    /**
     * Gets the value of the codiceTipoDocumento property.
     * 
     *     
     */
    public String getCodiceTipoDocumento() {
        return codiceTipoDocumento;
    }
    /**
     * Sets the value of the codiceTipoDocumento property.
     * 
     *     
     */
    public void setCodiceTipoDocumento(String value) {
        this.codiceTipoDocumento = value;
    }
    /**
     * Gets the value of the descrizioneTipoDocumento property.
     * 
     *     
     */
    public String getDescrizioneTipoDocumento() {
        return descrizioneTipoDocumento;
    }
    /**
     * Sets the value of the descrizioneTipoDocumento property.
     * 
     *     
     */
    public void setDescrizioneTipoDocumento(String value) {
        this.descrizioneTipoDocumento = value;
    }
}
