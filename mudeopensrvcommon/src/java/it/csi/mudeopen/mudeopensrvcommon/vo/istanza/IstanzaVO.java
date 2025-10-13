/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneSlimCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.IstanzaTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FunzioneUtenteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

public class IstanzaVO {
    public static final String _NEW_TEMPLATE = "_NEW_TEMPLATE";

    private static final long serialVersionUID = -3961724086179323695L;
    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_fascicolo")
    private Long idFascicolo;

    @JsonProperty("fascicolo")
    private FascicoloVO fascicoloVO;

    private ProvinciaVO provincia;

    private ComuneVO comune;

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("branch")
    private Integer branch;

    @JsonProperty("tipologia_istanza")
    private SelectVO tipologiaIstanza;

    @JsonProperty("tipo_istanza")
    private TipoIstanzaVO tipoIstanza;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_creazione")
    private LocalDateTime dataCreazione = null;

    @JsonProperty("tipologia_presentatore")
    private SelectVO tipologiaPresentatore;

    @JsonProperty("titolo_soggetto_abilitato")
    private SelectVO titoloSoggettoAbilitato;

    @JsonProperty("soggetto_abilitato")
    private UtenteVO soggettoAbilitato; // soggetto loggato che presenta l'istanza

    // NOT USED
    @JsonProperty("soggetto_list")
    private List<SoggettoIstanzaVO> soggettoList;

    @JsonProperty("proprieta")
    private List<SelectVO> proprieta;

    @JsonProperty("pagamento_attivo")
    private boolean pagamentoAttivo;

    //fixme da rimuovere
    //    private StatoIstanza stato;
    //private StepIstanza step;

    @JsonProperty("stato_istanza")
    private DizionarioVO statoIstanza;

    @JsonProperty("data_stato")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime dataStato;

    @JsonProperty("data_stato_dps")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime dataStatoDPS;

    @JsonProperty("json_data")
    private String jsonData;

    @JsonProperty("codice_istanza")
    private String codiceIstanza;

    private boolean modifica = false;

    private boolean visualizza = false;

    private boolean elimina = false;

    @JsonProperty("assegna_abilitazioni")
    private boolean assegnaAbilitazioni = false;

    @JsonProperty("nuova_istanza")
    private boolean nuovaIstanza = false;

    @JsonProperty("specie_pratica")
    private DizionarioVO speciePratica;

    /*
    Campi relativi alla pratica
     */

    @JsonProperty("idPratica")
    private Long idPratica;

    @JsonProperty("numero_pratica")
    private String numeroPratica;

    @JsonProperty("numero_protocollo")
    private String numeroProtocollo;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_protocollo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime dataProtocollo = null;

    @JsonProperty("anno")
    private Long anno;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_registrazione_pratica")
    private LocalDateTime dataRegistrazionePratica = null;

    @JsonProperty("id_istanza_riferimento")
    private Long idIstanzaRiferimento;

    @JsonProperty("index_folder")
    private String uuidIndex;

    @JsonProperty("index_node")
    private String uuidFileIndex;

    @JsonProperty("nome_file_istanza")
    private String filename;

    @JsonProperty("dimensione_file_istanza")
    private Long dimensioneFile;

    @JsonProperty("data_caricamento_file_istanza")
    private Date dataCaricamentoFileIstanza;

    //@JsonProperty("hash_file_istanza_generato")
    //private String hashFileIstanzaGenerato;

    @JsonProperty("abilitazioni")
    private List<AbilitazioneFunzioneSlimCustomVO> abilitazioni;

    @JsonProperty("indirizzo")
    private IndirizzoVO indirizzo;

    @JsonProperty("istanza_riferimento")
    private IstanzaVO istanzaRiferimento;

    @JsonProperty("istanze_collegate")
    private List<IstanzaVO> istanzeCollegate;

    @JsonProperty("aggiorna_fascicolo_indirizzo")
    private Boolean aggiornaFascicoloIndirizzo;

    @JsonProperty("nuovo_indirizzo")
    private Boolean nuovoIndirizzo = false;

