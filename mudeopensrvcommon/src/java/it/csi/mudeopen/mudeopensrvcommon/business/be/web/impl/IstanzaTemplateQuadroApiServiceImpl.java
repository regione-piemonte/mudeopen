/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaTemplateQuadroService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.IstanzaTemplateQuadroApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.IstanzaTemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.IstanzaTemplateQuadroRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateResponse;

@Component
public class IstanzaTemplateQuadroApiServiceImpl extends AbstractApi implements IstanzaTemplateQuadroApi {

    @Autowired
    private IstanzaTemplateQuadroService istanzaTemplateQuadroService;

    @Override
    public Response loadIstanzaTemplateQuadroByIdistanza(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

        List<IstanzaTemplateResponse> response = istanzaTemplateQuadroService.loadIstanzeTemplateQuadroByIdIstanza(idIstanza);

        if (null == response) {
            buildInternaleServerErrorResponse();
        }

        return Response.ok(response).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(response)).build();
    }

    @Override
    public Response getIstanzaTemplateQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    			Long idIstanza, 
    			Long idTemplateQuadro) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        IstanzaTemplateQuadroVO response = istanzaTemplateQuadroService.loadIstanzaTemplateQuadroByPK(idIstanza, idTemplateQuadro, mudeTUser, httpHeaders);

        if(null == response)
            return Response.ok(response).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).build();

        return voToResponse(response, 200);
    }

    @Override
    public Response getTemplateQuadriByCodeTemplateAndIdIstanza(String userCf, String XRequestId, String XForwardedFor, String codeTemplate, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

        List<IstanzaTemplateResponse> response = istanzaTemplateQuadroService.loadIstanzeTemplateQuadroByIdIstanza(idIstanza);

        if (null == response) {
            buildInternaleServerErrorResponse();
        }

        return Response.ok(response).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(response)).build();
    }

    @Override
    public Response saveIstanzaTempleteQuadro(String userCf, String XRequestId, String XForwardedFor, IstanzaTemplateQuadroRequest istanzaTemplateQuadroRequest, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        IstanzaTemplateQuadroVO vo = new IstanzaTemplateQuadroVO();
        vo.setJsonDataQuadro(istanzaTemplateQuadroRequest.getJsonDataQuadro());

        IstanzaVO istanza = new IstanzaVO();
        istanza.setIdIstanza(istanzaTemplateQuadroRequest.getIdIstanza());
        vo.setIstanza(istanza);

        TemplateQuadroVO tq = new TemplateQuadroVO();
        tq.setIdTemplateQuadro(istanzaTemplateQuadroRequest.getIdTemplateQuadro());
        vo.setTemplateQuadro(tq);

        vo.setJsonDataSubquadro(istanzaTemplateQuadroRequest.getJsonDataSubquadro());
        vo.setCodeSubQuadro(istanzaTemplateQuadroRequest.getCodSubQuadro());
        vo.setIdSubQuadro(istanzaTemplateQuadroRequest.getIdSubQuadro());
        vo.setjDataKeysToDelete(istanzaTemplateQuadroRequest.getjDataKeysToDelete());

        // validate getJsonDataQuadro
        validate(istanzaTemplateQuadroRequest, mudeTUser, httpHeaders);

        return voToResponse(istanzaTemplateQuadroService.saveIstanzaTemplateQuadro(vo, istanzaTemplateQuadroRequest.isJsondataModificato(), istanzaTemplateQuadroRequest.isMainQuadroValidated(), mudeTUser, httpHeaders), 200);
    }

    private void buildInternaleServerErrorResponse() {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String errorMessage = "Errore nell'esecuzione della richiesta";
        LoggerUtil.error(logger, "[" + CLS_NAME + "::" + methodName + "] ERROR : " + errorMessage);

        throw new BusinessException(errorMessage);
    }

    private void validate(IstanzaTemplateQuadroRequest istanzaTemplateQuadroVO, MudeTUser mudeTUser, HttpHeaders httpHeaders) throws ValidationException {
        HashMap<String, String> detail = new HashMap<>();

        IstanzaTemplateQuadroVO itq = istanzaTemplateQuadroService.loadIstanzaTemplateQuadroByPK(istanzaTemplateQuadroVO.getIdIstanza(), istanzaTemplateQuadroVO.getIdTemplateQuadro(), null, httpHeaders);

        if (itq != null && itq.getTemplateQuadro() != null && itq.getTemplateQuadro().getQuadro() != null) {
            QuadroVO quadro = itq.getTemplateQuadro().getQuadro();
            String vScript = quadro.getValidationScript();

            // groovy script
            if (null != vScript) {
                // valid ??

                ScriptEngineManager factory = new ScriptEngineManager();
                ScriptEngine engine = factory.getEngineByName("groovy");

                try {
                    // EXAMPLE
                    //String fact = "def factorial(n) { n == 1 ? 1 : n * factorial(n - 1) }";

                    String json = istanzaTemplateQuadroVO.getJsonDataQuadro();
                    engine.eval(vScript);

                    Invocable inv = (Invocable) engine;
                    Object[] params = {json};
                    Object result = inv.invokeFunction("validate", params);

                    LinkedHashMap<String, ArrayList<String>> errorMap = (LinkedHashMap<String, ArrayList<String>>) result;
                    for (Map.Entry<String, ArrayList<String>> entry : errorMap.entrySet()) {
                        // trasform in jsonarray
                        JSONArray jsonArray = new JSONArray();
                        entry.getValue().forEach(jsonArray::put);
                        detail.put(entry.getKey(), jsonArray.toString());
                    }

                } catch (ScriptException | NoSuchMethodException e) {
                    detail.put("validationScript", "not valid");
                }
            } else {
                // TODO 20210409 - in caso di mancata valorizzazione dello script vado avanti, da capire in seguito se sara' obbligatorio
                //                detail.put("validationScript", "not found");
            }
        }

        if (!detail.isEmpty()) {
            throw new ValidationException(detail);
        }

    }

}