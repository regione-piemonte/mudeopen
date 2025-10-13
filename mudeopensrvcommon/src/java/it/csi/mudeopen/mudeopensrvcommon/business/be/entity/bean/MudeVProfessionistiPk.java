/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity.bean;
import java.io.Serializable;
public class MudeVProfessionistiPk   implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idIstanza;

    private String cf;
    private String ruoli;

	public long getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(long idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getRuoli() {
		return ruoli;
	}

	public void setRuoli(String ruoli) {
		this.ruoli = ruoli;
	}       
}
