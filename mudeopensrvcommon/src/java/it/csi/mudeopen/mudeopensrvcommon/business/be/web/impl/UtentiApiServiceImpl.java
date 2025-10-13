/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.UtentiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.DefaultResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;
@Component
public class UtentiApiServiceImpl extends AbstractApi implements UtentiApi {

	@Autowired
	private UtenteService utenteService;

	@Override
	public Response getInfoProfilo(String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest,
			@QueryParam("scope") String scope) {
		LoggerUtil.debug(logger, "[UtentiApiServiceImpl::getInfoProfilo]");
		String cf = userCf;
		try {
			MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
			cf = mudeTUser.getCf();
		}catch(Throwable t) { }
		
        if(!cf.matches("^[A-Za-z]{6}[0-9LMNPQRSTUV]{2}[A-Za-z]{1}[0-9LMNPQRSTUV]{2}[A-Za-z]{1}[0-9LMNPQRSTUV]{3}[A-Za-z]{1}$"))
        	throw new BusinessException("Codice fiscale '" + cf + "' non valido. Verificare che il codice fiscale contenuto nel certificato utilizzato sia valido.");
		
		ProfiloResponse res = utenteService.getProfiloByCF(cf,scope);
		
        return Response.ok().entity(res).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(res)).build();
		
	}

	@Override
	public DefaultResponse salvaInfoProfilo(String userCf, String XRequestId, String XForwardedFor,
			UtenteVO utente, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		LoggerUtil.debug(logger, "[UtentiApiServiceImpl::salvaInfoProfilo]");
		
//		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders);
		utente.getContatto().setTipoContatto(TipoContatto.PF);		
		utenteService.salvaProfilo(utente);
		return new DefaultResponse();
	}

	@Override
	public DefaultResponse modificaInfoProfilo(String userCf, String XRequestId, String XForwardedFor,
			UtenteVO utente, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		LoggerUtil.debug(logger, "[UtentiApiServiceImpl::modificaInfoProfilo]");
		// TODO Auto-generated method stub
		
		
		return new DefaultResponse();
		
		
	}
	
	@Override
	public Response recuperaAccreditamenti(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
											String filter, String sort, int page, int size) {

		return utenteService.recuperaAccreditamenti(headerUtil.getUserCF(httpHeaders, false), filter, page, size, sort);
	}

	

}