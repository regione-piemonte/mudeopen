/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.AmountSerializer;

public class GetIUVChiamanteEsternoInputVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983528L;

    @JsonProperty("codiceFiscaleEnte")
    private String codiceFiscaleEnte;

    @JsonProperty("causale")
    private String causale;

    @JsonProperty("tipoPagamento")
    private String tipoPagamento;

    @JsonProperty("importo")
    @JsonSerialize(using = AmountSerializer.class)
    private Double importo;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("ragioneSociale")
    private String ragioneSociale;

    @JsonProperty("codiceFiscalePartitaIVAPagatore")
    private String codiceFiscalePartitaIVAPagatore;

    @JsonProperty("email")
    private String email;

    @JsonProperty("identificativoPagamento")
    private String identificativoPagamento;

    @JsonProperty("componentiPagamento")
    private List<ComponentePagamentoVO> componentiPagamento;

	public String getCodiceFiscaleEnte() {
		return codiceFiscaleEnte;
	}

	public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
		this.codiceFiscaleEnte = codiceFiscaleEnte;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCodiceFiscalePartitaIVAPagatore() {
		return codiceFiscalePartitaIVAPagatore;
	}

	public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
		this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}

	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}

	public List<ComponentePagamentoVO> getComponentiPagamento() {
		return componentiPagamento;
	}

	public void setComponentiPagamento(List<ComponentePagamentoVO> componentiPagamento) {
		this.componentiPagamento = componentiPagamento;
	}

	public void addComponentePagamento(ComponentePagamentoVO componentiPagamento) {
		if(this.componentiPagamento == null)
			this.componentiPagamento = new ArrayList<>();
		
		this.componentiPagamento.add(componentiPagamento);
	}

	
}