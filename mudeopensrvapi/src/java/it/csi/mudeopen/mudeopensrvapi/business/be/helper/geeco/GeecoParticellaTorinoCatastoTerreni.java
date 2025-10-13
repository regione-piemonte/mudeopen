/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;

public class GeecoParticellaTorinoCatastoTerreni extends GeecoParticella{
    String citPart;
    String foglio;
    String nPart;

    public String getCitPart() {
        return citPart;
    }

    public void setCitPart(String citPart) {
        this.citPart = citPart;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getnPart() {
        return nPart;
    }

    public void setnPart(String nPart) {
        this.nPart = nPart;
    }

    @Override
    public String toString() {
        return "GeecoParticellaTorinoCatastoTerreni{" +
                "origin='" + origin + '\'' +
                ", firstPoint=" + Arrays.toString(firstPoint) +
                ", belfiore='" + belfiore + '\'' +
                ", citPart='" + citPart + '\'' +
                ", foglio='" + foglio + '\'' +
                ", nPart='" + nPart + '\'' +
                '}';
    }

    @Override
    public JsonNode toJson() {
        return mapper.valueToTree(this);
    }
}
