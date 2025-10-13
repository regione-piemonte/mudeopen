/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_r_pf_pg")
public class MudeRPfPg extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pf_pg")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_soggetto_pf")
    private MudeTContatto soggettoPf;

    @ManyToOne
    @JoinColumn(name="id_soggetto_pg")
    private MudeTContatto soggettoPg;

    @Column(name="id_titolo")
	private String idTitolo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeTContatto getSoggettoPf() {
        return soggettoPf;
    }

    public void setSoggettoPf(MudeTContatto soggettoPf) {
        this.soggettoPf = soggettoPf;
    }

    public MudeTContatto getSoggettoPg() {
        return soggettoPg;
    }

    public void setSoggettoPg(MudeTContatto soggettoPg) {
        this.soggettoPg = soggettoPg;
    }

    public String getIdTitolo() {
		return idTitolo;
	}

    public void setIdTitolo(String idTitolo) {
		this.idTitolo = idTitolo;
	}
}