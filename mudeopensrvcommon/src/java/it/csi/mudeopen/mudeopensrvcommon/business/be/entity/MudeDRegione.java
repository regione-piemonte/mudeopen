/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "mudeopen_d_regione")
public class MudeDRegione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_regione")
	private long idRegione;

	@Column(name="cod_regione")
	private String codRegione;

	@Column(name="denom_regione")
	private String denomRegione;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to MudeDProvincia
	@OneToMany(mappedBy="mudeDRegione")
	private List<MudeDProvincia> mudeDProvincias;

	//bi-directional many-to-one association to MudeDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione")
	private MudeDNazione mudeDNazione;

    public MudeDRegione() {
	}

    public long getIdRegione() {
		return this.idRegione;
	}

    public void setIdRegione(long idRegione) {
		this.idRegione = idRegione;
	}

    public String getCodRegione() {
		return this.codRegione;
	}

    public void setCodRegione(String codRegione) {
		this.codRegione = codRegione;
	}

    public String getDenomRegione() {
		return this.denomRegione;
	}

    public void setDenomRegione(String denomRegione) {
		this.denomRegione = denomRegione;
	}

    public Date getFineValidita() {
		return this.fineValidita;
	}

    public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

    public Date getInizioValidita() {
		return this.inizioValidita;
	}

    public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

    public List<MudeDProvincia> getMudeDProvincias() {
		return this.mudeDProvincias;
	}

    public void setMudeDProvincias(List<MudeDProvincia> mudeDProvincias) {
		this.mudeDProvincias = mudeDProvincias;
	}

    public MudeDNazione getMudeDNazione() {
		return mudeDNazione;
	}

    public void setMudeDNazione(MudeDNazione mudeDNazione) {
		this.mudeDNazione = mudeDNazione;
	}
}