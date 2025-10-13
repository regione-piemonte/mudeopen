/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

public class TipoDocumentoVO {
	
	private String codiceTipoDocumento;
	
	private String descrizioneTipoDocumento;

	
	
	public TipoDocumentoVO() {
		super();
	}

	public TipoDocumentoVO(String codiceTipoDocumento, String descrizioneTipoDocumento) {
		super();
		this.codiceTipoDocumento = codiceTipoDocumento;
		this.descrizioneTipoDocumento = descrizioneTipoDocumento;
	}

	public String getCodiceTipoDocumento() {
		return codiceTipoDocumento;
	}

	public void setCodiceTipoDocumento(String codiceTipoDocumento) {
		this.codiceTipoDocumento = codiceTipoDocumento;
	}

	public String getDescrizioneTipoDocumento() {
		return descrizioneTipoDocumento;
	}

	public void setDescrizioneTipoDocumento(String descrizioneTipoDocumento) {
		this.descrizioneTipoDocumento = descrizioneTipoDocumento;
	}
	
	

}
