/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

import java.io.Serializable;

public class SalvaFascicoloRequest implements Serializable {

    private static final long serialVersionUID = 940131279672490001L;

    @JsonProperty("id_fascicolo")
    private Long idFascicolo;

    private ProvinciaVO provincia;

    private ComuneVO comune;

    @JsonProperty("tipologia_istanza")
    private SelectVO tipologiaIstanza;

    @JsonProperty("tipologia_intervento")
    private SelectVO tipologiaIntervento;

    public ProvinciaVO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaVO provincia) {
        this.provincia = provincia;
    }

    public ComuneVO getComune() {
        return comune;
    }

    public void setComune(ComuneVO comune) {
        this.comune = comune;
    }

    public SelectVO getTipologiaIstanza() {
        return tipologiaIstanza;
    }

    public void setTipologiaIstanza(SelectVO tipologiaIstanza) {
        this.tipologiaIstanza = tipologiaIstanza;
    }

    public SelectVO getTipologiaIntervento() {
        return tipologiaIntervento;
    }

    public void setTipologiaIntervento(SelectVO tipologiaIntervento) {
        this.tipologiaIntervento = tipologiaIntervento;
    }

    public Long getIdFascicolo() {
        return idFascicolo;
    }

    public void setIdFascicolo(Long idFascicolo) {
        this.idFascicolo = idFascicolo;
    }

}