/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

import java.io.Serializable;

public class FascicoloStatoSlimVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -3786766068645723984L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("id_fascicolo")
    private Long idFascicolo;

    @JsonProperty("stato_fascicolo")
    private DizionarioVO statoFascicolo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFascicolo() {
        return idFascicolo;
    }

    public void setIdFascicolo(Long idFascicolo) {
        this.idFascicolo = idFascicolo;
    }

    public DizionarioVO getStatoFascicolo() {
        return statoFascicolo;
    }

    public void setStatoFascicolo(DizionarioVO statoFascicolo) {
        this.statoFascicolo = statoFascicolo;
    }
}