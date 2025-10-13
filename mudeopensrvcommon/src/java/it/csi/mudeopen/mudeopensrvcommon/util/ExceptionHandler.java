/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import java.util.HashMap;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.log4j.Logger;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.CreatePdfException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.UserNotFoundException;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;

import org.springframework.stereotype.Component;
@Provider
@Component
public class ExceptionHandler implements ExceptionMapper<RuntimeException>{

	private static final String ERRORE_BUSINESS = "errore_business";
	private static Logger logger = Logger.getLogger(ExceptionHandler.class.getCanonicalName());

	@Override
	public Response toResponse(RuntimeException e) {
		if(e instanceof BusinessException && ((BusinessException)e).getException() != null)
			LoggerUtil.error(logger, e.getMessage(), ((BusinessException)e).getException());
		else
			LoggerUtil.error(logger, e.getMessage(), e);
		
		if(e instanceof BusinessException || e instanceof CreatePdfException ) {
			ErrorResponse error = new ErrorResponse("403", ERRORE_BUSINESS, e.getMessage(), null, null);
			return Response.ok(error).status(Status.FORBIDDEN).build();
		}
		else if( e instanceof UserNotFoundException) {
			ErrorResponse error = new ErrorResponse("401", ERRORE_BUSINESS, e.getMessage(), null, null);
			return Response.ok(error).status(Status.UNAUTHORIZED).build();
		}
		else if(e instanceof ValidationException) {
			HashMap<String, String> detail = new HashMap<String, String>();
			detail.putAll(((ValidationException) e).getDetail());
			ErrorResponse error = new ErrorResponse("403", "errore_validazione", null, detail, null);
			return Response.ok(error).status(Status.FORBIDDEN).build();
		} 

		String errorMsg = "Si è verificato un errore nella richiesta. Si prega di riprovare successivamente. Se l'errore persiste contattare l'amministratore";
		ErrorResponse error = new ErrorResponse("500", "errore_interno", errorMsg, null, null);
		return Response.serverError().entity(error).build();
	}
}