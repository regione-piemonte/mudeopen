/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;

public class EnteVO {
    @JsonIgnore
    private static final long serialVersionUID = 8767089393520398530L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("codice")
    private String codice;

    @JsonProperty("descrizione")
    private String descrizione;

    @JsonProperty("descrizione_estesa")
    private String descrizioneEstesa;

    @JsonProperty("indirizzo_ente")
    private String indirizzoEnte;

    @JsonProperty("cap_ente")
    private String capEnte;

    @JsonProperty("responsabile_ente")
    private String responsabileEnte;

    @JsonProperty("pec_ente")
    private String pecEnte;

    @JsonProperty("comune")
    private ComuneVO comune;

    @JsonProperty("tipo_ente")
    private DizionarioVO tipoEnte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getIndirizzoEnte() {
        return indirizzoEnte;
    }

    public void setIndirizzoEnte(String indirizzoEnte) {
        this.indirizzoEnte = indirizzoEnte;
    }

    public String getCapEnte() {
        return capEnte;
    }

    public void setCapEnte(String capEnte) {
        this.capEnte = capEnte;
    }

    public String getResponsabileEnte() {
        return responsabileEnte;
    }

    public void setResponsabileEnte(String responsabileEnte) {
        this.responsabileEnte = responsabileEnte;
    }

    public String getPecEnte() {
        return pecEnte;
    }

    public void setPecEnte(String pecEnte) {
        this.pecEnte = pecEnte;
    }

    public ComuneVO getComune() {
        return comune;
    }

    public void setComune(ComuneVO comune) {
        this.comune = comune;
    }

    public DizionarioVO getTipoEnte() {
        return tipoEnte;
    }

    public void setTipoEnte(DizionarioVO tipoEnte) {
        this.tipoEnte = tipoEnte;
    }
}