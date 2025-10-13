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
    "cercaComuniPerIdProvinciaReturn"
})
@XmlRootElement(name = "cercaComuniPerIdProvinciaResponse")
public class CercaComuniPerIdProvinciaResponse {

    protected List<Comune> cercaComuniPerIdProvinciaReturn;

    public List<Comune> getCercaComuniPerIdProvinciaReturn() {
        if (cercaComuniPerIdProvinciaReturn == null) {
            cercaComuniPerIdProvinciaReturn = new ArrayList<Comune>();
        }
        return this.cercaComuniPerIdProvinciaReturn;
    }

}