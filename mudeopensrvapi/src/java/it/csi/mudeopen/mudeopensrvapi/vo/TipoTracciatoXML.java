/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

public class TipoTracciatoXML {

	private String codTipoTracciato;
	
	private String descrizione;
	
	private Boolean xmlPresente;

	public String getCodTipoTracciato() {
		return codTipoTracciato;
	}

	public void setCodTipoTracciato(String codTipoTracciato) {
		this.codTipoTracciato = codTipoTracciato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getXmlPresente() {
		return xmlPresente;
	}

	public void setXmlPresente(Boolean xmlPresente) {
		this.xmlPresente = xmlPresente;
	}

	
}
