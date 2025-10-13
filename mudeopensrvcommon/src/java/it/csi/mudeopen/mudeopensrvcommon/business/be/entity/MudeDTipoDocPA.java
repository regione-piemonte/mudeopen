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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mudeopen_d_tipo_docpa")
public class MudeDTipoDocPA implements Serializable {
    private static final long serialVersionUID = 2404039273488839223L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_docpa")
    private Long id;

    @Column(name="cod_tipo_docpa")
    private String codeTipoDocpa;

    @Column(name="desc_tipo_docpa")
    private String descTipoDocpa;

    @Column(name = "data_inizio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInizio;

    @Column(name = "data_fine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFine;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeTipoDocpa() {
		return codeTipoDocpa;
	}

	public void setCodeTipoDocpa(String codeTipoDocpa) {
		this.codeTipoDocpa = codeTipoDocpa;
	}

	public String getDescTipoDocpa() {
		return descTipoDocpa;
	}

	public void setDescTipoDocpa(String descTipoDocpa) {
		this.descTipoDocpa = descTipoDocpa;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
}