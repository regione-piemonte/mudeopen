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

public class StoricoCivico  {
  
  // @ApiModelProperty(value = "id del nuovo Civico")
 /**
   * id del nuovo Civico
  **/
  private Integer citNew = null;

  // @ApiModelProperty(value = "id del civico che viene sostituito")
 /**
   * id del civico che viene sostituito
  **/
  private Integer citOld = null;

  // @ApiModelProperty(value = "note")
 /**
   * note
  **/
  private String note = null;

  // @ApiModelProperty(value = "data modifica in formato ISO-8601 (in formato YYYY-MM-DD)")
 /**
   * data modifica in formato ISO-8601 (in formato YYYY-MM-DD)
  **/
  private LocalDate dataModifica = null;
 /**
   * id del nuovo Civico
   * @return citNew
  **/
  @JsonProperty("citNew")
  public Integer getCitNew() {
    return citNew;
  }

  public void setCitNew(Integer citNew) {
    this.citNew = citNew;
  }

  public StoricoCivico citNew(Integer citNew) {
    this.citNew = citNew;
    return this;
  }

 /**
   * id del civico che viene sostituito
   * @return citOld
  **/
  @JsonProperty("citOld")
  public Integer getCitOld() {
    return citOld;
  }

  public void setCitOld(Integer citOld) {
    this.citOld = citOld;
  }

  public StoricoCivico citOld(Integer citOld) {
    this.citOld = citOld;
    return this;
  }

 /**
   * note
   * @return note
  **/
  @JsonProperty("note")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public StoricoCivico note(String note) {
    this.note = note;
    return this;
  }

 /**
   * data modifica in formato ISO-8601 (in formato YYYY-MM-DD)
   * @return dataModifica
  **/
  @JsonProperty("dataModifica")
  public LocalDate getDataModifica() {
    return dataModifica;
  }

  public void setDataModifica(LocalDate dataModifica) {
    this.dataModifica = dataModifica;
  }

  public StoricoCivico dataModifica(LocalDate dataModifica) {
    this.dataModifica = dataModifica;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoricoCivico {\n");
    
    sb.append("    citNew: ").append(toIndentedString(citNew)).append("\n");
    sb.append("    citOld: ").append(toIndentedString(citOld)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    dataModifica: ").append(toIndentedString(dataModifica)).append("\n");
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

