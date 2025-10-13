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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_r_notifica_documento")
public class MudeRNotificaDocumento extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notifica_documento")
	private long idNotificaDocumento;

	@ManyToOne
	@JoinColumn(name="id_notifica")
	private MudeTNotifica mudeTNotifica;

	@ManyToOne
	@JoinColumn(name="id_documento")
	private MudeTDocumento mudeTDocumento;

	public long getIdNotificaDocumento() {
		return idNotificaDocumento;
	}

	public void setIdNotificaDocumento(long idNotificaDocumento) {
		this.idNotificaDocumento = idNotificaDocumento;
	}

	public MudeTNotifica getMudeTNotifica() {
		return mudeTNotifica;
	}

	public void setMudeTNotifica(MudeTNotifica mudeTNotifica) {
		this.mudeTNotifica = mudeTNotifica;
	}

	public MudeTDocumento getMudeTDocumento() {
		return mudeTDocumento;
	}

	public void setMudeTDocumento(MudeTDocumento mudeTDocumento) {
		this.mudeTDocumento = mudeTDocumento;
	}
}