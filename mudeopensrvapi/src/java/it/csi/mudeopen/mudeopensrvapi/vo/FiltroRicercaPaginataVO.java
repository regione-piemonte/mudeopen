/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.util.List;

public class FiltroRicercaPaginataVO {

    protected String codIstatComune;

    protected String numeroFascicoloIntervento;

    protected Integer annoIntervento;

    protected Integer annoIstanza;

    protected String numeroPraticaComunale;

    protected Integer annoPraticaComunale;

    protected List<String> statiIstanza;

    protected List<String> tipologieIstanza;

    protected String intestatarioIstanza;

    protected String nomeIntestatarioIstanza;

    protected String tipoIntestatarioIstanza;

    protected String indirizzo;

    protected String numeroIstanza; 

    protected Integer maxNumIstanzeDaRestituire;

    protected Integer numPagina;

    protected Long idFruitore;

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public void setCodIstatComune(String istatComune) {
		this.codIstatComune = istatComune;
	}

	public String getNumeroFascicoloIntervento() {
		return numeroFascicoloIntervento;
	}

	public void setNumeroFascicoloIntervento(String numeroFascicoloIntervento) {
		this.numeroFascicoloIntervento = numeroFascicoloIntervento;
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

	public String getNumeroPraticaComunale() {
		return numeroPraticaComunale;
	}

	public void setNumeroPraticaComunale(String numeroPraticaComunale) {
		this.numeroPraticaComunale = numeroPraticaComunale;
	}

	public Integer getAnnoPraticaComunale() {
		return annoPraticaComunale;
	}

	public void setAnnoPraticaComunale(Integer annoPraticaComunale) {
		this.annoPraticaComunale = annoPraticaComunale;
	}

	public List<String> getStatiIstanza() {
		return statiIstanza;
	}

	public void setStatiIstanza(List<String> statiIstanza) {
		this.statiIstanza = statiIstanza;
	}

	public List<String> getTipologieIstanza() {
		return tipologieIstanza;
	}

	public void setTipologieIstanza(List<String> tipologieIstanza) {
		this.tipologieIstanza = tipologieIstanza;
	}

	public String getIntestatarioIstanza() {
		return intestatarioIstanza;
	}

	public void setIntestatarioIstanza(String intestatarioIstanza) {
		this.intestatarioIstanza = intestatarioIstanza;
	}

	public String getNomeIntestatarioIstanza() {
		return nomeIntestatarioIstanza;
	}

	public void setNomeIntestatarioIstanza(String nomeIntestatarioIstanza) {
		this.nomeIntestatarioIstanza = nomeIntestatarioIstanza;
	}

	public String getTipoIntestatarioIstanza() {
		return tipoIntestatarioIstanza;
	}

	public void setTipoIntestatarioIstanza(String tipoIntestatario) {
		this.tipoIntestatarioIstanza = tipoIntestatario;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroIstanza() {
		return numeroIstanza;
	}

	public void setNumeroIstanza(String numeroIstanza) {
		this.numeroIstanza = numeroIstanza;
	}

	public Integer getMaxNumIstanzeDaRestituire() {
		return maxNumIstanzeDaRestituire;
	}

	public void setMaxNumIstanzeDaRestituire(Integer maxNumIstanzeDaRestituire) {
		this.maxNumIstanzeDaRestituire = maxNumIstanzeDaRestituire;
	}

	public Integer getNumPagina() {
		return numPagina;
	}

	public void setNumPagina(Integer numPagina) {
		this.numPagina = numPagina;
	}

	public Long getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(Long idFruitore) {
		this.idFruitore = idFruitore;
	}
}
