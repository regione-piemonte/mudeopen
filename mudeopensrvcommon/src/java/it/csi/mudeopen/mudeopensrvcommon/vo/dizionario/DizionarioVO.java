/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DizionarioVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983558L;

    @JsonProperty("id")
    private String codice;

    @JsonProperty("value")
    private String descrizione;

    @JsonProperty("descrizione_estesa")
    private String descrizioneEstesa;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }

    public DizionarioVO() {
    }

    public DizionarioVO(String codice, String descrizione) {
    	this.codice = codice;
    	this.descrizione = descrizione;
    }

    public DizionarioVO(String codice, String descrizione, String descrizioneEstesa) {
    	this.codice = codice;
    	this.descrizione = descrizione;
    	this.descrizioneEstesa = descrizioneEstesa;
    }

}