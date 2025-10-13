/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_t_geeco_session")
public class MudeTGeecoSession extends BaseEntity {

    @Id
    @GeneratedValue(
    		strategy = GenerationType.IDENTITY,
    		generator="seq_session_geeco"
    )
    @SequenceGenerator(name="seq_session_geeco",sequenceName="seq_mudeopen_t_geeco_session", allocationSize=1)    
    @Column(name = "id_session")
    private Long id;

    @Column(name = "id_istanza")
    private Long idIstanza;

    @Column(name = "id_localizzazione")
    private Long idLocalizzazione;

    @Column(name = "url_geeco")
    private String url_Geeco;

    @Column(name = "sessione_geeco")
    private String sessioneGeeco;

	@ManyToOne
	@JoinColumn(name="id_user")
	private MudeTUser mudeTUser;

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

	public Long getIdLocalizzazione() {
		return idLocalizzazione;
	}

	public void setIdLocalizzazione(Long idLocalizzazione) {
		this.idLocalizzazione = idLocalizzazione;
	}

	public String getUrl_Geeco() {
		return url_Geeco;
	}

	public void setUrl_Geeco(String url_Geeco) {
		this.url_Geeco = url_Geeco;
	}

	public String getSessioneGeeco() {
		return sessioneGeeco;
	}

	public void setSessioneGeeco(String pSessioneGeeco) {
		this.sessioneGeeco = pSessioneGeeco;
	}

	public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

	public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}    
}