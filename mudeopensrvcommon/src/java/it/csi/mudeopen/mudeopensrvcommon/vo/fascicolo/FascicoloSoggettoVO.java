/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

public class FascicoloSoggettoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -8803947702807292649L;

    @JsonProperty("id_fascicolo_soggetto")
    private Long id;

    @JsonProperty("fascicolo")
    private FascicoloVO fascicolo;

    @JsonProperty("contatto")
    private ContattoVO contattoFascicolo;

    @JsonProperty("istanze_soggetto")
    private List<IstanzaVO> istanzeSoggetto;
	
    @JsonProperty("utente")
    private UtenteVO user;

    @JsonProperty("guid_soggetto")
    private String guidSoggetto;

	@JsonProperty("data_inizio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataInizio;

	@JsonProperty("data_fine")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataFine;
	
	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FascicoloVO getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(FascicoloVO fascicolo) {
        this.fascicolo = fascicolo;
    }

    public UtenteVO getUser() {
        return user;
    }

    public void setUser(UtenteVO user) {
        this.user = user;
    }

    public String getGuidSoggetto() {
        return guidSoggetto;
    }

    public void setGuidSoggetto(String guidSoggetto) {
        this.guidSoggetto = guidSoggetto;
    }

	public List<IstanzaVO> getIstanzeSoggetto() {
		return istanzeSoggetto;
	}

	public void setIstanzeSoggetto(List<IstanzaVO> istanzeSoggetto) {
		this.istanzeSoggetto = istanzeSoggetto;
	}

	public ContattoVO getContattoFascicolo() {
		return contattoFascicolo;
	}

	public void setContattoFascicolo(ContattoVO contattoFascicolo) {
		this.contattoFascicolo = contattoFascicolo;
	}

}