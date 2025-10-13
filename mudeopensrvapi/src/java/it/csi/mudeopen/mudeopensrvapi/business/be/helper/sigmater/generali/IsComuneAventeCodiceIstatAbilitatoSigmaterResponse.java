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
 *         &lt;element name="isComuneAventeCodiceIstatAbilitatoSigmaterReturn" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isComuneAventeCodiceIstatAbilitatoSigmaterReturn"
})
@XmlRootElement(name = "isComuneAventeCodiceIstatAbilitatoSigmaterResponse")
public class IsComuneAventeCodiceIstatAbilitatoSigmaterResponse {

    protected boolean isComuneAventeCodiceIstatAbilitatoSigmaterReturn;

    /**
     * Recupera il valore della proprieta isComuneAventeCodiceIstatAbilitatoSigmaterReturn.
     * 
     */
    public boolean isIsComuneAventeCodiceIstatAbilitatoSigmaterReturn() {
        return isComuneAventeCodiceIstatAbilitatoSigmaterReturn;
    }

    /**
     * Imposta il valore della proprieta isComuneAventeCodiceIstatAbilitatoSigmaterReturn.
     * 
     */
    public void setIsComuneAventeCodiceIstatAbilitatoSigmaterReturn(boolean value) {
        this.isComuneAventeCodiceIstatAbilitatoSigmaterReturn = value;
    }

}
