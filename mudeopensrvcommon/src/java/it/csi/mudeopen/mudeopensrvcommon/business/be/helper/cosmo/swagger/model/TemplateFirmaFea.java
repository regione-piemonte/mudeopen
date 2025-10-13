/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class TemplateFirmaFea   {

  private String descrizione = null;
  private String codiceTipoDocumento = null;
  private Double coordinataX = null;
  private Double coordinataY = null;
  private Long pagina = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("descrizione")
  @NotNull
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceTipoDocumento")
  @NotNull
  public String getCodiceTipoDocumento() {
    return codiceTipoDocumento;
  }
  public void setCodiceTipoDocumento(String codiceTipoDocumento) {
    this.codiceTipoDocumento = codiceTipoDocumento;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("coordinataX")
  @NotNull
  public Double getCoordinataX() {
    return coordinataX;
  }
  public void setCoordinataX(Double coordinataX) {
    this.coordinataX = coordinataX;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("coordinataY")
  @NotNull
  public Double getCoordinataY() {
    return coordinataY;
  }
  public void setCoordinataY(Double coordinataY) {
    this.coordinataY = coordinataY;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("pagina")
  @NotNull
  public Long getPagina() {
    return pagina;
  }
  public void setPagina(Long pagina) {
    this.pagina = pagina;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateFirmaFea templateFirmaFea = (TemplateFirmaFea) o;
    return Objects.equals(descrizione, templateFirmaFea.descrizione) &&
        Objects.equals(codiceTipoDocumento, templateFirmaFea.codiceTipoDocumento) &&
        Objects.equals(coordinataX, templateFirmaFea.coordinataX) &&
        Objects.equals(coordinataY, templateFirmaFea.coordinataY) &&
        Objects.equals(pagina, templateFirmaFea.pagina);
  }

  @Override
  public int hashCode() {
    return Objects.hash(descrizione, codiceTipoDocumento, coordinataX, coordinataY, pagina);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateFirmaFea {\n");

    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    codiceTipoDocumento: ").append(toIndentedString(codiceTipoDocumento)).append("\n");
    sb.append("    coordinataX: ").append(toIndentedString(coordinataX)).append("\n");
    sb.append("    coordinataY: ").append(toIndentedString(coordinataY)).append("\n");
    sb.append("    pagina: ").append(toIndentedString(pagina)).append("\n");
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

