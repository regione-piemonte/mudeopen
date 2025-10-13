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
 *         &lt;element name="estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn"
})
@XmlRootElement(name = "estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaResponse")
public class EstraiPlanimetriaPerBelfioreEdIdImmaginePiantinaResponse {

    protected byte[] estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn;

    /**
     * Recupera il valore della proprieta estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn.
     * 
     *     byte[]
     */
    public byte[] getEstraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn() {
        return estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn;
    }

    /**
     * Imposta il valore della proprieta estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn.
     * 
     *     byte[]
     */
    public void setEstraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn(byte[] value) {
        this.estraiPlanimetriaPerBelfioreEdIdImmaginePiantinaReturn = value;
    }

}
