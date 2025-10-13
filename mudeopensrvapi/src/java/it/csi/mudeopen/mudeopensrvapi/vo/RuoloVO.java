/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RuoloVO implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private String codiceRuolo;

    private String descrizioneRuolo;

	public RuoloVO(String codiceRuolo, String descrizioneRuolo) {
		super();
		this.codiceRuolo = codiceRuolo;
		this.descrizioneRuolo = descrizioneRuolo;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codice) {
		this.codiceRuolo = codice;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizione) {
		this.descrizioneRuolo = descrizione;
	}
	
	
}
