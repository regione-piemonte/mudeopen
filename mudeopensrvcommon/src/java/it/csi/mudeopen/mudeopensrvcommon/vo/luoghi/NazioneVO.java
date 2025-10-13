/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.luoghi;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

public class NazioneVO extends SelectVO {

	private static final long serialVersionUID = -8259679163033935929L;
	
    @JsonProperty("stato_membro_ue")
	private Boolean statoMembroUE;

	@JsonProperty("codice_belfiore")
	private String codiceBelfiore;

	public Boolean getStatoMembroUE() {
		return statoMembroUE;
	}

	public void setStatoMembroUE(Boolean statoMembroUE) {
		this.statoMembroUE = statoMembroUE;
	}

	public String getCodiceBelfiore() {
		return codiceBelfiore;
	}

	public void setCodiceBelfiore(String codiceBelfiore) {
		this.codiceBelfiore = codiceBelfiore;
	}
}