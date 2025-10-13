package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;


import java.util.ArrayList;
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

public class InserisciUiuMultipleResponse  {
  
  // @ApiModelProperty(value = "")
  private List<Integer> uiuInserite = null;

  // @ApiModelProperty(value = "")
  private List<InserisciUiuMultipleResponseUiuNonElaborate> uiuNonElaborate = null;
 /**
   * Get uiuInserite
   * @return uiuInserite
  **/
  @JsonProperty("uiuInserite")
  public List<Integer> getUiuInserite() {
    return uiuInserite;
  }

  public void setUiuInserite(List<Integer> uiuInserite) {
    this.uiuInserite = uiuInserite;
  }

  public InserisciUiuMultipleResponse uiuInserite(List<Integer> uiuInserite) {
    this.uiuInserite = uiuInserite;
    return this;
  }

  public InserisciUiuMultipleResponse addUiuInseriteItem(Integer uiuInseriteItem) {
    this.uiuInserite.add(uiuInseriteItem);
    return this;
  }

 /**
   * Get uiuNonElaborate
   * @return uiuNonElaborate
  **/
  @JsonProperty("uiuNonElaborate")
  public List<InserisciUiuMultipleResponseUiuNonElaborate> getUiuNonElaborate() {
    return uiuNonElaborate;
  }

  public void setUiuNonElaborate(List<InserisciUiuMultipleResponseUiuNonElaborate> uiuNonElaborate) {
    this.uiuNonElaborate = uiuNonElaborate;
  }

  public InserisciUiuMultipleResponse uiuNonElaborate(List<InserisciUiuMultipleResponseUiuNonElaborate> uiuNonElaborate) {
    this.uiuNonElaborate = uiuNonElaborate;
    return this;
  }

  public InserisciUiuMultipleResponse addUiuNonElaborateItem(InserisciUiuMultipleResponseUiuNonElaborate uiuNonElaborateItem) {
    this.uiuNonElaborate.add(uiuNonElaborateItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InserisciUiuMultipleResponse {\n");
    
    sb.append("    uiuInserite: ").append(toIndentedString(uiuInserite)).append("\n");
    sb.append("    uiuNonElaborate: ").append(toIndentedString(uiuNonElaborate)).append("\n");
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

