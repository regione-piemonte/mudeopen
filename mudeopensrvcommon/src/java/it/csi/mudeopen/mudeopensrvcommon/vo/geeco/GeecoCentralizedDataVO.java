/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.geeco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeecoCentralizedDataVO {

	@JsonProperty("idSavedData")
	private String idSavedData;
	
	@JsonProperty("fkEditingSession")
	private String fkEditingSession;

	@JsonProperty("fkStateId")
	private Integer fkStateId;
	
	@JsonProperty("featureState")
	private String featureState;
	
	@JsonProperty("savedData")
	private String savedData;
	
	@JsonProperty("featureId")
	private String featureId;
	
	@JsonProperty("dtInsert")
	private String dtInsert;

	@JsonProperty("layerId")
	private String layerId;

	@JsonProperty("sessionState")
	private SessionStateVO SessionStateVO;

	public String getIdSavedData() {
		return idSavedData;
	}

	public void setIdSavedData(String idSavedData) {
		this.idSavedData = idSavedData;
	}

	public String getFkEditingSession() {
		return fkEditingSession;
	}

	public void setFkEditingSession(String fkEditingSession) {
		this.fkEditingSession = fkEditingSession;
	}

	public Integer getFkStateId() {
		return fkStateId;
	}

	public void setFkStateId(Integer fkStateId) {
		this.fkStateId = fkStateId;
	}

	public String getFeatureState() {
		return featureState;
	}

	public void setFeatureState(String featureState) {
		this.featureState = featureState;
	}

	public String getSavedData() {
		return savedData;
	}

	public void setSavedData(String savedData) {
		this.savedData = savedData;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getDtInsert() {
		return dtInsert;
	}

	public void setDtInsert(String dtInsert) {
		this.dtInsert = dtInsert;
	}

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}

	public SessionStateVO getSessionStateVO() {
		return SessionStateVO;
	}

	public void setSessionStateVO(SessionStateVO sessionStateVO) {
		SessionStateVO = sessionStateVO;
	}

	
	
}
