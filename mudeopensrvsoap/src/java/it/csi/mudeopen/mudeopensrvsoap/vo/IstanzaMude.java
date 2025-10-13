/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;
import java.util.List;

import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoRiferimentoPOJO;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoUbicazione;

public class IstanzaMude extends SoapResponse implements Serializable{

	
    protected String xmlStream;
    protected List<GeoRiferimento> elencoGeoRiferimento;
    protected List<GeoUbicazione> topeUbicaziones;
    protected List<Allegato> allegatos;
    protected MudeRVersioneModello mudeRVersioneModello;
	/**
	  the xmlStream
	 */
	public String getXmlStream() {
		return xmlStream;
	}
	/**
	 * @param xmlStream the xmlStream to set
	 */
	public void setXmlStream(String xmlStream) {
		this.xmlStream = xmlStream;
	}
	/**
	  the elencoGeoRiferimento
	 */
	public List<GeoRiferimento> getElencoGeoRiferimento() {
		return elencoGeoRiferimento;
	}
	/**
	 * @param elencoGeoRiferimento the elencoGeoRiferimento to set
	 */
	public void setElencoGeoRiferimento(List<GeoRiferimento> elencoGeoRiferimento) {
		this.elencoGeoRiferimento = elencoGeoRiferimento;
	}
	/**
	  the topeUbicaziones
	 */
	public List<GeoUbicazione> getTopeUbicaziones() {
		return topeUbicaziones;
	}
	/**
	 * @param topeUbicaziones the topeUbicaziones to set
	 */
	public void setTopeUbicaziones(List<GeoUbicazione> topeUbicaziones) {
		this.topeUbicaziones = topeUbicaziones;
	}
	/**
	  the allegatos
	 */
	public List<Allegato> getAllegatos() {
		return allegatos;
	}
	/**
	 * @param allegatos the allegatos to set
	 */
	public void setAllegatos(List<Allegato> allegatos) {
		this.allegatos = allegatos;
	}
	/**
	  the mudeRVersioneModello
	 */
	public MudeRVersioneModello getMudeRVersioneModello() {
		return mudeRVersioneModello;
	}
	/**
	 * @param mudeRVersioneModello the mudeRVersioneModello to set
	 */
	public void setMudeRVersioneModello(MudeRVersioneModello mudeRVersioneModello) {
		this.mudeRVersioneModello = mudeRVersioneModello;
	}
}
