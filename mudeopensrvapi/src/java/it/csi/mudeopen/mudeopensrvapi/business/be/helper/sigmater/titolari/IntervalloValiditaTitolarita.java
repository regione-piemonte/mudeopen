/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per IntervalloValiditaTitolarita complex type.
 * 
 * 
 * &lt;complexType name="IntervalloValiditaTitolarita"&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataFineValidita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntervalloValiditaTitolarita", propOrder = {
    "dataAggiornamento",
    "dataFineValidita",
    "dataInizioValidita"
})
public class IntervalloValiditaTitolarita {

    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String dataFineValidita;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioValidita;

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
     * Recupera il valore della proprieta dataFineValidita.
     * 
     *     
     */
    public String getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Imposta il valore della proprieta dataFineValidita.
     * 
     *     
     */
    public void setDataFineValidita(String value) {
        this.dataFineValidita = value;
    }

    /**
     * Recupera il valore della proprieta dataInizioValidita.
     * 
     *     
     */
    public String getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprieta dataInizioValidita.
     * 
     *     
     */
    public void setDataInizioValidita(String value) {
        this.dataInizioValidita = value;
    }

}
