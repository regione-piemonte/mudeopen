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

public class Sedime  {
  
  // @ApiModelProperty(value = "id del sedime")
 /**
   * id del sedime
  **/
  private Integer idSedime = null;

  // @ApiModelProperty(value = "descrizione del sedime")
 /**
   * descrizione del sedime
  **/
  private String descrizione = null;

  // @ApiModelProperty(value = "descrizione breve del sedime")
 /**
   * descrizione breve del sedime
  **/
  private String descrizioneBreve = null;

  // @ApiModelProperty(value = "preposizione del sedime")
 /**
   * preposizione del sedime
  **/
  private String preposizione = null;
 /**
   * id del sedime
   * @return idSedime
  **/
  @JsonProperty("idSedime")
  public Integer getIdSedime() {
    return idSedime;
  }

  public void setIdSedime(Integer idSedime) {
    this.idSedime = idSedime;
  }

  public Sedime idSedime(Integer idSedime) {
    this.idSedime = idSedime;
    return this;
  }

 /**
   * descrizione del sedime
   * @return descrizione
  **/
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Sedime descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * descrizione breve del sedime
   * @return descrizioneBreve
  **/
  @JsonProperty("descrizioneBreve")
  public String getDescrizioneBreve() {
    return descrizioneBreve;
  }

  public void setDescrizioneBreve(String descrizioneBreve) {
    this.descrizioneBreve = descrizioneBreve;
  }

  public Sedime descrizioneBreve(String descrizioneBreve) {
    this.descrizioneBreve = descrizioneBreve;
    return this;
  }

 /**
   * preposizione del sedime
   * @return preposizione
  **/
  @JsonProperty("preposizione")
  public String getPreposizione() {
    return preposizione;
  }

  public void setPreposizione(String preposizione) {
    this.preposizione = preposizione;
  }

  public Sedime preposizione(String preposizione) {
    this.preposizione = preposizione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sedime {\n");
    
    sb.append("    idSedime: ").append(toIndentedString(idSedime)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    descrizioneBreve: ").append(toIndentedString(descrizioneBreve)).append("\n");
    sb.append("    preposizione: ").append(toIndentedString(preposizione)).append("\n");
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

