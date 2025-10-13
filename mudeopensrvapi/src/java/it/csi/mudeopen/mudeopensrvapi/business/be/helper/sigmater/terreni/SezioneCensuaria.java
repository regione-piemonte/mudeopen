/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per SezioneCensuaria complex type.
 * 
 * 
 * &lt;complexType name="SezioneCensuaria"&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrSezioneCensuaria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sezioneCensuaria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SezioneCensuaria", propOrder = {
    "dataAggiornamento",
    "descrSezioneCensuaria",
    "sezioneCensuaria"
})
public class SezioneCensuaria {

    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String descrSezioneCensuaria;
    @XmlElement(required = true, nillable = true)
    protected String sezioneCensuaria;

    /**
     * Recupera il valore della proprieta dataAggiornamento.
     * 
     *     
     */
    public String getDataAggiornamento() {
        return dataAggiornamento;
    }

    /**
     * Imposta il valore della proprieta dataAggiornamento.
     * 
     *     
     */
    public void setDataAggiornamento(String value) {
        this.dataAggiornamento = value;
    }

    /**
     * Recupera il valore della proprieta descrSezioneCensuaria.
     * 
     *     
     */
    public String getDescrSezioneCensuaria() {
        return descrSezioneCensuaria;
    }

    /**
     * Imposta il valore della proprieta descrSezioneCensuaria.
     * 
     *     
     */
    public void setDescrSezioneCensuaria(String value) {
        this.descrSezioneCensuaria = value;
    }

    /**
     * Recupera il valore della proprieta sezioneCensuaria.
     * 
     *     
     */
    public String getSezioneCensuaria() {
        return sezioneCensuaria;
    }

    /**
     * Imposta il valore della proprieta sezioneCensuaria.
     * 
     *     
     */
    public void setSezioneCensuaria(String value) {
        this.sezioneCensuaria = value;
    }

}
