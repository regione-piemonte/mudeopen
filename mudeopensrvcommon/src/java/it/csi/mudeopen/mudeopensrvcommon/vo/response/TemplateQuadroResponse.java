/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TipoQuadroVO;

import java.io.Serializable;

@JsonInclude(Include.ALWAYS)
public class TemplateQuadroResponse implements Serializable {
	private static final long serialVersionUID = -4608160394767998375L;

	@JsonProperty("id_template_quadro")
	private long idTemplateQuadro;

	@JsonProperty("id_quadro")
	private long idQuadro;

	@JsonProperty("tipo_quadro")
	private TipoQuadroVO tipoQuadro;

	@JsonProperty("num_versione")
	private long numVersione;

	@JsonProperty("flg_tipo_gestione")
	private String flgTipoGestione;

	@JsonProperty("json_configura_quadro")
	private String jsonConfiguraQuadro;

	@JsonProperty("flg_attivo")
	private long flgAttivo;

	@JsonProperty("ordinamento_template_quadro")
	private long ordinamentoTemplateQuadro;

	@JsonProperty("flg_quadro_obbigatorio")
	private Boolean flgQuadroObbigatorio;

	@JsonProperty("json_configura_riepilogo")
	private String jsonConfiguraRiepilogo;

	@JsonProperty("validation_script")
	private String validationScript;

	@JsonProperty("proprieta")
	private String proprieta;

    public long getIdTemplateQuadro() {
		return idTemplateQuadro;
	}

    public void setIdTemplateQuadro(long idTemplateQuadro) {
		this.idTemplateQuadro = idTemplateQuadro;
	}

    public long getIdQuadro() {
		return idQuadro;
	}

    public void setIdQuadro(long idQuadro) {
		this.idQuadro = idQuadro;
	}

    public TipoQuadroVO getTipoQuadro() {
		return tipoQuadro;
	}

    public void setTipoQuadro(TipoQuadroVO tipoQuadro) {
		this.tipoQuadro = tipoQuadro;
	}

    public long getNumVersione() {
		return numVersione;
	}

    public void setNumVersione(long numVersione) {
		this.numVersione = numVersione;
	}

    public String getFlgTipoGestione() {
		return flgTipoGestione;
	}

    public void setFlgTipoGestione(String flgTipoGestione) {
		this.flgTipoGestione = flgTipoGestione;
	}

    public String getJsonConfiguraQuadro() {
		return jsonConfiguraQuadro;
	}

    public void setJsonConfiguraQuadro(String jsonConfiguraQuadro) {
		this.jsonConfiguraQuadro = jsonConfiguraQuadro;
	}

    public long getFlgAttivo() {
		return flgAttivo;
	}

    public void setFlgAttivo(long flgAttivo) {
		this.flgAttivo = flgAttivo;
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

    public String getJsonConfiguraRiepilogo() {
		return jsonConfiguraRiepilogo;
	}

    public void setJsonConfiguraRiepilogo(String jsonConfiguraRiepilogo) {
		this.jsonConfiguraRiepilogo = jsonConfiguraRiepilogo;
	}

    public String getValidationScript() {
		return validationScript;
	}

    public void setValidationScript(String validationScript) {
		this.validationScript = validationScript;
	}
    
	public String getProprieta() {
		return proprieta;
	}

	public void setProprieta(String proprieta) {
		this.proprieta = proprieta;
	}
}