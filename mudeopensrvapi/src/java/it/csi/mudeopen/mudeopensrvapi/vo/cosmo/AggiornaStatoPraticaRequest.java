/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.cosmo;

import java.util.Objects;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.DocumentoFruitore;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;

public class AggiornaStatoPraticaRequest   {

  private String idRiferimento = null;
  private String idFiglia = null;
  private String codice = null;
  private String codiceFiscaleUtente = null;
  private String numeroPratica = null;
  private String annoPratica = null;
  private String numeroProtocollo = null;
  private Timestamp dataProtocollo = null;
  private Timestamp dataCambioStato = null;
  private List<DocumentoFruitore> documenti = new ArrayList<DocumentoFruitore>();
  private String tipoIstruttoria = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idRiferimento")
  @NotNull
  public String getIdRiferimento() {
    return idRiferimento;
  }
  public void setIdRiferimento(String idRiferimento) {
    this.idRiferimento = idRiferimento;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("idFiglia")
  public String getIdFiglia() {
    return idFiglia;
  }
  public void setIdFiglia(String idFiglia) {
    this.idFiglia = idFiglia;
  }

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

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceFiscaleUtente")
  @NotNull
  public String getCodiceFiscaleUtente() {
    return codiceFiscaleUtente;
  }
  public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
    this.codiceFiscaleUtente = codiceFiscaleUtente;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("numeroPratica")
  public String getNumeroPratica() {
    return numeroPratica;
  }
  public void setNumeroPratica(String numeroPratica) {
    this.numeroPratica = numeroPratica;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("annoPratica")
  public String getAnnoPratica() {
    return annoPratica;
  }
  public void setAnnoPratica(String annoPratica) {
    this.annoPratica = annoPratica;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("numeroProtocollo")
  public String getNumeroProtocollo() {
    return numeroProtocollo;
  }
  public void setNumeroProtocollo(String numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataProtocollo")
  public Timestamp getDataProtocollo() {
    return dataProtocollo;
  }
  public void setDataProtocollo(Timestamp dataProtocollo) {
    this.dataProtocollo = dataProtocollo;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("dataCambioStato")
  @NotNull
  public Timestamp getDataCambioStato() {
    return dataCambioStato;
  }
  public void setDataCambioStato(Timestamp dataCambioStato) {
    this.dataCambioStato = dataCambioStato;
  }

  /**
   * array dei documenti della pratica
   **/

  @ApiModelProperty(value = "array dei documenti della pratica")
  @JsonProperty("documenti")
  public List<DocumentoFruitore> getDocumenti() {
    return documenti;
  }
  public void setDocumenti(List<DocumentoFruitore> documenti) {
    this.documenti = documenti;
  }

  @ApiModelProperty(value = "tipo istruttoria: 2 = Presa d'atto, 1 = Controllo Formale")
  @JsonProperty("Tipo_istruttoria")
	public String getTipoIstruttoria() {
		return tipoIstruttoria;
	}
	
	public void setTipoIstruttoria(String tipoIstruttoria) {
		this.tipoIstruttoria = tipoIstruttoria;
	}
	
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiornaStatoPraticaRequest aggiornaStatoPraticaRequest = (AggiornaStatoPraticaRequest) o;
    return Objects.equals(idRiferimento, aggiornaStatoPraticaRequest.idRiferimento) &&
        Objects.equals(idFiglia, aggiornaStatoPraticaRequest.idFiglia) &&
        Objects.equals(codice, aggiornaStatoPraticaRequest.codice) &&
        Objects.equals(codiceFiscaleUtente, aggiornaStatoPraticaRequest.codiceFiscaleUtente) &&
        Objects.equals(numeroPratica, aggiornaStatoPraticaRequest.numeroPratica) &&
        Objects.equals(annoPratica, aggiornaStatoPraticaRequest.annoPratica) &&
        Objects.equals(numeroProtocollo, aggiornaStatoPraticaRequest.numeroProtocollo) &&
        Objects.equals(dataProtocollo, aggiornaStatoPraticaRequest.dataProtocollo) &&
        Objects.equals(dataCambioStato, aggiornaStatoPraticaRequest.dataCambioStato) &&
        Objects.equals(documenti, aggiornaStatoPraticaRequest.documenti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idRiferimento, idFiglia, codice, codiceFiscaleUtente, numeroPratica, annoPratica, numeroProtocollo, dataProtocollo, dataCambioStato, documenti);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaStatoPraticaRequest {\n");

    sb.append("    idRiferimento: ").append(toIndentedString(idRiferimento)).append("\n");
    sb.append("    idFiglia: ").append(toIndentedString(idFiglia)).append("\n");
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    codiceFiscaleUtente: ").append(toIndentedString(codiceFiscaleUtente)).append("\n");
    sb.append("    numeroPratica: ").append(toIndentedString(numeroPratica)).append("\n");
    sb.append("    annoPratica: ").append(toIndentedString(annoPratica)).append("\n");
    sb.append("    numeroProtocollo: ").append(toIndentedString(numeroProtocollo)).append("\n");
    sb.append("    dataProtocollo: ").append(toIndentedString(dataProtocollo)).append("\n");
    sb.append("    dataCambioStato: ").append(toIndentedString(dataCambioStato)).append("\n");
    sb.append("    documenti: ").append(toIndentedString(documenti)).append("\n");
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

