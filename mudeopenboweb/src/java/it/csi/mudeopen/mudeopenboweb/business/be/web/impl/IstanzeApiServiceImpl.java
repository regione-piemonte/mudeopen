/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.TracciatoXMLManager;
/**
 * The type Istanze api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.EsitoGenerazioneTracciatoEnum;
@Component
public class IstanzeApiServiceImpl extends BaseAPI<IstanzeApi> implements IstanzeApi {

    private static final String BACKOFFICE = "backoffice";

	@Autowired
    private IstanzaService istanzaService;

    @Autowired
    private IstanzaStatoService istanzaStatoService;

    @Autowired
    MudeTEnteRepository mudeTEnteRepository;

    @Autowired
    private TracciatoXMLManager tracciatoXMLManager;

    @Override
    public Response recuperaIstanze(String userCf, String XRequestId, String XForwardedFor, int page, int size, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {

            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));

            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            IstanzeApi istanzaApi = rtarget.proxy(IstanzeApi.class);

            Response response = istanzaApi.recuperaIstanze(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), page, size, securityContext, httpHeaders, httpRequest);
            return response;
        } catch (Throwable t) {
            throw new RemoteException();
        }
    }
    @Override
    public Response recuperaIstanza(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,@QueryParam("scope") String scope) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {

            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));

            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            IstanzeApi istanzaApi = rtarget.proxy(IstanzeApi.class);

            Response response = istanzaApi.recuperaIstanza(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), idIstanza, securityContext, httpHeaders, httpRequest,BACKOFFICE);
            return response;
        } catch (Throwable t) {
            throw new RemoteException();
        }
    }

    @Override
    public Response downloadTemplate(Long idIstanza, Long idTemplate, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));

            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            IstanzeApi istanzaApi = rtarget.proxy(IstanzeApi.class);

            Response response = istanzaApi.downloadTemplate(idIstanza, idTemplate, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest);
            return response;
        } catch (Throwable t) {
            throw new RemoteException();
        }
    }

    @Override
    public Response downloadTemplatePDFIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
									    		Long idIstanza,
									    		String scope, 
									    		String saveAllFilesToDisk) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, scope, saveAllFilesToDisk);
	}

	@Override
	public Response cambiaStatoIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
										Long idIstanza,
										String codStatus,
							    		IstanzaVO istanza,
							    		String scope) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, codStatus, 
		        		istanza,
		        		BACKOFFICE);
	}
	
    @Override
    public Response cercaIstanze(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									String filter, 
									String sort, 
									int page, 
									int size,
									String scope) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort, 
        		page, 
        		size,
        		BACKOFFICE);
    }

    /**
     * Download istanza response.
     *
     * @param idIstanza       the id istanza
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response downloadIstanza(String userCf, String XRequestId, String XForwardedFor, 
    								SecurityContext securityContext, HttpHeaders httpHeaders, 
    								HttpServletRequest httpRequest,
									Long idIstanza, 
									Boolean con_firma, 
									String scope) {

    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, con_firma, 
    			BACKOFFICE);
    }

    @Override
    public Response loadTipiStatoIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null
    			);
    }
	@Override
	public Response downloadRicevutaPdf(String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,Long idIstanza) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));

            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            IstanzeApi istanzaApi = rtarget.proxy(IstanzeApi.class);

            Response response = istanzaApi.downloadRicevutaPdf(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest,idIstanza);
            return response;
        } catch (Throwable t) {
            throw new RemoteException();
        }
	}
	    public Response rigeneraTracciati(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
	    		Long idIstanza,
	    		String debug) {
	        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
	 
            if(debug != null && "local".equals(Constants._environment)) {
            	String xml = tracciatoXMLManager.gemerateTracciato(idIstanza, mudeTUser);

    	        return Response.ok(xml, MediaType.APPLICATION_XML).build();
            }
	        
			try {
				if(tracciatoXMLManager.generaTracciatiPerIstanza(idIstanza, mudeTUser) == EsitoGenerazioneTracciatoEnum.OK)
					return okResponse();
			}
			catch(Throwable t) {
				logger.error("[IstanzeApiServiceImpl::rigeneraTracciati] exception", t);
			}
	        
	    	throw new BusinessException("Sono avvenuti degli errori durante la generazione del tracciato. Verificare i log per maggiori dettagli.");
	    }

	    @Override
		public Response reuperoTemplateFormIo(String userCf, String XRequestId, String XForwardedFor,
				SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
				Long idIstanza, String codStatusEnd) {
	    	
	    	MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
	    	IstanzaVO istanzaVO = istanzaService.recuperaIstanza(mudeTUser, idIstanza, BACKOFFICE, null);

	    	List<SelectVO> listStatiVO = istanzaService.recuperoTemplateFormIoCambioStato( mudeTUser, istanzaVO, codStatusEnd);
	        return voToResponse(listStatiVO, 200);

		}
	    
	    @Override
	    public Response possibileStatoIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza) {
	        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
	        IstanzaVO istanzaVO = istanzaService.recuperaIstanza(mudeTUser, idIstanza, BACKOFFICE, null);

	        List<SelectVO> listStatiVO = istanzaStatoService.recuperaStati(istanzaVO);
	        return voToResponse(listStatiVO, 200);
	    }

	    @Override
	    public Response istanzePratica(String userCf, String XRequestId, String XForwardedFor, Long idPratica, int page, int size, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {       
	        logger.debug("[IstanzeApiServiceImpl::istanzePratica]");
	        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

	        return istanzaService.cercaIstanzePratica(idPratica, page, size, mudeTUser);
	    }
	    
	    @Override
	    public Response cercaIstanzeScrivania(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter, String sort, int page, int size, String scope) {
	        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

	        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

	        return istanzaService.cercaIstanzeUtenteScrivania(filter, mudeTUser, BACKOFFICE, page, size);
	    }

	    @Override
	    public Response exportExcelIstanze(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter, String sort, String scope) {
	        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

	        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
	        Long idFascicolo = FilterUtil.getLongValue(filter, "id_fascicolo");
	        Long idPratica = FilterUtil.getLongValue(filter, "id_pratica");
	        String numPratica = FilterUtil.getStringValue(filter, "num_pratica");
	        String idTipoIStanza = FilterUtil.getStringValue(filter, "id_tipo_istanza");
	        String idIntestatarioPf = FilterUtil.getStringValue(filter, "id_intestatario_pf");
	        String idIntestatarioPg = FilterUtil.getStringValue(filter, "id_intestatario_pg");
	        //Filtro PM
	        String idPm = FilterUtil.getStringValue(filter, "id_pm");

	        Long idComune = FilterUtil.getLongValue(filter, "id_comune");
	        Long idProvincia = FilterUtil.getLongValue(filter, "id_provincia");
	        Long idDug = FilterUtil.getLongValue(filter, "id_dug");
	        String duf = FilterUtil.getStringValue(filter, "duf");
	        LocalDate creationDateFrom = FilterUtil.getDateFromValue(filter, "data_creazione");
	        LocalDate creationDateTo = FilterUtil.getDateToValue(filter, "data_creazione");
	        String code = FilterUtil.getStringValue(filter, "codice_istanza");

	        byte[] model = istanzaService.exportExcelIstanzeUtente(filter, sort, mudeTUser, idFascicolo, idPratica, numPratica, idTipoIStanza, idIntestatarioPf, idIntestatarioPg, idPm, idComune, idProvincia, idDug, duf, creationDateFrom, creationDateTo, code, BACKOFFICE);

	        String outputFilename = new StringBuilder().append("Istanze").append(".xls").toString();
	        return Response.ok(model, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + outputFilename + "\"").build();
	    }

	    @Override
	    public Response getListaTipoPresentatore(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
	    			Long idTipoPresentatore) {
	    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idTipoPresentatore);
	   }
	    
	    @Override
	    public Response getRuoliIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
	    								Long idIstanza, String excl_user_ids, Boolean loadAbilitazioni) {
	    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, excl_user_ids, loadAbilitazioni);
	    }
	    
	    @Override
	    public Response getStatiIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
	    									Long idIstanza,
	    									String scope) {
	    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, "backoffice");
	    }
	    
	    @Override
	    public Response istanzeFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo, String filter, String sort, int page, int size, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
	        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
	        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
	        return istanzaService.cercaIstanzeFascicolo(idFascicolo, filter, sort, page, size, mudeTUser, "backoffice");
	    }

	    
}