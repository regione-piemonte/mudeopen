/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class EsitoCreazioneDocumentoLinkFruitore   {

  private CreaDocumentoLinkFruitoreRequest input = null;
  private DocumentoFruitore output = null;
  private Esito esito = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("input")
  public CreaDocumentoLinkFruitoreRequest getInput() {
    return input;
  }
  public void setInput(CreaDocumentoLinkFruitoreRequest input) {
    this.input = input;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("output")
  public DocumentoFruitore getOutput() {
    return output;
  }
  public void setOutput(DocumentoFruitore output) {
    this.output = output;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("esito")
  public Esito getEsito() {
    return esito;
  }
  public void setEsito(Esito esito) {
    this.esito = esito;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EsitoCreazioneDocumentoLinkFruitore esitoCreazioneDocumentoLinkFruitore = (EsitoCreazioneDocumentoLinkFruitore) o;
    return Objects.equals(input, esitoCreazioneDocumentoLinkFruitore.input) &&
        Objects.equals(output, esitoCreazioneDocumentoLinkFruitore.output) &&
        Objects.equals(esito, esitoCreazioneDocumentoLinkFruitore.esito);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, output, esito);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoCreazioneDocumentoLinkFruitore {\n");

    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
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

