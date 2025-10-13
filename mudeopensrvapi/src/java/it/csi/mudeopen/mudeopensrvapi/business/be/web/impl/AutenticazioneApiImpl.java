/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTSessioneService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.AutenticazioneApi;
import it.csi.mudeopen.mudeopensrvapi.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;

/**
 * The type Autenticazione api service.
 */
@Component
public class AutenticazioneApiImpl extends AbstractApiServiceImpl implements AutenticazioneApi {
	
	@Override
	public Response autenticazioneMUDE(String fruitoreID) {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

        	MudeopenTSessione sessione = saveSessione(fruitoreID);
        	UUID token = sessione.getIdSessione();
        	String str = token.toString();
        	
            return Response.ok(str).status(HttpStatus.SC_OK).header(HttpHeaders.CONTENT_ENCODING, it.csi.mudeopen.mudeopensrvcommon.util.Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, "" + str.getBytes("UTF-8").length).build();
        }
        catch(Throwable t) {
        	try {
    			return errorToResponse(handleUnexpectedError(t));
			} catch (Exception e) {
	            throw new RemoteException();
			}
        }
	}

	private MudeopenTSessione saveSessione(String codiceFruitore) {
		MudeopenTSessione mudeopenTSessione = new MudeopenTSessione();
    	mudeopenTSessione.setFruitore(codiceFruitore);
    	mudeopenTSessione.setDataCreazione(new Date());
    	mudeopenTSessione.setIdSessione(UUID.randomUUID());
    	MudeopenTSessione sessione = mudeopenTSessioneService.save(mudeopenTSessione);
    	return sessione;
	}
}