    @JsonProperty("cambio_intestatario")
    private Boolean cambioIntestatario;

    @JsonProperty("presentatore")
    private UtenteVO presentatore;

    @JsonProperty("consolida")
    private Boolean consolida;

    @JsonProperty("torna_bozza")
    private Boolean tornaBozza;

    @JsonProperty("data_inizio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataInizio;

    @JsonProperty("data_fine")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataFine;

    @JsonProperty("occupazione_suolo_pubblico")
    private Boolean occupazioneSuoloPubblico;

    @JsonProperty("pm")
    private String pm;

    @JsonProperty("pm_presente")
    private boolean pmPresente;

    @JsonProperty("pm_codice_fiscale")
    private String pmCodiceFiscale;

    @JsonProperty("guid_intestatario")
    private String guidIntestatario;

    @JsonProperty("responsabile_procedimento")
    private String responsabile_procedimento;

    @JsonProperty("tracciato_presente")
    private Boolean tracciatoXmlPresente = false;

    @JsonProperty("ente_di_riferimento")
    private EnteVO enteDiRiferimento;

    @JsonProperty("georiferimento_attivo")
    private boolean georiferimentoAttivo;

    @JsonProperty("ruolo_utente_bo_su_istanza")
    private String ruoloUtenteBoSuIstanza;

    private List<FunzioneUtenteVO> funzioniUtente = new ArrayList<>();

    @JsonProperty("genera_tracciato")
    private Boolean rigeneraTracciato = false;

    @JsonProperty("tracciati_istanza")
    private List<IstanzaTracciatoVO> tracciatiIstanza = new ArrayList<>();

    @JsonProperty("tipi_tracciato")
    private List<TipoTracciatoVO> tipiTracciato = new ArrayList<>();

    @JsonProperty("intestatario")
    private ContattoVO intestatario;

    @JsonProperty("stato_counters")
    private String statoCounters;

    @JsonProperty("fonte")
    private String fonte;

    @JsonProperty("keywords")
    private String keywords;

    @JsonProperty("extra_columns")
    private String extraColumns;

    @JsonProperty("idf_competenza")
    private String idfCompetenza;
    
	@JsonProperty("idf_autorizzato")
    private String idfAutorizzato;

    @JsonProperty("idf_numero_determina_esito_aut")
    private String idfNumeroDeterminaEsitoAut;
    
    @JsonProperty("idf_data_scadenza_autorizzazione")
    private String idfDataScadenzaAutorizzazione;
    
    public String getIdfDataScadenzaAutorizzazione() {
		return idfDataScadenzaAutorizzazione;
	}

	public void setIdfDataScadenzaAutorizzazione(String idfDataScadenzaAutorizzazione) {
		this.idfDataScadenzaAutorizzazione = idfDataScadenzaAutorizzazione;
	}

	@JsonProperty("idf_index_node")
    private String idfIndexNode;
    
    @JsonProperty("idf_numero_protocollo")
    private String idfNumeroProtocollo;
    
    @JsonProperty("idf_data_protocollo")
    private String idfDataProtocollo;
    
	public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public SelectVO getTipologiaPresentatore() {
        return tipologiaPresentatore;
    }

    public void setTipologiaPresentatore(SelectVO tipologiaPresentatore) {
        this.tipologiaPresentatore = tipologiaPresentatore;
    }

    public ComuneVO getComune() {
        return comune;
    }

    public void setComune(ComuneVO comune) {
        this.comune = comune;
    }

    public SelectVO getTipologiaIstanza() {
        return tipologiaIstanza;
    }

    public void setTipologiaIstanza(SelectVO tipologiaIstanza) {
        this.tipologiaIstanza = tipologiaIstanza;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime datacreazione) {
        this.dataCreazione = datacreazione;
    }

    public SelectVO getTitoloSoggettoAbilitato() {
        return titoloSoggettoAbilitato;
    }

    public void setTitoloSoggettoAbilitato(SelectVO titoloSoggettoAbilitato) {
        this.titoloSoggettoAbilitato = titoloSoggettoAbilitato;
    }

    /*
    public List<SoggettoIstanzaVO> getSoggettoList() {
        return soggettoList;
    }

    public void setSoggettoList(List<SoggettoIstanzaVO> soggettoList) {
        this.soggettoList = soggettoList;
    }
    */

    public ProvinciaVO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaVO provincia) {
        this.provincia = provincia;
    }

