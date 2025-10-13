/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzeExt;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.FileServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzeExtRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ModelliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ReportService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TemplateService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloCompilatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.RuoliIstanzaResponse;
@Component
public class IstanzeApiServiceImpl extends AbstractApi implements IstanzeApi {
    private static final String ERRORE_INTERNO = "errore_interno";

	private static final String UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE = "L'utente non è abilitato ad eseguire questa operazione";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	@Autowired
    private IstanzaService istanzaService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private IndexManager indexManager;

    @Autowired
    private IstanzaStatoService istanzaStatoService;

    @Autowired
    private ModelliService modelliService;

    @Autowired
    private FileServiceHelper fileServiceHelper;

    @Autowired
    MudeTEnteRepository mudeTEnteRepository;
    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MudeTIstanzeExtRepository mudeTIstanzeExtRepository;

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

    @Override
    public Response recuperaIstanza(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String scope) {
        LoggerUtil.debug(logger, "[IstanzaApiServiceImpl::recuperaIstanza]");
        MudeTUser mudeTUser = (scope == null || !Constants.SCOPE_WS.equalsIgnoreCase(scope)) ? headerUtil.getUserCF(httpHeaders, false) : null;
        IstanzaVO vo = istanzaService.recuperaIstanza(mudeTUser, idIstanza, scope, scope == null || "frontoffice".equals(scope) ?"frontoffice-istanza" : scope);
        return Response.ok(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
    }

    @Override
    public Response cercaIstanze(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter, String sort, int page, int size, String scope) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        //if (scope != null && scope.equalsIgnoreCase(Constants.SCOPE_WS))
        //    return istanzaService.cercaIstanzeWS(filter, userCf/*codice fruitore*/, scope, page, size);
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        return istanzaService.cercaIstanzeUtente(filter, mudeTUser, scope, page, size, sort);
        //		return PaginationResponseUtil.buildResponse(result.getContent(), page, size, result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public Response downloadTemplate(Long idIstanza, Long idTemplate, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.DOWN_PDF_IST.getDescription(), idIstanza, mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        ModelloCompilatoVO vo = modelliService.loadTemplate(idIstanza, idTemplate);
        if (null == vo) {
            Map<String, String> detail = new HashMap<>();
            detail.put("idTemplate", "Elemento non trovato con id [" + idTemplate + "]");
            ErrorResponse error = new ErrorResponse("404", ERRORE_INTERNO, "Elemento non trovato con id [" + idTemplate + "]", detail, null);
            logger.error("[IstanzeApiServiceImpl::downloadTemplate] ERROR : idTemplate [" + idTemplate + "]\n" + error);
            return Response.serverError().entity(error).status(404).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }

        return Response.ok(vo.getFileContent(), MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + vo.getFilename() + "\"").header(HttpHeaders.CONTENT_LENGTH, vo.getFileContent().length).build();
    }

    @Override
    public Response downloadTemplatePDFIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
									    		Long idIstanza,
									    		String scope, 
									    		String saveAllFilesToDisk) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        if(!"backoffice".equals(scope))
        	checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.DOWN_PDF_IST.getDescription(), idIstanza, mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        IstanzaVO istanza = istanzaService.recuperaIstanza(headerUtil.getUserCF(httpHeaders, false), idIstanza, scope, null);
        if (istanza == null)
            throw new BusinessException("Elemento non trovato con idIstanza [" + idIstanza + "]", "404", null, null);
        try {
            updateFascicoloFolderMetadata(istanza.getFascicoloVO());
        } catch (Throwable e) {
            logger.error("[IstanzeApiServiceImpl::downloadTemplatePDFIstanza] ERROR : idIstanza [" + idIstanza + "] - Errore nell'aggiornamento dei metadati della cartella fascicolo su Index ", e);
        }

        if (istanza.getIdTemplate() == null)
            throw new BusinessException("Template non impostato per l'istanza con id [" + idIstanza + "]", "404", null, null);
        TemplateVO template = templateService.loadTemplateById(istanza.getIdTemplate());
        if (template == null || template.getModello() == null)
            throw new BusinessException("Modello documentale non impostato per l'istanza con id [" + idIstanza + "]");
        ModelloCompilatoVO vo = modelliService.loadTemplatePDF(idIstanza, template.getModello().getId(), mudeTUser, !"prod".equals(Constants._environment) && !StringUtils.isBlank(saveAllFilesToDisk));
        if (null == vo) {
            throw new BusinessException("Modello non trovato con id [" + template.getModello().getId() + "]", "404", null, null);
        }

        String version = "V" + template.getNumeroVersione();
