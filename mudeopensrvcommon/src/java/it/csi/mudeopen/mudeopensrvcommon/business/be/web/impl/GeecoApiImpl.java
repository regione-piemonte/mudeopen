/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.csi.ecogis.geeco_java_client.dto.internal.Feature;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSelected;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSession;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.GeecoServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.LoccsiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.SvistaServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.factory.GeecoServiceFactory;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSelectedCllbkRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSelectedRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSessionRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaTemplateQuadroService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.GeecoApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.GeecoCentralizedDataVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.GeecoConfigurationVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.GeecoGeometryVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.SavedDataVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.IstanzaTemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
/**
 * 
http://tst-extga.territorio.csi.it/gisutils/console
 * 
 * 
 * @author guideluc
 *
 */
@Component
public class GeecoApiImpl extends AbstractApi implements GeecoApi{

    @Autowired
	private GeecoServiceHelper geecoServiceHelper;

    private GeecoGeometryVO geometryVO=null;
    private String geecoSessionId=null;

    @Autowired
    protected UserUtil userUtil;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private SvistaServiceHelper svistaService;

    @Autowired
    MudeTGeecoSessionRepository mudeTGeecoSessionRepository;
    
    @Autowired
    private MudeTGeecoSelectedCllbkRepository mudeTGeecoSelectedCllbkRepository;

    @Autowired
    MudeTGeecoSelectedRepository geecoSelectedRepo;

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private IstanzaTemplateQuadroService istanzaTemplateQuadroService;

    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    private LoccsiServiceHelper loccsiServiceHelper;

    /**
     * 
     * 
     * 
     */
    public Response getGeecoConfiguration(
			@HeaderParam("user-cf") String userCf, 
			@HeaderParam ("X-Request-ID") String XRequestId, 
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,    		
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest , 
			@PathParam("idIstanza") Long idIstanza
    ) {
    	
    	//------------------------------------------------------
    	MudeTUser mudeTUser = userUtil.getUserCF(httpHeaders, false);
    	//------------------------------------------------------
    	//@TODO: verificare perche il metodo non funge..
    	String codiceBelfioreComune=null;
    	IstanzaVO istanzaVO = istanzaService.recuperaIstanza(mudeTUser, idIstanza);
    	ComuneVO comune=istanzaVO.getComune() ;
    	codiceBelfioreComune=comune.getCodBelfiore();
    	//codiceBelfioreComune="A182";
    	//------------------------------------------------------
    	logger.info(" codiceBelfioreComune "+codiceBelfioreComune);
    	Date date = new Date();
    	//    	
    	//String sGeoJson=svistaService.getGeoJSONComune(codiceBelfioreComune);
    	String featuresGeometry=geecoServiceHelper.getJsonByCodiceBelfiore(codiceBelfioreComune);
    	
    	logger.info(" sGeoJson: "+featuresGeometry);    	    	
    	Feature featureFocus=svistaService.getFeatureFromGeoJson(featuresGeometry,codiceBelfioreComune); 
    	
    	String urlQuit=geecoServiceHelper.getUrlQuit()+idIstanza;
		String uuid=geecoServiceHelper.getUuid();
		String env=geecoServiceHelper.getEnv();
		String json=null;
		String configuratinURL=null;
		try {
			//Genera il json 		
			if(featureFocus!=null) {
				GeecoServiceFactory factory=new GeecoServiceFactory(codiceBelfioreComune);
				json = factory.initializeGeecoConfiguration(env,urlQuit,featureFocus.getGeometry());				
			}
			
			//Ritorna l'url per angular..
			configuratinURL = geecoServiceHelper.callGeecoConfigurationService(json, codiceBelfioreComune, idIstanza, featuresGeometry, geecoServiceHelper.getFirsGeometryPoint(idIstanza, null));
			//Recupera il gecco session id
			geecoSessionId=GeecoServiceFactory.getGeecoSessionId(configuratinURL);
			
			Long idSessione=null;
			//----------------------------------------------------						
			MudeTGeecoSession geecoSession=null;//new MudeTGeecoSession();								
			//List<MudeTGeecoSession> listGeecoSession=mudeTGeecoSessionRepository.findByIdIstanza(idIstanza);
			//if(listGeecoSession!=null && !listGeecoSession.isEmpty())
			//	geecoSession=listGeecoSession.get(0);
			//if(geecoSession==null)
				geecoSession=new MudeTGeecoSession();
			
			geecoSession.setIdIstanza(idIstanza);
			geecoSession.setIdLocalizzazione(0L);			
		    //----------------------------------------------------			
			//Viene sovrascitto l'id di sessione Geeco
			geecoSession.setSessioneGeeco(geecoSessionId);
		    //Storicizza la sessione geeco
		    geecoSession.setUrl_Geeco(configuratinURL);
		    if(geecoSession.getDataInizio()==null)
		    	geecoSession.setDataInizio(date);
		    else
		    	geecoSession.setDataModifica(date);
		    
		    // set the user that has initiated the geeco session
		    geecoSession.setMudeTUser(mudeTUser);
		    
		    //Storicizza la sessione geeco
		    mudeTGeecoSessionRepository.saveDAO(geecoSession);			
			//----------------------------------------------------			
		} catch (Exception e) {
            LoggerUtil.error(logger, "errore nella chiamam verso geeco : " + e.getMessage());
            throw new BusinessException("Errore generico durante nei processi interni. Se il problema persiste, contattare l'amministratore del sistema per comunicargli l'errore.");
		}
		GeecoConfigurationVO resultVO=new GeecoConfigurationVO();
		resultVO.setConfigurationUrl(configuratinURL);
		resultVO.setGeecoSessionId(geecoSessionId);
		return voToResponse(resultVO,200);
    }

