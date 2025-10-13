/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "mudeopen_d_comune")
public class MudeDComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_comune")
	private long idComune;

	@Column(name="cod_belfiore_comune")
	private String codBelfioreComune;

	@Column(name="cod_istat_comune")
	private String istatComune;

	@Column(name="denom_comune")
	private String denomComune;

	@Column(name="dt_id_comune")
	private BigDecimal dtIdComune;

	@Column(name="dt_id_comune_next")
	private BigDecimal dtIdComuneNext;

	@Column(name="dt_id_comune_prev")
	private BigDecimal dtIdComunePrev;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to MudeDProvincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private MudeDProvincia mudeDProvincia;

	//bi-directional many-to-one association to mudeDComune
	@OneToMany(mappedBy="mudeDComune")
	private List<MudeTIndirizzo> indirizzi;

	@Column(name="email")
	private String email;

    public MudeDComune() {
	}

    public long getIdComune() {
		return this.idComune;
	}

    public void setIdComune(long idComune) {
		this.idComune = idComune;
	}

    public String getCodBelfioreComune() {
		return this.codBelfioreComune;
	}

    public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

    public String getIstatComune() {
		return this.istatComune;
	}

    public void setIstatComune(String istatComune) {
		this.istatComune = istatComune;
	}

    public String getDenomComune() {
		return this.denomComune;
	}

    public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

    public BigDecimal getDtIdComune() {
		return this.dtIdComune;
	}

    public void setDtIdComune(BigDecimal dtIdComune) {
		this.dtIdComune = dtIdComune;
	}

    public BigDecimal getDtIdComuneNext() {
		return this.dtIdComuneNext;
	}

    public void setDtIdComuneNext(BigDecimal dtIdComuneNext) {
		this.dtIdComuneNext = dtIdComuneNext;
	}

    public BigDecimal getDtIdComunePrev() {
		return this.dtIdComunePrev;
	}

    public void setDtIdComunePrev(BigDecimal dtIdComunePrev) {
		this.dtIdComunePrev = dtIdComunePrev;
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

    public MudeDProvincia getMudeDProvincia() {
		return mudeDProvincia;
	}

    public void setMudeDProvincia(MudeDProvincia mudeDProvincia) {
		this.mudeDProvincia = mudeDProvincia;
	}

	public List<MudeTIndirizzo> getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(List<MudeTIndirizzo> indirizzi) {
		this.indirizzi = indirizzi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}