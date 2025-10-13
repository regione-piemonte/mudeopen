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
@Table(name = "mudeopen_r_fascicolo_utente")
public class MudeRFascicoloUtente extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5378218599773827049L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fascicolo_utente")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_fascicolo")
    private MudeTFascicolo fascicolo;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private MudeTUser utente;

    @ManyToOne
    @JoinColumn(name = "id_abilitazione")
    private MudeDAbilitazione abilitazione;

    @ManyToOne
    @JoinColumn(name = "id_abilitatore")
    private MudeTUser abilitatore;

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

    public MudeTUser getUtente() {
        return utente;
    }

    public void setUtente(MudeTUser utente) {
        this.utente = utente;
    }

    public MudeDAbilitazione getAbilitazione() {
        return abilitazione;
    }

    public void setAbilitazione(MudeDAbilitazione abilitazione) {
        this.abilitazione = abilitazione;
    }

    public MudeTUser getAbilitatore() {
        return abilitatore;
    }

    public void setAbilitatore(MudeTUser abilitatore) {
        this.abilitatore = abilitatore;
    }

}