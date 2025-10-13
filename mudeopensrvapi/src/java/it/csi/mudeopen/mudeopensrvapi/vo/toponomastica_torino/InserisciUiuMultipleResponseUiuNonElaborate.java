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

public class InserisciUiuMultipleResponseUiuNonElaborate  {
  
  // @ApiModelProperty(value = "nui")
 /**
   * nui
  **/
  private Integer nui = null;

  // @ApiModelProperty(value = "piano")
 /**
   * piano
  **/
  private String piano = null;

  // @ApiModelProperty(value = "attivo nel caso in cui la UIU era già esistente ed attiva")
 /**
   * attivo nel caso in cui la UIU era già esistente ed attiva
  **/
  private Boolean esistente = null;

  // @ApiModelProperty(value = "")
  private BaseErrore errore = null;
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

  public InserisciUiuMultipleResponseUiuNonElaborate nui(Integer nui) {
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

  public InserisciUiuMultipleResponseUiuNonElaborate piano(String piano) {
    this.piano = piano;
    return this;
  }

 /**
   * attivo nel caso in cui la UIU era già esistente ed attiva
   * @return esistente
  **/
  @JsonProperty("esistente")
  public Boolean isEsistente() {
    return esistente;
  }

  public void setEsistente(Boolean esistente) {
    this.esistente = esistente;
  }

  public InserisciUiuMultipleResponseUiuNonElaborate esistente(Boolean esistente) {
    this.esistente = esistente;
    return this;
  }

 /**
   * Get errore
   * @return errore
  **/
  @JsonProperty("errore")
  public BaseErrore getErrore() {
    return errore;
  }

  public void setErrore(BaseErrore errore) {
    this.errore = errore;
  }

  public InserisciUiuMultipleResponseUiuNonElaborate errore(BaseErrore errore) {
    this.errore = errore;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InserisciUiuMultipleResponseUiuNonElaborate {\n");
    
    sb.append("    nui: ").append(toIndentedString(nui)).append("\n");
    sb.append("    piano: ").append(toIndentedString(piano)).append("\n");
    sb.append("    esistente: ").append(toIndentedString(esistente)).append("\n");
    sb.append("    errore: ").append(toIndentedString(errore)).append("\n");
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

