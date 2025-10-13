/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.NotificaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.NotificheApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
@Component
public class NotificheApiServiceImpl extends AbstractApi implements NotificheApi {
    @Autowired
    private NotificaService notificaService;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    MudeDTipoNotificaRepository mudeDTipoNotificaRepository;
	@Autowired
    MudeTDocumentoRepository mudeTDocumentoRepository;
    @Override
    public Response loadNotificheIstanza(String userCf, String XRequestId, String XForwardedFor, 
    	SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    	Long idIstanza, int sort, int page, int size)
    {
        //MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        String userCfHeader = httpHeaders.getHeaderString(Constants.HEADER_USER_CF);
        LoggerUtil.info(logger, "loadNotificheIstanza - userCF su header: "+userCfHeader);
        return notificaService.cercaNotificheIstanza(idIstanza, page, size, null);
    }

    /*
     * BO / WS
     */
 	@Override
	public Response nuovaNotifica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza,
			IstanzaVO istanza, Long idTipoNotifica, String scope) {
		
		Boolean rifDocumenti = false;
		List <MudeTDocumento> documenti = new ArrayList<MudeTDocumento>();
		try {
			if(istanza != null) {
				JSONObject objJson = new JSONObject(istanza.getJsonData());
				if(idTipoNotifica == null || idTipoNotifica == 0L)
					idTipoNotifica = objJson.getLong("tipo_notifica");
				if(objJson.has("rifDocumenti")) {
					rifDocumenti = objJson.getBoolean("rifDocumenti");
					if(rifDocumenti != null && rifDocumenti) {
				        JSONArray documentiArray = objJson.getJSONArray("rifDocumentiList");
				        for (int i = 0, size = documentiArray.length(); i < size; i++) {
				        	documenti.add(mudeTDocumentoRepository.findOne(documentiArray.getLong(i)));
				        }
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MudeTUser mudeTUser = null;
		if((userCf == null || StringUtils.isBlank(userCf)) && !StringUtils.equals(Constants.SCOPE_WS, scope)) {
			mudeTUser = headerUtil.getUserCF(httpHeaders, false);
		}

		MudeDTipoNotifica mudeDTipoNotifica = mudeDTipoNotificaRepository.findByIdTipoNotifica(idTipoNotifica);
    	
    	MudeTNotifica mudeTNotifica = istanzaService.insertTNotifica(mudeTUser,idIstanza,istanza.getJsonData(),mudeDTipoNotifica);
    	
    	List<MudeTContatto> contattiDestinatari = istanzaService.recuperaContatti(idIstanza,mudeTUser);
    	
    	if(mudeTNotifica != null && contattiDestinatari != null && !contattiDestinatari.isEmpty()) {
    		istanzaService.insertRNotificaUtenti(idIstanza,mudeTNotifica,contattiDestinatari);
    		
    		if(!documenti.isEmpty())
    			istanzaService.insertRNotificaDocumento(idIstanza,mudeTNotifica,documenti);
    		
    		if(mudeDTipoNotifica.getInvioEmail())
    			istanzaService.insertRNotificaContatto(idIstanza,mudeTNotifica,contattiDestinatari,istanza);
    	}

    	return Response.ok(mudeTNotifica != null ? mudeTNotifica.getId(): null).build();
	}

// FO
 	/*
    @Override
    public Response loadNotificheFO(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    								String filter, int sort, int page, int size) {
        return notificaService.loadNotificheFO(filter, page, size, headerUtil.getUserCF(httpHeaders, false));
    }
	
    @Override
	public Response notificaLettaFO(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		Long idNotifica) {
        return voToResponse(notificaService.notificaLettaFO(idNotifica, headerUtil.getUserCF(httpHeaders, false)), 200);
    }
    */
// BO
    /*
    @Override
    public Response loadTipiNotifica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<SelectVO> listStatiVO = notificaService.recuperaTipiNotifica();
        Map<String, String> map = new HashMap<>();
        try {
            map = getContentLength(listStatiVO);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            LoggerUtil.warn(logger, "[NotificheApiServiceImpl::loadTipiNotifica] problem mapping result : " + e.getMessage());
        }

        return Response.ok(listStatiVO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(listStatiVO)).build();
    }

    @Override
	public Response reuperoTemplateFormIoNotifica(String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idIstanza, Long idTipoNotifica) {
    	
    	//MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
    	MudeTUser mudeTUser = mudeTUserRepository.findByCf(userCf);
    	
    	List<SelectVO> listNotificaVO = notificaService.reuperoTemplateFormIoNotifica(idIstanza, mudeTUser, idTipoNotifica);
    	Map<String, String> map = new HashMap<>();
        try {
            map = getContentLength(listNotificaVO);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            LoggerUtil.warn(logger, "[NotificheApiServiceImpl::reuperoTemplateFormIoNotifica] problem mapping result : " + e.getMessage());
        }

        return Response.ok(listNotificaVO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(listNotificaVO)).build();
	}
    */
 	
}