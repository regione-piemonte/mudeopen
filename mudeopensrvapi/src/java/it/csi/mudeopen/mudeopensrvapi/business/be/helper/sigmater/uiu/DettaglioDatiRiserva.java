/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per DettaglioDatiRiserva complex type.
 * 
 * 
 * &lt;complexType name="DettaglioDatiRiserva"&gt;
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="partitaIscrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioDatiRiserva", propOrder = {
    "codice",
    "dataAggiornamento",
    "descrizione",
    "partitaIscrizione"
})
public class DettaglioDatiRiserva {

    @XmlElement(required = true, nillable = true)
    protected String codice;
    @XmlElement(required = true, nillable = true)
    protected String dataAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String descrizione;
    @XmlElement(required = true, nillable = true)
    protected String partitaIscrizione;

    /**
     * Recupera il valore della proprieta codice.
     * 
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprieta codice.
     * 
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

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
     * Recupera il valore della proprieta descrizione.
     * 
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprieta descrizione.
     * 
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprieta partitaIscrizione.
     * 
     *     
     */
    public String getPartitaIscrizione() {
        return partitaIscrizione;
    }

    /**
     * Imposta il valore della proprieta partitaIscrizione.
     * 
     *     
     */
    public void setPartitaIscrizione(String value) {
        this.partitaIscrizione = value;
    }

}
