/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_loc_ubicazione")
public class MudeopenRLocUbicazione extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_ubicazione")
    private Long idUbicazione;

    @Column(name = "id_georiferimento")
    private Long idGeoriferimento;

    @Column(name = "id_fabbricato")
    private Long idFabbricato;

    @Column(name = "id_cellula")
    private Long idCellula;

    @Column(name = "id_istanza")
    private Long idIstanza;

    @Column(name = "sedime")
    private String sedime;

    @Column(name = "desc_via")
    private String descVia;

    @Column(name = "num_civico")
    private String numCivico;

    @Column(name = "bis")
    private String bis;

    @Column(name = "bisinterno")
    private String bisinterno;

    @Column(name = "interno")
    private String interno;

    @Column(name = "interno2")
    private String interno2;

    @Column(name = "scala")
    private String scala;

    @Column(name = "secondario")
    private String secondario;

    @Column(name = "cap")
    private String cap;

    @Column(name = "f1_personalizzato")
    private String f1Personalizzato;

    @Column(name = "f1_personalizzato_dettaglio")
    private String f1PersonalizzatoDettaglio;

    @Column(name = "piano")
    private String piano;

    @Column(name = "id_civico_toponom")
    private String idCivicoToponom;

    @Column(name = "id_via_toponom")
    private String idViaToponom;

    @Column(name = "desc_fonte_ubicazione")
    private String descFonteUbicazione;

    public Long getIdUbicazione() {
        return idUbicazione;
    }

    public void setIdUbicazione(Long idUbicazione) {
        this.idUbicazione = idUbicazione;
    }

    public Long getIdGeoriferimento() {
        return idGeoriferimento;
    }

    public void setIdGeoriferimento(Long idGeoriferimento) {
        this.idGeoriferimento = idGeoriferimento;
    }

    public Long getIdFabbricato() {
        return idFabbricato;
    }

    public void setIdFabbricato(Long idFabbricato) {
        this.idFabbricato = idFabbricato;
    }

    public Long getIdCellula() {
        return idCellula;
    }

    public void setIdCellula(Long idCellula) {
        this.idCellula = idCellula;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getSedime() {
        return sedime;
    }

    public void setSedime(String sedime) {
        this.sedime = sedime;
    }

    public String getDescVia() {
        return descVia;
    }

    public void setDescVia(String descVia) {
        this.descVia = descVia;
    }

    public String getNumCivico() {
        return numCivico;
    }

    public void setNumCivico(String numCivico) {
        this.numCivico = numCivico;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public String getBisinterno() {
        return bisinterno;
    }

    public void setBisinterno(String bisinterno) {
        this.bisinterno = bisinterno;
    }

    public String getInterno() {
        return interno;
    }

    public void setInterno(String interno) {
        this.interno = interno;
    }

    public String getInterno2() {
        return interno2;
    }

    public void setInterno2(String interno2) {
        this.interno2 = interno2;
    }

    public String getScala() {
        return scala;
    }

    public void setScala(String scala) {
        this.scala = scala;
    }

    public String getSecondario() {
        return secondario;
    }

    public void setSecondario(String secondario) {
        this.secondario = secondario;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getF1Personalizzato() {
        return f1Personalizzato;
    }

    public void setF1Personalizzato(String f1Personalizzato) {
        this.f1Personalizzato = f1Personalizzato;
    }

    public String getF1PersonalizzatoDettaglio() {
        return f1PersonalizzatoDettaglio;
    }

    public void setF1PersonalizzatoDettaglio(String f1PersonalizzatoDettaglio) {
        this.f1PersonalizzatoDettaglio = f1PersonalizzatoDettaglio;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public String getIdCivicoToponom() {
        return idCivicoToponom;
    }

    public void setIdCivicoToponom(String idCivicoToponom) {
        this.idCivicoToponom = idCivicoToponom;
    }

    public String getIdViaToponom() {
        return idViaToponom;
    }

    public void setIdViaToponom(String idViaToponom) {
        this.idViaToponom = idViaToponom;
    }

    public String getDescFonteUbicazione() {
        return descFonteUbicazione;
    }

    public void setDescFonteUbicazione(String descFonteUbicazione) {
        this.descFonteUbicazione = descFonteUbicazione;
    }
}
