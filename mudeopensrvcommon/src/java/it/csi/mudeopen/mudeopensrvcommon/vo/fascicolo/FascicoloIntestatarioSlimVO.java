/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;

import java.io.Serializable;

public class FascicoloIntestatarioSlimVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5983055199827752753L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("id_fascicolo")
    private Long idFascicolo;

    @JsonProperty("intestatario")
    private ContattoVO intestatario;

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

    public ContattoVO getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(ContattoVO intestatario) {
        this.intestatario = intestatario;
    }
}