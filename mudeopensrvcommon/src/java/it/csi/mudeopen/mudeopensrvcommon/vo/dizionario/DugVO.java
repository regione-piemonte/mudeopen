/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;

public class DugVO implements Serializable {
    private static final long serialVersionUID = 2791662507469801243L;

    @JsonProperty("id_dug")
    private long idDug;

    @JsonProperty("denominazione")
    private String denominazione;

    public long getIdDug() {
        return idDug;
    }

    public void setIdDug(long idDug) {
        this.idDug = idDug;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
}