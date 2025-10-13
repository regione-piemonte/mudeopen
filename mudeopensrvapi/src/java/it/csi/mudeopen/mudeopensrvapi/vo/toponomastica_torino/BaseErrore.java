package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseErrore  {
  
  // @ApiModelProperty(value = "")
  private String code = null;

  // @ApiModelProperty(value = "")
  private String title = null;

  // @ApiModelProperty(value = "")
  private Map<String, String> detail = null;
 /**
   * Get code
   * @return code
  **/
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public BaseErrore code(String code) {
    this.code = code;
    return this;
  }

 /**
   * Get title
   * @return title
  **/
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BaseErrore title(String title) {
    this.title = title;
    return this;
  }

 /**
   * Get detail
   * @return detail
  **/
  @JsonProperty("detail")
  public Map<String, String> getDetail() {
    return detail;
  }

  public void setDetail(Map<String, String> detail) {
    this.detail = detail;
  }

  public BaseErrore detail(Map<String, String> detail) {
    this.detail = detail;
    return this;
  }

  public BaseErrore putDetailItem(String key, String detailItem) {
    this.detail.put(key, detailItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseErrore {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
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

