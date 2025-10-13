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

public class ViaLight  {
  
  // @ApiModelProperty(value = "id della via")
 /**
   * id della via
  **/
  private Integer idVia = null;

  // @ApiModelProperty(value = "stato della via")
 /**
   * stato della via
  **/
  private Integer stato = null;

  // @ApiModelProperty(value = "descrizione dello stato")
 /**
   * descrizione dello stato
  **/
  private String descStato = null;

  // @ApiModelProperty(value = "convenzionale via")
 /**
   * convenzionale via
  **/
  private Integer convenzionale = null;

  // @ApiModelProperty(value = "")
  private Sedime sedime = null;

  // @ApiModelProperty(value = "denominazione principale")
 /**
   * denominazione principale
  **/
  private String denominazionePrincipale = null;

  // @ApiModelProperty(value = "denominazione secondaria")
 /**
   * denominazione secondaria
  **/
  private String denominazioneSecondaria = null;

  // @ApiModelProperty(value = "denominazione breve")
 /**
   * denominazione breve
  **/
  private String denominazioneBreve = null;
 /**
   * id della via
   * @return idVia
  **/
  @JsonProperty("idVia")
  public Integer getIdVia() {
    return idVia;
  }

  public void setIdVia(Integer idVia) {
    this.idVia = idVia;
  }

  public ViaLight idVia(Integer idVia) {
    this.idVia = idVia;
    return this;
  }

 /**
   * stato della via
   * @return stato
  **/
  @JsonProperty("stato")
  public Integer getStato() {
    return stato;
  }

  public void setStato(Integer stato) {
    this.stato = stato;
  }

  public ViaLight stato(Integer stato) {
    this.stato = stato;
    return this;
  }

 /**
   * descrizione dello stato
   * @return descStato
  **/
  @JsonProperty("descStato")
  public String getDescStato() {
    return descStato;
  }

  public void setDescStato(String descStato) {
    this.descStato = descStato;
  }

  public ViaLight descStato(String descStato) {
    this.descStato = descStato;
    return this;
  }

 /**
   * convenzionale via
   * @return convenzionale
  **/
  @JsonProperty("convenzionale")
  public Integer getConvenzionale() {
    return convenzionale;
  }

  public void setConvenzionale(Integer convenzionale) {
    this.convenzionale = convenzionale;
  }

  public ViaLight convenzionale(Integer convenzionale) {
    this.convenzionale = convenzionale;
    return this;
  }

 /**
   * Get sedime
   * @return sedime
  **/
  @JsonProperty("sedime")
  public Sedime getSedime() {
    return sedime;
  }

  public void setSedime(Sedime sedime) {
    this.sedime = sedime;
  }

  public ViaLight sedime(Sedime sedime) {
    this.sedime = sedime;
    return this;
  }

 /**
   * denominazione principale
   * @return denominazionePrincipale
  **/
  @JsonProperty("denominazionePrincipale")
  public String getDenominazionePrincipale() {
    return denominazionePrincipale;
  }

  public void setDenominazionePrincipale(String denominazionePrincipale) {
    this.denominazionePrincipale = denominazionePrincipale;
  }

  public ViaLight denominazionePrincipale(String denominazionePrincipale) {
    this.denominazionePrincipale = denominazionePrincipale;
    return this;
  }

 /**
   * denominazione secondaria
   * @return denominazioneSecondaria
  **/
  @JsonProperty("denominazioneSecondaria")
  public String getDenominazioneSecondaria() {
    return denominazioneSecondaria;
  }

  public void setDenominazioneSecondaria(String denominazioneSecondaria) {
    this.denominazioneSecondaria = denominazioneSecondaria;
  }

  public ViaLight denominazioneSecondaria(String denominazioneSecondaria) {
    this.denominazioneSecondaria = denominazioneSecondaria;
    return this;
  }

 /**
   * denominazione breve
   * @return denominazioneBreve
  **/
  @JsonProperty("denominazioneBreve")
  public String getDenominazioneBreve() {
    return denominazioneBreve;
  }

  public void setDenominazioneBreve(String denominazioneBreve) {
    this.denominazioneBreve = denominazioneBreve;
  }

  public ViaLight denominazioneBreve(String denominazioneBreve) {
    this.denominazioneBreve = denominazioneBreve;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ViaLight {\n");
    
    sb.append("    idVia: ").append(toIndentedString(idVia)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    descStato: ").append(toIndentedString(descStato)).append("\n");
    sb.append("    convenzionale: ").append(toIndentedString(convenzionale)).append("\n");
    sb.append("    sedime: ").append(toIndentedString(sedime)).append("\n");
    sb.append("    denominazionePrincipale: ").append(toIndentedString(denominazionePrincipale)).append("\n");
    sb.append("    denominazioneSecondaria: ").append(toIndentedString(denominazioneSecondaria)).append("\n");
    sb.append("    denominazioneBreve: ").append(toIndentedString(denominazioneBreve)).append("\n");
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

