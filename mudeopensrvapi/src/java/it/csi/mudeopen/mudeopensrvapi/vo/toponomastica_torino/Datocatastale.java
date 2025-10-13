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

public class Datocatastale  {
  
  // @ApiModelProperty(value = "Id dato catastale")
 /**
   * Id dato catastale
  **/
  private Integer idDatoCatastale = null;

  // @ApiModelProperty(value = "Numero subalterno del catasto (es. 0003)")
 /**
   * Numero subalterno del catasto (es. 0003)
  **/
  private String subalterno = null;

  // @ApiModelProperty(value = "Numero catasto (es. 00150)")
 /**
   * Numero catasto (es. 00150)
  **/
  private String numero = null;

  // @ApiModelProperty(value = "Numero foglio catasto (es. 1418)")
 /**
   * Numero foglio catasto (es. 1418)
  **/
  private String foglio = null;

  // @ApiModelProperty(value = "Numero Sezione catasto (es. 000)")
 /**
   * Numero Sezione catasto (es. 000)
  **/
  private String sezione = null;

  // @ApiModelProperty(value = "- RT - ZZ  - 00  - 01  - 02  - 03  - 04  - 05  - 06 - 07 ")
 /**
   * - RT - ZZ  - 00  - 01  - 02  - 03  - 04  - 05  - 06 - 07 
  **/
  private String codiceProvenienzaInfo = null;

  // @ApiModelProperty(value = "- RT = Ribaltamento TARSU - ZZ = Caricamento iniziale  - 00 = valori nulli  - 01 = Amministratore - 02 = Comunicato del proprietario  - 03 = Ufficio Tributi - 04 = esiste in mnf - 05 = Ufficio Toponomastica - 06 = categorià già variata da tarsu - 07 = Diana's Files ")
 /**
   * - RT = Ribaltamento TARSU - ZZ = Caricamento iniziale  - 00 = valori nulli  - 01 = Amministratore - 02 = Comunicato del proprietario  - 03 = Ufficio Tributi - 04 = esiste in mnf - 05 = Ufficio Toponomastica - 06 = categorià già variata da tarsu - 07 = Diana's Files 
  **/
  private String descrizioneProvenienzaInfo = null;

  // @ApiModelProperty(value = "- RT  - ZZ  - 00  - 01  - 02  - 04  - 05  - 06  - 07  - 08  - 09  - 10  - 11  - 12  ")
 /**
   * - RT  - ZZ  - 00  - 01  - 02  - 04  - 05  - 06  - 07  - 08  - 09  - 10  - 11  - 12  
  **/
  private String codCessazioneValidita = null;

  // @ApiModelProperty(value = "- RT = Ribaltamento TARSU - ZZ = Caricamento iniziale - 00 = N.A. - 01 = Errore materiale - 02 = Accorpamento con altra U.I.U. - 04 = cessazione tecnica per gest si rilevato - 05 = Soppressione numero civico principale (S3) - 06 = Soppressione per cambio denominazione via numero civico principale (S4) - 07 = Soppressione numero civico secondario (S6) - 08 = Soppressione per cambio denominazione via numero civico secondario (S7) - 09 = Sostituzione U.I.U. - 10 = cessazione tecnica indirizzo bloccato - 11 = PORZIONE DI UIU UNITA DI FATTO - 12 = dissociazione identificativo catastale soppresso in catasto  ")
 /**
   * - RT = Ribaltamento TARSU - ZZ = Caricamento iniziale - 00 = N.A. - 01 = Errore materiale - 02 = Accorpamento con altra U.I.U. - 04 = cessazione tecnica per gest si rilevato - 05 = Soppressione numero civico principale (S3) - 06 = Soppressione per cambio denominazione via numero civico principale (S4) - 07 = Soppressione numero civico secondario (S6) - 08 = Soppressione per cambio denominazione via numero civico secondario (S7) - 09 = Sostituzione U.I.U. - 10 = cessazione tecnica indirizzo bloccato - 11 = PORZIONE DI UIU UNITA DI FATTO - 12 = dissociazione identificativo catastale soppresso in catasto  
  **/
  private String motivoCessazioneValidita = null;

  // @ApiModelProperty(value = "Data cessazione validita (in formato YYYY-MM-DD)")
 /**
   * Data cessazione validita (in formato YYYY-MM-DD)
  **/
  private LocalDate cessazioneValidita = null;

  // @ApiModelProperty(value = "Data inizio validita (in formato YYYY-MM-DD)")
 /**
   * Data inizio validita (in formato YYYY-MM-DD)
  **/
  private LocalDate inizioValidita = null;

  // @ApiModelProperty(value = "Flag di validazione dato catastale = S per Validato - N per Non validato")
 /**
   * Flag di validazione dato catastale = S per Validato - N per Non validato
  **/
  private String validita = null;

  // @ApiModelProperty(value = "- 0 = per Soppressa  - 1 = per Attiva  - 3 = per Record non trovati su scarico MNF ")
 /**
   * - 0 = per Soppressa  - 1 = per Attiva  - 3 = per Record non trovati su scarico MNF 
  **/
  private Integer statoCatastale = null;

