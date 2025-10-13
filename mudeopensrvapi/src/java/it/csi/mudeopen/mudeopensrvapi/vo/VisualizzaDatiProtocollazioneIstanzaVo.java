/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.csi.mudeopen.mudeopensrvapi.util.Constants;

public class VisualizzaDatiProtocollazioneIstanzaVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601)
	private Date dataVariazioneStato;
	
	private String oggettoNotifica;
	
	private String messaggioNotifica;
	
	private String numeroProtocollo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT_ISO_8601, timezone = "Europe/Rome")
	private Date dataProtocollo;

	public Date getDataVariazioneStato() {
		return dataVariazioneStato;
	}

	public void setDataVariazioneStato(Date dataVariazioneStato) {
		this.dataVariazioneStato = dataVariazioneStato;
	}

	public String getOggettoNotifica() {
		return oggettoNotifica;
	}

	public void setOggettoNotifica(String oggettoNotifica) {
		this.oggettoNotifica = oggettoNotifica;
	}

	public String getMessaggioNotifica() {
		return messaggioNotifica;
	}

	public void setMessaggioNotifica(String messaggioNotifica) {
		this.messaggioNotifica = messaggioNotifica;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
	
}
