/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.wizrad;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;

@JsonInclude(Include.ALWAYS)
public class OperaVO extends ParentVO {

	private static final long serialVersionUID = -3961724086179323695L;

	@JsonProperty("id_opera")
	private long idOpera;
	
	private String denominazione;

	@JsonIgnore
	private ElementoVO elemento;
	
	private CategoriaInterventoVO categoria;

    public long getIdOpera() {
		return idOpera;
	}

    public void setIdOpera(long idOpera) {
		this.idOpera = idOpera;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public ElementoVO getElemento() {
		return elemento;
	}

    public void setElemento(ElementoVO elemento) {
		this.elemento = elemento;
	}

    public CategoriaInterventoVO getCategoria() {
		return categoria;
	}

    public void setCategoria(CategoriaInterventoVO categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, denominazione, elemento, idOpera);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OperaVO))
			return false;
		OperaVO other = (OperaVO) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(denominazione, other.denominazione)
				&& Objects.equals(elemento, other.elemento) && idOpera == other.idOpera;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperaVO\n [idOpera=").append(idOpera).append("\n denominazione=").append(denominazione)
				.append("\n elemento=").append(elemento).append("\n categoria=").append(categoria).append("]");
		return builder.toString();
	}

	
	
}