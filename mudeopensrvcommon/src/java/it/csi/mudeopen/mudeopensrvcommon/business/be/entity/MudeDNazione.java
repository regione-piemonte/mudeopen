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
@Table(name = "mudeopen_d_nazione")
public class MudeDNazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_nazione")
	private long idNazione;

	@Column(name="cod_belfiore_nazione")
	private String codBelfioreNazione;

	@Column(name="cod_istat_nazione")
	private String codIstatNazione;

	@Column(name="denom_nazione")
	private String denomNazione;

	@Column(name="dt_id_stato")
	private BigDecimal dtIdStato;

	@Column(name="dt_id_stato_next")
	private BigDecimal dtIdStatoNext;

	@Column(name="dt_id_stato_prev")
	private BigDecimal dtIdStatoPrev;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to MudeDRegione
	@OneToMany(mappedBy="mudeDNazione")
	private List<MudeDRegione> mudeDRegiones;

	@ManyToOne
	@JoinColumn(name="id_user")
	private MudeTUser mudeTUser;

	@Column(name="stato_membro_ue")
	private Boolean statoMembroUE;

    public MudeDNazione() {
	}

    public long getIdNazione() {
		return this.idNazione;
	}

    public void setIdNazione(long idNazione) {
		this.idNazione = idNazione;
	}

    public String getCodBelfioreNazione() {
		return this.codBelfioreNazione;
	}

    public void setCodBelfioreNazione(String codBelfioreNazione) {
		this.codBelfioreNazione = codBelfioreNazione;
	}

    public String getCodIstatNazione() {
		return this.codIstatNazione;
	}

    public void setCodIstatNazione(String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}

    public String getDenomNazione() {
		return this.denomNazione;
	}

    public void setDenomNazione(String denomNazione) {
		this.denomNazione = denomNazione;
	}

    public BigDecimal getDtIdStato() {
		return this.dtIdStato;
	}

    public void setDtIdStato(BigDecimal dtIdStato) {
		this.dtIdStato = dtIdStato;
	}

    public BigDecimal getDtIdStatoNext() {
		return this.dtIdStatoNext;
	}

    public void setDtIdStatoNext(BigDecimal dtIdStatoNext) {
		this.dtIdStatoNext = dtIdStatoNext;
	}

    public BigDecimal getDtIdStatoPrev() {
		return this.dtIdStatoPrev;
	}

    public void setDtIdStatoPrev(BigDecimal dtIdStatoPrev) {
		this.dtIdStatoPrev = dtIdStatoPrev;
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

    public List<MudeDRegione> getMudeDRegiones() {
		return mudeDRegiones;
	}

    public void setMudeDRegiones(List<MudeDRegione> mudeDRegiones) {
		this.mudeDRegiones = mudeDRegiones;
	}

    public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

    public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}

    public Boolean getStatoMembroUE() {
		return statoMembroUE;
	}

    public void setStatoMembroUE(Boolean statoMembroUE) {
		this.statoMembroUE = statoMembroUE;
	}
}