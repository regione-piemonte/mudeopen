/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn"
})
@XmlRootElement(name = "isComuneAventeCodiceBelfioreAbilitatoSigmaterResponse")
public class IsComuneAventeCodiceBelfioreAbilitatoSigmaterResponse {

    protected boolean isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn;

    /**
     * Recupera il valore della proprieta isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn.
     * 
     */
    public boolean isIsComuneAventeCodiceBelfioreAbilitatoSigmaterReturn() {
        return isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn;
    }

    /**
     * Imposta il valore della proprieta isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn.
     * 
     */
    public void setIsComuneAventeCodiceBelfioreAbilitatoSigmaterReturn(boolean value) {
        this.isComuneAventeCodiceBelfioreAbilitatoSigmaterReturn = value;
    }

}
