/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.csi.mudeopen.mudeopensrvapi.util.Constants;

public class IstanzaEstesaVO implements Serializable {
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private String numeroMude;
	
	private String tipoIstanza;
	
	private String codiceTipoIstanza; 
	
	private String speciePratica;
	
	private String identificativoPratica; 
	
	private String tipoIntestatario;
	
	private String intestatario;
	
	private String nome;

	private String numeroCivico;
	
	private String bis = "";
	
	private String interno = "";
	
	private String sedime = "";
	private String via = "";
	private String codiceSpeciePratica = "";
	private String numeroIntervento = "";
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601)
	private Date dataRicezione;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601)
	private Date dataUltimaVariazioneStato;
	private String statoIstanza; 
	private String codiceStatoIstanza;
	private String occupazioneSuoloPubblico;
	private List<TipoTracciatoXML> tipiTracciatiXML = new ArrayList<>();
	
	public String getTipoIstanza() {
		return tipoIstanza;
	}
	public void setTipoIstanza(String tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}
	public String getCodiceTipoIstanza() {
		return codiceTipoIstanza;
	}
	public void setCodiceTipoIstanza(String codiceTipoIstanza) {
		this.codiceTipoIstanza = codiceTipoIstanza;
	}
	public String getSpeciePratica() {
		return speciePratica;
	}
	public void setSpeciePratica(String speciePratica) {
		this.speciePratica = speciePratica;
	}
	public String getIdentificativoPratica() {
		return identificativoPratica;
	}
	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	}
	public String getTipoIntestatario() {
		return tipoIntestatario;
	}
	public void setTipoIntestatario(String tipoIntestatario) {
		this.tipoIntestatario = tipoIntestatario;
	}
	public String getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	public String getBis() {
		return bis;
	}
	public void setBis(String bis) {
		this.bis = bis;
	}
	public String getInterno() {
		return interno;
	}
	public void setInterno(String interno) {
		this.interno = interno;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public Date getDataUltimaVariazioneStato() {
		return dataUltimaVariazioneStato;
	}
	public void setDataUltimaVariazioneStato(Date dataUltimaVariazioneStato) {
		this.dataUltimaVariazioneStato = dataUltimaVariazioneStato;
	}
	public String getStatoIstanza() {
		return statoIstanza;
	}
	public void setStatoIstanza(String statoIstanza) {
		this.statoIstanza = statoIstanza;
	}
	public String getCodiceStatoIstanza() {
		return codiceStatoIstanza;
	}
	public void setCodiceStatoIstanza(String codiceStatoIstanza) {
		this.codiceStatoIstanza = codiceStatoIstanza;
	}

	public String getOccupazioneSuoloPubblico() {
		return occupazioneSuoloPubblico;
	}
	public void setOccupazioneSuoloPubblico(String occupazioneSuoloPubblico) {
		this.occupazioneSuoloPubblico = occupazioneSuoloPubblico;
	}
	public List<TipoTracciatoXML> getTipiTracciatiXML() {
		return tipiTracciatiXML;
	}
	public void setTipiTracciatiXML(List<TipoTracciatoXML> tipiTracciatiXML) {
		this.tipiTracciatiXML = tipiTracciatiXML;
	}
	public void addTipiTracciatiXML(TipoTracciatoXML tipiTracciatiXML) {
		this.tipiTracciatiXML.add(tipiTracciatiXML);
	}
	public String getNumeroMude() {
		return numeroMude;
	}
	public void setNumeroMude(String numeroMude) {
		this.numeroMude = numeroMude;
	}
	public String getSedime() {
		return sedime;
	}
	public void setSedime(String sedime) {
		this.sedime = sedime;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getCodiceSpeciePratica() {
		return codiceSpeciePratica;
	}
	public void setCodiceSpeciePratica(String codiceSpeciePratica) {
		this.codiceSpeciePratica = codiceSpeciePratica;
	}
	public String getNumeroIntervento() {
		return numeroIntervento;
	}
	public void setNumeroIntervento(String numeroIntervento) {
		this.numeroIntervento = numeroIntervento;
	}
 }