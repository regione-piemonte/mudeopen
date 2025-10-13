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

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDRuoloUtenteService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.RuoliUtenteApi;
import it.csi.mudeopen.mudeopensrvapi.vo.RuoloVO;
import it.csi.mudeopen.mudeopensrvapi.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;

/**
 * The type RuoliUtente api service.
 */
@Component
public class RuoliUtenteApiImpl  extends AbstractApiServiceImpl implements RuoliUtenteApi {
    private static Logger logger = Logger.getLogger(RuoliUtenteApiImpl.class.getCanonicalName());

    @Autowired
    private MudeopenDRuoloUtenteService mudeopenDRuoloUtenteService;

	@Override
	public Response ricercaRuoli(String XRequestId, String XForwardedFor, String fruitoreID) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			List<RuoloVO> list = mudeopenDRuoloUtenteService.findByIdFunzione(fruitoreID);
			return voToResponse(list, HttpStatus.SC_OK);
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}
}