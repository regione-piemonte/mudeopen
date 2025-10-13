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
 * <p>Classe Java per DettaglioDatiDeduzione complex type.
 * 
 * 
 * &lt;complexType name="DettaglioDatiDeduzione"&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="simbolo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioDatiDeduzione", propOrder = {
    "dataAggiornamento",
    "importo",
    "simbolo"
})
public class DettaglioDatiDeduzione {

    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    protected double importo;
    @XmlElement(required = true, nillable = true)
    protected String simbolo;

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
     * Recupera il valore della proprieta importo.
     * 
     */
    public double getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprieta importo.
     * 
     */
    public void setImporto(double value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprieta simbolo.
     * 
     *     
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Imposta il valore della proprieta simbolo.
     * 
     *     
     */
    public void setSimbolo(String value) {
        this.simbolo = value;
    }

}
