/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.quadri;

import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;

@JsonInclude(Include.ALWAYS)
public class QuadroVO extends ParentVO {
	@JsonProperty("id_quadro")
	private Long idQuadro;

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

	@JsonProperty("json_default")
	private String jsonDefault;

	@JsonProperty("validation_script")
	private String validationScript;

	@JsonProperty("flg_eliminabile")
	private boolean flgEliminabile;

	@JsonProperty("ultima_versione")
	private boolean ultimaVersione;

	@JsonProperty("flg_modificabile")
	private boolean flgModificabile;

	@JsonProperty("flg_creabile")
	private boolean flgCreabile;

	@JsonProperty("modello_documentale")
	private ModelloVO modello;

	@JsonProperty("id_quadro_parent")
	private Long idQuadroParent;

    public Long getIdQuadro() {
		return idQuadro;
	}

    public void setIdQuadro(Long idQuadro) {
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

    public String getJsonDefault() {
		return jsonDefault;
	}

    public void setJsonDefault(String jsonDefault) {
		this.jsonDefault = jsonDefault;
	}

    public String getValidationScript() {
		return validationScript;
	}

	public boolean isFlgEliminabile() {
		return flgEliminabile;
	}

	public void setFlgEliminabile(boolean flgEliminabile) {
		this.flgEliminabile = flgEliminabile;
	}

	public boolean isFlgModificabile() {
		return flgModificabile;
	}

	public void setFlgModificabile(boolean flgModificabile) {
		this.flgModificabile = flgModificabile;
	}

    public void setValidationScript(String validationScript) {
		this.validationScript = validationScript;
	}

	@Override
	public String toString() {
		return new StringJoiner("\n ", QuadroVO.class.getSimpleName() + "[", "]")
				.add("idQuadro=" + idQuadro)
				.add("tipoQuadro=" + tipoQuadro)
				.add("numVersione=" + numVersione)
				.add("flgTipoGestione='" + flgTipoGestione + "'")
				.add("jsonConfiguraQuadro='" + jsonConfiguraQuadro + "'")
				.add("flgAttivo=" + flgAttivo)
				.add("jsonDefault='" + jsonDefault + "'")
				.add("validationScript='" + validationScript + "'")
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof QuadroVO)) return false;
		QuadroVO quadroVO = (QuadroVO) o;
		return idQuadro == quadroVO.idQuadro && numVersione == quadroVO.numVersione && flgAttivo == quadroVO.flgAttivo && Objects.equals(tipoQuadro, quadroVO.tipoQuadro) && Objects.equals(flgTipoGestione, quadroVO.flgTipoGestione) && Objects.equals(jsonConfiguraQuadro, quadroVO.jsonConfiguraQuadro) && Objects.equals(jsonDefault, quadroVO.jsonDefault) && Objects.equals(validationScript, quadroVO.validationScript);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idQuadro, tipoQuadro, numVersione, flgTipoGestione, jsonConfiguraQuadro, flgAttivo, jsonDefault, validationScript);
	}

	public boolean isFlgCreabile() {
		return flgCreabile;
	}

	public void setFlgCreabile(boolean flgCreabile) {
		this.flgCreabile = flgCreabile;
	}

	public ModelloVO getModello() {
		return modello;
	}

	public void setModello(ModelloVO modello) {
		this.modello = modello;
	}

	public boolean isUltimaVersione() {
		return ultimaVersione;
	}

	public void setUltimaVersione(boolean ultimaVersione) {
		this.ultimaVersione = ultimaVersione;
	}

	public Long getIdQuadroParent() {
		return idQuadroParent;
	}

	public void setIdQuadroParent(Long idQuadroParent) {
		this.idQuadroParent = idQuadroParent;
	}

}