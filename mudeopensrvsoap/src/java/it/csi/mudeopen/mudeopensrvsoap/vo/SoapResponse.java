/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;

public class SoapResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String esito;
	
	public SoapResponse() {
		super();
	}
	
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}

}
