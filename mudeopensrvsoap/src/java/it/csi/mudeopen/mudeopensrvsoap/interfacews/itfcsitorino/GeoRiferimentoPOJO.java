/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import java.util.List;
public class GeoRiferimentoPOJO {
    protected CsiGeometry csiGeometry;
    protected int idLivelloPoligono;
    protected String dataGeoriferimento;
    protected List<GeoUbicazione> geoUbicaziones;
    protected String descLivelloPoligono;
    protected int servizioFonte;
    protected List<GeoCatasto> geoCatastos;
    protected int servizioLivello;
    protected String codIstatComune;
    protected List<GeoDatocarota> geoDatocarotas;
    protected List<GeoCellula> geoCellulas;
	public CsiGeometry getCsiGeometry() {
		return csiGeometry;
	}
	public void setCsiGeometry(CsiGeometry csiGeometry) {
		this.csiGeometry = csiGeometry;
	}
	public int getIdLivelloPoligono() {
		return idLivelloPoligono;
	}
	public void setIdLivelloPoligono(int idLivelloPoligono) {
		this.idLivelloPoligono = idLivelloPoligono;
	}
	public String getDataGeoriferimento() {
		return dataGeoriferimento;
	}
	public void setDataGeoriferimento(String dataGeoriferimento) {
		this.dataGeoriferimento = dataGeoriferimento;
	}
	public List<GeoUbicazione> getGeoUbicaziones() {
		return geoUbicaziones;
	}
	public void setGeoUbicaziones(List<GeoUbicazione> geoUbicaziones) {
		this.geoUbicaziones = geoUbicaziones;
	}
	public String getDescLivelloPoligono() {
		return descLivelloPoligono;
	}
	public void setDescLivelloPoligono(String descLivelloPoligono) {
		this.descLivelloPoligono = descLivelloPoligono;
	}
	public int getServizioFonte() {
		return servizioFonte;
	}
	public void setServizioFonte(int servizioFonte) {
		this.servizioFonte = servizioFonte;
	}
	public List<GeoCatasto> getGeoCatastos() {
		return geoCatastos;
	}
	public void setGeoCatastos(List<GeoCatasto> geoCatastos) {
		this.geoCatastos = geoCatastos;
	}
	public int getServizioLivello() {
		return servizioLivello;
	}
	public void setServizioLivello(int servizioLivello) {
		this.servizioLivello = servizioLivello;
	}
	public String getCodIstatComune() {
		return codIstatComune;
	}
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}
	public List<GeoDatocarota> getGeoDatocarotas() {
		return geoDatocarotas;
	}
	public void setGeoDatocarotas(List<GeoDatocarota> geoDatocarotas) {
		this.geoDatocarotas = geoDatocarotas;
	}
	public List<GeoCellula> getGeoCellulas() {
		return geoCellulas;
	}
	public void setGeoCellulas(List<GeoCellula> geoCellulas) {
		this.geoCellulas = geoCellulas;
	}
}
