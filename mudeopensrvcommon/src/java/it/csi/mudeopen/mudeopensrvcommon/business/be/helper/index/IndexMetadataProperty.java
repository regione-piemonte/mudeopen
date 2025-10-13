/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index;

import java.util.Objects;

public class IndexMetadataProperty {

    private String idIstanza;

    private String codiceIstanza;

    private String codicePratica;

    private String dimensioneByte;

    private String codiceAllegato;

    private String codiceTipoAdempimento;

    private String descrizioneTipoAdempimento;

    private String codiceAdempimento;

    private String descrizioneAdempimento;

    private String codiceAutoritaCompetente;

    private String codiceMultipleAutoritaCompetenti;

    private String codiceCategoriaAllegato;

    private String descrizioneCategoriaAllegato;

    private String codiceTipologiaAllegato;

    private String descrizioneTipologiaAllegato;

    private String noteAllegato;

    private String documentoObbligatorio;

    private String codiceIntegrazione;

    private String descrizioneIntegrazione;

    // Aspect properties
    private String firmato;

    private String tipoFirma;

    private String errorCode;

    private String cancellato;

    private String dataCancellazione;

    private String riservato;

    public String getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(String idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    public void setCodiceIstanza(String codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    public String getCodicePratica() {
        return codicePratica;
    }

    public void setCodicePratica(String codicePratica) {
        this.codicePratica = codicePratica;
    }

    public String getDimensioneByte() {
        return dimensioneByte;
    }

    public void setDimensioneByte(String dimensioneByte) {
        this.dimensioneByte = dimensioneByte;
    }

    public String getCodiceAllegato() {
        return codiceAllegato;
    }

    public void setCodiceAllegato(String codiceAllegato) {
        this.codiceAllegato = codiceAllegato;
    }

    public String getCodiceTipoAdempimento() {
        return codiceTipoAdempimento;
    }

    public void setCodiceTipoAdempimento(String codiceTipoAdempimento) {
        this.codiceTipoAdempimento = codiceTipoAdempimento;
    }

    public String getDescrizioneTipoAdempimento() {
        return descrizioneTipoAdempimento;
    }

    public void setDescrizioneTipoAdempimento(String descrizioneTipoAdempimento) {
        this.descrizioneTipoAdempimento = descrizioneTipoAdempimento;
    }

    public String getCodiceAdempimento() {
        return codiceAdempimento;
    }

    public void setCodiceAdempimento(String codiceAdempimento) {
        this.codiceAdempimento = codiceAdempimento;
    }

    public String getDescrizioneAdempimento() {
        return descrizioneAdempimento;
    }

    public void setDescrizioneAdempimento(String descrizioneAdempimento) {
        this.descrizioneAdempimento = descrizioneAdempimento;
    }

    public String getCodiceAutoritaCompetente() {
        return codiceAutoritaCompetente;
    }

    public void setCodiceAutoritaCompetente(String codiceAutoritaCompetente) {
        this.codiceAutoritaCompetente = codiceAutoritaCompetente;
    }

    public String getCodiceMultipleAutoritaCompetenti() {
        return codiceMultipleAutoritaCompetenti;
    }

    public void setCodiceMultipleAutoritaCompetenti(String codiceMultipleAutoritaCompetenti) {
        this.codiceMultipleAutoritaCompetenti = codiceMultipleAutoritaCompetenti;
    }

    public String getCodiceCategoriaAllegato() {
        return codiceCategoriaAllegato;
    }

    public void setCodiceCategoriaAllegato(String codiceCategoriaAllegato) {
        this.codiceCategoriaAllegato = codiceCategoriaAllegato;
    }

    public String getDescrizioneCategoriaAllegato() {
        return descrizioneCategoriaAllegato;
    }

    public void setDescrizioneCategoriaAllegato(String descrizioneCategoriaAllegato) {
        this.descrizioneCategoriaAllegato = descrizioneCategoriaAllegato;
    }

    public String getCodiceTipologiaAllegato() {
        return codiceTipologiaAllegato;
    }

    public void setCodiceTipologiaAllegato(String codiceTipologiaAllegato) {
        this.codiceTipologiaAllegato = codiceTipologiaAllegato;
    }

    public String getDescrizioneTipologiaAllegato() {
        return descrizioneTipologiaAllegato;
    }

    public void setDescrizioneTipologiaAllegato(String descrizioneTipologiaAllegato) {
        this.descrizioneTipologiaAllegato = descrizioneTipologiaAllegato;
    }

    public String getNoteAllegato() {
        return noteAllegato;
    }

    public void setNoteAllegato(String noteAllegato) {
        this.noteAllegato = noteAllegato;
    }

    public String getDocumentoObbligatorio() {
        return documentoObbligatorio;
    }

    public void setDocumentoObbligatorio(String documentoObbligatorio) {
        this.documentoObbligatorio = documentoObbligatorio;
    }

    public String getCodiceIntegrazione() {
        return codiceIntegrazione;
    }

    public void setCodiceIntegrazione(String codiceIntegrazione) {
        this.codiceIntegrazione = codiceIntegrazione;
    }

    public String getDescrizioneIntegrazione() {
        return descrizioneIntegrazione;
    }

    public void setDescrizioneIntegrazione(String descrizioneIntegrazione) {
        this.descrizioneIntegrazione = descrizioneIntegrazione;
    }

    public String getFirmato() {
        return firmato;
    }

    public void setFirmato(String firmato) {
        this.firmato = firmato;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCancellato() {
        return cancellato;
    }

    public void setCancellato(String cancellato) {
        this.cancellato = cancellato;
    }

    public String getDataCancellazione() {
        return dataCancellazione;
    }

    public void setDataCancellazione(String dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }

    public String getRiservato() {
        return riservato;
    }

    public void setRiservato(String riservato) {
        this.riservato = riservato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexMetadataProperty that = (IndexMetadataProperty) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(codiceIstanza, that.codiceIstanza) && Objects.equals(codicePratica, that.codicePratica) && Objects.equals(dimensioneByte, that.dimensioneByte) && Objects.equals(codiceAllegato, that.codiceAllegato) && Objects.equals(codiceTipoAdempimento, that.codiceTipoAdempimento) && Objects.equals(descrizioneTipoAdempimento, that.descrizioneTipoAdempimento) && Objects.equals(codiceAdempimento, that.codiceAdempimento) && Objects.equals(descrizioneAdempimento, that.descrizioneAdempimento) && Objects.equals(codiceAutoritaCompetente, that.codiceAutoritaCompetente) && Objects.equals(codiceMultipleAutoritaCompetenti, that.codiceMultipleAutoritaCompetenti) && Objects.equals(codiceCategoriaAllegato, that.codiceCategoriaAllegato) && Objects.equals(descrizioneCategoriaAllegato, that.descrizioneCategoriaAllegato) && Objects.equals(codiceTipologiaAllegato, that.codiceTipologiaAllegato) && Objects.equals(descrizioneTipologiaAllegato, that.descrizioneTipologiaAllegato) && Objects.equals(noteAllegato, that.noteAllegato) && Objects.equals(documentoObbligatorio, that.documentoObbligatorio) && Objects.equals(codiceIntegrazione, that.codiceIntegrazione) && Objects.equals(descrizioneIntegrazione, that.descrizioneIntegrazione) && Objects.equals(firmato, that.firmato) && Objects.equals(tipoFirma, that.tipoFirma) && Objects.equals(errorCode, that.errorCode) && Objects.equals(cancellato, that.cancellato) && Objects.equals(dataCancellazione, that.dataCancellazione) && Objects.equals(riservato, that.riservato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, codiceIstanza, codicePratica, dimensioneByte, codiceAllegato, codiceTipoAdempimento, descrizioneTipoAdempimento, codiceAdempimento, descrizioneAdempimento, codiceAutoritaCompetente, codiceMultipleAutoritaCompetenti, codiceCategoriaAllegato, descrizioneCategoriaAllegato, codiceTipologiaAllegato, descrizioneTipologiaAllegato, noteAllegato, documentoObbligatorio, codiceIntegrazione, descrizioneIntegrazione, firmato, tipoFirma, errorCode, cancellato, dataCancellazione, riservato);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IndexMetadataProperty {");
        sb.append("         idIstanza:'").append(idIstanza).append("'");
        sb.append(",         codiceIstanza:'").append(codiceIstanza).append("'");
        sb.append(",         codicePratica:'").append(codicePratica).append("'");
        sb.append(",         dimensioneByte:'").append(dimensioneByte).append("'");
        sb.append(",         codiceAllegato:'").append(codiceAllegato).append("'");
        sb.append(",         codiceTipoAdempimento:'").append(codiceTipoAdempimento).append("'");
        sb.append(",         descrizioneTipoAdempimento:'").append(descrizioneTipoAdempimento).append("'");
        sb.append(",         codiceAdempimento:'").append(codiceAdempimento).append("'");
        sb.append(",         descrizioneAdempimento:'").append(descrizioneAdempimento).append("'");
        sb.append(",         codiceAutoritaCompetente:'").append(codiceAutoritaCompetente).append("'");
        sb.append(",         codiceMultipleAutoritaCompetenti:'").append(codiceMultipleAutoritaCompetenti).append("'");
        sb.append(",         codiceCategoriaAllegato:'").append(codiceCategoriaAllegato).append("'");
        sb.append(",         descrizioneCategoriaAllegato:'").append(descrizioneCategoriaAllegato).append("'");
        sb.append(",         codiceTipologiaAllegato:'").append(codiceTipologiaAllegato).append("'");
        sb.append(",         descrizioneTipologiaAllegato:'").append(descrizioneTipologiaAllegato).append("'");
        sb.append(",         noteAllegato:'").append(noteAllegato).append("'");
        sb.append(",         documentoObbligatorio:'").append(documentoObbligatorio).append("'");
        sb.append(",         codiceIntegrazione:'").append(codiceIntegrazione).append("'");
        sb.append(",         descrizioneIntegrazione:'").append(descrizioneIntegrazione).append("'");
        sb.append(",         firmato:'").append(firmato).append("'");
        sb.append(",         tipoFirma:'").append(tipoFirma).append("'");
        sb.append(",         errorCode:'").append(errorCode).append("'");
        sb.append(",         cancellato:'").append(cancellato).append("'");
        sb.append(",         dataCancellazione:'").append(dataCancellazione).append("'");
        sb.append(",         riservato:'").append(riservato).append("'");
        sb.append("}");
        return sb.toString();
    }
}