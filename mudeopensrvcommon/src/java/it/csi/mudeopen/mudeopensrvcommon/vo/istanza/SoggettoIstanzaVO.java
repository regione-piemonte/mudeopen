/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

public class SoggettoIstanzaVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 6698721171253833516L;

	@JsonProperty("id_istanza_soggetto")
	private long idIstanzaSoggetto;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idSoggetto;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String idTitoloSoggetto;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String idTitoloSoggettoRT;

    private ContattoVO contatto;
    private List<SelectVO> ruoloSoggetto;
    //fixme verificare se va cambiato in stringa

    private Long idIstanza;
    private String ruoliPossibili;

    @JsonProperty("opera_fini_fiscali_dipendente")
    private Boolean operaFiniFiscaliDipendente = Boolean.FALSE;

    @JsonProperty("ente_societa_appartenenza")
    private String enteSocietaAppartenenza;

    @JsonProperty("domiciliazione_corrispondenza_professionista")
    private Boolean domiciliazioneCorrispondenzaProfessionista;

    private List<IstanzaUtenteVO> abilitazioni;

    private IstanzaVO istanza;

    private String operation;

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public ContattoVO getContatto() {
        return contatto;
    }

    public void setContatto(ContattoVO contatto) {
        this.contatto = contatto;
    }

    public List<SelectVO> getRuoloSoggetto() {
        return ruoloSoggetto;
    }

    public void setRuoloSoggetto(List<SelectVO> ruoloSoggetto) {
        this.ruoloSoggetto = ruoloSoggetto;
    }

    public String getIdTitoloSoggetto() {
        return idTitoloSoggetto;
    }

    public void setIdTitoloSoggetto(String idTitoloSoggetto) {
        this.idTitoloSoggetto = idTitoloSoggetto;
    }

    public List<IstanzaUtenteVO> getAbilitazioni() {
        return abilitazioni;
    }

    public void setAbilitazioni(List<IstanzaUtenteVO> abilitazioni) {
        this.abilitazioni = abilitazioni;
    }

    public Boolean getOperaFiniFiscaliDipendente() {
        return operaFiniFiscaliDipendente;
    }

    public void setOperaFiniFiscaliDipendente(Boolean operaFiniFiscaliDipendente) {
        this.operaFiniFiscaliDipendente = operaFiniFiscaliDipendente;
    }

    public String getEnteSocietaAppartenenza() {
        return enteSocietaAppartenenza;
    }

    public void setEnteSocietaAppartenenza(String enteSocietaAppartenenza) {
        this.enteSocietaAppartenenza = enteSocietaAppartenenza;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getRuoliPossibili() {
        return ruoliPossibili;
    }

    public void setRuoliPossibili(String ruoliPossibili) {
        this.ruoliPossibili = ruoliPossibili;
    }

    public Boolean getDomiciliazioneCorrispondenzaProfessionista() {
        return domiciliazioneCorrispondenzaProfessionista;
    }

    public void setDomiciliazioneCorrispondenzaProfessionista(Boolean domiciliazioneCorrispondenzaProfessionista) {
        this.domiciliazioneCorrispondenzaProfessionista = domiciliazioneCorrispondenzaProfessionista;
    }

	public long getIdIstanzaSoggetto() {
		return idIstanzaSoggetto;
	}

	public void setIdIstanzaSoggetto(long idIstanzaSoggetto) {
		this.idIstanzaSoggetto = idIstanzaSoggetto;
	}

	public IstanzaVO getIstanza() {
		return istanza;
	}

	public void setIstanza(IstanzaVO istanza) {
		this.istanza = istanza;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIdTitoloSoggettoRT() {
		return idTitoloSoggettoRT;
	}

	public void setIdTitoloSoggettoRT(String idTitoloSoggettoRT) {
		this.idTitoloSoggettoRT = idTitoloSoggettoRT;
	}
}