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
public class AttivitaFruitore   {

  private String nome = null;
  private String descrizione = null;
  private String nomeGruppoAssegnatario = null;
  private String dataInserimento = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("nome")
  @NotNull
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

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

  @ApiModelProperty(value = "")
  @JsonProperty("nomeGruppoAssegnatario")
  public String getNomeGruppoAssegnatario() {
    return nomeGruppoAssegnatario;
  }
  public void setNomeGruppoAssegnatario(String nomeGruppoAssegnatario) {
    this.nomeGruppoAssegnatario = nomeGruppoAssegnatario;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataInserimento")
  public String getDataInserimento() {
    return dataInserimento;
  }
  public void setDataInserimento(String dataInserimento) {
    this.dataInserimento = dataInserimento;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttivitaFruitore attivitaFruitore = (AttivitaFruitore) o;
    return Objects.equals(nome, attivitaFruitore.nome) &&
        Objects.equals(descrizione, attivitaFruitore.descrizione) &&
        Objects.equals(nomeGruppoAssegnatario, attivitaFruitore.nomeGruppoAssegnatario) &&
        Objects.equals(dataInserimento, attivitaFruitore.dataInserimento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, descrizione, nomeGruppoAssegnatario, dataInserimento);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttivitaFruitore {\n");

    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    nomeGruppoAssegnatario: ").append(toIndentedString(nomeGruppoAssegnatario)).append("\n");
    sb.append("    dataInserimento: ").append(toIndentedString(dataInserimento)).append("\n");
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

