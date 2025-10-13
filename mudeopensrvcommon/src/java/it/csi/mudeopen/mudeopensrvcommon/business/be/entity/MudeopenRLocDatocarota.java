/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_loc_datocarota")
public class MudeopenRLocDatocarota extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_geo_datocarota")
    private Long idGeoDatocarota;

    @Column(name="id_georiferimento")
    private Long idGeoriferimento;

    @Column(name="tipo_carotaggio")
    private String tipoCarotaggio;

    @Column(name="id_fonte")
    private String idFonte;

    @Column(name="desc_fonte")
    private String descFonte;

    @Column(name="id_livello")
    private String idLivello;

    @Column(name="desc_livello")
    private String descLivello;

    @Column(name="valore")
    private String valore;

    public Long getIdGeoDatocarota() {
        return idGeoDatocarota;
    }

    public void setIdGeoDatocarota(Long idGeoDatocarota) {
        this.idGeoDatocarota = idGeoDatocarota;
    }

    public Long getIdGeoriferimento() {
        return idGeoriferimento;
    }

    public void setIdGeoriferimento(Long idGeoriferimento) {
        this.idGeoriferimento = idGeoriferimento;
    }

    public String getTipoCarotaggio() {
        return tipoCarotaggio;
    }

    public void setTipoCarotaggio(String tipoCarotaggio) {
        this.tipoCarotaggio = tipoCarotaggio;
    }

    public String getIdFonte() {
        return idFonte;
    }

    public void setIdFonte(String idFonte) {
        this.idFonte = idFonte;
    }

    public String getDescFonte() {
        return descFonte;
    }

    public void setDescFonte(String descFonte) {
        this.descFonte = descFonte;
    }

    public String getIdLivello() {
        return idLivello;
    }

    public void setIdLivello(String idLivello) {
        this.idLivello = idLivello;
    }

    public String getDescLivello() {
        return descLivello;
    }

    public void setDescLivello(String descLivello) {
        this.descLivello = descLivello;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }
}