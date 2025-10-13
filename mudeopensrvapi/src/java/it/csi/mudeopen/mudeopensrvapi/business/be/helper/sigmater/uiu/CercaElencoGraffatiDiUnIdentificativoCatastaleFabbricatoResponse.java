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
 *         &lt;element name="cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn" type="{urn:sigalfsrvUiu}Graffato" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn"
})
@XmlRootElement(name = "cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoResponse")
public class CercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoResponse {

    protected Graffato cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn;

    /**
     * Recupera il valore della proprieta cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn.
     * 
     *     {@link Graffato }
     *     
     */
    public Graffato getCercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn() {
        return cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn;
    }

    /**
     * Imposta il valore della proprieta cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn.
     * 
     *     {@link Graffato }
     *     
     */
    public void setCercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn(Graffato value) {
        this.cercaElencoGraffatiDiUnIdentificativoCatastaleFabbricatoReturn = value;
    }

}
