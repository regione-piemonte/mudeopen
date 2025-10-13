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
 * 
	"TAB_LOCAL_2": {
		"submit": true,
		"isValid": true,
		"dataGrid": [{
			"map": "3",
			"sub": "",
			"foglioN": "4",
			"text_fieldsezione": "",
			"censito_al_catasto": "catasto_terreni"
		}],
		"_URL_BACKEND": "https://dev-fo-mudeopen.nivolapiemonte.it/mudeopen/rest"
	}
	
	
 * @author guideluc
 *
 */
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TabLocal2VO {

	@JsonProperty("submit")
    protected boolean submit=true;
	
	@JsonProperty("isValid")
    protected boolean isValid=true;
	
	@JsonProperty("dataGrid")
    protected List<DataGridLocal2> dataGrid;
	
	@JsonProperty("relativo_all_immobile")
    protected String denominazioneComune;	
	
	@JsonProperty("_URL_BACKEND")
    protected String urlBackEnd= "https://dev-fo-mudeopen.nivolapiemonte.it/mudeopen/rest";		
	
}

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
class DataGridLocal2{
	@JsonProperty("map")
    protected String map;

	@JsonProperty("sub")
    protected String sub;	

	@JsonProperty("foglioN")
    protected String foglioN;	
	
	@JsonProperty("text_fieldsezione")
    protected String sezione;	
	
	@JsonProperty("censito_al_catasto")
    protected String tipoCatasto="catasto_fabbricati";

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getFoglioN() {
		return foglioN;
	}

	public void setFoglioN(String foglioN) {
		this.foglioN = foglioN;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getTipoCatasto() {
		return tipoCatasto;
	}

	public void setTipoCatasto(String tipoCatasto) {
		this.tipoCatasto = tipoCatasto;
	}	
	
	

}
