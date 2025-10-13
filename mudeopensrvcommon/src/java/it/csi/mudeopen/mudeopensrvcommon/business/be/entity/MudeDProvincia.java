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
@Table(name = "mudeopen_d_provincia")
public class MudeDProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_provincia")
	private long idProvincia;

	@Column(name="cod_provincia")
	private String codProvincia;

	@Column(name="denom_provincia")
	private String denomProvincia;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	@Column(name="sigla_provincia")
	private String siglaProvincia;

	//bi-directional many-to-one association to MudeDComune
	@OneToMany(mappedBy="mudeDProvincia")
	private List<MudeDComune> mudeDComunes;

	@ManyToOne
	@JoinColumn(name="id_regione")
	private MudeDRegione mudeDRegione;

    public MudeDProvincia() {
	}

    public long getIdProvincia() {
		return this.idProvincia;
	}

    public void setIdProvincia(long idProvincia) {
		this.idProvincia = idProvincia;
	}

    public String getCodProvincia() {
		return this.codProvincia;
	}

    public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

    public String getDenomProvincia() {
		return this.denomProvincia;
	}

    public void setDenomProvincia(String denomProvincia) {
		this.denomProvincia = denomProvincia;
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

    public String getSiglaProvincia() {
		return this.siglaProvincia;
	}

    public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

    public List<MudeDComune> getMudeDComunes() {
		return this.mudeDComunes;
	}

    public void setMudeDComunes(List<MudeDComune> mudeDComunes) {
		this.mudeDComunes = mudeDComunes;
	}

    public MudeDRegione getMudeDRegione() {
		return mudeDRegione;
	}

    public void setMudeDRegione(MudeDRegione mudeDRegione) {
		this.mudeDRegione = mudeDRegione;
	}

}