/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

public class TipoINAILVO {
	
	private String codice;
	private IndirizzoVO indirizzo;

    public TipoINAILVO() {
	}

    public TipoINAILVO(String codice, IndirizzoVO indirizzo) {
		super();
		this.codice = codice;
		this.indirizzo = indirizzo;
	}

    public String getCodice() {
		return codice;
	}

    public void setCodice(String codice) {
		this.codice = codice;
	}

    public IndirizzoVO getIndirizzo() {
		return indirizzo;
	}

    public void setIndirizzo(IndirizzoVO indirizzo) {
		this.indirizzo = indirizzo;
	}
	
}