/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.pratiche;

import java.util.List;

public class RicercaPraticheFiltroCommon {

    protected String codIstat;

    protected String numeroPratica;

    protected String annoPratica;

    protected List<String> statiIstanza;

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public String getAnnoPratica() {
		return annoPratica;
	}

	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}

	public List<String> getStatiIstanza() {
		return statiIstanza;
	}

	public void setStatiIstanza(List<String> statiIstanza) {
		this.statiIstanza = statiIstanza;
	}
}
