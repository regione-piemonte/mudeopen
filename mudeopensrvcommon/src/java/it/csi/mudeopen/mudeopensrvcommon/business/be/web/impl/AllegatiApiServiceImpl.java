/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AllegatiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;

@Component
public class AllegatiApiServiceImpl extends AbstractApi implements AllegatiApi {

	private static final String ERRORE_BUSINESS = "errore_business";

	private static final String ERRORE_INTERNO = "errore_interno";
	private static final String UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE = "L'utente non è abilitato ad eseguire questa operazione";

	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	@Autowired
    private AllegatiService allegatiService;

    @Autowired
    private IndexManager indexManager;

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;
    
    @Autowired
    private MudeTDocumentoRepository mudeTDocumentoRepository;

    @Override
    public Response loadAllegato(Long idAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        AllegatoVO vo = allegatiService.loadAllegato(idAllegato);
        if (null == vo)
	        throw new BusinessException("Elemento non trovato con id [" + idAllegato + "]", "404", ERRORE_INTERNO, null);
        return Response.ok(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
    }

    @Override
    public Response downloadAllegato(String uuid, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String scope, Boolean conFirma) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, "ws".equals(scope));
        boolean isIDF = "idf".equals(scope);

        MudeTAllegatoSlim vo = allegatiService.findSlimByFileUID(uuid);
        if((!"backoffice".equals(scope) && !"ws".equals(scope) && vo !=null)) {
            if(!checkAbilitazioneFunzioneIstanza(false, FunzioniAbilitazioniEnum.CONSULTA_ALLEG.getDescription(), vo.getIdIstanza(), mudeTUser, httpHeaders, null) 
            		&& !checkAbilitazioneFunzioneFascicolo(FunzioniAbilitazioniEnum.CONS_IST_ALL_FASCIC.getDescription(), mudeTFascicoloRepository.getIdFascicoloFromIdIstanza(vo.getIdIstanza()), mudeTUser, null))
                throw new BusinessException(UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE, "403", ERRORE_BUSINESS, null);
        }
        
        File file = indexManager.getFileByUuid(uuid);        
        try {
	        if(isIDF) {
	        	MudeTDocumento mudeTDocumento = mudeTDocumentoRepository.findByFileUID(uuid).get();
	            return Response.ok(file, "application/octet-stream").header(CONTENT_DISPOSITION, "attachment; filename=\"" + mudeTDocumento.getNomeFileDocumento().replaceAll("([.][pP]7[mM]){1,}$", "") + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();
	        }
	
	        MudeDTipoAllegato tipoAllegato = allegatiService.findTipoAllegatoByCodice(vo.getIdTipoAllegato());
	
	        File fileDecrypted = null;
	        if(conFirma != null && !conFirma && tipoAllegato != null)
	            if((fileDecrypted = indexManager.getExtractedDocumentFromEnvelopeFile(file)) != null)
	            	file = fileDecrypted;
	
	        if (null != file) {
	            if(fileDecrypted != null)
	                return Response.ok(file, "application/octet-stream").header(CONTENT_DISPOSITION, "attachment; filename=\"" + vo.getNomeFileAllegato().replaceAll("([.][pP]7[mM]){1,}$", "") + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();
	
	            return Response.ok(file, vo.getMimeType()).header(CONTENT_DISPOSITION, "attachment; filename=\"" + vo.getNomeFileAllegato() + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();
	        }
	        else
	            throw new BusinessException("File non trovato [" + vo.getNomeFileAllegato() + "]", "404", ERRORE_INTERNO, null);
        }
        finally {
        	if(file != null)
        		file.deleteOnExit();
        }
    }

    @Override
    public Response downloadAllegatoTest(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		String scope,Boolean con_firma) {
        ErrorMessage errorMSG = indexManager.deleteContenutoByUuid("58a51669-1bb4-11ec-8b53-3f777c3e4e75");
        if(null != errorMSG){
            logger.debug("[AllegatiApiServiceImpl::downloadAllegatoTest] ERROR : " + errorMSG.toString());

        }
        File file = indexManager.getFileByUuid("58a51669-1bb4-11ec-8b53-3f777c3e4e75");
        if (null != file) {

            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"0100127200000000572011_0100127200000000572011_DIA.pdf\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();
        } 
        else
	        throw new BusinessException("File non trovato ", "404", ERRORE_INTERNO, null);

    }

    @Override
        public Response loadAllegatiIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		Long idIstanza,
    		String tipo_allegato,
    		String scope) {
        if(scope != null && scope.equalsIgnoreCase("ws-ricerca-allegati")){
            MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, true);
            List<MudeTAllegatoSlim> vos = allegatiService.loadAllegatiSlimIstanza(idIstanza, tipo_allegato, mudeTUser);

            return Response.ok(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
        }
        else {
            MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, Constants.SCOPE_WS.equals(scope));
            List<AllegatoVO> vos = allegatiService.loadAllegatiIstanza(idIstanza, tipo_allegato, mudeTUser, "essential");
            vos.stream().forEach(u -> u.setFileContent(null));

            return Response.ok(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
        }

    }

}