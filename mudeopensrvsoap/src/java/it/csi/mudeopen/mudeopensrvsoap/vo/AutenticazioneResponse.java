/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;

public class AutenticazioneResponse extends SoapResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;

	public AutenticazioneResponse() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