    //###########################################################
    public Response getSelectedData(
			String userCf, 
			String XRequestId, 
			String XForwardedFor,    		    		
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest  , 
			Long idIstanza
    ) {

    	MudeTUser mudeTUser = userUtil.getUserCF(httpHeaders, false);

		String result=null;
		
    	String uuid=geecoServiceHelper.getUuid();
    	String env=geecoServiceHelper.getEnv();
    	String versione="1.0.0";
    	
    	String geecoSessioinID = getLatestGeecoSession(mudeTUser, idIstanza).getSessioneGeeco();

    	//String centralizedURL="http://tst-extga.territorio.csi.it/cgsiapi/"+env+"/"+versione+"/TOREPLACE/data-saved/by-app-id/"+uuid;
    	//centralizedURL=centralizedURL.replaceAll("TOREPLACE", geecoSessioinID);
    	
    	List<GeecoCentralizedDataVO> geecoSavedData=null; 
    	
		try {
	    	String centralizedURL = mudeCProprietaRepository.getValueByName("GEECO_URL_GET_METADATA_SERVICE", "http://tst-extga.territorio.csi.it/cgsiapi/${env}/${versione}/${geecoSessionID}/data-saved/by-app-id/${UUID}")
					.replace("${env}", URLEncoder.encode(StringUtils.trimToEmpty(env)))
					.replace("${versione}", URLEncoder.encode(StringUtils.trimToEmpty(versione)))
					.replace("${geecoSessionID}", URLEncoder.encode(StringUtils.trimToEmpty(geecoSessioinID)))
					.replace("${UUID}", URLEncoder.encode(StringUtils.trimToEmpty(uuid)));

			//--------------------------------------------
			//ricerca i dati "Selezionati" ..per Editing...
			geecoSavedData= geecoServiceHelper.callGeecoGetMetadataService(centralizedURL)	;
			//--------------------------------------------			
			//Filtra per Sessione utente
			List<GeecoCentralizedDataVO> selected =geecoSavedData.stream().filter(
					s->(s.getFkEditingSession().equals(geecoSessioinID))							
			).collect(Collectors.toList());
			//--------------------------------------------
			if(selected!=null && !selected.isEmpty()) {
				//Filtra per MAssimo id per data sessione utente..
				GeecoCentralizedDataVO maxObjSelcted =selected.stream().max(Comparator.comparing(GeecoCentralizedDataVO::getIdSavedData)).
				orElseThrow(NoSuchElementException::new);;								
				String sSavedData=null;
				if(maxObjSelcted!=null) {
					sSavedData=maxObjSelcted.getSavedData();				
					//Deserializzazione oggetto..
				    ObjectMapper mapper = new ObjectMapper();
				    mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				    result = mapper.writeValueAsString(maxObjSelcted);				    
				}
				if(sSavedData!=null) {
					//sSavedData=sSavedData.replaceAll("\\", " ");
					SavedDataVO bean=GeecoServiceFactory.deserializeSavedData(sSavedData);					
					this.geometryVO=bean.getGeometry();
					logger.info(this.geometryVO);										
					//-----------------------
					//una geometria in sessione viene salvata su database
					//@TODO: mettere nella geecoFacory la seguente parte di codice
					//@TODO: implementare la cancellazione..
					//-----------------------
					MudeTGeecoSession geecoSession=new MudeTGeecoSession();
					geecoSession.setSessioneGeeco(geecoSessioinID);
					geecoSession.setIdIstanza(idIstanza);
					List<MudeTGeecoSession> listaSessioni=mudeTGeecoSessionRepository.findByIdIstanzaAndSessioneGeeco(idIstanza, geecoSessioinID);
					Date date = new Date();
					//Se  la riga nella sessione la legge..
					if(listaSessioni!=null && !listaSessioni.isEmpty()) {
						geecoSession=listaSessioni.get(0);					
						MudeTGeecoSelected geecoSelected =geecoSelectedRepo.findBySessioneGeeco(geecoSession.getSessioneGeeco()); 
								
						if(geecoSelected==null)
							geecoSelected=new MudeTGeecoSelected();
						
						geecoSelected.setIdSession(geecoSession.getSessioneGeeco());
						geecoSelected.setSelectedPosition(sSavedData);
						geecoSelected.setDataInizio(date);
						geecoSelectedRepo.saveDAO(geecoSelected);
					}else {
						//Se non la riga nella sessione la crea..
						/*
						geecoSession.setIdLocalizzazione(new Long(0));
						geecoSession.setDataInizio(date);
						geecoSession=mudeTGeecoSessionRepository.saveDAO(geecoSession);
						MudeTGeecoSelected geecoSelected = new MudeTGeecoSelected();
						geecoSelected.setIdSession(geecoSession.getSessioneGeeco());
						geecoSelected.setSelectedData(sSavedData);						
						geecoSelected.setDataInizio(date);
						geecoSelectedRepo.saveDAO(geecoSelected);
						*/						
					}
						
				}
			}
		    
		} catch (Exception e) {
			logger.error("getSelectedData: exception", e);
		}		
		

		return Response.ok(result).build();	
	}

