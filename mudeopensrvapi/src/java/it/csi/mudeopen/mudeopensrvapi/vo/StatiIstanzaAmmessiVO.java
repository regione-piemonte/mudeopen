/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type AllegatoVO vo.
 */
public class StatiIstanzaAmmessiVO implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private String codice;
	private String descrizione;
	private String oggettoProposto;
	private String messaggioProposto;

	public StatiIstanzaAmmessiVO() {
		super();
	}
	
	public StatiIstanzaAmmessiVO(String codice, String descrizione, String oggettoProposto, String messaggioProposto) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.oggettoProposto = oggettoProposto;
		this.messaggioProposto = messaggioProposto;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codiceStatoIstanza) {
		this.codice = codiceStatoIstanza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizioneStatoIstanza) {
		this.descrizione = descrizioneStatoIstanza;
	}

	public String getOggettoProposto() {
		return oggettoProposto;
	}

	public void setOggettoProposto(String oggettoProposto) {
		this.oggettoProposto = oggettoProposto;
	}

	public String getMessaggioProposto() {
		return messaggioProposto;
	}

	public void setMessaggioProposto(String messaggioProposto) {
		this.messaggioProposto = messaggioProposto;
	}
	
	

}