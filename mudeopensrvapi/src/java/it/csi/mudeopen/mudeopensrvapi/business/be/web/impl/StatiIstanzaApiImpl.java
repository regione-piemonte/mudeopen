/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDStatoIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.StatiIstanzaApi;
import it.csi.mudeopen.mudeopensrvapi.util.Constants;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiModificaStatoIstanza;
import it.csi.mudeopen.mudeopensrvapi.vo.StatiIstanzaAmmessiVO;
import it.csi.mudeopen.mudeopensrvapi.vo.StatoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDWfStatiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRRuoloFruitoreRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaCosmoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;
/**
 * The type Stati istanza api service.
 */
@Component
public class StatiIstanzaApiImpl extends AbstractApiServiceImpl implements StatiIstanzaApi {
	
    private static final String NUMERO_ISTANZA_OBBLIGATORIO = "[CampoObbligatorioException] Numero istanza";

    @Autowired(required = true)
    protected MudeopenDStatoIstanzaService mudeopenDStatoIstanzaService;
    @Autowired
    private MudeopenTIstanzaService mudeopenTIstanzaService;

    @Autowired
    private MudeRRuoloFruitoreRepository mudeRRuoloFruitoreRepository;

	@Autowired
	private MudeDWfStatiRepository mudeDWfStatiRepository;
	
    @Autowired
    MudeTPraticaRepository mudeTPraticaRepository;
    
    @Autowired
    MudeTIstanzaRepository mudeTIstanzaRepository;
    
	@Autowired
	private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;
	
	@Autowired
	private MudeTPraticaCosmoRepository mudeTPraticaCosmoRepository;
	