  // @ApiModelProperty(value = "Soppresso - Attivo - Record non trovati su scarico MNF")
 /**
   * Soppresso - Attivo - Record non trovati su scarico MNF
  **/
  private String descStatoCatastale = null;
 /**
   * Id dato catastale
   * @return idDatoCatastale
  **/
  @JsonProperty("idDatoCatastale")
  public Integer getIdDatoCatastale() {
    return idDatoCatastale;
  }

  public void setIdDatoCatastale(Integer idDatoCatastale) {
    this.idDatoCatastale = idDatoCatastale;
  }

  public Datocatastale idDatoCatastale(Integer idDatoCatastale) {
    this.idDatoCatastale = idDatoCatastale;
    return this;
  }

 /**
   * Numero subalterno del catasto (es. 0003)
   * @return subalterno
  **/
  @JsonProperty("subalterno")
  public String getSubalterno() {
    return subalterno;
  }

  public void setSubalterno(String subalterno) {
    this.subalterno = subalterno;
  }

  public Datocatastale subalterno(String subalterno) {
    this.subalterno = subalterno;
    return this;
  }

 /**
   * Numero catasto (es. 00150)
   * @return numero
  **/
  @JsonProperty("numero")
  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public Datocatastale numero(String numero) {
    this.numero = numero;
    return this;
  }

 /**
   * Numero foglio catasto (es. 1418)
   * @return foglio
  **/
  @JsonProperty("foglio")
  public String getFoglio() {
    return foglio;
  }

  public void setFoglio(String foglio) {
    this.foglio = foglio;
  }

  public Datocatastale foglio(String foglio) {
    this.foglio = foglio;
    return this;
  }

 /**
   * Numero Sezione catasto (es. 000)
   * @return sezione
  **/
  @JsonProperty("sezione")
  public String getSezione() {
    return sezione;
  }

  public void setSezione(String sezione) {
    this.sezione = sezione;
  }

  public Datocatastale sezione(String sezione) {
    this.sezione = sezione;
    return this;
  }

 /**
   * - RT - ZZ  - 00  - 01  - 02  - 03  - 04  - 05  - 06 - 07 
   * @return codiceProvenienzaInfo
  **/
  @JsonProperty("codiceProvenienzaInfo")
  public String getCodiceProvenienzaInfo() {
    return codiceProvenienzaInfo;
  }

  public void setCodiceProvenienzaInfo(String codiceProvenienzaInfo) {
    this.codiceProvenienzaInfo = codiceProvenienzaInfo;
  }

  public Datocatastale codiceProvenienzaInfo(String codiceProvenienzaInfo) {
    this.codiceProvenienzaInfo = codiceProvenienzaInfo;
    return this;
  }

 /**
   * - RT &#x3D; Ribaltamento TARSU - ZZ &#x3D; Caricamento iniziale  - 00 &#x3D; valori nulli  - 01 &#x3D; Amministratore - 02 &#x3D; Comunicato del proprietario  - 03 &#x3D; Ufficio Tributi - 04 &#x3D; esiste in mnf - 05 &#x3D; Ufficio Toponomastica - 06 &#x3D; categorià già variata da tarsu - 07 &#x3D; Diana&#39;s Files 
   * @return descrizioneProvenienzaInfo
  **/
  @JsonProperty("descrizioneProvenienzaInfo")
  public String getDescrizioneProvenienzaInfo() {
    return descrizioneProvenienzaInfo;
  }

  public void setDescrizioneProvenienzaInfo(String descrizioneProvenienzaInfo) {
    this.descrizioneProvenienzaInfo = descrizioneProvenienzaInfo;
  }

  public Datocatastale descrizioneProvenienzaInfo(String descrizioneProvenienzaInfo) {
    this.descrizioneProvenienzaInfo = descrizioneProvenienzaInfo;
    return this;
  }

 /**
   * - RT  - ZZ  - 00  - 01  - 02  - 04  - 05  - 06  - 07  - 08  - 09  - 10  - 11  - 12  
   * @return codCessazioneValidita
  **/
  @JsonProperty("codCessazioneValidita")
  public String getCodCessazioneValidita() {
    return codCessazioneValidita;
  }

  public void setCodCessazioneValidita(String codCessazioneValidita) {
    this.codCessazioneValidita = codCessazioneValidita;
  }

  public Datocatastale codCessazioneValidita(String codCessazioneValidita) {
    this.codCessazioneValidita = codCessazioneValidita;
    return this;
  }

