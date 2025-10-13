/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.wizrad;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;

@JsonInclude(Include.ALWAYS)
public class CategoriaInterventoVO extends ParentVO {

	private static final long serialVersionUID = -3961724086179323695L;

	@JsonProperty("id_categoria")
	private long idCategoria;
	
	private String denominazione;
	
	@JsonProperty("regime_giuridico")
	private RegimeGiuridicoVO regimeGiuridicoVO;

    public long getIdCategoria() {
		return idCategoria;
	}

    public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public RegimeGiuridicoVO getRegimeGiuridicoVO() {
		return regimeGiuridicoVO;
	}

    public void setRegimeGiuridicoVO(RegimeGiuridicoVO regimeGiuridicoVO) {
		this.regimeGiuridicoVO = regimeGiuridicoVO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(denominazione, idCategoria, regimeGiuridicoVO);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CategoriaInterventoVO))
			return false;
		CategoriaInterventoVO other = (CategoriaInterventoVO) obj;
		return Objects.equals(denominazione, other.denominazione) && idCategoria == other.idCategoria
				&& Objects.equals(regimeGiuridicoVO, other.regimeGiuridicoVO);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoriaVO\n [idCategoria=").append(idCategoria).append("\n denominazione=")
				.append(denominazione).append("\n regimeGiuridicoVO=").append(regimeGiuridicoVO).append("]");
		return builder.toString();
	}
	
	

	
	
}