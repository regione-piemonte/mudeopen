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
 * <p>Java class for cronologiaStatoIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="cronologiaStatoIstanza"&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataVariazioneStato" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cronologiaStatoIstanza", propOrder = {
    "numeroIstanza",
    "stato",
    "dataVariazioneStato"
})
public class CronologiaStatoIstanza {
    @XmlElement(required = true)
    protected String numeroIstanza;
    @XmlElement(required = true)
    protected String stato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVariazioneStato;
    /**
     * Gets the value of the numeroIstanza property.
     * 
     *     
     */
    public String getNumeroIstanza() {
        return numeroIstanza;
    }
    /**
     * Sets the value of the numeroIstanza property.
     * 
     *     
     */
    public void setNumeroIstanza(String value) {
        this.numeroIstanza = value;
    }
    /**
     * Gets the value of the stato property.
     * 
     *     
     */
    public String getStato() {
        return stato;
    }
    /**
     * Sets the value of the stato property.
     * 
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }
    /**
     * Gets the value of the dataVariazioneStato property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVariazioneStato() {
        return dataVariazioneStato;
    }
    /**
     * Sets the value of the dataVariazioneStato property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVariazioneStato(XMLGregorianCalendar value) {
        this.dataVariazioneStato = value;
    }
}
