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
    "cercaTutteLeProvinceReturn"
})
@XmlRootElement(name = "cercaTutteLeProvinceResponse")
public class CercaTutteLeProvinceResponse {

    protected List<Provincia> cercaTutteLeProvinceReturn;

    public List<Provincia> getCercaTutteLeProvinceReturn() {
        if (cercaTutteLeProvinceReturn == null) {
            cercaTutteLeProvinceReturn = new ArrayList<Provincia>();
        }
        return this.cercaTutteLeProvinceReturn;
    }

}