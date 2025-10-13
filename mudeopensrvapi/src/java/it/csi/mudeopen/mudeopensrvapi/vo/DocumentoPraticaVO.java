/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentoPraticaVO implements Serializable {
   //@JsonProperty("codice_tipo_documento")
    private String codiceTipologia;

    //@JsonProperty("tipo_allegato")
    private String tipologia;

    //@JsonProperty("nome_file")
    private String nomeFile;

    //@JsonProperty("data_caricamento")
    private String dataCaricamento;

    private Boolean notificato;

    private Long identificativo;

	public DocumentoPraticaVO(String codiceTipoDocumento, String tipoDocumento, String nomeFileDocumento,
			String dataCaricamento, Boolean notificato, Long identificativo) {
		super();
		this.codiceTipologia = codiceTipoDocumento;
		this.tipologia = tipoDocumento;
		this.nomeFile = nomeFileDocumento;
		this.dataCaricamento = dataCaricamento;
		this.notificato = notificato;
		this.identificativo = identificativo;
	}

	

	/**
	  the codiceTipologia
	 */
	public String getCodiceTipologia() {
		return codiceTipologia;
	}

	/**
	 * @param codiceTipologia the codiceTipologia to set
	 */
	public void setCodiceTipologia(String codiceTipologia) {
		this.codiceTipologia = codiceTipologia;
	}

	/**
	  the tipologia
	 */
	public String getTipologia() {
		return tipologia;
	}

	/**
	 * @param tipologia the tipologia to set
	 */
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	/**
	  the nomeFile
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @param nomeFile the nomeFile to set
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(String dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
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

	/**
	  the identificativo
	 */
	public Long getIdentificativo() {
		return identificativo;
	}

	/**
	 * @param identificativo the identificativo to set
	 */
	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}
	
	

 }