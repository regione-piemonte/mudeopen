/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuoliFascicoloRequest implements Serializable {
    private static final long serialVersionUID = 940131279672490001L;

    @JsonProperty("id")
    private String idRuolo;

    @JsonProperty("soggetti")
    private List<Long> idSoggettiFascicolo;

	public List<Long> getIdSoggettiFascicolo() {
		return idSoggettiFascicolo;
	}

	public void setIdSoggettiFascicolo(List<Long> idSoggettiFascicolo) {
		this.idSoggettiFascicolo = idSoggettiFascicolo;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}
	
}