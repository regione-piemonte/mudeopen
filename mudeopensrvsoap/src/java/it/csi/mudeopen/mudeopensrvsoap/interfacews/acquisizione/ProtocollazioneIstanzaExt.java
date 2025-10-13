/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for protocollazioneIstanzaExt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="protocollazioneIstanzaExt"&gt;
 *         &lt;element name="dataVariazioneStato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oggettoNotifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="messaggioNotifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocollazioneIstanzaExt", propOrder = {
    "dataVariazioneStato",
    "oggettoNotifica",
    "messaggioNotifica",
    "numeroProtocollo",
    "dataProtocollo"
})
public class ProtocollazioneIstanzaExt {
    protected String dataVariazioneStato;
    protected String oggettoNotifica;
    protected String messaggioNotifica;
    protected String numeroProtocollo;
    protected String dataProtocollo;
    /**
     * Gets the value of the dataVariazioneStato property.
     * 
     *     
     */
    public String getDataVariazioneStato() {
        return dataVariazioneStato;
    }
    /**
     * Sets the value of the dataVariazioneStato property.
     * 
     *     
     */
    public void setDataVariazioneStato(String value) {
        this.dataVariazioneStato = value;
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
     * Gets the value of the messaggioNotifica property.
     * 
     *     
     */
    public String getMessaggioNotifica() {
        return messaggioNotifica;
    }
    /**
     * Sets the value of the messaggioNotifica property.
     * 
     *     
     */
    public void setMessaggioNotifica(String value) {
        this.messaggioNotifica = value;
    }
    /**
     * Gets the value of the numeroProtocollo property.
     * 
     *     
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }
    /**
     * Sets the value of the numeroProtocollo property.
     * 
     *     
     */
    public void setNumeroProtocollo(String value) {
        this.numeroProtocollo = value;
    }
    /**
     * Gets the value of the dataProtocollo property.
     * 
     *     
     */
    public String getDataProtocollo() {
        return dataProtocollo;
    }
    /**
     * Sets the value of the dataProtocollo property.
     * 
     *     
     */
    public void setDataProtocollo(String value) {
        this.dataProtocollo = value;
    }
}
