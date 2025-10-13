/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "mudeopen_t_pratica")
public class MudeTPratica extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6942252871333308128L;
    
    public static final String IDF_user = "IDF-user";

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_pratica")
    private Long id;

	@Column(name="numero_pratica")
	private String numeroPratica;

	@Column(name="anno_pratica")
	private Long annoPratica;
	
    @ManyToOne
    @JoinColumn(name="id_ente")
    private MudeTEnte ente;

    // uuid della folder su index
    @Column(name = "index_folder")
    private String uuidIndex;

    @ManyToMany(mappedBy = "pratiche", fetch = FetchType.EAGER)
    private List<MudeTIstanza> istanze = new ArrayList<MudeTIstanza>();

    @Column(name = "ice")
    private String ice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public Long getAnnoPratica() {
		return annoPratica;
	}

	public void setAnnoPratica(Long annoPratica) {
		this.annoPratica = annoPratica;
	}

	public MudeTEnte getEnte() {
		return ente;
	}

	public void setEnte(MudeTEnte ente) {
		this.ente = ente;
	}

	public List<MudeTIstanza> getIstanze() {
		return istanze;
	}

	public void setIstanze(List<MudeTIstanza> istanze) {
		this.istanze = istanze;
	}

	public String getUuidIndex() {
		return uuidIndex;
	}

	public void setUuidIndex(String uuidIndex) {
		this.uuidIndex = uuidIndex;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

}