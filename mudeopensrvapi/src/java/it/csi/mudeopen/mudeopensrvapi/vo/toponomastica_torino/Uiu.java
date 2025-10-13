package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;



import java.util.ArrayList;
import java.util.List;
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

public class Uiu extends UiuLight {
  
  // @ApiModelProperty(value = "")
  private List<TipoUiu> tipiUiu = null;

  // @ApiModelProperty(value = "codice causa nascita")
 /**
   * codice causa nascita
  **/
  private String codCausaNascita = null;

  // @ApiModelProperty(value = "descrizione causa nascita")
 /**
   * descrizione causa nascita
  **/
  private String descrizioneCausaNascita = null;

  // @ApiModelProperty(value = "cod cessazione validita")
 /**
   * cod cessazione validita
  **/
  private String codCessazioneValidita = null;

  // @ApiModelProperty(value = "motivo cessazione validita")
 /**
   * motivo cessazione validita
  **/
  private String motivoCessazioneValidita = null;

  // @ApiModelProperty(value = "codice cessazione provenienza info")
 /**
   * codice cessazione provenienza info
  **/
  private String codiceCessazioneProvenienzaInfo = null;

  // @ApiModelProperty(value = "descrizione cessazione provenienza info")
 /**
   * descrizione cessazione provenienza info
  **/
  private String descrizioneCessazioneProvenienzaInfo = null;

  // @ApiModelProperty(value = "codice nascita provenienza info")
 /**
   * codice nascita provenienza info
  **/
  private String codiceNascitaProvenienzaInfo = null;

  // @ApiModelProperty(value = "descrizione nascita provenienza info")
 /**
   * descrizione nascita provenienza info
  **/
  private String descrizioneNascitaProvenienzaInfo = null;

  // @ApiModelProperty(value = "data decorrenza in formato ISO-8601, di default impostato alla data odierna  (in formato YYYY-MM-DD)")
 /**
   * data decorrenza in formato ISO-8601, di default impostato alla data odierna  (in formato YYYY-MM-DD)
  **/
  private LocalDate dataDecorrenza = null;

  // @ApiModelProperty(value = "data cessazione in formato ISO-8601, di default impostato alla data odierna (in formato YYYY-MM-DD)")
 /**
   * data cessazione in formato ISO-8601, di default impostato alla data odierna (in formato YYYY-MM-DD)
  **/
  private LocalDate dataCessazione = null;

  // @ApiModelProperty(value = "codice provenienza info")
 /**
   * codice provenienza info
  **/
  private String codiceProvenienzaInfo = null;

  // @ApiModelProperty(value = "descrizione provenienza info")
 /**
   * descrizione provenienza info
  **/
  private String descrizioneProvenienzaInfo = null;

  // @ApiModelProperty(value = "atc")
 /**
   * atc
  **/
  private Integer atc = null;
 /**
   * Get tipiUiu
   * @return tipiUiu
  **/
  @JsonProperty("tipiUiu")
  public List<TipoUiu> getTipiUiu() {
    return tipiUiu;
  }

  public void setTipiUiu(List<TipoUiu> tipiUiu) {
    this.tipiUiu = tipiUiu;
  }

  public Uiu tipiUiu(List<TipoUiu> tipiUiu) {
    this.tipiUiu = tipiUiu;
    return this;
  }

  public Uiu addTipiUiuItem(TipoUiu tipiUiuItem) {
    this.tipiUiu.add(tipiUiuItem);
    return this;
  }

 /**
   * codice causa nascita
   * @return codCausaNascita
  **/
  @JsonProperty("codCausaNascita")
  public String getCodCausaNascita() {
    return codCausaNascita;
  }

  public void setCodCausaNascita(String codCausaNascita) {
    this.codCausaNascita = codCausaNascita;
  }

  public Uiu codCausaNascita(String codCausaNascita) {
    this.codCausaNascita = codCausaNascita;
    return this;
  }

 /**
   * descrizione causa nascita
   * @return descrizioneCausaNascita
  **/
  @JsonProperty("descrizioneCausaNascita")
  public String getDescrizioneCausaNascita() {
    return descrizioneCausaNascita;
  }

  public void setDescrizioneCausaNascita(String descrizioneCausaNascita) {
    this.descrizioneCausaNascita = descrizioneCausaNascita;
  }

