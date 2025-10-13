/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;

public class RuoloPossibileVO extends ParentVO {

    private static final long serialVersionUID = -2641198993382229145L;

    private String id;

    @JsonProperty(value = "value")
    private String denominazione;

    @JsonProperty(value = "default")
    private Boolean _default;
    private Boolean visibile;
    private String gruppo;

    private String operatori;
    private String includi;
    private String escludi;
    private String obbligatori;

    public RuoloPossibileVO() {}

    public RuoloPossibileVO(String id,
    						String denominazione, 
    						Boolean _default, 
    						Boolean visibile, 
    						String gruppo,
    						String operatori,
    						String includi,
    						String escludi,
    						String obbligatori) {
        super();
        this.id = id;
        this.denominazione = denominazione;
        this._default = _default;
        this.visibile = visibile;
        this.gruppo = gruppo;
        this.operatori = operatori;
        this.includi = includi;
        this.escludi = escludi;
        this.obbligatori = obbligatori;
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

    @JsonProperty(value = "default")
    public Boolean get_default() {
        return _default;
    }

    @JsonProperty(value = "default")
    public void set_default(Boolean _default) {
        this._default = _default;
    }

    public Boolean getVisibile() {
        return visibile;
    }

    public void setVisibile(Boolean visibile) {
        this.visibile = visibile;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

	public String getOperatori() {
		return operatori;
	}

	public void setOperatori(String operatori) {
		this.operatori = operatori;
	}

	public String getEscludi() {
		return escludi;
	}

	public void setEscludi(String escludi) {
		this.escludi = escludi;
	}

	public String getObbligatori() {
		return obbligatori;
	}

	public void setObbligatori(String obbligatori) {
		this.obbligatori = obbligatori;
	}

	public String getIncludi() {
		return includi;
	}

	public void setIncludi(String includi) {
		this.includi = includi;
	}
}