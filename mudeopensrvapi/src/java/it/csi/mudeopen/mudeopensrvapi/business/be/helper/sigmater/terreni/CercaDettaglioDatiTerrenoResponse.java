/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="cercaDettaglioDatiTerrenoReturn" type="{urn:sigalfsrvTerreni}DettaglioDatiTerreno" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaDettaglioDatiTerrenoReturn"
})
@XmlRootElement(name = "cercaDettaglioDatiTerrenoResponse")
public class CercaDettaglioDatiTerrenoResponse {

    protected DettaglioDatiTerreno cercaDettaglioDatiTerrenoReturn;

    /**
     * Recupera il valore della proprieta cercaDettaglioDatiTerrenoReturn.
     * 
     *     {@link DettaglioDatiTerreno }
     *     
     */
    public DettaglioDatiTerreno getCercaDettaglioDatiTerrenoReturn() {
        return cercaDettaglioDatiTerrenoReturn;
    }

    /**
     * Imposta il valore della proprieta cercaDettaglioDatiTerrenoReturn.
     * 
     *     {@link DettaglioDatiTerreno }
     *     
     */
    public void setCercaDettaglioDatiTerrenoReturn(DettaglioDatiTerreno value) {
        this.cercaDettaglioDatiTerrenoReturn = value;
    }

}
