/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaLocalitaPerIdComuneReturn"
})
@XmlRootElement(name = "cercaLocalitaPerIdComuneResponse")
public class CercaLocalitaPerIdComuneResponse {

    protected List<Localita> cercaLocalitaPerIdComuneReturn;

    public List<Localita> getCercaLocalitaPerIdComuneReturn() {
        if (cercaLocalitaPerIdComuneReturn == null) {
            cercaLocalitaPerIdComuneReturn = new ArrayList<Localita>();
        }
        return this.cercaLocalitaPerIdComuneReturn;
    }

}