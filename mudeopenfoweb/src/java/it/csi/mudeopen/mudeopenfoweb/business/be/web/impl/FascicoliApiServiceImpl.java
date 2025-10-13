/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.FascicoliApi;
import it.csi.mudeopen.mudeopenfoweb.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaFascicoloRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;

import org.apache.commons.lang.StringUtils;

/**
 * The type Fascicoli api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class FascicoliApiServiceImpl extends BaseAPI<FascicoliApi> implements FascicoliApi {

	private static final String UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE = "L'utente non è abilitato ad eseguire questa operazione";

	@Autowired
	private FascicoliService fascicoliService;

	@Autowired
	private IstanzaService istanzaService;
	
    @Autowired
    private FascicoloUtenteService fascicoloUtenteService;

    @Autowired
    private FascicoloSoggettoService fascicoloSoggettoService;
	
	@Override
    public Response recuperaFascicoli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    		String filter, 
    		int sort, 
    		int page, 
    		int size, 
    		String scope) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort,
	    		page, 
	    		size, 
	    		"frontoffice");
	}

	@Override
	public Response recuperaFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {
			
			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;           
            FascicoliApi fascicoliApi = rtarget.proxy(FascicoliApi.class);

            Response response = fascicoliApi.recuperaFascicolo(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), idFascicolo, securityContext, httpHeaders, httpRequest);
            return response;
		}
		catch(Throwable t) {
			throw new RemoteException();
		}
	}

	@Override
	public Response getRuoliFascicolo(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idFascicolo) {
		return voToResponse(fascicoloSoggettoService.getRuoliFascicolo(idFascicolo, getMudeTUser(httpHeaders)), 200);
	}

	@Override
	public Response salvaFascicolo(String userCf, String XRequestId, String XForwardedFor, SalvaFascicoloRequest request, SecurityContext securityContext
			, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		LoggerUtil.debug(logger, "[FascicoliApiServiceImpl::salvaFascicolo]");
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
		String numeroFascicolo = httpHeaders.getHeaderString(Constants.NUMERO_MUDE);//per richieste da WS che arrivano con numero fascicolo valorizzato
		if(request.getIdFascicolo() != null) // modify?
			checkAbilitazioneFunzioneFascicolo(new String[]{FunzioniAbilitazioniEnum.CREA_FASCIC.getDescription()}, request.getIdFascicolo(), mudeTUser,UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
		
		FascicoloVO vo = fascicoliService.salvaFascicolo(request, mudeTUser, numeroFascicolo);
		return voToResponse(vo, 200);
	}

	@Override
	public Response modificaFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo,
			SalvaFascicoloRequest request, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

		checkAbilitazioneFunzioneFascicolo(new String[]{FunzioniAbilitazioniEnum.CREA_FASCIC.getDescription()},idFascicolo, mudeTUser,UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);

		int countIstanze =  istanzaService.countIstanzeByFascicolo(idFascicolo);
		if(countIstanze > 0){
			Map<String, String> detail = new HashMap<>();
			detail.put("idFascicolo","Non è possibile modificare il fascicolo poiché ha n°" + countIstanze + " istanze associate");
			throw new BusinessException("Non è possibile modificare il fascicolo poiché ha n°", "403", "errore_validazione", detail);
		}

		request.setIdFascicolo(idFascicolo);
		FascicoloVO vo = fascicoliService.salvaFascicolo(request, mudeTUser, null);
		return voToResponse(vo, 200);
	}

	@Override
	public Response eliminaFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

		checkAbilitazioneFunzioneFascicolo(new String[]{FunzioniAbilitazioniEnum.ELIMINA_FASCIC.getDescription()},idFascicolo, mudeTUser,UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);

		fascicoliService.eliminaFascicolo(idFascicolo, mudeTUser);
		return Response.ok().build();
	}
	
    @Override
	public Response recuperaAbilitazioniFascicolo(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idFascicolo) {
		return voToResponse(fascicoloUtenteService.recuperaAbilitazioniFascicolo(idFascicolo), 200);
    }

}