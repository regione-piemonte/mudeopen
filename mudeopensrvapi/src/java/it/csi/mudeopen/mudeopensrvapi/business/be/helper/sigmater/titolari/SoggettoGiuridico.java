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
 * <p>Classe Java per SoggettoGiuridico complex type.
 * 
 * 
 * &lt;complexType name="SoggettoGiuridico"&gt;
 *         &lt;element name="codComuneBelfioreSedeOperativa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codComuneBelfioreTitolarita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoGiuridico", propOrder = {
    "codComuneBelfioreSedeOperativa",
    "codComuneBelfioreTitolarita",
    "denominazione",
    "partitaIva"
})
public class SoggettoGiuridico {

    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfioreSedeOperativa;
    @XmlElement(required = true, nillable = true)
    protected String codComuneBelfioreTitolarita;
    @XmlElement(required = true, nillable = true)
    protected String denominazione;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;

    /**
     * Recupera il valore della proprieta codComuneBelfioreSedeOperativa.
     * 
     *     
     */
    public String getCodComuneBelfioreSedeOperativa() {
        return codComuneBelfioreSedeOperativa;
    }

    /**
     * Imposta il valore della proprieta codComuneBelfioreSedeOperativa.
     * 
     *     
     */
    public void setCodComuneBelfioreSedeOperativa(String value) {
        this.codComuneBelfioreSedeOperativa = value;
    }

    /**
     * Recupera il valore della proprieta codComuneBelfioreTitolarita.
     * 
     *     
     */
    public String getCodComuneBelfioreTitolarita() {
        return codComuneBelfioreTitolarita;
    }

    /**
     * Imposta il valore della proprieta codComuneBelfioreTitolarita.
     * 
     *     
     */
    public void setCodComuneBelfioreTitolarita(String value) {
        this.codComuneBelfioreTitolarita = value;
    }

    /**
     * Recupera il valore della proprieta denominazione.
     * 
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprieta denominazione.
     * 
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprieta partitaIva.
     * 
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Imposta il valore della proprieta partitaIva.
     * 
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

}
