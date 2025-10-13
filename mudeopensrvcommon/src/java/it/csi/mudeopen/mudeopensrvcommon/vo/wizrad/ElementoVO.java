/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.wizrad;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;

@JsonInclude(Include.ALWAYS)
public class ElementoVO extends ParentVO {

	private static final long serialVersionUID = -3961724086179323695L;

	@JsonProperty("id_elemento")
	private long idElemento;
	
	private long posizione;
	private String denominazione;

	private Set<OperaVO> opere;

    public long getIdElemento() {
		return idElemento;
	}

    public void setIdElemento(long idElemento) {
		this.idElemento = idElemento;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public Set<OperaVO> getOpere() {
		return opere;
	}

    public void setOpere(Set<OperaVO> opere) {
		this.opere = opere;
	}

    public long getPosizione() {
		return posizione;
	}

    public void setPosizione(long posizione) {
		this.posizione = posizione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(denominazione, idElemento, opere, posizione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementoVO))
			return false;
		ElementoVO other = (ElementoVO) obj;
		return Objects.equals(denominazione, other.denominazione) && idElemento == other.idElemento
				&& Objects.equals(opere, other.opere) && posizione == other.posizione;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ElementoVO\n [idElemento=").append(idElemento).append("\n posizione=").append(posizione)
				.append("\n denominazione=").append(denominazione).append("\n opere=").append(opere).append("]");
		return builder.toString();
	}
	
	
	
}