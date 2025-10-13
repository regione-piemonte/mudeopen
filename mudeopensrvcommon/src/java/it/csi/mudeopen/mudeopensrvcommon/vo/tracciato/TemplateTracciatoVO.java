/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TemplateTracciatoVO implements Serializable {

    private static final long serialVersionUID = -810137781015249831L;

    @JsonProperty("id_template_tracciato")
    private Long id;

    @JsonProperty("codice")
    private String codice;

    @JsonProperty("descrizione")
    private String descrizione;

    @JsonProperty("xslt_template")
    private String xsltTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getXsltTemplate() {
        return xsltTemplate;
    }

    public void setXsltTemplate(String xsltTemplate) {
        this.xsltTemplate = xsltTemplate;
    }

}