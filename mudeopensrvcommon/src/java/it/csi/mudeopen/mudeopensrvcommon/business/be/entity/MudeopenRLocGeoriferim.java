/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mudeopen_r_loc_georiferim")
public class MudeopenRLocGeoriferim extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_georiferimento")
    private Long idGeoriferimento;

    @Column(name = "id_istanza")
    private Long idIstanza;

    @Column(name="id_livello_poligono")
    private Long idLivelloPoligono;

    @Column(name="desc_livello_poligono")
    private String descLivelloPoligono;

    @Column(name="servizio_fonte")
    private String servizioFonte;

    @Column(name="servizio_livello")
    private String servizioLivello;

    @Column(name="geometria_sdo")
    private String geometriaSdo;

    @Column(name="data_georiferimento")
    private Date dataGeoriferimento;

    @Column(name="cod_istat_comune")
    private String istatComune;

    public Long getIdGeoriferimento() {
        return idGeoriferimento;
    }

    public void setIdGeoriferimento(Long idGeoriferimento) {
        this.idGeoriferimento = idGeoriferimento;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Long getIdLivelloPoligono() {
        return idLivelloPoligono;
    }

    public void setIdLivelloPoligono(Long idLivelloPoligono) {
        this.idLivelloPoligono = idLivelloPoligono;
    }

    public String getDescLivelloPoligono() {
        return descLivelloPoligono;
    }

    public void setDescLivelloPoligono(String descLivelloPoligono) {
        this.descLivelloPoligono = descLivelloPoligono;
    }

    public String getServizioFonte() {
        return servizioFonte;
    }

    public void setServizioFonte(String servizioFonte) {
        this.servizioFonte = servizioFonte;
    }

    public String getServizioLivello() {
        return servizioLivello;
    }

    public void setServizioLivello(String servizioLivello) {
        this.servizioLivello = servizioLivello;
    }

    public String getGeometriaSdo() {
        return geometriaSdo;
    }

    public void setGeometriaSdo(String geometriaSdo) {
        this.geometriaSdo = geometriaSdo;
    }

    public Date getDataGeoriferimento() {
        return dataGeoriferimento;
    }

    public void setDataGeoriferimento(Date dataGeoriferimento) {
        this.dataGeoriferimento = dataGeoriferimento;
    }

    public String getIstatComune() {
        return istatComune;
    }

    public void setIstatComune(String istatComune) {
        this.istatComune = istatComune;
    }
}