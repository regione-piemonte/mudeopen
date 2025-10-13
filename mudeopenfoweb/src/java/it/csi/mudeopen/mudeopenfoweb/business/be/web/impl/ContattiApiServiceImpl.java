/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.ContattiApi;
import it.csi.mudeopen.mudeopenfoweb.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;

/**
 * The type Contatti api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class ContattiApiServiceImpl extends BaseAPI<ContattiApi> implements ContattiApi {

    private static final String ERRORE_VALIDAZIONE = "errore_validazione";

	@Autowired
    private ContattoService contattoService;

    @Autowired
    private IstanzaSoggettoService istanzaSoggettoService;

    @Override
    public Response saveContatto(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
					    		Long idIstanza,
								ContattoVO contatto) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        ContattoVO contattoVOToChange = null;

       if(idIstanza != null)
           checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.COMP_SOGG_COINV.getDescription(), idIstanza, mudeTUser, httpHeaders, "L'utente non è abilitato ad eseguire questa operazione");

        if (null != contatto.getId()) {
            contattoVOToChange = contattoService.findContattoByID(contatto.getId());
        }
        ErrorResponse error = null;
        if (null == contattoVOToChange) {
            String piva = null != contatto.getAnagrafica().getPartitaIva() ? contatto.getAnagrafica().getPartitaIva() : (null != contatto.getAnagrafica().getPartitaIvaComunitaria() ? contatto.getAnagrafica().getPartitaIvaComunitaria() : null);
            ContattoVO contattoVODb = null;
            if(TipoContatto.PF.equals(contatto.getTipoContatto()) && StringUtils.isNotBlank(contatto.getAnagrafica().getCodiceFiscale())){
                contattoVODb = contattoService.findByCF(contatto.getAnagrafica().getCodiceFiscale(), mudeTUser.getCf());
            }
            else if(TipoContatto.PG.equals(contatto.getTipoContatto())){
                contattoVODb = contattoService.findByPiva(piva, mudeTUser.getCf());
            }
            if (null != contattoVODb) {
                error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Esiste già un soggetto con il 'codice fiscale / piva / piva comunitaria' inserito", null, null);
            }
        }
        else{
            Long countIstanzeSoggetto = istanzaSoggettoService.countBySoggetto(contatto.getId());

            if(TipoContatto.PF.equals(contatto.getTipoContatto()) && StringUtils.isNotBlank(contatto.getAnagrafica().getCodiceFiscale())) {
                if (!contatto.getAnagrafica().getCodiceFiscale().equalsIgnoreCase(contattoVOToChange.getAnagrafica().getCodiceFiscale()) && countIstanzeSoggetto > 0) {
                    error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Non è possibile cambiare il codice fiscale del soggetto. Soggetto già collegato ad un istanza.", null, null);
                }
            }
            else if(TipoContatto.PG.equals(contatto.getTipoContatto())) {
                String pivaToChange = null != contattoVOToChange.getAnagrafica().getPartitaIva() ? contattoVOToChange.getAnagrafica().getPartitaIva() : contattoVOToChange.getAnagrafica().getPartitaIvaComunitaria();
                String piva = null != contatto.getAnagrafica().getPartitaIva() ? contatto.getAnagrafica().getPartitaIva() : contatto.getAnagrafica().getPartitaIvaComunitaria();

                if (!piva.equalsIgnoreCase(pivaToChange) && countIstanzeSoggetto > 0) {
                    error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Non è possibile cambiare la piva del soggetto. Soggetto già collegato ad un istanza.", null, null);
                }
            }
        }

        checkForError(error);
        return voToResponse(contattoService.saveContatto(contatto, idIstanza, mudeTUser), 200);
    }

    @Override
    public Response ricercaContatto(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		String filter, String sort, int page, int size) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort, page, size);
    }

    @Override
    public Response modificaContatto(String userCf, String XRequestId, String XForwardedFor, ContattoVO contatto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        return voToResponse(contattoService.saveContatto(contatto, null, mudeTUser), 200);
    }

    @Override
    public Response eliminaContatto(String userCf, String XRequestId, String XForwardedFor, String cf, String piva, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        Boolean isDeletable = contattoService.isContattoDeletable(piva, cf, mudeTUser.getCf());
        if(!isDeletable)
        	throw new BusinessException("Non è possibile eliminare il soggetto. Soggetto già collegato ad un istanza.", "403", ERRORE_VALIDAZIONE, null);

        contattoService.deleteContatto(piva, cf, mudeTUser);
        return Response.noContent().build();
    }
}