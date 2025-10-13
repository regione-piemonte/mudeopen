/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(Include.NON_NULL)
public class IstanzaTemplateQuadroResponse extends TemplateQuadroResponse {

	@JsonProperty("json_data_quadro")
	private String jsonDataQuadro;

    public String getJsonDataQuadro() {
		return jsonDataQuadro;
	}

    public void setJsonDataQuadro(String jsonDataQuadro) {
		this.jsonDataQuadro = jsonDataQuadro;
	}

	@Override
	public String toString() {
		return new StringJoiner("\n ", IstanzaTemplateQuadroResponse.class.getSimpleName() + "[", "]")
				.add("jsonDataQuadro='" + jsonDataQuadro + "'")
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof IstanzaTemplateQuadroResponse)) return false;
		IstanzaTemplateQuadroResponse that = (IstanzaTemplateQuadroResponse) o;
		return Objects.equals(jsonDataQuadro, that.jsonDataQuadro);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jsonDataQuadro);
	}
}