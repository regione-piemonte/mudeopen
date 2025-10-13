/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo;

import java.util.ArrayList;
import java.util.List;
public class GeoFabbricato {

    protected String chiaveCarotaggio;
    protected Integer codFabbricato;
    protected List<GeoUbicazione> geoUbicaziones;
    protected List<GeoCatasto> geoCatastos;

    /**
     * Gets the value of the chiaveCarotaggio property.
     * 
     *     
     */
    public String getChiaveCarotaggio() {
        return chiaveCarotaggio;
    }

    /**
     * Sets the value of the chiaveCarotaggio property.
     * 
     *     
     */
    public void setChiaveCarotaggio(String value) {
        this.chiaveCarotaggio = value;
    }

    /**
     * Gets the value of the codFabbricato property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getCodFabbricato() {
        return codFabbricato;
    }

    /**
     * Sets the value of the codFabbricato property.
     * 
     *     {@link Integer }
     *     
     */
    public void setCodFabbricato(Integer value) {
        this.codFabbricato = value;
    }

    /**
     * Gets the value of the geoUbicaziones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoUbicaziones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getGeoUbicaziones().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoUbicazione }
     * 
     * 
     */
    public List<GeoUbicazione> getGeoUbicaziones() {
        if (geoUbicaziones == null) {
            geoUbicaziones = new ArrayList<GeoUbicazione>();
        }
        return this.geoUbicaziones;
    }

    /**
     * Gets the value of the geoCatastos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoCatastos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getGeoCatastos().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoCatasto }
     * 
     * 
     */
    public List<GeoCatasto> getGeoCatastos() {
        if (geoCatastos == null) {
            geoCatastos = new ArrayList<GeoCatasto>();
        }
        return this.geoCatastos;
    }

	/**
	 * @param geoUbicaziones the geoUbicaziones to set
	 */
	public void setGeoUbicaziones(List<GeoUbicazione> geoUbicaziones) {
		this.geoUbicaziones = geoUbicaziones;
	}

	/**
	 * @param geoCatastos the geoCatastos to set
	 */
	public void setGeoCatastos(List<GeoCatasto> geoCatastos) {
		this.geoCatastos = geoCatastos;
	}
}
