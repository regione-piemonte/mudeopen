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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.ProtocolloDocumentoFruitore;

import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class ArchiviazioneDocumento   {

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
    ArchiviazioneDocumento archiviazioneDocumento = (ArchiviazioneDocumento) o;
    return Objects.equals(protocollo, archiviazioneDocumento.protocollo) &&
        Objects.equals(classificazione, archiviazioneDocumento.classificazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(protocollo, classificazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArchiviazioneDocumento {\n");

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

