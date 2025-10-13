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
    "cercaComuniPerNomeEIdProvinciaReturn"
})
@XmlRootElement(name = "cercaComuniPerNomeEIdProvinciaResponse")
public class CercaComuniPerNomeEIdProvinciaResponse {

    protected List<Comune> cercaComuniPerNomeEIdProvinciaReturn;

    public List<Comune> getCercaComuniPerNomeEIdProvinciaReturn() {
        if (cercaComuniPerNomeEIdProvinciaReturn == null) {
            cercaComuniPerNomeEIdProvinciaReturn = new ArrayList<Comune>();
        }
        return this.cercaComuniPerNomeEIdProvinciaReturn;
    }

}