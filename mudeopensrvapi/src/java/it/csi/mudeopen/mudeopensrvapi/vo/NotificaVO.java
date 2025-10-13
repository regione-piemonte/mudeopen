/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificaVO {
	
	private String mittente;

    //@JsonProperty("identificativo_notifica")
	private Long identificativoNotifica;
	
    //@JsonProperty("cognome_destinatario")
	private String cognomeDestinatario;
	
    //@JsonProperty("nome_destinatario")
	private String nomeDestinatario;
	
	private String oggetto;
	
	private String testo;
	
	private Boolean allegati;
	
	// missing:
    //@JsonProperty("dettaglio_notifica")
	private String dettaglioNotifica;
	

	public NotificaVO(Long identificativoNotifica, String mittente, String cognomeDestinatario,
			String nomeDestinatario, String oggetto, String testo, Boolean allegati, String dettaglioNotifica) {
		super();
		this.identificativoNotifica = identificativoNotifica;
		this.mittente = mittente;
		this.cognomeDestinatario = cognomeDestinatario;
		this.nomeDestinatario = nomeDestinatario;
		this.oggetto = oggetto;
		this.testo = testo;
		this.allegati = allegati;
		this.dettaglioNotifica = dettaglioNotifica;
	}

	public Long getIdentificativoNotifica() {
		return identificativoNotifica;
	}

	public void setIdentificativoNotifica(Long idEventoIstanza) {
		this.identificativoNotifica = idEventoIstanza;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
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

	public Boolean getAllegati() {
		return allegati;
	}

	public void setAllegati(Boolean allegati) {
		this.allegati = allegati;
	}

	public String getDettaglioNotifica() {
		return dettaglioNotifica;
	}

	public void setDettaglioNotifica(String dettaglioNotifica) {
		this.dettaglioNotifica = dettaglioNotifica;
	}
	
	
}
