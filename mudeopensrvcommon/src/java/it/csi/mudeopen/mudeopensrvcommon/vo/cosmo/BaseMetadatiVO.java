/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseMetadatiVO extends IntestatarioVO {
	@JsonProperty("oggetto_fascicolo")
	private String oggettoFascicolo;

	@JsonProperty("intestazione")
	private String intestazione;
	
	@JsonProperty("denom_comune")
	private String denomComune;

	@JsonProperty("email_comune")
	private String emailComune;

	@JsonProperty("sedime")
	private String sedime;

	@JsonProperty("descrizione")
	private String indirizzo;

	@JsonProperty("civico")
	private String civico;
	
	@JsonProperty("destinatari")
	private List<String> destinatari;
	
	@JsonProperty("destinatario")
	private String destinatario;
	
	@JsonProperty("destinatari_pc")
	private List<String> destinatariPC;
	
	@JsonProperty("destinatario_pc")
	private String destinatarioPC;

	@JsonProperty("cognome_rappresentante")
	private String cognomeRappresentante;

	@JsonProperty("nome_rappresentante")
	private String nomeRappresentante;

	@JsonProperty("email_rappresentante")
	private String emailRappresentante;
	
	@JsonProperty("ice")
	private String ice;
	
	@JsonProperty("titolario")
	private String titolario;

	@JsonProperty("serie_fascicoli")
	private String serieFascicoli;

	@JsonProperty("numero_fascicolo")
	private String numeroFascicolo;
	
	@JsonProperty("codice_istanza_riferimento")
	private String codiceIstanzaRiferimento;

	@JsonProperty("descrizione_ente")
	private String descrizioneEnte;

	@JsonProperty("indirizzo_ente")
	private List<String> indirizzoEnte;

	@JsonProperty("elenco_email_ente")
	private String elencoEmailEnte;

	@JsonProperty("url_bo")
	private String urlBo;

	@JsonProperty("url_bo_details")
	private String urlBoDetails;

	@JsonProperty("gruppo_utr")
	private String gruppoUtr;

	// array for destinatari
	@JsonProperty("tipo_destinatario")
	private List<String> tipoDestinatario = new ArrayList<>();
	
	@JsonProperty("denominazione_dest_pg")
	private List<String> denominazioneDestPg = new ArrayList<>();
	
	@JsonProperty("indirizzo_telematico_dest_pg")
	private List<String> indirizzoTelematicoDestPg = new ArrayList<>();
	
	@JsonProperty("tipo_indirizzo_telematico_dest_pg")
	private List<String> tipoIndirizzoTelematicoDestPg = new ArrayList<>();
	
	@JsonProperty("ruolo_destinatario_pg")
	private List<Integer> ruoloDestinatarioPg = new ArrayList<>();
	
	@JsonProperty("codice_fiscale_dest_pf")
	private List<String> codiceFiscaleDestPf = new ArrayList<>();
	
	@JsonProperty("cognome_dest_pf")
	private List<String> cognomeDestPf = new ArrayList<>();
	
	@JsonProperty("nome_dest_pf")
	private List<String> nomeDestPf = new ArrayList<>();
	
	@JsonProperty("indirizzo_telematico_dest_pf")
	private List<String> indirizzoTelematicoDestPf = new ArrayList<>();;
	
	@JsonProperty("tipo_indirizzo_telematico_dest_pf")
	private List<String> tipoIndirizzoTelematicoDestPf = new ArrayList<>();;
	
	@JsonProperty("ruolo_destinatario_pf")
	private List<Integer> ruoloDestinatarioPf = new ArrayList<>();;

	@JsonProperty("assegnatari")
	private List<User> assegnatari = new ArrayList<>();

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public String getDenomComune() {
		return denomComune;
	}

	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

	public String getEmailComune() {
		return emailComune;
	}

	public void setEmailComune(String emailComune) {
		this.emailComune = emailComune;
	}

	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
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

	public String getTitolario() {
		return titolario;
	}

	public void setTitolario(String titolario) {
		this.titolario = titolario;
	}

	public String getSerieFascicoli() {
		return serieFascicoli;
	}

	public void setSerieFascicoli(String serieFascicoli) {
		this.serieFascicoli = serieFascicoli;
	}

	public String getNumeroFascicolo() {
		return numeroFascicolo;
	}

	public void setNumeroFascicolo(String numeroFascicolo) {
		this.numeroFascicolo = numeroFascicolo;
	}

	public String getCodiceIstanzaRiferimento() {
		return codiceIstanzaRiferimento;
	}

	public void setCodiceIstanzaRiferimento(String codiceIstanzaRiferimento) {
		this.codiceIstanzaRiferimento = codiceIstanzaRiferimento;
	}

	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}

	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}

	public List<String> getIndirizzoEnte() {
		return indirizzoEnte;
	}

	public void setIndirizzoEnte(List<String> indirizzoEnte) {
		this.indirizzoEnte = indirizzoEnte;
	}

	public String getElencoEmailEnte() {
		return elencoEmailEnte;
	}

	public void setElencoEmailEnte(String elencoEmailEnte) {
		this.elencoEmailEnte = elencoEmailEnte;
	}

	public String getUrlBo() {
		return urlBo;
	}

	public void setUrlBo(String urlBo) {
		this.urlBo = urlBo;
	}

	public String getUrlBoDetails() {
		return urlBoDetails;
	}

	public void setUrlBoDetails(String urlBoDetails) {
		this.urlBoDetails = urlBoDetails;
	}

	public String getGruppoUtr() {
		return gruppoUtr;
	}

	public void setGruppoUtr(String gruppoUtr) {
		this.gruppoUtr = gruppoUtr;
	}

	public List<String> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<String> destinatari) {
		this.destinatari = destinatari;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public List<String> getDestinatariPC() {
		return destinatariPC;
	}

	public void setDestinatariPC(List<String> destinatariPC) {
		this.destinatariPC = destinatariPC;
	}

	public String getDestinatarioPC() {
		return destinatarioPC;
	}

	public void setDestinatarioPC(String destinatarioPC) {
		this.destinatarioPC = destinatarioPC;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

	public String getOggettoFascicolo() {
		return oggettoFascicolo;
	}

	public void setOggettoFascicolo(String oggettoFascicolo) {
		this.oggettoFascicolo = oggettoFascicolo;
	}

	public List<String> getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(List<String> tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public List<String> getDenominazioneDestPg() {
		return denominazioneDestPg;
	}

	public void setDenominazioneDestPg(List<String> denominazioneDestPg) {
		this.denominazioneDestPg = denominazioneDestPg;
	}

	public List<String> getIndirizzoTelematicoDestPg() {
		return indirizzoTelematicoDestPg;
	}

	public void setIndirizzoTelematicoDestPg(List<String> indirizzoTelematicoDestPg) {
		this.indirizzoTelematicoDestPg = indirizzoTelematicoDestPg;
	}

	public List<String> getTipoIndirizzoTelematicoDestPg() {
		return tipoIndirizzoTelematicoDestPg;
	}

	public void setTipoIndirizzoTelematicoDestPg(List<String> tipoIndirizzoTelematicoDestPg) {
		this.tipoIndirizzoTelematicoDestPg = tipoIndirizzoTelematicoDestPg;
	}

	public List<Integer> getRuoloDestinatarioPg() {
		return ruoloDestinatarioPg;
	}

	public void setRuoloDestinatarioPg(List<Integer> ruoloDestinatarioPg) {
		this.ruoloDestinatarioPg = ruoloDestinatarioPg;
	}

	public List<String> getCodiceFiscaleDestPf() {
		return codiceFiscaleDestPf;
	}

	public void setCodiceFiscaleDestPf(List<String> codiceFiscaleDestPf) {
		this.codiceFiscaleDestPf = codiceFiscaleDestPf;
	}

	public List<String> getCognomeDestPf() {
		return cognomeDestPf;
	}

	public void setCognomeDestPf(List<String> cognomeDestPf) {
		this.cognomeDestPf = cognomeDestPf;
	}

	public List<String> getNomeDestPf() {
		return nomeDestPf;
	}

	public void setNomeDestPf(List<String> nomeDestPf) {
		this.nomeDestPf = nomeDestPf;
	}

	public List<String> getIndirizzoTelematicoDestPf() {
		return indirizzoTelematicoDestPf;
	}

	public void setIndirizzoTelematicoDestPf(List<String> indirizzoTelematicoDestPf) {
		this.indirizzoTelematicoDestPf = indirizzoTelematicoDestPf;
	}

	public List<String> getTipoIndirizzoTelematicoDestPf() {
		return tipoIndirizzoTelematicoDestPf;
	}

	public void setTipoIndirizzoTelematicoDestPf(List<String> tipoIndirizzoTelematicoDestPf) {
		this.tipoIndirizzoTelematicoDestPf = tipoIndirizzoTelematicoDestPf;
	}

	public List<Integer> getRuoloDestinatarioPf() {
		return ruoloDestinatarioPf;
	}

	public void setRuoloDestinatarioPf(List<Integer> ruoloDestinatarioPf) {
		this.ruoloDestinatarioPf = ruoloDestinatarioPf;
	}

	public List<User> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<User> assegnatari) {
		this.assegnatari = assegnatari;
	}

}