/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TipoNotificaVO implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private String codiceTipoNotifica;
	
	private String descrizioneTipoNotifica;

		
	public TipoNotificaVO(String codiceTipoNotifica, String descrizioneTipoNotifica) {
		super();
		this.codiceTipoNotifica = codiceTipoNotifica;
		this.descrizioneTipoNotifica = descrizioneTipoNotifica;
	}

	public String getCodiceTipoNotifica() {
		return codiceTipoNotifica;
	}

	public void setCodiceTipoNotifica(String codiceTipoNotifica) {
		this.codiceTipoNotifica = codiceTipoNotifica;
	}

	public String getDescrizioneTipoNotifica() {
		return descrizioneTipoNotifica;
	}

	public void setDescrizioneTipoNotifica(String descrizioneTipoNotifica) {
		this.descrizioneTipoNotifica = descrizioneTipoNotifica;
	}
	

}
