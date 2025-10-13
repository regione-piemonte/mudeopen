/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

import java.io.Serializable;

public class IstanzaStatoSlimVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -3786766068645723984L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("stato_istanza")
    private DizionarioVO statoIstanza;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public DizionarioVO getStatoIstanza() {
        return statoIstanza;
    }

    public void setStatoIstanza(DizionarioVO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }
}