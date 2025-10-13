/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

public class TipoCassaEdileVO {
	
	private String codiceImpresa;
	private String codiceCassa;
	private IndirizzoVO indirizzo;

    public TipoCassaEdileVO() {
	}

    public TipoCassaEdileVO(String codiceImpresa, String codiceCassa, IndirizzoVO indirizzo) {
		super();
		this.codiceImpresa = codiceImpresa;
		this.codiceCassa = codiceCassa;
		this.indirizzo = indirizzo;
	}

    public String getCodiceImpresa() {
		return codiceImpresa;
	}

    public void setCodiceImpresa(String codiceImpresa) {
		this.codiceImpresa = codiceImpresa;
	}

    public String getCodiceCassa() {
		return codiceCassa;
	}

    public void setCodiceCassa(String codiceCassa) {
		this.codiceCassa = codiceCassa;
	}

    public IndirizzoVO getIndirizzo() {
		return indirizzo;
	}

    public void setIndirizzo(IndirizzoVO indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
}