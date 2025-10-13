/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.cosmo;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.ProtocolloDocumentoFruitore;

import javax.validation.constraints.*;
import io.swagger.annotations.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-12-01T11:43:27.748Z")
public class ArchiviazioneDocumentoFruitore   {

  private ProtocolloDocumentoFruitore protocollo = null;
  private String classificazione = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("protocollo")
  public ProtocolloDocumentoFruitore getProtocollo() {
    return protocollo;
  }
  public void setProtocollo(ProtocolloDocumentoFruitore protocollo) {
    this.protocollo = protocollo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("classificazione")
  public String getClassificazione() {
    return classificazione;
  }
  public void setClassificazione(String classificazione) {
    this.classificazione = classificazione;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArchiviazioneDocumentoFruitore archiviazioneDocumentoFruitore = (ArchiviazioneDocumentoFruitore) o;
    return Objects.equals(protocollo, archiviazioneDocumentoFruitore.protocollo) &&
        Objects.equals(classificazione, archiviazioneDocumentoFruitore.classificazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(protocollo, classificazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArchiviazioneDocumentoFruitore {\n");

    sb.append("    protocollo: ").append(toIndentedString(protocollo)).append("\n");
    sb.append("    classificazione: ").append(toIndentedString(classificazione)).append("\n");
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

