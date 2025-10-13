/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_d_qualifica")
public class MudeDQualifica extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_qualifica")
	private long idQualifica;

	@Column(name="denominazione")
	private String denominazione;

	@OneToMany(mappedBy="mudeDQualifica")
	private List<MudeRContattoQualifica> contattoQualifiche;

	@Column(name="codice")
	private String codice;

    public MudeDQualifica() {
	}

    public long getIdQualifica() {
		return idQualifica;
	}

    public void setIdQualifica(long idQualifica) {
		this.idQualifica = idQualifica;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public List<MudeRContattoQualifica> getContattoQualifiche() {
		return contattoQualifiche;
	}

    public void setContattoQualifiche(List<MudeRContattoQualifica> contattoQualifiche) {
		this.contattoQualifiche = contattoQualifiche;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}
}