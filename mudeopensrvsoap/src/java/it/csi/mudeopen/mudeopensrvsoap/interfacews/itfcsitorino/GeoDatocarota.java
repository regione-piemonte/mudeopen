/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * <p>Java class for geoDatocarota complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="geoDatocarota"&gt;
 *         &lt;element name="tipoCarotaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idFonte" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="descFonte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idLivello" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="dataGeoriferimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="descLivello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geoDatocarota", propOrder = {
    "tipoCarotaggio",
    "idFonte",
    "descFonte",
    "idLivello",
    "dataGeoriferimento",
    "descLivello",
    "valore"
})
public class GeoDatocarota {
    protected String tipoCarotaggio;
    protected Integer idFonte;
    protected String descFonte;
    protected Integer idLivello;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataGeoriferimento;
    protected String descLivello;
    protected String valore;
    /**
     * Gets the value of the tipoCarotaggio property.
     * 
     *     
     */
    public String getTipoCarotaggio() {
        return tipoCarotaggio;
    }
    /**
     * Sets the value of the tipoCarotaggio property.
     * 
     *     
     */
    public void setTipoCarotaggio(String value) {
        this.tipoCarotaggio = value;
    }
    /**
     * Gets the value of the idFonte property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getIdFonte() {
        return idFonte;
    }
    /**
     * Sets the value of the idFonte property.
     * 
     *     {@link Integer }
     *     
     */
    public void setIdFonte(Integer value) {
        this.idFonte = value;
    }
    /**
     * Gets the value of the descFonte property.
     * 
     *     
     */
    public String getDescFonte() {
        return descFonte;
    }
    /**
     * Sets the value of the descFonte property.
     * 
     *     
     */
    public void setDescFonte(String value) {
        this.descFonte = value;
    }
    /**
     * Gets the value of the idLivello property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getIdLivello() {
        return idLivello;
    }
    /**
     * Sets the value of the idLivello property.
     * 
     *     {@link Integer }
     *     
     */
    public void setIdLivello(Integer value) {
        this.idLivello = value;
    }
    /**
     * Gets the value of the dataGeoriferimento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataGeoriferimento() {
        return dataGeoriferimento;
    }
    /**
     * Sets the value of the dataGeoriferimento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataGeoriferimento(XMLGregorianCalendar value) {
        this.dataGeoriferimento = value;
    }
    /**
     * Gets the value of the descLivello property.
     * 
     *     
     */
    public String getDescLivello() {
        return descLivello;
    }
    /**
     * Sets the value of the descLivello property.
     * 
     *     
     */
    public void setDescLivello(String value) {
        this.descLivello = value;
    }
    /**
     * Gets the value of the valore property.
     * 
     *     
     */
    public String getValore() {
        return valore;
    }
    /**
     * Sets the value of the valore property.
     * 
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }
}
