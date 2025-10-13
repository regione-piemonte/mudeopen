/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_r_civici_particelleurbane")
public class MudeopenRCiviciParticelleurbane extends BaseEntity {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="fk_civico")
    private String fkCivico;

    @Column(name="fk_cellula")
    private String fkCellula;

    @Column(name="id_civico")
    private Long idCivico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkCivico() {
        return fkCivico;
    }

    public void setFkCivico(String fkCivico) {
        this.fkCivico = fkCivico;
    }

    public String getFkCellula() {
        return fkCellula;
    }

    public void setFkCellula(String fkCellula) {
        this.fkCellula = fkCellula;
    }

    public Long getIdCivico() {
        return idCivico;
    }

    public void setIdCivico(Long idCivico) {
        this.idCivico = idCivico;
    }
}