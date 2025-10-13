/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

import java.io.Serializable;

public class FascicoloStatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -3786766068645723984L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fascicolo")
    private FascicoloVO fascicolo;

    @JsonProperty("stato_fascicolo")
    private DizionarioVO statoFascicolo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FascicoloVO getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(FascicoloVO fascicolo) {
        this.fascicolo = fascicolo;
    }

    public DizionarioVO getStatoFascicolo() {
        return statoFascicolo;
    }

    public void setStatoFascicolo(DizionarioVO statoFascicolo) {
        this.statoFascicolo = statoFascicolo;
    }
}