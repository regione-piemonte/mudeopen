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
 * <p>Classe Java per IdentitaPersonaGiuridica complex type.
 * 
 * 
 * &lt;complexType name="IdentitaPersonaGiuridica"&gt;
 *         &lt;element name="codBelfioreComuneDiRicerca" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codBelfioreSede" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentitaPersonaGiuridica", propOrder = {
    "codBelfioreComuneDiRicerca",
    "codBelfioreSede",
    "denominazione",
    "partitaIva"
})
public class IdentitaPersonaGiuridica {

    @XmlElement(required = true, nillable = true)
    protected String codBelfioreComuneDiRicerca;
    @XmlElement(required = true, nillable = true)
    protected String codBelfioreSede;
    @XmlElement(required = true, nillable = true)
    protected String denominazione;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;

    /**
     * Recupera il valore della proprieta codBelfioreComuneDiRicerca.
     * 
     *     
     */
    public String getCodBelfioreComuneDiRicerca() {
        return codBelfioreComuneDiRicerca;
    }

    /**
     * Imposta il valore della proprieta codBelfioreComuneDiRicerca.
     * 
     *     
     */
    public void setCodBelfioreComuneDiRicerca(String value) {
        this.codBelfioreComuneDiRicerca = value;
    }

    /**
     * Recupera il valore della proprieta codBelfioreSede.
     * 
     *     
     */
    public String getCodBelfioreSede() {
        return codBelfioreSede;
    }

    /**
     * Imposta il valore della proprieta codBelfioreSede.
     * 
     *     
     */
    public void setCodBelfioreSede(String value) {
        this.codBelfioreSede = value;
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
