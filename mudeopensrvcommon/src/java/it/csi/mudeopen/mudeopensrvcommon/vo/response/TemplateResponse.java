/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(Include.ALWAYS)
public class TemplateResponse implements Serializable {
	private static final long serialVersionUID = -4608160394767998375L;

	@JsonProperty("id_template")
	private long idTemplate;

	@JsonProperty("tipo_istanza")
	private TipoIstanzaVO tipoIstanza;

	@JsonProperty("cod_template")
	private String codTemplate;

	@JsonProperty("ver_template")
	private Integer numeroVersione;

	@JsonProperty("stato_template")
	private Long flagAttivo;

	@JsonProperty("des_template")
	private String desTemplate;

	@JsonProperty("data_inizio_validita")
	private Date dataInizioValidita;

	@JsonProperty("data_cessazione")
	private Date dataCessazione;

	@JsonProperty("json_configura_template")
	private String jsonConfiguraTemplate;

	@JsonProperty("quadri")
	private List<TemplateQuadroResponse> quadri;

	@JsonProperty("modello_documentale")
	private ModelloVO modello;

	@JsonProperty("previsto_pm")
	private Boolean previstoPM;
	
    public long getIdTemplate() {
		return idTemplate;
	}

    public void setIdTemplate(long idTemplate) {
		this.idTemplate = idTemplate;
	}

    public TipoIstanzaVO getTipoIstanza() {
		return tipoIstanza;
	}

    public void setTipoIstanza(TipoIstanzaVO tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

    public String getCodTemplate() {
		return codTemplate;
	}

    public void setCodTemplate(String codTemplate) {
		this.codTemplate = codTemplate;
	}

    public String getDesTemplate() {
		return desTemplate;
	}

    public void setDesTemplate(String desTemplate) {
		this.desTemplate = desTemplate;
	}

    public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

    public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

    public String getJsonConfiguraTemplate() {
		return jsonConfiguraTemplate;
	}

    public void setJsonConfiguraTemplate(String jsonConfiguraTemplate) {
		this.jsonConfiguraTemplate = jsonConfiguraTemplate;
	}

    public List<TemplateQuadroResponse> getQuadri() {
		return quadri;
	}

    public void setQuadri(List<TemplateQuadroResponse> quadri) {
		this.quadri = quadri;
	}

    public Date getDataCessazione() {
		return dataCessazione;
	}

    public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

    public ModelloVO getModello() {
		return modello;
	}

    public void setModello(ModelloVO modello) {
		this.modello = modello;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TemplateResponse that = (TemplateResponse) o;
		return idTemplate == that.idTemplate && Objects.equals(tipoIstanza, that.tipoIstanza) && Objects.equals(codTemplate, that.codTemplate) && Objects.equals(desTemplate, that.desTemplate) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataCessazione, that.dataCessazione) && Objects.equals(jsonConfiguraTemplate, that.jsonConfiguraTemplate) && Objects.equals(quadri, that.quadri) && Objects.equals(modello, that.modello);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTemplate, tipoIstanza, codTemplate, desTemplate, dataInizioValidita, dataCessazione, jsonConfiguraTemplate, quadri, modello);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TemplateResponse {");
		sb.append("         idTemplate:").append(idTemplate);
		sb.append(",         tipoIstanza:").append(tipoIstanza);
		sb.append(",         codTemplate:'").append(codTemplate).append("'");
		sb.append(",         desTemplate:'").append(desTemplate).append("'");
		sb.append(",         dataInizioValidita:").append(dataInizioValidita);
		sb.append(",         dataCessazione:").append(dataCessazione);
		sb.append(",         jsonConfiguraTemplate:'").append(jsonConfiguraTemplate).append("'");
		sb.append(",         quadri:").append(quadri);
		sb.append(",         modello:").append(modello);
		sb.append("}");
		return sb.toString();
	}

	public Integer getNumeroVersione() {
		return numeroVersione;
	}

	public void setNumeroVersione(Integer numeroVersione) {
		this.numeroVersione = numeroVersione;
	}

	public Long getFlagAttivo() {
		return flagAttivo;
	}

	public void setFlagAttivo(Long flagAttivo) {
		this.flagAttivo = flagAttivo;
	}

	public Boolean getPrevistoPM() {
		return previstoPM;
	}

	public void setPrevistoPM(Boolean previstoPM) {
		this.previstoPM = previstoPM;
	}
	
}