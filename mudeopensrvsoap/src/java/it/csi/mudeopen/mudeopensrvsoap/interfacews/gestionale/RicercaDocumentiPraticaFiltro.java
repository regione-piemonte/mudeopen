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
 * <p>Java class for ricercaDocumentiPraticaFiltro complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaDocumentiPraticaFiltro"&gt;
 *         &lt;element name="codIstat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="annoPratica" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaDocumentiPraticaFiltro", propOrder = {
    "codIstat",
    "numeroPratica",
    "annoPratica"
})
public class RicercaDocumentiPraticaFiltro {
    @XmlElement(required = true)
    protected String codIstat;
    protected String numeroPratica;
    protected Integer annoPratica;
    /**
     * Gets the value of the codIstat property.
     * 
     *     
     */
    public String getCodIstat() {
        return codIstat;
    }
    /**
     * Sets the value of the codIstat property.
     * 
     *     
     */
    public void setCodIstat(String value) {
        this.codIstat = value;
    }
    /**
     * Gets the value of the numeroPratica property.
     * 
     *     
     */
    public String getNumeroPratica() {
        return numeroPratica;
    }
    /**
     * Sets the value of the numeroPratica property.
     * 
     *     
     */
    public void setNumeroPratica(String value) {
        this.numeroPratica = value;
    }
    /**
     * Gets the value of the annoPratica property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getAnnoPratica() {
        return annoPratica;
    }
    /**
     * Sets the value of the annoPratica property.
     * 
     *     {@link Integer }
     *     
     */
    public void setAnnoPratica(Integer value) {
        this.annoPratica = value;
    }
}
