/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_loc_cellula")
public class MudeopenRLocCellula extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_cellula")
    private Long idCellula;

    @Column(name="id_georiferimento")
    private Long idGeoriferimento;

    @Column(name="chiave_carotaggio")
    private String chiaveCarotaggio;

    @Column(name="cod_cellula")
    private String codCellula;

    public Long getIdCellula() {
        return idCellula;
    }

    public void setIdCellula(Long idCellula) {
        this.idCellula = idCellula;
    }

    public Long getIdGeoriferimento() {
        return idGeoriferimento;
    }

    public void setIdGeoriferimento(Long idGeoriferimento) {
        this.idGeoriferimento = idGeoriferimento;
    }

    public String getChiaveCarotaggio() {
        return chiaveCarotaggio;
    }

    public void setChiaveCarotaggio(String chiaveCarotaggio) {
        this.chiaveCarotaggio = chiaveCarotaggio;
    }

    public String getCodCellula() {
        return codCellula;
    }

    public void setCodCellula(String codCellula) {
        this.codCellula = codCellula;
    }
}
