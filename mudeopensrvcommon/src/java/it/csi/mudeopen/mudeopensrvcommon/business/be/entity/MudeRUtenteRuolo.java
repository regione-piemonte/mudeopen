/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mudeopen_r_utente_ruolo")
public class MudeRUtenteRuolo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5378218599773827049L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_utente_ruolo")
    private Long id;

    @Column(name = "id_utente")
    private long utente;

    @Column(name = "codice_ruolo_utente")
    private String ruoloUtente;

/*	
 *  @ManyToOne
    @JoinColumn(name = "codice")
    private MudeDRuoloUtente ruoloUtente;
*
*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public long getUtente() {
		return utente;
	}

	public void setUtente(long utente) {
		this.utente = utente;
	}

	public String getRuoloUtente() {
		return ruoloUtente;
	}

	public void setRuoloUtente(String ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}

}