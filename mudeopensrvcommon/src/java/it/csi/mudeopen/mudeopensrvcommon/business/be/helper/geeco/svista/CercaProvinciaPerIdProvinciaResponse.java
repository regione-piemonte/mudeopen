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
    "cercaProvinciaPerIdProvinciaReturn"
})
@XmlRootElement(name = "cercaProvinciaPerIdProvinciaResponse")
public class CercaProvinciaPerIdProvinciaResponse {

    protected Provincia cercaProvinciaPerIdProvinciaReturn;

    public Provincia getCercaProvinciaPerIdProvinciaReturn() {
        return cercaProvinciaPerIdProvinciaReturn;
    }

    public void setCercaProvinciaPerIdProvinciaReturn(Provincia value) {
        this.cercaProvinciaPerIdProvinciaReturn = value;
    }

}