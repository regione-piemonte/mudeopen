/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

public class FascicoloVO extends ParentVO {

    @JsonIgnore
    private static final long serialVersionUID = -3961724086179323695L;

    @JsonProperty("id_fascicolo")
    private Long idFascicolo;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_creazione")
    private LocalDateTime dataCreazione = null;

    //	@JsonSerialize(using = CustomDateTimeSerializer.class)
    //	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
    //	@JsonProperty("data_presentazione")
    //	private LocalDateTime dataPresentazione = null;

    @JsonProperty("codice_fascicolo")
    private String codiceFascicolo;

    @JsonProperty("json_data")
    private String jsonData;

    private ComuneVO comune;

    private ProvinciaVO provincia;

    private IndirizzoVO ubicazione;

    private ContattoVO intestatario;

    @JsonProperty("tipologia_istanza")
    private SelectVO tipologiaIstanza;

    @JsonProperty("tipologia_intervento")
    private DizionarioVO tipologiaIntervento;

    private boolean modifica = false;

    private boolean visualizza = false;

    private boolean elimina = false;

    @JsonProperty("assegna_abilitazioni")
    private boolean assegnaAbilitazioni = false;

    @JsonProperty("nuova_istanza")
    private boolean nuovaIstanza = false;

    @JsonProperty("istanza_list")
    private List<IstanzaVO> istanzaList = null;

    @JsonProperty("index_folder")
    private String uuidIndex;

    @JsonProperty("stato_fascicolo")
    DizionarioVO statoFascicolo;

    @JsonProperty("stato_counters")
    private String statoCounters;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_stato")
    private LocalDateTime dataStato = null;

    public Long getIdFascicolo() {
        return idFascicolo;
    }

    public void setIdFascicolo(Long idFascicolo) {
        this.idFascicolo = idFascicolo;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getCodiceFascicolo() {
        return codiceFascicolo;
    }

    public void setCodiceFascicolo(String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public ComuneVO getComune() {
        return comune;
    }

    public void setComune(ComuneVO comune) {
        this.comune = comune;
    }

    public IndirizzoVO getUbicazione() {
        return ubicazione;
    }

    public void setUbicazione(IndirizzoVO ubicazione) {
        this.ubicazione = ubicazione;
    }

    public ContattoVO getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(ContattoVO intestatario) {
        this.intestatario = intestatario;
    }

    public ProvinciaVO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaVO provincia) {
        this.provincia = provincia;
    }

    public SelectVO getTipologiaIstanza() {
        return tipologiaIstanza;
    }

    public void setTipologiaIstanza(SelectVO tipologiaIstanza) {
        this.tipologiaIstanza = tipologiaIstanza;
    }

    public DizionarioVO getTipologiaIntervento() {
        return tipologiaIntervento;
    }

    public void setTipologiaIntervento(DizionarioVO tipologiaIntervento) {
        this.tipologiaIntervento = tipologiaIntervento;
    }

    public List<IstanzaVO> getIstanzaList() {
        return istanzaList;
    }

    public void setIstanzaList(List<IstanzaVO> istanzaList) {
        this.istanzaList = istanzaList;
    }

    public boolean isModifica() {
        return modifica;
    }

    public void setModifica(boolean modifica) {
        this.modifica = modifica;
    }

    public boolean isVisualizza() {
        return visualizza;
    }

    public void setVisualizza(boolean visualizza) {
        this.visualizza = visualizza;
    }

    public boolean isElimina() {
        return elimina;
    }

    public void setElimina(boolean elimina) {
        this.elimina = elimina;
    }

    public boolean isNuovaIstanza() {
        return nuovaIstanza;
    }

    public void setNuovaIstanza(boolean nuovaIstanza) {
        this.nuovaIstanza = nuovaIstanza;
    }

    public String getUuidIndex() {
        return uuidIndex;
    }

    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
    }

    public DizionarioVO getStatoFascicolo() {
        return statoFascicolo;
    }

    public void setStatoFascicolo(DizionarioVO statoFascicolo) {
        this.statoFascicolo = statoFascicolo;
    }

    public boolean isAssegnaAbilitazioni() {
		return assegnaAbilitazioni;
	}

    public void setAssegnaAbilitazioni(boolean assegnaAbilitazioni) {
		this.assegnaAbilitazioni = assegnaAbilitazioni;
	}

	public String getStatoCounters() {
		return statoCounters;
	}

	public void setStatoCounters(String statoCounters) {
		this.statoCounters = statoCounters;
	}

	/**
	 * @return the dataStato
	 */
	public LocalDateTime getDataStato() {
		return dataStato;
	}

	/**
	 * @param dataStato the dataStato to set
	 */
	public void setDataStato(LocalDateTime dataStato) {
		this.dataStato = dataStato;
	}
	
	
}