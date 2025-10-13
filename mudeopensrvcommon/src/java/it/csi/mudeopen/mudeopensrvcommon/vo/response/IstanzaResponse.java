/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(Include.ALWAYS)
public class IstanzaResponse implements Serializable {
	private static final long serialVersionUID = 2756069078986275871L;

	@JsonProperty("id_istanza")
	private Long idIstanza;

	@JsonProperty("tipo_istanza")
	private TipoIstanzaVO tipoIstanza;

	@JsonProperty("data_inserimento_istanza")
	private Date dataInserimentoIstanza;

	private String stato;
	private String step;

	@JsonProperty("json_data")
	private String jsonData;

    public Long getIdIstanza() {
		return idIstanza;
	}

    public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

    public TipoIstanzaVO getTipoIstanza() {
		return tipoIstanza;
	}

    public void setTipoIstanza(TipoIstanzaVO tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

    public Date getDataInserimentoIstanza() {
		return dataInserimentoIstanza;
	}

    public void setDataInserimentoIstanza(Date dataInserimentoIstanza) {
		this.dataInserimentoIstanza = dataInserimentoIstanza;
	}

    public String getStato() {
		return stato;
	}

    public void setStato(String stato) {
		this.stato = stato;
	}

    public String getStep() {
		return step;
	}

    public void setStep(String step) {
		this.step = step;
	}

    public String getJsonData() {
		return jsonData;
	}

    public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	public String toString() {
		return new StringJoiner("\n ", IstanzaResponse.class.getSimpleName() + "[", "]")
				.add("idIstanza=" + idIstanza)
				.add("tipoIstanza=" + tipoIstanza)
				.add("dataInserimentoIstanza=" + dataInserimentoIstanza)
				.add("stato='" + stato + "'")
				.add("step='" + step + "'")
				.add("jsonData='" + jsonData + "'")
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof IstanzaResponse)) return false;
		IstanzaResponse that = (IstanzaResponse) o;
		return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(tipoIstanza, that.tipoIstanza) && Objects.equals(dataInserimentoIstanza, that.dataInserimentoIstanza) && Objects.equals(stato, that.stato) && Objects.equals(step, that.step) && Objects.equals(jsonData, that.jsonData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idIstanza, tipoIstanza, dataInserimentoIstanza, stato, step, jsonData);
	}
}