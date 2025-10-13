/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.entity;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.BaseEntity;
@Entity
@Table(name = "mudeopen_t_istanza")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MudeTIstanzaSLIM extends BaseEntity  {
	@Id
	@Column(name = "id_istanza")
    private Long id;

    @Column(name = "data_ultimo_stato")
    private Timestamp dataCreazione;

    @Column(name = "codice_istanza")
    private String codiceIstanza;

    @Column(name = "id_tipo_istanza")
    private String idTipoIstanza;

    @Column(name = "id_soggetto_rappresentato")
    private Long idSoggettoRappresentato;

    @Column(name = "id_fascicolo")
    private Long idFascicolo;

    @Column(name = "id_comune")
    private Long idComune;

    @Column(name = "id_template")
    private Long idTemplate;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "creatore")
    private String creatore;

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

    @Column(name = "index_folder")
    private String uuidIndex;

    @Column(name = "index_node")
    private String uuidFileIndex;

    @Column(name = "nome_file_istanza")
    private String filename;

    @Column(name = "dimensione_file_istanza")
    private Long dimensioneFile;

    @Column(name = "data_caricamento_file_istanza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCaricamentoFileIstanza;

    @Column(name = "id_specie_pratica")
    private String idSpeciePratica;

    @Column(name = "id_indirizzo")
    private Long idIndirizzo;

    @Column(name = "id_user")
    private Long idUser;

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

    @Column(name = "formula_opera_col", insertable = false, updatable = false)  
    @ColumnTransformer(read = "COALESCE("
    					+ "      NULLIF((json_data->'TAB_QUALIF_1'->'opere_in_precario_su_suolo_pubblico'), 'null'),"
			    		+ "      NULLIF((json_data->'TAB_IND_PROC'->'opere_in_precario_su_suolo_pubblico'), 'null'), "
			    		+ "      'false')::boolean")    
    private boolean opereInPrecario;

    @Column(name = "id_fruitore")
    private Long idFruitore;

    @Type(type = "jsonb")
    @Column(name = "json_data", columnDefinition = "jsonb")
    private String jsonData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getCodiceIstanza() {
		return codiceIstanza;
	}

	public void setCodiceIstanza(String codiceIstanza) {
		this.codiceIstanza = codiceIstanza;
	}

	public Long getIdSoggettoRappresentato() {
		return idSoggettoRappresentato;
	}

	public void setIdSoggettoRappresentato(Long idSoggettoRappresentato) {
		this.idSoggettoRappresentato = idSoggettoRappresentato;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
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

	public String getCreatore() {
		return creatore;
	}

	public void setCreatore(String creatore) {
		this.creatore = creatore;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

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

	public String getIdSpeciePratica() {
		return idSpeciePratica;
	}

	public void setIdSpeciePratica(String idSpeciePratica) {
		this.idSpeciePratica = idSpeciePratica;
	}

	public Long getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Boolean getRigeneraTracciato() {
		return rigeneraTracciato;
	}

	public void setRigeneraTracciato(Boolean rigeneraTracciato) {
		this.rigeneraTracciato = rigeneraTracciato;
	}

	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}

	public void setResponsabileProcedimento(String responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}

	public Timestamp getDataDps() {
		return dataDps;
	}

	public void setDataDps(Timestamp dataDps) {
		this.dataDps = dataDps;
	}

	public String getIdFonte() {
		return idFonte;
	}

	public void setIdFonte(String idFonte) {
		this.idFonte = idFonte;
	}

	public String getAnnoIntervento() {
		return annoIntervento;
	}

	public void setAnnoIntervento(String annoIntervento) {
		this.annoIntervento = annoIntervento;
	}

	public Long getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(Long idFruitore) {
		this.idFruitore = idFruitore;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getIdTipoIstanza() {
		return idTipoIstanza;
	}

	public void setIdTipoIstanza(String idTipoIstanza) {
		this.idTipoIstanza = idTipoIstanza;
	}

	public boolean isOpereInPrecario() {
		return opereInPrecario;
	}

	public void setOpereInPrecario(boolean opereInPrecario) {
		this.opereInPrecario = opereInPrecario;
	}
}