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

public class CercaIdUiuIdCivicoIndirizzoPianoNuiByDatiCatastaliResponse  {
  
  // @ApiModelProperty(value = "identificativo UIU")
 /**
   * identificativo UIU
  **/
  private Integer idUiu = null;

  // @ApiModelProperty(value = "identificativo Civico")
 /**
   * identificativo Civico
  **/
  private Integer idCivico = null;

  // @ApiModelProperty(example = "LUNGO PO ANTONELLI 117 int. 4 int. E PIANO QUINTO NUI: 23", value = "")
  private String indirizzoCompleto = null;
 /**
   * identificativo UIU
   * @return idUiu
  **/
  @JsonProperty("idUiu")
  public Integer getIdUiu() {
    return idUiu;
  }

  public void setIdUiu(Integer idUiu) {
    this.idUiu = idUiu;
  }

  public CercaIdUiuIdCivicoIndirizzoPianoNuiByDatiCatastaliResponse idUiu(Integer idUiu) {
    this.idUiu = idUiu;
    return this;
  }

 /**
   * identificativo Civico
   * @return idCivico
  **/
  @JsonProperty("idCivico")
  public Integer getIdCivico() {
    return idCivico;
  }

  public void setIdCivico(Integer idCivico) {
    this.idCivico = idCivico;
  }

  public CercaIdUiuIdCivicoIndirizzoPianoNuiByDatiCatastaliResponse idCivico(Integer idCivico) {
    this.idCivico = idCivico;
    return this;
  }

 /**
   * Get indirizzoCompleto
   * @return indirizzoCompleto
  **/
  @JsonProperty("indirizzoCompleto")
  public String getIndirizzoCompleto() {
    return indirizzoCompleto;
  }

  public void setIndirizzoCompleto(String indirizzoCompleto) {
    this.indirizzoCompleto = indirizzoCompleto;
  }

  public CercaIdUiuIdCivicoIndirizzoPianoNuiByDatiCatastaliResponse indirizzoCompleto(String indirizzoCompleto) {
    this.indirizzoCompleto = indirizzoCompleto;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CercaIdUiuIdCivicoIndirizzoPianoNuiByDatiCatastaliResponse {\n");
    
    sb.append("    idUiu: ").append(toIndentedString(idUiu)).append("\n");
    sb.append("    idCivico: ").append(toIndentedString(idCivico)).append("\n");
    sb.append("    indirizzoCompleto: ").append(toIndentedString(indirizzoCompleto)).append("\n");
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

