/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.*;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.*;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenDComuneRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRComuneFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvapi.vo.Allegato;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiSintesiIstanzaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.IstanzaMude;
import it.csi.mudeopen.mudeopensrvapi.vo.MudeRVersioneModello;
import it.csi.mudeopen.mudeopensrvapi.vo.VisualizzaDatiProtocollazioneIstanzaVo;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoUbicazione;
import it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza.IstanzaExtVO;
import it.csi.mudeopen.mudeopensrvapi.vo.mapper.AllegatiMapper;
import it.csi.mudeopen.mudeopensrvapi.vo.mapper.IstanzaMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl.JsonDataHelper;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.IstanzaTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
/**
 * The type Istanze api service.
 */
@Component
public class IstanzeApiServiceImpl extends AbstractApiServiceImpl implements IstanzeApi {
	
    private static final String NUMERO_ISTANZA_OBBLIGATORIO = "[CampoObbligatorioException] Numero istanza obbligatorio";

	private static Logger logger = Logger.getLogger(IstanzeApiServiceImpl.class.getCanonicalName());
	@Autowired
	public MudeopenTIstanzaService mudeopenTIstanzaService; 
    @Autowired
    private MudeopenDComuneRepository mudeopenDComuneRepository;
	
    @Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;

    @Autowired
    private MudeopenRComuneFruitoreRepository comuneFruitoreRepository;

    @Autowired
    private IstanzaMapper istanzaMapper;

    @Autowired
    private AllegatiMapper allegatiMapper;

    @Autowired
    private MudeDTemplateRepository mudeopenDTemplateRepository;

    @Autowired
    private MudeopenRLocUbicazioneRepository mudeopenRLocUbicazioneRepository;

    @Autowired
    private JsonDataHelper jsonDataHelper;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeopenTIstanzaRepository mudeopenTIstanzaRepository;

    @Autowired
	private IstanzaStatoService istanzaStatoService;

    @Autowired
	private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;

