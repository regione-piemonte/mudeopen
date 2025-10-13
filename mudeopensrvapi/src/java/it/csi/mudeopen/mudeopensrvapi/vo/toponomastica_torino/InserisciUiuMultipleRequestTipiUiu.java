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

public class InserisciUiuMultipleRequestTipiUiu  {
  
  // @ApiModelProperty(required = true, value = "codice tipo uiu")
 /**
   * codice tipo uiu
  **/
  private String codiceTipoUiu = null;
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

  public InserisciUiuMultipleRequestTipiUiu codiceTipoUiu(String codiceTipoUiu) {
    this.codiceTipoUiu = codiceTipoUiu;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InserisciUiuMultipleRequestTipiUiu {\n");
    
    sb.append("    codiceTipoUiu: ").append(toIndentedString(codiceTipoUiu)).append("\n");
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

