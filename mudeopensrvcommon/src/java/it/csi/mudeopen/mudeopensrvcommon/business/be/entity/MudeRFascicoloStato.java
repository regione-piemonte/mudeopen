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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mudeopen_r_fascicolo_stato")
public class MudeRFascicoloStato extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7390169748903264629L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fascicolo_stato")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_fascicolo")
    private MudeTFascicolo fascicolo;

    @ManyToOne
    @JoinColumn(name="codice_stato_fascicolo")
    private MudeDStatoFascicolo statoFascicolo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeTFascicolo getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(MudeTFascicolo fascicolo) {
        this.fascicolo = fascicolo;
    }

    public MudeDStatoFascicolo getStatoFascicolo() {
        return statoFascicolo;
    }

    public void setStatoFascicolo(MudeDStatoFascicolo statoFascicolo) {
        this.statoFascicolo = statoFascicolo;
    }

}