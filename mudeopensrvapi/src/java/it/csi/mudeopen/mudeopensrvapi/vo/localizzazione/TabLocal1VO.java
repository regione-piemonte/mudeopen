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

/**
 *  - UBICAZIONE - 

	"TAB_LOCAL_1": {
		"submit": true,
		"isValid": true,
		"dataGrid": [{
			"n": "",
			"bis": "",
			"cap": "",
			"int": "",
			"piano": "",
			"scala": "",
			"sedime": "CAVALCAVIA",
			"interno2": "",
			"bisInterno": "",
			"secondario": "",
			"denominazione": "4",
			"apriMappaConGeeco": false,
			"selezionare_se_si_tratta_di_indirizzo_principale": true
		}],
		"_URL_BACKEND": "https://dev-fo-mudeopen.nivolapiemonte.it/mudeopen/rest",
		"relativo_all_immobile": "ACQUI TERME"
	}
	
 * 
 * @author guideluc
 *
 */
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TabLocal1VO {

	@JsonProperty("submit")
    protected boolean submit=true;
	
	@JsonProperty("isValid")
    protected boolean isValid=true;

	@JsonProperty("dataGrid")
    protected List<DataGrid> dataGrid;
	
	@JsonProperty("relativo_all_immobile")
    protected String denominazioneComune;	
	
	@JsonProperty("_URL_BACKEND")
    protected String urlBackEnd= "https://dev-fo-mudeopen.nivolapiemonte.it/mudeopen/rest";		
	
	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getDenominazioneComune() {
		return denominazioneComune;
	}

	public void setDenominazioneComune(String denominazioneComune) {
		this.denominazioneComune = denominazioneComune;
	}

	public String getUrlBackEnd() {
		return urlBackEnd;
	}

	public void setUrlBackEnd(String urlBackEnd) {
		this.urlBackEnd = urlBackEnd;
	}

	public List<DataGrid> getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(List<DataGrid> dataGrid) {
		this.dataGrid = dataGrid;
	}
		
}
