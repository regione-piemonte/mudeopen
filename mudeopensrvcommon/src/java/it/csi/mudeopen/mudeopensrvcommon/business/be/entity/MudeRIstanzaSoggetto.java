/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_r_istanza_soggetto")
public class MudeRIstanzaSoggetto extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_istanza_soggetto")
	private long idIstanzaSoggetto;

	@ManyToOne
	@JoinColumn(name="id_soggetto")
	private MudeTContatto mudeTContatto;

	@ManyToOne
	@JoinColumn(name="id_istanza")
	private MudeTIstanza mudeTIstanza;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "mudeopen_r_istanza_soggetto_ruoli", joinColumns = @JoinColumn(name = "id_istanza_soggetto"))
    @Column(name="ruoli")
	private List<String> ruoli;

	@Column(name="opera_fini_fiscali_dipendente")
	private Boolean operaFiniFiscaliDipendente;

	@Column(name="ente_societa_appartenenza")
	private String enteSocietaAppartenenza;

	@Column(name="domiciliazione_corrispondenza_professionista")
	private Boolean domiciliazioneCorrispondenzaProfessionista;

    public MudeRIstanzaSoggetto() {
	}

    public MudeTContatto getMudeTContatto() {
		return mudeTContatto;
	}

    public void setMudeTContatto(MudeTContatto mudeTContatto) {
		this.mudeTContatto = mudeTContatto;
	}

    public MudeTIstanza getMudeTIstanza() {
		return mudeTIstanza;
	}

    public void setMudeTIstanza(MudeTIstanza mudeTIstanza) {
		this.mudeTIstanza = mudeTIstanza;
	}

    public List<String> getRuoli() {
		return ruoli;
	}

    public void setRuoli(List<String> ruoli) {
		this.ruoli = ruoli;
	}

    public long getIdIstanzaSoggetto() {
		return idIstanzaSoggetto;
	}

    public void setIdIstanzaSoggetto(long idIstanzaSoggetto) {
		this.idIstanzaSoggetto = idIstanzaSoggetto;
	}

	public Boolean getOperaFiniFiscaliDipendente() {
		return operaFiniFiscaliDipendente;
	}

	public void setOperaFiniFiscaliDipendente(Boolean dipendenteIntestatarioIstanza) {
		this.operaFiniFiscaliDipendente = dipendenteIntestatarioIstanza;
	}

	public String getEnteSocietaAppartenenza() {
		return enteSocietaAppartenenza;
	}

	public void setEnteSocietaAppartenenza(String enteSocietaAppartenenza) {
		this.enteSocietaAppartenenza = enteSocietaAppartenenza;
	}

	public Boolean getDomiciliazioneCorrispondenzaProfessionista() {
		return domiciliazioneCorrispondenzaProfessionista;
	}

	public void setDomiciliazioneCorrispondenzaProfessionista(Boolean domiciliazioneCorrispondenzaProfessionista) {
		this.domiciliazioneCorrispondenzaProfessionista = domiciliazioneCorrispondenzaProfessionista;
	}

	public void aggiungiRuolo(String ruolo) {
		if(this.ruoli == null) {
			this.ruoli = new ArrayList<String>();
			this.ruoli.add(ruolo);
		}else {
			// Controllo se i ruoli che voglio aggiungere esistono gia'
			boolean presente = false;
			for(String ruoloPresente : ruoli) {
				if(ruoloPresente == ruolo) {
					presente = true;
					break;
				}
			}
			if(!presente) {
				this.ruoli.add(ruolo);
			}
		}
		
	}

}