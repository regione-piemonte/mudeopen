/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza;

import java.sql.Timestamp;
import java.util.List;

public class IstanzaExtVO {

	protected String codIstatComune;
	
	protected String tipologiaIstanza;
	
	protected String numeroIstanza;
	
	protected String numeroFascicoloIntervento;
	
	protected String speciePratica;
	
	protected Integer annoIntervento;
	
	protected Integer annoIstanza;
	
	protected String tipoIntestatario;
	
	protected String intestatarioCognome;
	
	protected String intestatarioNome;
	
	protected String intestatarioRagioneSociale;
	
	protected String professionistaCognome;
	
	protected String professionistaNome;
	
	protected String compilanteCodiceFiscale;
	
	protected String ubicazioneSedime;
	
	protected String ubicazioneDescVia;
	
	protected String ubicazioneNumeroCivico;
	
	protected String ubicazioneBis;
	
	protected String ubicazioneInterno;
	
	protected String operePrecarie;
	
	protected String numeroMudeIstanzaPrincipale;
	
	protected List<TraccXMLVO> tracciatiXML; //solo xml unico
	
	protected String timestampInserimento;

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public void setCodIstatComune(String istatComune) {
		this.codIstatComune = istatComune;
	}

	public String getTipologiaIstanza() {
		return tipologiaIstanza;
	}

	public void setTipologiaIstanza(String tipologiaIstanza) {
		this.tipologiaIstanza = tipologiaIstanza;
	}

	public String getNumeroFascicoloIntervento() {
		return numeroFascicoloIntervento;
	}

	public void setNumeroFascicoloIntervento(String numeroFascicoloIntervento) {
		this.numeroFascicoloIntervento = numeroFascicoloIntervento;
	}

	public String getSpeciePratica() {
		return speciePratica;
	}

	public void setSpeciePratica(String speciePratica) {
		this.speciePratica = speciePratica;
	}

	public Integer getAnnoIntervento() {
		return annoIntervento;
	}

	public void setAnnoIntervento(Integer annoIntervento) {
		this.annoIntervento = annoIntervento;
	}

	public Integer getAnnoIstanza() {
		return annoIstanza;
	}

	public void setAnnoIstanza(Integer annoIstanza) {
		this.annoIstanza = annoIstanza;
	}

	public String getTipoIntestatario() {
		return tipoIntestatario;
	}

	public void setTipoIntestatario(String tipoIntestatario) {
		this.tipoIntestatario = tipoIntestatario;
	}

	public String getIntestatarioCognome() {
		return intestatarioCognome;
	}

	public void setIntestatarioCognome(String intestatarioCognome) {
		this.intestatarioCognome = intestatarioCognome;
	}

	public String getIntestatarioNome() {
		return intestatarioNome;
	}

	public void setIntestatarioNome(String intestatarioNome) {
		this.intestatarioNome = intestatarioNome;
	}

	public String getIntestatarioRagioneSociale() {
		return intestatarioRagioneSociale;
	}

	public void setIntestatarioRagioneSociale(String intestatarioRagioneSociale) {
		this.intestatarioRagioneSociale = intestatarioRagioneSociale;
	}

	public String getProfessionistaCognome() {
		return professionistaCognome;
	}

	public void setProfessionistaCognome(String professionistaCognome) {
		this.professionistaCognome = professionistaCognome;
	}

	public String getProfessionistaNome() {
		return professionistaNome;
	}

	public void setProfessionistaNome(String professionistaNome) {
		this.professionistaNome = professionistaNome;
	}

	public String getUbicazioneSedime() {
		return ubicazioneSedime;
	}

	public void setUbicazioneSedime(String ubicazioneSedime) {
		this.ubicazioneSedime = ubicazioneSedime;
	}

	public String getUbicazioneDescVia() {
		return ubicazioneDescVia;
	}

	public void setUbicazioneDescVia(String ubicazioneDescvia) {
		this.ubicazioneDescVia = ubicazioneDescvia;
	}

	public String getUbicazioneNumeroCivico() {
		return ubicazioneNumeroCivico;
	}

	public void setUbicazioneNumeroCivico(String ubicazioneNumeroCivico) {
		this.ubicazioneNumeroCivico = ubicazioneNumeroCivico;
	}

	public String getUbicazioneBis() {
		return ubicazioneBis;
	}

	public void setUbicazioneBis(String ubicazioneBis) {
		this.ubicazioneBis = ubicazioneBis;
	}

	public String getUbicazioneInterno() {
		return ubicazioneInterno;
	}

	public void setUbicazioneInterno(String ubicazioneInterno) {
		this.ubicazioneInterno = ubicazioneInterno;
	}

	public String getOperePrecarie() {
		return operePrecarie;
	}

	public void setOperePrecarie(String operePrecarie) {
		this.operePrecarie = operePrecarie;
	}

	public String getNumeroMudeIstanzaPrincipale() {
		return numeroMudeIstanzaPrincipale;
	}

	public void setNumeroMudeIstanzaPrincipale(String numeroMudeIstanzaPrincipale) {
		this.numeroMudeIstanzaPrincipale = numeroMudeIstanzaPrincipale;
	}

	public List<TraccXMLVO> getTracciatiXML() {
		return tracciatiXML;
	}

	public void setTracciatiXML(List<TraccXMLVO> tracciatiXml) {
		this.tracciatiXML = tracciatiXml;
	}

	public String getTimestampInserimento() {
		return timestampInserimento;
	}

	public void setTimestampInserimento(String timestampInserimento) {
		this.timestampInserimento = timestampInserimento;
	}

	public String getNumeroIstanza() {
		return numeroIstanza;
	}

	public void setNumeroIstanza(String numeroIstanza) {
		this.numeroIstanza = numeroIstanza;
	}

	public String getCompilanteCodiceFiscale() {
		return compilanteCodiceFiscale;
	}

	public void setCompilanteCodiceFiscale(String compilanteCodiceFiscale) {
		this.compilanteCodiceFiscale = compilanteCodiceFiscale;
	}
	
	
	
}