    private MudeTGeecoSession getLatestGeecoSession(MudeTUser mudeTUser, Long idIstanza) {
		// get the last session used by the user 
    	MudeTGeecoSession mudeTGeecoSession = mudeTGeecoSessionRepository.findTop1ByMudeTUser_IdUserAndIdIstanzaOrderByDataInizioDesc(mudeTUser.getIdUser(), idIstanza);
    	if(mudeTGeecoSession == null)
    		throw new BusinessException("Errore nella sessione geeco: impossibile trovare nessuna sessione attiva.");

    	return mudeTGeecoSession;
    }

    /*
     * FRONT OFFICE SEARCH FUNCTIONS
     */

	@Override
	public Response listaToponomastica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									   String query,
									   String siglaComune, 
									   String nomeComune, 
									   int page, int size) {

		final String loccsiUrl = mudeCProprietaRepository.getValueByName("LOCCSI_URL_TOPONOMASTICA", "http://tst-loccsi.gis.csi.it/oapi/1/catalogs/toponomastica/suggest?q=${ricerca},{comune}") // http://tst-loccsi.gis.csi.it/oapi/1/catalogs/toponomastica/suggest?q=${ricerca}
																.replace("${ricerca}", URLEncoder.encode(StringUtils.trimToEmpty(query)))
																.replace("${sigla_comune}", URLEncoder.encode(StringUtils.trimToEmpty(siglaComune)))
																.replace("${comune}", URLEncoder.encode(StringUtils.trimToEmpty(nomeComune)));
		
        return voStringToResponse(loccsiServiceHelper.getToponomastica(loccsiUrl + "&limit=" + size + "&offset=" + page), 200);
	}
	
	@Override
	public Response recuperaDatiGeeco(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									  Long idIstanza) {
		// old Guido function: TODO: see if required!
		getSelectedData(userCf, XRequestId, XForwardedFor, securityContext, httpHeaders, httpRequest, idIstanza);

    	MudeTUser mudeTUser = userUtil.getUserCF(httpHeaders, false);
    	String geecoSessioinID = getLatestGeecoSession(mudeTUser, idIstanza).getSessioneGeeco();
    	

        return voToResponse(mudeTGeecoSelectedCllbkRepository.recuperaDatiGeeco(geecoSessioinID), 200);
	}
	
	private void setSubQuadroAddress(Long idIstanza, String tablocalJson, int tabIndex, HttpHeaders httpHeaders) {
    	MudeTUser mudeTUser = userUtil.getUserCF(httpHeaders, false);
    	
        IstanzaTemplateQuadroVO vo = new IstanzaTemplateQuadroVO();
        IstanzaVO istanza = new IstanzaVO();
        istanza.setIdIstanza(idIstanza);
		vo.setIstanza(istanza);
        TemplateQuadroVO templateQuadro = new TemplateQuadroVO(); 
        QuadroVO quadro = new QuadroVO(); 
        quadro.setIdQuadro(3l /* QDR_LOCALIZZAZIONE */ ); 
		templateQuadro.setQuadro(quadro);
		vo.setTemplateQuadro(templateQuadro);

        vo.setJsonDataSubquadro(tablocalJson);
        vo.setJsonDataQuadro("{}");
        vo.setCodeSubQuadro("TAB_LOCAL_" + tabIndex);
        vo.setIdSubQuadro(2000l + (10 * tabIndex));
		istanzaTemplateQuadroService.saveIstanzaTemplateQuadro(vo, true, false, mudeTUser, httpHeaders);
	}

	/**
	 *Metodo invocato dalla callback per storicizzare il vaore ritornato da 
	 *mudeopen-api 
	 *
	 * 
	 */
	@Override
	public Response saveSelectedJson(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
			final Long idIstanza,
			final String selectedJson) {
		
		try {
	        JSONObject jsonDataIstanza = new JSONObject(selectedJson);
	        
	        if(!StatoIstanza.BOZZA.equals(mudeRIstanzaStatoRepository.getStatoIstanza(idIstanza))
	        		&& !StatoIstanza.RESTITUITA_PER_VERIFICHE.equals(mudeRIstanzaStatoRepository.getStatoIstanza(idIstanza))) 
	        	return null;

	        String ubicazioni = jsonDataIstanza.getString("datiUbicazione").toString();
	        if("[]".equals(ubicazioni))
	        	setSubQuadroAddress(idIstanza, "{\"dataGrid\": [{\"sedime\": \"\",\"denominazione\": \"\",\"n\": \"\",\"bis\": \"\",\"scala\": \"\",\"piano\": \"\",\"int\": \"\",\"selezionare_se_si_tratta_di_indirizzo_principale\": false,\"bisInterno\": \"\",\"interno2\": \"\",\"secondario\": \"\",\"cap\": \"\",\"geolocalizzazione\": \"\"}],\"_noAddressFound\": true}", 1, httpHeaders);
	        else
	        	setSubQuadroAddress(idIstanza, "{\"dataGrid\": "+jsonDataIstanza.getString("datiUbicazione").toString()+"}", 1, httpHeaders);

	        setSubQuadroAddress(idIstanza, "{\"dataGrid\": "+jsonDataIstanza.getString("datiCatastali").toString()+"}", 2, httpHeaders);
	        
		} catch (Exception e) {
            logger.debug("[GeecoApiImpl::saveSelectedJson] ERROR ", e);
		}

		return Response.ok().build();
	}
	
}