	@Override
	public Response ricercaPaginataIstanze03(String XRequestId, String XForwardedFor, String fruitoreID,
											String filter, Integer page, Integer size) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);
    		
			if(page == null) page = 1;
			if(size == null) size = 150;
			page--;
			
            JSONObject js = new JSONObject(filter);
            if(!hasJsonValue(js, "codIstatComune"))
            	throw new Exception("[CampoObbligatorioException] Codice ISTAT Comune");
            if(hasJsonValue(js, "tipoIntestatarioIstanza") && 
            			!(hasJsonValue(js, "nomeIntestatarioIstanza") || hasJsonValue(js, "intestatarioIstanza")))
            	throw new Exception("[CampoObbligatorioException] Indicare almeno un valore tra IntestatarioIstanza e nomeIntestatarioIstanza");
            if(hasJsonValue(js, "intestatarioIstanza") && !hasJsonValue(js, "tipoIntestatarioIstanza"))
            	throw new Exception("[CampoObbligatorioException] Tipo intestatario obbligatorio");
            if(hasJsonValue(js, "nomeIntestatarioIstanza") && !hasJsonValue(js, "tipoIntestatarioIstanza"))
            	throw new Exception("[CampoObbligatorioException] Tipo intestatario obbligatorio");
            if(hasJsonValue(js, "tipoIntestatarioIstanza")) {
				if(!("F".equals(getJsonValue(js, "tipoIntestatarioIstanza")) || "G".equals(getJsonValue(js, "tipoIntestatarioIstanza"))))
					throw new Exception("[ValoreNonAmmessoException] Tipo Intestatario Istanza = " + js.get("tipoIntestatarioIstanza"));
	            if("G".equals(getJsonValue(js, "tipoIntestatarioIstanza")) && !hasJsonValue(js, "intestatarioIstanza"))
	            	throw new Exception("[CampoObbligatorioException] Per una persona giuridica occorre indicare l'intestatarioIstanza");
	            if("G".equals(getJsonValue(js, "tipoIntestatarioIstanza")) && !(hasJsonValue(js, "intestatarioIstanza") && !hasJsonValue(js, "nomeIntestatarioIstanza")))
	            	throw new Exception("[CampoObbligatorioException] Per una persona giuridica occorre indicare l'intestatarioIstanza e non il nomeIntestatarioIstanza");
            }

			return mudeopenTIstanzaService.ricercaPaginataIstanze03Slim(filter, fruitoreID, null, page, size);
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}

	@Override
	public Response visualizzaDatiProtocollazioneIstanza(String XRequestId, String XForwardedFor, String fruitoreID,
															String numeroIstanza) throws Exception {
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			
			if(numeroIstanza != null && numeroIstanza.length()>22)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND,"[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 22 caratt"));
			
			if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, NUMERO_ISTANZA_OBBLIGATORIO));
			Optional<MudeTIstanzaSLIM> istanzaOpt = mudeopenTIstanzaService.cercaIstanzaAPAWSSlim(fruitore, numeroIstanza);
			if(istanzaOpt.isPresent()) {
				try {
					Optional<VisualizzaDatiProtocollazioneIstanzaVo> visualizzaDatiProtocollazione = mudeopenTIstanzaService.visualizzaDatiProtocollazione(istanzaOpt.get());
					if(visualizzaDatiProtocollazione.isPresent())
						return voToResponse(visualizzaDatiProtocollazione.get(), HttpStatus.SC_OK);
					else
						throw new IllegalArgumentException("[IstanzaNonTrovataException] Istanza non trovata");
					
				} catch(IllegalArgumentException e) {
					return errorToResponse(handleUnexpectedError(e));
				}
			}
			
			return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[NumeroIstanzaRiferimentoInesistenteException] Errore 04: Numero istanza di riferimento " + numeroIstanza + " inesistente"));
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}

	@Override
	public Response estraiFileIstanza(String XRequestId, String XForwardedFor, String fruitoreID, String numeroIstanza) throws Exception {
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Numero istanza obbligatorio"));
			Optional<MudeTIstanzaSLIM> opt = mudeopenTIstanzaService.cercaIstanzaWSSlim(fruitore, numeroIstanza);
			if(opt.isPresent()) {
				MudeTIstanzaSLIM mudeTIstanza = opt.get();
				String statoIstanza = istanzaStatoService.getStatoIstanza(mudeTIstanza.getId());
				if("DPS,APA,PRC".indexOf(statoIstanza) == -1)
					return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[StatoIstanzaNonValidoException] " + numeroIstanza));
				Client client = ClientBuilder.newClient();
				client.register(new AddDefaultHeadersRequestFilter(fruitoreID));
				WebTarget target = client.target(String.format("%s/istanze/download/%s/?scope=ws&con_firma=true", configurationHelper.getEndpointBase(), mudeTIstanza.getId()));
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_OCTET_STREAM);
				Response response =  invocationBuilder.header(Constants.HEADER_USER_CF, fruitoreID).get();
				if(response.getStatus() <= 201) // ok
					return response;
				if(response.getStatus() == 404) // not found
					return handleFileNotFound(response, numeroIstanza);
			} else
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[IstanzaNonTrovataException] " + numeroIstanza));
			throw new Exception("[SystemException] Errore generico");
		}catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}

	@Override
	public Response estraiAllegatoIstanza(String XRequestId, String XForwardedFor, String fruitoreID, String numeroIstanza, String nomeFileAllegato, String sbustato) throws Exception {
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Numero istanza obbligatorio"));
			if(StringUtils.isBlank(nomeFileAllegato))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Nome file allegato"));
			if(StringUtils.isBlank(sbustato))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Sbustato"));
			if(!StringUtils.equalsIgnoreCase(sbustato, "SI") && !StringUtils.equalsIgnoreCase(sbustato, "NO"))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "Parametro 'sbustato' puo' essere uguale solo a 'SI' oppure 'NO'"));
			Optional<MudeTIstanzaSLIM> opt = mudeopenTIstanzaService.cercaIstanzaWSSlim(fruitore, numeroIstanza);
			if(opt.isPresent()) {
				MudeTIstanzaSLIM mudeTIstanza = opt.get();
				String statoIstanza = istanzaStatoService.getStatoIstanza(mudeTIstanza.getId());
				if("DPS,APA,PRC".indexOf(statoIstanza) == -1)
					return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[StatoIstanzaNonValidoException] " + numeroIstanza));
				Boolean conFirma = StringUtils.equalsIgnoreCase(sbustato, "NO"); //se conFirma è true, viene restituito il file non estratto.
				String allegatoFileUid = mudeTAllegatoRepository.findFileUidByIdIstanzaAndFilename(mudeTIstanza.getId(), nomeFileAllegato);
				if(allegatoFileUid != null) {
					Response response =  getResponse(String.format("/allegati/download/%s/?scope=ws&con_firma=%s", allegatoFileUid, conFirma), fruitoreID);
					if(response.getStatus() <= 201) // ok
						return response;
					if(response.getStatus() == 404) // not found
						return handleFileNotFound(response, numeroIstanza);
				}

				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[AllegatoNonTrovatoException] informzioni sull'allegato non trovate"));
			} else
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[IstanzaNonTrovataException] " + numeroIstanza));
		}

		catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}

	private Response handleFileNotFound(Response response, String numeroIstanza) throws Exception {

		String jsonResp = response.readEntity(String.class);
		
		String filename = null;
		if(jsonResp != null)
			filename = jsonResp.replaceAll(".*\\[(.*)\\]", "$1");
		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[AllegatoNonTrovatoException] ISTANZA: " + numeroIstanza + " NOME FILE ALLEGATO:" + filename));
	}
	
	@Override
	public Response generaNumeroMUDE(String XRequestId, String XForwardedFor, String fruitoreID,
			String istatComune, String nuovoIntervento ) throws Exception {
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			
			if(istatComune != null && istatComune.length()>6)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND,"[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 6 caratt"));
			MudeDComune comune = mudeopenDComuneRepository.findByIstatComuneAndFineValidita(istatComune);
			if(comune == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND,"[ComuneInsesistenteException] Errore  01: Codice ISTAT "+ istatComune + " inesistente"));
			
			List<MudeRComuneFruitore> comuniFruitore = comuneFruitoreRepository.findByComuneAndFruitore(comune, fruitore);
			if(comuniFruitore.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND,"[FruitoreNonAbilitatoComuneException] Errore 01: Fruitore non abilitato al Comune " + istatComune));
			if(nuovoIntervento != null && nuovoIntervento.length()>2)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND,"[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 2 caratt"));
			if(StringUtils.isBlank(nuovoIntervento) ||
					(!StringUtils.equalsIgnoreCase(nuovoIntervento, "SI") && !StringUtils.equalsIgnoreCase(nuovoIntervento, "NO")))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[ValoreNonAmmessoException] Errore 04: Valore non ammesso per il parametro Nuovo Intervento"));
			String numeroMude = mudeopenTIstanzaService.getNewNumeroMude(comune, fruitore, "SI".equalsIgnoreCase(nuovoIntervento));
			return voToResponse(numeroMude, HttpStatus.SC_OK);
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
        }
	}

	@Override
	public Response ricercaDatiSintesiIstanza(String XRequestId, String XForwardedFor, String fruitoreID,
													String numeroIstanza) throws Exception {
		
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			
			if(StringUtils.isBlank(numeroIstanza))
            	return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));
			Optional<MudeTIstanzaSLIM> opt = mudeopenTIstanzaService.cercaIstanzaDPSOSuccessivoWSSlim(fruitore, numeroIstanza);
            if(opt.isEmpty())
            	return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[IstanzaNonTrovataException] Istanza non trovata"));
        	DatiSintesiIstanzaVO sintesiIstanza = istanzaMapper.mapIstanzaSlimtoSintesiIstanza(Arrays.asList(opt.get())).get(0);
        	
        	if(StringUtils.isNotBlank(sintesiIstanza.getIstanzaDiRiferimento()))
        		sintesiIstanza.setIstanzaDiRiferimento(sintesiIstanza.getIstanzaDiRiferimento());
        	if(StringUtils.isNotBlank(sintesiIstanza.getNumeroIntervento()))
        		sintesiIstanza.setNumeroIntervento(sintesiIstanza.getNumeroIntervento());
        	if(StringUtils.isNotBlank(sintesiIstanza.getNumeroIstanza()))
        		sintesiIstanza.setNumeroIstanza(sintesiIstanza.getNumeroIstanza());
        	
        	return voToResponse(sintesiIstanza, HttpStatus.SC_OK);
		}

		catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
        }
	}
	
	/*
	protected Response postSalvaConNumeroMude(String url,MultipartFormDataOutput output, String numeroMude, MudeTUser mudeTUser) {
		CSILOGGER.info("chiamata endpoint POST"+url);
		Client client = ClientBuilder.newClient();
		GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(output) {};
		client.register(new AddDefaultHeadersRequestFilter(mudeTUser.getCf(), null, null));
		WebTarget target = client.target(configurationHelper.getEndpointBase()+url);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		return rtarget.request().header(Constants.HEADER_USER_CF, mudeTUser.getCf()).header(it.csi.mudeopen.mudeopensrvcommon.util.Constants.NUMERO_MUDE, numeroMude).post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
	}
	 */
	
	@Override
	public Response invioIstanza(String XRequestId, String XForwardedFor, String fruitoreID,
										IstanzaExtVO salvaIstanzaRequest) throws Exception {
		MudeDFruitore fruitore = verifyFruitore(fruitoreID);
		
		try {
			mudeopenTIstanzaService.salvaIstanzaRequest(fruitore, salvaIstanzaRequest);
			return voToResponse(Boolean.TRUE, HttpStatus.SC_OK);
		} catch (Exception e) {
			return errorToResponse(handleUnexpectedError(e));
		}
	}
	
	@Override
	public Response estraiIstanzaMude(String XRequestId, String XForwardedFor, String fruitoreID, String numeroIstanza) throws Exception {
		long beginTime = 0, deltaTime = 0;
		if(!Constants._environment.equals("prod")) beginTime = deltaTime = (deltaTime = System.currentTimeMillis());
		
		try {
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_START-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
			
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_verifyFruitore-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
			
			if(StringUtils.isBlank(numeroIstanza))
            	return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));
			Optional<MudeTIstanzaSLIM> opt = mudeopenTIstanzaService.cercaIstanzaDPSOSuccessivoWSSlim(fruitore, numeroIstanza);
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_cercaIstanzaDPSOSuccessivoWSSlim-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
            if(opt.isEmpty())
            	return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[IstanzaNonTrovataException] Istanza non trovata"));
            IstanzaMude istanzaMude = new IstanzaMude();
            String datiXml = mudeRIstanzaTracciatoRepository.findTracciatoXMLByIdIstanza(opt.get().getId(), "XMLUNICO");
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_findTracciatoXMLByIdIstanza-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
            if(datiXml==null){
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] XML inesistente per istanza"));
			}

        	istanzaMude.setXmlStream(datiXml);
        	List<GeoRiferimento> geoRiferimentoVOs = mudeopenTIstanzaService.estraiElencoGeoRiferimento (opt.get(), fruitoreID);
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_estraiElencoGeoRiferimento-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
        	istanzaMude.setElencoGeoRiferimento(geoRiferimentoVOs);
        	
        	List<MudeopenRLocUbicazione> geoUbicaziones = mudeopenRLocUbicazioneRepository.findByIdIstanza(opt.get().getId());
        	List<GeoUbicazione> geoUbicazioniVOs = istanzaMapper.mapListEntityGeoUbicazioniToListVO(geoUbicaziones);
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_mapListEntityGeoUbicazioniToListVO-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
        	istanzaMude.setTopeUbicaziones(geoUbicazioniVOs);
        	
        	Response response = getResponse("/allegati/id-istanza/"+opt.get().getId()+"?scope=ws-ricerca-allegati", fruitoreID);
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_getResponse-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
			response.bufferEntity();
			String json = response.readEntity(String.class);
			
			if(HttpStatus.SC_OK == response.getStatus()) {
				ObjectMapper objectMapper = new ObjectMapper();
				List<MudeTAllegatoSlim> list = objectMapper.readValue(json, new TypeReference<List<MudeTAllegatoSlim>>() {});
				List<Allegato> allegatoVOs = new ArrayList<>();
				if (list != null) {
					allegatoVOs = allegatiMapper.mapAllegatiSlimToAllegato(list);
				}

				istanzaMude.setAllegatos(allegatoVOs);
			}

			MudeRVersioneModello mudeRVersioneModello = new MudeRVersioneModello();
			Integer numVersion = mudeopenDTemplateRepository.findMaxVersionByTipoIstanza(opt.get().getIdTipoIstanza());
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_findMaxVersionByTipoIstanza-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
			/*if(mudeDTemplate == null) 
				mudeDTemplate = mudeopenDTemplateRepository.findByTipoIstanza_idAndMaxVersion(opt.get().getTipoIstanza().getId());*/
			if(numVersion != null) {
				mudeRVersioneModello.setCodModello(opt.get().getIdTipoIstanza());
				mudeRVersioneModello.setCodVersioneModello(numVersion.toString());
			}
				
			istanzaMude.setMudeRVersioneModello(mudeRVersioneModello);
        	
			if(!Constants._environment.equals("prod")) logger.info("[IstanzeApiServiceImpl::estraiIstanzaMude] __ptime_END-- " + (new SimpleDateFormat("mm:ss,S").format(new Date(System.currentTimeMillis() - deltaTime))) + " - " + (new SimpleDateFormat("mm:ss,S").format(new Date((deltaTime = System.currentTimeMillis()) - beginTime))));
        	return voToResponse(istanzaMude, HttpStatus.SC_OK);
		}

		catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
        }
	}
	
	
	@Override
	public Response getElencoGeoRiferimento(String XRequestId, String XForwardedFor, String fruitoreID, String numeroIstanza) throws Exception {
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			
			if(StringUtils.isBlank(numeroIstanza))
            	return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));
			Optional<MudeTIstanzaSLIM> opt = mudeopenTIstanzaService.cercaIstanzaDPSOSuccessivoWSSlim(fruitore, numeroIstanza);
            if(opt.isEmpty())
            	return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[IstanzaNonTrovataException] Istanza non trovata"));
            List<GeoRiferimento> geoRiferimentoVOs = mudeopenTIstanzaService.estraiElencoGeoRiferimento (opt.get(), fruitoreID);
        	return voToResponse(geoRiferimentoVOs, HttpStatus.SC_OK);
		}

		catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
        }
	}
	
	@Override
	public Response fillInIndirizzi(String XRequestId, String XForwardedFor, String fruitoreID) throws Exception {
		List<BigInteger> istanzaIDs = mudeopenTIstanzaRepository.findMissingAddressedInstances();
		for(BigInteger idIstanza : istanzaIDs) {
			MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza.longValue());
        	try {
        		MudeTIndirizzo indirizzo = jsonDataHelper.getIndirizzoFromJson(istanza, new JSONObject(istanza.getJsonData()));
                if(null != indirizzo) {
                    istanza.setIndirizzo(indirizzo);
                    mudeTIstanzaRepository.saveDAO(istanza);
                }
			} catch (Exception skip) {
                logger.error("[IstanzeApiServiceImpl::fillInIndirizzi] ERROR : " + skip, skip);
			}
 		}
		
    	return voToResponse(istanzaIDs, HttpStatus.SC_OK);
	}	
	
}
