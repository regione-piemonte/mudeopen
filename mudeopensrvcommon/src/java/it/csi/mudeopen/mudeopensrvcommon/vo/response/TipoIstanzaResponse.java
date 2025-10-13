/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;

import java.util.Objects;
import java.util.StringJoiner;

public class TipoIstanzaResponse {
	
	private String id;
	private String denominazione;

    public TipoIstanzaResponse(TipoIstanzaVO tipoIstanza) {
		super();
		this.id = tipoIstanza.getId();
		this.denominazione = tipoIstanza.getDescrizione();
	}

    public String getId() {
		return id;
	}

    public void setId(String id) {
		this.id = id;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	@Override
	public String toString() {
		return new StringJoiner("\n ", TipoIstanzaResponse.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("denominazione='" + denominazione + "'")
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TipoIstanzaResponse)) return false;
		TipoIstanzaResponse that = (TipoIstanzaResponse) o;
		return Objects.equals(id, that.id) && Objects.equals(denominazione, that.denominazione);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, denominazione);
	}
}