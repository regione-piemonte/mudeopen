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
@Table(name = "mudeopen_fabbricatiparticelle")
public class MudeopenFabbricatiparticelle {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="fk_edificio")
    private String fkEdificio;

    @Column(name="fk_cellula")
    private String fkCellula;

    @Column(name="parent_rec_key")
    private String parentRecKey;

    @Column(name="pk_fabbricati_particelle")
    private Long pkFabbricatiParticelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkEdificio() {
        return fkEdificio;
    }

    public void setFkEdificio(String fkEdificio) {
        this.fkEdificio = fkEdificio;
    }

    public String getFkCellula() {
        return fkCellula;
    }

    public void setFkCellula(String fkCellula) {
        this.fkCellula = fkCellula;
    }

    public String getParentRecKey() {
        return parentRecKey;
    }

    public void setParentRecKey(String parentRecKey) {
        this.parentRecKey = parentRecKey;
    }

    public Long getPkFabbricatiParticelle() {
        return pkFabbricatiParticelle;
    }

    public void setPkFabbricatiParticelle(Long pkFabbricatiParticelle) {
        this.pkFabbricatiParticelle = pkFabbricatiParticelle;
    }
}