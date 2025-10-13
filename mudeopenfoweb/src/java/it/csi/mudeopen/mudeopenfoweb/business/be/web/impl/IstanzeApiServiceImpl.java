/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;
import org.apache.commons.lang.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopenfoweb.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Signature;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.VerifyReport;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.FileServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.IstanzaApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ModelliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TemplateService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.SignedFileVerify;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.UploadIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaIstanzaRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaTitoloSoggettoAbilitatoRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.RuoliIstanzaResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;
/**
 * The type Istanze api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class IstanzeApiServiceImpl extends BaseAPI<IstanzeApi> implements IstanzeApi {
    private static final String FRONTOFFICE = "frontoffice";

	private static final String FIRMA_NON_VALIDA = "Firma non valida";
	private static final String LOCAL = "local";

	private static final String ERRORE_INTERNO = "errore_interno";
	private static final String UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE = "L'utente non è abilitato ad eseguire questa operazione";

	@Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private IndexManager indexManager;

    @Autowired
    private IstanzaApiServiceHelper istanzaApiServiceHelper;

    @Autowired
    private FascicoliService fascicoliService;

    @Autowired
    private IstanzaStatoService istanzaStatoService;

    @Autowired
    MudeTEnteRepository mudeTEnteRepository;
    @Autowired
    private IstanzaUtenteService istanzaUtenteService;

    @Autowired
    private SignedFileVerify signedFileVerify;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;
    
    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

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
    public Response recuperaIstanza(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            IstanzeApi istanzaApi = rtarget.proxy(IstanzeApi.class);
            Response response = istanzaApi.recuperaIstanza(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), idIstanza, securityContext, httpHeaders, httpRequest);
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
	public Response downloadQuadroPDFIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
											 Long idIstanza,
											 Long idQuadro) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, idQuadro);
	}

    @Override
    public Response downloadDocxPDF(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
											 Long idIstanza,
											 String options,
								    		 MultipartFormDataInput input) {
		return callMultipartAPI(userCf, securityContext, httpHeaders, httpRequest, "/istanze/quadro/istanza/"+idIstanza + (options != null?"?"+options : ""), "quadroVO",
				input);
    }

    @Override
    public Response cambiaStatoIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
						    		Long idIstanza, 
						    		String codiceStato,
						    		IstanzaVO istanza,
						    		String scope) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, codiceStato,
        		istanza, 
        		FRONTOFFICE);
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
        		FRONTOFFICE);
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
									Boolean con_firma) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, con_firma);
    }

	@PostConstruct
	public void doInit() {
		Constants._environment = mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_ENVIRONMENT, "");
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	// LOCAL APIs
	/////////////////////////////////////////////////////////////////////////////
	
	
	
    @Override
    public Response salvaIstanza(String userCf, String XRequestId, String XForwardedFor, SalvaIstanzaRequest request, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LoggerUtil.debug(logger, "[IstanzaApiServiceImpl::salvaIstanza]");
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        checkAbilitazioneFascicolo(new String[]{AbilitazioniEnum.FASCIC_CREATORE.getDescription(), AbilitazioniEnum.FASCIC_CREATORE_IST.getDescription()}, request.getIdFascicolo(), mudeTUser, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        IstanzaVO vo = istanzaService.salvaIstanza(request, mudeTUser, httpHeaders);
        return voToResponse(vo, 200);
    }

    @Override
    public Response salvaTitoloSoggettoAbilitato(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, SalvaTitoloSoggettoAbilitatoRequest request, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LoggerUtil.debug(logger, "[IstanzaApiServiceImpl::salvaTitoloSoggettoAbilitato]");
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.COMP_TIT.getDescription(), idIstanza, mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        request.setIdIstanza(idIstanza);
        IstanzaVO vo = istanzaService.salvaTitoloSoggettoAbilitato(request, headerUtil.getUserCF(httpHeaders, false));
        return voToResponse(vo, 200);
    }

    @Override
    public Response aggiungiSoggettoCoinvolto(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza, Long idSoggetto2Replace, SoggettoIstanzaVO soggetto) {
        LoggerUtil.debug(logger, "[IstanzaApiServiceImpl::aggiungiSoggettoPFCoinvolto]");
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        soggetto.setIdIstanza(idIstanza);
        RuoliIstanzaResponse ruoliIstanzaResponse = istanzaService.aggiungiSoggetto(soggetto, idSoggetto2Replace, headerUtil.getUserCF(httpHeaders, false), true, null);
        return voToResponse(ruoliIstanzaResponse, 200);
    }
	
    @Override
    public Response eliminaIstanza(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.CREA_IST.getDescription(), idIstanza, mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        istanzaService.eliminaIstanza(headerUtil.getUserCF(httpHeaders, false), idIstanza);
        return Response.ok().build();
    }

    @Override
    public Response eliminaSoggettoCoinvolto(String userCf, String XRequestId, String XForwardedFor, Long idIstanza, Long idSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.COMP_SOGG_COINV.getDescription(), idIstanza, mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        RuoliIstanzaResponse ruoliIstanzaResponse = istanzaService.eliminaSoggettoCoinvolto(idIstanza, idSoggetto, headerUtil.getUserCF(httpHeaders, false));
        return voToResponse(ruoliIstanzaResponse, 200);
    }

    @Override
    public Response uploadIstanzaFirmata(MultipartFormDataInput input, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        
        IstanzaVO saved = null;
        try {
            String istanzaJson = input.getFormDataPart("istanza", String.class, null);
            File file = input.getFormDataPart("file", File.class, null);
            String filename = istanzaApiServiceHelper.getFileName(input, "file");
            Constants._pdf_istanza_checks = mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_SKIP_PDF_CHECKS, "");
            Constants._mude_allowed_cf_pm = mudeCProprietaRepository.getValueByName(Constants.CONFIG_MUDE_ALLOWED_CF_PM, "");
            // Recupero oggetto dalla stringa
            ObjectMapper mapper = new ObjectMapper();
            UploadIstanzaVO uploadIstanzaVO = mapper.readValue(istanzaJson, UploadIstanzaVO.class);
            IstanzaVO istanzaVO = istanzaService.recuperaIstanza(mudeTUser, uploadIstanzaVO.getIdIstanza());
            if (!StatoIstanza.DA_FIRMARE.getValue().equalsIgnoreCase(istanzaVO.getStatoIstanza().getCodice()))
                throw new BusinessException("Impossibile effettuare l'upload dell'istanza firmata. L'istanza non si trova nello stato 'DA FIRMARE'.");
            checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.UPL_IST.getDescription(), istanzaVO.getIdIstanza(), mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
            // Verifica nome file duplicato
            checkForError(istanzaApiServiceHelper.verifyDuplicateFilename(istanzaVO.getFascicoloVO().getCodiceFascicolo(), istanzaVO.getCodiceIstanza(), filename));
            // Verifica estensioni consentite
            checkForError(istanzaApiServiceHelper.validateFileExtension(filename));
            // Verifica lunghezza del file
            checkForError(istanzaApiServiceHelper.valideteFileLength(file));
            if (!Constants._environment.equals(LOCAL) && Constants._pdf_istanza_checks.indexOf("skipVerifySignedDocument") == -1) {
                // Verifica della firma
                VerifyReport verifyReport = indexManager.verifySignedDocument(null, file);
                Response resp = getVerifySignatureResponse(verifyReport, istanzaVO);
                if (null != resp)
                    return resp;
            }

            saved = saveFileOnIndex(httpHeaders, mudeTUser, saved, file, filename, istanzaVO);
        } catch (IOException e) {
            logger.debug("[IstanzeApiServiceImpl::uploadIstanzaFirmata] ERROR :" + e.getMessage());
            e.printStackTrace();
        }

        return voToResponse(saved, 200);
    }

	private IstanzaVO saveFileOnIndex(HttpHeaders httpHeaders, MudeTUser mudeTUser, IstanzaVO saved, File file,
			String filename, IstanzaVO istanzaVO) throws IOException {
		
		if (!Constants._environment.equals(LOCAL) && Constants._pdf_istanza_checks.indexOf("skipCheckFileHash") == -1) {
		    // Verifica hash del file
		    //verifico che il file che è stato firmato sia effettivamente quello che è stato scaricato per la firma (solo per i p7m)
		    File extracted = indexManager.getExtractedDocumentFromEnvelopeFile(file);
		    byte[] fileBytes = FileUtils.readFileToByteArray(extracted);
		    checkForError(istanzaApiServiceHelper.checkFileHash(mudeTIstanzaRepository.retrievePdfHashcode(istanzaVO.getIdIstanza()), fileBytes));
		}

		//se non esiste creo su index la cartella del fascicolo
		if (StringUtils.isBlank(istanzaVO.getFascicoloVO().getUuidIndex())) {
		    String uuidIndexFascicolo = indexManager.createIndexFolderFascicolo(istanzaVO.getFascicoloVO());
		    if (StringUtils.isBlank(uuidIndexFascicolo))
		    	throw new BusinessException("impossibile caricare l'allegato ", "500", ERRORE_INTERNO, null);
		    FascicoloVO fascicoloVO = istanzaVO.getFascicoloVO();
		    fascicoloVO.setUuidIndex(uuidIndexFascicolo);
		    fascicoliService.updateUuidIndex(fascicoloVO, mudeTUser);
		}

		//se non esiste creo su index la cartella dell'istanza
		if (StringUtils.isBlank(istanzaVO.getUuidIndex())) {
		    String uuidIndexIstanza = indexManager.createIndexFolderIstanza(istanzaVO);
		    if (StringUtils.isBlank(uuidIndexIstanza))
		    	throw new BusinessException("impossibile caricare l'allegato ", "500", ERRORE_INTERNO, null);
		    istanzaVO.setUuidIndex(uuidIndexIstanza);
		    //				istanzaService.updateUuidIndex(istanzaVO, mudeTUser);
		}

		istanzaVO.setFilename(filename);
		istanzaVO.setDimensioneFile(file.length());
		istanzaVO.setDataCaricamentoFileIstanza(Calendar.getInstance().getTime());
		String uuidIndexFile = indexManager.createIndexIstanzaContent(istanzaVO, file);
		if (StringUtils.isNotBlank(uuidIndexFile)) {
		    istanzaVO.setUuidFileIndex(uuidIndexFile);
		    saved = istanzaService.updateDatiFileIstanza(istanzaVO, mudeTUser);
		    // set new state "FIRMATA"
		    istanzaStatoService.saveIstanzaStato(saved.getIdIstanza(), StatoIstanza.FIRMATA.getValue(), null, httpHeaders);
		}

		if (null == saved)
			throw new BusinessException("Errore inaspettato nella gestione della richiesta. Riprova a breve.", "500", ERRORE_INTERNO, null);
		return saved;
	}
	
    @Override
    public Response deleteFileIstanzaFirmata(Long idIstanza, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.UPL_IST.getDescription(), idIstanza, mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        istanzaService.deleteFileIstanzaFirmata(idIstanza, mudeTUser, httpHeaders, true, null);
        return Response.noContent().build();
    }

    @Override
    public Response getRuoliIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza, String excl_user_ids, Boolean loadAbilitazioni) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, excl_user_ids, loadAbilitazioni);
    }

    @Override
    public Response getStatiIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
									Long idIstanza,
									String scope) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, "frontoffice");
    }

    @Override
    public Response recuperaAbilitazioniIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza) {
        return istanzaService.recuperaAbilitazioniIstanza(idIstanza, headerUtil.getUserCF(httpHeaders, false));
    }

    @Override
    public Response listaPossibiliIstanze(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idFascicolo, Long idIstanza, String idTipoIstanza, int page, int size) {
        return istanzaService.listaPossibiliIstanze(getMudeTUser(httpHeaders), idTipoIstanza, idFascicolo, idIstanza, page, size);
    }

    @Override
    public Response istanzeFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo, String filter, String sort, int page, int size, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        return istanzaService.cercaIstanzeFascicolo(idFascicolo, filter, sort, page, size, mudeTUser, null);
    }

    private Response getVerifySignatureResponse(VerifyReport verifyReport, IstanzaVO istanzaVO) {

    	/*
    	 * formatoFirma
			cades = 1
			cades-detached = 2
			m7m = 3
			tsr = 4
			tsd = 5
			pades = 6
    	 */
        if (null == verifyReport) {
            Map<String, String> detail = new HashMap<>();
            detail.put("file", "Impossibile verificare la validità della firma.");
            throw new BusinessException(FIRMA_NON_VALIDA, "403", "errore_validazione", detail);
        } else {
		    if(!mudeCProprietaRepository.getValueByName("INDEX_FORMATO_FIRMA", "1").matches("(?i).*\\b"+verifyReport.getFormatoFirma()+"\\b.*"))
		        throw new BusinessException("Il file non è firmato con formato CADES", "403", Constants.ERRORE_BUSINESS, null);
        	
            //verifica conformità parametri di input
            Integer conformitaParametriInput = verifyReport.getConformitaParametriInput();
            if (conformitaParametriInput < 1) {
                Map<String, String> detail = new HashMap<>();
                detail.put("file", "Il file non è firmato.La firma è richiesta per questa tipologia");
                throw new BusinessException(FIRMA_NON_VALIDA, "403", ERRORE_INTERNO, detail);
            }

            //verifica presenza firma
            if (null == verifyReport.getSignature()) {
                Map<String, String> detail = new HashMap<>();
                detail.put("file", "Il file non è firmato.La firma è richiesta per questa tipologia");
                throw new BusinessException(FIRMA_NON_VALIDA, "403", ERRORE_INTERNO, detail);
            }

            ErrorResponse errorDecode = signedFileVerify.getDecodificaErroriFirma(verifyReport);
            if (errorDecode != null)
                return signedFileVerify.formatErrorMsg(errorDecode);
            Boolean isUserSigner = Boolean.FALSE;
            //verifica corrispondenza cf firma / cf utente con abilitazione PM
            List<Signature> signatures = verifyReport.getSignature();
            List<IstanzaUtenteVO> istanzeUtenti = istanzaUtenteService.findByIstanzaAndCodiceAbilitazione(istanzaVO.getIdIstanza(), new String[]{AbilitazioniEnum.PM_RUP_PM_OPZ.getDescription(), AbilitazioniEnum.PM_RUP_PM_OBB.getDescription(), AbilitazioniEnum.CREATORE_IST_PM_OPZ.getDescription()});
            final boolean isPMMandatory = istanzeUtenti.stream().anyMatch(x -> x.getAbilitazione().getCodice().equals(AbilitazioniEnum.PM_RUP_PM_OBB.getDescription()) );
            
            // find valid CFs based upon the mandatroy PM 
            String validCFs = String.join(" ", istanzeUtenti.stream().map(x -> {
		            		if((isPMMandatory && x.getAbilitazione().getCodice().equals(AbilitazioniEnum.PM_RUP_PM_OBB.getDescription()))
		            				|| (!isPMMandatory && (x.getAbilitazione().getCodice().equals(AbilitazioniEnum.PM_RUP_PM_OPZ.getDescription()) || x.getAbilitazione().getCodice().equals(AbilitazioniEnum.CREATORE_IST_PM_OPZ.getDescription()))))
		                    	return x.getUtente().getContatto().getAnagrafica().getCodiceFiscale();
		            		return "";
                    }).collect(Collectors.toList()));
            
            String signingRoles = mudeCProprietaRepository.getValueByName("RUOLI_FIRMATARI_ISTANZA_" + istanzaVO.getTipologiaIstanza().getId(), "");
            if(signingRoles != null && !(isUserSigner = "SKIP_CF_CHECK".equals(signingRoles)) && signingRoles.length() > 0)
            	validCFs += ", " + String.join(", ", mudeTContattoRepository.findGUIDsByIstanzaRoles(istanzaVO.getIdIstanza(), signingRoles.replaceAll("[ ]+", "").split(",")));
            
            if(!isUserSigner)
	            for (Signature signature : signatures) {
	                String signatureCF = signature.getCodiceFiscale();
	                if (Constants._mude_allowed_cf_pm.indexOf(signatureCF == null ? "SKIP_CF_NULL" : signatureCF) > -1 
	                		|| validCFs.indexOf(signatureCF == null? "" : signatureCF) > -1) {
	                    isUserSigner = Boolean.TRUE;
	                    break;
	                }
	            }

            //fixme valutare se mettere una property per disabilitare il controllo
            if(Constants._environment.equals(LOCAL) || Constants._pdf_istanza_checks.indexOf("skipCFCompare") != -1) {
                logger.info("[IstanzeApiServiceImpl::skipSignedFileVerify!!!!");
            } else if (!isUserSigner) {
                Map<String, String> detail = new HashMap<>();
                detail.put("file", "La firma non appartiene all'utente con abilitazione di PM per l'istanza");
                throw new BusinessException(FIRMA_NON_VALIDA, "403", ERRORE_INTERNO, detail);
            }
        }

        return null;
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

    @Override
    public Response getListaTipoPresentatore(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    			Long idTipoPresentatore) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idTipoPresentatore);
   }
}