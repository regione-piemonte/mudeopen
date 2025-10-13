/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;

import java.io.Serializable;
import java.util.List;

public class AbilitazioneFunzioneSlimCustomVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 4231201505794020238L;

    @JsonProperty("abilitazione")
    private String abilitazione;

    @JsonProperty("funzioni")
    private List<String> funzioni;

    @JsonProperty("quadri")
    List<Long> quadri;

	public String getAbilitazione() {
		return abilitazione;
	}

	public void setAbilitazione(String abilitazione) {
		this.abilitazione = abilitazione;
	}

	public List<String> getFunzioni() {
		return funzioni;
	}

	public void setFunzioni(List<String> funzioni) {
		this.funzioni = funzioni;
	}

	public List<Long> getQuadri() {
		return quadri;
	}

	public void setQuadri(List<Long> quadri) {
		this.quadri = quadri;
	}
}