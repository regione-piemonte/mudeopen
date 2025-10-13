/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.allegato;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadAllegatoVO implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -437430341487247793L;

    @JsonProperty("cod_tipo_allegato")
    private String codTipoAllegato;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("desc_breve_allegato")
    private String descBreveAllegato;

    @JsonProperty("jsondata")
    private String jsondata;

    @JsonProperty("quadro_validated")
    private Boolean quadroValidated;

    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("dont_save_jdata")
    private Boolean doNotsaveJdata = false;

    public String getCodTipoAllegato() {
        return codTipoAllegato;
    }

    public void setCodTipoAllegato(String codTipoAllegato) {
        this.codTipoAllegato = codTipoAllegato;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getDescBreveAllegato() {
        return descBreveAllegato;
    }

    public void setDescBreveAllegato(String descBreveAllegato) {
        this.descBreveAllegato = descBreveAllegato;
    }

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}

	public Boolean getQuadroValidated() {
		return quadroValidated;
	}

	public void setQuadroValidated(Boolean quadroValidated) {
		this.quadroValidated = quadroValidated;
	}

	public Long getIdTemplateQuadro() {
		return idTemplateQuadro;
	}

	public void setIdTemplateQuadro(Long idTemplateQuadro) {
		this.idTemplateQuadro = idTemplateQuadro;
	}

	public boolean isDoNotsaveJdata() {
		return doNotsaveJdata;
	}

	public void setDoNotsaveJdata(boolean doNotsaveJdata) {
		this.doNotsaveJdata = doNotsaveJdata;
	}
}