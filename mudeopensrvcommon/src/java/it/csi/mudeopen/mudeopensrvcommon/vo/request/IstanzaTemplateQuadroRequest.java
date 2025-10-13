/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(Include.ALWAYS)
public class IstanzaTemplateQuadroRequest implements Serializable {

    private static final long serialVersionUID = 69863981023718621L;

    private Long idIstanza;
    private Long idTemplateQuadro;
    private String jsonDataQuadro;

	@JsonProperty("id_sub_quadro")
	private Long idSubQuadro;

	@JsonProperty("cod_sub_quadro")
    private String codSubQuadro;
	
	@JsonProperty("jsondata_subquadro")
    private String jsonDataSubquadro;

	@JsonProperty("jsondata_modificato")
    private boolean jsondataModificato;
	
	@JsonProperty("main_quadro_validated")
    private boolean mainQuadroValidated;
	
	@JsonProperty("jdata_keys_to_delete")
    private String jDataKeysToDelete;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Long getIdTemplateQuadro() {
        return idTemplateQuadro;
    }

    public void setIdTemplateQuadro(Long idTemplateQuadro) {
        this.idTemplateQuadro = idTemplateQuadro;
    }

    public String getJsonDataQuadro() {
        return jsonDataQuadro;
    }

    public void setJsonDataQuadro(String jsonDataQuadro) {
        this.jsonDataQuadro = jsonDataQuadro;
    }

    @Override
    public String toString() {
        return new StringJoiner("\n ", IstanzaTemplateQuadroRequest.class.getSimpleName() + "[", "]")
                .add("idIstanza=" + idIstanza)
                .add("idTemplateQuadro=" + idTemplateQuadro)
                .add("jsonDataQuadro='" + jsonDataQuadro + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IstanzaTemplateQuadroRequest)) return false;
        IstanzaTemplateQuadroRequest that = (IstanzaTemplateQuadroRequest) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTemplateQuadro, that.idTemplateQuadro) && Objects.equals(jsonDataQuadro, that.jsonDataQuadro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idTemplateQuadro, jsonDataQuadro);
    }

	public String getCodSubQuadro() {
		return codSubQuadro;
	}

	public void setCodSubQuadro(String codSubQuadro) {
		this.codSubQuadro = codSubQuadro;
	}

	public boolean isMainQuadroValidated() {
		return mainQuadroValidated;
	}

	public void setMainQuadroValidated(boolean jsondataCompletato) {
		this.mainQuadroValidated = jsondataCompletato;
	}

	public String getJsonDataSubquadro() {
		return jsonDataSubquadro;
	}

	public void setJsonDataSubquadro(String jsonDataSubquadro) {
		this.jsonDataSubquadro = jsonDataSubquadro;
	}

	public boolean isJsondataModificato() {
		return jsondataModificato;
	}

	public void setJsondataModificato(boolean jsondataModificato) {
		this.jsondataModificato = jsondataModificato;
	}

	public Long getIdSubQuadro() {
		return idSubQuadro;
	}

	public void setIdSubQuadro(Long idSubQuadro) {
		this.idSubQuadro = idSubQuadro;
	}

	public String getjDataKeysToDelete() {
		return jDataKeysToDelete;
	}

	public void setjDataKeysToDelete(String jDataKeysToDelete) {
		this.jDataKeysToDelete = jDataKeysToDelete;
	}

}