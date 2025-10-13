/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.VerifyReport;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.AllegatiApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PraticaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoDocumentoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.PraticheApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.TipoDocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;
import javax.ws.rs.core.MediaType;

@Component
public class PraticheApiServiceImpl extends AbstractApi implements PraticheApi {

    private static final String ERRORE_VALIDAZIONE = "errore_validazione";
	private static final String BACKOFFICE = "backoffice";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	@Autowired
    private IstanzaService istanzaService;
    @Autowired
    private PraticaService praticaService;
    @Autowired
    private IndexManager indexManager;

    @Autowired
    private AllegatiApiServiceHelper allegatiApiServiceHelper;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private FascicoliService fascicoliService;

    @Autowired
    MudeDTipoNotificaRepository mudeDTipoNotificaRepository;

    @Autowired
    MudeRNotificaDocumentoRepository mudeRNotificaDocumentoRepository;

    @Autowired
    MudeDTipoDocpaRepository mudeDTipoDocpaRepository;

    @Autowired
    PraticaEntityMapper praticaEntityMapper;

    @Autowired
    MudeTDocumentoRepository mudeTDocumentoRepository;

    @Override
    public Response recuperaPratiche(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, 
    		HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter, int sort, int page, int size, String scope) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

        if(StringUtils.equals(Constants.SCOPE_WS, scope)) {
        	return praticaService.cercaPraticheWs(userCf, filter, page, size);
        }

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        if(!BACKOFFICE.equals(scope) && !"frontoffice".equals(scope)) {
            Long idFascicolo = FilterUtil.getLongValue(filter, "id_fascicolo");
            String code = FilterUtil.getStringValue(filter, "codice_istanza");
    		String idReferentePf = FilterUtil.getStringValue(filter, "id_intestatario_pf");
    		String idReferentePg = FilterUtil.getStringValue(filter, "id_intestatario_pg");
    		String idPm = FilterUtil.getStringValue(filter, "id_pm");
            Long idIstanzaRiferimento = FilterUtil.getLongValue(filter, "id_istanza_riferimento");
            String idTipoIStanza = FilterUtil.getStringValue(filter, "id_tipo_istanza");
            String numeroProtocollo = FilterUtil.getStringValue(filter, "numero_protocollo");
            LocalDate dataProtocolloDa = FilterUtil.getDateFromValue(filter, "data_protocollo");
            LocalDate dataProtocolloA = FilterUtil.getDateToValue(filter, "data_protocollo");
            LocalDate dataRegistrazionePraticaDa = FilterUtil.getDateFromValue(filter, "data_registrazione_pratica");
            LocalDate dataRegistrazionePraticaA = FilterUtil.getDateToValue(filter, "data_registrazione_pratica");

            Long anno = FilterUtil.getLongValue(filter, "anno");
            Long idPratica = FilterUtil.getLongValue(filter, "id_pratica");
            String numPratica = FilterUtil.getStringValue(filter, "num_pratica");
        	
            // TODO: check whether it is used anymore!
	        return istanzaService.cercaPraticheUtente(mudeTUser, idFascicolo,idPratica,
	                code, idReferentePf, idReferentePg, idPm, idIstanzaRiferimento, idTipoIStanza,
	                numeroProtocollo, dataProtocolloDa, dataProtocolloA, anno, numPratica, dataRegistrazionePraticaDa,
	                dataRegistrazionePraticaA, scope, page, size );
        } else
            return praticaService.cercaPratiche(filter, mudeTUser, scope, page, size );

    }
	@Override
	public Response recuperaDocumentiPraticaById(String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idPratica,
			int sort, int page, int size) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

        //MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        return praticaService.recuperaDocumenti(idPratica, page, size);
	}

	@Override
	public Response deleteDocumento(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idDocumento,
			String scope) {

		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, Constants.SCOPE_WS.equals(scope));
		DocumentoVO vo = praticaService.loadDocumento(idDocumento);
		MudeTDocumento documento = mudeTDocumentoRepository.findOne(idDocumento);
		List<MudeRNotificaDocumento> notificaDocumentoLst = mudeRNotificaDocumentoRepository.findByMudeTDocumento(documento);
		if(notificaDocumentoLst != null && notificaDocumentoLst.size() > 0) {
			LoggerUtil.debug(logger,"[PraticheApiServiceImpl::deleteDocumento] Utente non autorizzato");
			ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Documento già notificato, non è possibile eliminarlo.", null, null);
			 	
			return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
		}
		
		if(mudeTUser == null || vo.getUtente().getId()==mudeTUser.getIdUser()) {
	        if(null != vo) {
	            ErrorMessage errorMessage = indexManager.deleteContenutoByUuid(vo.getFileUID());
	            if(null != errorMessage){
	                ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Si è verificata un'anomalia durante la cancellazione del file.", null, null);
	                
	                LoggerUtil.debug(logger,"[PraticheApiServiceImpl::deleteDocumento] ERROR : (Index error) " + errorMessage.toString());

	                return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
	            }
	        }
	        praticaService.deleteDocumento(idDocumento);

	        return Response.ok().build();
		}
		LoggerUtil.debug(logger,"[PraticheApiServiceImpl::deleteDocumento] Utente non autorizzato");
		ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Utente non autorizzato alla cancellazione del file.", null, null);
			
		return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
	}
	
	@Override
    public Response uploadDocumento(MultipartFormDataInput input, Long idPratica, String scope, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, Constants.SCOPE_WS.equals(scope));
        DocumentoVO saved = null;
        try {
            String documentoPraticaJson = input.getFormDataPart("documentoPratica", String.class, null);
            
            File file = input.getFormDataPart("file", File.class, null);
            String filename = allegatiApiServiceHelper.getFileName(input, "file");

            // Recupero oggetto dalla stringa

            DocumentoVO documentoPratica = new DocumentoVO();
            if(BACKOFFICE.equals(scope)) {
            	documentoPratica.setIdPratica(idPratica);
            	documentoPratica.setDataCaricamento(new Date());
            	
            	documentoPratica.setNumeroProtocollo(input.getFormDataPart("numeroProtocollo", String.class, null));
            	documentoPratica.setDataProtocollo(input.getFormDataPart("dataProtocollo", String.class, null));
            	
            	StringBuffer nomeFile = new StringBuffer();
            	if(documentoPraticaJson != null)
            		nomeFile.append(documentoPraticaJson).append("_");
            	nomeFile.append(filename);
            	documentoPratica.setNomeFileDocumento(nomeFile.toString());
            	
            	//JSONObject jsonObj = new JSONObject(documentoPraticaJson);
            	//String descTipoDoc = jsonObj.getString("Tipo File");
            	//Optional<MudeDTipoDocPA> mudeDTipoDocPA = mudeDTipoDocpaRepository.findByDesc(descTipoDoc);
            	String codTipoDoc = documentoPraticaJson;
            	MudeDTipoDocPA mudeDTipoDocPA = mudeDTipoDocpaRepository.findByCodice(codTipoDoc);
            	
            	if(mudeDTipoDocPA != null) {
	            	TipoDocumentoVO tipoDocumentoVO = new TipoDocumentoVO();
	            	tipoDocumentoVO.setId(mudeDTipoDocPA.getId());
	            	tipoDocumentoVO.setCodice(mudeDTipoDocPA.getCodeTipoDocpa());
	            	// Fix CSI_MUDE-364	 MUDEOPEN-329 MUDEOPEN-BO: Upload Documenti PA - salvare nome documento con prefisso CODICE TIPOLOGIA
	            	documentoPratica.setNomeFileDocumento(tipoDocumentoVO.getCodice() + "_" + filename);
	            	tipoDocumentoVO.setDescrizione(mudeDTipoDocPA.getDescTipoDocpa());
	            	documentoPratica.setTipoDocumento(tipoDocumentoVO);
            	}
                UtenteVO utenteVO = new UtenteVO();
                utenteVO.setBackofficeUser(true);
                utenteVO.setId(mudeTUser.getIdUser());
                documentoPratica.setUtente(utenteVO);
                //documentoVO.setTipoDocumento(new TipoDocumentoVO(mudeDTipoDocpa.getCodTipoDocpa(), mudeDTipoDocpa.getDescTipoDocpa()));
            }else { // Json version
            	 ObjectMapper mapper = new ObjectMapper();
            	 documentoPratica = mapper.readValue(documentoPraticaJson, DocumentoVO.class);
            }
            //filename = new StringBuilder(documentoPratica.getTipoDocumento().getDescrizione()).append("_").append(filename).toString();
            // SD - Per i documenti BO meglio non modificare il nome del file

            MudeTPratica mudeTPratica = praticaService.findById(idPratica);
            MudeTIstanza mudeTIstanza = null;
            try {
            	if(mudeTPratica.getIstanze() != null) {
            		logger.debug("Istanze trovate per la pratica " + mudeTPratica.getNumeroPratica() +": " + mudeTPratica.getIstanze().size());
            		mudeTIstanza = mudeTPratica.getIstanze().get(0);
            	}
            }
            catch (Exception e)
            {
                logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + e.getMessage());

                return Response.serverError().status(500).build();
            }

            if(mudeTIstanza == null) {
                logger.error("[PraticheApiServiceImpl::uploadDocumento] istanza not found");
                return Response.serverError().status(500).build();
            }

            // Verifica nome file duplicato
            ErrorResponse duplicateFilenameError = allegatiApiServiceHelper.verifyDuplicateFilename(mudeTIstanza.getMudeTFascicolo().getCodiceFascicolo(), mudeTPratica.getNumeroPratica(), null, filename, documentoPratica.getTipoDocumento().getCodice());
            if(null != duplicateFilenameError){

                logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + duplicateFilenameError.getTitle());

                return Response.serverError().entity(duplicateFilenameError).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(duplicateFilenameError)).build();
            }

            // Verifica estensioni consentite
            ErrorResponse estensioniError = allegatiApiServiceHelper.validateFileExtension(filename);
            if (null != estensioniError) {
                logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + estensioniError);

                return Response.serverError().entity(estensioniError).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(estensioniError)).build();
            }

            TipoDocumentoVO tipoDocumentoVO = tipoDocumentoService.findByCodTipoDocumento(documentoPratica.getTipoDocumento().getCodice());
            VerifyReport verifyReport = null;
            if (!Constants._environment.equals("local") && Constants._pdf_istanza_checks.indexOf("skipVerifySignedDocument") == -1) {
	            // Verifica lunghezza del file
	            ErrorResponse fileLengthError = allegatiApiServiceHelper.valideteFileLength(file);
	            if (null != fileLengthError) {
	                
	                logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + fileLengthError);

	                return Response.serverError().entity(fileLengthError).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(fileLengthError)).build();
	            }
	            
	            verifyReport = indexManager.verifySignedDocument(null, file);
	            if(null == verifyReport){
	                Map<String, String> detail = new HashMap<>();
	                detail.put("file", "Impossibile verificare la validità della firma.");
	                ErrorResponse error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Firma non valida", detail, null);
	                
	                logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + error);

	                return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
	            }
            }

            DocumentoVO vo = new DocumentoVO();
            vo.setIdPratica(mudeTPratica.getId());
            vo.setTipoDocumento(tipoDocumentoVO);
            vo.setDataCaricamento(Calendar.getInstance().getTime());
            
            vo.setNumeroProtocollo(documentoPratica.getNumeroProtocollo());
            vo.setDataProtocollo(documentoPratica.getDataProtocollo());
            
            if(documentoPratica.getNomeFileDocumento() != null)
            	vo.setNomeFileDocumento(documentoPratica.getNomeFileDocumento());
            else if(documentoPraticaJson != null && !"ws".equals(scope)) {
            	vo.setNomeFileDocumento(documentoPraticaJson + "_" + filename);        		
        	}else
        		vo.setNomeFileDocumento(filename);
            vo.setDimensioneFile(file.length());
            vo.setUtente(mudeTUser!=null?utenteEntityMapper.mapEntityToVO(mudeTUser):null);

            //se non esiste creo su index la cartella del fascicolo
            if (StringUtils.isBlank(mudeTIstanza.getMudeTFascicolo().getUuidIndex())) {
            	FascicoloVO fascicoloVO = fascicoloEntityMapper.mapEntityToVO(mudeTIstanza.getMudeTFascicolo(), mudeTUser);
                String uuidIndexFascicolo = indexManager.createIndexFolderFascicolo(fascicoloVO);
                if (StringUtils.isBlank(uuidIndexFascicolo)) {
                    //Map<String, String> detail = new HashMap<>();
                    ErrorResponse error = new ErrorResponse("500", "errore_interno", "impossibile caricare l'allegato ", null, null);
                    logger.error("[PraticheApiServiceImpl::uploadAllegato] ERROR : " + error);
                    logger.error("[PraticheApiServiceImpl::uploadAllegato] ERROR : errore nella creazione della folder fascicolo su index");

                    return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
                }
                //FascicoloVO fascicoloVO = istanzaVO.getFascicoloVO();
                fascicoloVO.setUuidIndex(uuidIndexFascicolo);
                fascicoliService.updateUuidIndex(fascicoloVO, mudeTUser);
            }

            //se non esiste creo su index la cartella della pratica
            if (StringUtils.isBlank(documentoPratica.getFileUID())) {
                String uuidIndexPratica = indexManager.createIndexFolderPratica(mudeTPratica);
                if (StringUtils.isBlank(uuidIndexPratica)) {
                    //Map<String, String> detail = new HashMap<>();
                    ErrorResponse error = new ErrorResponse("500", "errore_interno", "impossibile caricare il documento ", null, null);
                    logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + error);
                    logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : errore nella creazione della folder pratica su index");

                    return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
                }
                mudeTPratica.setUuidIndex(uuidIndexPratica);
                praticaService.updateUuidIndex(mudeTPratica, mudeTUser);
            }

            String uuidIndexFile = indexManager.createIndexDocumentoContent(vo, mudeTPratica, mudeTIstanza.getComune().getIstatComune(), file, mudeTUser != null? mudeTUser.getCf() : userCf);

            if (StringUtils.isNotBlank(uuidIndexFile)) {
                vo.setFileUID(uuidIndexFile);
                saved = praticaService.saveDocumento(vo, mudeTUser);
            }

            if (null == saved) {
            	if(vo.getFileUID() != null) {
                    ErrorMessage errorMessage = indexManager.deleteContenutoByUuid(vo.getFileUID());
                    if(null != errorMessage){
                        logger.error("[PraticheApiServiceImpl::deleteDocumento] ERROR : (Index error) " + errorMessage.toString());

                    }
            	}

                ErrorResponse error = new ErrorResponse("500", "errore_interno", "Errore inaspettato nella gestione della richiesta. Riprova a breve.", null, null);

                logger.error("[PraticheApiServiceImpl::uploadDocumento] ERROR : " + error);

                return Response.serverError().entity(error).status(500).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
            }

        } catch (IOException e) {
            logger.debug("[PraticheApiServiceImpl::uploadDocumento] ERROR :" + e.getMessage());
            e.printStackTrace();
        } 

        return Response.ok(saved).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(saved)).build();
    }
	
	@Override
	public Response downloadDocumento(String uuid, String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "["+ CLS_NAME + "::"+ methodName +"]");

        DocumentoVO vo = praticaService.findByFileUID(uuid);

        File file = indexManager.getFileByUuid(uuid);

        if (null != file) {

        	String mimetype = null;
        	
        	try {
        		mimetype = Files.probeContentType(new File(vo.getNomeFileDocumento()).toPath());
        		
        		if(mimetype == null)
	    			switch(vo.getNomeFileDocumento().toLowerCase().replaceAll("^.*[.](.*)$", "$1")) {
	    			case "p7m":
	    				mimetype = "application/pkcs7-mime";
	    				break;
	    			case "docx":
	    				mimetype = "officedocument.wordprocessingml.document";
	    				break;
	    			case "pdf":
	    				mimetype = "application/pdf";
	    				break;
	    			}
        		
			} catch (Exception skip) { }
        	
            return Response.ok(file, mimetype == null? MediaType.APPLICATION_OCTET_STREAM : mimetype).header(CONTENT_DISPOSITION, "attachment; filename=\"" + vo.getNomeFileDocumento() + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();
        } else {
            ErrorResponse error = new ErrorResponse("404", "errore_interno", "File non trovato ", null, null);

            return Response.serverError().entity(error).status(404).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }
	}

}