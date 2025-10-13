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
public class AdempimentoVO extends ParentVO {

	private static final long serialVersionUID = -3961724086179323695L;

	@JsonProperty("id_adempimento")
	private long idAdempimento;
	
	
	private long posizione;
	private String tipologia;
	private String ambito;
	private String denominazione;
	
	@JsonProperty("regime_aggiuntivo")
	private RegimeAggiuntivoVO regimeAggiuntivo;

    public long getIdAdempimento() {
		return idAdempimento;
	}

    public void setIdAdempimento(long idAdempimento) {
		this.idAdempimento = idAdempimento;
	}

    public String getTipologia() {
		return tipologia;
	}

    public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

    public String getAmbito() {
		return ambito;
	}

    public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public RegimeAggiuntivoVO getRegimeAggiuntivo() {
		return regimeAggiuntivo;
	}

    public void setRegimeAggiuntivo(RegimeAggiuntivoVO regimeAggiuntivo) {
		this.regimeAggiuntivo = regimeAggiuntivo;
	}

    public long getPosizione() {
		return posizione;
	}

    public void setPosizione(long posizione) {
		this.posizione = posizione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ambito, denominazione, idAdempimento, posizione, regimeAggiuntivo, tipologia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AdempimentoVO))
			return false;
		AdempimentoVO other = (AdempimentoVO) obj;
		return Objects.equals(ambito, other.ambito) && Objects.equals(denominazione, other.denominazione)
				&& idAdempimento == other.idAdempimento && posizione == other.posizione
				&& Objects.equals(regimeAggiuntivo, other.regimeAggiuntivo)
				&& Objects.equals(tipologia, other.tipologia);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdempimentoVO\n [idAdempimento=").append(idAdempimento).append("\n posizione=").append(posizione)
				.append("\n tipologia=").append(tipologia).append("\n ambito=").append(ambito).append("\n denominazione=")
				.append(denominazione).append("\n regimeAggiuntivo=").append(regimeAggiuntivo).append("]");
		return builder.toString();
	}
	
	
	
}