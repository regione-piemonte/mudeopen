/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_t_pratica_idf")
public class MudeTPraticaIdf extends BaseLoggerEntity implements Serializable {

    private static final long serialVersionUID = -9110433609297411435L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pratica_idf")
    private Long id;

    @Column(name = "id_istanza")
    private long idIstanza;

    @Column(name = "codice_stato_idf")
    private String codiceStatoIdf;

    @Column(name = "retries")
    private Integer retries;
    
    @Column(name = "competenza")
    private String competenza;
    
    @Column(name = "autorizzato")
    private String autorizzato;

	@Column(name = "numero_determina_esito_aut")
    private String numeroDeterminaEsitoAut;

    @Column(name = "data_scadenza_autorizzazione")
    private String dataScadenzaAutorizzazione;
    
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    
    public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getAutorizzato() {
		return autorizzato;
	}

	public void setAutorizzato(String autorizzato) {
		this.autorizzato = autorizzato;
	}

	public String getNumeroDeterminaEsitoAut() {
		return numeroDeterminaEsitoAut;
	}

	public void setNumeroDeterminaEsitoAut(String numeroDeterminaEsitoAut) {
		this.numeroDeterminaEsitoAut = numeroDeterminaEsitoAut;
	}

	public String getDataScadenzaAutorizzazione() {
		return dataScadenzaAutorizzazione;
	}

	public void setDataScadenzaAutorizzazione(String dataScadenzaAutorizzazione) {
		this.dataScadenzaAutorizzazione = dataScadenzaAutorizzazione;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getCodiceStatoIdf() {
		return codiceStatoIdf;
	}

	public void setCodiceStatoIdf(String codiceStatoIdf) {
		this.codiceStatoIdf = codiceStatoIdf;
	}

	public Integer getRetries() {
		return retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public void setIdIstanza(long idIstanza) {
		this.idIstanza = idIstanza;
	}


}