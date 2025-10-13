/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SpeciePraticaTipoInterventoPaesaggisticaVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5801443446514254257L;

    @JsonProperty("id_specie_pratica_tipo_interv_paesaggistica")
    private long id;

    @JsonProperty("specie_pratica")
    private DizionarioVO speciePratica;

    @JsonProperty("tipo_intervento_paesaggistica")
    private DizionarioVO tipoInterventoPaesaggistica;

    @JsonProperty("abilitato")
    private Boolean abilitato;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DizionarioVO getSpeciePratica() {
        return speciePratica;
    }

    public void setSpeciePratica(DizionarioVO speciePratica) {
        this.speciePratica = speciePratica;
    }

    public DizionarioVO getTipoInterventoPaesaggistica() {
        return tipoInterventoPaesaggistica;
    }

    public void setTipoInterventoPaesaggistica(DizionarioVO tipoInterventoPaesaggistica) {
        this.tipoInterventoPaesaggistica = tipoInterventoPaesaggistica;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }
}