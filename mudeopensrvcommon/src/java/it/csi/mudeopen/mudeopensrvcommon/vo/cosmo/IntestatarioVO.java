/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntestatarioVO {
	
	@JsonProperty("tipo_intestatario")
	private String tipo;

	@JsonProperty("cognome_intestatario")
	private String cognome;

	@JsonProperty("nome_intestatario")
	private String nome;

	@JsonProperty("email_intestatario")
	private String email;
	
	@JsonProperty("codice_fiscale_richiedente_PF")
	private String cf;
	
	@JsonProperty("ragione_sociale")
	private String ragioneSociale;

	@JsonProperty("pec")
	private String pec;

	@JsonProperty("cognome_rappresentante")
	private String cognomeRappresentante;

	@JsonProperty("nome_rappresentante")
	private String nomeRappresentante;

	@JsonProperty("email_rappresentante")
	private String emailRappresentante;

	@JsonProperty("codice_fiscale_piva_richiedente_PG")
	private String piva;
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public String getEmailRappresentante() {
		return emailRappresentante;
	}

	public void setEmailRappresentante(String emailRappresentante) {
		this.emailRappresentante = emailRappresentante;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}
}