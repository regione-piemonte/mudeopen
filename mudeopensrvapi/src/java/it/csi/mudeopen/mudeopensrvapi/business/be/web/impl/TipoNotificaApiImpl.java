/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDTipoNotificaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.TipiNotificaApi;
import it.csi.mudeopen.mudeopensrvapi.vo.TipoNotificaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;

@Component
public class TipoNotificaApiImpl extends AbstractApiServiceImpl implements TipiNotificaApi {
	 
	private static Logger logger = Logger.getLogger(TipoNotificaApiImpl.class.getCanonicalName());
	
	@Autowired
	private MudeopenDTipoNotificaService mudeopenDTipoNotificaService;
	
	@Override
	public Response ricercaTipoNotifica(String XRequestId, String XForwardedFor, String fruitoreID) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			List<TipoNotificaVO> list = mudeopenDTipoNotificaService.findAllValid();
			if(list.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[TipoNotificaInesistenteException] Nessun tipo notifica trovato"));

			return voToResponse(list, HttpStatus.SC_OK);
			
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}

	
}
