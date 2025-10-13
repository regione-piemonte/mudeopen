/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="in0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "in0",
    "in1"
})
@XmlRootElement(name = "isComuneAventeCodiceBelfioreAbilitatoSigmater")
public class IsComuneAventeCodiceBelfioreAbilitatoSigmater {

    protected String in0;
    protected String in1;

    /**
     * Recupera il valore della proprieta in0.
     * 
     *     
     */
    public String getIn0() {
        return in0;
    }

    /**
     * Imposta il valore della proprieta in0.
     * 
     *     
     */
    public void setIn0(String value) {
        this.in0 = value;
    }

    /**
     * Recupera il valore della proprieta in1.
     * 
     *     
     */
    public String getIn1() {
        return in1;
    }

    /**
     * Imposta il valore della proprieta in1.
     * 
     *     
     */
    public void setIn1(String value) {
        this.in1 = value;
    }

}
