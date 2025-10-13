/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

public class TipoINPSVO {
	
	private String matricola;
	private String posizioneContributiva;
	private IndirizzoVO indirizzo;

    public TipoINPSVO() {
	}

    public TipoINPSVO(String matricola, String posizioneContributiva, IndirizzoVO indirizzo) {
		super();
		this.matricola = matricola;
		this.posizioneContributiva = posizioneContributiva;
		this.indirizzo = indirizzo;
	}

    public String getMatricola() {
		return matricola;
	}

    public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

    public String getPosizioneContributiva() {
		return posizioneContributiva;
	}

    public void setPosizioneContributiva(String posizioneContributiva) {
		this.posizioneContributiva = posizioneContributiva;
	}

    public IndirizzoVO getIndirizzo() {
		return indirizzo;
	}

    public void setIndirizzo(IndirizzoVO indirizzo) {
		this.indirizzo = indirizzo;
	}

	
	
}