  public Uiu descrizioneCausaNascita(String descrizioneCausaNascita) {
    this.descrizioneCausaNascita = descrizioneCausaNascita;
    return this;
  }

 /**
   * cod cessazione validita
   * @return codCessazioneValidita
  **/
  @JsonProperty("codCessazioneValidita")
  public String getCodCessazioneValidita() {
    return codCessazioneValidita;
  }

  public void setCodCessazioneValidita(String codCessazioneValidita) {
    this.codCessazioneValidita = codCessazioneValidita;
  }

  public Uiu codCessazioneValidita(String codCessazioneValidita) {
    this.codCessazioneValidita = codCessazioneValidita;
    return this;
  }

 /**
   * motivo cessazione validita
   * @return motivoCessazioneValidita
  **/
  @JsonProperty("motivoCessazioneValidita")
  public String getMotivoCessazioneValidita() {
    return motivoCessazioneValidita;
  }

  public void setMotivoCessazioneValidita(String motivoCessazioneValidita) {
    this.motivoCessazioneValidita = motivoCessazioneValidita;
  }

  public Uiu motivoCessazioneValidita(String motivoCessazioneValidita) {
    this.motivoCessazioneValidita = motivoCessazioneValidita;
    return this;
  }

 /**
   * codice cessazione provenienza info
   * @return codiceCessazioneProvenienzaInfo
  **/
  @JsonProperty("codiceCessazioneProvenienzaInfo")
  public String getCodiceCessazioneProvenienzaInfo() {
    return codiceCessazioneProvenienzaInfo;
  }

  public void setCodiceCessazioneProvenienzaInfo(String codiceCessazioneProvenienzaInfo) {
    this.codiceCessazioneProvenienzaInfo = codiceCessazioneProvenienzaInfo;
  }

  public Uiu codiceCessazioneProvenienzaInfo(String codiceCessazioneProvenienzaInfo) {
    this.codiceCessazioneProvenienzaInfo = codiceCessazioneProvenienzaInfo;
    return this;
  }

 /**
   * descrizione cessazione provenienza info
   * @return descrizioneCessazioneProvenienzaInfo
  **/
  @JsonProperty("descrizioneCessazioneProvenienzaInfo")
  public String getDescrizioneCessazioneProvenienzaInfo() {
    return descrizioneCessazioneProvenienzaInfo;
  }

  public void setDescrizioneCessazioneProvenienzaInfo(String descrizioneCessazioneProvenienzaInfo) {
    this.descrizioneCessazioneProvenienzaInfo = descrizioneCessazioneProvenienzaInfo;
  }

  public Uiu descrizioneCessazioneProvenienzaInfo(String descrizioneCessazioneProvenienzaInfo) {
    this.descrizioneCessazioneProvenienzaInfo = descrizioneCessazioneProvenienzaInfo;
    return this;
  }

 /**
   * codice nascita provenienza info
   * @return codiceNascitaProvenienzaInfo
  **/
  @JsonProperty("codiceNascitaProvenienzaInfo")
  public String getCodiceNascitaProvenienzaInfo() {
    return codiceNascitaProvenienzaInfo;
  }

  public void setCodiceNascitaProvenienzaInfo(String codiceNascitaProvenienzaInfo) {
    this.codiceNascitaProvenienzaInfo = codiceNascitaProvenienzaInfo;
  }

  public Uiu codiceNascitaProvenienzaInfo(String codiceNascitaProvenienzaInfo) {
    this.codiceNascitaProvenienzaInfo = codiceNascitaProvenienzaInfo;
    return this;
  }

 /**
   * descrizione nascita provenienza info
   * @return descrizioneNascitaProvenienzaInfo
  **/
  @JsonProperty("descrizioneNascitaProvenienzaInfo")
  public String getDescrizioneNascitaProvenienzaInfo() {
    return descrizioneNascitaProvenienzaInfo;
  }

  public void setDescrizioneNascitaProvenienzaInfo(String descrizioneNascitaProvenienzaInfo) {
    this.descrizioneNascitaProvenienzaInfo = descrizioneNascitaProvenienzaInfo;
  }

  public Uiu descrizioneNascitaProvenienzaInfo(String descrizioneNascitaProvenienzaInfo) {
    this.descrizioneNascitaProvenienzaInfo = descrizioneNascitaProvenienzaInfo;
    return this;
  }

