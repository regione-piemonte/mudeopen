/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TemplateQuadroService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.TemplateQuadroApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateResponse;

@Component
public class TemplateQuadroApiServiceImpl extends AbstractApi implements TemplateQuadroApi {

    @Autowired
    private TemplateQuadroService templateQuadroService;

    @Override
    public Response loadTemplateQuadriByTipoIstanza(String userCf, String XRequestId, String XForwardedFor, String tipoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

        TemplateQuadroVO tq = templateQuadroService.loadTemplateQuadroByTipoIstanzaDesc(tipoIstanza);

        if (null == tq) {
            buildInternaleServerErrorResponse();
        }

        ObjectMapper mapper = new ObjectMapper();
		String str = "";
		int len = 0;
		try {
			str = mapper.writeValueAsString(tq);
			len = str.getBytes("UTF-8").length;
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LoggerUtil.warn(logger, "[" + CLS_NAME + "::" + methodName + "] problem mapping result ");
		} 

        return Response.ok(str)
                .header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
                .header(HttpHeaders.CONTENT_LENGTH, len)
                .build();
    }

    @Override
    public Response loadTemplateQuadriByCodeTemplate(String userCf, String XRequestId, String XForwardedFor, 
											    		String idTipoIstanza,
											    		String boTemplateOverride,
											    		Long idIstanza,
											    		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

        List<TemplateResponse> tq = templateQuadroService.loadTemplateQuadroResponseByCodeTemplate(idTipoIstanza, boTemplateOverride, headerUtil.getUserCF(httpHeaders, false), idIstanza);

        if (tq == null || tq.size() == 0)
			throw new BusinessException("Non ci sono procedure al momento attive per il tipo di intervento selezionato");

        ObjectMapper mapper = new ObjectMapper();
		String str = "";
		int len = 0;
		try {
			str = mapper.writeValueAsString(tq);
			len = str.getBytes("UTF-8").length;
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LoggerUtil.warn(logger, "[" + CLS_NAME + "::" + methodName + "] problem mapping result ");
		} 

        return Response.ok(str)
                .header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
                .header(HttpHeaders.CONTENT_LENGTH, len)
                .build();
    }

    @Override
    public Response loadTemplateQuadriByIdTemplateQuadro(String userCf, String XRequestId, String XForwardedFor, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

        TemplateQuadroVO tq = templateQuadroService.loadTemplateQuadroById(idTemplateQuadro);

        if (null == tq) {
            buildInternaleServerErrorResponse();
        }

        ObjectMapper mapper = new ObjectMapper();
		String str = "";
		int len = 0;
		try {
			str = mapper.writeValueAsString(tq);
			len = str.getBytes("UTF-8").length;
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LoggerUtil.warn(logger, "[" + CLS_NAME + "::" + methodName + "] problem mapping result ");
		} 

        return Response.ok(str)
                .header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
                .header(HttpHeaders.CONTENT_LENGTH, len)
                .build();
    }

    private void buildInternaleServerErrorResponse() {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String errorMessage = "Errore nell'esecuzione della richiesta";
        LoggerUtil.error(logger, "[" + CLS_NAME + "::" + methodName + "] ERROR : " + errorMessage);

        throw new BusinessException(errorMessage);
    }

    @Override
	public Response loadQuadroById(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									Long idQuadro,
									Long idIstanza,
									Long idFascicolo) {
        MudeTUser mudeTUser = getMudeTUser(httpHeaders);
        if(!managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.CONSULTA_IST.getDescription(), idIstanza, mudeTUser, null)
        		&& !managerAbilitazioni.hasUtenteAbilitazioneFunzionePerFascicolo(FunzioniAbilitazioniEnum.CONS_IST_ALL_FASCIC.getDescription(), idFascicolo, mudeTUser.getIdUser()))
        	throw new BusinessException("L'utente non è abilitato ad eseguire questa operazione");

		return voToResponse(templateQuadroService.loadQuadroById(idQuadro, idIstanza, idFascicolo, httpHeaders), 200);
	}
	

	@Override
	public Response loadJDataIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
										Long idIstanza,
										Long idQuadro,
										String codTipoQuadro,
										Boolean isObbligatoriaNominaPM,
										String requestType) {
        if(!checkAbilitazioneFunzioneIstanza(false, FunzioniAbilitazioniEnum.CONSULTA_IST.getDescription(), idIstanza, getMudeTUser(httpHeaders), httpHeaders, null))
        	return okResponse(); // just don't return any data in case of no rights

		return voToResponse(templateQuadroService.loadJDataIstanza(idIstanza,
																	idQuadro,
																	codTipoQuadro,
																	isObbligatoriaNominaPM,
																	requestType,
																	httpHeaders, headerUtil.getUserCF(httpHeaders, false)), 200);
	}
	
	@Override
	public Response retrieveAllQuadriInTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
												Long idTemplate,
												Long idIstanza,
												Long idUser,
												Boolean includeJson) {
		return voToResponse(templateQuadroService.retrieveAllQuadriInTemplate(idTemplate, idIstanza, idUser, includeJson), 200);
	}

	
}