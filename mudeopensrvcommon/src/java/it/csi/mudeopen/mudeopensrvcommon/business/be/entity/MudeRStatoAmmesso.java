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
@Table(name = "mudeopen_r_stato_ammesso")
public class MudeRStatoAmmesso extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_ammesso")
	private long idStatoAmmesso;

	@ManyToOne
	@JoinColumn(name="codice_stato_istanza")
	private MudeDStatoIstanza statoIstanza;

	@ManyToOne
	@JoinColumn(name="codice_stato_istanza_ammesso")
	private MudeDStatoIstanza statoIstanzaAmmesso;

	@Column(name = "oggetto_proposto")
	private String oggettoProposto;
	
	@Column(name = "messaggio_proposto")
	private String messaggioProposto;

	public long getIdStatoAmmesso() {
		return idStatoAmmesso;
	}

	public void setIdStatoAmmesso(long idStatoAmmesso) {
		this.idStatoAmmesso = idStatoAmmesso;
	}

	public MudeDStatoIstanza getStatoIstanza() {
		return statoIstanza;
	}

	public void setStatoIstanza(MudeDStatoIstanza statoIstanza) {
		this.statoIstanza = statoIstanza;
	}

	public MudeDStatoIstanza getStatoIstanzaAmmesso() {
		return statoIstanzaAmmesso;
	}

	public void setStatoIstanzaAmmesso(MudeDStatoIstanza statoIstanzaAmmesso) {
		this.statoIstanzaAmmesso = statoIstanzaAmmesso;
	}

	public String getOggettoProposto() {
		return oggettoProposto;
	}

	public void setOggettoProposto(String oggettoProposto) {
		this.oggettoProposto = oggettoProposto;
	}

	public String getMessaggioProposto() {
		return messaggioProposto;
	}

	public void setMessaggioProposto(String messaggioProposto) {
		this.messaggioProposto = messaggioProposto;
	}

}
