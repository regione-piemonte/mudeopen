/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
@Entity
@Table(name = "mudeopen_t_istanza")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MudeTIstanza extends BaseEntity implements LoggableEntity {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id_istanza")
	@Id
	@GenericGenerator(name = "IstanzaIdentity", strategy = "it.csi.mudeopen.mudeopensrvcommon.business.be.entity.identity.IstanzaIdentity")
	@GeneratedValue(generator = "IstanzaIdentity")
	@Column(name = "id_istanza", unique = true, nullable = false)	
    private Long id;

    @Column(name = "data_ultimo_stato")
    private Timestamp dataCreazione;

    @Column(name = "codice_istanza")
    private String codiceIstanza;

    @ManyToOne
    @JoinColumn(name = "id_tipo_istanza")
    private MudeDTipoIstanza tipoIstanza;

    @Column(name = "id_soggetto_rappresentato")
    private Long idSoggettoRappresentato;

    //@OneToMany(mappedBy = "mudeTIstanza", cascade = CascadeType.ALL)
    //private List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettos;
    @OneToMany(mappedBy = "mudeTIstanza", cascade = CascadeType.ALL)
    private List<MudeTDatiIstanza> mudeTDatiIstanzas;

    @ManyToOne
    @JoinColumn(name = "id_fascicolo")
    private MudeTFascicolo mudeTFascicolo;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "mudeopen_r_istanza_pratica", 
        joinColumns = { @JoinColumn(name = "id_istanza") }, 
        inverseJoinColumns = { @JoinColumn(name = "id_pratica") }
    )
    List<MudeTPratica> pratiche = new ArrayList<MudeTPratica>();
    @ManyToOne
    @JoinColumn(name = "id_comune")
    private MudeDComune comune;

    @Type(type = "jsonb")
    @Column(name = "json_data", columnDefinition = "jsonb")
    private String jsonData;

    @ManyToOne
    @JoinColumn(name = "id_template")
    private MudeDTemplate template;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "creatore")
    private String creatore;

    /* Campi relativi alla pratica
    private Long anno;
    
    @Column(name = "numero_pratica")
    private String numeroPratica;
    */

    @Column(name = "numero_protocollo")
    private String numeroProtocollo;

    @Column(name = "data_protocollo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProtocollo;

    @Column(name = "data_registrazione_pratica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistrazionePratica;

    @Column(name = "id_istanza_riferimento")
    private Long idIstanzaRiferimento;

    /*
        Campi relativi a Index
     */
    // uuid della folder su index
    @Column(name = "index_folder")
    private String uuidIndex;

    // uuid del file firmato su index
    @Column(name = "index_node")
    private String uuidFileIndex;

    @Column(name = "nome_file_istanza")
    private String filename;

    @Column(name = "dimensione_file_istanza")
    private Long dimensioneFile;

    @Column(name = "data_caricamento_file_istanza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCaricamentoFileIstanza;

    @ManyToOne
    @JoinColumn(name = "id_specie_pratica")
    private MudeDSpeciePratica speciePratica;

    @ManyToOne
    @JoinColumn(name = "id_indirizzo")
    private MudeTIndirizzo indirizzo;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private MudeTUser mudeTUser;

    @Column(name = "rigenera_tracciato")
    private Boolean rigeneraTracciato = false;

    @Column(name = "responsabile_procedimento")
    private String responsabileProcedimento;

    @Column(name = "data_dps")
    private Timestamp dataDps;

    @Column(name = "id_fonte")
    private String idFonte = "FO";

    @Column(name = "formula_col", insertable = false, updatable = false)  
    @ColumnTransformer(read = "REPLACE("  
    		+ "  COALESCE("
    		+ "    NULLIF((json_data->'TAB_QUALIF_1'->'data_avvio_lavori')::varchar, 'null'),"
    		+ "    NULLIF((json_data->'TAB_FIL_COMUNIC'->'data_fine_lavori')::varchar, 'null'),"
    		+ "    NULLIF((json_data->'TAB_FILPDC_COMUNICAZ'->'data_fine_lavori')::varchar, 'null'),"
    		+ "    NULLIF((json_data->'TAB_FILCILA_COMUNIC'->'data_fine_lavori')::varchar, 'null'),"
    		+ "    NULL),"
    		+ "	'\"','')")    
    private String annoIntervento;

    @Column(name = "id_fruitore")
    private Long idFruitore;

    @Column(name = "keywords")
    private String keywords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeDTipoIstanza getTipoIstanza() {
        return tipoIstanza;
    }

    public void setTipoIstanza(MudeDTipoIstanza tipoIstanza) {
        this.tipoIstanza = tipoIstanza;
    }

    public Timestamp getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    /*
    public List<MudeRIstanzaSoggetto> getMudeRIstanzaSoggettos() {
        return mudeRIstanzaSoggettos;
    }

    public void setMudeRIstanzaSoggettos(List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettos) {
        this.mudeRIstanzaSoggettos = mudeRIstanzaSoggettos;
    }
    */
    public List<MudeTDatiIstanza> getMudeTDatiIstanzas() {
        return mudeTDatiIstanzas;
    }

    public void setMudeTDatiIstanzas(List<MudeTDatiIstanza> mudeTDatiIstanzas) {
        this.mudeTDatiIstanzas = mudeTDatiIstanzas;
    }

    public MudeDComune getComune() {
        return comune;
    }

    public void setComune(MudeDComune comune) {
        this.comune = comune;
    }

    public Long getIdSoggettoRappresentato() {
        return idSoggettoRappresentato;
    }

    public void setIdSoggettoRappresentato(Long idSoggettoRappresentato) {
        this.idSoggettoRappresentato = idSoggettoRappresentato;
    }

    /*
    public StepIstanza getStep() {
        return step;
    }

    public void setStep(StepIstanza step) {
        // modifico lo step solo se id dello step passato e' > dello step attuale, in modo da tenere traccia dello step in cui sono arrivato
        if (this.step == null || (step.getId() > this.step.getId())) {
            this.step = step;
        }
    }
    */
    public MudeTFascicolo getMudeTFascicolo() {
        return mudeTFascicolo;
    }

    public void setMudeTFascicolo(MudeTFascicolo mudeTFascicolo) {
        this.mudeTFascicolo = mudeTFascicolo;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    public void setCodiceIstanza(String codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    @Override
    public String getTableName() {
        return "mudeopen_t_istanza";
    }

/*
    public String getNumeroPratica() {
        return numeroPratica;
    }

    public void setNumeroPratica(String numeroPratica) {
        this.numeroPratica = numeroPratica;
    }*/
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    /*public Long getAnno() {
        return anno;
    }

    public void setAnno(Long anno) {
        this.anno = anno;
    }*/
    public Date getDataRegistrazionePratica() {
        return dataRegistrazionePratica;
    }

    public void setDataRegistrazionePratica(Date dataRegistrazionePratica) {
        this.dataRegistrazionePratica = dataRegistrazionePratica;
    }

    public Long getIdIstanzaRiferimento() {
        return idIstanzaRiferimento;
    }

    public void setIdIstanzaRiferimento(Long idIstanzaRiferimento) {
        this.idIstanzaRiferimento = idIstanzaRiferimento;
    }

    public MudeDTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MudeDTemplate template) {
        this.template = template;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getUuidIndex() {
        return uuidIndex;
    }

    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
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

    public MudeDSpeciePratica getSpeciePratica() {
        return speciePratica;
    }

    public void setSpeciePratica(MudeDSpeciePratica speciePratica) {
        this.speciePratica = speciePratica;
    }

    public MudeTIndirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(MudeTIndirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

	public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

	public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}

	public String getAnnoIntervento() {
		return annoIntervento;
	}

	public void setAnnoIntervento(String annoIntervento) {
		this.annoIntervento = annoIntervento;
	}

	public List<MudeTPratica> getPratiche() {
		return pratiche;
	}

	public void setPratiche(List<MudeTPratica> pratiche) {
		this.pratiche = pratiche;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
    //    public byte[] getPdfcache() {
//        return pdfcache;
//    }
//
//    public void setPdfcache(byte[] pdfcache) {
//        this.pdfcache = pdfcache;
//    }
    public Boolean getRigeneraTracciato() {
        return rigeneraTracciato;
    }

    public void setRigeneraTracciato(Boolean rigeneraTracciato) {
        this.rigeneraTracciato = rigeneraTracciato;
    }

	/**
	 * @return the responsabileProcedimento
	 */
	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}

	/**
	 * @param responsabileProcedimento the responsabileProcedimento to set
	 */
	public void setResponsabileProcedimento(String responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}

	/**
	 * @return the dataDps
	 */
	public Timestamp getDataDps() {
		return dataDps;
	}

	/**
	 * @param dataDps the dataDps to set
	 */
	public void setDataDps(Timestamp dataDps) {
		this.dataDps = dataDps;
	}

	public String getIdFonte() {
		return idFonte;
	}

	public void setIdFonte(String idFonte) {
		this.idFonte = idFonte;
	}

	public String getCreatore() {
		return creatore;
	}

	public void setCreatore(String creatore) {
		this.creatore = creatore;
	}

	public Long getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(Long idFruitore) {
		this.idFruitore = idFruitore;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}