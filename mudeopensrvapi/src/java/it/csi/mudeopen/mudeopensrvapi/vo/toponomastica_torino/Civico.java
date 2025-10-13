package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Civico  {
	@JsonIgnore
	@JsonProperty("idCivico")
	private Integer idCivico = null;

	@JsonIgnore
	@JsonProperty("idVia")
	private Integer idVia = null;

	@JsonIgnore
	@JsonProperty("numeroRadice")
	private Integer numeroRadice = null;

	@JsonIgnore
	@JsonProperty("iBisTer")
	private Integer iBisTer = null;

	@JsonIgnore
	@JsonProperty("interno1")
	private String interno1 = null;

	@JsonIgnore
	@JsonProperty("iBisTer1")
	private Integer iBisTer1 = null;

	@JsonIgnore
	@JsonProperty("interno2")
	private String interno2 = null;

	@JsonIgnore
	@JsonProperty("iBisTer2")
	private Integer iBisTer2 = null;

	@JsonIgnore
	@JsonProperty("scala")
	private String scala = null;

	@JsonIgnore
	@JsonProperty("numerazioneSecondaria")
	private String numerazioneSecondaria = null;

	@JsonIgnore
	@JsonProperty("fronteVia")
	private Integer fronteVia = null;

	@JsonIgnore
	@JsonProperty("codiceAbitativo")
	private Integer codiceAbitativo = null;

	@JsonIgnore
	@JsonProperty("abitativo")
	private String abitativo = null;

	@JsonIgnore
	@JsonProperty("cap")
	private Integer cap = null;

	@JsonIgnore
	@JsonProperty("stato")
	private Integer stato = null;

	@JsonIgnore
	@JsonProperty("campoNomadi")
	private Integer campoNomadi = null;

	@JsonIgnore
	@JsonProperty("descrizioneStato")
	private String descrizioneStato = null;

	@JsonIgnore
	@JsonProperty("carraio")
	private Boolean carraio = null;

	@JsonIgnore
	@JsonProperty("uiuCompletate")
	private Boolean uiuCompletate = null;

	@JsonIgnore
	@JsonProperty("tipologia")
	private String tipologia = null;

	@JsonIgnore
	@JsonProperty("descrizioneTipologia")
	private String descrizioneTipologia = null;

	@JsonIgnore
	@JsonProperty("bister")
	private String bister = null;

	@JsonIgnore
	@JsonProperty("bisInterno1")
	private String bisInterno1 = null;

	@JsonIgnore
	@JsonProperty("bisInterno2")
	private String bisInterno2 = null;
	
	public Integer getIdCivico() {
		return idCivico;
	}
	public void setIdCivico(Integer idCivico) {
		this.idCivico = idCivico;
	}
	public Integer getIdVia() {
		return idVia;
	}
	public void setIdVia(Integer idVia) {
		this.idVia = idVia;
	}
	public Integer getNumeroRadice() {
		return numeroRadice;
	}
	public void setNumeroRadice(Integer numeroRadice) {
		this.numeroRadice = numeroRadice;
	}
	public Integer getiBisTer() {
		return iBisTer;
	}
	public void setiBisTer(Integer iBisTer) {
		this.iBisTer = iBisTer;
	}
	public String getInterno1() {
		return interno1;
	}
	public void setInterno1(String interno1) {
		this.interno1 = interno1;
	}
	public Integer getiBisTer1() {
		return iBisTer1;
	}
	public void setiBisTer1(Integer iBisTer1) {
		this.iBisTer1 = iBisTer1;
	}
	public String getInterno2() {
		return interno2;
	}
	public void setInterno2(String interno2) {
		this.interno2 = interno2;
	}
	public Integer getiBisTer2() {
		return iBisTer2;
	}
	public void setiBisTer2(Integer iBisTer2) {
		this.iBisTer2 = iBisTer2;
	}
	public String getScala() {
		return scala;
	}
	public void setScala(String scala) {
		this.scala = scala;
	}
	public String getNumerazioneSecondaria() {
		return numerazioneSecondaria;
	}
	public void setNumerazioneSecondaria(String numerazioneSecondaria) {
		this.numerazioneSecondaria = numerazioneSecondaria;
	}
	public Integer getFronteVia() {
		return fronteVia;
	}
	public void setFronteVia(Integer fronteVia) {
		this.fronteVia = fronteVia;
	}
	public Integer getCodiceAbitativo() {
		return codiceAbitativo;
	}
	public void setCodiceAbitativo(Integer codiceAbitativo) {
		this.codiceAbitativo = codiceAbitativo;
	}
	public String getAbitativo() {
		return abitativo;
	}
	public void setAbitativo(String abitativo) {
		this.abitativo = abitativo;
	}
	public Integer getCap() {
		return cap;
	}
	public void setCap(Integer cap) {
		this.cap = cap;
	}
	public Integer getStato() {
		return stato;
	}
	public void setStato(Integer stato) {
		this.stato = stato;
	}
	public Integer getCampoNomadi() {
		return campoNomadi;
	}
	public void setCampoNomadi(Integer campoNomadi) {
		this.campoNomadi = campoNomadi;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public Boolean getCarraio() {
		return carraio;
	}
	public void setCarraio(Boolean carraio) {
		this.carraio = carraio;
	}
	public Boolean getUiuCompletate() {
		return uiuCompletate;
	}
	public void setUiuCompletate(Boolean uiuCompletate) {
		this.uiuCompletate = uiuCompletate;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getDescrizioneTipologia() {
		return descrizioneTipologia;
	}
	public void setDescrizioneTipologia(String descrizioneTipologia) {
		this.descrizioneTipologia = descrizioneTipologia;
	}
	public String getBister() {
		return bister;
	}
	public void setBister(String bister) {
		this.bister = bister;
	}
	public String getBisInterno1() {
		return bisInterno1;
	}
	public void setBisInterno1(String bisInterno1) {
		this.bisInterno1 = bisInterno1;
	}
	public String getBisInterno2() {
		return bisInterno2;
	}
	public void setBisInterno2(String bisInterno2) {
		this.bisInterno2 = bisInterno2;
	}

}

