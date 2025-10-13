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
 * <p>Java class for tracciatoXML complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="tracciatoXML"&gt;
 *         &lt;element name="codTipoTracciato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="xmlPresente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tracciatoXML", propOrder = {
    "codTipoTracciato",
    "descrizione",
    "xmlPresente"
})
public class TracciatoXML {
    @XmlElement(required = true)
    protected String codTipoTracciato;
    @XmlElement(required = true)
    protected String descrizione;
    protected Boolean xmlPresente;
    /**
     * Gets the value of the codTipoTracciato property.
     * 
     *     
     */
    public String getCodTipoTracciato() {
        return codTipoTracciato;
    }
    /**
     * Sets the value of the codTipoTracciato property.
     * 
     *     
     */
    public void setCodTipoTracciato(String value) {
        this.codTipoTracciato = value;
    }
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
     * Gets the value of the xmlPresente property.
     * 
     *     {@link Boolean }
     *     
     */
    public Boolean isXmlPresente() {
        return xmlPresente;
    }
    /**
     * Sets the value of the xmlPresente property.
     * 
     *     {@link Boolean }
     *     
     */
    public void setXmlPresente(Boolean value) {
        this.xmlPresente = value;
    }
}