 /**
   * data decorrenza in formato ISO-8601, di default impostato alla data odierna  (in formato YYYY-MM-DD)
   * @return dataDecorrenza
  **/
  @JsonProperty("dataDecorrenza")
  public LocalDate getDataDecorrenza() {
    return dataDecorrenza;
  }

  public void setDataDecorrenza(LocalDate dataDecorrenza) {
    this.dataDecorrenza = dataDecorrenza;
  }

  public Uiu dataDecorrenza(LocalDate dataDecorrenza) {
    this.dataDecorrenza = dataDecorrenza;
    return this;
  }

 /**
   * data cessazione in formato ISO-8601, di default impostato alla data odierna (in formato YYYY-MM-DD)
   * @return dataCessazione
  **/
  @JsonProperty("dataCessazione")
  public LocalDate getDataCessazione() {
    return dataCessazione;
  }

  public void setDataCessazione(LocalDate dataCessazione) {
    this.dataCessazione = dataCessazione;
  }

  public Uiu dataCessazione(LocalDate dataCessazione) {
    this.dataCessazione = dataCessazione;
    return this;
  }

 /**
   * codice provenienza info
   * @return codiceProvenienzaInfo
  **/
  @JsonProperty("codiceProvenienzaInfo")
  public String getCodiceProvenienzaInfo() {
    return codiceProvenienzaInfo;
  }

  public void setCodiceProvenienzaInfo(String codiceProvenienzaInfo) {
    this.codiceProvenienzaInfo = codiceProvenienzaInfo;
  }

  public Uiu codiceProvenienzaInfo(String codiceProvenienzaInfo) {
    this.codiceProvenienzaInfo = codiceProvenienzaInfo;
    return this;
  }

 /**
   * descrizione provenienza info
   * @return descrizioneProvenienzaInfo
  **/
  @JsonProperty("descrizioneProvenienzaInfo")
  public String getDescrizioneProvenienzaInfo() {
    return descrizioneProvenienzaInfo;
  }

  public void setDescrizioneProvenienzaInfo(String descrizioneProvenienzaInfo) {
    this.descrizioneProvenienzaInfo = descrizioneProvenienzaInfo;
  }

  public Uiu descrizioneProvenienzaInfo(String descrizioneProvenienzaInfo) {
    this.descrizioneProvenienzaInfo = descrizioneProvenienzaInfo;
    return this;
  }

 /**
   * atc
   * @return atc
  **/
  @JsonProperty("atc")
  public Integer getAtc() {
    return atc;
  }

  public void setAtc(Integer atc) {
    this.atc = atc;
  }

  public Uiu atc(Integer atc) {
    this.atc = atc;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Uiu {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    tipiUiu: ").append(toIndentedString(tipiUiu)).append("\n");
    sb.append("    codCausaNascita: ").append(toIndentedString(codCausaNascita)).append("\n");
    sb.append("    descrizioneCausaNascita: ").append(toIndentedString(descrizioneCausaNascita)).append("\n");
    sb.append("    codCessazioneValidita: ").append(toIndentedString(codCessazioneValidita)).append("\n");
    sb.append("    motivoCessazioneValidita: ").append(toIndentedString(motivoCessazioneValidita)).append("\n");
    sb.append("    codiceCessazioneProvenienzaInfo: ").append(toIndentedString(codiceCessazioneProvenienzaInfo)).append("\n");
    sb.append("    descrizioneCessazioneProvenienzaInfo: ").append(toIndentedString(descrizioneCessazioneProvenienzaInfo)).append("\n");
    sb.append("    codiceNascitaProvenienzaInfo: ").append(toIndentedString(codiceNascitaProvenienzaInfo)).append("\n");
    sb.append("    descrizioneNascitaProvenienzaInfo: ").append(toIndentedString(descrizioneNascitaProvenienzaInfo)).append("\n");
    sb.append("    dataDecorrenza: ").append(toIndentedString(dataDecorrenza)).append("\n");
    sb.append("    dataCessazione: ").append(toIndentedString(dataCessazione)).append("\n");
    sb.append("    codiceProvenienzaInfo: ").append(toIndentedString(codiceProvenienzaInfo)).append("\n");
    sb.append("    descrizioneProvenienzaInfo: ").append(toIndentedString(descrizioneProvenienzaInfo)).append("\n");
    sb.append("    atc: ").append(toIndentedString(atc)).append("\n");
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

