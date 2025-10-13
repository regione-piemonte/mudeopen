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
    "cercaProvincePerIdRegioneReturn"
})
@XmlRootElement(name = "cercaProvincePerIdRegioneResponse")
public class CercaProvincePerIdRegioneResponse {

    protected List<Provincia> cercaProvincePerIdRegioneReturn;

    public List<Provincia> getCercaProvincePerIdRegioneReturn() {
        if (cercaProvincePerIdRegioneReturn == null) {
            cercaProvincePerIdRegioneReturn = new ArrayList<Provincia>();
        }
        return this.cercaProvincePerIdRegioneReturn;
    }

}