    /*
    public StepIstanza getStep() {
        return step;
    }

    public void setStep(StepIstanza step) {
        this.step = step;
    }
    */

    public DizionarioVO getStatoIstanza() {
        return statoIstanza;
    }

    public void setStatoIstanza(DizionarioVO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
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

    public Long getIdFascicolo() {
        return idFascicolo;
    }

    public void setIdFascicolo(Long idFascicolo) {
        this.idFascicolo = idFascicolo;
    }

    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    public void setCodiceIstanza(String codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroPratica() {
        return numeroPratica;
    }

    public void setNumeroPratica(String numeroPratica) {
        this.numeroPratica = numeroPratica;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Long getAnno() {
        return anno;
    }

    public void setAnno(Long anno) {
        this.anno = anno;
    }

    public LocalDateTime getDataRegistrazionePratica() {
        return dataRegistrazionePratica;
    }

    public void setDataRegistrazionePratica(LocalDateTime dataRegistrazionePratica) {
        this.dataRegistrazionePratica = dataRegistrazionePratica;
    }

    public Long getIdIstanzaRiferimento() {
        return idIstanzaRiferimento;
    }

    public void setIdIstanzaRiferimento(Long idIstanzaRiferimento) {
        this.idIstanzaRiferimento = idIstanzaRiferimento;
    }

    public boolean isNuovaIstanza() {
        return nuovaIstanza;
    }

    public void setNuovaIstanza(boolean nuovaIstanza) {
        this.nuovaIstanza = nuovaIstanza;
    }

    public FascicoloVO getFascicoloVO() {
        return fascicoloVO;
    }

    public void setFascicoloVO(FascicoloVO fascicoloVO) {
        this.fascicoloVO = fascicoloVO;
    }

    public String getUuidIndex() {
        return uuidIndex;
    }

    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
    }

    public Long getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getUuidFileIndex() {
        return uuidFileIndex;
    }

    public void setUuidFileIndex(String uuidFileIndex) {
        this.uuidFileIndex = uuidFileIndex;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getDimensioneFile() {
        return dimensioneFile;
    }

    public void setDimensioneFile(Long dimensioneFile) {
        this.dimensioneFile = dimensioneFile;
    }

    public Date getDataCaricamentoFileIstanza() {
        return dataCaricamentoFileIstanza;
    }

    public void setDataCaricamentoFileIstanza(Date dataCaricamentoFileIstanza) {
        this.dataCaricamentoFileIstanza = dataCaricamentoFileIstanza;
    }

    /*
    public String getHashFileIstanzaGenerato() {
        return hashFileIstanzaGenerato;
    }

    public void setHashFileIstanzaGenerato(String hashFileIstanzaGenerato) {
        this.hashFileIstanzaGenerato = hashFileIstanzaGenerato;
    }
    */

    public List<AbilitazioneFunzioneSlimCustomVO> getAbilitazioni() {
        return abilitazioni;
    }

    public void setAbilitazioni(List<AbilitazioneFunzioneSlimCustomVO> abilitazioni) {
        this.abilitazioni = abilitazioni;
    }

    public IndirizzoVO getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(IndirizzoVO indirizzo) {
        this.indirizzo = indirizzo;
    }

    public boolean isAssegnaAbilitazioni() {
        return assegnaAbilitazioni;
    }

    public void setAssegnaAbilitazioni(boolean assegnaAbilitazioni) {
        this.assegnaAbilitazioni = assegnaAbilitazioni;
    }

    /**
	 * @return the dataProtocollo
	 */
	public LocalDateTime getDataProtocollo() {
		return dataProtocollo;
	}

	/**
	 * @param dataProtocollo the dataProtocollo to set
	 */
	public void setDataProtocollo(LocalDateTime dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public IstanzaVO getIstanzaRiferimento() {
        return istanzaRiferimento;
    }

    public void setIstanzaRiferimento(IstanzaVO istanzaRiferimento) {
        this.istanzaRiferimento = istanzaRiferimento;
    }

    public Boolean getAggiornaFascicoloIndirizzo() {
        return aggiornaFascicoloIndirizzo;
    }

    public void setAggiornaFascicoloIndirizzo(Boolean aggiornaFascicoloIndirizzo) {
        this.aggiornaFascicoloIndirizzo = aggiornaFascicoloIndirizzo;
    }

    public Boolean getNuovoIndirizzo() {
        return nuovoIndirizzo;
    }

    public void setNuovoIndirizzo(Boolean nuovoIndirizzo) {
        this.nuovoIndirizzo = nuovoIndirizzo;
    }

    public Boolean getCambioIntestatario() {
        return cambioIntestatario;
    }

    public void setCambioIntestatario(Boolean cambioIntestatario) {
        this.cambioIntestatario = cambioIntestatario;
    }

    public UtenteVO getPresentatore() {
        return presentatore;
    }

    public void setPresentatore(UtenteVO presentatore) {
        this.presentatore = presentatore;
    }

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

    public Boolean getConsolida() {
        return consolida;
    }

    public void setConsolida(Boolean consolida) {
        this.consolida = consolida;
    }

    public Boolean getTornaBozza() {
        return tornaBozza;
    }

    public void setTornaBozza(Boolean tornaBozza) {
        this.tornaBozza = tornaBozza;
    }

    public Boolean getOccupazioneSuoloPubblico() {
        return occupazioneSuoloPubblico;
    }

    public void setOccupazioneSuoloPubblico(Boolean occupazioneSuoloPubblico) {
        this.occupazioneSuoloPubblico = occupazioneSuoloPubblico;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public Boolean getTracciatoXmlPresente() {
        return tracciatoXmlPresente;
    }

    public void setTracciatoXmlPresente(Boolean tracciatoXmlPresente) {
        this.tracciatoXmlPresente = tracciatoXmlPresente;
    }

	public List<SelectVO> getProprieta() {
		return proprieta;
	}

	public void setProprieta(List<SelectVO> proprieta) {
		this.proprieta = proprieta;
	}

	public String getResponsabile_procedimento() {
		return responsabile_procedimento;
	}

	public void setResponsabile_procedimento(String responsabile_procedimento) {
		this.responsabile_procedimento = responsabile_procedimento;
	}

	public Long getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getPmCodiceFiscale() {
		return pmCodiceFiscale;
	}

	public void setPmCodiceFiscale(String pmCodiceFiscale) {
		this.pmCodiceFiscale = pmCodiceFiscale;
	}

	public EnteVO getEnteDiRiferimento() {
		return enteDiRiferimento;
	}

	public void setEnteDiRiferimento(EnteVO enteDiRiferimento) {
		this.enteDiRiferimento = enteDiRiferimento;
	}

	public boolean isPagamentoAttivo() {
		return pagamentoAttivo;
	}

	public void setPagamentoAttivo(boolean pagamentoAttivo) {
		this.pagamentoAttivo = pagamentoAttivo;
	}

	public boolean isGeoriferimentoAttivo() {
		return georiferimentoAttivo;
	}

	public void setGeoriferimentoAttivo(boolean georiferimentoAttivo) {
		this.georiferimentoAttivo = georiferimentoAttivo;
	}

	public String getGuidIntestatario() {
		return guidIntestatario;
	}

	public void setGuidIntestatario(String guidIntestatario) {
		this.guidIntestatario = guidIntestatario;
	}

	public String getRuoloUtenteBoSuIstanza() {
		return ruoloUtenteBoSuIstanza;
	}

	public void setRuoloUtenteBoSuIstanza(String ruoloUtenteBoSuIstanza) {
		this.ruoloUtenteBoSuIstanza = ruoloUtenteBoSuIstanza;
	}

    public Boolean getRigeneraTracciato() {
        return rigeneraTracciato;
    }

    public void setRigeneraTracciato(Boolean rigeneraTracciato) {
        this.rigeneraTracciato = rigeneraTracciato;
    }
	public LocalDateTime getDataStato() {
		return dataStato;
	}

	public void setDataStato(LocalDateTime dataStato) {
		this.dataStato = dataStato;
	}

	public List<FunzioneUtenteVO> getFunzioniUtente() {
		return funzioniUtente;
	}

	public void setFunzioniUtente(List<FunzioneUtenteVO> funzioniUtente) {
		this.funzioniUtente = funzioniUtente;
	}

	public LocalDateTime getDataStatoDPS() {
		return dataStatoDPS;
	}

	public void setDataStatoDPS(LocalDateTime dataStatoDPS) {
		this.dataStatoDPS = dataStatoDPS;
	}

	public List<IstanzaVO> getIstanzeCollegate() {
		return istanzeCollegate;
	}

	public void setIstanzeCollegate(List<IstanzaVO> istanzeCollegate) {
		this.istanzeCollegate = istanzeCollegate;
	}

	public DizionarioVO getSpeciePratica() {
		return speciePratica;
	}

	public void setSpeciePratica(DizionarioVO speciePratica) {
		this.speciePratica = speciePratica;
	}

	public List<IstanzaTracciatoVO> getTracciatiIstanza() {
		return tracciatiIstanza;
	}

	public void setTracciatiIstanza(List<IstanzaTracciatoVO> tracciatiIstanza) {
		this.tracciatiIstanza = tracciatiIstanza;
	}

	public List<TipoTracciatoVO> getTipiTracciato() {
		return tipiTracciato;
	}

	public void setTipiTracciato(List<TipoTracciatoVO> tipiTracciato) {
		this.tipiTracciato = tipiTracciato;
	}

	public ContattoVO getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(ContattoVO intestatario) {
		this.intestatario = intestatario;
	}

	public String getStatoCounters() {
		return statoCounters;
	}

	public void setStatoCounters(String statoCounters) {
		this.statoCounters = statoCounters;
	}

	public TipoIstanzaVO getTipoIstanza() {
		return tipoIstanza;
	}

	public void setTipoIstanza(TipoIstanzaVO tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public boolean isPmPresente() {
		return pmPresente;
	}

	public void setPmPresente(boolean pmPresente) {
		this.pmPresente = pmPresente;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getExtraColumns() {
		return extraColumns;
	}

	public void setExtraColumns(String extraColumns) {
		this.extraColumns = extraColumns;
	}

    public String getIdfCompetenza() {
		return idfCompetenza;
	}

	public void setIdfCompetenza(String idfCompetenza) {
		this.idfCompetenza = idfCompetenza;
	}

	public String getIdfAutorizzato() {
		return idfAutorizzato;
	}

	public void setIdfAutorizzato(String idfAutorizzato) {
		this.idfAutorizzato = idfAutorizzato;
	}

	public String getIdfNumeroDeterminaEsitoAut() {
		return idfNumeroDeterminaEsitoAut;
	}

	public void setIdfNumeroDeterminaEsitoAut(String idfNumeroDeterminaEsitoAut) {
		this.idfNumeroDeterminaEsitoAut = idfNumeroDeterminaEsitoAut;
	}

	public String getIdfIndexNode() {
		return idfIndexNode;
	}

	public void setIdfIndexNode(String idfIndexNode) {
		this.idfIndexNode = idfIndexNode;
	}

	public String getIdfNumeroProtocollo() {
		return idfNumeroProtocollo;
	}

	public void setIdfNumeroProtocollo(String idfNumeroProtocollo) {
		this.idfNumeroProtocollo = idfNumeroProtocollo;
	}

	public String getIdfDataProtocollo() {
		return idfDataProtocollo;
	}

	public void setIdfDataProtocollo(String idfDataProtocollo) {
		this.idfDataProtocollo = idfDataProtocollo;
	}

}