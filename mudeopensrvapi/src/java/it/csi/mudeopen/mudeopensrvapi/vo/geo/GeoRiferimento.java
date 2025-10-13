/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvapi.vo.serializers.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvapi.vo.serializers.CustomDateTimeSerializer;
public class GeoRiferimento implements Serializable{

	private static final long serialVersionUID = 1L;

	protected int idLivelloPoligono;
    protected String descLivelloPoligono;
    protected int servizioFonte;
    protected int servizioLivello;

    @JsonProperty("dataGeoriferimento")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataGeoriferimento;

    protected String codIstatComune;
    protected List<GeoCellula> geoCellulas;
    protected List<GeoCatasto> geoCatastos;
    protected List<GeoDatocarota> geoDatocarotas;
    protected List<GeoUbicazione> geoUbicaziones;
    protected CSIGeometry csiGeometry;

    /**
     * Gets the value of the idLivelloPoligono property.
     * 
     */
    public int getIdLivelloPoligono() {
        return idLivelloPoligono;
    }

    /**
     * Sets the value of the idLivelloPoligono property.
     * 
     */
    public void setIdLivelloPoligono(int value) {
        this.idLivelloPoligono = value;
    }

    /**
     * Gets the value of the descLivelloPoligono property.
     * 
     *     
     */
    public String getDescLivelloPoligono() {
        return descLivelloPoligono;
    }

    /**
     * Sets the value of the descLivelloPoligono property.
     * 
     *     
     */
    public void setDescLivelloPoligono(String value) {
        this.descLivelloPoligono = value;
    }

    /**
     * Gets the value of the servizioFonte property.
     * 
     */
    public int getServizioFonte() {
        return servizioFonte;
    }

    /**
     * Sets the value of the servizioFonte property.
     * 
     */
    public void setServizioFonte(int value) {
        this.servizioFonte = value;
    }

    /**
     * Gets the value of the servizioLivello property.
     * 
     */
    public int getServizioLivello() {
        return servizioLivello;
    }

    /**
     * Sets the value of the servizioLivello property.
     * 
     */
    public void setServizioLivello(int value) {
        this.servizioLivello = value;
    }

    /**
	  the dataGeoriferimento
	 */
	public LocalDateTime getDataGeoriferimento() {
		return dataGeoriferimento;
	}

	/**
	 * @param dataGeoriferimento the dataGeoriferimento to set
	 */
	public void setDataGeoriferimento(LocalDateTime dataGeoriferimento) {
		this.dataGeoriferimento = dataGeoriferimento;
	}

	/**
     * Gets the value of the codIstatComune property.
     * 
     *     
     */
    public String getCodIstatComune() {
        return codIstatComune;
    }

    /**
     * Sets the value of the codIstatComune property.
     * 
     *     
     */
    public void setCodIstatComune(String value) {
        this.codIstatComune = value;
    }

    /**
     * Gets the value of the geoCellulas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoCellulas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getGeoCellulas().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoCellula }
     * 
     * 
     */
    public List<GeoCellula> getGeoCellulas() {
        if (geoCellulas == null) {
            geoCellulas = new ArrayList<GeoCellula>();
        }
        return this.geoCellulas;
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
     * Gets the value of the geoDatocarotas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoDatocarotas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getGeoDatocarotas().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoDatocarota }
     * 
     * 
     */
    public List<GeoDatocarota> getGeoDatocarotas() {
        if (geoDatocarotas == null) {
            geoDatocarotas = new ArrayList<GeoDatocarota>();
        }
        return this.geoDatocarotas;
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
     * Gets the value of the csiGeometry property.
     * 
     *     {@link CSIGeometry }
     *     
     */
    public CSIGeometry getCsiGeometry() {
        return csiGeometry;
    }

    /**
     * Sets the value of the csiGeometry property.
     * 
     *     {@link CSIGeometry }
     *     
     */
    public void setCsiGeometry(CSIGeometry value) {
        this.csiGeometry = value;
    }

	/**
	 * @param geoCellulas the geoCellulas to set
	 */
	public void setGeoCellulas(List<GeoCellula> geoCellulas) {
		this.geoCellulas = geoCellulas;
	}

	/**
	 * @param geoCatastos the geoCatastos to set
	 */
	public void setGeoCatastos(List<GeoCatasto> geoCatastos) {
		this.geoCatastos = geoCatastos;
	}

	/**
	 * @param geoDatocarotas the geoDatocarotas to set
	 */
	public void setGeoDatocarotas(List<GeoDatocarota> geoDatocarotas) {
		this.geoDatocarotas = geoDatocarotas;
	}

	/**
	 * @param geoUbicaziones the geoUbicaziones to set
	 */
	public void setGeoUbicaziones(List<GeoUbicazione> geoUbicaziones) {
		this.geoUbicaziones = geoUbicaziones;
	}
}
