/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.util.ArrayList;
import java.util.List;

public class InserimentoNotificaVO {
	
	private String numeroIstanza;
	
	private String mittente;
	
	private String tipoNotifica;
	
	private String oggetto;
	
	private String testo;
	
	private List<String> nomeDocumento = new ArrayList<>();

	public String getNumeroIstanza() {
		return numeroIstanza;
	}

	public void setNumeroIstanza(String numeroIstanza) {
		this.numeroIstanza = numeroIstanza;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
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

	public List<String> getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(List<String> nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	
}
