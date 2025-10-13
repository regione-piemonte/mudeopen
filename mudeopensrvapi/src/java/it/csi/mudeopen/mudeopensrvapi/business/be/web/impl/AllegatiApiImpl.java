/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRComuneFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTSessioneRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopensrvapi.vo.Allegato;
import it.csi.mudeopen.mudeopensrvapi.vo.AllegatoIstanzaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.mapper.AllegatiMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;

/**
 * The type Allegati api service.
 */
@Component
public class AllegatiApiImpl extends AbstractApiServiceImpl implements AllegatiApi {
	
    private static Logger logger = Logger.getLogger(AllegatiApiImpl.class.getCanonicalName());

    @Autowired
    public MudeopenTSessioneRepository mudeopenTSessioneRepository;

    @Autowired
    public MudeopenRComuneFruitoreRepository mudeopenRComuneFruitoreRepository;

    @Autowired
    private MudeopenTIstanzaService mudeopenTIstanzaService;

    @Autowired
    private AllegatiMapper allegatiMapper;

	@Override
    public Response ricercaElencoAllegati(String XRequestId, String XForwardedFor, String fruitoreID, 
    												String numeroIstanza) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);
    		
        	if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));

			long start = System.currentTimeMillis();
			Optional<MudeTIstanzaSLIM> istanza = mudeopenTIstanzaService.cercaIstanzaDPSOSuccessivoWSSlim(fruitore, numeroIstanza);
			long end = System.currentTimeMillis();
			long ricercaIstanzaDuration = end-start;
			logger.info("Ricerca Istanza Duration "+ ricercaIstanzaDuration+"ms");

			if(istanza.isPresent()) {
				long start2 = System.currentTimeMillis();
				Response response = getResponse("/allegati/id-istanza/"+istanza.get().getId()+"?scope=ws-ricerca-allegati", fruitoreID);
				long end2 = System.currentTimeMillis();
				long ricercaAllegatiDuration = end2-start2;
				logger.info("Ricerca Allegati Duration "+ ricercaAllegatiDuration+"ms");

				response.bufferEntity();
				String json = response.readEntity(String.class);
				
				if(HttpStatus.SC_OK == response.getStatus()) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<MudeTAllegatoSlim> list = objectMapper.readValue(json, new TypeReference<List<MudeTAllegatoSlim>>() {});
					
					List<AllegatoIstanzaVO> result = new ArrayList<>();
					if (list != null) {
						result = allegatiMapper.mapAllegatiSlimToAllegati(list, numeroIstanza);
					}
					
					return voToResponse(result, HttpStatus.SC_OK);
				}
				
				return errorToResponse(handleUnexpectedMessage(json));
			}
			Error error = new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] Istanza non trovata" );
			return voToResponse(error, error.getHttpStatus().value());
			
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}

    }

	
	@Override
    public Response getElencoAllegatiByNumeroIstanza(String XRequestId, String XForwardedFor, String fruitoreID,
    													String numeroIstanza) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);
    		
        	if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));

        	long start = System.currentTimeMillis();
			Optional<MudeTIstanzaSLIM> istanza = mudeopenTIstanzaService.cercaIstanzaDPSOSuccessivoWSSlim(fruitore, numeroIstanza);
			long end = System.currentTimeMillis();
			long ricercaIstanzaDuration = end-start;
			logger.info("Ricerca Istanza Duration "+ ricercaIstanzaDuration+"ms");

			if(istanza.isPresent()) {
				long start2 = System.currentTimeMillis();
				Response response = getResponse("/allegati/id-istanza/"+istanza.get().getId()+"?scope=ws-ricerca-allegati", fruitoreID);
				long end2 = System.currentTimeMillis();
				long ricercaAllegatiDuration = end2-start2;
				logger.info("Ricerca Allegati Duration "+ ricercaAllegatiDuration+"ms");

				response.bufferEntity();
				String json = response.readEntity(String.class);
				
				if(HttpStatus.SC_OK == response.getStatus()) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<MudeTAllegatoSlim> list = objectMapper.readValue(json, new TypeReference<List<MudeTAllegatoSlim>>() {});
					
					List<Allegato> result = new ArrayList<>();
					if (list != null) {
						result = allegatiMapper.mapAllegatiSlimToAllegato(list);
					}
					
					return voToResponse(result, HttpStatus.SC_OK);
				}
				
				return errorToResponse(handleUnexpectedMessage(json));
			}
			Error error = new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] Istanza non trovata" );
			return voToResponse(error, error.getHttpStatus().value());
			
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}

    }

}