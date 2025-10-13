/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.quadri;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(Include.ALWAYS)
public class IstanzaTemplateQuadroVO extends ParentVO {
	private IstanzaVO istanza;

	@JsonProperty("template_quadro")
	private TemplateQuadroVO templateQuadro;

	@JsonProperty("json_data_quadro")
	private String jsonDataQuadro;

	@JsonProperty("json_data_subquadro")
	private String jsonDataSubquadro;

	@JsonProperty("cod_sub_quadro")
	private String codeSubQuadro;

	@JsonProperty("id_sub_quadro")
	private Long idSubQuadro;

	@JsonProperty("quadro_modificabile")
	private Boolean quadroModificabile;

	@JsonProperty("jdata_keys_to_delete")
    private String jDataKeysToDelete;
	
    public IstanzaVO getIstanza() {
		return istanza;
	}

    public void setIstanza(IstanzaVO istanza) {
		this.istanza = istanza;
	}

    public TemplateQuadroVO getTemplateQuadro() {
		return templateQuadro;
	}

    public void setTemplateQuadro(TemplateQuadroVO templateQuadro) {
		this.templateQuadro = templateQuadro;
	}

    public String getJsonDataQuadro() {
		return jsonDataQuadro;
	}

    public void setJsonDataQuadro(String jsonDataQuadro) {
		this.jsonDataQuadro = jsonDataQuadro;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		IstanzaTemplateQuadroVO that = (IstanzaTemplateQuadroVO) o;
		return Objects.equals(istanza, that.istanza) && Objects.equals(templateQuadro, that.templateQuadro) && Objects.equals(jsonDataQuadro, that.jsonDataQuadro);
	}

	@Override
	public int hashCode() {
		return Objects.hash(istanza, templateQuadro, jsonDataQuadro);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("IstanzaTemplateQuadroVO {");
		sb.append(super.toString());
		sb.append("         istanza:").append(istanza);
		sb.append(",         templateQuadro:").append(templateQuadro);
		sb.append(",         jsonDataQuadro:'").append(jsonDataQuadro).append("'");
		sb.append("}");
		return sb.toString();
	}

	public String getCodeSubQuadro() {
		return codeSubQuadro;
	}

	public void setCodeSubQuadro(String codeSubQuadro) {
		this.codeSubQuadro = codeSubQuadro;
	}

	public String getJsonDataSubquadro() {
		return jsonDataSubquadro;
	}

	public void setJsonDataSubquadro(String jsonDataSubquadro) {
		this.jsonDataSubquadro = jsonDataSubquadro;
	}

	public Boolean getQuadroModificabile() {
		return quadroModificabile;
	}

	public void setQuadroModificabile(Boolean quadroModificabile) {
		this.quadroModificabile = quadroModificabile;
	}

	public Long getIdSubQuadro() {
		return idSubQuadro;
	}

	public void setIdSubQuadro(Long idSubQuadro) {
		this.idSubQuadro = idSubQuadro;
	}

	public String getjDataKeysToDelete() {
		return jDataKeysToDelete;
	}

	public void setjDataKeysToDelete(String jDataKeysToDelete) {
		this.jDataKeysToDelete = jDataKeysToDelete;
	}

}