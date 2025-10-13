/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * <p>Java class for documentoPA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="documentoPA"&gt;
 *         &lt;element name="tipologia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataCaricamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="codiceTipologia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notificato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoPA", propOrder = {
    "tipologia",
    "nomeFile",
    "dataCaricamento",
    "codiceTipologia",
    "notificato",
    "identificativo"
})
public class DocumentoPA {
    @XmlElement(required = true)
    protected String tipologia;
    @XmlElement(required = true)
    protected String nomeFile;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCaricamento;
    protected String codiceTipologia;
    protected Boolean notificato;
    protected Long identificativo;
    /**
     * Gets the value of the tipologia property.
     * 
     *     
     */
    public String getTipologia() {
        return tipologia;
    }
    /**
     * Sets the value of the tipologia property.
     * 
     *     
     */
    public void setTipologia(String value) {
        this.tipologia = value;
    }
    /**
     * Gets the value of the nomeFile property.
     * 
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }
    /**
     * Sets the value of the nomeFile property.
     * 
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }
    /**
     * Gets the value of the dataCaricamento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCaricamento() {
        return dataCaricamento;
    }
    /**
     * Sets the value of the dataCaricamento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCaricamento(XMLGregorianCalendar value) {
        this.dataCaricamento = value;
    }
    /**
     * Gets the value of the codiceTipologia property.
     * 
     *     
     */
    public String getCodiceTipologia() {
        return codiceTipologia;
    }
    /**
     * Sets the value of the codiceTipologia property.
     * 
     *     
     */
    public void setCodiceTipologia(String value) {
        this.codiceTipologia = value;
    }
    /**
     * Gets the value of the notificato property.
     * 
     *     {@link Boolean }
     *     
     */
    public Boolean isNotificato() {
        return notificato;
    }
    /**
     * Sets the value of the notificato property.
     * 
     *     {@link Boolean }
     *     
     */
    public void setNotificato(Boolean value) {
        this.notificato = value;
    }
    /**
     * Gets the value of the identificativo property.
     * 
     *     {@link Long }
     *     
     */
    public Long getIdentificativo() {
        return identificativo;
    }
    /**
     * Sets the value of the identificativo property.
     * 
     *     {@link Long }
     *     
     */
    public void setIdentificativo(Long value) {
        this.identificativo = value;
    }
}
