/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadatiFineLavoriVO extends BaseMetadatiVO {
	@JsonProperty("numero_Pratica_MUDE")
	private String numeroPraticaMUDE;

	@JsonProperty("anno_Pratica_MUDE")
	private String annoPraticaMUDE;

	@JsonProperty("denom_comune")
	private String denomComune;

	@JsonProperty("email_comune")
	private String emailComune;

	@JsonProperty("titolario")
	private String titolario;

	@JsonProperty("serie_fascicoli")
	private String serieFascicoli;

	@JsonProperty("numero_fascicolo")
	private String numeroFascicolo;
	
	@JsonProperty("ice")
	private String ice;
	
	@JsonProperty("cognome_pm")
	private String cognomePm;

	@JsonProperty("nome_pm")
	private String nomePm;

	@JsonProperty("pec_pm")
	private String pecPm;

	@JsonProperty("email_pm")
	private String emailPm;

	@JsonProperty("desc_titolo_abilitativo")
	private String descTitoloAbilitativo;

	@JsonProperty("data_reg_titolo_abilitativo")
	private String dataRegTitoloAbilitativo;

	@JsonProperty("opere")
	private String opere;

	@JsonProperty("descrizione_ente")
	private String descrizioneEnte;

	@JsonProperty("elenco_email_ente")
	private String elencoEmailEnte;

	@JsonProperty("url_bo")
	private String urlBo;

	@JsonProperty("url_bo_details")
	private String urlBoDetails;

	@JsonProperty("gruppo_utr")
	private String gruppoUtr;

	public String getNumeroPraticaMUDE() {
		return numeroPraticaMUDE;
	}

	public void setNumeroPraticaMUDE(String numeroPraticaMUDE) {
		this.numeroPraticaMUDE = numeroPraticaMUDE;
	}

	public String getAnnoPraticaMUDE() {
		return annoPraticaMUDE;
	}

	public void setAnnoPraticaMUDE(String annoPraticaMUDE) {
		this.annoPraticaMUDE = annoPraticaMUDE;
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

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

	public String getCognomePm() {
		return cognomePm;
	}

	public void setCognomePm(String cognomePm) {
		this.cognomePm = cognomePm;
	}

	public String getNomePm() {
		return nomePm;
	}

	public void setNomePm(String nomePm) {
		this.nomePm = nomePm;
	}

	public String getPecPm() {
		return pecPm;
	}

	public void setPecPm(String pecPm) {
		this.pecPm = pecPm;
	}

	public String getEmailPm() {
		return emailPm;
	}

	public void setEmailPm(String emailPm) {
		this.emailPm = emailPm;
	}

	public String getDescTitoloAbilitativo() {
		return descTitoloAbilitativo;
	}

	public void setDescTitoloAbilitativo(String descTitoloAbilitativo) {
		this.descTitoloAbilitativo = descTitoloAbilitativo;
	}

	public String getDataRegTitoloAbilitativo() {
		return dataRegTitoloAbilitativo;
	}

	public void setDataRegTitoloAbilitativo(String dataRegTitoloAbilitativo) {
		this.dataRegTitoloAbilitativo = dataRegTitoloAbilitativo;
	}

	public String getOpere() {
		return opere;
	}

	public void setOpere(String opere) {
		this.opere = opere;
	}

	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}

	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
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

	
}