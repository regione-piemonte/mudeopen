/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.csi.mudeopen.mudeopensrvapi.util.Constants;

public class NotificaInviataVO {
	
	private Long identificativoNotifica;
	
	private String cognomeDestinatario;
	
	private String nomeDestinatario;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601)
	private Date dataNotifica;
	
	private String tipoNotifica;
	
	private String statoNotifica;
	
	private String oggettoNotifica;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601)
	private Date dataLettura;
	
	private Boolean esistonoAllegati;
	

	public NotificaInviataVO(Long idEventoIstanza, String destinatarioCognome, String destinatarioNome,
			Date dataNotifica, String tipoNotifica, String stato, String oggettoNotifica, Date dataLettura,
			Boolean allegati) {
		super();
		this.identificativoNotifica = idEventoIstanza;
		this.cognomeDestinatario = destinatarioCognome;
		this.nomeDestinatario = destinatarioNome;
		this.dataNotifica = dataNotifica;
		this.tipoNotifica = tipoNotifica;
		this.statoNotifica = stato;
		this.oggettoNotifica = oggettoNotifica;
		this.dataLettura = dataLettura;
		this.esistonoAllegati = allegati;
	}

	public NotificaInviataVO() {
		super();
	}

	public Long getIdentificativoNotifica() {
		return identificativoNotifica;
	}

	public void setIdentificativoNotifica(Long idEventoIstanza) {
		this.identificativoNotifica = idEventoIstanza;
	}

	public String getCognomeDestinatario() {
		return cognomeDestinatario;
	}

	public void setCognomeDestinatario(String destinatarioCognome) {
		this.cognomeDestinatario = destinatarioCognome;
	}

	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String destinatarioNome) {
		this.nomeDestinatario = destinatarioNome;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public String getStatoNotifica() {
		return statoNotifica;
	}

	public void setStatoNotifica(String stato) {
		this.statoNotifica = stato;
	}

	public String getOggettoNotifica() {
		return oggettoNotifica;
	}

	public void setOggettoNotifica(String oggettoNotifica) {
		this.oggettoNotifica = oggettoNotifica;
	}

	public Date getDataLettura() {
		return dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public Boolean getEsistonoAllegati() {
		return esistonoAllegati;
	}

	public void setEsistonoAllegati(Boolean allegati) {
		this.esistonoAllegati = allegati;
	}
	
	
	
	
}
