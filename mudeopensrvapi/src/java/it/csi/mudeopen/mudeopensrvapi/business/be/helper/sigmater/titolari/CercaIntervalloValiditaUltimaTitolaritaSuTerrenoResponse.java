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
 *         &lt;element name="cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn" type="{urn:sigalfsrvTitolari}IntervalloValiditaTitolarita" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn"
})
@XmlRootElement(name = "cercaIntervalloValiditaUltimaTitolaritaSuTerrenoResponse")
public class CercaIntervalloValiditaUltimaTitolaritaSuTerrenoResponse {

    protected IntervalloValiditaTitolarita cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn;

    /**
     * Recupera il valore della proprieta cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn.
     * 
     *     {@link IntervalloValiditaTitolarita }
     *     
     */
    public IntervalloValiditaTitolarita getCercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn() {
        return cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn;
    }

    /**
     * Imposta il valore della proprieta cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn.
     * 
     *     {@link IntervalloValiditaTitolarita }
     *     
     */
    public void setCercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn(IntervalloValiditaTitolarita value) {
        this.cercaIntervalloValiditaUltimaTitolaritaSuTerrenoReturn = value;
    }

}
