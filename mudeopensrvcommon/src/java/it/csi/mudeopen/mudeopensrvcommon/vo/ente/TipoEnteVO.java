/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TipoEnteVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983558L;

    @JsonProperty("id_tipo_ente")
    private Long id;

    @JsonProperty("codice_tipo_ente")
    private String codice;

    @JsonProperty("descrizione_tipo_ente")
    private String descrizione;

    @JsonProperty("descrizione_estesa_tipo_ente")
    private String descrizioneEstesa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }
}