	@Override
	public Response ricercaStatiIstanza(String XRequestId, String XForwardedFor, String fruitoreID) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);
    		
			List<StatoIstanzaVO> list = mudeopenDStatoIstanzaService.findAllActive().stream().map(item -> { 
					return new StatoIstanzaVO(item.getCodice(), item.getDescrizione()); 
				}).collect(Collectors.toList());
			
			return voToResponse(list, HttpStatus.SC_OK);
			
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}

	@Override
	public Response ricercaStatiIstanzaAmmessi(String XRequestId, String XForwardedFor, String fruitoreID,
												String statoIniziale) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);
    		
	    	if(StringUtils.isBlank(statoIniziale))
	    		throw new Exception("[CampoObbligatorioException] Stato iniziale");
	    	
	    	if(mudeopenDStatoIstanzaService.findStato(statoIniziale) == null)
	    		throw new Exception("[StatoInesistenteException] " + statoIniziale);
    		
			List<StatiIstanzaAmmessiVO> list = mudeopenDStatoIstanzaService.findStatiAmmessi(statoIniziale);
			return voToResponse(list, HttpStatus.SC_OK);
			
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}
	
	protected String modificaStatoIstanza(IstanzaVO inIstanzaVO, String fruitoreID,
			DatiModificaStatoIstanza datiModificaStatoIstanza,
			String mittente) throws Exception {
		return modificaStatoIstanza(inIstanzaVO, fruitoreID,
				datiModificaStatoIstanza,
				mittente,
				null);
	}
	
	protected String modificaStatoIstanza(IstanzaVO inIstanzaVO, String fruitoreID,
			DatiModificaStatoIstanza datiModificaStatoIstanza,
			String mittente,
			String dataCambioStato) throws Exception {
		Client client = ClientBuilder.newClient();
		client.register(new AddDefaultHeadersRequestFilter(fruitoreID));
		WebTarget target = client.target(String.format("%s/istanze/%s/cambia-stato/%s/?scope=ws", getCommonServerURL(), inIstanzaVO.getIdIstanza(), datiModificaStatoIstanza.getNuovoStato()));
		IstanzaVO istanzaVO = new IstanzaVO();
		JSONObject objJson = new JSONObject();				
		// JIRA 851
		String oggettoNotifica = datiModificaStatoIstanza.getOggetto();
		
		String descrNuovoStato = mudeDStatoIstanzaRepository.findMudeDStatoIstanzaByCodice(datiModificaStatoIstanza.getNuovoStato()).getDescrizione();
		
		String numeroPratica = mudeTPraticaRepository.getNumeroPraticaByIdIstanza(inIstanzaVO.getIdIstanza());
		if(StringUtils.isBlank(numeroPratica) && StringUtils.isBlank(numeroPratica = datiModificaStatoIstanza.getNumeroPraticaComunale()))
			numeroPratica = "";
			
		if(oggettoNotifica != null)
			oggettoNotifica = oggettoNotifica.replace("${NUMERO_ISTANZA}", inIstanzaVO.getCodiceIstanza())
											 .replace("${DESCRIZIONE_STATO}", descrNuovoStato != null? descrNuovoStato : inIstanzaVO.getStatoIstanza().getDescrizione())
											 .replace("${NUMERO_PRATICA}", numeroPratica);
		String testoNotifica = datiModificaStatoIstanza.getTesto();
		if(testoNotifica != null)
			testoNotifica = testoNotifica.replace("${NUMERO_ISTANZA}", inIstanzaVO.getCodiceIstanza())
										 .replace("${DESCRIZIONE_STATO}", descrNuovoStato != null? descrNuovoStato : inIstanzaVO.getStatoIstanza().getDescrizione())
										 .replace("${NUMERO_PRATICA}", numeroPratica);
		
		objJson.put("oggetto", oggettoNotifica);
		objJson.put("testo", testoNotifica);
		objJson.put("mittente", mittente);
		istanzaVO.setEnteDiRiferimento(inIstanzaVO.getEnteDiRiferimento());
		istanzaVO.setStatoIstanza(inIstanzaVO.getStatoIstanza());
		istanzaVO.setTipologiaIstanza(inIstanzaVO.getTipologiaIstanza());
		istanzaVO.setJsonData(objJson.toString());
		istanzaVO.setAnno(datiModificaStatoIstanza.getAnnoPratica() == null ? null : Long.valueOf(datiModificaStatoIstanza.getAnnoPratica()));
		istanzaVO.setNumeroPratica(datiModificaStatoIstanza.getNumeroPraticaComunale());
		// FIX JIRA MUDEOPEN-450
		if(datiModificaStatoIstanza.getDataProtocollo() != null)
			istanzaVO.setDataProtocollo(LocalDateTime.ofInstant(getDate(datiModificaStatoIstanza.getDataProtocollo()).toInstant(), ZoneId.systemDefault()));
		if(dataCambioStato != null)
			istanzaVO.setDataCreazione(LocalDateTime.ofInstant(getDate(dataCambioStato).toInstant(), ZoneId.systemDefault()));
		
		istanzaVO.setNumeroProtocollo(datiModificaStatoIstanza.getNumeroProtocollo());
		istanzaVO.setIdIstanza(inIstanzaVO.getIdIstanza());
		istanzaVO.setResponsabile_procedimento(datiModificaStatoIstanza.getResponsabileProcedimento());
		
		// Fix per allineamento al comportamento BO per indicare se la pratica esiste o deve essere creata
		MudeTPratica mudeTPratica = null;
		if (istanzaVO.getEnteDiRiferimento() != null && istanzaVO.getNumeroPratica() != null && istanzaVO.getAnno() != null)
			mudeTPratica = mudeTPraticaRepository.findByNumeroPraticaAnnoPraticaEnte(istanzaVO.getNumeroPratica(),istanzaVO.getAnno(), istanzaVO.getEnteDiRiferimento().getId());
		
		// FIx  MUDEOPEN JIRA 553
		if(mudeTPratica != null) {
			List<MudeTIstanza> istanzePratica = mudeTIstanzaRepository.getInstancesByPratica(mudeTPratica.getId());
			MudeTIstanza mudeTIstanzaByVO = mudeTIstanzaRepository.findByIdIstanza(istanzaVO.getIdIstanza());
			for(MudeTIstanza mudeTIstanza : istanzePratica) {
				if(mudeTIstanza.getMudeTFascicolo() != null &&
						mudeTIstanzaByVO.getMudeTFascicolo() != null &&
					mudeTIstanza.getMudeTFascicolo().getId().longValue() != mudeTIstanzaByVO.getMudeTFascicolo().getId().longValue()) {
					return "[PraticaDiAltroFascicoloException] Pratica " + istanzaVO.getNumeroPratica() + " di altro fascicolo";
				}
			}
		}

		if(mudeTPratica == null)
			istanzaVO.setIdPratica(9999L);
		
		Invocation.Builder invocationBuilder = target.request();

		Response response = invocationBuilder.put(Entity.entity(istanzaVO, MediaType.APPLICATION_JSON));
		if(HttpStatus.SC_OK == response.getStatus())
			return null;
		
		return response.readEntity(String.class);
	}
	
	@Override
	public Response modificaStatoIstanza(String XRequestId, String XForwardedFor, String fruitoreID,
											DatiModificaStatoIstanza datiModificaStatoIstanza) throws Exception {
		try {
			MudeDFruitore fruitore = verifyFruitore(fruitoreID);
			IstanzaVO inIstanzaVO = mudeopenTIstanzaService.ricercaIstanzaDPSOSuccessivo(fruitore, datiModificaStatoIstanza.getNumeroIstanza());

			if(inIstanzaVO != null) {
				if(mudeTPraticaCosmoRepository.findByIdIstanza(inIstanzaVO.getIdIstanza()) != null)
					return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PassaggioStatoImpossibileException] Passaggio di stato non consentito per le DENUNCIE SISMICHE regionali"));
				
				Optional<Error> validaDati = validateDatiModificaStatoIstanza(datiModificaStatoIstanza);
				if(validaDati.isPresent())
					return voToResponse(validaDati.get(), validaDati.get().getHttpStatus().value());
				
				/*
				if("CIL-EL".equals(optional.get().getTipologiaIstanza().getId()) 
						&& !StatoIstanza.REGISTRATA_DA_PA.getValue().equals(datiModificaStatoIstanza.getNuovoStato()))
					return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PassaggioStatoImpossibileException] Cambio Stato non permesso per istanza CIL-EL"));
				*/
				
				if(mudeDWfStatiRepository.findByCodiceStatoStartAndTipoIstanza(inIstanzaVO.getStatoIstanza().getCodice().toString(), inIstanzaVO.getTipologiaIstanza() != null?  inIstanzaVO.getTipologiaIstanza().getId() : "", inIstanzaVO.getSpeciePratica() == null? "" : inIstanzaVO.getSpeciePratica().getCodice()).isEmpty())
					return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PassaggioStatoImpossibileException] Passaggio Stato Impossibile"));
				
				mudeopenDStatoIstanzaService.findStatiAmmessi(inIstanzaVO.getStatoIstanza().getCodice().toString());
				
				MudeRRuoloFruitore mudeRRuoloFruitore = mudeRRuoloFruitoreRepository.findByIdFruitoreWithType(fruitore.getIdFruitore(), inIstanzaVO.getTipologiaIstanza().getId());
				if(mudeRRuoloFruitore == null || mudeRRuoloFruitore.getMudeDRuoloUtente().getCodice().equals(TipoRuoloUtente.UTENTE_LETTORE_OPERAZIONI.getValue()))
					return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[FruitoreNonAbilitatoOperazioneException] OP_modificaStatoIstanza"));	
				
				String stringRes = modificaStatoIstanza(inIstanzaVO, fruitoreID, datiModificaStatoIstanza, mudeRRuoloFruitore.getMudeDRuoloUtente().getDescrizione());
				if(stringRes == null)
					return voToResponse(Boolean.TRUE, HttpStatus.SC_OK);
				
				return errorToResponse(handleUnexpectedMessage(stringRes));
			} else {
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[IstanzaNonTrovataException] Istanza " + datiModificaStatoIstanza.getNumeroIstanza() + " non trovata"));				
			}
			
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}
	
	private LocalDateTime asLocalDateTime(Date date) {

		if (date == null)
			return null;
		return Instant.ofEpochMilli(date.getTime()).atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toLocalDateTime();
	}
	
	private Optional<Error> validateDatiModificaStatoIstanza(DatiModificaStatoIstanza datiModificaStatoIstanza) throws Exception {

			if(StringUtils.isBlank(datiModificaStatoIstanza.getNumeroIstanza())) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, NUMERO_ISTANZA_OBBLIGATORIO));
			}

			if(StringUtils.isBlank(datiModificaStatoIstanza.getNuovoStato())) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Nuovo stato"));
			}
			
			if(datiModificaStatoIstanza.getNuovoStato().length()>3) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Nuovo stato non deve contenere piu di 3 caratteri"));
			}
			
			if(StringUtils.isBlank(datiModificaStatoIstanza.getOggetto())) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Oggetto"));
			}

			if(datiModificaStatoIstanza.getOggetto().length()>120) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Oggetto non deve contenere piu di 120 caratteri"));
			}

			if(StringUtils.isBlank(datiModificaStatoIstanza.getTesto())) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Testo"));
			}

			if(datiModificaStatoIstanza.getTesto().length()>2000) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Testo non deve contenere piu di 2000 caratteri"));
			}

			if(!StatoIstanza.REGISTRATA_DA_PA.getValue().equals(datiModificaStatoIstanza.getNuovoStato()) &&
			   !StatoIstanza.PRESA_IN_CARICO.getValue().equals(datiModificaStatoIstanza.getNuovoStato()) && 
			   !StatoIstanza.RESTITUITA_PER_VERIFICHE.getValue().equals(datiModificaStatoIstanza.getNuovoStato())) {
				return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[PassaggioStatoImpossibileException] "+ datiModificaStatoIstanza.getNuovoStato()));
			}
			
			if(StatoIstanza.REGISTRATA_DA_PA.getValue().equals(datiModificaStatoIstanza.getNuovoStato())) {
				if(StringUtils.isBlank(datiModificaStatoIstanza.getNumeroPraticaComunale()))
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, Constants.NUMERO_PRATICA_OBBLIGATORIO));
				if(datiModificaStatoIstanza.getAnnoPratica() == null)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Anno DettaglioPraticaVO comunale"));
				if(datiModificaStatoIstanza.getAnnoPratica().toString().length() != 4)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Anno deve essere di 4 cifre"));
				if(datiModificaStatoIstanza.getAnnoPratica() < 1900)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[ValoreNonAmmessoException] Anno DettaglioPraticaVO comunale"));
				if(StringUtils.isBlank(datiModificaStatoIstanza.getNumeroPraticaComunale()))
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Numero DettaglioPraticaVO Comunale"));
				if(datiModificaStatoIstanza.getNumeroPraticaComunale().trim().length() > 20)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[ValoreNonAmmessoException] Numero DettaglioPraticaVO Comunale"));
				if(datiModificaStatoIstanza.getNumeroPraticaComunale().length() > 20)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Numero DettaglioPraticaVO Comunale non deve contenere piu di 20 caratteri"));
				if(StringUtils.isBlank(datiModificaStatoIstanza.getNumeroProtocollo()))
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Numero Protocollo"));
				if(datiModificaStatoIstanza.getNumeroProtocollo().length() != 7)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Numero Protocollo deve essere numerico di 7 cifre"));
				if(!datiModificaStatoIstanza.getNumeroProtocollo().matches("-?\\d+"))
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[ValoreNonAmmessoException] Numero Protocollo"));
				if(datiModificaStatoIstanza.getDataProtocollo() == null)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Data Protocollo"));
				if(!isValidDate(datiModificaStatoIstanza.getDataProtocollo()))
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[ValoreNonAmmessoException] Data Protocollo"));
				if(StringUtils.isBlank(datiModificaStatoIstanza.getResponsabileProcedimento()))
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Responsabile del procedimento"));
				
				if(datiModificaStatoIstanza.getResponsabileProcedimento().length() > 150)
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[SystemException] Il campo Responsabile del procedimento non deve contenere piu di 150 caratteri"));
			} else {
				if(StringUtils.isNotBlank(datiModificaStatoIstanza.getNumeroPraticaComunale())
					|| StringUtils.isNotBlank(datiModificaStatoIstanza.getNumeroProtocollo())
					|| StringUtils.isNotBlank(datiModificaStatoIstanza.getResponsabileProcedimento())
					|| !Objects.isNull(datiModificaStatoIstanza.getAnnoPratica())
					|| !Objects.isNull(datiModificaStatoIstanza.getDataProtocollo())) {
					return Optional.of(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[PassaggioStatoImpossibileException] "+ datiModificaStatoIstanza.getNuovoStato()));
				}
			}
			
		return  Optional.empty();
		
	}
	
}