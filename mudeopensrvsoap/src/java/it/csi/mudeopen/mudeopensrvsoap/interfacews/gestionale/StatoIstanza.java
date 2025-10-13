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
 * <p>Java class for statoIstanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="statoIstanza"&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggettoProposto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="messaggioProposto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statoIstanza", propOrder = {
    "descrizione",
    "oggettoProposto",
    "messaggioProposto",
    "codice"
})
public class StatoIstanza {
    @XmlElement(required = true)
    protected String descrizione;
    @XmlElement(required = true)
    protected String oggettoProposto;
    @XmlElement(required = true)
    protected String messaggioProposto;
    @XmlElement(required = true)
    protected String codice;
    /**
     * Gets the value of the descrizione property.
     * 
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }
    /**
     * Sets the value of the descrizione property.
     * 
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }
    /**
     * Gets the value of the oggettoProposto property.
     * 
     *     
     */
    public String getOggettoProposto() {
        return oggettoProposto;
    }
    /**
     * Sets the value of the oggettoProposto property.
     * 
     *     
     */
    public void setOggettoProposto(String value) {
        this.oggettoProposto = value;
    }
    /**
     * Gets the value of the messaggioProposto property.
     * 
     *     
     */
    public String getMessaggioProposto() {
        return messaggioProposto;
    }
    /**
     * Sets the value of the messaggioProposto property.
     * 
     *     
     */
    public void setMessaggioProposto(String value) {
        this.messaggioProposto = value;
    }
    /**
     * Gets the value of the codice property.
     * 
     *     
     */
    public String getCodice() {
        return codice;
    }
    /**
     * Sets the value of the codice property.
     * 
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }
}
