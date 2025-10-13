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
 * <p>Java class for ricercaStatiAmmessi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="ricercaStatiAmmessi"&gt;
 *         &lt;element name="statoIniziale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaStatiAmmessi", propOrder = {
    "statoIniziale"
})
public class RicercaStatiAmmessi {
    @XmlElement(required = true)
    protected String statoIniziale;
    /**
     * Gets the value of the statoIniziale property.
     * 
     *     
     */
    public String getStatoIniziale() {
        return statoIniziale;
    }
    /**
     * Sets the value of the statoIniziale property.
     * 
     *     
     */
    public void setStatoIniziale(String value) {
        this.statoIniziale = value;
    }
}
