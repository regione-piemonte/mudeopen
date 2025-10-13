/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;

import java.io.Serializable;

public class FascicoloIntestatarioVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 2172082497254766322L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fascicolo")
    private FascicoloVO fascicolo;

    @JsonProperty("intestatario_fascicolo")
    private ContattoVO intestatario;

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

    public ContattoVO getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(ContattoVO intestatario) {
        this.intestatario = intestatario;
    }
}