/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DelegatoVO {
	@JsonProperty("cognome_delegato")
	private String cognomeDelegato;

	@JsonProperty("nome_delegato")
	private String nomeDelegato;

	@JsonProperty("pec")
	private String pec;

	@JsonProperty("email_delegato")
	private String emailDelegato;

	public String getCognomeDelegato() {
		return cognomeDelegato;
	}

	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}

	public String getNomeDelegato() {
		return nomeDelegato;
	}

	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getEmailDelegato() {
		return emailDelegato;
	}

	public void setEmailDelegato(String emailDelegato) {
		this.emailDelegato = emailDelegato;
	}

}