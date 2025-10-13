/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TipoIstanzaTipoOperaVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 8810368978522154186L;

    @JsonProperty("id_tipo_istanza_tipo_opera")
    private long id;

    @JsonProperty("tipo_istanza")
    private TipoIstanzaVO tipoIstanza;

    @JsonProperty("tipo_opera")
    private DizionarioVO tipoOpera;

    @JsonProperty("is_diretta")
    private Boolean isDiretta = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoIstanzaVO getTipoIstanza() {
        return tipoIstanza;
    }

    public void setTipoIstanza(TipoIstanzaVO tipoIstanza) {
        this.tipoIstanza = tipoIstanza;
    }

    public DizionarioVO getTipoOpera() {
        return tipoOpera;
    }

    public void setTipoOpera(DizionarioVO tipoOpera) {
        this.tipoOpera = tipoOpera;
    }

    public Boolean getDiretta() {
        return isDiretta;
    }

    public void setDiretta(Boolean diretta) {
        isDiretta = diretta;
    }
}