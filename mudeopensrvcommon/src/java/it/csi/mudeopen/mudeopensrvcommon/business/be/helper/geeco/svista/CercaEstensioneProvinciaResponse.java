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
    "cercaEstensioneProvinciaReturn"
})
@XmlRootElement(name = "cercaEstensioneProvinciaResponse")
public class CercaEstensioneProvinciaResponse {

    protected String cercaEstensioneProvinciaReturn;

    public String getCercaEstensioneProvinciaReturn() {
        return cercaEstensioneProvinciaReturn;
    }

    public void setCercaEstensioneProvinciaReturn(String value) {
        this.cercaEstensioneProvinciaReturn = value;
    }

}