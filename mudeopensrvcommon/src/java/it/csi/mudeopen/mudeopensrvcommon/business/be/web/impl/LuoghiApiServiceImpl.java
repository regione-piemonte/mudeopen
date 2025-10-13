/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.LuoghiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.LuoghiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.RegioneVO;

@Component
public class LuoghiApiServiceImpl extends AbstractApi implements LuoghiApi {

	@Autowired
	private LuoghiService luoghiService;

	@Override
	public Response getNazioni(String userCf, String XRequestId, String XForwardedFor) {
		LoggerUtil.debug(logger, "[LuoghiApiServiceImpl::getNazioni]");
		List<NazioneVO> vos = luoghiService.getNazioni();
		
		return Response.ok(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
	}

	@Override
	public Response getStatiMembriUE(String userCf, String XRequestId, String XForwardedFor) {
		LoggerUtil.debug(logger, "[LuoghiApiServiceImpl::getStatiMembriUE]");
		List<NazioneVO> vos = luoghiService.getStatiMembriUE();
		
		return Response.ok(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
	}

	@Override
	public Response getRegioni(String filter, String userCf, String XRequestId, String XForwardedFor) {
		LoggerUtil.debug(logger, "[LuoghiApiServiceImpl::getRegioni]");
		List<RegioneVO> vos = luoghiService.getRegioni();
		
		return Response.ok(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
	}

	@Override
	public Response getProvince(String filter, String userCf, String XRequestId, String XForwardedFor) {
		LoggerUtil.debug(logger, "[LuoghiApiServiceImpl::getProvince]");
		Long idRegione = FilterUtil.getLongValue(filter, "idRegione");
		List<ProvinciaVO> vos = null;
		if(idRegione != null) {
			vos = luoghiService.getProvinceByIdRegione(idRegione);
		}else{
			vos = luoghiService.getProvince();
		}

		
		return Response.ok(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
	}

	
	@Override
	public Response getComuni(String filter, String userCf, String XRequestId, String XForwardedFor) {
		LoggerUtil.debug(logger, "[LuoghiApiServiceImpl::getComuni]");
		
		List<ComuneVO> comuni = luoghiService.getComuniByIdProvincia(filter);
		ObjectMapper mapper = new ObjectMapper();
		String str = "";
		int len = 0;
		try {
			str = mapper.writeValueAsString(comuni);
			len = str.getBytes("UTF-8").length;
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LoggerUtil.warn(logger, "[LuoghiApiServiceImpl::getComuni] problem mapping result ");
		} 

		return Response.ok(comuni , MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_LENGTH, len).build();
	}

	@Override
	public Response getProvinceForComuniRegistered(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		return voToResponse(luoghiService.findProvinceForComuniRegistered(), 201);
	}

	@Override
	public Response findComuniRegisteredForProvincia(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idProvincia) {

		return voToResponse(luoghiService.findComuniRegisteredForProvincia(idProvincia), 200);
	}
}