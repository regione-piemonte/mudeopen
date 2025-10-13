/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetIUVChiamanteEsternoOutputVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983528L;

    @JsonProperty("identificativoPagamento")
    private String identificativoPagamento;

    @JsonProperty("iuv")
    private String iuv;

    @JsonProperty("codiceAvviso")
    private String codiceAvviso;

    @JsonProperty("codiceEsito")
    private String codiceEsito;

    @JsonProperty("descrizioneEsito")
    private String descrizioneEsito;

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}

	public String getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}

	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}

	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}

}