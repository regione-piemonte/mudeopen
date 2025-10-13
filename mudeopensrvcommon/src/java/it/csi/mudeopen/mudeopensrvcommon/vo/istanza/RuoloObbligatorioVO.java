/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;

public class RuoloObbligatorioVO extends ParentVO {

    private static final long serialVersionUID = -2641198993382229145L;

    private String id;
    private String denominazione;
    private Boolean presente;

    public RuoloObbligatorioVO() {}

    public RuoloObbligatorioVO(String id, String denominazione, Boolean presente) {
        super();
        this.id = id;
        this.denominazione = denominazione;
        this.presente = presente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty(value = "value")
    public String getDenominazione() {
        return denominazione;
    }

    @JsonProperty(value = "value")
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RuoloObbligatorioVO other = (RuoloObbligatorioVO) obj;
        if (denominazione == null) {
            if (other.denominazione != null)
                return false;
        } else if (!denominazione.equals(other.denominazione))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}