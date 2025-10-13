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

/**
 * The type Mude d abilitazione.
 */
@Entity
@Table(name = "mudeopen_d_elemento_elenco")
public class MudeDElementoElenco extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 832576564204398689L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_elemento_elenco")
    private Long id;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "descrizione_estesa")
    private String descrizioneEstesa;

    @Column(name = "id_tipo_elenco")
    private Long idTipoElenco;

    @Column(name = "ordinamento")
    private Long ordinamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTipoElenco() {
		return idTipoElenco;
	}

	public void setIdTipoElenco(Long idTipoElenco) {
		this.idTipoElenco = idTipoElenco;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public Long getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}
}