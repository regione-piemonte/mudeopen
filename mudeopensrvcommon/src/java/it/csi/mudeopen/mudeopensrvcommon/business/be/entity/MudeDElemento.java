/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_elemento")
public class MudeDElemento extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_elemento")
	private long idElemento;

	@Column(name = "posizione")
	private long posizione;

	@Column(name = "denominazione")
	private String denominazione;

    @OneToMany(mappedBy="elemento", fetch = FetchType.LAZY )
	Set<MudeDOpera> opere;
    public MudeDElemento() {
	}

    public long getIdElemento() {
		return idElemento;
	}

    public void setIdElemento(long idElemento) {
		this.idElemento = idElemento;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public Set<MudeDOpera> getOpere() {
		return opere;
	}

    public void setOpere(Set<MudeDOpera> opere) {
		this.opere = opere;
	}

    public long getPosizione() {
		return posizione;
	}

    public void setPosizione(long posizione) {
		this.posizione = posizione;
	}

	
}