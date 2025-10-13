/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;

public class Allegato extends SoapResponse implements Serializable{

	
    //@JsonProperty("idTipoallegato")
    protected int idTipoallegato;
	
    //@JsonProperty("idAllegatoFile")
    protected int idAllegatoFile;

    protected String nomeAllegato;

	/**
	  the idTipoallegato
	 */
	public int getIdTipoallegato() {
		return idTipoallegato;
	}

	/**
	 * @param idTipoallegato the idTipoallegato to set
	 */
	public void setIdTipoallegato(int idTipoallegato) {
		this.idTipoallegato = idTipoallegato;
	}

	/**
	  the idAllegatoFile
	 */
	public int getIdAllegatoFile() {
		return idAllegatoFile;
	}

	/**
	 * @param idAllegatoFile the idAllegatoFile to set
	 */
	public void setIdAllegatoFile(int idAllegatoFile) {
		this.idAllegatoFile = idAllegatoFile;
	}

	/**
	  the nomeAllegato
	 */
	public String getNomeAllegato() {
		return nomeAllegato;
	}

	/**
	 * @param nomeAllegato the nomeAllegato to set
	 */
	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

}
