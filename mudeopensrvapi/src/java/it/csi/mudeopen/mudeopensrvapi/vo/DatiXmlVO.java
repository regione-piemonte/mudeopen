/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

public class DatiXmlVO {

    protected String tracciatoXML;

    protected String versione;

    protected String descrizioneTipoTracciato;

	public String getTracciatoXML() {
		return tracciatoXML;
	}

	public void setTracciatoXML(String tracciatoXML) {
		this.tracciatoXML = tracciatoXML;
	}

	public String getVersione() {
		return versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

	public String getDescrizioneTipoTracciato() {
		return descrizioneTipoTracciato;
	}

	public void setDescrizioneTipoTracciato(String descrizioneTipoTracciato) {
		this.descrizioneTipoTracciato = descrizioneTipoTracciato;
	}
}
