/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;
import java.util.List;

import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoRiferimentoPOJO;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoUbicazione;

public class IstanzaMudePOJO extends SoapResponse implements Serializable{

    protected MudeRVersioneModello mudeRVersioneModello;
    protected List<Allegato> allegatos;
	private static final long serialVersionUID = 1L;
    protected List<GeoUbicazione> topeUbicaziones;
	protected String xmlStream;
    protected List<GeoRiferimentoPOJO> elencoGeoRiferimento;
	public MudeRVersioneModello getMudeRVersioneModello() {
		return mudeRVersioneModello;
	}
	public void setMudeRVersioneModello(MudeRVersioneModello mudeRVersioneModello) {
		this.mudeRVersioneModello = mudeRVersioneModello;
	}
	public List<Allegato> getAllegatos() {
		return allegatos;
	}
	public void setAllegatos(List<Allegato> allegatos) {
		this.allegatos = allegatos;
	}
	public List<GeoUbicazione> getTopeUbicaziones() {
		return topeUbicaziones;
	}
	public void setTopeUbicaziones(List<GeoUbicazione> topeUbicaziones) {
		this.topeUbicaziones = topeUbicaziones;
	}
	public String getXmlStream() {
		return xmlStream;
	}
	public void setXmlStream(String xmlStream) {
		this.xmlStream = xmlStream;
	}
	public List<GeoRiferimentoPOJO> getElencoGeoRiferimento() {
		return elencoGeoRiferimento;
	}
	public void setElencoGeoRiferimento(List<GeoRiferimentoPOJO> elencoGeoRiferimento) {
		this.elencoGeoRiferimento = elencoGeoRiferimento;
	}

}
