/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geeco;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
jsonToDeserialize:: {

 "id": "24646567",
 "id_geo_particella": "4455184",
 "id_geo_foglio": "5383",
 "cod_com_belfiore": "A518",
 "cod_com_istat": "001013",
 "comune": "Avigliana",
 "sezione": "_",
 "foglio": "20",
 "particella": "199",
 "allegato": "0",
 "sviluppo": "0",
 "aggiornato_al": "2021-12-31",
 "istat_prov": "001",
 "sigla_prov": "TO",
 "ol_uid": "936",
 "check": "<span class=\"gridCheck far\"></span>",
 "$$hashKey": "object:597"
}

..per Torino...
	"cit_part": "44462",
	"foglio": "1233",
	"n_part": "44"
 * @author guideluc
 *
 */

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeecoSelectedPropertiesVO implements Serializable{
	
	
	  @JsonProperty("id")
	  private String id = null;
	  
	  @JsonProperty("id_geo_particella")
	  private String idGeoParticella = null;
	  
	  @JsonProperty("id_geo_foglio")
	  private String idGeoFoglio = null;
	  
	  @JsonProperty("cod_com_belfiore")
	  private String codComBelfiore = null;
	  
	  @JsonProperty("cod_com_istat")
	  private String codComIstat = null;
	  
	  @JsonProperty("comune")
	  private String comune = null;
	  
	  @JsonProperty("sezione")
	  private String sezione = null;
	  
	  @JsonProperty("foglio")
	  private String foglio = null;
	  
	  @JsonProperty("particella")
	  private String particella = null;
	  
	  @JsonProperty("allegato")
	  private String allegato = null;	  
	  
	  @JsonProperty("sviluppo")
	  private String sviluppo = null;	  
	  	  
	  @JsonProperty("aggiornato_al")
	  private String aggiornatoAl = null;	  
	  
	  @JsonProperty("istat_prov")
	  private String istatProv = null;	  
	  
	  @JsonProperty("ol_uid")
	  private String olUid = null;	  	  

	  @JsonProperty("check")
	  private String check = null;	  	  
	  
	  @JsonProperty("$$hashKey")
	  private String hashKey = null;

	  //Dati per Torino
	  @JsonProperty("cit_part")
	  private String citPart;

	  @JsonProperty("n_part")
	  private String nPart;
	  

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdGeoParticella() {
		return idGeoParticella;
	}

	public void setIdGeoParticella(String idGeoParticella) {
		this.idGeoParticella = idGeoParticella;
	}

	public String getIdGeoFoglio() {
		return idGeoFoglio;
	}

	public void setIdGeoFoglio(String idGeoFoglio) {
		this.idGeoFoglio = idGeoFoglio;
	}

	public String getCodComBelfiore() {
		return codComBelfiore;
	}

	public void setCodComBelfiore(String codComBelfiore) {
		this.codComBelfiore = codComBelfiore;
	}

	public String getCodComIstat() {
		return codComIstat;
	}

	public void setCodComIstat(String codComIstat) {
		this.codComIstat = codComIstat;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getAllegato() {
		return allegato;
	}

	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}

	public String getSviluppo() {
		return sviluppo;
	}

	public void setSviluppo(String sviluppo) {
		this.sviluppo = sviluppo;
	}

	public String getAggiornatoAl() {
		return aggiornatoAl;
	}

	public void setAggiornatoAl(String aggiornatoAl) {
		this.aggiornatoAl = aggiornatoAl;
	}

	public String getIstatProv() {
		return istatProv;
	}

	public void setIstatProv(String istatProv) {
		this.istatProv = istatProv;
	}

	public String getOlUid() {
		return olUid;
	}

	public void setOlUid(String olUid) {
		this.olUid = olUid;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getCitPart() {
		return citPart;
	}

	public void setCitPart(String citPart) {
		this.citPart = citPart;
	}

	public String getnPart() {
		return nPart;
	}

	public void setnPart(String nPart) {
		this.nPart = nPart;
	}	  	  
	  

	  
}
