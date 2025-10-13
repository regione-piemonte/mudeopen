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

public class CivicoLight  {
  
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

  // @ApiModelProperty(value = "indirizzo")
 /**
   * indirizzo
  **/
  private String indirizzo = null;

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

  // @ApiModelProperty(value = "codice tipologia civico")
 /**
   * codice tipologia civico
  **/
  private String tipologia = null;

  // @ApiModelProperty(value = "descrizione tipologia civico")
 /**
   * descrizione tipologia civico
  **/
  private String descrizioneTipologia = null;

  // @ApiModelProperty(value = "uiu completate (il civico risulta con uiu propagate- indica la presenza di almeno una uiu attiva)")
 /**
   * uiu completate (il civico risulta con uiu propagate- indica la presenza di almeno una uiu attiva)
  **/
  private Boolean uiuCompletate = null;

  // @ApiModelProperty(value = "esponente (composto dalla decodifica degli attributi riferiti a bister, interni, scale e secondari)")
 /**
   * esponente (composto dalla decodifica degli attributi riferiti a bister, interni, scale e secondari)
  **/
  private String esponente = null;
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

  public CivicoLight idCivico(Integer idCivico) {
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

  public CivicoLight idVia(Integer idVia) {
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

  public CivicoLight numeroRadice(Integer numeroRadice) {
    this.numeroRadice = numeroRadice;
    return this;
  }

 /**
   * indirizzo
   * @return indirizzo
  **/
  @JsonProperty("indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public CivicoLight indirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
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

  public CivicoLight cap(Integer cap) {
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

  public CivicoLight stato(Integer stato) {
    this.stato = stato;
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

  public CivicoLight descrizioneStato(String descrizioneStato) {
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

  public CivicoLight carraio(Boolean carraio) {
    this.carraio = carraio;
    return this;
  }

 /**
   * codice tipologia civico
   * @return tipologia
  **/
  @JsonProperty("tipologia")
  public String getTipologia() {
    return tipologia;
  }

  public void setTipologia(String tipologia) {
    this.tipologia = tipologia;
  }

  public CivicoLight tipologia(String tipologia) {
    this.tipologia = tipologia;
    return this;
  }

 /**
   * descrizione tipologia civico
   * @return descrizioneTipologia
  **/
  @JsonProperty("descrizioneTipologia")
  public String getDescrizioneTipologia() {
    return descrizioneTipologia;
  }

  public void setDescrizioneTipologia(String descrizioneTipologia) {
    this.descrizioneTipologia = descrizioneTipologia;
  }

  public CivicoLight descrizioneTipologia(String descrizioneTipologia) {
    this.descrizioneTipologia = descrizioneTipologia;
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

  public CivicoLight uiuCompletate(Boolean uiuCompletate) {
    this.uiuCompletate = uiuCompletate;
    return this;
  }

 /**
   * esponente (composto dalla decodifica degli attributi riferiti a bister, interni, scale e secondari)
   * @return esponente
  **/
  @JsonProperty("esponente")
  public String getEsponente() {
    return esponente;
  }

  public void setEsponente(String esponente) {
    this.esponente = esponente;
  }

  public CivicoLight esponente(String esponente) {
    this.esponente = esponente;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CivicoLight {\n");
    
    sb.append("    idCivico: ").append(toIndentedString(idCivico)).append("\n");
    sb.append("    idVia: ").append(toIndentedString(idVia)).append("\n");
    sb.append("    numeroRadice: ").append(toIndentedString(numeroRadice)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    descrizioneStato: ").append(toIndentedString(descrizioneStato)).append("\n");
    sb.append("    carraio: ").append(toIndentedString(carraio)).append("\n");
    sb.append("    tipologia: ").append(toIndentedString(tipologia)).append("\n");
    sb.append("    descrizioneTipologia: ").append(toIndentedString(descrizioneTipologia)).append("\n");
    sb.append("    uiuCompletate: ").append(toIndentedString(uiuCompletate)).append("\n");
    sb.append("    esponente: ").append(toIndentedString(esponente)).append("\n");
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

