/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.csi.mudeopen.mudeopensrvapi.util.Constants;

public class AllegatoNotificaVO implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private String tipologia;
	
	private String codiceTipologia;
	
	private String nomeFile;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601)
	private Date dataCaricamento;
	
	private Long identificativo;
	
	private Boolean notificato;
	
	public AllegatoNotificaVO() {
		super();
	}
	
	public AllegatoNotificaVO(String codiceTipologia, String tipologia, String nomeFile, Date dataCaricamento, Long identificativo, Boolean notificato) {
		super();
		
		this.codiceTipologia = codiceTipologia;
		this.tipologia = tipologia;
		this.nomeFile = nomeFile;
		this.dataCaricamento = dataCaricamento;
		this.identificativo = identificativo;
		this.notificato = notificato;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Long getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}

	/**
	  the notificato
	 */
	public Boolean getNotificato() {
		return notificato;
	}

	/**
	 * @param notificato the notificato to set
	 */
	public void setNotificato(Boolean notificato) {
		this.notificato = notificato;
	}

	public String getCodiceTipologia() {
		return codiceTipologia;
	}

	public void setCodiceTipologia(String codiceTipologia) {
		this.codiceTipologia = codiceTipologia;
	}
	
	
	
}
