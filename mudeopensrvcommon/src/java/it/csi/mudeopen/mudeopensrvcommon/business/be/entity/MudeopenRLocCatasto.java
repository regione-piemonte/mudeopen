/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_loc_catasto")
public class MudeopenRLocCatasto extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_catasto")
    private Long idCatasto;

    @Column(name="id_georiferimento")
    private Long idGeoriferimento;

    @Column(name="id_fabbricato")
    private Long idFabbricato;

    @Column(name="id_cellula")
    private Long idCellula;

    @Column(name="id_istanza")
    private Long idIstanza;

    @Column(name="sezione")
    private String sezione;

    @Column(name="sezione_urbana")
    private String sezioneUrbana;

    @Column(name="foglio")
    private String foglio;

    @Column(name="particella")
    private String particella;

    @Column(name="subalterno")
    private String subalterno;

    @Column(name="f1_tipo_catasto")
    private String f1TipoCatasto;

    @Column(name="f1_personalizzato")
    private String f1Personalizzato;

    @Column(name="f1_personalizzato_dettaglio")
    private String f1PersonalizzatoDettaglio;

    @Column(name="desc_fonte_catasto")
    private String descFonteCatasto;

    @Column(name="chiave_carotaggio")
    private String chiaveCarotaggio;

    public Long getIdCatasto() {
        return idCatasto;
    }

    public void setIdCatasto(Long idCatasto) {
        this.idCatasto = idCatasto;
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

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getSezioneUrbana() {
        return sezioneUrbana;
    }

    public void setSezioneUrbana(String sezioneUrbana) {
        this.sezioneUrbana = sezioneUrbana;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getParticella() {
        return particella;
    }

    public void setParticella(String particella) {
        this.particella = particella;
    }

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

    public String getF1TipoCatasto() {
        return f1TipoCatasto;
    }

    public void setF1TipoCatasto(String f1TipoCatasto) {
        this.f1TipoCatasto = f1TipoCatasto;
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

    public String getDescFonteCatasto() {
        return descFonteCatasto;
    }

    public void setDescFonteCatasto(String descFonteCatasto) {
        this.descFonteCatasto = descFonteCatasto;
    }

    public String getChiaveCarotaggio() {
        return chiaveCarotaggio;
    }

    public void setChiaveCarotaggio(String chiaveCarotaggio) {
        this.chiaveCarotaggio = chiaveCarotaggio;
    }
}

