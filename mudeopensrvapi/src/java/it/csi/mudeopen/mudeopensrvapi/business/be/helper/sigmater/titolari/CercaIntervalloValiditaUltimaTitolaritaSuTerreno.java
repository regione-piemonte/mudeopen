/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="in0" type="{urn:sigalfsrvTitolari}IdentitaPersonaFisica" minOccurs="0"/&gt;
 *         &lt;element name="in1" type="{urn:sigalfsrvTitolari}IdentificativoCatastaleTerreno" minOccurs="0"/&gt;
 *         &lt;element name="in2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "in0",
    "in1",
    "in2"
})
@XmlRootElement(name = "cercaIntervalloValiditaUltimaTitolaritaSuTerreno")
public class CercaIntervalloValiditaUltimaTitolaritaSuTerreno {

    protected IdentitaPersonaFisica in0;
    protected IdentificativoCatastaleTerreno in1;
    protected String in2;

    /**
     * Recupera il valore della proprieta in0.
     * 
     *     {@link IdentitaPersonaFisica }
     *     
     */
    public IdentitaPersonaFisica getIn0() {
        return in0;
    }

    /**
     * Imposta il valore della proprieta in0.
     * 
     *     {@link IdentitaPersonaFisica }
     *     
     */
    public void setIn0(IdentitaPersonaFisica value) {
        this.in0 = value;
    }

    /**
     * Recupera il valore della proprieta in1.
     * 
     *     {@link IdentificativoCatastaleTerreno }
     *     
     */
    public IdentificativoCatastaleTerreno getIn1() {
        return in1;
    }

    /**
     * Imposta il valore della proprieta in1.
     * 
     *     {@link IdentificativoCatastaleTerreno }
     *     
     */
    public void setIn1(IdentificativoCatastaleTerreno value) {
        this.in1 = value;
    }

    /**
     * Recupera il valore della proprieta in2.
     * 
     *     
     */
    public String getIn2() {
        return in2;
    }

    /**
     * Imposta il valore della proprieta in2.
     * 
     *     
     */
    public void setIn2(String value) {
        this.in2 = value;
    }

}
