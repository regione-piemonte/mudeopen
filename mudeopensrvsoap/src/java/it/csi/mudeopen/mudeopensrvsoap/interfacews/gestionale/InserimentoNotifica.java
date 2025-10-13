/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for inserimentoNotifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="inserimentoNotifica"&gt;
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mittente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoNotifica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeDocumento" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserimentoNotifica", propOrder = {
    "numeroIstanza",
    "mittente",
    "tipoNotifica",
    "oggetto",
    "testo",
    "nomeDocumento"
})
public class InserimentoNotifica {
    @XmlElement(required = true)
    protected String numeroIstanza;
    @XmlElement(required = true)
    protected String mittente;
    @XmlElement(required = true)
    protected String tipoNotifica;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected String testo;
    @XmlElement(nillable = true)
    protected List<String> nomeDocumento;
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
     * Gets the value of the nomeDocumento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nomeDocumento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getNomeDocumento().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNomeDocumento() {
        if (nomeDocumento == null) {
            nomeDocumento = new ArrayList<String>();
        }
        return this.nomeDocumento;
    }
}
