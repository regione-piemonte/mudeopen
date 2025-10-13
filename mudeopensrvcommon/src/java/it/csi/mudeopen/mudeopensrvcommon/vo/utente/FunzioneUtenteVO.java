/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.utente;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FunzioneUtenteVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -8332295733602645691L;

    @JsonProperty("cod_funzione")
    private String codFunzione;

	public String getCodFunzione() {
		return codFunzione;
	}

	public void setCodFunzione(String codFunzione) {
		this.codFunzione = codFunzione;
	}
}