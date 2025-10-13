/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.quadri;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;

@JsonInclude(Include.ALWAYS)
public class TipoQuadroVO extends ParentVO {
	@JsonProperty("id_tipo_quadro")
	private Long idTipoQuadro;

	@JsonProperty("cod_tipo_quadro")
	private String codTipoQuadro;

	@JsonProperty("des_tipo_quadro")
	private String desTipoQuadro;

	@JsonProperty("modificabile")
	private boolean modificabile;
	
	@JsonProperty("id_categoria_quadro")
	private Long idCategoriaQuadro;

	@JsonProperty("funzioni_richieste")
	private String funzioniRichieste;

	@JsonProperty("tipo_quadro_padre")
	private TipoQuadroVO tipoQuadroPadre;
	
    public boolean isModificabile() {
		return modificabile;
	}

	public void setModificabile(boolean modificabile) {
		this.modificabile = modificabile;
	}

	public Long getIdTipoQuadro() {
		return idTipoQuadro;
	}

    public void setIdTipoQuadro(Long idTipoQuadro) {
		this.idTipoQuadro = idTipoQuadro;
	}

    public String getCodTipoQuadro() {
		return codTipoQuadro;
	}

    public void setCodTipoQuadro(String codTipoQuadro) {
		this.codTipoQuadro = codTipoQuadro;
	}

    public String getDesTipoQuadro() {
		return desTipoQuadro;
	}

    public void setDesTipoQuadro(String desTipoQuadro) {
		this.desTipoQuadro = desTipoQuadro;
	}

	public String getFunzioniRichieste() {
		return funzioniRichieste;
	}

	public void setFunzioniRichieste(String funzioniRichieste) {
		this.funzioniRichieste = funzioniRichieste;
	}

	public Long getIdCategoriaQuadro() {
		return idCategoriaQuadro;
	}

	public void setIdCategoriaQuadro(Long idCategoriaQuadro) {
		this.idCategoriaQuadro = idCategoriaQuadro;
	}

	public TipoQuadroVO getTipoQuadroPadre() {
		return tipoQuadroPadre;
	}

	public void setTipoQuadroPadre(TipoQuadroVO tipoQuadroPadre) {
		this.tipoQuadroPadre = tipoQuadroPadre;
	}
}