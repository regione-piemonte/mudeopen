/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.quadri;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;

import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(Include.ALWAYS)
public class AmbitoVO extends ParentVO {
	@JsonProperty("id_ambito")
	private Long idAmbito;

	@JsonProperty("cod_ambito")
	private String codAmbito;

	@JsonProperty("des_ambito")
	private String desAmbito;

	@JsonProperty("des_estesa_ambito")
	private String desEstesaAmbito;

	@JsonProperty("flg_attivo")
	private long flgAttivo;

    public Long getIdAmbito() {
		return idAmbito;
	}

    public void setIdAmbito(Long idAmbito) {
		this.idAmbito = idAmbito;
	}

    public String getCodAmbito() {
		return codAmbito;
	}

    public void setCodAmbito(String codAmbito) {
		this.codAmbito = codAmbito;
	}

    public String getDesAmbito() {
		return desAmbito;
	}

    public void setDesAmbito(String desAmbito) {
		this.desAmbito = desAmbito;
	}

    public String getDesEstesaAmbito() {
		return desEstesaAmbito;
	}

    public void setDesEstesaAmbito(String desEstesaAmbito) {
		this.desEstesaAmbito = desEstesaAmbito;
	}

    public long getFlgAttivo() {
		return flgAttivo;
	}

    public void setFlgAttivo(long flgAttivo) {
		this.flgAttivo = flgAttivo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AmbitoVO ambitoVO = (AmbitoVO) o;
		return flgAttivo == ambitoVO.flgAttivo && Objects.equals(idAmbito, ambitoVO.idAmbito) && Objects.equals(codAmbito, ambitoVO.codAmbito) && Objects.equals(desAmbito, ambitoVO.desAmbito) && Objects.equals(desEstesaAmbito, ambitoVO.desEstesaAmbito);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAmbito, codAmbito, desAmbito, desEstesaAmbito, flgAttivo);
	}

	@Override
	public String toString() {
		return new StringJoiner("\n ", AmbitoVO.class.getSimpleName() + "[", "]")
				.add("idAmbito=" + idAmbito)
				.add("codAmbito='" + codAmbito + "'")
				.add("desAmbito='" + desAmbito + "'")
				.add("desEstesaAmbito='" + desEstesaAmbito + "'")
				.add("flgAttivo=" + flgAttivo)
				.toString();
	}
}