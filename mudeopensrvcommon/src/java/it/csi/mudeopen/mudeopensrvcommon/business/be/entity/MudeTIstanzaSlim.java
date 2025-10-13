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
public class MudeTIstanzaSlim extends BaseEntity {

    @Id
    @GenericGenerator(name = "IstanzaIdentity", strategy = "it.csi.mudeopen.mudeopensrvcommon.business.be.entity.identity.IstanzaIdentity")
    @GeneratedValue(generator = "IstanzaIdentity")
    @Column(name = "id_istanza", unique = true, nullable = false)
    private Long idIstanza;

    @Column(name = "id_tipo_istanza")
    private String idTipoIstanza;

    @Column(name = "data_ultimo_stato")
    private Timestamp dataUltimoStato;

    @Column(name = "id_comune")
    private Long idComune;

    @Column(name = "id_soggetto_rappresentato")
    private Long idSoggettoRappresentato;

    @Column(name = "creatore")
    private String creatore;

    @Column(name = "json_data", columnDefinition = "jsonb")
    private String jsonData;

    @Column(name = "codice_istanza")
    private String codiceIstanza;

    @Column(name = "id_fascicolo")
    private Long idFascicolo;

    @Column(name = "numero_protocollo")
    private String numeroProtocollo;
    
    /*
    @Column(name = "numero_pratica")
    private String numeroPratica;

    @Column(name="anno")
    private Integer anno;
    */

    @Column(name = "id_istanza_riferimento")
    private Long idIstanzaRiferimento;

    @Column(name = "data_registrazione_pratica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistrazionePratica;

    @Column(name = "flg_principale")
    private Boolean flgPrincipale;

    @Column(name = "id_template")
    private Long idTemplate;

    @Column(name = "index_folder")
    private String indexFolder;

    @Column(name = "index_node")
    private String indexNode;

    @Column(name = "nome_file_istanza")
    private String nomeFileIstanza;

    @Column(name = "dimensione_file_istanza")
    private Long dimensioneFileIstanza;

    @Column(name = "data_caricamento_file_istanza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCaricamentoFileIstanza;

    @Column(name = "id_indirizzo")
    private Long idIndirizzo;

    @Column(name = "id_specie_pratica")
    private String idSpeciePratica;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "rigenera_tracciato")
    private Boolean rigeneraTracciato;

    @Column(name = "data_protocollo")
    private Date dataProtocollo;

    @Column(name = "responsabile_procedimento")
    private String responsabileProcedimento;

    @Column(name = "id_fonte")
    private String idFonte;

    @Column(name = "data_dps")
    private Timestamp dataDps;

    @Column(name = "id_fruitore")
    private Long idFruitore;

    @Column(name = "keywords")
    private String keywords;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(final Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getIdTipoIstanza() {
        return idTipoIstanza;
    }

    public void setIdTipoIstanza(final String idTipoIstanza) {
        this.idTipoIstanza = idTipoIstanza;
    }

    public Timestamp getDataUltimoStato() {
        return dataUltimoStato;
    }

    public void setDataUltimoStato(final Timestamp dataUltimoStato) {
        this.dataUltimoStato = dataUltimoStato;
    }

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(final Long idComune) {
        this.idComune = idComune;
    }

    public Long getIdSoggettoRappresentato() {
        return idSoggettoRappresentato;
    }

    public void setIdSoggettoRappresentato(final Long idSoggettoRappresentato) {
        this.idSoggettoRappresentato = idSoggettoRappresentato;
    }

    public String getCreatore() {
        return creatore;
    }

    public void setCreatore(final String creatore) {
        this.creatore = creatore;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(final String jsonData) {
        this.jsonData = jsonData;
    }

    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    public void setCodiceIstanza(final String codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    public Long getIdFascicolo() {
        return idFascicolo;
    }

    public void setIdFascicolo(final Long idFascicolo) {
        this.idFascicolo = idFascicolo;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(final String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Long getIdIstanzaRiferimento() {
        return idIstanzaRiferimento;
    }

    public void setIdIstanzaRiferimento(final Long idIstanzaRiferimento) {
        this.idIstanzaRiferimento = idIstanzaRiferimento;
    }

    public Date getDataRegistrazionePratica() {
        return dataRegistrazionePratica;
    }

    public void setDataRegistrazionePratica(final Date dataRegistrazionePratica) {
        this.dataRegistrazionePratica = dataRegistrazionePratica;
    }

    public Boolean getFlgPrincipale() {
        return flgPrincipale;
    }

    public void setFlgPrincipale(final Boolean flgPrincipale) {
        this.flgPrincipale = flgPrincipale;
    }

    public Long getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(final Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getIndexFolder() {
        return indexFolder;
    }

    public void setIndexFolder(final String indexFolder) {
        this.indexFolder = indexFolder;
    }

    public String getIndexNode() {
        return indexNode;
    }

    public void setIndexNode(final String indexNode) {
        this.indexNode = indexNode;
    }

    public String getNomeFileIstanza() {
        return nomeFileIstanza;
    }

    public void setNomeFileIstanza(final String nomeFileIstanza) {
        this.nomeFileIstanza = nomeFileIstanza;
    }

    public Long getDimensioneFileIstanza() {
        return dimensioneFileIstanza;
    }

    public void setDimensioneFileIstanza(final Long dimensioneFileIstanza) {
        this.dimensioneFileIstanza = dimensioneFileIstanza;
    }

    public Date getDataCaricamentoFileIstanza() {
        return dataCaricamentoFileIstanza;
    }

    public void setDataCaricamentoFileIstanza(final Date dataCaricamentoFileIstanza) {
        this.dataCaricamentoFileIstanza = dataCaricamentoFileIstanza;
    }

    public Long getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(final Long idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }

    public String getIdSpeciePratica() {
        return idSpeciePratica;
    }

    public void setIdSpeciePratica(final String idSpeciePratica) {
        this.idSpeciePratica = idSpeciePratica;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(final Integer branch) {
        this.branch = branch;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(final Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getRigeneraTracciato() {
        return rigeneraTracciato;
    }

    public void setRigeneraTracciato(final Boolean rigeneraTracciato) {
        this.rigeneraTracciato = rigeneraTracciato;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(final Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(final String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }

    public String getIdFonte() {
        return idFonte;
    }

    public void setIdFonte(final String idFonte) {
        this.idFonte = idFonte;
    }

    public Timestamp getDataDps() {
        return dataDps;
    }

    public void setDataDps(final Timestamp dataDps) {
        this.dataDps = dataDps;
    }

    public Long getIdFruitore() {
        return idFruitore;
    }

    public void setIdFruitore(final Long idFruitore) {
        this.idFruitore = idFruitore;
    }

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
    
}
