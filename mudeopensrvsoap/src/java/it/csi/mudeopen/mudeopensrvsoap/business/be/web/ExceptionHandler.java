/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvsoap.business.be.web;

import java.util.HashMap;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.DefaultResponse;
import it.csi.mudeopen.mudeopensrvsoap.util.LoggerUtil;
/**
 * The type Exception handler.
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException>{

	private static final String ERRORE_BUSINESS = "errore_business";
	private static Logger logger = Logger.getLogger(ExceptionHandler.class);

	public Response toResponse(RuntimeException e) {
		
		LoggerUtil.error(logger, e.getMessage(), e);
		if(e instanceof BusinessException) {
			DefaultResponse retVo = new DefaultResponse(DefaultResponse.StatusKO, ERRORE_BUSINESS);
			HashMap<String, String> detail = new HashMap<String, String>();
			detail.put("messaggio", e.getMessage());
			retVo.setDetail(detail);
			return Response.ok(retVo).status(Status.OK).build();
		} else if(e instanceof ValidationException) {
			// mapping errori di validazione
			DefaultResponse retVo = new DefaultResponse(DefaultResponse.StatusKO, "errore_validazione");
			retVo.setDetail(((ValidationException) e).getDetail());
			return Response.ok(retVo).status(Status.OK).build();
		}
		DefaultResponse retVo = new DefaultResponse(DefaultResponse.StatusKO, "errore_interno");
		HashMap<String, String> detail = new HashMap<String, String>();
		detail.put("messaggio", e.getMessage());
		retVo.setDetail(detail);
		
		return Response.ok(retVo).status(Status.OK).build();
	}
}