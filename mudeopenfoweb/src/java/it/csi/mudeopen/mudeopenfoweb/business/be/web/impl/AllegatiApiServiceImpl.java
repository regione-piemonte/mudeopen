/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.MDC;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopenfoweb.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.VerifyReport;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.AllegatiApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AllegatiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaTemplateQuadroService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoAllegatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.SignedFileVerify;
/**
 * The type Allegati api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.UploadAllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.IstanzaTemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.commons.lang.StringUtils;
@Component
@Transactional
public class AllegatiApiServiceImpl  extends BaseAPI<AllegatiApi> implements AllegatiApi {

    private static final String IMPOSSIBILE_CARICARE_L_ALLEGATO = "Impossibile caricare l'allegato ";

	private static final String QDR_ALLEGATI = "QDR_ALLEGATI";
	private static final String QDR_PAGAMENTO = "QDR_PAGAMENTO";

	private static final String UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE = "L'utente non è abilitato ad eseguire questa operazione";

	@Autowired
    private AllegatiService allegatiService;

    @Autowired
    private FascicoliService fascicoliService;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private TipoAllegatoService tipoAllegatoService;

    @Autowired
    private AllegatiApiServiceHelper allegatiApiServiceHelper;

    @Autowired
    private IndexManager indexManager;

    @Autowired
    private ManagerAbilitazioni managerAbilitazioni;

    @Autowired
    private SignedFileVerify signedFileVerify;

    @Autowired
    public IstanzaTemplateQuadroService istanzaTemplateQuadroService;

    @Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Override
    public Response uploadAllegato(MultipartFormDataInput input, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        AllegatoVO vo = null;
        try {
            String allegatoIstanzaJson = input.getFormDataPart("allegatoIstanza", String.class, null);
            File file = input.getFormDataPart("file", File.class, null);

            // Recupero oggetto dalla stringa
            ObjectMapper mapper = new ObjectMapper();
            final UploadAllegatoVO allegatoIstanza = mapper.readValue(allegatoIstanzaJson, UploadAllegatoVO.class);

            JSONObject newAttach = new JSONObject(allegatoIstanza.getJsondata());
            String filename = getUploadFileName(input, allegatoIstanza, newAttach).replaceAll("[.]{2,}", ".").replaceAll("([.][pP]7[mM]){2,}", ".p7m"); // g16

            IstanzaVO istanzaVO = getIstanzaVO(httpHeaders, mudeTUser, allegatoIstanza);
            doUploadChecks(file, allegatoIstanza, filename, istanzaVO);

            TipoAllegatoVO tipoAllegatoVO = tipoAllegatoService.findByCodTipoAllegato(allegatoIstanza.getCodTipoAllegato());
            VerifyReport verifyReport = getVerifyReport(file, tipoAllegatoVO);
		    if(verifyReport != null) {
			    String extensions = mudeCProprietaRepository.getValueByName("INDEX_ESTENSIONE_ALLEGATO_FIRMATO", ".pdf.p7m");
			    
			    boolean foundExt = false;
			    for(String ext : extensions.split(","))
			    	if(filename.matches("(?i).*\\b"+ext.trim()+"\\b"))
			    		foundExt = true;

		    	if(!foundExt)
			        throw new BusinessException("Il file firmato deve avere estensione: " +extensions, "403", Constants.ERRORE_BUSINESS, null);
		    }
            	
            
			//###################################### 
			//verifica validità firma (se presente!)
			//######################################
        	
            Response isSignNotOk = verifySign(verifyReport);
            if(isSignNotOk != null)
            	return isSignNotOk;
            vo = createAllegatoVO(file, allegatoIstanza, filename, istanzaVO, verifyReport, tipoAllegatoVO);

			//######################################           
			//verifica il myme-type del file..
			//######################################
            Response isErr = verifyIndexMimeType(vo, file, verifyReport);
            if(isErr != null)
            	return isErr;

            String uuidIndexFile = doIndexChecks(mudeTUser, file, istanzaVO, vo);
            	
            vo.setFileUID(uuidIndexFile);
            vo = allegatiService.saveAllegato(vo, mudeTUser);

            JSONObject mainQuadroJDATA = createJsonData(allegatoIstanza, newAttach, istanzaVO, vo, allegatoIstanza.isDoNotsaveJdata()?  QDR_PAGAMENTO : QDR_ALLEGATI);

        	saveTermplateQuadro(httpHeaders, mudeTUser, allegatoIstanza, mainQuadroJDATA);

      		vo.setDescBreveAllegato(mainQuadroJDATA.toString());

            return voToResponse(vo, 200);
        } catch (BusinessException be) {
        	if(vo != null)
        		rollbackIndex(vo.getFileUID());
        	throw be;
        } catch (Throwable e) {
            handleUploadException(vo, e);
        }

        return null;
    }

	/**
	 * @param verifyReport
	 */
	private Response verifySign(VerifyReport verifyReport) {

		if(verifyReport != null) {
			ErrorResponse errorDecode = signedFileVerify.getDecodificaErroriFirma(verifyReport);
		    if(errorDecode!=null) 
		    	return signedFileVerify.formatErrorMsg(errorDecode);	            
         }
		
		return null;
	}

	/**
	 * @param vo
	 * @param file
	 * @param verifyReport
	 */
	private Response verifyIndexMimeType(AllegatoVO vo, File file, VerifyReport verifyReport) {

		if(verifyReport != null) {        	   
        	   ErrorResponse errorMymeType = signedFileVerify.verifyMimeTypeSignedFile(file, vo);
	           if(errorMymeType!=null) 
	            	return signedFileVerify.formatErrorMsg(errorMymeType);	                    	   
           }else {
        	   ErrorResponse errorMymeTypeBin = signedFileVerify.verifyMimeTypeBinary(file, vo);
	           if(errorMymeTypeBin!=null) 
	            	return signedFileVerify.formatErrorMsg(errorMymeTypeBin);	                    	           	           	   
           }
		
		return null;
	}

	/**
	 * @param e
	 */
	private void handleUploadException(AllegatoVO vo, Throwable e) {

		logger.debug("[AllegatiApiServiceImpl::uploadAllegato] ERROR :" + e.getMessage());
		if(vo != null)
			rollbackIndex(vo.getFileUID());
		throw new BusinessException("Non e' stato possibile salvare l'allegato. Riprovare in seguito.");
	}

	/**
	 * @param allegatoIstanza
	 * @param newAttach
	 * @param istanzaVO
	 * @param vo
	 * @return
	 * @throws JSONException
	 */
	private JSONObject createJsonData(final UploadAllegatoVO allegatoIstanza, 
										JSONObject newAttach, 
										IstanzaVO istanzaVO,
										AllegatoVO vo,
										String quadroTag) throws JSONException {
		JSONObject jsonDataIstanza;
		if(istanzaVO.getJsonData() == null)
			jsonDataIstanza = new JSONObject();
		else
		    jsonDataIstanza = new JSONObject(istanzaVO.getJsonData());
		
		JSONObject mainQuadroJDATA = null;
		if(jsonDataIstanza.has(quadroTag))
			mainQuadroJDATA = (JSONObject)jsonDataIstanza.get(quadroTag);
		else
			jsonDataIstanza.put(quadroTag, mainQuadroJDATA = new JSONObject());

		JSONArray jsonArray = new JSONArray();
		if(mainQuadroJDATA.has(allegatoIstanza.getCodTipoAllegato()))
			jsonArray = (JSONArray)mainQuadroJDATA.get(allegatoIstanza.getCodTipoAllegato());
		
		newAttach.put("id", vo.getId());
		newAttach.put("nodeIndex", vo.getFileUID());
		newAttach.put("name", vo.getNomeFileAllegato().substring(vo.getNomeFileAllegato().indexOf("_")+1).replaceAll("[.]{2,}", "."));
		
		jsonArray.put(newAttach);
		mainQuadroJDATA.put(allegatoIstanza.getCodTipoAllegato(), jsonArray);
		return mainQuadroJDATA;
	}

	/**
	 * @param input
	 * @param allegatoIstanza
	 * @param newAttach
	 * @return
	 * @throws JSONException
	 */
	private String getUploadFileName(MultipartFormDataInput input, final UploadAllegatoVO allegatoIstanza,
									JSONObject newAttach) throws JSONException {
		String filename = allegatiApiServiceHelper.getFileName(input, "file");
		if(newAttach.has("name")) 
			newAttach.put("name", filename);
		
		filename = new StringBuilder(allegatoIstanza.getCodTipoAllegato()).append("_").append(filename).toString();
		return filename;
	}

	/**
	 * @param httpHeaders
	 * @param mudeTUser
	 * @param allegatoIstanza
	 * @param mainQuadroJDATA
	 */
	private void saveTermplateQuadro(HttpHeaders httpHeaders, MudeTUser mudeTUser,
									final UploadAllegatoVO allegatoIstanza, 
									JSONObject mainQuadroJDATA) {
		final String quadroJson = mainQuadroJDATA.toString();
		IstanzaTemplateQuadroVO istanzaTemplateQuadro = new IstanzaTemplateQuadroVO();
		IstanzaVO istanza = new IstanzaVO();
		istanza.setIdIstanza(allegatoIstanza.getIdIstanza());
		istanzaTemplateQuadro.setIstanza(istanza);
		TemplateQuadroVO templateQuadro = new TemplateQuadroVO();
		templateQuadro.setIdTemplateQuadro(allegatoIstanza.getIdTemplateQuadro());
		istanzaTemplateQuadro.setTemplateQuadro(templateQuadro);
		istanzaTemplateQuadro.setJsonDataQuadro(quadroJson);
		IstanzaTemplateQuadroVO jsonDataNewUpload = istanzaTemplateQuadroService.saveIstanzaTemplateQuadro(istanzaTemplateQuadro, true, allegatoIstanza.getQuadroValidated(), mudeTUser, httpHeaders);
	}

	/**
	 * @param mudeTUser
	 * @param file
	 * @param istanzaVO
	 * @param vo
	 * @return
	 */
	private String doIndexChecks(MudeTUser mudeTUser, File file, IstanzaVO istanzaVO, AllegatoVO vo) {

		//se non esiste creo su index la cartella del fascicolo
		if (StringUtils.isBlank(istanzaVO.getFascicoloVO().getUuidIndex()) ) {
		    String uuidIndexFascicolo = indexManager.createIndexFolderFascicolo(istanzaVO.getFascicoloVO());
		    if (StringUtils.isBlank(uuidIndexFascicolo))
		        throw new BusinessException(IMPOSSIBILE_CARICARE_L_ALLEGATO, "500", Constants.ERRORE_BUSINESS, null);

		    FascicoloVO fascicoloVO = istanzaVO.getFascicoloVO();
		    fascicoloVO.setUuidIndex(uuidIndexFascicolo);
		    fascicoliService.updateUuidIndex(fascicoloVO, mudeTUser);
		}

		//se non esiste creo su index la cartella dell'istanza
		if (StringUtils.isBlank(istanzaVO.getUuidIndex())) {
		    String uuidIndexIstanza = indexManager.createIndexFolderIstanza(istanzaVO);
		    if (StringUtils.isBlank(uuidIndexIstanza))
		        throw new BusinessException(IMPOSSIBILE_CARICARE_L_ALLEGATO, "500", Constants.ERRORE_BUSINESS, null);

		    istanzaVO.setUuidIndex(uuidIndexIstanza);
		    istanzaService.updateUuidIndex(istanzaVO, mudeTUser);
		}

		String uuidIndexFile = indexManager.createIndexAllegatoContent(vo, file, mudeTUser);
		if (StringUtils.isBlank(uuidIndexFile))
		    throw new BusinessException(IMPOSSIBILE_CARICARE_L_ALLEGATO, "500", Constants.ERRORE_BUSINESS, null);
		return uuidIndexFile;
	}

	/**
	 * @param file
	 * @param tipoAllegatoVO
	 * @return
	 */
	private VerifyReport getVerifyReport(File file, TipoAllegatoVO tipoAllegatoVO) {

		VerifyReport verifyReport = null;
		if(tipoAllegatoVO.getFirmaObbligatoria()){
		    verifyReport = indexManager.verifySignedDocument(null, file );
		    if(null == verifyReport)
		        throw new BusinessException("Impossibile verificare la validità della firma.", "403", Constants.ERRORE_BUSINESS, null);
		    if(verifyReport.getSignature() == null || verifyReport.getSignature().size() == 0)
		        throw new BusinessException("Il file non è firmato. La firma è richiesta per questa tipologia", "403", Constants.ERRORE_BUSINESS, null);
		    if(!mudeCProprietaRepository.getValueByName("INDEX_FORMATO_FIRMA", "1").matches("(?i).*\\b"+verifyReport.getFormatoFirma()+"\\b.*"))
		        throw new BusinessException("Il file non è firmato con formato CADES", "403", Constants.ERRORE_BUSINESS, null);
		}

		return verifyReport;
	}

	/**
	 * @param file
	 * @param allegatoIstanza
	 * @param filename
	 * @param istanzaVO
	 */
	private void doUploadChecks(File file, final UploadAllegatoVO allegatoIstanza, String filename,

			IstanzaVO istanzaVO) {
		// Verifica nome file duplicato
		ErrorResponse duplicateFilenameError = allegatiApiServiceHelper.verifyDuplicateFilename(istanzaVO.getFascicoloVO().getCodiceFascicolo(), istanzaVO.getCodiceIstanza(),istanzaVO.getIdIstanza(),filename, allegatoIstanza.getCodTipoAllegato());
		if(null != duplicateFilenameError)
		    throw new BusinessException(duplicateFilenameError.getTitle(), "403", Constants.ERRORE_INTERNO, null);

		// Verifica estensioni consentite
		ErrorResponse estensioniError = allegatiApiServiceHelper.validateFileExtension(filename);
		if (null != estensioniError)
		    throw new BusinessException(estensioniError.getTitle(), "403", Constants.ERRORE_INTERNO, null);

		// Verifica lunghezza del file
		ErrorResponse fileLengthError = allegatiApiServiceHelper.valideteFileLength(file);
		if(null != fileLengthError)
		    throw new BusinessException(fileLengthError.getTitle(), "403", Constants.ERRORE_INTERNO, null);
	}

	/**
	 * @param httpHeaders
	 * @param mudeTUser
	 * @param allegatoIstanza
	 * @return
	 */
	private IstanzaVO getIstanzaVO(HttpHeaders httpHeaders, MudeTUser mudeTUser,
									final UploadAllegatoVO allegatoIstanza) {
		IstanzaVO istanzaVO = istanzaService.recuperaIstanza(mudeTUser, allegatoIstanza.getIdIstanza());
		if(!managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(true, new String[]{FunzioniAbilitazioniEnum.UPLOAD_ALLEG.getDescription()}, istanzaVO.getIdIstanza(), mudeTUser, httpHeaders)
		        || !managerAbilitazioni.hasUtenteAbilitazioneAllegatiPerIstanza(allegatoIstanza.getCodTipoAllegato(), istanzaVO.getIdIstanza(), istanzaVO.getIdTemplate(), mudeTUser, httpHeaders))
		    throw new BusinessException(UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
		return istanzaVO;
	}

	/**
	 * @param file
	 * @param allegatoIstanza
	 * @param filename
	 * @param istanzaVO
	 * @param verifyReport
	 * @param tipoAllegatoVO
	 * @return
	 */
	private AllegatoVO createAllegatoVO(File file, final UploadAllegatoVO allegatoIstanza, String filename,

			IstanzaVO istanzaVO, VerifyReport verifyReport, TipoAllegatoVO tipoAllegatoVO) {
       AllegatoVO vo = new AllegatoVO();
       vo.setIstanza(istanzaVO);
       vo.setFascicolo(istanzaVO.getFascicoloVO());
       vo.setTipoAllegato(tipoAllegatoVO);
       vo.setDescBreveAllegato(allegatoIstanza.getDescBreveAllegato());
       vo.setFirmato(false);
       vo.setDataCaricamento(Calendar.getInstance().getTime());
       vo.setNomeFileAllegato(filename);
       vo.setDimensioneFile(file.length());
       return vo;
	}

    private void rollbackIndex(String indexUID) {
    	if(indexUID == null) return;

        ErrorMessage errorMessage = indexManager.deleteContenutoByUuid(indexUID);
        if(null != errorMessage)
            logger.error("[AllegatiApiServiceImpl::deleteAllegato] ERROR : (Index error) " + errorMessage.toString());
    }

    @Override
    public Response deleteAllegato(Long idAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        AllegatoVO vo = allegatiService.loadAllegato(idAllegato);

        checkAbilitazioneFunzioneIstanza(true, FunzioniAbilitazioniEnum.UPLOAD_ALLEG.getDescription(), vo.getIstanza().getIdIstanza(), mudeTUser, httpHeaders, UTENTE_NON_ABILITATO_AD_ESEGUIRE_QUESTA_OPERAZIONE);
        if(managerAbilitazioni.hasUtenteAbilitazionePerIstanza(true, AbilitazioniEnum.PROF_SPEC.getDescription(), vo.getIstanza().getIdIstanza(), mudeTUser, httpHeaders)
        		&& mudeTUser.getIdUser() != vo.getUtente().getId())
	        throw new BusinessException("Non e' consentito cancellare gli allegati inseriti da altri utenti");

        if(null != vo) {
            if(!mudeTAllegatoRepository.canDeleteAttach(idAllegato))
    	        throw new BusinessException("Non è consentito cancellare gli allegati in una istanza che non sia in stato BOZZA");
        	
            ErrorMessage errorMessage = indexManager.deleteContenutoByUuid(vo.getFileUID());
            if(null != errorMessage)
    	        throw new BusinessException(errorMessage.getTitle(), "500", Constants.ERRORE_INTERNO, null);

            allegatiService.deleteAllegato(idAllegato);
        }

        return Response.noContent().build();
    }

    @Override
    public Response loadAllegato(Long idAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        AllegatoVO vo = allegatiService.loadAllegato(idAllegato);
        if (null == vo) {
            Map<String, String> detail = new HashMap<>();
            detail.put("idAllegato", "Elemento non trovato con id [" + idAllegato + "]");
        	throw new BusinessException("Elemento non trovato con id [" + idAllegato + "]", "404", Constants.ERRORE_INTERNO, detail);
        }

        return voToResponse(vo, 200);
    }

    @Override
    public Response downloadAllegato(String uuid, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String scope,Boolean con_firma) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));

            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            AllegatiApi allegatiApi = rtarget.proxy(AllegatiApi.class);

            Response response = allegatiApi.downloadAllegato(uuid, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest, scope, con_firma);

            return response;
        }
        catch(Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response loadAllegatiIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
							    		Long idIstanza, 
							    		String tipo_allegato,
		    							String scope) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, tipo_allegato,
					   scope);
    }
}