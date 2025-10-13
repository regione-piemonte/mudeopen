/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza;

public class TraccXMLVO {
	protected String versioneTracciato;
	
	protected String tracciatoXML;

	public String getVersioneTracciato() {
		return versioneTracciato;
	}

	public void setVersioneTracciato(String versioneTracciato) {
		this.versioneTracciato = versioneTracciato;
	}

	public String getTracciatoXML() {
		return tracciatoXML;
	}

	public void setTracciatoXML(String tracciatoXml) {
		this.tracciatoXML = tracciatoXml;
	}
	
	
}
