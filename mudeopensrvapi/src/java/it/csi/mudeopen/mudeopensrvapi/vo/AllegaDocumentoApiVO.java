/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type AllegaDocumentoApiVO
 */
public class AllegaDocumentoApiVO implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;

    private String uuidIndex;

    private String nomeFileDocumento;

    public AllegaDocumentoApiVO() {
    	super();
    }

	public AllegaDocumentoApiVO(String uuidIndex, String nomeFileDocumento) {
		super();
		this.uuidIndex = uuidIndex;
		this.nomeFileDocumento = nomeFileDocumento;
	}

	public String getUuidIndex() {
		return uuidIndex;
	}

	public void setUuidIndex(String uuidIndex) {
		this.uuidIndex = uuidIndex;
	}

	public String getNomeFileDocumento() {
		return nomeFileDocumento;
	}

	public void setNomeFileDocumento(String nomeFileDocumento) {
		this.nomeFileDocumento = nomeFileDocumento;
	}

}