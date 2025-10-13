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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "mudeopen_r_notifica_contatto")
public class MudeRNotificaContatto extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notifica_contatto")
	private long idNotificaContatto;

	@ManyToOne
	@JoinColumn(name="id_notifica")
	private MudeTNotifica mudeTNotifica;

	@ManyToOne
	@JoinColumn(name="id_contatto")
	private MudeTContatto mudeTContatto;

	@Column(name="email_num_retry")
	private int emailNumRetry ;
	
	@Column(name="email_status")
	private String emailStatus;
	
    @Column(name = "data_invio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInvio;

    @Column(name = "data_last_retry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLastRetry;

	@Column(name="body_email")
	private String bodyEmail;

	public long getIdNotificaContatto() {
		return idNotificaContatto;
	}

	public void setIdNotificaContatto(long idNotificaContatto) {
		this.idNotificaContatto = idNotificaContatto;
	}

	public MudeTNotifica getMudeTNotifica() {
		return mudeTNotifica;
	}

	public void setMudeTNotifica(MudeTNotifica mudeTNotifica) {
		this.mudeTNotifica = mudeTNotifica;
	}

	public MudeTContatto getMudeTContatto() {
		return mudeTContatto;
	}

	public void setMudeTContatto(MudeTContatto mudeTContatto) {
		this.mudeTContatto = mudeTContatto;
	}

	public int getEmailNumRetry() {
		return emailNumRetry;
	}

	public void setEmailNumRetry(int emailNumRetry) {
		this.emailNumRetry = emailNumRetry;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Date getDataLastRetry() {
		return dataLastRetry;
	}

	public void setDataLastRetry(Date dataLastRetry) {
		this.dataLastRetry = dataLastRetry;
	}

	/**
	 * @return the bodyEmail
	 */
	public String getBodyEmail() {
		return bodyEmail;
	}

	/**
	 * @param bodyEmail the bodyEmail to set
	 */
	public void setBodyEmail(String bodyEmail) {
		this.bodyEmail = bodyEmail;
	}

	

}