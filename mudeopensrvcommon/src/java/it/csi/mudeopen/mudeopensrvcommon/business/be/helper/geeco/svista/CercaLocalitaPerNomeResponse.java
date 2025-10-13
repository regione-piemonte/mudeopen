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
    "cercaLocalitaPerNomeReturn"
})
@XmlRootElement(name = "cercaLocalitaPerNomeResponse")
public class CercaLocalitaPerNomeResponse {

    protected List<Localita> cercaLocalitaPerNomeReturn;

    public List<Localita> getCercaLocalitaPerNomeReturn() {
        if (cercaLocalitaPerNomeReturn == null) {
            cercaLocalitaPerNomeReturn = new ArrayList<Localita>();
        }
        return this.cercaLocalitaPerNomeReturn;
    }

}