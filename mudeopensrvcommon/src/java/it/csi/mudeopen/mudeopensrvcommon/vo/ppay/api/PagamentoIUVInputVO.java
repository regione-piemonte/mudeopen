/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PagamentoIUVInputVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983528L;

	@JsonProperty("iuv")
	private String iuv;
	
	@JsonProperty("codiceFiscale")
	private String codiceFiscale;
	
	@JsonProperty("identificativoPagamento")
	private String identificativoPagamento;

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}

	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}

}