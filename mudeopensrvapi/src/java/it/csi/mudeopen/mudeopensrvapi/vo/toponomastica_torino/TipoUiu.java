package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;

import org.joda.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoUiu  {
  
  // @ApiModelProperty(required = true, value = "codice tipo uiu")
 /**
   * codice tipo uiu
  **/
  private String codiceTipoUiu = null;

  // @ApiModelProperty(value = "descrizione")
 /**
   * descrizione
  **/
  private String descrizione = null;

  // @ApiModelProperty(value = "data decorrenza tipo uiu  (in formato YYYY-MM-DD)")
 /**
   * data decorrenza tipo uiu  (in formato YYYY-MM-DD)
  **/
  private LocalDate dataDecorrenzaTipoUiu = null;
 /**
   * codice tipo uiu
   * @return codiceTipoUiu
  **/
  @JsonProperty("codiceTipoUiu")
  public String getCodiceTipoUiu() {
    return codiceTipoUiu;
  }

  public void setCodiceTipoUiu(String codiceTipoUiu) {
    this.codiceTipoUiu = codiceTipoUiu;
  }

  public TipoUiu codiceTipoUiu(String codiceTipoUiu) {
    this.codiceTipoUiu = codiceTipoUiu;
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

  public TipoUiu descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * data decorrenza tipo uiu  (in formato YYYY-MM-DD)
   * @return dataDecorrenzaTipoUiu
  **/
  @JsonProperty("dataDecorrenzaTipoUiu")
  public LocalDate getDataDecorrenzaTipoUiu() {
    return dataDecorrenzaTipoUiu;
  }

  public void setDataDecorrenzaTipoUiu(LocalDate dataDecorrenzaTipoUiu) {
    this.dataDecorrenzaTipoUiu = dataDecorrenzaTipoUiu;
  }

  public TipoUiu dataDecorrenzaTipoUiu(LocalDate dataDecorrenzaTipoUiu) {
    this.dataDecorrenzaTipoUiu = dataDecorrenzaTipoUiu;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TipoUiu {\n");
    
    sb.append("    codiceTipoUiu: ").append(toIndentedString(codiceTipoUiu)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    dataDecorrenzaTipoUiu: ").append(toIndentedString(dataDecorrenzaTipoUiu)).append("\n");
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

