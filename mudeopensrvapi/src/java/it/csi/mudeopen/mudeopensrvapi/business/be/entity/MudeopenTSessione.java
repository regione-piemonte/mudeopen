/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The type Mudeopen t sessione.
 */
@Entity
@Table(name = "mudeopen_t_sessione")
public class MudeopenTSessione  {
    @Id
    @Type(type="pg-uuid")
    @Column(name = "id_sessione", columnDefinition = "uuid")
    private UUID idSessione;

    @Column(name = "fruitore")
    private String fruitore;

    @Column(name = "data_creazione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCreazione;

    @Column(name = "parametro_input")
    private String parametroInput;

	public UUID getIdSessione() {
		return idSessione;
	}

	public void setIdSessione(UUID idSessione) {
		this.idSessione = idSessione;
	}

	public String getFruitore() {
		return fruitore;
	}

	public void setFruitore(String fruitore) {
		this.fruitore = fruitore;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getParametroInput() {
		return parametroInput;
	}

	public void setParametroInput(String parametroInput) {
		this.parametroInput = parametroInput;
	}

 }