/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvapi.vo.VisualizzaDatiProtocollazioneIstanzaVo;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoUbicazione;
import it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza.IstanzaExtVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

public interface MudeopenTIstanzaService {

	//Optional<Error> verificaFruitore(MudeopenTSessione sessione, String numeroIstanza);
	
	Optional<VisualizzaDatiProtocollazioneIstanzaVo> visualizzaDatiProtocollazione(MudeTIstanzaSLIM istanzaSlim) throws IllegalArgumentException;
	
	MudeTIstanza findByNumeroIstanza(String numeroIstanza);
	
	//Response ricercaPaginataIstanzeResponse(MudeDFruitore fruitore, FiltroRicercaPaginataVO filtroRicercaPaginata,int page, int size) throws JsonProcessingException, UnsupportedEncodingException;
	
	//Response ricercaPaginataIstanzeResponse(String fruitoreID, String filtro, int page ,int size) throws UnsupportedEncodingException;
	
	String getNewNumeroMude(MudeDComune mudeDComune, MudeDFruitore fruitore, boolean createAlsoFolder);
	
	IstanzaVO ricercaIstanzaDPSOSuccessivo(MudeDFruitore fruitore, String numeroIstanza) throws Exception;
	
	Optional<IstanzaVO> ricercaIstanza(MudeDFruitore fruitore, String numeroIstanza) throws JsonProcessingException, UnsupportedEncodingException;
	
	MudeTIstanza salvaIstanzaRequest(MudeDFruitore fruitore, IstanzaExtVO salvaIstanzaRequest) throws Exception;
	
	List<GeoRiferimento> estraiElencoGeoRiferimento(MudeTIstanzaSLIM istanza, String fruitoreID);
	
	List<GeoUbicazione> estraiElencoUbicazione(IstanzaVO istanzaVO, String fruitoreID);
	
	Response ricercaPaginataIstanze03Slim(String filter, String fruitore, String responseFilters, int page, int size);

	Optional<MudeTIstanzaSLIM> cercaIstanzaDPSOSuccessivoWSSlim(MudeDFruitore fruitore, String numeroIstanza) throws Exception;
	Optional<MudeTIstanzaSLIM> cercaIstanzaWSSlim(MudeDFruitore fruitore, String numeroIstanza) throws Exception;
	Optional<MudeTIstanzaSLIM> cercaIstanzaAPAWSSlim(MudeDFruitore fruitore, String numeroIstanza) throws Exception;
}
