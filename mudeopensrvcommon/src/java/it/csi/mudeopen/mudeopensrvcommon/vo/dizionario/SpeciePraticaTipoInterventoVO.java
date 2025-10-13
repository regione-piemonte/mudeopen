/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SpeciePraticaTipoInterventoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -4630926754824597458L;

    @JsonProperty("id_specie_pratica_tipo_intervento")
    private long id;

    @JsonProperty("tipo_intervento")
    private DizionarioVO tipoIntervento;

    @JsonProperty("specie_pratica")
    private DizionarioVO speciePratica;

    @JsonProperty("abilitato")
    private Boolean abilitato;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DizionarioVO getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(DizionarioVO tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }

    public DizionarioVO getSpeciePratica() {
        return speciePratica;
    }

    public void setSpeciePratica(DizionarioVO speciePratica) {
        this.speciePratica = speciePratica;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }
}