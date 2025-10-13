/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;

public class GeecoParticellaTorinoCatastoFabbricati extends GeecoParticella {
    String idFabbricato;
    String foglioCt;
    String foglioCu;
    String particellaCt;
    String particellaCu;

    public String getIdFabbricato() {
        return idFabbricato;
    }

    public void setIdFabbricato(String idFabbricato) {
        this.idFabbricato = idFabbricato;
    }

    public String getFoglioCt() {
        return foglioCt;
    }

    public void setFoglioCt(String foglioCt) {
        this.foglioCt = foglioCt;
    }

    public String getFoglioCu() {
        return foglioCu;
    }

    public void setFoglioCu(String foglioCu) {
        this.foglioCu = foglioCu;
    }

    public String getParticellaCt() {
        return particellaCt;
    }

    public void setParticellaCt(String particellaCt) {
        this.particellaCt = particellaCt;
    }

    public String getParticellaCu() {
        return particellaCu;
    }

    public void setParticellaCu(String particellaCu) {
        this.particellaCu = particellaCu;
    }

    @Override
    public String toString() {
        return "GeecoParticellaTorinoCatastoFabbricati{" +
                "origin='" + origin + '\'' +
                ", firstPoint=" + Arrays.toString(firstPoint) +
                ", belfiore='" + belfiore + '\'' +
                ", idFabbricato='" + idFabbricato + '\'' +
                ", foglioCt='" + foglioCt + '\'' +
                ", foglioCu='" + foglioCu + '\'' +
                ", particellaCt='" + particellaCt + '\'' +
                ", particellaCu='" + particellaCu + '\'' +
                '}';
    }

    @Override
    public JsonNode toJson() {
        return mapper.valueToTree(this);
    }
}
