/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn"
})
@XmlRootElement(name = "determinaRedditoDominicalePerIdentificativoCatastaleTerrenoResponse")
public class DeterminaRedditoDominicalePerIdentificativoCatastaleTerrenoResponse {

    protected double determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn;

    /**
     * Recupera il valore della proprieta determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn.
     * 
     */
    public double getDeterminaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn() {
        return determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn;
    }

    /**
     * Imposta il valore della proprieta determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn.
     * 
     */
    public void setDeterminaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn(double value) {
        this.determinaRedditoDominicalePerIdentificativoCatastaleTerrenoReturn = value;
    }

}
