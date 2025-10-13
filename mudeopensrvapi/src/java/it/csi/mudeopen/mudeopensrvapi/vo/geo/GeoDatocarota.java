/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvapi.vo.serializers.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvapi.vo.serializers.CustomDateTimeSerializer;

public class GeoDatocarota {

    protected String tipoCarotaggio;
    protected Integer idFonte;
    protected String descFonte;
    protected Integer idLivello;

    @JsonProperty("dataGeoriferimento")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    //protected Date dataGeoriferimento;
    private LocalDateTime dataGeoriferimento;

    protected String descLivello;
    protected String valore;

    /**
     * Gets the value of the tipoCarotaggio property.
     * 
     *     
     */
    public String getTipoCarotaggio() {
        return tipoCarotaggio;
    }

    /**
     * Sets the value of the tipoCarotaggio property.
     * 
     *     
     */
    public void setTipoCarotaggio(String value) {
        this.tipoCarotaggio = value;
    }

    /**
     * Gets the value of the idFonte property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getIdFonte() {
        return idFonte;
    }

    /**
     * Sets the value of the idFonte property.
     * 
     *     {@link Integer }
     *     
     */
    public void setIdFonte(Integer value) {
        this.idFonte = value;
    }

    /**
     * Gets the value of the descFonte property.
     * 
     *     
     */
    public String getDescFonte() {
        return descFonte;
    }

    /**
     * Sets the value of the descFonte property.
     * 
     *     
     */
    public void setDescFonte(String value) {
        this.descFonte = value;
    }

    /**
     * Gets the value of the idLivello property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getIdLivello() {
        return idLivello;
    }

    /**
     * Sets the value of the idLivello property.
     * 
     *     {@link Integer }
     *     
     */
    public void setIdLivello(Integer value) {
        this.idLivello = value;
    }

    /**
     * Gets the value of the dataGeoriferimento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public LocalDateTime getDataGeoriferimento() {
        return dataGeoriferimento;
    }

    /**
     * Sets the value of the dataGeoriferimento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataGeoriferimento(LocalDateTime value) {
        this.dataGeoriferimento = value;
    }

    /**
     * Gets the value of the descLivello property.
     * 
     *     
     */
    public String getDescLivello() {
        return descLivello;
    }

    /**
     * Sets the value of the descLivello property.
     * 
     *     
     */
    public void setDescLivello(String value) {
        this.descLivello = value;
    }

    /**
     * Gets the value of the valore property.
     * 
     *     
     */
    public String getValore() {
        return valore;
    }

    /**
     * Sets the value of the valore property.
     * 
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }

}
