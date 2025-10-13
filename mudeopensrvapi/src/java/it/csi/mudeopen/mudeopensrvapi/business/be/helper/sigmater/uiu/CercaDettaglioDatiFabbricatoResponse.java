/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="cercaDettaglioDatiFabbricatoReturn" type="{urn:sigalfsrvUiu}DettaglioDatiFabbricato" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaDettaglioDatiFabbricatoReturn"
})
@XmlRootElement(name = "cercaDettaglioDatiFabbricatoResponse")
public class CercaDettaglioDatiFabbricatoResponse {

    protected DettaglioDatiFabbricato cercaDettaglioDatiFabbricatoReturn;

    /**
     * Recupera il valore della proprieta cercaDettaglioDatiFabbricatoReturn.
     * 
     *     {@link DettaglioDatiFabbricato }
     *     
     */
    public DettaglioDatiFabbricato getCercaDettaglioDatiFabbricatoReturn() {
        return cercaDettaglioDatiFabbricatoReturn;
    }

    /**
     * Imposta il valore della proprieta cercaDettaglioDatiFabbricatoReturn.
     * 
     *     {@link DettaglioDatiFabbricato }
     *     
     */
    public void setCercaDettaglioDatiFabbricatoReturn(DettaglioDatiFabbricato value) {
        this.cercaDettaglioDatiFabbricatoReturn = value;
    }

}
