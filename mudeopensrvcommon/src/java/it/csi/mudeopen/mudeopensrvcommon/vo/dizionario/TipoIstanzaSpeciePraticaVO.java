/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;

import java.io.Serializable;

public class TipoIstanzaSpeciePraticaVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -1539557544725209656L;

    @JsonProperty("id_tipo_istanza_specie_pratica")
    private long id;

    @JsonProperty("tipo_istanza")
    private TipoIstanzaVO tipoIstanzaVO;

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

    public TipoIstanzaVO getTipoIstanzaVO() {
        return tipoIstanzaVO;
    }

    public void setTipoIstanzaVO(TipoIstanzaVO tipoIstanzaVO) {
        this.tipoIstanzaVO = tipoIstanzaVO;
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