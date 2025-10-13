/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class AllegaDocumentoSoapResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uuidIndex;

    private String nomeFileDocumento;

	public AllegaDocumentoSoapResponse() {
		super();
	}

	@XmlElement(nillable = true, name = "uuidIndex")
	public String getUuidIndex() {
		return uuidIndex;
	}

	public void setUuidIndex(String uuidIndex) {
		this.uuidIndex = uuidIndex;
	}

	@XmlElement(nillable = true, name = "nomeFileDocumento")
	public String getNomeFileDocumento() {
		return nomeFileDocumento;
	}

	public void setNomeFileDocumento(String nomeFileDocumento) {
		this.nomeFileDocumento = nomeFileDocumento;
	}

}
