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

public class UiuLight  {
  
  // @ApiModelProperty(value = "id della uiu")
 /**
   * id della uiu
  **/
  private Integer idUiu = null;

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

  // @ApiModelProperty(value = "descrizione del piano")
 /**
   * descrizione del piano
  **/
  private String descrizionePiano = null;

  // @ApiModelProperty(value = "stato")
 /**
   * stato
  **/
  private Integer stato = null;

  // @ApiModelProperty(value = "descrizione stato")
 /**
   * descrizione stato
  **/
  private String descrizioneStato = null;
 /**
   * id della uiu
   * @return idUiu
  **/
  @JsonProperty("idUiu")
  public Integer getIdUiu() {
    return idUiu;
  }

  public void setIdUiu(Integer idUiu) {
    this.idUiu = idUiu;
  }

  public UiuLight idUiu(Integer idUiu) {
    this.idUiu = idUiu;
    return this;
  }

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

  public UiuLight nui(Integer nui) {
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

  public UiuLight piano(String piano) {
    this.piano = piano;
    return this;
  }

 /**
   * descrizione del piano
   * @return descrizionePiano
  **/
  @JsonProperty("descrizionePiano")
  public String getDescrizionePiano() {
    return descrizionePiano;
  }

  public void setDescrizionePiano(String descrizionePiano) {
    this.descrizionePiano = descrizionePiano;
  }

  public UiuLight descrizionePiano(String descrizionePiano) {
    this.descrizionePiano = descrizionePiano;
    return this;
  }

 /**
   * stato
   * @return stato
  **/
  @JsonProperty("stato")
  public Integer getStato() {
    return stato;
  }

  public void setStato(Integer stato) {
    this.stato = stato;
  }

  public UiuLight stato(Integer stato) {
    this.stato = stato;
    return this;
  }

 /**
   * descrizione stato
   * @return descrizioneStato
  **/
  @JsonProperty("descrizioneStato")
  public String getDescrizioneStato() {
    return descrizioneStato;
  }

  public void setDescrizioneStato(String descrizioneStato) {
    this.descrizioneStato = descrizioneStato;
  }

  public UiuLight descrizioneStato(String descrizioneStato) {
    this.descrizioneStato = descrizioneStato;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UiuLight {\n");
    
    sb.append("    idUiu: ").append(toIndentedString(idUiu)).append("\n");
    sb.append("    nui: ").append(toIndentedString(nui)).append("\n");
    sb.append("    piano: ").append(toIndentedString(piano)).append("\n");
    sb.append("    descrizionePiano: ").append(toIndentedString(descrizionePiano)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    descrizioneStato: ").append(toIndentedString(descrizioneStato)).append("\n");
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

