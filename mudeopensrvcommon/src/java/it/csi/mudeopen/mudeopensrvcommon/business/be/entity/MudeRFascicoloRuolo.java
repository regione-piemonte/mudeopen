/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_r_fascicolo_ruolo")
public class MudeRFascicoloRuolo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6942252871333308128L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_fascicolo_ruolo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_fascicolo")
    private MudeTFascicolo fascicolo;

	@Column(name="ruolo")
	private String ruolo;

    @ManyToOne
    @JoinColumn(name = "id_user_ins")
    private MudeTUser user;

    @Column(name="guid_soggetto")
    private String guidSoggetto;

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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public MudeTUser getUser() {
		return user;
	}

	public void setUser(MudeTUser user) {
		this.user = user;
	}

	public String getGuidSoggetto() {
		return guidSoggetto;
	}

	public void setGuidSoggetto(String guidSoggetto) {
		this.guidSoggetto = guidSoggetto;
	}

}