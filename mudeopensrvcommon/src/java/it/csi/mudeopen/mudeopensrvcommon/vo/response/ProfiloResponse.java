/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

public class ProfiloResponse extends DefaultResponse {

    private static final long serialVersionUID = 7433414345994550604L;

    private boolean profiloCompleto;
    private List<SelectVO> qualificaList;
    private List<SelectVO> ordineList;
    private List<SelectVO> tipoResidenzaList;
    private List<SelectVO> dugList;
    private List<TipoIstanzaVO> tipoIstanzaList;
    private List<SelectVO> tipoDittaList;

    private List<SelectVO> tipoInterventoList;

    private List<SelectVO> statoIstanzaList;

    private List<SelectVO> statoFascicoloList;

    private UtenteVO infoUtente;

    @JsonProperty("proprieta")
    private List<SelectVO> proprieta;

    public ProfiloResponse() {
        super();
    }

    public ProfiloResponse(boolean profiloCompleto, List<SelectVO> qualificaList, List<SelectVO> ordineList, List<SelectVO> tipoResidenzaList, List<SelectVO> dugList, List<SelectVO> tipoDittaList, List<TipoIstanzaVO> tipoIstanzaList, List<SelectVO> tipoInterventoList, UtenteVO infoUtente, List<SelectVO> statoIstanzaList, List<SelectVO> statoFascicoloList,
    		List<SelectVO> proprieta) {
        super();
        this.profiloCompleto = profiloCompleto;
        this.qualificaList = qualificaList;
        this.ordineList = ordineList;
        this.tipoResidenzaList = tipoResidenzaList;
        this.dugList = dugList;
        this.tipoIstanzaList = tipoIstanzaList;
        this.infoUtente = infoUtente;
        this.tipoDittaList = tipoDittaList;
        this.tipoInterventoList = tipoInterventoList;
        this.statoIstanzaList = statoIstanzaList;
        this.statoFascicoloList = statoFascicoloList;
        this.proprieta = proprieta;
    }

    public boolean isProfiloCompleto() {
        return profiloCompleto;
    }

    public void setProfiloCompleto(boolean profiloCompleto) {
        this.profiloCompleto = profiloCompleto;
    }

    public List<SelectVO> getQualificaList() {
        return qualificaList;
    }

    public void setQualificaList(List<SelectVO> qualificaList) {
        this.qualificaList = qualificaList;
    }

    public List<SelectVO> getOrdineList() {
        return ordineList;
    }

    public void setOrdineList(List<SelectVO> ordineList) {
        this.ordineList = ordineList;
    }

    public UtenteVO getInfoUtente() {
        return infoUtente;
    }

    public void setInfoUtente(UtenteVO infoUtente) {
        this.infoUtente = infoUtente;
    }

    public List<SelectVO> getTipoResidenzaList() {
        return tipoResidenzaList;
    }

    public void setTipoResidenzaList(List<SelectVO> tipoResidenzaList) {
        this.tipoResidenzaList = tipoResidenzaList;
    }

    public List<SelectVO> getDugList() {
        return dugList;
    }

    public void setDugList(List<SelectVO> dugList) {
        this.dugList = dugList;
    }

    public List<TipoIstanzaVO> getTipoIstanzaList() {
        return tipoIstanzaList;
    }

    public void setTipoIstanzaList(List<TipoIstanzaVO> tipoIstanzaList) {
        this.tipoIstanzaList = tipoIstanzaList;
    }

    public List<SelectVO> getTipoDittaList() {
        return tipoDittaList;
    }

    public void setTipoDittaList(List<SelectVO> tipoDittaList) {
        this.tipoDittaList = tipoDittaList;
    }

    public List<SelectVO> getTipoInterventoList() {
        return tipoInterventoList;
    }

    public void setTipoInterventoList(List<SelectVO> tipoInterventoList) {
        this.tipoInterventoList = tipoInterventoList;
    }

    public List<SelectVO> getStatoIstanzaList() {
        return statoIstanzaList;
    }

    public void setStatoIstanzaList(List<SelectVO> statoIstanzaList) {
        this.statoIstanzaList = statoIstanzaList;
    }

    public List<SelectVO> getStatoFascicoloList() {
        return statoFascicoloList;
    }

    public void setStatoFascicoloList(List<SelectVO> statoFascicoloList) {
        this.statoFascicoloList = statoFascicoloList;
    }

	public List<SelectVO> getProprieta() {
		return proprieta;
	}

	public void setProprieta(List<SelectVO> proprieta) {
		this.proprieta = proprieta;
	}

}