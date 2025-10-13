/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_task_quadro")
public class MudeDTaskQuadro implements Serializable {
    private static final long serialVersionUID = -3532218377483356107L;

    @Id
    @Column(name = "codice_task")
    private String codiceTask;

    @Column(name = "desc_task")
    private String descTask;

    @Column(name = "id_quadro")
    private Long quadro;

	public String getCodiceTask() {
		return codiceTask;
	}

	public void setCodiceTask(String codiceTask) {
		this.codiceTask = codiceTask;
	}

	public String getDescTask() {
		return descTask;
	}

	public void setDescTask(String descTask) {
		this.descTask = descTask;
	}

	public Long getQuadro() {
		return quadro;
	}

	public void setQuadro(Long quadro) {
		this.quadro = quadro;
	}
}