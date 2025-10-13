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
 * <p>Java class for pratica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="pratica"&gt;
 *         &lt;element name="numeroPratica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pratica", propOrder = {
    "numeroPratica",
    "comune",
    "anno"
})
public class Pratica {
    @XmlElement(required = true)
    protected String numeroPratica;
    @XmlElement(required = true)
    protected String comune;
    protected int anno;
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
     * Gets the value of the comune property.
     * 
     *     
     */
    public String getComune() {
        return comune;
    }
    /**
     * Sets the value of the comune property.
     * 
     *     
     */
    public void setComune(String value) {
        this.comune = value;
    }
    /**
     * Gets the value of the anno property.
     * 
     */
    public int getAnno() {
        return anno;
    }
    /**
     * Sets the value of the anno property.
     * 
     */
    public void setAnno(int value) {
        this.anno = value;
    }
}
