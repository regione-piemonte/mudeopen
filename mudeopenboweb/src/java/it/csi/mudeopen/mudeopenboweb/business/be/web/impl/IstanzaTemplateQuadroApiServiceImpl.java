/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.IstanzaTemplateQuadroApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.IstanzaTemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.IstanzaTemplateQuadroRequest;
@Component
public class IstanzaTemplateQuadroApiServiceImpl extends BaseAPI<IstanzaTemplateQuadroApi> implements IstanzaTemplateQuadroApi {
	
    @Autowired
    private EntityManager entityManager;
	
    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Override
    public Response getIstanzaTemplateQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
			Long idIstanza, 
			Long idTemplateQuadro) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, idTemplateQuadro);
    }
    
    @Override
    public Response saveIstanzaTempleteQuadro(String userCf, String XRequestId, String XForwardedFor, 
    											IstanzaTemplateQuadroRequest istanzaTemplateQuadroRequest, 
    											SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        
        IstanzaTemplateQuadroVO istanzaTemplateQuadro = new IstanzaTemplateQuadroVO();

        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(istanzaTemplateQuadroRequest.getIdIstanza());
    	try {
	        JSONObject jsonDataIstanza;
	        if(istanza.getJsonData() == null)
	        	jsonDataIstanza = new JSONObject();
	        else
	        	jsonDataIstanza = new JSONObject(istanza.getJsonData());
	        
	        JSONObject jsonQuadro = new JSONObject(istanzaTemplateQuadroRequest.getJsonDataQuadro());
	        
	        String codTipoQuadro = istanzaTemplateQuadroRequest.getCodSubQuadro();
        
	        jsonQuadro.put("_LASTUSERID", mudeTUser.getIdUser());
	        jsonQuadro.put("_LASTTIMESTAMP", ""+System.currentTimeMillis());
	        jsonDataIstanza.put("BO_" + codTipoQuadro, jsonQuadro);
	        
		    istanza.setJsonData(jsonDataIstanza.toString());
		    mudeTIstanzaRepository.saveAndFlushDAO(istanza);
		    
    		// call storproc to handle data
		    if("EXTRA_FIELDS".equals(codTipoQuadro))
	    		entityManager.createNativeQuery("SELECT fnc_store_extra_fields("+istanza.getId()+")").getResultList();
		} catch (Exception e) {
			LoggerUtil.error(logger, "[" + this.getClass().getSimpleName() + "::" + Thread.currentThread().getStackTrace()[1].getMethodName()  + " - updateFolderAddressFromIstanza] QDR_EXTRA_FIELDS_BO SQL EXCEPTION : " + e);
			throw new BusinessException("Errore durante il salvataggio dei dati. Riprovare inseguito.");
		}

        return voToResponse(istanzaTemplateQuadro, 200);
    }
    
}	
