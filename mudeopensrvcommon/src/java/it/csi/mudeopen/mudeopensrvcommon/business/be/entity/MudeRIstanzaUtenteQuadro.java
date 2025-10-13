/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "mudeopen_r_istanza_utente_quadro")
public class MudeRIstanzaUtenteQuadro extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5298060657466743709L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_istanza_utente_quadro")
    private Long id;

	@ManyToOne
	@JoinColumn(name = "id_istanza_utente")
	private MudeRIstanzaUtente mudeRIstanzaUtente;

	@ManyToOne
	@JoinColumn(name = "id_quadro")
	private MudeDQuadro mudeDQuadro;

	@ManyToOne
	@JoinColumn(name = "id_funzione")
	private MudeDFunzione mudeDFunzione;

	@ManyToOne
	@JoinColumn(name = "id_abilitazione")
	private MudeDAbilitazione mudeDAbilitazione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeDQuadro getMudeDQuadro() {
		return mudeDQuadro;
	}

	public void setMudeDQuadro(MudeDQuadro mudeDQuadro) {
		this.mudeDQuadro = mudeDQuadro;
	}

	public MudeDFunzione getMudeDFunzione() {
		return mudeDFunzione;
	}

	public void setMudeDFunzione(MudeDFunzione mudeDFunzione) {
		this.mudeDFunzione = mudeDFunzione;
	}

	public MudeDAbilitazione getMudeDAbilitazione() {
		return mudeDAbilitazione;
	}

	public void setMudeDAbilitazione(MudeDAbilitazione mudeDAbilitazione) {
		this.mudeDAbilitazione = mudeDAbilitazione;
	}

	public MudeRIstanzaUtente getMudeRIstanzaUtente() {
		return mudeRIstanzaUtente;
	}

	public void setMudeRIstanzaUtente(MudeRIstanzaUtente mudeRIstanzaUtente) {
		this.mudeRIstanzaUtente = mudeRIstanzaUtente;
	}

}