//        String outputFilename = new StringBuilder(istanza.getTipologiaIstanza().getId()).append("_").append(version).append("_").append(istanza.getCodiceIstanza()).append(".pdf").toString();
        String outputFilename = template.getCodTemplate() + "_" + istanza.getCodiceIstanza() + ".pdf";        
        return Response.ok(vo.getFileContent(), MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + outputFilename + "\"").header(HttpHeaders.CONTENT_LENGTH, vo.getFileContent().length).build();
    }

    @Override
    public Response downloadDocxPDF(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
											 Long idIstanza,
											 String options,
								    		 MultipartFormDataInput input) {
        String filename = fileServiceHelper.getFileName(input, "file");
        byte[] outputBinary = modelliService.renderQuadroPDFFromDocx(idIstanza, input, options);
        String outputFilename = filename.substring(0, filename.lastIndexOf('.')) + ".pdf";
        return Response.ok(outputBinary, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + outputFilename + "\"").header(HttpHeaders.CONTENT_LENGTH, outputBinary.length).build();
    }

    @Override
    public Response downloadQuadroPDFIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
											 Long idIstanza,
								    		 Long idQuadro) {
    	ModelloVO modelloVO = modelliService.loadQuadroPDF(idIstanza, idQuadro);
    	String name = modelloVO.getDenominazione();
    	if(modelloVO.getDenominazione().lastIndexOf('.') > -1)
    		name = modelloVO.getDenominazione().substring(0, modelloVO.getDenominazione().lastIndexOf('.'));
        String outputFilename = name + ".pdf";
        return Response.ok(modelloVO.getFileContent(), MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + outputFilename + "\"").header(HttpHeaders.CONTENT_LENGTH, modelloVO.getFileContent().length).build();
    }

    private void updateFascicoloFolderMetadata(FascicoloVO fascicolo) throws Throwable {

        if (!StringUtils.isBlank(fascicolo.getUuidIndex())) {
            String uuidIndexFascicolo = indexManager.updateIndexFolderFascicolo(fascicolo);
            if (StringUtils.isBlank(uuidIndexFascicolo) || !uuidIndexFascicolo.equalsIgnoreCase(fascicolo.getUuidIndex())) {
                Map<String, String> detail = new HashMap<>();
                ErrorResponse error = new ErrorResponse("500", ERRORE_INTERNO, "impossibile aggiornare i metadati del fascicolo [" + fascicolo.getCodiceFascicolo() + "]", null, null);
                logger.error("[IstanzeApiServiceImpl::updateFascicoloFolderMetadata] ERROR : " + error);
                logger.error("[IstanzeApiServiceImpl::updateFascicoloFolderMetadata] ERROR : errore nell'aggiornamento dei metadati della folder fascicolo [" + fascicolo.getCodiceFascicolo() + "] su index");
            }
        }
    }

    @Override
    public Response listaPossibiliIstanze(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idFascicolo, Long idIstanza, String idTipoIstanza, int page, int size) {
        return istanzaService.listaPossibiliIstanze(getMudeTUser(httpHeaders), idTipoIstanza, idFascicolo, idIstanza, page, size);
    }

    @Override
    public Response cambiaStatoIstanza(String userCf, String XRequestId, 
    		String XForwardedFor, SecurityContext securityContext, 
    		HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    		Long idIstanza, String codStatus, 
    		IstanzaVO istanza,
    		String scope) {
        MudeTUser mudeTUser = null;
        if(!Constants.SCOPE_WS.equals(scope))
        	mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        // Check ruoli sul cambio stato
        /*MudeTEnte enteRiferimento = mudeTEnteRepository.getEnteRiceventeByUtenteIstanza(
        		mudeTUser.getIdUser(),
        		idIstanza);
    	
    	if(enteRiferimento == null)
    		throw new BusinessException("Non sono presenti i ruoli necessari per gestire il cambio stato. Impossibile inviare la richiesta.");
    	*/
        String jsonFormIO = "";
        if(istanza != null)
        	jsonFormIO = istanza.getJsonData();
        // Inserimento dati pratica da json data
        if(jsonFormIO != null) {
        	try {
	        	JSONObject objJson = new JSONObject(jsonFormIO);
		        if(objJson.has("numPratica")) {
		        	String numeroPratica = objJson.getString("numPratica");
		        	if(numeroPratica != null && !numeroPratica.isEmpty()
		        						&& !"{}".equals(numeroPratica)) {
		        		int iMinus = numeroPratica.lastIndexOf("-");
			        	if(iMinus != -1) {
			        		istanza.setNumeroPratica(numeroPratica.substring(0, iMinus).trim());
			        		istanza.setAnno(Long.valueOf(numeroPratica.substring(iMinus+1).trim()));
			        	}
		        	}		
		        }

		        if(objJson.has("numPraticaNew") && 
		        		(istanza.getNumeroPratica()==null || StringUtils.isBlank(istanza.getNumeroPratica()))) {
	        		istanza.setNumeroPratica(objJson.getString("numPraticaNew"));
	        		istanza.setIdPratica(9999L);
		        
		        	if(objJson.has("anno")) 
		        		istanza.setAnno(objJson.getLong("anno"));
		        }	
		        
		        if(objJson.has("data_delibera")) {
			        String data_protocollo = objJson.getString("data_delibera");
			        
			        if(data_protocollo!=null) {
			        	String pattern = "dd/MM/yyyy";
			        	SimpleDateFormat df = new SimpleDateFormat(pattern); 
			        	try {
			        		istanza.setDataProtocollo(utilsDate.asLocalDateTime(df.parse(data_protocollo)));
			        	} catch (Exception skip) { 
			        		istanza.setDataProtocollo(utilsDate.asLocalDateTime(new Date()));
			        	}
			        }
		        }

		        if(objJson.has("numero")) 
		        	istanza.setNumeroProtocollo(objJson.getString("numero"));
		        
		        if(objJson.has("responsabile"))
		        	istanza.setResponsabile_procedimento(objJson.getString("responsabile"));
        	} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }

        boolean changeStatus = istanzaStatoService.saveIstanzaStato(mudeTUser, userCf, idIstanza, codStatus, istanza, httpHeaders, scope, jsonFormIO);
        return Response.ok(changeStatus).build();
    }

    @Override
    public Response downloadIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza, Boolean con_firma, String scope) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        MudeTIstanzaSlim istanza = istanzaService.recuperaIstanzaSlim(idIstanza);
        String filename = istanza.getNomeFileIstanza();
        
        if (filename == null)
            filename = istanza.getIdTipoIstanza() + "_" + istanza.getCodiceIstanza() + ".pdf.p7m";
        
        File file = indexManager.getFileByUuid(istanza.getIndexNode());
        if (null == file)
            if (null == file)
                throw new BusinessException("File non trovato [" + filename + "]", "404", ERRORE_INTERNO, null);
        
        if (!(con_firma != null && !con_firma))
            return Response.ok(file, istanza.getNomeFileIstanza() != null && istanza.getNomeFileIstanza().toLowerCase().endsWith(".pdf")?
            							"application/pdf" : "application/pkcs7-mime"
            					).header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();

        file = indexManager.getExtractedDocumentFromEnvelopeFile(file);
        return Response.ok(file, "application/pdf").header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename.replaceAll("[.][pP]7[mM]$", "") + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length()).build();
    }
    
    @Override
    public Response loadTipiStatoIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<SelectVO> listStatiVO = istanzaStatoService.recuperaStatiFiltroVeloce();
        return Response.ok(listStatiVO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(listStatiVO)).build();
    }

	@Override
	public Response downloadRicevutaPdf(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idIstanza) {
    	byte[] file = null;
    	
    	MudeTIstanzeExt mudeTIstanzeExtDB = mudeTIstanzeExtRepository.findOne(idIstanza);
    	
    	if (mudeTIstanzeExtDB!=null) {
    		if (mudeTIstanzeExtDB.isRicevutaPdfContent()) {
        		file = mudeTIstanzeExtDB.getRicevutaPdfContent();
    		}

    		else {
    			MudeTUser mudeTUser; 
    			if(!StringUtils.isBlank(userCf))
    				mudeTUser = mudeTUserRepository.findByCf(userCf);
    			else 
    				mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        		
        		file = reportService.generaReportPDFToByte(idIstanza, mudeTUser, mudeTIstanzeExtDB.getDataGenerazione());
    		}
    	}

    	else {
    		MudeTUser mudeTUser = new MudeTUser(); 
    		if (userCf!=null) {mudeTUser = mudeTUserRepository.findByCf(userCf);}
    		//MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
    		file = reportService.generaReportPDFToByte(idIstanza, mudeTUser, new Timestamp(System.currentTimeMillis()));
    	}
    	
    	if (file!=null) {
    		String outputFilename = new StringBuilder().append("ricevuta").append(".pdf").toString();
    	    return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + outputFilename + "\"").header(HttpHeaders.CONTENT_LENGTH, file.length).build();
    	}

    	else {
    		Map<String, String> detail = new HashMap<>();
            detail.put("idIstanza", "Elemento non trovato con id [" + idIstanza + "]");
            ErrorResponse error = new ErrorResponse("404", ERRORE_INTERNO, "Elemento non trovato con id [" + idIstanza + "]", detail, null);
            logger.error("[IstanzeApiServiceImpl::downloadRicevutaPdf] ERROR : idIstanza [" + idIstanza + "]\n" + error);
            return Response.serverError().entity(error).status(404).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
    	}
    }
	
    @Override
    public Response getListaTipoPresentatore(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    			Long idTipoPresentatore) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        List<SelectVO> vos = istanzaService.getListaTipoPresentatore(null, idTipoPresentatore);
        return voToResponse(vos, 200);
    }

    @Override
    public Response getRuoliIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    			Long idIstanza, String excl_user_ids, Boolean loadAbilitazioni) {
        RuoliIstanzaResponse ruoliIstanzaResponse = istanzaService.getRuoliIstanza(headerUtil.getUserCF(httpHeaders, false), idIstanza, excl_user_ids, loadAbilitazioni);
        return voToResponse(ruoliIstanzaResponse, 200);
    }

    @Override
    public Response getStatiIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
									Long idIstanza,
									String scope) {
    	List<IstanzaStatoVO> vo = istanzaStatoService.getStatiIstanza(idIstanza, scope);
        return voToResponse(vo, 200);
    }
    
}