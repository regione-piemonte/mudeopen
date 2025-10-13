/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index;

public class IndexIstanzaMetadataProperty {
    String numeroIntervento;

    String codiceModello;

    String codiceVersioneModello;

    String numeroMudeIstanza;

    public String getNumeroIntervento() {
        return numeroIntervento;
    }

    public void setNumeroIntervento(String numeroIntervento) {
        this.numeroIntervento = numeroIntervento;
    }

    public String getCodiceModello() {
        return codiceModello;
    }

    public void setCodiceModello(String codiceModello) {
        this.codiceModello = codiceModello;
    }

    public String getCodiceVersioneModello() {
        return codiceVersioneModello;
    }

    public void setCodiceVersioneModello(String codiceVersioneModello) {
        this.codiceVersioneModello = codiceVersioneModello;
    }

    public String getNumeroMudeIstanza() {
        return numeroMudeIstanza;
    }

    public void setNumeroMudeIstanza(String numeroMudeIstanza) {
        this.numeroMudeIstanza = numeroMudeIstanza;
    }
}