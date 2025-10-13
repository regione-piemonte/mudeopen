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
    "cercaEstensioneComuneReturn"
})
@XmlRootElement(name = "cercaEstensioneComuneResponse")
public class CercaEstensioneComuneResponse {

    protected String cercaEstensioneComuneReturn;

    public String getCercaEstensioneComuneReturn() {
        return cercaEstensioneComuneReturn;
    }

    public void setCercaEstensioneComuneReturn(String value) {
        this.cercaEstensioneComuneReturn = value;
    }

}