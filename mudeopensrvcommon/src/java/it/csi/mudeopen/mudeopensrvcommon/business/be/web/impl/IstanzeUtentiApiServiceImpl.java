/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.IstanzeUtentiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.QualificaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.CategorieRuoliEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

@Component
public class IstanzeUtentiApiServiceImpl extends AbstractApi implements IstanzeUtentiApi {

    private static final String ERRORE_VALIDAZIONE = "errore_validazione";

	@Autowired
    private IstanzaUtenteService istanzaUtenteService;

    @Autowired
    private ContattoService contattoService;

    @Autowired
    private AbilitazioneService abilitazioneService;

    @Autowired
    private IstanzaSoggettoService istanzaSoggettoService;

    @Override
    public Response saveIstanzaUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, IstanzaUtenteVO istanzaUtenteVO) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        Long idIstanza = istanzaUtenteVO.getIstanza().getIdIstanza();

        AbilitazioneVO abilitazioneVO = abilitazioneService.findById(istanzaUtenteVO.getAbilitazione().getId());
        ContattoVO contattoVO = contattoService.findContattoByID(istanzaUtenteVO.getUtente().getId());
        if (abilitazioneVO.getNecessariaIscrizioneAlbo()) {
            List<QualificaVO> qualificheProfessionali = contattoVO.getQualificheProfessionali();
            boolean hasIscrizioneAlbo = false;
            for (QualificaVO qualificaVO : qualificheProfessionali) {
                String iscrizioneAlbo = qualificaVO.getNumeroIscrizioneOrdine();
                if (StringUtils.isNotBlank(iscrizioneAlbo)) {
                    hasIscrizioneAlbo = true;
                    break;
                }
            }
            if (!hasIscrizioneAlbo) {
                ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "L' abilitazione [" + abilitazioneVO.getDescrizione() + "] necessita che l'utente sia iscritto ad un albo professionale", null, null);

                logger.error("[IstanzeUtentiApiServiceImpl::saveIstanzaUtente] ERROR : " + error);

                return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
            }
        }
        if (abilitazioneVO.getNecessariaPresenzaInIstanza()) {
            //fixme da implementare controllo
            Boolean isSoggettoInIStanzaConRuolo = istanzaSoggettoService.isSoggettoInIStanzaConRuolo(idIstanza, contattoVO.getId(), CategorieRuoliEnum.TECNICI.getDescription());
            if (!isSoggettoInIStanzaConRuolo) {
                ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "L' abilitazione [" + abilitazioneVO.getDescrizione() + "] necessita che l'utente sia inserito nell'istanza con un ruolo tecnico", null, null);

                logger.error("[IstanzeUtentiApiServiceImpl::saveIstanzaUtente] ERROR : " + error);

                return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
            }
        }
        if (abilitazioneVO.getTipo() == 'S') {
            List<IstanzaUtenteVO> istanzaUtenteList = istanzaUtenteService.findByIstanzaAndAbilitazione(idIstanza, abilitazioneVO.getId());
            if (!istanzaUtenteList.isEmpty()) {
                ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Abilitazione [" + abilitazioneVO.getDescrizione() + "] già assegnata. Non è possibile assegnare questa abilitazione a più utenti", null, null);

                logger.error("[IstanzeUtentiApiServiceImpl::saveIstanzaUtente] ERROR : " + error);

                return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
            }
        }

        IstanzaUtenteVO vo = istanzaUtenteService.save(idIstanza, abilitazioneVO.getCodice(), mudeTUser);
        return Response.ok(vo).status(201).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
    }

    @Override
    public Response saveAbilitazioniFunzioniPerIstanzaUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza, AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        AbilitazioneVO abilitazioneVO = abilitazioneService.findById(abilitazioneFunzioneCustomVO.getAbilitazione().getId());

        // is it not "abilitazioneFunzioneCustomVO" update?
        if (null == istanzaUtenteService.findByIstanzaAndAbilitazioneAndUtente(idIstanza, abilitazioneFunzioneCustomVO.getAbilitazione().getId(), abilitazioneFunzioneCustomVO.getIdUtente())) {
            alboFoundGoOn:
            if (abilitazioneVO.getNecessariaIscrizioneAlbo()) {
                ContattoVO contattoVO = contattoService.findPrivateContactByAccreditedIdUser(abilitazioneFunzioneCustomVO.getIdUtente(), mudeTUser);
                if(contattoVO == null)
                	contattoVO = contattoService.findAccreditedContactByIdUser(abilitazioneFunzioneCustomVO.getIdUtente());
                	
                if(contattoVO.getQualificheProfessionali() != null) {
                	int iNoAlbo = 0;
	                for (QualificaVO qualificaVO : contattoVO.getQualificheProfessionali()) {
	                    if (StringUtils.isNotBlank(qualificaVO.getNumeroIscrizioneOrdine()))
	                        break alboFoundGoOn;
	                    
	                    if(qualificaVO.getSenzaObbligoIscrizioneOrdine() != null && qualificaVO.getSenzaObbligoIscrizioneOrdine() == true)
	                    	iNoAlbo++;
	                }
	                
	                if(iNoAlbo > 0 && iNoAlbo == contattoVO.getQualificheProfessionali().size())
                        break alboFoundGoOn; // in case all users have "NO ALBO", then ok
	
	                throw new BusinessException("L' abilitazione [" + abilitazioneVO.getDescrizione() + "] necessita che l'utente sia iscritto ad un albo professionale", "403", ERRORE_VALIDAZIONE, null);
                }
            }

            // THIS CHECK MUST ME MOVED AFTER THE SUBJECT HAS BEEN ADDED TO THE ISTANZA LIST (why CategorieRuoliEnum.TECNICI?)
            //fixme da implementare controllo --- verificare se spostare in download pdf e bloccare il download o se mettere da altra parte
	        /*
	        if(abilitazioneVO.getNecessariaPresenzaInIstanza() 
	        		&& !istanzaSoggettoService.isSoggettoInIStanzaConRuolo(idIstanza, contattoVO.getId(), CategorieRuoliEnum.TECNICI.getDescription()))
	        	throw new BusinessException("L' abilitazione [" + abilitazioneVO.getDescrizione() + "] necessita che l'utente sia tra i soggetti coinvolti con ruolo tecnico", "403", "errore_validazione", null);
	  		*/

            if (abilitazioneVO.getTipo() == 'S') {
                List<IstanzaUtenteVO> istanzaUtenteList = istanzaUtenteService.findByIstanzaAndAbilitazione(idIstanza, abilitazioneVO.getId());
                if (!istanzaUtenteList.isEmpty()) {
                    ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Abilitazione [" + abilitazioneVO.getDescrizione() + "] già assegnata. Non è possibile assegnare questa abilitazione a più utenti", null, null);

                    logger.error("[IstanzeUtentiApiServiceImpl::saveIstanzaUtente] ERROR : " + error);

                    return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
                }
            }
        }

        istanzaUtenteService.saveAbilitazioniFunzioniPerIstanzaUtente(idIstanza, abilitazioneFunzioneCustomVO, mudeTUser);
        return Response.ok().status(201).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).build();
    }

    @Override
    public Response deleteIstanzaUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanzaUtente) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        istanzaUtenteService.delete(idIstanzaUtente);
        return Response.noContent().header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).build();
    }
}