 /**
   * - RT &#x3D; Ribaltamento TARSU - ZZ &#x3D; Caricamento iniziale - 00 &#x3D; N.A. - 01 &#x3D; Errore materiale - 02 &#x3D; Accorpamento con altra U.I.U. - 04 &#x3D; cessazione tecnica per gest si rilevato - 05 &#x3D; Soppressione numero civico principale (S3) - 06 &#x3D; Soppressione per cambio denominazione via numero civico principale (S4) - 07 &#x3D; Soppressione numero civico secondario (S6) - 08 &#x3D; Soppressione per cambio denominazione via numero civico secondario (S7) - 09 &#x3D; Sostituzione U.I.U. - 10 &#x3D; cessazione tecnica indirizzo bloccato - 11 &#x3D; PORZIONE DI UIU UNITA DI FATTO - 12 &#x3D; dissociazione identificativo catastale soppresso in catasto  
   * @return motivoCessazioneValidita
  **/
  @JsonProperty("motivoCessazioneValidita")
  public String getMotivoCessazioneValidita() {
    return motivoCessazioneValidita;
  }

  public void setMotivoCessazioneValidita(String motivoCessazioneValidita) {
    this.motivoCessazioneValidita = motivoCessazioneValidita;
  }

  public Datocatastale motivoCessazioneValidita(String motivoCessazioneValidita) {
    this.motivoCessazioneValidita = motivoCessazioneValidita;
    return this;
  }

 /**
   * Data cessazione validita (in formato YYYY-MM-DD)
   * @return cessazioneValidita
  **/
  @JsonProperty("cessazioneValidita")
  public LocalDate getCessazioneValidita() {
    return cessazioneValidita;
  }

  public void setCessazioneValidita(LocalDate cessazioneValidita) {
    this.cessazioneValidita = cessazioneValidita;
  }

  public Datocatastale cessazioneValidita(LocalDate cessazioneValidita) {
    this.cessazioneValidita = cessazioneValidita;
    return this;
  }

 /**
   * Data inizio validita (in formato YYYY-MM-DD)
   * @return inizioValidita
  **/
  @JsonProperty("inizioValidita")
  public LocalDate getInizioValidita() {
    return inizioValidita;
  }

  public void setInizioValidita(LocalDate inizioValidita) {
    this.inizioValidita = inizioValidita;
  }

  public Datocatastale inizioValidita(LocalDate inizioValidita) {
    this.inizioValidita = inizioValidita;
    return this;
  }

 /**
   * Flag di validazione dato catastale &#x3D; S per Validato - N per Non validato
   * @return validita
  **/
  @JsonProperty("validita")
  public String getValidita() {
    return validita;
  }

  public void setValidita(String validita) {
    this.validita = validita;
  }

  public Datocatastale validita(String validita) {
    this.validita = validita;
    return this;
  }

 /**
   * - 0 &#x3D; per Soppressa  - 1 &#x3D; per Attiva  - 3 &#x3D; per Record non trovati su scarico MNF 
   * @return statoCatastale
  **/
  @JsonProperty("statoCatastale")
  public Integer getStatoCatastale() {
    return statoCatastale;
  }

  public void setStatoCatastale(Integer statoCatastale) {
    this.statoCatastale = statoCatastale;
  }

  public Datocatastale statoCatastale(Integer statoCatastale) {
    this.statoCatastale = statoCatastale;
    return this;
  }

 /**
   * Soppresso - Attivo - Record non trovati su scarico MNF
   * @return descStatoCatastale
  **/
  @JsonProperty("descStatoCatastale")
  public String getDescStatoCatastale() {
    return descStatoCatastale;
  }

  public void setDescStatoCatastale(String descStatoCatastale) {
    this.descStatoCatastale = descStatoCatastale;
  }

  public Datocatastale descStatoCatastale(String descStatoCatastale) {
    this.descStatoCatastale = descStatoCatastale;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Datocatastale {\n");
    
    sb.append("    idDatoCatastale: ").append(toIndentedString(idDatoCatastale)).append("\n");
    sb.append("    subalterno: ").append(toIndentedString(subalterno)).append("\n");
    sb.append("    numero: ").append(toIndentedString(numero)).append("\n");
    sb.append("    foglio: ").append(toIndentedString(foglio)).append("\n");
    sb.append("    sezione: ").append(toIndentedString(sezione)).append("\n");
    sb.append("    codiceProvenienzaInfo: ").append(toIndentedString(codiceProvenienzaInfo)).append("\n");
    sb.append("    descrizioneProvenienzaInfo: ").append(toIndentedString(descrizioneProvenienzaInfo)).append("\n");
    sb.append("    codCessazioneValidita: ").append(toIndentedString(codCessazioneValidita)).append("\n");
    sb.append("    motivoCessazioneValidita: ").append(toIndentedString(motivoCessazioneValidita)).append("\n");
    sb.append("    cessazioneValidita: ").append(toIndentedString(cessazioneValidita)).append("\n");
    sb.append("    inizioValidita: ").append(toIndentedString(inizioValidita)).append("\n");
    sb.append("    validita: ").append(toIndentedString(validita)).append("\n");
    sb.append("    statoCatastale: ").append(toIndentedString(statoCatastale)).append("\n");
    sb.append("    descStatoCatastale: ").append(toIndentedString(descStatoCatastale)).append("\n");
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

