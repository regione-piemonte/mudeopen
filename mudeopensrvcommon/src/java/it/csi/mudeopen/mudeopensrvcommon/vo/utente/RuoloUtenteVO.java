/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.utente;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RuoloUtenteVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -8332295733602645691L;

    @JsonProperty("id_utente_ruolo")
    private Long id;

    @JsonProperty("ruoloUtenteCod")
    private String ruoloUtenteCod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getRuoloUtenteCod() {
		return ruoloUtenteCod;
	}

	public void setRuoloUtenteCod(String ruoloUtenteCod) {
		this.ruoloUtenteCod = ruoloUtenteCod;
	}
}