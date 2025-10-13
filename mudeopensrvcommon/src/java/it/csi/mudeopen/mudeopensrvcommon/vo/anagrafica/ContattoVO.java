/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ContattoVO {
	@JsonIgnore
	private static final long serialVersionUID = -8203307512682631117L;

	private Long id;
	private String userCf = null;
	private String nome = null;

	@JsonProperty("tipo_contatto")
	private TipoContatto tipoContatto;

	private String guid;

	private UUID uuid;

	@JsonProperty("updatable")
	private Boolean canChangeCF = Boolean.TRUE;

	@JsonProperty("id_user")
	private Long idUser;
	
	@JsonProperty("id_user_accreditato")
	private Long idUserAccreditato;
	
	@JsonProperty("contatto_in_rubrica")
	private Boolean contattoInRubrica;

	@JsonProperty("proprietario_rubrica")
	private String proprietarioRubrica;

	@JsonProperty("titolare")
	private Boolean titolare = false;

	@JsonProperty("impresa_lavori")
	private Boolean impresaLavori = false;

	@JsonProperty("professionista")
	private Boolean professionista = false;

	//ex pg/pf
	private AnagraficaVO anagrafica = new AnagraficaVO();

	private List<IndirizzoVO> indirizzi = null;

	//ex pg
	private ContattoVO legaleRappresentante;

	@JsonProperty("id_titolo_rappresentante")
	private String idTitoloRappresentante;

	//ex pf
	private List<QualificaVO> qualificheProfessionali = null;

	public ContattoVO() {}

	public ContattoVO(TipoContatto tipoContatto, String nome) {
		this.tipoContatto = tipoContatto;
		this.nome = nome;
	}

	public ContattoVO(TipoContatto tipoContatto, AnagraficaVO anagrafica, List<QualificaVO> qualificheProfessionali,
							List<IndirizzoVO> indirizzi) {
		this.tipoContatto = tipoContatto;
		this.anagrafica = anagrafica;
		this.qualificheProfessionali = qualificheProfessionali;
		this.indirizzi = indirizzi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserCf() {
		return userCf;
	}

	public void setUserCf(String userCf) {
		this.userCf = userCf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoContatto getTipoContatto() {
		return tipoContatto;
	}

	public void setTipoContatto(TipoContatto tipoContatto) {
		this.tipoContatto = tipoContatto;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Boolean getCanChangeCF() {
		return canChangeCF;
	}

	public void setCanChangeCF(Boolean canChangeCF) {
		this.canChangeCF = canChangeCF;
	}

	public Long getIdUserAccreditato() {
		return idUserAccreditato;
	}

	public void setIdUserAccreditato(Long idUserAccreditato) {
		this.idUserAccreditato = idUserAccreditato;
	}

	public Boolean getContattoInRubrica() {
		return contattoInRubrica;
	}

	public void setContattoInRubrica(Boolean contattoInRubrica) {
		this.contattoInRubrica = contattoInRubrica;
	}

	public Boolean getTitolare() {
		return titolare;
	}

	public void setTitolare(Boolean titolare) {
		this.titolare = titolare;
	}

	public Boolean getImpresaLavori() {
		return impresaLavori;
	}

	public void setImpresaLavori(Boolean impresaLavori) {
		this.impresaLavori = impresaLavori;
	}

	public Boolean getProfessionista() {
		return professionista;
	}

	public void setProfessionista(Boolean professionista) {
		this.professionista = professionista;
	}

	public AnagraficaVO getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(AnagraficaVO anagrafica) {
		this.anagrafica = anagrafica;
	}

	public List<IndirizzoVO> getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(List<IndirizzoVO> indirizzi) {
		this.indirizzi = indirizzi;
	}

	public ContattoVO getLegaleRappresentante() {
		return legaleRappresentante;
	}

	public void setLegaleRappresentante(ContattoVO legaleRappresentante) {
		this.legaleRappresentante = legaleRappresentante;
	}

	public String getIdTitoloRappresentante() {
		return idTitoloRappresentante;
	}

	public void setIdTitoloRappresentante(String idTitoloRappresentante) {
		this.idTitoloRappresentante = idTitoloRappresentante;
	}

	public List<QualificaVO> getQualificheProfessionali() {
		return qualificheProfessionali;
	}

	public void setQualificheProfessionali(List<QualificaVO> qualificheProfessionali) {
		this.qualificheProfessionali = qualificheProfessionali;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getProprietarioRubrica() {
		return proprietarioRubrica;
	}

	public void setProprietarioRubrica(String proprietarioRubrica) {
		this.proprietarioRubrica = proprietarioRubrica;
	}
}