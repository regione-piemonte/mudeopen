/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.localizzazione;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UbicazioneVO {

	@JsonProperty("layer_name")
    protected String sedime;
	@JsonProperty("label")
    protected String indirizzo;
	@JsonProperty("actions")
	protected List<Action> actions;
	public String getSedime() {
		return sedime;
	}
	public void setSedime(String sedime) {
		this.sedime = sedime;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public List<Action> getActions() {
		return actions;
	}
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
	
	
}

/**
	"actions":[
	{
	"data": "CIVICI",
	"action": "add_layer"
	}
	] 
 * @author guideluc
 *
 */
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
class Action {
	
	@JsonProperty("data")
	String data;
	@JsonProperty("action")
	String action;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}