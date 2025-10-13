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
@Table(name = "mudeopen_r_istanza_stato")
public class MudeRIstanzaStato extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2323964779738157989L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_istanza_stato")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_istanza")
    private MudeTIstanza istanza;

    @ManyToOne
    @JoinColumn(name="codice_stato_istanza")
    private MudeDStatoIstanza statoIstanza;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeTIstanza getIstanza() {
        return istanza;
    }

    public void setIstanza(MudeTIstanza istanza) {
        this.istanza = istanza;
    }

    public MudeDStatoIstanza getStatoIstanza() {
        return statoIstanza;
    }

    public void setStatoIstanza(MudeDStatoIstanza statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

}