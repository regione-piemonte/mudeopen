/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;

public class GeecoParticellaExtraTorino extends GeecoParticella {
    String foglio = null;
    String mappale = null;
    String sezione = null;

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getMappale() {
        return mappale;
    }

    public void setMappale(String mappale) {
        this.mappale = mappale;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public JsonNode toJson(){

        return mapper.valueToTree(this);
    }

    @Override
    public String toString() {
        return "GeecoParticellaExtraTorino{" +
                "origin='" + origin + '\'' +
                ", firstPoint=" + Arrays.toString(firstPoint) +
                ", belfiore='" + belfiore + '\'' +
                ", foglio='" + foglio + '\'' +
                ", mappale='" + mappale + '\'' +
                ", sezione='" + sezione + '\'' +
                '}';
    }
}
