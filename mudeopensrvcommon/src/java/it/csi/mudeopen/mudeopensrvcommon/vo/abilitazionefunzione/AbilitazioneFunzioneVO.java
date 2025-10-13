/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(Include.ALWAYS)
public class AbilitazioneFunzioneVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -6277396771975069782L;

    @JsonProperty("id_abilitazione_funzione")
    private Long id;

    @JsonProperty("abilitazione")
    private AbilitazioneVO abilitazione;

    @JsonProperty("funzione")
    private FunzioneVO funzione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbilitazioneVO getAbilitazione() {
        return abilitazione;
    }

    public void setAbilitazione(AbilitazioneVO abilitazione) {
        this.abilitazione = abilitazione;
    }

    public FunzioneVO getFunzione() {
        return funzione;
    }

    public void setFunzione(FunzioneVO funzione) {
        this.funzione = funzione;
    }
}