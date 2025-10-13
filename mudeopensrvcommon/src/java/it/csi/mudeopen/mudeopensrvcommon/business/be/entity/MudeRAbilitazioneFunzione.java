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
@Table(name = "mudeopen_r_abilitazione_funzione")
public class MudeRAbilitazioneFunzione extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2835835837168005813L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abilitazione_funzione")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_abilitazione")
    private MudeDAbilitazione abilitazione;

    @ManyToOne
    @JoinColumn(name = "id_funzione")
    private MudeDFunzione funzione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeDAbilitazione getAbilitazione() {
        return abilitazione;
    }

    public void setAbilitazione(MudeDAbilitazione abilitazione) {
        this.abilitazione = abilitazione;
    }

    public MudeDFunzione getFunzione() {
        return funzione;
    }

    public void setFunzione(MudeDFunzione funzione) {
        this.funzione = funzione;
    }

}