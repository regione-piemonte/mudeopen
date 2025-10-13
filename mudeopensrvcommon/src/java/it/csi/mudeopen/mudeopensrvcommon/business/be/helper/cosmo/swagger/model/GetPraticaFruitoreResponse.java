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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AttivitaFruitore1;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.DocumentoFruitore1;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.MessaggioFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.TipoPraticaFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.Utente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Tutti i contenuti della pratica aggiornati alla data odierna")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class GetPraticaFruitoreResponse   {

  private String id = null;
  private String codiceIpaEnte = null;
  private TipoPraticaFruitore tipo = null;
  private String oggetto = null;
  private TipoPraticaFruitore stato = null;
  private String riassunto = null;
  private Utente utenteCreazione = null;
  private Date dataCreazione = null;
  private Date dataFine = null;
  private Date dataCambioStato = null;
  private Date dataAggiornamento = null;
  private Object metadati = null;
  private List<AttivitaFruitore1> attivita = new ArrayList<AttivitaFruitore1>();
  private List<MessaggioFruitore> commenti = new ArrayList<MessaggioFruitore>();
  private List<DocumentoFruitore1> documenti = new ArrayList<DocumentoFruitore1>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("id")
  @NotNull
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("codiceIpaEnte")
  public String getCodiceIpaEnte() {
    return codiceIpaEnte;
  }
  public void setCodiceIpaEnte(String codiceIpaEnte) {
    this.codiceIpaEnte = codiceIpaEnte;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("tipo")
  public TipoPraticaFruitore getTipo() {
    return tipo;
  }
  public void setTipo(TipoPraticaFruitore tipo) {
    this.tipo = tipo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("oggetto")
  public String getOggetto() {
    return oggetto;
  }
  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("stato")
  public TipoPraticaFruitore getStato() {
    return stato;
  }
  public void setStato(TipoPraticaFruitore stato) {
    this.stato = stato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("riassunto")
  public String getRiassunto() {
    return riassunto;
  }
  public void setRiassunto(String riassunto) {
    this.riassunto = riassunto;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("utenteCreazione")
  public Utente getUtenteCreazione() {
    return utenteCreazione;
  }
  public void setUtenteCreazione(Utente utenteCreazione) {
    this.utenteCreazione = utenteCreazione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataCreazione")
  public Date getDataCreazione() {
    return dataCreazione;
  }
  public void setDataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataFine")
  public Date getDataFine() {
    return dataFine;
  }
  public void setDataFine(Date dataFine) {
    this.dataFine = dataFine;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataCambioStato")
  public Date getDataCambioStato() {
    return dataCambioStato;
  }
  public void setDataCambioStato(Date dataCambioStato) {
    this.dataCambioStato = dataCambioStato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataAggiornamento")
  public Date getDataAggiornamento() {
    return dataAggiornamento;
  }
  public void setDataAggiornamento(Date dataAggiornamento) {
    this.dataAggiornamento = dataAggiornamento;
  }

  /**
   * metadati della pratica in formato libero
   **/

  @ApiModelProperty(value = "metadati della pratica in formato libero")
  @JsonProperty("metadati")
  public Object getMetadati() {
    return metadati;
  }
  public void setMetadati(Object metadati) {
    this.metadati = metadati;
  }

  /**
   * array di tutte le attività della pratica in ordine cronologico, le attività concluse hanno dataFine not null
   **/

  @ApiModelProperty(value = "array di tutte le attività della pratica in ordine cronologico, le attività concluse hanno dataFine not null")
  @JsonProperty("attivita")
  public List<AttivitaFruitore1> getAttivita() {
    return attivita;
  }
  public void setAttivita(List<AttivitaFruitore1> attivita) {
    this.attivita = attivita;
  }

  /**
   * array in ordine cronologico dei commenti della pratica
   **/

  @ApiModelProperty(value = "array in ordine cronologico dei commenti della pratica")
  @JsonProperty("commenti")
  public List<MessaggioFruitore> getCommenti() {
    return commenti;
  }
  public void setCommenti(List<MessaggioFruitore> commenti) {
    this.commenti = commenti;
  }

  /**
   * array dei documenti della pratica
   **/

  @ApiModelProperty(value = "array dei documenti della pratica")
  @JsonProperty("documenti")
  public List<DocumentoFruitore1> getDocumenti() {
    return documenti;
  }
  public void setDocumenti(List<DocumentoFruitore1> documenti) {
    this.documenti = documenti;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetPraticaFruitoreResponse getPraticaFruitoreResponse = (GetPraticaFruitoreResponse) o;
    return Objects.equals(id, getPraticaFruitoreResponse.id) &&
        Objects.equals(codiceIpaEnte, getPraticaFruitoreResponse.codiceIpaEnte) &&
        Objects.equals(tipo, getPraticaFruitoreResponse.tipo) &&
        Objects.equals(oggetto, getPraticaFruitoreResponse.oggetto) &&
        Objects.equals(stato, getPraticaFruitoreResponse.stato) &&
        Objects.equals(riassunto, getPraticaFruitoreResponse.riassunto) &&
        Objects.equals(utenteCreazione, getPraticaFruitoreResponse.utenteCreazione) &&
        Objects.equals(dataCreazione, getPraticaFruitoreResponse.dataCreazione) &&
        Objects.equals(dataFine, getPraticaFruitoreResponse.dataFine) &&
        Objects.equals(dataCambioStato, getPraticaFruitoreResponse.dataCambioStato) &&
        Objects.equals(dataAggiornamento, getPraticaFruitoreResponse.dataAggiornamento) &&
        Objects.equals(metadati, getPraticaFruitoreResponse.metadati) &&
        Objects.equals(attivita, getPraticaFruitoreResponse.attivita) &&
        Objects.equals(commenti, getPraticaFruitoreResponse.commenti) &&
        Objects.equals(documenti, getPraticaFruitoreResponse.documenti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codiceIpaEnte, tipo, oggetto, stato, riassunto, utenteCreazione, dataCreazione, dataFine, dataCambioStato, dataAggiornamento, metadati, attivita, commenti, documenti);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetPraticaFruitoreResponse {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    oggetto: ").append(toIndentedString(oggetto)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    riassunto: ").append(toIndentedString(riassunto)).append("\n");
    sb.append("    utenteCreazione: ").append(toIndentedString(utenteCreazione)).append("\n");
    sb.append("    dataCreazione: ").append(toIndentedString(dataCreazione)).append("\n");
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
    sb.append("    dataCambioStato: ").append(toIndentedString(dataCambioStato)).append("\n");
    sb.append("    dataAggiornamento: ").append(toIndentedString(dataAggiornamento)).append("\n");
    sb.append("    metadati: ").append(toIndentedString(metadati)).append("\n");
    sb.append("    attivita: ").append(toIndentedString(attivita)).append("\n");
    sb.append("    commenti: ").append(toIndentedString(commenti)).append("\n");
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

