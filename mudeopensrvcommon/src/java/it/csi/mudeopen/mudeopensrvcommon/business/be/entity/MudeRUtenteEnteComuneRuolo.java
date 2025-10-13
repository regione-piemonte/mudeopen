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
@Table(name = "mudeopen_r_utente_ente_comune_ruolo")
public class MudeRUtenteEnteComuneRuolo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5378218599773827049L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_utente_ente_comune_ruolo")
    private Long id;

    @Column(name = "id_ente")
    private long idEnte;

    @Column(name = "id_comune")
    private long idComune;

    @Column(name = "id_utente")
    private long idUtente;

    @Column(name = "cod_ruolo")
    private String ruoloUtente;

/*	
 *  @ManyToOne
    @JoinColumn(name = "codice")
    private MudeDRuoloUtente ruoloUtente;
*
*/
	/**
	 * @return the ruoloUtente
	 */
	

	public long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(long idEnte) {
		this.idEnte = idEnte;
	}

	public long getIdComune() {
		return idComune;
	}

	public void setIdComune(long idComune) {
		this.idComune = idComune;
	}

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public String getRuoloUtente() {
		return ruoloUtente;
	}

	public void setRuoloUtente(String ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}