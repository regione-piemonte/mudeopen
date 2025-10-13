/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.quadri;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;

import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateQuadroVO extends ParentVO {
	@JsonProperty("id_template_quadro")
	private Long idTemplateQuadro;

	private TemplateVO template;

	private QuadroVO quadro;

	@JsonProperty("ordinamento_template_quadro")
	private long ordinamentoTemplateQuadro;

	@JsonProperty("flg_quadro_obbigatorio")
	private Boolean flgQuadroObbigatorio;

	@JsonProperty("proprieta")
	private String proprieta;

    public Long getIdTemplateQuadro() {
		return idTemplateQuadro;
	}

    public void setIdTemplateQuadro(Long idTemplateQuadro) {
		this.idTemplateQuadro = idTemplateQuadro;
	}

    public TemplateVO getTemplate() {
		return template;
	}

    public void setTemplate(TemplateVO template) {
		this.template = template;
	}

    public QuadroVO getQuadro() {
		return quadro;
	}

    public void setQuadro(QuadroVO quadro) {
		this.quadro = quadro;
	}

    public long getOrdinamentoTemplateQuadro() {
		return ordinamentoTemplateQuadro;
	}

    public void setOrdinamentoTemplateQuadro(long ordinamentoTemplateQuadro) {
		this.ordinamentoTemplateQuadro = ordinamentoTemplateQuadro;
	}

    public Boolean getFlgQuadroObbigatorio() {
		return flgQuadroObbigatorio;
	}

    public void setFlgQuadroObbigatorio(Boolean flgQuadroObbigatorio) {
		this.flgQuadroObbigatorio = flgQuadroObbigatorio;
	}

	@Override
	public String toString() {
		return new StringJoiner("\n ", TemplateQuadroVO.class.getSimpleName() + "[", "]")
				.add("idTemplateQuadro=" + idTemplateQuadro)
				.add("template=" + template)
				.add("quadro=" + quadro)
				.add("ordinamentoTemplateQuadro=" + ordinamentoTemplateQuadro)
				.add("flgQuadroObbigatorio=" + flgQuadroObbigatorio)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TemplateQuadroVO)) return false;
		TemplateQuadroVO that = (TemplateQuadroVO) o;
		return idTemplateQuadro == that.idTemplateQuadro && ordinamentoTemplateQuadro == that.ordinamentoTemplateQuadro && Objects.equals(template, that.template) && Objects.equals(quadro, that.quadro) && Objects.equals(flgQuadroObbigatorio, that.flgQuadroObbigatorio);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTemplateQuadro, template, quadro, ordinamentoTemplateQuadro, flgQuadroObbigatorio);
	}

	public String getProprieta() {
		return proprieta;
	}

	public void setProprieta(String proprieta) {
		this.proprieta = proprieta;
	}

}