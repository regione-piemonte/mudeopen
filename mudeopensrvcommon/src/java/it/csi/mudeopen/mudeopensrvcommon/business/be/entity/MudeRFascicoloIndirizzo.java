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
import java.util.Date;

@Entity
@Table(name = "mudeopen_r_fascicolo_indirizzo")
public class MudeRFascicoloIndirizzo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fascicolo_indirizzo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_fascicolo")
    private MudeTFascicolo mudeTFascicolo;

    @ManyToOne
    @JoinColumn(name = "id_indirizzo")
    private MudeTIndirizzo indirizzo;

    @Column(name = "id_istanza_rif")
    private Long idIstanza;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeTFascicolo getMudeTFascicolo() {
        return mudeTFascicolo;
    }

    public void setMudeTFascicolo(MudeTFascicolo mudeTFascicolo) {
        this.mudeTFascicolo = mudeTFascicolo;
    }

    public MudeTIndirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(MudeTIndirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

	public Long getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

}