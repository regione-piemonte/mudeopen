/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.localizzazione;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.Indirizzo;
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndirizzoVO {

	
	@JsonProperty("codBelfioreComune")
    protected String codBelfioreComune;
	@JsonProperty("dataAggiornamento")
    protected String dataAggiornamento;
	@JsonProperty("indirizzo")
    protected String indirizzo;
	@JsonProperty("numeroCivico")
    protected String numeroCivico;
	@JsonProperty("toponimo")
    protected String toponimo;
	public String getCodBelfioreComune() {
		return codBelfioreComune;
	}
	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}
	public String getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(String dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	public String getToponimo() {
		return toponimo;
	}
	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
	
	public static IndirizzoVO buildIndirizzo(Indirizzo pExt) {
		IndirizzoVO vo=new IndirizzoVO();
		vo.setCodBelfioreComune(pExt.getCodBelfioreComune());
		vo.setDataAggiornamento(pExt.getDataAggiornamento());
		vo.setIndirizzo(pExt.getIndirizzo());
		vo.setNumeroCivico(pExt.getNumeroCivico());
		vo.setToponimo(pExt.getToponimo());		
		return vo;
	}
	
	

	
}
