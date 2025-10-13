/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.pratica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneSlimCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FunzioneUtenteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

public class PraticaVO extends ParentVO {
    public static final String _NEW_TEMPLATE = "_NEW_TEMPLATE";
    private static final long serialVersionUID = -3961724086179323695L;
    @JsonProperty("id_pratica")
    private Long idPratica;

    private ComuneVO comune;

    private ProvinciaVO provincia;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_creazione")
    private LocalDateTime dataCreazione = null;

    @JsonProperty("numero_pratica")
    private String numeroPratica;

    @JsonProperty("anno")
    private Long anno;

    @JsonProperty("ruolo_utente_bo_su_pratica")
    private String ruoloUtenteBoSuPratica;

    private List<FunzioneUtenteVO> funzioniUtente = new ArrayList<>();

    private EnteVO ente;

	public Long getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public ComuneVO getComune() {
		return comune;
	}

	public void setComune(ComuneVO comune) {
		this.comune = comune;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public String getRuoloUtenteBoSuPratica() {
		return ruoloUtenteBoSuPratica;
	}

	public void setRuoloUtenteBoSuPratica(String ruoloUtenteBoSuPratica) {
		this.ruoloUtenteBoSuPratica = ruoloUtenteBoSuPratica;
	}

	public List<FunzioneUtenteVO> getFunzioniUtente() {
		return funzioniUtente;
	}

	public void setFunzioniUtente(List<FunzioneUtenteVO> funzioniUtente) {
		this.funzioniUtente = funzioniUtente;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the ente
	 */
	public EnteVO getEnte() {
		return ente;
	}

	/**
	 * @param ente the ente to set
	 */
	public void setEnte(EnteVO ente) {
		this.ente = ente;
	}
}