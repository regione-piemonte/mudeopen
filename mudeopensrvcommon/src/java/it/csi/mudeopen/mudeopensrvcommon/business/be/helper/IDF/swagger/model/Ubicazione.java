/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
////import io.swagger.v3.oas.annotations.media.Schema;


//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2024-12-18T22:40:48.348410165Z[GMT]")
public class Ubicazione   {
  private String comune = null;
  private String codiceIstat = null;
  private String sezione = null;
  private String foglio = null;
  private String particella = null;

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("comune")
  @NotNull
  public String getComune() {
    return comune;
  }
  public void setComune(String comune) {
    this.comune = comune;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("codiceIstat")
  @NotNull
  public String getCodiceIstat() {
    return codiceIstat;
  }
  public void setCodiceIstat(String codiceIstat) {
    this.codiceIstat = codiceIstat;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("sezione")
  @NotNull
  public String getSezione() {
    return sezione;
  }
  public void setSezione(String sezione) {
    this.sezione = sezione;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("foglio")
  @NotNull
  public String getFoglio() {
    return foglio;
  }
  public void setFoglio(String foglio) {
    this.foglio = foglio;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("particella")
  @NotNull
  public String getParticella() {
    return particella;
  }
  public void setParticella(String particella) {
    this.particella = particella;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ubicazione ubicazione = (Ubicazione) o;
    return Objects.equals(comune, ubicazione.comune) &&
        Objects.equals(codiceIstat, ubicazione.codiceIstat) &&
        Objects.equals(sezione, ubicazione.sezione) &&
        Objects.equals(foglio, ubicazione.foglio) &&
        Objects.equals(particella, ubicazione.particella);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comune, codiceIstat, sezione, foglio, particella);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ubicazione {\n");
    
    sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
    sb.append("    codiceIstat: ").append(toIndentedString(codiceIstat)).append("\n");
    sb.append("    sezione: ").append(toIndentedString(sezione)).append("\n");
    sb.append("    foglio: ").append(toIndentedString(foglio)).append("\n");
    sb.append("    particella: ").append(toIndentedString(particella)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
