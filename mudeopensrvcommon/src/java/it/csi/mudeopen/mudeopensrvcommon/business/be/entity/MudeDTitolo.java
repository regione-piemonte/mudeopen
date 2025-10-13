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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_titolo")
public class MudeDTitolo extends BaseDictionaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="id_tipo_presentatore")
	private Long idTipoPresentatore;

	public Long getIdTipoPresentatore() {
		return idTipoPresentatore;
	}

	public void setIdTipoPresentatore(Long idTipoPresentatore) {
		this.idTipoPresentatore = idTipoPresentatore;
	}
}