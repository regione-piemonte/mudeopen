/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;
public class DatiModificaStatoIstanza implements Serializable {

//	private static final long serialVersionUID = 1L;

	private String numeroIstanza;
	
	private String nuovoStato;
	
	private String oggetto;
	
	private String testo;
	
	private String numeroPraticaComunale;
	
	private Integer annoPratica;
	
	private String numeroProtocollo;
	
	private String dataProtocollo;
	
	private String responsabileProcedimento;

	public String getNumeroIstanza() {
		return numeroIstanza;
	}

	public void setNumeroIstanza(String numeroIstanza) {
		this.numeroIstanza = numeroIstanza;
	}

	public String getNuovoStato() {
		return nuovoStato;
	}

	public void setNuovoStato(String nuovoStato) {
		this.nuovoStato = nuovoStato;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getNumeroPraticaComunale() {
		return numeroPraticaComunale;
	}

	public void setNumeroPraticaComunale(String numeroPratica) {
		this.numeroPraticaComunale = numeroPratica;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	/**
	  the dataProtocollo
	 */
	public String getDataProtocollo() {
		return dataProtocollo;
	}

	/**
	 * @param dataProtocollo the dataProtocollo to set
	 */
	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}

	public void setResponsabileProcedimento(String responsabileDelProcedimento) {
		this.responsabileProcedimento = responsabileDelProcedimento;
	}

	public Integer getAnnoPratica() {
		return annoPratica;
	}

	public void setAnnoPratica(Integer annoPratica) {
		this.annoPratica = annoPratica;
	}
	
	
}
