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
 *         &lt;element name="cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn" type="{urn:sigalfsrvTitolari}IntervalloValiditaTitolarita" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn"
})
@XmlRootElement(name = "cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoResponse")
public class CercaIntervalloValiditaUltimaTitolaritaSuFabbricatoResponse {

    protected IntervalloValiditaTitolarita cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn;

    /**
     * Recupera il valore della proprieta cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn.
     * 
     *     {@link IntervalloValiditaTitolarita }
     *     
     */
    public IntervalloValiditaTitolarita getCercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn() {
        return cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn;
    }

    /**
     * Imposta il valore della proprieta cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn.
     * 
     *     {@link IntervalloValiditaTitolarita }
     *     
     */
    public void setCercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn(IntervalloValiditaTitolarita value) {
        this.cercaIntervalloValiditaUltimaTitolaritaSuFabbricatoReturn = value;
    }

}
