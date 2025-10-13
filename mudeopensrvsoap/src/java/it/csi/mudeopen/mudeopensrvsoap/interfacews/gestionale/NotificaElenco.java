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
 * <p>Java class for notificaElenco complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="notificaElenco"&gt;
 *         &lt;element name="cognomeDestinatario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeDestinatario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataNotifica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="tipoNotifica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="statoNotifica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggettoNotifica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataLettura" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="esistonoAllegati" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="identificativoNotifica" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificaElenco", propOrder = {
    "cognomeDestinatario",
    "nomeDestinatario",
    "dataNotifica",
    "tipoNotifica",
    "statoNotifica",
    "oggettoNotifica",
    "dataLettura",
    "esistonoAllegati",
    "identificativoNotifica"
})
public class NotificaElenco {
    @XmlElement(required = true)
    protected String cognomeDestinatario;
    @XmlElement(required = true)
    protected String nomeDestinatario;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNotifica;
    @XmlElement(required = true)
    protected String tipoNotifica;
    @XmlElement(required = true)
    protected String statoNotifica;
    @XmlElement(required = true)
    protected String oggettoNotifica;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLettura;
    protected boolean esistonoAllegati;
    protected long identificativoNotifica;
    /**
     * Gets the value of the cognomeDestinatario property.
     * 
     *     
     */
    public String getCognomeDestinatario() {
        return cognomeDestinatario;
    }
    /**
     * Sets the value of the cognomeDestinatario property.
     * 
     *     
     */
    public void setCognomeDestinatario(String value) {
        this.cognomeDestinatario = value;
    }
    /**
     * Gets the value of the nomeDestinatario property.
     * 
     *     
     */
    public String getNomeDestinatario() {
        return nomeDestinatario;
    }
    /**
     * Sets the value of the nomeDestinatario property.
     * 
     *     
     */
    public void setNomeDestinatario(String value) {
        this.nomeDestinatario = value;
    }
    /**
     * Gets the value of the dataNotifica property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNotifica() {
        return dataNotifica;
    }
    /**
     * Sets the value of the dataNotifica property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNotifica(XMLGregorianCalendar value) {
        this.dataNotifica = value;
    }
    /**
     * Gets the value of the tipoNotifica property.
     * 
     *     
     */
    public String getTipoNotifica() {
        return tipoNotifica;
    }
    /**
     * Sets the value of the tipoNotifica property.
     * 
     *     
     */
    public void setTipoNotifica(String value) {
        this.tipoNotifica = value;
    }
    /**
     * Gets the value of the statoNotifica property.
     * 
     *     
     */
    public String getStatoNotifica() {
        return statoNotifica;
    }
    /**
     * Sets the value of the statoNotifica property.
     * 
     *     
     */
    public void setStatoNotifica(String value) {
        this.statoNotifica = value;
    }
    /**
     * Gets the value of the oggettoNotifica property.
     * 
     *     
     */
    public String getOggettoNotifica() {
        return oggettoNotifica;
    }
    /**
     * Sets the value of the oggettoNotifica property.
     * 
     *     
     */
    public void setOggettoNotifica(String value) {
        this.oggettoNotifica = value;
    }
    /**
     * Gets the value of the dataLettura property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataLettura() {
        return dataLettura;
    }
    /**
     * Sets the value of the dataLettura property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataLettura(XMLGregorianCalendar value) {
        this.dataLettura = value;
    }
    /**
     * Gets the value of the esistonoAllegati property.
     * 
     */
    public boolean isEsistonoAllegati() {
        return esistonoAllegati;
    }
    /**
     * Sets the value of the esistonoAllegati property.
     * 
     */
    public void setEsistonoAllegati(boolean value) {
        this.esistonoAllegati = value;
    }
    /**
     * Gets the value of the identificativoNotifica property.
     * 
     */
    public long getIdentificativoNotifica() {
        return identificativoNotifica;
    }
    /**
     * Sets the value of the identificativoNotifica property.
     * 
     */
    public void setIdentificativoNotifica(long value) {
        this.identificativoNotifica = value;
    }
}
