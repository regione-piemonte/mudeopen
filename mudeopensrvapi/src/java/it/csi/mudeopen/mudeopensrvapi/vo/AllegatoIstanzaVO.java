/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllegatoIstanzaVO {

	
    //@JsonProperty("numero_istanza")
    protected String numeroIstanza;
	
    //@JsonProperty("tipoa_llegato")
    protected String tipoAllegato;

    protected String nomeFile;

	public String getNumeroIstanza() {
		return numeroIstanza;
	}

	public void setNumeroIstanza(String numeroIstanza) {
		this.numeroIstanza = numeroIstanza;
	}

	public String getTipoAllegato() {
		return tipoAllegato;
	}

	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
}
