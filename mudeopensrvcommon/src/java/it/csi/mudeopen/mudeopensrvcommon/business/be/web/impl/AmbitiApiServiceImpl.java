/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AmbitoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.AmbitiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;

@Component
public class AmbitiApiServiceImpl extends AbstractApi implements AmbitiApi {

	@Autowired
	private AmbitoService ambitoService;

	@Override
	public Response loadAmbiti(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

		List<AmbitoVO> elementi = ambitoService.loadAmbitiAttivi();

		if (null == elementi) {
			buildInternaleServerErrorResponse();
		}
		

		return Response.ok(elementi)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.header(HttpHeaders.CONTENT_LENGTH, getContentLen(elementi))
				.build();
	}

	@Override
	public Response loadAmbito(String userCf, String XRequestId, String XForwardedFor, Long idAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

		LoggerUtil.debug(logger,
				"[AmbitiApiServiceImpl::loadAmbito] Parametro in input idAmbito ["	+ idAmbito + " ]");

		AmbitoVO ambito = ambitoService.loadAmbitoById(idAmbito);

		if (null == ambito) {
			buildInternaleServerErrorResponse();
		}
		
		return Response.ok(ambito)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.header(HttpHeaders.CONTENT_LENGTH, getContentLen(ambito))
				.build();
	}

	@Override
	public Response loadAmbitoByCodice(String userCf, String XRequestId, String XForwardedFor, String codAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

		LoggerUtil.debug(logger,
				"[AmbitiApiServiceImpl::loadAmbitoByCodice] Parametro in input codAmbito ["	+ codAmbito + " ]");

		AmbitoVO ambito = ambitoService.loadAmbitoByCode(codAmbito);

		if (null == ambito) {
			buildInternaleServerErrorResponse();
		}
		

		return Response.ok(ambito)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.header(HttpHeaders.CONTENT_LENGTH, getContentLen(ambito))
				.build();
	}

	@Override
	public Response saveAmbito(String userCf, String XRequestId, String XForwardedFor, AmbitoVO ambito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

		LoggerUtil.debug(logger,
				"["+ CLS_NAME +"::"+ methodName +"] Parametro in input ambito ["	+ ambito + " ]");
		Long savedId = ambitoService.saveAmbito(ambito);
		ambito.setIdAmbito(savedId);

		if (null == savedId || savedId == 0) {
			buildInternaleServerErrorResponse();
		}
		

		return Response.ok(ambito)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.header(HttpHeaders.CONTENT_LENGTH, getContentLen(ambito))
				.build();
	}

	@Override
	public Response updateAmbito(String userCf, String XRequestId, String XForwardedFor, AmbitoVO ambito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

		LoggerUtil.debug(logger,
				"["+ CLS_NAME +"::"+ methodName +"] Parametro in input ambito ["	+ ambito + " ]");
		Long updateId = ambitoService.updateAmbito(ambito);
		ambito.setIdAmbito(updateId);

		if (null == updateId || updateId == 0) {
			buildInternaleServerErrorResponse();
		}
		

		return Response.ok(ambito)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.header(HttpHeaders.CONTENT_LENGTH, getContentLen(ambito))
				.build();
	}

	private void buildInternaleServerErrorResponse() {
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String errorMessage = "Errore nell'esecuzione della richiesta";
		LoggerUtil.error(logger, "[" + CLS_NAME +"::" + methodName + "] ERROR : " + errorMessage);

		throw new BusinessException(errorMessage);
	}

}