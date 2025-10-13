/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mudeopen_r_documenti_notifica")
public class MudeRDocumentiNotifica extends BaseEntity {

	@Id
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_notifica")
	private MudeTNotifica mudeTNotifica;
	
	@ManyToOne
	@JoinColumn(name="id_documento")
	private MudeTDocumento mudeopenTDocumento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeTNotifica getMudeTNotifica() {
		return mudeTNotifica;
	}

	public void setMudeTNotifica(MudeTNotifica mudeTNotifica) {
		this.mudeTNotifica = mudeTNotifica;
	}

	public MudeTDocumento getMudeopenTDocumento() {
		return mudeopenTDocumento;
	}

	public void setMudeopenTDocumento(MudeTDocumento mudeopenTDocumento) {
		this.mudeopenTDocumento = mudeopenTDocumento;
	}
	
	
}
