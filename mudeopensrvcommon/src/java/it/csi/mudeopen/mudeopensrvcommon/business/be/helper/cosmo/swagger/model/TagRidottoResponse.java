/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;

//import io.swagger.annotations.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class TagRidottoResponse   {

  private String codice = null;
  private String descrizione = null;
  private TipoTag tipoTag = null;
  private String warning = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codice")
  @NotNull
  public String getCodice() {
    return codice;
  }
  public void setCodice(String codice) {
    this.codice = codice;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("tipoTag")
  @NotNull
  public TipoTag getTipoTag() {
    return tipoTag;
  }
  public void setTipoTag(TipoTag tipoTag) {
    this.tipoTag = tipoTag;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("warning")
  public String getWarning() {
    return warning;
  }
  public void setWarning(String warning) {
    this.warning = warning;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagRidottoResponse tagRidottoResponse = (TagRidottoResponse) o;
    return Objects.equals(codice, tagRidottoResponse.codice) &&
        Objects.equals(descrizione, tagRidottoResponse.descrizione) &&
        Objects.equals(tipoTag, tagRidottoResponse.tipoTag) &&
        Objects.equals(warning, tagRidottoResponse.warning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codice, descrizione, tipoTag, warning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagRidottoResponse {\n");

    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    tipoTag: ").append(toIndentedString(tipoTag)).append("\n");
    sb.append("    warning: ").append(toIndentedString(warning)).append("\n");
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

