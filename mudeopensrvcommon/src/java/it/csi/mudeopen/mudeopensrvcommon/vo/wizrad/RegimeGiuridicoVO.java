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
public class RegimeGiuridicoVO extends ParentVO {

	private static final long serialVersionUID = -3961724086179323695L;

	@JsonProperty("id_regime")
	private long idRegime;

	private String denominazione;

	private Long priorita;

    public long getIdRegime() {
		return idRegime;
	}

    public void setIdRegime(long idRegime) {
		this.idRegime = idRegime;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public Long getPriorita() {
		return priorita;
	}

    public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(denominazione, idRegime, priorita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RegimeGiuridicoVO))
			return false;
		RegimeGiuridicoVO other = (RegimeGiuridicoVO) obj;
		return Objects.equals(denominazione, other.denominazione) && idRegime == other.idRegime
				&& Objects.equals(priorita, other.priorita);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegimeGiuridicoVO\n [idRegime=").append(idRegime).append("\n denominazione=")
				.append(denominazione).append("\n priorita=").append(priorita).append("]");
		return builder.toString();
	}

}