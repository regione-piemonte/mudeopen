/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_loc_fabbricato")
public class MudeopenRLocFabbricato extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_fabbricato")
    private Long idFabbricato;

    @Column(name="id_cellula")
    private Long idCellula;

    @Column(name="chiave_carotaggio")
    private String chiaveCarotaggio;

    @Column(name="cod_fabbricato")
    private String codFabbricato;

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

    public String getChiaveCarotaggio() {
        return chiaveCarotaggio;
    }

    public void setChiaveCarotaggio(String chiaveCarotaggio) {
        this.chiaveCarotaggio = chiaveCarotaggio;
    }

    public String getCodFabbricato() {
        return codFabbricato;
    }

    public void setCodFabbricato(String codFabbricato) {
        this.codFabbricato = codFabbricato;
    }
}