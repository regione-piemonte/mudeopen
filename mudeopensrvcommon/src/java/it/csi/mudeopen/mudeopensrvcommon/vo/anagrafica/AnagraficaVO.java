/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

public class AnagraficaVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -8190033541110139148L;

    //ex pg
    private String ragioneSociale = null;
    private String nazionalita = null;
    private String partitaIva = null;
    private String partitaIvaComunitaria = null;

    private String codiceFiscale = null;
    private SelectVO statoMembro = null;
    private String nomeLegaleRappresentante = null;
    private String cognomeLegaleRappresentante = null;
    private String codiceFiscaleLegaleRappresentante = null;
    private String tipoAttivita = null;

    //ex pf
    private String nome = null;
    private String cognome = null;
    //	private String codiceFiscale = null;

    private String sesso = null;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate dataNascita = null;
    private NazioneVO statoNascita = null;
    private ProvinciaVO provinciaNascita = null;
    private ComuneVO comuneNascita = null;
    private String telefono = null;
    private String cellulare = null;
    private String mail = null;
    private String pec = null;
    private String statoEstero = null;

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIvaComunitaria() {
        return partitaIvaComunitaria;
    }

    public void setPartitaIvaComunitaria(String partitaIvaComunitaria) {
        this.partitaIvaComunitaria = partitaIvaComunitaria;
    }

    public SelectVO getStatoMembro() {
        return statoMembro;
    }

    public void setStatoMembro(SelectVO statoMembro) {
        this.statoMembro = statoMembro;
    }

    public String getNomeLegaleRappresentante() {
        return nomeLegaleRappresentante;
    }

    public void setNomeLegaleRappresentante(String nomeLegaleRappresentante) {
        this.nomeLegaleRappresentante = nomeLegaleRappresentante;
    }

    public String getCognomeLegaleRappresentante() {
        return cognomeLegaleRappresentante;
    }

    public void setCognomeLegaleRappresentante(String cognomeLegaleRappresentante) {
        this.cognomeLegaleRappresentante = cognomeLegaleRappresentante;
    }

    public String getCodiceFiscaleLegaleRappresentante() {
        return codiceFiscaleLegaleRappresentante;
    }

    public void setCodiceFiscaleLegaleRappresentante(String codiceFiscaleLegaleRappresentante) {
        this.codiceFiscaleLegaleRappresentante = codiceFiscaleLegaleRappresentante;
    }

    public String getTipoAttivita() {
        return tipoAttivita;
    }

    public void setTipoAttivita(String tipoAttivita) {
        this.tipoAttivita = tipoAttivita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public NazioneVO getStatoNascita() {
        return statoNascita;
    }

    public void setStatoNascita(NazioneVO statoNascita) {
        this.statoNascita = statoNascita;
    }

    public ProvinciaVO getProvinciaNascita() {
        return provinciaNascita;
    }

    public void setProvinciaNascita(ProvinciaVO provinciaNascita) {
        this.provinciaNascita = provinciaNascita;
    }

    public ComuneVO getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(ComuneVO comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getStatoEstero() {
        return statoEstero;
    }

    public void setStatoEstero(String statoEstero) {
        this.statoEstero = statoEstero;
    }
}