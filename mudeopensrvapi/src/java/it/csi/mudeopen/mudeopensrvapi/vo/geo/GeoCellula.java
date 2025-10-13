/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvapi.vo.serializers.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvapi.vo.serializers.CustomDateTimeSerializer;
public class GeoCellula {

    protected String chiaveCarotaggio;
    protected Integer codCellula;
    @JsonProperty("dataGeoriferimento")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataGeoriferimento;
    protected List<GeoFabbricato> geoFabbricatos;

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
     * Gets the value of the codCellula property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getCodCellula() {
        return codCellula;
    }

    /**
     * Sets the value of the codCellula property.
     * 
     *     {@link Integer }
     *     
     */
    public void setCodCellula(Integer value) {
        this.codCellula = value;
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
     * Gets the value of the geoFabbricatos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoFabbricatos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getGeoFabbricatos().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoFabbricato }
     * 
     * 
     */
    public List<GeoFabbricato> getGeoFabbricatos() {
        if (geoFabbricatos == null) {
            geoFabbricatos = new ArrayList<GeoFabbricato>();
        }
        return this.geoFabbricatos;
    }

	/**
	 * @param geoFabbricatos the geoFabbricatos to set
	 */
	public void setGeoFabbricatos(List<GeoFabbricato> geoFabbricatos) {
		this.geoFabbricatos = geoFabbricatos;
	}
}
