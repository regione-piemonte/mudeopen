/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mudeopen_d_wf_stato")
public class MudeDWfStato implements Serializable {
    private static final long serialVersionUID = -3532218377483356107L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_wf_stato")
	private long id;

    @Column(name = "codice_stato_start")
    private String codiceStatoStart;

    @Column(name = "codice_stato_end")
    private String codiceStatoEnd;

    @Column(name = "id_tipo_istanza")
    private String tipoIstanza;

    @Column(name = "oggetto_notifica")
    private String oggettoNotifica;

    @Column(name = "testo_notifica")
    private String testoNotifica;

    @Column(name = "data_inizio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInizio = new Date();

    @Column(name = "data_fine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFine;

    @Column(name = "specie_pratica")
    private String speciePratica;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOggettoNotifica() {
		return oggettoNotifica;
	}

	public void setOggettoNotifica(String oggettoNotifica) {
		this.oggettoNotifica = oggettoNotifica;
	}

	public String getTestoNotifica() {
		return testoNotifica;
	}

	public void setTestoNotifica(String testoNotifica) {
		this.testoNotifica = testoNotifica;
	}

	public String getCodiceStatoStart() {
		return codiceStatoStart;
	}

	public void setCodiceStatoStart(String codiceStatoStart) {
		this.codiceStatoStart = codiceStatoStart;
	}

	public String getCodiceStatoEnd() {
		return codiceStatoEnd;
	}

	public void setCodiceStatoEnd(String codiceStatoEnd) {
		this.codiceStatoEnd = codiceStatoEnd;
	}

	public String getTipoIstanza() {
		return tipoIstanza;
	}

	public void setTipoIstanza(String tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

	public String getSpeciePratica() {
		return speciePratica;
	}

	public void setSpeciePratica(String speciePratica) {
		this.speciePratica = speciePratica;
	}
}