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

public class Piano  {
  
  // @ApiModelProperty(value = "codice piano")
 /**
   * codice piano
  **/
  private String codicePiano = null;

  // @ApiModelProperty(value = "descrizione")
 /**
   * descrizione
  **/
  private String descrizione = null;
 /**
   * codice piano
   * @return codicePiano
  **/
  @JsonProperty("codicePiano")
  public String getCodicePiano() {
    return codicePiano;
  }

  public void setCodicePiano(String codicePiano) {
    this.codicePiano = codicePiano;
  }

  public Piano codicePiano(String codicePiano) {
    this.codicePiano = codicePiano;
    return this;
  }

 /**
   * descrizione
   * @return descrizione
  **/
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Piano descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Piano {\n");
    
    sb.append("    codicePiano: ").append(toIndentedString(codicePiano)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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

