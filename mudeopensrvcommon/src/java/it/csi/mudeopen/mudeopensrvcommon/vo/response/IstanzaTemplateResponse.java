/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class IstanzaTemplateResponse implements Serializable {

	private static final long serialVersionUID = -5017146808481702919L;

	@JsonProperty("istanza")
	private IstanzaResponse istanza;

	@JsonProperty("template")
	private TemplateResponse templateResponse;

    public IstanzaResponse getIstanza() {
		return istanza;
	}

    public void setIstanza(IstanzaResponse istanza) {
		this.istanza = istanza;
	}

    public TemplateResponse getTemplateResponse() {
		return templateResponse;
	}

    public void setTemplateResponse(TemplateResponse templateResponse) {
		this.templateResponse = templateResponse;
	}
}