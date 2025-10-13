/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento.TipoAdempimento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AdempimentoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ElementoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.WizardApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TipoIstanzaResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.AdempimentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.ElementoVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class WizardApiServiceImpl implements WizardApi {
	private static Logger logger = Logger.getLogger(WizardApiServiceImpl.class.getCanonicalName());

	private final String CLS_NAME =  this.getClass().getSimpleName();

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private AdempimentoService adempimentoService;

	@Autowired
	private TipoIstanzaService tipoIstanzaService;

	@Override
	public Response recuperaElementi(String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

		List<ElementoVO> elementi = elementoService.getElementi();

		if (null == elementi) {
			buildInternaleServerErrorResponse();
		}
		
//		List<ComuneVO> comuni = luoghiService.getComuniByIdProvincia(idProvincia);
		ObjectMapper mapper = new ObjectMapper();
		String str = "";
		int len = 0;
		try {
			str = mapper.writeValueAsString(elementi);
			len = str.getBytes("UTF-8").length;
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LoggerUtil.warn(logger, "[LuoghiApiServiceImpl::getComuni] problem mapping result ");
		} 
		

		return Response.ok(str , MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_LENGTH, len)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).build();
		
//		return Response.ok(elementi)
//				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
//				.build();
	}

	@Override
	public Response recuperaAdempimenti(String userCf, String XRequestId,
			String XForwardedFor, String tipologia, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");
		LoggerUtil.debug(logger,
				"["+ CLS_NAME + "::"+ methodName +"] Parametro in input tipologia [" + tipologia + "]");

		TipoAdempimento tipo = TipoAdempimento.fromString(tipologia);

		List<AdempimentoVO> adempimenti = adempimentoService.getAdempimenti(tipo);

		if (null == adempimenti) {
			this.buildInternaleServerErrorResponse();
		}

		return Response.ok(adempimenti)
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.build();
	}

	@Override
	public Response recuperaTipoIstanza(String userCf, String XRequestId, String XForwardedFor,
			Long idRegimeGiuridico, Long idRegimeAggiuntivo, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");
		LoggerUtil.debug(logger,
				"["+ CLS_NAME + "::"+ methodName +"] Parametro in input idRegimeGiuridico, idRegimeAggiuntivo ["
						+ idRegimeGiuridico + ", " + idRegimeAggiuntivo + " ]");

		TipoIstanzaVO tipoIstanza = tipoIstanzaService.findByRegimeGiuridicoAndRegimeAggiuntivo(idRegimeGiuridico,
				idRegimeAggiuntivo);

		if (null == tipoIstanza) {
			this.buildInternaleServerErrorResponse();
		}

		return Response.ok(new TipoIstanzaResponse(tipoIstanza))
				.header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
				.build();
	}

	private void buildInternaleServerErrorResponse() {
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String errorMessage = "Errore nell'esecuzione della richiesta";
		LoggerUtil.error(logger, "[" + CLS_NAME +"::" + methodName + "] ERROR : " + errorMessage);

		throw new BusinessException(errorMessage);
	}

}