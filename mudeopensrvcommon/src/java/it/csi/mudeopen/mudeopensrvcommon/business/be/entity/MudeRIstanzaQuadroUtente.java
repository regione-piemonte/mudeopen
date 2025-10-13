/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mudeopen_r_istanza_quadro_utente")
public class MudeRIstanzaQuadroUtente extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1713102371586177319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_istanza_quadro_utente")
    private Long id;

    @Column(name = "id_istanza")
    private Long idIstanza;

    @Column(name = "id_quadro")
    private Long idQuadro;

    @Column(name = "id_utente")
    private Long idUtente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Long getIdQuadro() {
        return idQuadro;
    }

    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

}