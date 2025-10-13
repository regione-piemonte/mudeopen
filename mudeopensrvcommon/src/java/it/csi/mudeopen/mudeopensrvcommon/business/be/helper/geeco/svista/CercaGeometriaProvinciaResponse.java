/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaGeometriaProvinciaReturn"
})
@XmlRootElement(name = "cercaGeometriaProvinciaResponse")
public class CercaGeometriaProvinciaResponse {

    protected String cercaGeometriaProvinciaReturn;

    public String getCercaGeometriaProvinciaReturn() {
        return cercaGeometriaProvinciaReturn;
    }

    public void setCercaGeometriaProvinciaReturn(String value) {
        this.cercaGeometriaProvinciaReturn = value;
    }

}