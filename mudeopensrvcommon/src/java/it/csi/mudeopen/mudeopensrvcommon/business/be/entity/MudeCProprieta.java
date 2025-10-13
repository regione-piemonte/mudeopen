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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_c_proprieta")
public class MudeCProprieta implements Serializable {
	private static final long serialVersionUID = 5923254739172552790L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_proprieta")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "valore")
	private String valore;

	@Column(name = "note")
	private String note;

    public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}

    public String getNome() {
		return nome;
	}

    public void setNome(String nome) {
		this.nome = nome;
	}

    public String getValore() {
		return valore;
	}

    public void setValore(String valore) {
		this.valore = valore;
	}

    public String getNote() {
		return note;
	}

    public void setNote(String note) {
		this.note = note;
	}
}