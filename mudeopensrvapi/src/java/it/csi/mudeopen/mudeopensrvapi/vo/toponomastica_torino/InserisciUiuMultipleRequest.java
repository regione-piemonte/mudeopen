package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InserisciUiuMultipleRequest  {
  
  // @ApiModelProperty(required = true, value = "nui")
 /**
   * nui
  **/
  private Integer nui = null;

  // @ApiModelProperty(required = true, value = "piano")
 /**
   * piano
  **/
  private String piano = null;

  // @ApiModelProperty(value = "codice causa nascita")
 /**
   * codice causa nascita
  **/
  private String codCausaNascita = "00";

  // @ApiModelProperty(value = "codice provenienza info")
 /**
   * codice provenienza info
  **/
  private String codiceProvenienzaInfo = "00";

  // @ApiModelProperty(value = "codice nascita provenienza info")
 /**
   * codice nascita provenienza info
  **/
  private String codiceNascitaProvenienzaInfo = "00";

  // @ApiModelProperty(value = "in formato ISO-8601, di default impostato alla data odierna (in formato YYYY-MM-DD)")
 /**
   * in formato ISO-8601, di default impostato alla data odierna (in formato YYYY-MM-DD)
  **/
  private Date dataDecorrenza = null;

  // @ApiModelProperty(required = true, value = "elenco dei tipi UIU associati")
 /**
   * elenco dei tipi UIU associati
  **/
  private List<InserisciUiuMultipleRequestTipiUiu> tipiUiu = new ArrayList<InserisciUiuMultipleRequestTipiUiu>();
 /**
   * nui
   * @return nui
  **/
  @JsonProperty("nui")
  public Integer getNui() {
    return nui;
  }

  public void setNui(Integer nui) {
    this.nui = nui;
  }

  public InserisciUiuMultipleRequest nui(Integer nui) {
    this.nui = nui;
    return this;
  }

 /**
   * piano
   * @return piano
  **/
  @JsonProperty("piano")
  public String getPiano() {
    return piano;
  }

  public void setPiano(String piano) {
    this.piano = piano;
  }

  public InserisciUiuMultipleRequest piano(String piano) {
    this.piano = piano;
    return this;
  }

 /**
   * codice causa nascita
   * @return codCausaNascita
  **/
  @JsonProperty("codCausaNascita")
  public String getCodCausaNascita() {
    return codCausaNascita;
  }

  public void setCodCausaNascita(String codCausaNascita) {
    this.codCausaNascita = codCausaNascita;
  }

  public InserisciUiuMultipleRequest codCausaNascita(String codCausaNascita) {
    this.codCausaNascita = codCausaNascita;
    return this;
  }

 /**
   * codice provenienza info
   * @return codiceProvenienzaInfo
  **/
  @JsonProperty("codiceProvenienzaInfo")
  public String getCodiceProvenienzaInfo() {
    return codiceProvenienzaInfo;
  }

  public void setCodiceProvenienzaInfo(String codiceProvenienzaInfo) {
    this.codiceProvenienzaInfo = codiceProvenienzaInfo;
  }

  public InserisciUiuMultipleRequest codiceProvenienzaInfo(String codiceProvenienzaInfo) {
    this.codiceProvenienzaInfo = codiceProvenienzaInfo;
    return this;
  }

 /**
   * codice nascita provenienza info
   * @return codiceNascitaProvenienzaInfo
  **/
  @JsonProperty("codiceNascitaProvenienzaInfo")
  public String getCodiceNascitaProvenienzaInfo() {
    return codiceNascitaProvenienzaInfo;
  }

  public void setCodiceNascitaProvenienzaInfo(String codiceNascitaProvenienzaInfo) {
    this.codiceNascitaProvenienzaInfo = codiceNascitaProvenienzaInfo;
  }

  public InserisciUiuMultipleRequest codiceNascitaProvenienzaInfo(String codiceNascitaProvenienzaInfo) {
    this.codiceNascitaProvenienzaInfo = codiceNascitaProvenienzaInfo;
    return this;
  }

 /**
   * in formato ISO-8601, di default impostato alla data odierna (in formato YYYY-MM-DD)
   * @return dataDecorrenza
  **/
  @JsonProperty("dataDecorrenza")
  public Date getDataDecorrenza() {
    return dataDecorrenza;
  }

  public void setDataDecorrenza(Date dataDecorrenza) {
    this.dataDecorrenza = dataDecorrenza;
  }

  public InserisciUiuMultipleRequest dataDecorrenza(Date dataDecorrenza) {
    this.dataDecorrenza = dataDecorrenza;
    return this;
  }

 /**
   * elenco dei tipi UIU associati
   * @return tipiUiu
  **/
  @JsonProperty("tipiUiu")
  public List<InserisciUiuMultipleRequestTipiUiu> getTipiUiu() {
    return tipiUiu;
  }

  public void setTipiUiu(List<InserisciUiuMultipleRequestTipiUiu> tipiUiu) {
    this.tipiUiu = tipiUiu;
  }

  public InserisciUiuMultipleRequest tipiUiu(List<InserisciUiuMultipleRequestTipiUiu> tipiUiu) {
    this.tipiUiu = tipiUiu;
    return this;
  }

  public InserisciUiuMultipleRequest addTipiUiuItem(InserisciUiuMultipleRequestTipiUiu tipiUiuItem) {
    this.tipiUiu.add(tipiUiuItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InserisciUiuMultipleRequest {\n");
    
    sb.append("    nui: ").append(toIndentedString(nui)).append("\n");
    sb.append("    piano: ").append(toIndentedString(piano)).append("\n");
    sb.append("    codCausaNascita: ").append(toIndentedString(codCausaNascita)).append("\n");
    sb.append("    codiceProvenienzaInfo: ").append(toIndentedString(codiceProvenienzaInfo)).append("\n");
    sb.append("    codiceNascitaProvenienzaInfo: ").append(toIndentedString(codiceNascitaProvenienzaInfo)).append("\n");
    sb.append("    dataDecorrenza: ").append(toIndentedString(dataDecorrenza)).append("\n");
    sb.append("    tipiUiu: ").append(toIndentedString(tipiUiu)).append("\n");
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

