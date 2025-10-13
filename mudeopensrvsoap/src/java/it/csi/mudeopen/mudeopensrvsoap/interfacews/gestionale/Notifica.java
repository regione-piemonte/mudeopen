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
 * <p>Java class for notifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="notifica"&gt;
 *         &lt;element name="mittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cognomeDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nomeDestinatario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dettaglioNotifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="allegati" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="identificativoNotifica" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notifica", propOrder = {
    "mittente",
    "cognomeDestinatario",
    "nomeDestinatario",
    "oggetto",
    "testo",
    "dettaglioNotifica",
    "allegati",
    "identificativoNotifica"
})
public class Notifica {
    protected String mittente;
    protected String cognomeDestinatario;
    @XmlElement(required = true)
    protected String nomeDestinatario;
    protected String oggetto;
    protected String testo;
    protected String dettaglioNotifica;
    protected Boolean allegati;
    protected long identificativoNotifica;
    /**
     * Gets the value of the mittente property.
     * 
     *     
     */
    public String getMittente() {
        return mittente;
    }
    /**
     * Sets the value of the mittente property.
     * 
     *     
     */
    public void setMittente(String value) {
        this.mittente = value;
    }
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
     * Gets the value of the oggetto property.
     * 
     *     
     */
    public String getOggetto() {
        return oggetto;
    }
    /**
     * Sets the value of the oggetto property.
     * 
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }
    /**
     * Gets the value of the testo property.
     * 
     *     
     */
    public String getTesto() {
        return testo;
    }
    /**
     * Sets the value of the testo property.
     * 
     *     
     */
    public void setTesto(String value) {
        this.testo = value;
    }
    /**
     * Gets the value of the dettaglioNotifica property.
     * 
     *     
     */
    public String getDettaglioNotifica() {
        return dettaglioNotifica;
    }
    /**
     * Sets the value of the dettaglioNotifica property.
     * 
     *     
     */
    public void setDettaglioNotifica(String value) {
        this.dettaglioNotifica = value;
    }
    /**
     * Gets the value of the allegati property.
     * 
     *     {@link Boolean }
     *     
     */
    public Boolean isAllegati() {
        return allegati;
    }
    /**
     * Sets the value of the allegati property.
     * 
     *     {@link Boolean }
     *     
     */
    public void setAllegati(Boolean value) {
        this.allegati = value;
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
