/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SpeciePraticaTipoOperaVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -7646338136751256775L;

    @JsonProperty("id_specie_pratica_tipo_opera")
    private long id;

    @JsonProperty("specie_pratica")
    private DizionarioVO speciePratica;

    @JsonProperty("tipo_opera")
    private DizionarioVO tipoOpera;

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

    public DizionarioVO getTipoOpera() {
        return tipoOpera;
    }

    public void setTipoOpera(DizionarioVO tipoOpera) {
        this.tipoOpera = tipoOpera;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }
}