package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;


import io.swagger.annotations.ApiModelProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateDeserializer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Via  {
	@JsonIgnore
	@JsonProperty("idVia")
	private Integer idVia = null;

	@JsonIgnore
	@JsonProperty("stato")
	private Integer stato = null;
	
	@JsonIgnore
	@JsonProperty("descStato")
	private String descStato = null;
	
	@JsonIgnore
	@JsonProperty("convenzionale")
	private Integer convenzionale = null;
	
	@JsonIgnore
	@JsonProperty("sedime")
	private Sedime sedime = null;
	
	@JsonIgnore
	@JsonProperty("denominazionePrincipale")
	private String denominazionePrincipale = null;
	
	@JsonIgnore
	@JsonProperty("denominazioneSecondaria")
	private String denominazioneSecondaria = null;
	
	@JsonIgnore
	@JsonProperty("denominazioneParte1")
	private String denominazioneParte1 = null;

	@JsonIgnore
	@JsonProperty("denominazioneParte2")
	private String denominazioneParte2 = null;

	@JsonIgnore
	@JsonProperty("denominazioneBreve")
	private String denominazioneBreve = null;

	@JsonIgnore
	@JsonProperty("denominazioneCorrente")
	private String denominazioneCorrente = null;

	@JsonIgnore
	@JsonProperty("denominazioneAlfabetica1")
	private String denominazioneAlfabetica1 = null;

	@JsonIgnore
	@JsonProperty("denominazioneAlfabetica2")
	private String denominazioneAlfabetica2 = null;

	@JsonIgnore
	@JsonProperty("denominazioneCompatta")
	private String denominazioneCompatta = null;

	@JsonIgnore
	@JsonProperty("descrizioneTipoVia")
	private String descrizioneTipoVia = null;

	@JsonIgnore
	@JsonProperty("idTipoVia")
	private Integer idTipoVia = null;

	@JsonIgnore
	@JsonProperty("dataDelibera")
	private String dataDelibera = null;

	public Integer getIdVia() {
		return idVia;
	}
	public void setIdVia(Integer idVia) {
		this.idVia = idVia;
	}
	public Integer getStato() {
		return stato;
	}
	public void setStato(Integer stato) {
		this.stato = stato;
	}
	public String getDescStato() {
		return descStato;
	}
	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}
	public Integer getConvenzionale() {
		return convenzionale;
	}
	public void setConvenzionale(Integer convenzionale) {
		this.convenzionale = convenzionale;
	}
	public Sedime getSedime() {
		return sedime;
	}
	public void setSedime(Sedime sedime) {
		this.sedime = sedime;
	}
	public String getDenominazionePrincipale() {
		return denominazionePrincipale;
	}
	public void setDenominazionePrincipale(String denominazionePrincipale) {
		this.denominazionePrincipale = denominazionePrincipale;
	}
	public String getDenominazioneSecondaria() {
		return denominazioneSecondaria;
	}
	public void setDenominazioneSecondaria(String denominazioneSecondaria) {
		this.denominazioneSecondaria = denominazioneSecondaria;
	}
	public String getDenominazioneParte1() {
		return denominazioneParte1;
	}
	public void setDenominazioneParte1(String denominazioneParte1) {
		this.denominazioneParte1 = denominazioneParte1;
	}
	public String getDenominazioneParte2() {
		return denominazioneParte2;
	}
	public void setDenominazioneParte2(String denominazioneParte2) {
		this.denominazioneParte2 = denominazioneParte2;
	}
	public String getDenominazioneBreve() {
		return denominazioneBreve;
	}
	public void setDenominazioneBreve(String denominazioneBreve) {
		this.denominazioneBreve = denominazioneBreve;
	}
	public String getDenominazioneCorrente() {
		return denominazioneCorrente;
	}
	public void setDenominazioneCorrente(String denominazioneCorrente) {
		this.denominazioneCorrente = denominazioneCorrente;
	}
	public String getDenominazioneAlfabetica1() {
		return denominazioneAlfabetica1;
	}
	public void setDenominazioneAlfabetica1(String denominazioneAlfabetica1) {
		this.denominazioneAlfabetica1 = denominazioneAlfabetica1;
	}
	public String getDenominazioneAlfabetica2() {
		return denominazioneAlfabetica2;
	}
	public void setDenominazioneAlfabetica2(String denominazioneAlfabetica2) {
		this.denominazioneAlfabetica2 = denominazioneAlfabetica2;
	}
	public String getDenominazioneCompatta() {
		return denominazioneCompatta;
	}
	public void setDenominazioneCompatta(String denominazioneCompatta) {
		this.denominazioneCompatta = denominazioneCompatta;
	}
	public String getDescrizioneTipoVia() {
		return descrizioneTipoVia;
	}
	public void setDescrizioneTipoVia(String descrizioneTipoVia) {
		this.descrizioneTipoVia = descrizioneTipoVia;
	}
	public Integer getIdTipoVia() {
		return idTipoVia;
	}
	public void setIdTipoVia(Integer idTipoVia) {
		this.idTipoVia = idTipoVia;
	}
	public String getDataDelibera() {
		return dataDelibera;
	}
	public void setDataDelibera(String dataDelibera) {
		this.dataDelibera = dataDelibera;
	}
	
}

