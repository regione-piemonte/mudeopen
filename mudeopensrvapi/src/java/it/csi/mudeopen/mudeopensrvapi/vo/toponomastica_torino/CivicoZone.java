package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;


import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CivicoZone  {
  
  // @ApiModelProperty(value = "id del civico")
 /**
   * id del civico
  **/
  private Integer idCivico = null;

  // @ApiModelProperty(value = "id della via")
 /**
   * id della via
  **/
  private Integer idVia = null;

  // @ApiModelProperty(value = "numero radice")
 /**
   * numero radice
  **/
  private Integer numeroRadice = null;

  // @ApiModelProperty(value = "iBisTer")
 /**
   * iBisTer
  **/
  private Integer iBisTer = null;

  // @ApiModelProperty(value = "interno1")
 /**
   * interno1
  **/
  private String interno1 = null;

  // @ApiModelProperty(value = "iBisTer1")
 /**
   * iBisTer1
  **/
  private Integer iBisTer1 = null;

  // @ApiModelProperty(value = "interno2")
 /**
   * interno2
  **/
  private String interno2 = null;

  // @ApiModelProperty(value = "iBisTer2")
 /**
   * iBisTer2
  **/
  private Integer iBisTer2 = null;

  // @ApiModelProperty(value = "scala")
 /**
   * scala
  **/
  private String scala = null;

  // @ApiModelProperty(value = "numerazione secondaria")
 /**
   * numerazione secondaria
  **/
  private String numerazioneSecondaria = null;

  // @ApiModelProperty(value = "fronte via")
 /**
   * fronte via
  **/
  private Integer fronteVia = null;

  // @ApiModelProperty(value = "codice abitativo")
 /**
   * codice abitativo
  **/
  private Integer codiceAbitativo = null;

  // @ApiModelProperty(value = "abitativo")
 /**
   * abitativo
  **/
  private String abitativo = null;

  // @ApiModelProperty(value = "cap")
 /**
   * cap
  **/
  private Integer cap = null;

  // @ApiModelProperty(value = "stato del civico")
 /**
   * stato del civico
  **/
  private Integer stato = null;

  // @ApiModelProperty(value = "campoNomadi")
 /**
   * campoNomadi
  **/
  private Integer campoNomadi = null;

  // @ApiModelProperty(value = "descrizione dello stato")
 /**
   * descrizione dello stato
  **/
  private String descrizioneStato = null;

  // @ApiModelProperty(value = "carraio")
 /**
   * carraio
  **/
  private Boolean carraio = null;

  // @ApiModelProperty(value = "uiu completate (il civico risulta con uiu propagate- indica la presenza di almeno una uiu attiva)")
 /**
   * uiu completate (il civico risulta con uiu propagate- indica la presenza di almeno una uiu attiva)
  **/
  private Boolean uiuCompletate = null;

  // @ApiModelProperty(value = "codice tipologia")
 /**
   * codice tipologia
  **/
  private String tipologia = null;

  // @ApiModelProperty(value = "descrizione tipologia")
 /**
   * descrizione tipologia
  **/
  private String descrizioneTipologia = null;

  // @ApiModelProperty(value = "decodifica di iBisTer")
 /**
   * decodifica di iBisTer
  **/
  private String bister = null;

  // @ApiModelProperty(value = "decodifica di iBisInterno1")
 /**
   * decodifica di iBisInterno1
  **/
  private String bisInterno1 = null;

  // @ApiModelProperty(value = "decodifica di iBisInterno2")
 /**
   * decodifica di iBisInterno2
  **/
  private String bisInterno2 = null;

  // @ApiModelProperty(value = "identificativo numerico della Sezione di Censimento Istat")
 /**
   * identificativo numerico della Sezione di Censimento Istat
  **/
  private Integer sezioneCensimento = null;

  // @ApiModelProperty(value = "identificativo della Circoscrizione")
 /**
   * identificativo della Circoscrizione
  **/
  private Integer idCircoscrizione = null;

  // @ApiModelProperty(value = "denominazione della Circoscrizione")
 /**
   * denominazione della Circoscrizione
  **/
  private String descCircoscrizione = null;

  // @ApiModelProperty(value = "identificativo dell'ambito cimiteriale")
 /**
   * identificativo dell'ambito cimiteriale
  **/
  private Integer idCimitero = null;

  // @ApiModelProperty(value = "denominazione dell'ambito cimiteriale")
 /**
   * denominazione dell'ambito cimiteriale
  **/
  private String descCimitero = null;

  // @ApiModelProperty(value = "codice della Localita' Istat")
 /**
   * codice della Localita' Istat
  **/
  private Integer idLocalitaIstat = null;

  // @ApiModelProperty(value = "denominazione della Localita' Istat")
 /**
   * denominazione della Localita' Istat
  **/
  private String descLocalitaIstat = null;

  // @ApiModelProperty(value = "identificativo del Distretto Assistenziale")
 /**
   * identificativo del Distretto Assistenziale
  **/
  private Integer idDistretto = null;

  // @ApiModelProperty(value = "denominazione Distretto Assistenza (ex Circoscrizione+Lettera)")
 /**
   * denominazione Distretto Assistenza (ex Circoscrizione+Lettera)
  **/
  private String descDistrAssistenza = null;

  // @ApiModelProperty(value = "identificativo Parrocchia")
 /**
   * identificativo Parrocchia
  **/
  private Integer idParrocchia = null;

  // @ApiModelProperty(value = "denominazione Parrocchia")
 /**
   * denominazione Parrocchia
  **/
  private String descParrocchia = null;

  // @ApiModelProperty(value = "identificativo Ex Quartiere")
 /**
   * identificativo Ex Quartiere
  **/
  private Integer idExQuartiere = null;

  // @ApiModelProperty(value = "denominazione Ex Quartiere")
 /**
   * denominazione Ex Quartiere
  **/
  private String descQuartiere = null;

  // @ApiModelProperty(value = "identificativo Asl")
 /**
   * identificativo Asl
  **/
  private Integer idAsl = null;

  // @ApiModelProperty(value = "denominazione Asl")
 /**
   * denominazione Asl
  **/
  private String descAsl = null;

  // @ApiModelProperty(value = "identificativo numerico Zona Statistica")
 /**
   * identificativo numerico Zona Statistica
  **/
  private Integer idZonaStatistica = null;

  // @ApiModelProperty(value = "denominazione Zona Statistica ('01', '09bis', ecc.)")
 /**
   * denominazione Zona Statistica ('01', '09bis', ecc.)
  **/
  private String descZonaStatistica = null;

  // @ApiModelProperty(value = "identificativo Raggruppamento Statistico")
 /**
   * identificativo Raggruppamento Statistico
  **/
  private Integer idRaggruppamento = null;

  // @ApiModelProperty(value = "denominazione Raggruppamento Statistico")
 /**
   * denominazione Raggruppamento Statistico
  **/
  private String descRaggrStatistico = null;
 /**
   * id del civico
   * @return idCivico
  **/
  @JsonProperty("idCivico")
  public Integer getIdCivico() {
    return idCivico;
  }

  public void setIdCivico(Integer idCivico) {
    this.idCivico = idCivico;
  }

  public CivicoZone idCivico(Integer idCivico) {
    this.idCivico = idCivico;
    return this;
  }

 /**
   * id della via
   * @return idVia
  **/
  @JsonProperty("idVia")
  public Integer getIdVia() {
    return idVia;
  }

  public void setIdVia(Integer idVia) {
    this.idVia = idVia;
  }

  public CivicoZone idVia(Integer idVia) {
    this.idVia = idVia;
    return this;
  }

 /**
   * numero radice
   * @return numeroRadice
  **/
  @JsonProperty("numeroRadice")
  public Integer getNumeroRadice() {
    return numeroRadice;
  }

  public void setNumeroRadice(Integer numeroRadice) {
    this.numeroRadice = numeroRadice;
  }

  public CivicoZone numeroRadice(Integer numeroRadice) {
    this.numeroRadice = numeroRadice;
    return this;
  }

 /**
   * iBisTer
   * @return iBisTer
  **/
  @JsonProperty("iBisTer")
  public Integer getIBisTer() {
    return iBisTer;
  }

  public void setIBisTer(Integer iBisTer) {
    this.iBisTer = iBisTer;
  }

  public CivicoZone iBisTer(Integer iBisTer) {
    this.iBisTer = iBisTer;
    return this;
  }

 /**
   * interno1
   * @return interno1
  **/
  @JsonProperty("interno1")
  public String getInterno1() {
    return interno1;
  }

  public void setInterno1(String interno1) {
    this.interno1 = interno1;
  }

  public CivicoZone interno1(String interno1) {
    this.interno1 = interno1;
    return this;
  }

 /**
   * iBisTer1
   * @return iBisTer1
  **/
  @JsonProperty("iBisTer1")
  public Integer getIBisTer1() {
    return iBisTer1;
  }

  public void setIBisTer1(Integer iBisTer1) {
    this.iBisTer1 = iBisTer1;
  }

  public CivicoZone iBisTer1(Integer iBisTer1) {
    this.iBisTer1 = iBisTer1;
    return this;
  }

 /**
   * interno2
   * @return interno2
  **/
  @JsonProperty("interno2")
  public String getInterno2() {
    return interno2;
  }

  public void setInterno2(String interno2) {
    this.interno2 = interno2;
  }

  public CivicoZone interno2(String interno2) {
    this.interno2 = interno2;
    return this;
  }

 /**
   * iBisTer2
   * @return iBisTer2
  **/
  @JsonProperty("iBisTer2")
  public Integer getIBisTer2() {
    return iBisTer2;
  }

  public void setIBisTer2(Integer iBisTer2) {
    this.iBisTer2 = iBisTer2;
  }

  public CivicoZone iBisTer2(Integer iBisTer2) {
    this.iBisTer2 = iBisTer2;
    return this;
  }

 /**
   * scala
   * @return scala
  **/
  @JsonProperty("scala")
  public String getScala() {
    return scala;
  }

  public void setScala(String scala) {
    this.scala = scala;
  }

  public CivicoZone scala(String scala) {
    this.scala = scala;
    return this;
  }

 /**
   * numerazione secondaria
   * @return numerazioneSecondaria
  **/
  @JsonProperty("numerazioneSecondaria")
  public String getNumerazioneSecondaria() {
    return numerazioneSecondaria;
  }

  public void setNumerazioneSecondaria(String numerazioneSecondaria) {
    this.numerazioneSecondaria = numerazioneSecondaria;
  }

  public CivicoZone numerazioneSecondaria(String numerazioneSecondaria) {
    this.numerazioneSecondaria = numerazioneSecondaria;
    return this;
  }

 /**
   * fronte via
   * @return fronteVia
  **/
  @JsonProperty("fronteVia")
  public Integer getFronteVia() {
    return fronteVia;
  }

  public void setFronteVia(Integer fronteVia) {
    this.fronteVia = fronteVia;
  }

  public CivicoZone fronteVia(Integer fronteVia) {
    this.fronteVia = fronteVia;
    return this;
  }

 /**
   * codice abitativo
   * @return codiceAbitativo
  **/
  @JsonProperty("codiceAbitativo")
  public Integer getCodiceAbitativo() {
    return codiceAbitativo;
  }

  public void setCodiceAbitativo(Integer codiceAbitativo) {
    this.codiceAbitativo = codiceAbitativo;
  }

  public CivicoZone codiceAbitativo(Integer codiceAbitativo) {
    this.codiceAbitativo = codiceAbitativo;
    return this;
  }

 /**
   * abitativo
   * @return abitativo
  **/
  @JsonProperty("abitativo")
  public String getAbitativo() {
    return abitativo;
  }

  public void setAbitativo(String abitativo) {
    this.abitativo = abitativo;
  }

  public CivicoZone abitativo(String abitativo) {
    this.abitativo = abitativo;
    return this;
  }

 /**
   * cap
   * @return cap
  **/
  @JsonProperty("cap")
  public Integer getCap() {
    return cap;
  }

  public void setCap(Integer cap) {
    this.cap = cap;
  }

  public CivicoZone cap(Integer cap) {
    this.cap = cap;
    return this;
  }

 /**
   * stato del civico
   * @return stato
  **/
  @JsonProperty("stato")
  public Integer getStato() {
    return stato;
  }

  public void setStato(Integer stato) {
    this.stato = stato;
  }

  public CivicoZone stato(Integer stato) {
    this.stato = stato;
    return this;
  }

 /**
   * campoNomadi
   * @return campoNomadi
  **/
  @JsonProperty("campoNomadi")
  public Integer getCampoNomadi() {
    return campoNomadi;
  }

  public void setCampoNomadi(Integer campoNomadi) {
    this.campoNomadi = campoNomadi;
  }

  public CivicoZone campoNomadi(Integer campoNomadi) {
    this.campoNomadi = campoNomadi;
    return this;
  }

 /**
   * descrizione dello stato
   * @return descrizioneStato
  **/
  @JsonProperty("descrizioneStato")
  public String getDescrizioneStato() {
    return descrizioneStato;
  }

  public void setDescrizioneStato(String descrizioneStato) {
    this.descrizioneStato = descrizioneStato;
  }

  public CivicoZone descrizioneStato(String descrizioneStato) {
    this.descrizioneStato = descrizioneStato;
    return this;
  }

 /**
   * carraio
   * @return carraio
  **/
  @JsonProperty("carraio")
  public Boolean isCarraio() {
    return carraio;
  }

  public void setCarraio(Boolean carraio) {
    this.carraio = carraio;
  }

  public CivicoZone carraio(Boolean carraio) {
    this.carraio = carraio;
    return this;
  }

 /**
   * uiu completate (il civico risulta con uiu propagate- indica la presenza di almeno una uiu attiva)
   * @return uiuCompletate
  **/
  @JsonProperty("uiuCompletate")
  public Boolean isUiuCompletate() {
    return uiuCompletate;
  }

  public void setUiuCompletate(Boolean uiuCompletate) {
    this.uiuCompletate = uiuCompletate;
  }

  public CivicoZone uiuCompletate(Boolean uiuCompletate) {
    this.uiuCompletate = uiuCompletate;
    return this;
  }

 /**
   * codice tipologia
   * @return tipologia
  **/
  @JsonProperty("tipologia")
  public String getTipologia() {
    return tipologia;
  }

  public void setTipologia(String tipologia) {
    this.tipologia = tipologia;
  }

  public CivicoZone tipologia(String tipologia) {
    this.tipologia = tipologia;
    return this;
  }

 /**
   * descrizione tipologia
   * @return descrizioneTipologia
  **/
  @JsonProperty("descrizioneTipologia")
  public String getDescrizioneTipologia() {
    return descrizioneTipologia;
  }

  public void setDescrizioneTipologia(String descrizioneTipologia) {
    this.descrizioneTipologia = descrizioneTipologia;
  }

  public CivicoZone descrizioneTipologia(String descrizioneTipologia) {
    this.descrizioneTipologia = descrizioneTipologia;
    return this;
  }

 /**
   * decodifica di iBisTer
   * @return bister
  **/
  @JsonProperty("bister")
  public String getBister() {
    return bister;
  }

  public void setBister(String bister) {
    this.bister = bister;
  }

  public CivicoZone bister(String bister) {
    this.bister = bister;
    return this;
  }

 /**
   * decodifica di iBisInterno1
   * @return bisInterno1
  **/
  @JsonProperty("bisInterno1")
  public String getBisInterno1() {
    return bisInterno1;
  }

  public void setBisInterno1(String bisInterno1) {
    this.bisInterno1 = bisInterno1;
  }

  public CivicoZone bisInterno1(String bisInterno1) {
    this.bisInterno1 = bisInterno1;
    return this;
  }

 /**
   * decodifica di iBisInterno2
   * @return bisInterno2
  **/
  @JsonProperty("bisInterno2")
  public String getBisInterno2() {
    return bisInterno2;
  }

  public void setBisInterno2(String bisInterno2) {
    this.bisInterno2 = bisInterno2;
  }

  public CivicoZone bisInterno2(String bisInterno2) {
    this.bisInterno2 = bisInterno2;
    return this;
  }

 /**
   * identificativo numerico della Sezione di Censimento Istat
   * @return sezioneCensimento
  **/
  @JsonProperty("sezioneCensimento")
  public Integer getSezioneCensimento() {
    return sezioneCensimento;
  }

  public void setSezioneCensimento(Integer sezioneCensimento) {
    this.sezioneCensimento = sezioneCensimento;
  }

  public CivicoZone sezioneCensimento(Integer sezioneCensimento) {
    this.sezioneCensimento = sezioneCensimento;
    return this;
  }

 /**
   * identificativo della Circoscrizione
   * @return idCircoscrizione
  **/
  @JsonProperty("idCircoscrizione")
  public Integer getIdCircoscrizione() {
    return idCircoscrizione;
  }

  public void setIdCircoscrizione(Integer idCircoscrizione) {
    this.idCircoscrizione = idCircoscrizione;
  }

  public CivicoZone idCircoscrizione(Integer idCircoscrizione) {
    this.idCircoscrizione = idCircoscrizione;
    return this;
  }

 /**
   * denominazione della Circoscrizione
   * @return descCircoscrizione
  **/
  @JsonProperty("descCircoscrizione")
  public String getDescCircoscrizione() {
    return descCircoscrizione;
  }

  public void setDescCircoscrizione(String descCircoscrizione) {
    this.descCircoscrizione = descCircoscrizione;
  }

  public CivicoZone descCircoscrizione(String descCircoscrizione) {
    this.descCircoscrizione = descCircoscrizione;
    return this;
  }

 /**
   * identificativo dell&#39;ambito cimiteriale
   * @return idCimitero
  **/
  @JsonProperty("idCimitero")
  public Integer getIdCimitero() {
    return idCimitero;
  }

  public void setIdCimitero(Integer idCimitero) {
    this.idCimitero = idCimitero;
  }

  public CivicoZone idCimitero(Integer idCimitero) {
    this.idCimitero = idCimitero;
    return this;
  }

 /**
   * denominazione dell&#39;ambito cimiteriale
   * @return descCimitero
  **/
  @JsonProperty("descCimitero")
  public String getDescCimitero() {
    return descCimitero;
  }

  public void setDescCimitero(String descCimitero) {
    this.descCimitero = descCimitero;
  }

  public CivicoZone descCimitero(String descCimitero) {
    this.descCimitero = descCimitero;
    return this;
  }

 /**
   * codice della Localita&#39; Istat
   * @return idLocalitaIstat
  **/
  @JsonProperty("idLocalitaIstat")
  public Integer getIdLocalitaIstat() {
    return idLocalitaIstat;
  }

  public void setIdLocalitaIstat(Integer idLocalitaIstat) {
    this.idLocalitaIstat = idLocalitaIstat;
  }

  public CivicoZone idLocalitaIstat(Integer idLocalitaIstat) {
    this.idLocalitaIstat = idLocalitaIstat;
    return this;
  }

 /**
   * denominazione della Localita&#39; Istat
   * @return descLocalitaIstat
  **/
  @JsonProperty("descLocalitaIstat")
  public String getDescLocalitaIstat() {
    return descLocalitaIstat;
  }

  public void setDescLocalitaIstat(String descLocalitaIstat) {
    this.descLocalitaIstat = descLocalitaIstat;
  }

  public CivicoZone descLocalitaIstat(String descLocalitaIstat) {
    this.descLocalitaIstat = descLocalitaIstat;
    return this;
  }

 /**
   * identificativo del Distretto Assistenziale
   * @return idDistretto
  **/
  @JsonProperty("idDistretto")
  public Integer getIdDistretto() {
    return idDistretto;
  }

  public void setIdDistretto(Integer idDistretto) {
    this.idDistretto = idDistretto;
  }

  public CivicoZone idDistretto(Integer idDistretto) {
    this.idDistretto = idDistretto;
    return this;
  }

 /**
   * denominazione Distretto Assistenza (ex Circoscrizione+Lettera)
   * @return descDistrAssistenza
  **/
  @JsonProperty("descDistrAssistenza")
  public String getDescDistrAssistenza() {
    return descDistrAssistenza;
  }

  public void setDescDistrAssistenza(String descDistrAssistenza) {
    this.descDistrAssistenza = descDistrAssistenza;
  }

  public CivicoZone descDistrAssistenza(String descDistrAssistenza) {
    this.descDistrAssistenza = descDistrAssistenza;
    return this;
  }

 /**
   * identificativo Parrocchia
   * @return idParrocchia
  **/
  @JsonProperty("idParrocchia")
  public Integer getIdParrocchia() {
    return idParrocchia;
  }

  public void setIdParrocchia(Integer idParrocchia) {
    this.idParrocchia = idParrocchia;
  }

  public CivicoZone idParrocchia(Integer idParrocchia) {
    this.idParrocchia = idParrocchia;
    return this;
  }

 /**
   * denominazione Parrocchia
   * @return descParrocchia
  **/
  @JsonProperty("descParrocchia")
  public String getDescParrocchia() {
    return descParrocchia;
  }

  public void setDescParrocchia(String descParrocchia) {
    this.descParrocchia = descParrocchia;
  }

  public CivicoZone descParrocchia(String descParrocchia) {
    this.descParrocchia = descParrocchia;
    return this;
  }

 /**
   * identificativo Ex Quartiere
   * @return idExQuartiere
  **/
  @JsonProperty("idExQuartiere")
  public Integer getIdExQuartiere() {
    return idExQuartiere;
  }

  public void setIdExQuartiere(Integer idExQuartiere) {
    this.idExQuartiere = idExQuartiere;
  }

  public CivicoZone idExQuartiere(Integer idExQuartiere) {
    this.idExQuartiere = idExQuartiere;
    return this;
  }

 /**
   * denominazione Ex Quartiere
   * @return descQuartiere
  **/
  @JsonProperty("descQuartiere")
  public String getDescQuartiere() {
    return descQuartiere;
  }

  public void setDescQuartiere(String descQuartiere) {
    this.descQuartiere = descQuartiere;
  }

  public CivicoZone descQuartiere(String descQuartiere) {
    this.descQuartiere = descQuartiere;
    return this;
  }

 /**
   * identificativo Asl
   * @return idAsl
  **/
  @JsonProperty("idAsl")
  public Integer getIdAsl() {
    return idAsl;
  }

  public void setIdAsl(Integer idAsl) {
    this.idAsl = idAsl;
  }

  public CivicoZone idAsl(Integer idAsl) {
    this.idAsl = idAsl;
    return this;
  }

 /**
   * denominazione Asl
   * @return descAsl
  **/
  @JsonProperty("descAsl")
  public String getDescAsl() {
    return descAsl;
  }

  public void setDescAsl(String descAsl) {
    this.descAsl = descAsl;
  }

  public CivicoZone descAsl(String descAsl) {
    this.descAsl = descAsl;
    return this;
  }

 /**
   * identificativo numerico Zona Statistica
   * @return idZonaStatistica
  **/
  @JsonProperty("idZonaStatistica")
  public Integer getIdZonaStatistica() {
    return idZonaStatistica;
  }

  public void setIdZonaStatistica(Integer idZonaStatistica) {
    this.idZonaStatistica = idZonaStatistica;
  }

  public CivicoZone idZonaStatistica(Integer idZonaStatistica) {
    this.idZonaStatistica = idZonaStatistica;
    return this;
  }

 /**
   * denominazione Zona Statistica (&#39;01&#39;, &#39;09bis&#39;, ecc.)
   * @return descZonaStatistica
  **/
  @JsonProperty("descZonaStatistica")
  public String getDescZonaStatistica() {
    return descZonaStatistica;
  }

  public void setDescZonaStatistica(String descZonaStatistica) {
    this.descZonaStatistica = descZonaStatistica;
  }

  public CivicoZone descZonaStatistica(String descZonaStatistica) {
    this.descZonaStatistica = descZonaStatistica;
    return this;
  }

 /**
   * identificativo Raggruppamento Statistico
   * @return idRaggruppamento
  **/
  @JsonProperty("idRaggruppamento")
  public Integer getIdRaggruppamento() {
    return idRaggruppamento;
  }

  public void setIdRaggruppamento(Integer idRaggruppamento) {
    this.idRaggruppamento = idRaggruppamento;
  }

  public CivicoZone idRaggruppamento(Integer idRaggruppamento) {
    this.idRaggruppamento = idRaggruppamento;
    return this;
  }

 /**
   * denominazione Raggruppamento Statistico
   * @return descRaggrStatistico
  **/
  @JsonProperty("descRaggrStatistico")
  public String getDescRaggrStatistico() {
    return descRaggrStatistico;
  }

  public void setDescRaggrStatistico(String descRaggrStatistico) {
    this.descRaggrStatistico = descRaggrStatistico;
  }

  public CivicoZone descRaggrStatistico(String descRaggrStatistico) {
    this.descRaggrStatistico = descRaggrStatistico;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CivicoZone {\n");
    
    sb.append("    idCivico: ").append(toIndentedString(idCivico)).append("\n");
    sb.append("    idVia: ").append(toIndentedString(idVia)).append("\n");
    sb.append("    numeroRadice: ").append(toIndentedString(numeroRadice)).append("\n");
    sb.append("    iBisTer: ").append(toIndentedString(iBisTer)).append("\n");
    sb.append("    interno1: ").append(toIndentedString(interno1)).append("\n");
    sb.append("    iBisTer1: ").append(toIndentedString(iBisTer1)).append("\n");
    sb.append("    interno2: ").append(toIndentedString(interno2)).append("\n");
    sb.append("    iBisTer2: ").append(toIndentedString(iBisTer2)).append("\n");
    sb.append("    scala: ").append(toIndentedString(scala)).append("\n");
    sb.append("    numerazioneSecondaria: ").append(toIndentedString(numerazioneSecondaria)).append("\n");
    sb.append("    fronteVia: ").append(toIndentedString(fronteVia)).append("\n");
    sb.append("    codiceAbitativo: ").append(toIndentedString(codiceAbitativo)).append("\n");
    sb.append("    abitativo: ").append(toIndentedString(abitativo)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    campoNomadi: ").append(toIndentedString(campoNomadi)).append("\n");
    sb.append("    descrizioneStato: ").append(toIndentedString(descrizioneStato)).append("\n");
    sb.append("    carraio: ").append(toIndentedString(carraio)).append("\n");
    sb.append("    uiuCompletate: ").append(toIndentedString(uiuCompletate)).append("\n");
    sb.append("    tipologia: ").append(toIndentedString(tipologia)).append("\n");
    sb.append("    descrizioneTipologia: ").append(toIndentedString(descrizioneTipologia)).append("\n");
    sb.append("    bister: ").append(toIndentedString(bister)).append("\n");
    sb.append("    bisInterno1: ").append(toIndentedString(bisInterno1)).append("\n");
    sb.append("    bisInterno2: ").append(toIndentedString(bisInterno2)).append("\n");
    sb.append("    sezioneCensimento: ").append(toIndentedString(sezioneCensimento)).append("\n");
    sb.append("    idCircoscrizione: ").append(toIndentedString(idCircoscrizione)).append("\n");
    sb.append("    descCircoscrizione: ").append(toIndentedString(descCircoscrizione)).append("\n");
    sb.append("    idCimitero: ").append(toIndentedString(idCimitero)).append("\n");
    sb.append("    descCimitero: ").append(toIndentedString(descCimitero)).append("\n");
    sb.append("    idLocalitaIstat: ").append(toIndentedString(idLocalitaIstat)).append("\n");
    sb.append("    descLocalitaIstat: ").append(toIndentedString(descLocalitaIstat)).append("\n");
    sb.append("    idDistretto: ").append(toIndentedString(idDistretto)).append("\n");
    sb.append("    descDistrAssistenza: ").append(toIndentedString(descDistrAssistenza)).append("\n");
    sb.append("    idParrocchia: ").append(toIndentedString(idParrocchia)).append("\n");
    sb.append("    descParrocchia: ").append(toIndentedString(descParrocchia)).append("\n");
    sb.append("    idExQuartiere: ").append(toIndentedString(idExQuartiere)).append("\n");
    sb.append("    descQuartiere: ").append(toIndentedString(descQuartiere)).append("\n");
    sb.append("    idAsl: ").append(toIndentedString(idAsl)).append("\n");
    sb.append("    descAsl: ").append(toIndentedString(descAsl)).append("\n");
    sb.append("    idZonaStatistica: ").append(toIndentedString(idZonaStatistica)).append("\n");
    sb.append("    descZonaStatistica: ").append(toIndentedString(descZonaStatistica)).append("\n");
    sb.append("    idRaggruppamento: ").append(toIndentedString(idRaggruppamento)).append("\n");
    sb.append("    descRaggrStatistico: ").append(toIndentedString(descRaggrStatistico)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

