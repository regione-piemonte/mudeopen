/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRComuneFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRTipoIstanzaFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTSessioneRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeTLogNumeriMudeService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTNotificaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.specification.MudeTIstanzaSlimSpecificationWS;
import it.csi.mudeopen.mudeopensrvapi.business.be.specification.MudeTIstanzaSpecificationWS;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.impl.AbstractApiServiceImpl;
import it.csi.mudeopen.mudeopensrvapi.vo.FiltroRicercaPaginataVO;
import it.csi.mudeopen.mudeopensrvapi.vo.IstanzaEstesaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.VisualizzaDatiProtocollazioneIstanzaVo;
import it.csi.mudeopen.mudeopensrvapi.vo.enumeration.TipoLogNumeriMude;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.CSIGeometry;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoCatasto;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoCellula;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoDatocarota;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoUbicazione;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon.CSICoordinate;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon.CSILinearRing;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon.CSIPolygon;
import it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza.IstanzaExtVO;
import it.csi.mudeopen.mudeopensrvapi.vo.mapper.IstanzaMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSelectedCllbk;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCellula;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocDatocarota;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocGeoriferim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSelectedCllbkRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenDFruitoreRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocCatastoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocCellulaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocDatocarotaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocGeoriferimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocUbicazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.XmlValidator;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
@Service
public class MudeopenTIstanzaServiceImpl extends AbstractApiServiceImpl implements MudeopenTIstanzaService{
	
	private static Logger logger = Logger.getLogger(MudeopenTIstanzaServiceImpl.class.getCanonicalName());
	
	@Autowired
    public MudeopenTSessioneRepository mudeopenTSessioneRepository;
    @Autowired
    public MudeopenRComuneFruitoreRepository mudeopenRComuneFruitoreRepository;
	@Autowired
	public MudeopenTNotificaService mudeopenTNotificaService;
	
	@Autowired
	private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;
	
	@Autowired
	private MudeopenRTipoIstanzaFruitoreRepository mudeopenRTipoIstanzaFruitoreRepository;
	
	@Autowired
	private MudeDDugRepository mudeDDugRepository;
	
    @Autowired
    private MudeTLogNumeriMudeService mudeTLogNumeriMudeService;

	@Autowired
	private MudeTIstanzaRepository mudeTIstanzaRepository;

	@Autowired
	private MudeopenTIstanzaRepository mudeopenTIstanzaRepository;

	@Autowired
	private MudeTFascicoloRepository mudeTFascicoloRepository;
	
	@Autowired
	private MudeDSpeciePraticaRepository mudeDSpeciePraticaRepository;
	
	@Autowired
	private MudeTIndirizzoRepository mudeTIndirizzoRepository;
	
	@Autowired
	private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;
	
	@Autowired
	private MudeTContattoRepository mudeTContattoRepository;
	
	@Autowired
	private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;
	
	@Autowired
	private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;
	
	@Autowired
	private MudeDTipoTracciatoRepository mudeDTipoTracciatoRepository;
	
    @Autowired
    private MudeopenRLocGeoriferimRepository mudeopenRLocGeoriferimRepository;

    @Autowired
    private MudeopenRLocCellulaRepository mudeopenRLocCellulaRepository;

    @Autowired
    private MudeopenRLocCatastoRepository mudeopenRLocCatastoRepository;

    @Autowired
    private MudeopenRLocDatocarotaRepository mudeopenRLocDatoCarotaRepository;

    @Autowired
    private MudeopenRLocUbicazioneRepository mudeopenRLocUbicazioneRepository;

    @Autowired
    private IstanzaMapper istanzaMapper;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private IstanzaTracciatoEntityMapper istanzaTracciatoEntityMapper;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private TipoTracciatoEntityMapper tipoTracciatoEntityMapper;

    @Autowired
    private MudeopenDFruitoreRepository mudeopenDFruitoreRepository;

    @Autowired
	private MudeTPraticaRepository mudeTPraticaRepository;

    @Autowired
	private MudeTGeecoSelectedCllbkRepository mudeTGeecoSelectedCllbkRepository;

	public static final List<StatoIstanza> statiPrecedentiADPS = Arrays.asList(StatoIstanza.DA_FIRMARE, StatoIstanza.FIRMATA, StatoIstanza.BOZZA, StatoIstanza.RESTITUITA_PER_VERIFICHE);
	
	@Override
	public Optional<VisualizzaDatiProtocollazioneIstanzaVo> visualizzaDatiProtocollazione(MudeTIstanzaSLIM istanzaSLIM) {
		if(istanzaSLIM != null) {
			MudeRIstanzaStato mudeRIstanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(istanzaSLIM.getId());
			if(mudeRIstanzaStato != null) {
				VisualizzaDatiProtocollazioneIstanzaVo vo = new VisualizzaDatiProtocollazioneIstanzaVo();
				List<MudeTNotifica> mudeTNotifiche = mudeopenTNotificaService.cercaPerNumeroIstanza(istanzaSLIM.getCodiceIstanza());
				MudeTNotifica notificaCambioStato = mudeTNotifiche.stream().filter(e-> StringUtils.equals("CAMBIO_STATO", e.getTipoNotifica().getCodTipoNotifica()))
						.findFirst()
						.orElseThrow(()-> new IllegalArgumentException("Notifica di cambio stato non trovata"));
				String praticaString = mudeTPraticaRepository.findPraticaStringByIdIstanza(istanzaSLIM.getId());
				if(praticaString==null) {
					throw new IllegalArgumentException("Nessuna pratica trovata per l'istanza inserita");
				}

				PraticaSlim praticaSlim = fromString(praticaString);
				
				vo.setOggettoNotifica(notificaCambioStato.getOggettoNotifica());
				vo.setMessaggioNotifica(notificaCambioStato.getTestoNotifica());
				vo.setDataVariazioneStato(mudeRIstanzaStato.getDataInizio());
				vo.setNumeroProtocollo(istanzaSLIM.getNumeroProtocollo());
				vo.setDataProtocollo(istanzaSLIM.getDataProtocollo());
				
				return Optional.of(vo);
			} else {
				throw new IllegalArgumentException("Istanza non ancora registrata da PA");
			}
			
		}
		
		return Optional.empty();
	}

	public PraticaSlim fromString(String praticaString){
		PraticaSlim c = new PraticaSlim();
		String[] tokens = praticaString.split("\\$\\$\\$");
		c.numeroPratica = "null_string".equalsIgnoreCase(tokens[0]) ? null : tokens[0];
		try {
			c.dataCreazione = "null_string".equalsIgnoreCase(tokens[1]) ? null : new Date(Long.parseLong(tokens[1]));
		} catch(Exception e){
		}

		return c;
	}

	public class PraticaSlim {
		public String numeroPratica;
		public Date dataCreazione;
		public String getNumeroPratica() {
			return numeroPratica;
		}

		public void setNumeroPratica(String numeroPratica) {
			this.numeroPratica = numeroPratica;
		}

		public Date getDataCreazione() {
			return dataCreazione;
		}

		public void setDataCreazione(Date dataCreazione) {
			this.dataCreazione = dataCreazione;
		}
	}

	@Override
	public MudeTIstanza findByNumeroIstanza(String numeroIstanza) {
		return mudeTIstanzaRepository.findByCodIstanza(numeroIstanza);
	}

	// USED JUST FOR: modificaStatoIstanza, inserisciNotifica
	//@Override
	private List<IstanzaVO> ricercaPaginataIstanze(MudeDFruitore fruitore, FiltroRicercaPaginataVO filtroRicercaPaginata) throws JsonProcessingException, UnsupportedEncodingException {
		
		ObjectMapper mapper = new ObjectMapper();
		//mapper.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
		
		filtroRicercaPaginata.setIdFruitore(fruitore.getIdFruitore());
		return cercaIstanzeWS(mapper.writeValueAsString(filtroRicercaPaginata), fruitore.getCodiceFruitore(), null);
	}

	/*
	 * USER BY modificaStatoIstanza
	 */
	@Override
	public IstanzaVO ricercaIstanzaDPSOSuccessivo(MudeDFruitore fruitore, String numeroIstanza) throws Exception {
		
		FiltroRicercaPaginataVO filtroRicercaPaginata = new FiltroRicercaPaginataVO();
		filtroRicercaPaginata.setStatiIstanza(new ArrayList<>());
		filtroRicercaPaginata.getStatiIstanza().addAll(Arrays.asList(StatoIstanza.DEPOSITATA, StatoIstanza.PRESA_IN_CARICO,StatoIstanza.REGISTRATA_DA_PA)
																.stream().map(StatoIstanza::getValue).collect(Collectors.toList()));
		filtroRicercaPaginata.setNumeroIstanza(numeroIstanza);
		
		List<IstanzaVO> ricercaPaginataIstanze = ricercaPaginataIstanze(fruitore, filtroRicercaPaginata);
		if(!ricercaPaginataIstanze.isEmpty()) {
			if(ricercaPaginataIstanze.size() > 1)
				throw new Exception("[SystemException] Errore: RISULTATI MULTIPLI");
			return ricercaPaginataIstanze.get(0);
		}
		
		return null;
	}

	/*
	 * USED BY inserisciNotifica
	 */
	@Override
	public Optional<IstanzaVO> ricercaIstanza(MudeDFruitore fruitore, String numeroIstanza) throws JsonProcessingException, UnsupportedEncodingException {
		FiltroRicercaPaginataVO filtroRicercaPaginata = new FiltroRicercaPaginataVO();
		filtroRicercaPaginata.setStatiIstanza(new ArrayList<>());
		filtroRicercaPaginata.setNumeroIstanza(numeroIstanza);
		
		List<IstanzaVO> ricercaPaginataIstanze = ricercaPaginataIstanze(fruitore, filtroRicercaPaginata);
		if(!ricercaPaginataIstanze.isEmpty()) {
			return Optional.of(ricercaPaginataIstanze.get(0));
		}
		
		return Optional.empty();
	}

	/*
	 * NOT USED
	@Override
	public Optional<IstanzaVO> ricercaIstanzaAPA(MudeDFruitore fruitore, String numeroIstanza)
			throws JsonProcessingException, UnsupportedEncodingException {
		FiltroRicercaPaginataVO filtroRicercaPaginata = new FiltroRicercaPaginataVO();
		filtroRicercaPaginata.setStatiIstanza(new ArrayList<>());
		filtroRicercaPaginata.getStatiIstanza().add(StatoIstanza.REGISTRATA_DA_PA.getValue());
		filtroRicercaPaginata.setNumeroIstanza(numeroIstanza);
		List<IstanzaVO> ricercaPaginataIstanze = ricercaPaginataIstanze(fruitore, filtroRicercaPaginata);
		if(!ricercaPaginataIstanze.isEmpty()) {
			return Optional.of(ricercaPaginataIstanze.get(0));
		}
		
		return Optional.empty();
	}
	*/
	// USED JUST FOR: modificaStatoIstanza, inserisciNotifica
    private List<IstanzaVO> cercaIstanzeWS(String filter, String fruitore, String responseFilters) {

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        /*
			String codIstatComune;
			List<String> statiIstanza;
			String numeroFascicoloIntervento;
			Integer annoIntervento;
			Integer annoIstanza;
			String numeroPraticaComunale;
			Integer annoPraticaComunale;
			List<String> tipologieIstanza;
			String tipoIntestatarioIstanza;
			String intestatarioIstanza;
			String nomeIntestatarioIstanza;
			String indirizzo;
			String numeroIstanza; 
         */
        List<Long> comuni = null;
        MudeTPratica mudeTPratica = null;
        //
        String codiceIstanza = FilterUtil.getValueWS(filter, "numeroIstanza");
        String codiceFascicolo = FilterUtil.getValueWS(filter, "numeroFascicoloIntervento");
        String tipoIntestatario = FilterUtil.getValueWS(filter, "tipoIntestatarioIstanza");
        String intestatario = FilterUtil.getValueWS(filter, "intestatarioIstanza");
        //TOCHECK  String intestatarioNome = FilterUtil.getValueWS(filter, "tipologieIstanza");
        String intestatarioNome = FilterUtil.getValueWS(filter, "nomeIntestatarioIstanza");
        String indirizzo = FilterUtil.getValueWS(filter, "indirizzo");
        String numeroPratica = FilterUtil.getValueWS(filter, "numeroPraticaComunale");
        String istatComune = FilterUtil.getValueWS(filter, "codIstatComune");
        Integer annoIntervento = FilterUtil.getValueNumWS(filter, "annoIntervento") != null ? FilterUtil.getValueNumWS(filter, "annoIntervento").intValue() : null;
        Long annoIstanza = FilterUtil.getValueNumWS(filter, "annoIstanza") != null ? FilterUtil.getValueNumWS(filter, "annoIstanza").longValue() : null;
        Long annoPratica = FilterUtil.getValueNumWS(filter, "annoPraticaComunale") != null ? FilterUtil.getValueNumWS(filter, "annoPraticaComunale").longValue() : null;
        Long idFruitore = FilterUtil.getValueNumWS(filter, "idFruitore") != null ? FilterUtil.getValueNumWS(filter, "idFruitore").longValue() : null;
		if(!StringUtils.isBlank(istatComune)) {
	    	MudeDComune comune = mudeDComuneRepository.findByIstatComuneAndFineValidita(istatComune);
	    	if(comune == null)
	    		throw new BusinessException("CampoObbligatorioException: codice istat (" + istatComune + ") inesistente");
	    	
        	comuni = new ArrayList<>();
        	comuni.add(comune.getIdComune());            	
        }

        Specification<MudeTIstanza> filterFruitore = MudeTIstanzaSpecificationWS.filterByFruitore(fruitore, idFruitore);
        Specification<MudeTIstanza> filterStato = null;
        List<String> statiIstanza = FilterUtil.getValues(filter, "statiIstanza"); 
        if(statiIstanza != null && statiIstanza.size() >0)
        	filterStato = MudeTIstanzaSpecificationWS.hasStatoIn(FilterUtil.getValues(filter, "statiIstanza"));
        List<String> tipologieIstanza = FilterUtil.getValues(filter, "tipologieIstanza");
        Specification<MudeTIstanza> filterTipoIstanza = null;
        if(tipologieIstanza != null && tipologieIstanza.size()>0)
        	filterTipoIstanza = MudeTIstanzaSpecificationWS.hasTipoIstanzaIn(FilterUtil.getValues(filter, "tipologieIstanza"));
        // this should be split for each specification
		Specification<MudeTIstanza> filters = MudeTIstanzaSpecificationWS.findByWS(comuni, numeroPratica, annoPratica,
				annoIstanza, tipoIntestatario, intestatario, intestatarioNome, indirizzo, annoIntervento);
		
		List<MudeTIstanza> mudeTIstanzaList = mudeTIstanzaRepository.findAll(Specifications.where(filters)
																				.and(filterStato)
																				.and(filterTipoIstanza)
																				.and(filterFruitore)
																				.and(MudeTIstanzaSpecificationWS.hasCodiceIstanza(codiceIstanza))
																				.and(MudeTIstanzaSpecificationWS.hasCodiceFascicolo(codiceFascicolo)));
        List<IstanzaVO> istanzeVoList = istanzaEntityMapper.mapListEntityToListVO(mudeTIstanzaList, null, responseFilters);
        //popolo i dati dei tracciati xml
        for (IstanzaVO istanzaVO : istanzeVoList) {
        	for (MudeRIstanzaTracciato tipoIstanzaTipoTracciato : mudeRIstanzaTracciatoRepository.findAllByDataFineNullAndMudeTIstanza_Id(istanzaVO.getIdIstanza())) {
        		istanzaVO.getTipiTracciato().add(tipoTracciatoEntityMapper.mapEntityToVO(tipoIstanzaTipoTracciato.getMudeDTipoTracciato()));
				istanzaVO.getTracciatiIstanza().add(istanzaTracciatoEntityMapper.mapEntityToVO(tipoIstanzaTipoTracciato, null));
			}
		}

        return istanzeVoList;
    }
	
		
    @Override
    public Response ricercaPaginataIstanze03Slim(String filter, String fruitore, String responseFilters, int page, int size) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "dataCreazione"));
        /*
			String codIstatComune;
			List<String> statiIstanza;
			String numeroFascicoloIntervento;
			Integer annoIntervento;
			Integer annoIstanza;
			String numeroPraticaComunale;
			Integer annoPraticaComunale;
			List<String> tipologieIstanza;
			String tipoIntestatarioIstanza;
			String intestatarioIstanza;
			String nomeIntestatarioIstanza;
			String indirizzo;
			String numeroIstanza; 
         */
        MudeTPratica mudeTPratica = null;
        //
        String codiceIstanza = FilterUtil.getValueWS(filter, "numeroIstanza");
        String codiceFascicolo = FilterUtil.getValueWS(filter, "numeroFascicoloIntervento");
        String tipoIntestatario = FilterUtil.getValueWS(filter, "tipoIntestatarioIstanza");
        String intestatario = FilterUtil.getValueWS(filter, "intestatarioIstanza");
        //TOCHECK  String intestatarioNome = FilterUtil.getValueWS(filter, "tipologieIstanza");
        String intestatarioNome = FilterUtil.getValueWS(filter, "nomeIntestatarioIstanza");
        String indirizzo = FilterUtil.getValueWS(filter, "indirizzo");
        String numeroPratica = FilterUtil.getValueWS(filter, "numeroPraticaComunale");
        String istatComune = FilterUtil.getValueWS(filter, "codIstatComune");
        Integer annoIntervento = FilterUtil.getValueNumWS(filter, "annoIntervento") != null ? FilterUtil.getValueNumWS(filter, "annoIntervento").intValue() : null;
        Long annoIstanza = FilterUtil.getValueNumWS(filter, "annoIstanza") != null ? FilterUtil.getValueNumWS(filter, "annoIstanza").longValue() : null;
        Long annoPratica = FilterUtil.getValueNumWS(filter, "annoPraticaComunale") != null ? FilterUtil.getValueNumWS(filter, "annoPraticaComunale").longValue() : null;
        Long idFruitore = FilterUtil.getValueNumWS(filter, "idFruitore") != null ? FilterUtil.getValueNumWS(filter, "idFruitore").longValue() : null;
        if(idFruitore == null)
        	idFruitore = mudeopenDFruitoreRepository.getIdFruitore(fruitore);
        Long idComune = null;
		if(StringUtils.isBlank(istatComune))
    		throw new BusinessException("CampoObbligatorioException: Campo comune ISTAT obbligatorio");
    	MudeDComune comune = mudeDComuneRepository.findByIstatComuneAndFineValidita(istatComune);
    	if(comune == null)
    		throw new BusinessException("CampoObbligatorioException: codice istat (" + istatComune + ") inesistente");
    	
    	idComune = comune.getIdComune();
        Specification<MudeTIstanzaSLIM> filterFruitore = MudeTIstanzaSlimSpecificationWS.filterByFruitore(idFruitore);
        Specification<MudeTIstanzaSLIM> filterStato = null;
        List<String> statiIstanza = FilterUtil.getValues(filter, "statiIstanza"); 
        if(statiIstanza != null && statiIstanza.size() >0)
        	filterStato = MudeTIstanzaSlimSpecificationWS.hasStatoIn(FilterUtil.getValues(filter, "statiIstanza"));
        List<String> tipologieIstanza = FilterUtil.getValues(filter, "tipologieIstanza");
        Specification<MudeTIstanzaSLIM> filterTipoIstanza = null;
        if(tipologieIstanza != null && tipologieIstanza.size()>0)
        	filterTipoIstanza = MudeTIstanzaSlimSpecificationWS.hasTipoIstanzaIn(FilterUtil.getValues(filter, "tipologieIstanza"));
        
        String[] specieDSRegional = mudeCProprietaRepository.getValueByName("DS_USER_ROLE_SP_VISIBILITY", "SPE00RP211,SPE00RP207,SPE00RP208,SPE00RP212").split(",");
        // this should be split for each specification
		Specification<MudeTIstanzaSLIM> filters = MudeTIstanzaSlimSpecificationWS.findByWS(specieDSRegional, idComune, numeroPratica, annoPratica,
				annoIstanza, tipoIntestatario, intestatario, intestatarioNome, indirizzo, annoIntervento);
		Page<MudeTIstanzaSLIM> mudeTIstanzaList = mudeopenTIstanzaRepository.findAll(Specifications.where(filters)
																				.and(filterStato)
																				.and(filterTipoIstanza)
																				.and(filterFruitore)
																				.and(MudeTIstanzaSlimSpecificationWS.hasCodiceIstanza(codiceIstanza))
																				.and(MudeTIstanzaSlimSpecificationWS.hasCodiceFascicolo(codiceFascicolo))
																			, pageable);
		List<IstanzaEstesaVO> istanzeVoList = istanzaMapper.mapVOToIstanzaEstesa(mudeTIstanzaList.getContent());
        ObjectMapper mapper = new ObjectMapper();
		String str = "";
		int len = 0;
		try {
			str = mapper.writeValueAsString(istanzeVoList);
			len = str.getBytes("UTF-8").length;
		} catch (JsonProcessingException | UnsupportedEncodingException e) {}
        return Response.ok(str)
                .header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING)
                .header(HttpHeaders.CONTENT_LENGTH, len)
                .header("NumPagina", page + 1)
        		.header("NumIstanzeRestituite", mudeTIstanzaList.getNumberOfElements())
        		.header("NumIstanzeTotali", mudeTIstanzaList.getTotalElements())
        		.header("NumPagineTotali", mudeTIstanzaList.getTotalPages())
        		.header("ListaCompleta", mudeTIstanzaList.getTotalElements() <= istanzeVoList.size())
                .build();
    }

	@Override
	public Optional<MudeTIstanzaSLIM> cercaIstanzaDPSOSuccessivoWSSlim(MudeDFruitore fruitore, String numeroIstanza) throws Exception {
		FiltroRicercaPaginataVO filtroRicercaPaginata = new FiltroRicercaPaginataVO();
		filtroRicercaPaginata.setStatiIstanza(new ArrayList<>());
		filtroRicercaPaginata.getStatiIstanza().addAll(Arrays.asList(StatoIstanza.DEPOSITATA, StatoIstanza.PRESA_IN_CARICO,StatoIstanza.REGISTRATA_DA_PA)
				.stream().map(StatoIstanza::getValue).collect(Collectors.toList()));
		filtroRicercaPaginata.setNumeroIstanza(numeroIstanza);
		filtroRicercaPaginata.setIdFruitore(fruitore.getIdFruitore());
		ObjectMapper mapper = new ObjectMapper();
		String filter = mapper.writeValueAsString(filtroRicercaPaginata);
		return cercaIstanzaSLIM(fruitore, filter);
	}

	@Override
	public Optional<MudeTIstanzaSLIM> cercaIstanzaAPAWSSlim(MudeDFruitore fruitore, String numeroIstanza) throws Exception {
		FiltroRicercaPaginataVO filtroRicercaPaginata = new FiltroRicercaPaginataVO();
		filtroRicercaPaginata.setStatiIstanza(new ArrayList<>());
		filtroRicercaPaginata.getStatiIstanza().add(StatoIstanza.REGISTRATA_DA_PA.getValue());
		filtroRicercaPaginata.setNumeroIstanza(numeroIstanza);
		filtroRicercaPaginata.setIdFruitore(fruitore.getIdFruitore());
		ObjectMapper mapper = new ObjectMapper();
		String filter = mapper.writeValueAsString(filtroRicercaPaginata);
		return cercaIstanzaSLIM(fruitore, filter);
	}

	@Override
	public Optional<MudeTIstanzaSLIM> cercaIstanzaWSSlim(MudeDFruitore fruitore, String numeroIstanza) throws Exception {
		FiltroRicercaPaginataVO filtroRicercaPaginata = new FiltroRicercaPaginataVO();
		filtroRicercaPaginata.setNumeroIstanza(numeroIstanza);
		filtroRicercaPaginata.setIdFruitore(fruitore.getIdFruitore());
		ObjectMapper mapper = new ObjectMapper();
		String filter = mapper.writeValueAsString(filtroRicercaPaginata);
		return cercaIstanzaSLIM(fruitore, filter);
	}

	private Optional<MudeTIstanzaSLIM> cercaIstanzaSLIM(MudeDFruitore fruitore, String filter) throws Exception {

		MudeTPratica mudeTPratica = null;
		//
		String codiceIstanza = FilterUtil.getValueWS(filter, "numeroIstanza");
		String codiceFascicolo = FilterUtil.getValueWS(filter, "numeroFascicoloIntervento");
		String tipoIntestatario = FilterUtil.getValueWS(filter, "tipoIntestatarioIstanza");
		String intestatario = FilterUtil.getValueWS(filter, "intestatarioIstanza");
		//TOCHECK  String intestatarioNome = FilterUtil.getValueWS(filter, "tipologieIstanza");
		String intestatarioNome = FilterUtil.getValueWS(filter, "nomeIntestatarioIstanza");
		String indirizzo = FilterUtil.getValueWS(filter, "indirizzo");
		String numeroPratica = FilterUtil.getValueWS(filter, "numeroPraticaComunale");
		String istatComune = FilterUtil.getValueWS(filter, "codIstatComune");
		Integer annoIntervento = FilterUtil.getValueNumWS(filter, "annoIntervento") != null ? FilterUtil.getValueNumWS(filter, "annoIntervento").intValue() : null;
		Long annoIstanza = FilterUtil.getValueNumWS(filter, "annoIstanza") != null ? FilterUtil.getValueNumWS(filter, "annoIstanza").longValue() : null;
		Long annoPratica = FilterUtil.getValueNumWS(filter, "annoPraticaComunale") != null ? FilterUtil.getValueNumWS(filter, "annoPraticaComunale").longValue() : null;
		Long idFruitore = FilterUtil.getValueNumWS(filter, "idFruitore") != null ? FilterUtil.getValueNumWS(filter, "idFruitore").longValue() : null;
		if(idFruitore == null)
			idFruitore = mudeopenDFruitoreRepository.getIdFruitore(fruitore.getCodiceFruitore());
		List<Long> comuni = null;
		if(!StringUtils.isBlank(istatComune)) {
			MudeDComune comune = mudeDComuneRepository.findByIstatComuneAndFineValidita(istatComune);
			if(comune == null)
				throw new BusinessException("CampoObbligatorioException: codice istat (" + istatComune + ") inesistente");
			comuni = new ArrayList<>();
			comuni.add(comune.getIdComune());
		}

		Specification<MudeTIstanzaSLIM> filterFruitore = MudeTIstanzaSlimSpecificationWS.filterByFruitore(idFruitore);
		Specification<MudeTIstanzaSLIM> filterStato = null;
		List<String> statiIstanza = FilterUtil.getValues(filter, "statiIstanza");
		if(statiIstanza != null && statiIstanza.size() >0)
			filterStato = MudeTIstanzaSlimSpecificationWS.hasStatoIn(FilterUtil.getValues(filter, "statiIstanza"));
		List<String> tipologieIstanza = FilterUtil.getValues(filter, "tipologieIstanza");
		Specification<MudeTIstanzaSLIM> filterTipoIstanza = null;
		if(tipologieIstanza != null && tipologieIstanza.size()>0)
			filterTipoIstanza = MudeTIstanzaSlimSpecificationWS.hasTipoIstanzaIn(FilterUtil.getValues(filter, "tipologieIstanza"));
		// this should be split for each specification
		Specification<MudeTIstanzaSLIM> filters = MudeTIstanzaSlimSpecificationWS.findByWS(comuni, numeroPratica, annoPratica,
				annoIstanza, tipoIntestatario, intestatario, intestatarioNome, indirizzo, annoIntervento);
		List<MudeTIstanzaSLIM> mudeTIstanzaList = mudeopenTIstanzaRepository.findAll(Specifications.where(filters)
				.and(filterStato)
				.and(filterTipoIstanza)
				.and(filterFruitore)
				.and(MudeTIstanzaSlimSpecificationWS.hasCodiceIstanza(codiceIstanza))
				.and(MudeTIstanzaSlimSpecificationWS.hasCodiceFascicolo(codiceFascicolo))
		);
		if(!mudeTIstanzaList.isEmpty()) {
			if(mudeTIstanzaList.size() > 1)
				throw new Exception("[SystemException] Errore: RISULTATI MULTIPLI");
			return Optional.of(mudeTIstanzaList.get(0));
		}

		return Optional.empty();
	}

	public String getNewNumeroMude(MudeDComune mudeDComune, MudeDFruitore fruitore, boolean createAlsoFolder) {
		return mudeTLogNumeriMudeService.getNewNumeroMude(mudeDComune, fruitore, createAlsoFolder);
	}

	private MudeTContatto getContattoIstanzaFruitore(String fruitoreID, MudeTIstanza mudeTIstanza, String nome, String cognome, String ragione, final String role) {

		MudeTContatto mudeTContatto = mudeTContattoRepository.findContattoByFruitore(fruitoreID, StringUtils.defaultString(nome) + StringUtils.defaultString(cognome) + StringUtils.defaultString(ragione));
		if(mudeTContatto != null)
			return mudeTContatto;
			
		mudeTContatto = new MudeTContatto();
		mudeTContatto.setGuid(fruitoreID);
		mudeTContatto.setNome(nome);
		mudeTContatto.setCognome(cognome);
		mudeTContatto.setRagioneSociale(ragione);
		mudeTContatto = mudeTContattoRepository.saveDAO(mudeTContatto);
		
        MudeRIstanzaSoggetto mudeRIstanzaSoggetto = new MudeRIstanzaSoggetto();
        mudeRIstanzaSoggetto.setMudeTContatto(mudeTContatto);
        mudeRIstanzaSoggetto.setMudeTIstanza(mudeTIstanza);
        ArrayList<String> ruoli = new ArrayList<String>();
        ruoli.add(role);
		mudeRIstanzaSoggetto.setRuoli(ruoli );
        mudeRIstanzaSoggetto = mudeRIstanzaSoggettoRepository.saveDAO(mudeRIstanzaSoggetto);
        return mudeTContatto;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, 
                   isolation = Isolation.DEFAULT,
                   rollbackFor = Exception.class,
                   readOnly = false)
    public MudeTIstanza salvaIstanzaRequest(MudeDFruitore fruitore, IstanzaExtVO istanzaExt) throws Exception {
		if(StringUtils.isBlank(istanzaExt.getCodIstatComune()))
			throw new Exception("[CampoObbligatorioException] Il campo Codice ISTAT Comune è obbligatorio");
		/*
		// it is required to have main OG role
		MudeRRuoloFruitore mudeRRuoloFruitore = mudeRRuoloFruitoreRepository.findByIdFruitoreNoType(fruitore.getIdFruitore());
		if(mudeRRuoloFruitore == null || mudeRRuoloFruitore.getMudeDRuoloUtente().getCodice().equals(TipoRuoloUtente.UTENTE_LETTORE_OPERAZIONI.getValue()))
			throw new Exception("[FruitoreNonAbilitatoOperazioneException] OP_invioIstanza");
		*/
		
		MudeRComuneFruitore comuneFruitore = mudeopenRComuneFruitoreRepository.findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(istanzaExt.getCodIstatComune(), fruitore.getCodiceFruitore());
		if(comuneFruitore == null)
			throw new Exception("[FruitoreNoComuniAssociatiException] Nessun Comune associato al fruitore " + fruitore.getCodiceFruitore());
		
		if(StringUtils.isBlank(istanzaExt.getTipologiaIstanza()))
			throw new Exception("[CampoObbligatorioException] Tipologia istanza");
		MudeRTipoIstanzaFruitore mudeRTipoIstanzaFruitore = mudeopenRTipoIstanzaFruitoreRepository.findByMudeDFruitore_codiceFruitoreAndMudeDTipoIstanza_codice(fruitore.getCodiceFruitore(), istanzaExt.getTipologiaIstanza());
		if(mudeRTipoIstanzaFruitore == null)
			throw new Exception("TipologiaIstanzaNonAbilitataFruitoreException] Errore 01: Fruitore non abilitato alla tipologia istanza " + istanzaExt.getTipologiaIstanza());
		if(StringUtils.isBlank(istanzaExt.getNumeroFascicoloIntervento()))
			throw new Exception("[CampoObbligatorioException] Numero fascicolo");
		if(istanzaExt.getNumeroFascicoloIntervento().length() != 22 && istanzaExt.getNumeroFascicoloIntervento().length() != 25)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Fascicolo Intervento non deve contenere più di 22 0 25 caratteri");
		
		if(StringUtils.isBlank(istanzaExt.getNumeroIstanza()))
			throw new Exception("[CampoObbligatorioException] Numero istanza");
		if(istanzaExt.getNumeroIstanza().length() != 22 && istanzaExt.getNumeroIstanza().length() != 25)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Numero Istanza non deve contenere un numero diverso da 22 o 25 caratteri");
		if(findByNumeroIstanza(istanzaExt.getNumeroIstanza()) != null)
			throw new Exception("[IstanzaInviataException] Errore 04: Istanza "+ istanzaExt.getNumeroIstanza() + " già inviata");
		if(!istanzaExt.getNumeroIstanza().replaceAll("-", "").substring(2, 8).equals(istanzaExt.getCodIstatComune()))
			throw new Exception("[NumeroIstanzaComuneNonCoerentiException] Errore 04: Numero istanza - Comune non coerenti");
		if(StringUtils.isBlank(istanzaExt.getSpeciePratica()))
			throw new Exception("[CampoObbligatorioException] Specie pratica");
		if(istanzaExt.getSpeciePratica().length() > 10)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Specie Pratica non deve contenere più di 10 caratteri");
		MudeDSpeciePratica mudeDSpeciePratica = mudeDSpeciePraticaRepository.findByCodice(istanzaExt.getSpeciePratica());
		if(mudeDSpeciePratica == null)
			throw new Exception("[ValoreNonAmmessoException] Errore 04: Specie pratica " +  istanzaExt.getSpeciePratica() + " non valida");
		/*
		 * NOT FOUND IN DOC: "specie pratica" related to "tipo istanza" 
		if(mudeDSpeciePraticaRepository.findBySpeciePraticaAndTipoIstanza(istanzaExt.getSpeciePratica(), istanzaExt.getTipologiaIstanza()) == null)
			throw new Exception("[SpeciePraticaNonRiferitaModelloException] Errore 04: Specie Pratica non riferira al modello");
		*/
		
		if(istanzaExt.getAnnoIntervento() == null)
			throw new Exception("[CampoObbligatorioException] Anno intervento obbligatorio");
		if((""+istanzaExt.getAnnoIntervento()).length() != 4)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Anno Intervento non deve contenere più di 4 caratteri");
		
		if(istanzaExt.getAnnoIstanza() == null)
			throw new Exception("[CampoObbligatorioException] Anno istanza obbligatorio");
		if((""+istanzaExt.getAnnoIstanza()).length() != 4)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Anno Istanza non deve contenere più di 4 caratteri");
		
		if(StringUtils.isBlank(istanzaExt.getTipoIntestatario()))
			throw new Exception("[CampoObbligatorioException] Tipo intestatario obbligatorio");
		if(istanzaExt.getTipoIntestatario().length() != 1)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo TipoIntestatario non deve contenere più di 1 caratteri");
		if("F".equals(istanzaExt.getTipoIntestatario())) {
			if(StringUtils.isBlank(istanzaExt.getIntestatarioNome()))
				throw new Exception("[CampoObbligatorioException] Errore 02: Il campo Intestatario Nome è obbligatorio");
			if(istanzaExt.getIntestatarioNome().length() > 50)
				throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Intestatario Nome non deve contenere più di 50 caratteri");
			if(StringUtils.isBlank(istanzaExt.getIntestatarioCognome()))
				throw new Exception("[CampoObbligatorioException] Errore 02: Il campo Intestatario Cognome è obbligatorio");
			if(istanzaExt.getIntestatarioCognome().length() > 50)
				throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Intestatario Cognome non deve contenere più di 50 caratteri");
			if(StringUtils.isNotBlank(istanzaExt.getIntestatarioRagioneSociale()))
				throw new Exception("[ValoreNonAmmessoException] Errore 04: Nome Intestatario Ragione Sociale solo per persone giuridiche");
		} 

		else if("G".equals(istanzaExt.getTipoIntestatario())) {
			if(StringUtils.isNotBlank(istanzaExt.getIntestatarioNome()))
				throw new Exception("[ValoreNonAmmessoException] Errore 04: Nome Intestatario solo per persone fisiche");
			if(StringUtils.isNotBlank(istanzaExt.getIntestatarioCognome()))
				throw new Exception("[ValoreNonAmmessoException] Errore 04: Cognome Intestatario solo per persone fisiche");
			if(StringUtils.isBlank(istanzaExt.getIntestatarioRagioneSociale()))
				throw new Exception("[CampoObbligatorioException] Errore 02: Il campo Intestatario Ragione Sociale è obbligatorio");
			if(istanzaExt.getIntestatarioRagioneSociale().length() > 100)
				throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 100 caratteri");
		}

		else
			throw new Exception("[ValoreNonAmmessoException] Errore04: Tipo Intestatario " +  istanzaExt.getTipoIntestatario() + " valore non ammesso");
		
		if(StringUtils.isBlank(istanzaExt.getProfessionistaCognome()))
			throw new Exception("[CampoObbligatorioException] Professionista cognome obbligatorio");
		if(istanzaExt.getProfessionistaCognome().length() > 50)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Professionista Cognome non deve contenere più di 50 caratteri");
		if(StringUtils.isBlank(istanzaExt.getProfessionistaNome()))
			throw new Exception("[CampoObbligatorioException] Professionista nome obbligatorio");
		if(istanzaExt.getProfessionistaNome().length() > 50)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Professionista Nome non deve contenere più di 50 caratteri");
		if(StringUtils.isBlank(istanzaExt.getCompilanteCodiceFiscale()))
			throw new Exception("[CampoObbligatorioException] Professionista codice fiscale obbligatorio");
		
		if(!"S".equals(istanzaExt.getOperePrecarie()) && !"N".equals(istanzaExt.getOperePrecarie()))
			throw new Exception("[ValoreNonAmmessoException] Errore 04: Opere precarie " + istanzaExt.getOperePrecarie() + " valore non valido");
		
		if(StringUtils.isBlank(istanzaExt.getUbicazioneDescVia()))
			throw new Exception("[CampoObbligatorioException] Ubicazione Ubicazione Desc obbligatorio");
		if(istanzaExt.getUbicazioneDescVia().length() > 255)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 255 caratteri");
		
		if(StringUtils.defaultString(istanzaExt.getUbicazioneNumeroCivico()).length() > 8)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 9 caratteri");
		
		if(StringUtils.defaultString(istanzaExt.getUbicazioneBis()).length() > 6)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 6 caratteri");
		
		if(StringUtils.defaultString(istanzaExt.getUbicazioneInterno()).length() > 4)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 4 caratteri");
		if(StringUtils.isBlank(istanzaExt.getUbicazioneSedime()))
			throw new Exception("[CampoObbligatorioException] Ubicazione Ubicazione Desc obbligatorio");
		if(istanzaExt.getUbicazioneSedime().length() > 30)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 30 caratteri");
		MudeDDug dug = mudeDDugRepository.findByDenominazioneAndDataFineIsNull(istanzaExt.getUbicazioneSedime().toUpperCase());
		if(dug == null)
			throw new Exception("[CampoObbligatorioException] Campo sedime non valido");
		
		if(istanzaExt.getTimestampInserimento() == null)
			throw new Exception("[CampoObbligatorioException] Errore  02: Il campo Timestamp inserimento è obbligatorio");
		
		Date tdateInserimento = null;
		try {
			tdateInserimento = new SimpleDateFormat("dd-MM-yyy HH:mm:ss").parse(istanzaExt.getTimestampInserimento().replaceAll("/", "-"));
		} catch (Exception e) {
			throw new Exception("[ValoreNonAmmessoException] Errore  04: timestampInserimento valore non ammesso. Formato corretto: dd/MM/yyyy HH:mm:ss");
		}

		MudeTIstanza istanzaRiferimento = null;
		if(istanzaExt.getNumeroMudeIstanzaPrincipale() != null) {
			if(StringUtils.defaultString(istanzaExt.getNumeroMudeIstanzaPrincipale()).length() != 22 && StringUtils.defaultString(istanzaExt.getNumeroMudeIstanzaPrincipale()).length() != 25)
				throw new Exception("[ValoreNonAmmessoException] Errore  03: Il campo Numero Mude Istanza Principale non deve contenere più di 22 o 25 caratteri");
			if((istanzaRiferimento = findByNumeroIstanza(istanzaExt.getNumeroMudeIstanzaPrincipale())) == null)
				throw new Exception("[NumeroIstanzaRiferimentoInesistenteException] Errore 04: "+ "Numero istanza di riferimento " + istanzaExt.getNumeroMudeIstanzaPrincipale() + " inesistente");
		}
		
		Long idFascicolo = mudeTLogNumeriMudeService.getIdByNumeroTipoCodiceFruitore(istanzaExt.getNumeroFascicoloIntervento(), TipoLogNumeriMude.FASCICOLO.getCode(), fruitore.getCodiceFruitore());
		if(idFascicolo == null)
			throw new Exception("[NumeroFascicoloNonCorrettoException] Errore 04: Numero fascicolo " + istanzaExt.getNumeroFascicoloIntervento() +" non corretto");
		Long idIstanza = mudeTLogNumeriMudeService.getIdByNumeroTipoCodiceFruitore(istanzaExt.getNumeroIstanza(), TipoLogNumeriMude.ISTANZA.getCode(), fruitore.getCodiceFruitore());
		if(idIstanza == null)
			throw new Exception("[NumeroIstanzaNonCorrettoException] Errore 04: Numero istanza " + istanzaExt.getNumeroIstanza() +" non corretto");
		if(istanzaExt.getTracciatiXML() == null || istanzaExt.getTracciatiXML().isEmpty()) 
			throw new Exception("[CampoObbligatorioException] Errore 02: Almeno un tracciato obbligatorio");
		if(istanzaExt.getTracciatiXML().stream().anyMatch(xml -> StringUtils.isBlank(xml.getVersioneTracciato()) || xml.getVersioneTracciato().length() > 10))
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo Versione Tracciato non deve contenere più di 10 caratteri");
		if(istanzaExt.getTracciatiXML().stream().anyMatch(xml -> StringUtils.isBlank(xml.getTracciatoXML())))
			throw new Exception("[CampoObbligatorioException] Il campo TracciatoXML obbligatorio");
		if(!istanzaExt.getTracciatiXML().stream().anyMatch(xml -> { 
					return xml.getTracciatoXML().indexOf("Versione=\""+xml.getVersioneTracciato()+"\"") > -1;  // this generates a stackoverflow: Pattern.compile("<MUDE (\\R|[^>])*Versione[ ]*=[ ]*\""+xml.getVersioneTracciato()+"\"").matcher().find();     
				}))
			throw new Exception("[TracciatoVersioneNonCoerentiException] Errore 04: Tracciato e versione non coerenti");
		if(StringUtils.isBlank(istanzaExt.getCompilanteCodiceFiscale()))
			throw new Exception("[CampoObbligatorioException] Campo Numero MUDE principale obbligatorio");
		if(istanzaExt.getCompilanteCodiceFiscale().length() != 16)
			throw new Exception("[ValoreNonAmmessoException] Errore 03: Il campo non deve contenere più di 16 caratteri");
        // Ubicazione
		MudeTIndirizzo mudeTIndirizzo = new MudeTIndirizzo();
		mudeTIndirizzo.setMudeDComune(comuneFruitore.getComune());
		mudeTIndirizzo.setNumeroCivico(istanzaExt.getUbicazioneNumeroCivico());
		mudeTIndirizzo.setIndirizzo(istanzaExt.getUbicazioneDescVia());
		mudeTIndirizzo.setLocalita("BIS: " + istanzaExt.getUbicazioneBis() + " - INTERNO: " + istanzaExt.getUbicazioneInterno());
    	mudeTIndirizzo.setIdDug(dug.getIdDug());
		mudeTIndirizzoRepository.saveDAO(mudeTIndirizzo);
		
		MudeTFascicolo mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
		if(mudeTFascicolo == null) {
			if(idFascicolo.longValue() != idIstanza.longValue())
				throw new Exception("Il Numero istanza non corrisponde al nuovo Numero fascicolo intervento");
			
			mudeTFascicolo = new MudeTFascicolo();
			mudeTFascicolo.setId(idFascicolo);
			mudeTFascicolo.setCodiceFascicolo(istanzaExt.getNumeroFascicoloIntervento());
			
			mudeTFascicolo.setTipoIstanza(mudeRTipoIstanzaFruitore.getMudeDTipoIstanza());
			mudeTFascicolo.setComune(comuneFruitore.getComune());
			
			/*
			String nIst22 = istanzaExt.getNumeroFascicoloIntervento();
			mudeTFascicolo.setCodiceFascicolo(nIst22.substring(0, 2) + "-" + nIst22.substring(2, 8) + "-" + nIst22.substring(8, 18) + "-" + nIst22.substring(18));
			*/
			
			MudeTFascicolo saved= mudeTFascicoloRepository.saveDAO(mudeTFascicolo);
			if(saved == null)
				throw new Exception("Errore nella creazione del fascicolo");
		}

		if(mudeTIstanzaRepository.findOne(idIstanza) != null)
			 throw new Exception("[IstanzaInviataException] Errore 04: Istanza "+ istanzaExt.getNumeroIstanza() + " già inviata");
		MudeTIstanza mudeTIstanza = new MudeTIstanza();
		mudeTIstanza.setId(idIstanza);
		mudeTIstanza.setCodiceIstanza(istanzaExt.getNumeroIstanza());
		mudeTIstanza.setMudeTFascicolo(mudeTFascicolo);
		mudeTIstanza.setComune(comuneFruitore.getComune());
		// tipo istanza
		mudeTIstanza.setTipoIstanza(mudeRTipoIstanzaFruitore.getMudeDTipoIstanza());
		// specie pratica
		mudeTIstanza.setSpeciePratica(mudeDSpeciePratica);
        // Anno istanza
        mudeTIstanza.setDataDps(new Timestamp(getDate("01-01-"+istanzaExt.getAnnoIstanza()).getTime()));
		// Anno intervento
		mudeTIstanza.setJsonData("{ "
									+ "\"TAB_QUALIF_1\": { "
										+"\"data_avvio_lavori\": \"01-01-"+ istanzaExt.getAnnoIntervento() +"\","
										+"\"opere_in_precario_su_suolo_pubblico\": " + ( "S".equals(istanzaExt.getOperePrecarie())? "true" : "false" )
									+"}"
								+"}");
        if(istanzaRiferimento != null)
            mudeTIstanza.setIdIstanzaRiferimento(istanzaRiferimento.getIdIstanzaRiferimento());
		mudeTIstanza.setDataCreazione(new Timestamp(tdateInserimento.getTime()));
		mudeTIstanza.setCreatore(istanzaExt.getCompilanteCodiceFiscale());
		mudeTIstanza.setIdFruitore(fruitore.getIdFruitore());
		mudeTIstanza.setIdFonte("WS");
		mudeTIstanza.setIndirizzo(mudeTIndirizzo);
		
		/*
		String nIst22 = istanzaExt.getNumeroIstanza();
		mudeTIstanza.setCodiceIstanza(nIst22.substring(0, 2) + "-" + nIst22.substring(2, 8) + "-" + nIst22.substring(8, 18) + "-" + nIst22.substring(18));
		*/
		
        // create istanza
		mudeTIstanzaRepository.saveDAO(mudeTIstanza);
		// stato
		MudeRIstanzaStato mudeRIstanzaStato = new MudeRIstanzaStato();
		mudeRIstanzaStato.setIstanza(mudeTIstanza);
		mudeRIstanzaStato.setStatoIstanza(mudeDStatoIstanzaRepository.findOne("DPS"));
		mudeRIstanzaStato.setDataInizio(new Date());
		mudeRIstanzaStatoRepository.saveDAO(mudeRIstanzaStato);
		
		// instance owner
		MudeTContatto owner = getContattoIstanzaFruitore(fruitore.getCodiceFruitore(), mudeTIstanza, istanzaExt.getIntestatarioNome(), istanzaExt.getIntestatarioCognome(), istanzaExt.getIntestatarioRagioneSociale(), "IN");
		
		// instance PM
		MudeTContatto pm = getContattoIstanzaFruitore(fruitore.getCodiceFruitore(), mudeTIstanza, istanzaExt.getProfessionistaNome(), istanzaExt.getProfessionistaNome(), null, "PR");
		
		// XML
		try {
			StringBuffer processed = new StringBuffer();
			istanzaExt.getTracciatiXML().stream().forEach(xml -> {
				MudeDTipoTracciato mudeDTipoTracciato = mudeDTipoTracciatoRepository.findTracciatoByVersion(xml.getVersioneTracciato(), "");
				if(mudeDTipoTracciato == null)
					throw new BusinessException("[VersioneTracciatoInesistenteException] Errore 04: Versione tracciato " + xml.getVersioneTracciato() + " inesistente");
				if((mudeDTipoTracciato = mudeDTipoTracciatoRepository.findTracciatoByVersion(xml.getVersioneTracciato(), mudeRTipoIstanzaFruitore.getMudeDTipoIstanza().getCodice())) == null)
					throw new BusinessException("[TipoTracciatoNonPrevistoTipoIstanzaException] Errore  04:Tipo tracciato " + xml.getVersioneTracciato() + " non prevista per la tipologia di istanza");
				
				if(processed.indexOf("~"+xml.getVersioneTracciato()) > -1)
					throw new BusinessException("[TracciatoVersioneNonCoerentiException] Errore 04: Non possono essere inviati più tracciati XML con la stessa versione, per un'unica istanza");
				processed.append("~"+xml.getVersioneTracciato());
                String xsdSchema = mudeDTipoTracciato.getXsdValidazione();
                
				String validationErrors = "";
                if("abilitato".equals(mudeCProprietaRepository.getValueByName("REST_INVIOIST_XML_XSD", "abilitato"))) {
    				Boolean isValidXml = false;
                	try {
                        // validation against XSD
                		String xmlCleaned = xml.getTracciatoXML();
                		
                		if(xmlCleaned.indexOf("<") > 0)
                			xmlCleaned = xmlCleaned.substring(xmlCleaned.indexOf("<"));
                		
                		//xmlCleaned = xmlCleaned.replaceAll("[^<]*<\\?xml version=\"1.0\" encoding=\"UTF-[0-9]+\"\\?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                		
                		XmlValidator xmlValidator = new XmlValidator(xsdSchema, xmlCleaned);
        				isValidXml = xmlValidator.isValid();

        				if(!isValidXml && xmlValidator.listParsingExceptions() != null) {
        					for(Exception ex : xmlValidator.listParsingExceptions())
        						validationErrors += "\r\n" + ex.getMessage();

        					logger.info("[IstanzaExtVO::salvaIstanzaRequest] idIstanza " + istanzaExt.getNumeroIstanza() + " validation errors:" + validationErrors);
        				}
        				
					} catch (Throwable e) { }
                	
    				if(!isValidXml)
    					throw new BusinessException("Tracciato non valido per il modello dell'istanza. Errore validazione dello schema XSD: " + validationErrors);
                }

				MudeRIstanzaTracciato mudeRIstanzaTracciato = new MudeRIstanzaTracciato();
				mudeRIstanzaTracciato.setMudeTIstanza(mudeTIstanza);
				mudeRIstanzaTracciato.setMudeDTipoTracciato(mudeDTipoTracciato);
				mudeRIstanzaTracciato.setXmlTracciato(xml.getTracciatoXML());
				
				mudeRIstanzaTracciatoRepository.saveDAO(mudeRIstanzaTracciato);
			});		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return mudeTIstanza; // OK, no errors
	}

	public List<GeoRiferimento> estraiElencoGeoRiferimento (MudeTIstanzaSLIM istanza, String fruitoreID){
		Long idIstanza = istanza.getId();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
	    
		List<GeoRiferimento> geoRiferimentoVOs = new ArrayList<GeoRiferimento>();
		List<MudeopenRLocGeoriferim> geoRiferimentos = mudeopenRLocGeoriferimRepository.findByIdIstanza(idIstanza);
		if(geoRiferimentos.size() == 0)
			geoRiferimentos = mudeopenRLocGeoriferimRepository.findByIdIstanzaInFascicolo(istanza.getIdFascicolo());
		
		CSIGeometry csiGeometry = null;
		MudeTGeecoSelectedCllbk jsonResult = mudeTGeecoSelectedCllbkRepository.getLatestSessionPosition(idIstanza);
		if(jsonResult != null && jsonResult.getSelectedPosition() != null)
			try {
				CSIPolygon csiPolygon = new CSIPolygon();
		        ObjectNode jsonData =  (ObjectNode)mapper.readTree(jsonResult.getSelectedPosition());
		        
		        String type = jsonData.get("geometry").get("type").toString().replaceAll("\"", "");
		        if("MultiPoint".equals(type))
		            for (JsonNode jsonNode : jsonData.get("geometry").get("coordinates"))
		            	// should be getCsiMultiPoint??????
		            	csiPolygon.getShell().getCoords().getCSICoordinate().add(
										            			new CSICoordinate(
																		mapper.treeToValue(jsonData.get(0), Double.class), 
																		mapper.treeToValue(jsonData.get(1), Double.class)));
		        else if("Polygon".equals(type))
		            for (JsonNode jsonNode : jsonData.get("geometry").get("coordinates").get(0))
		            	csiPolygon.getShell().getCoords().getCSICoordinate().add(
										            			new CSICoordinate(
																		mapper.treeToValue(jsonNode.get(0), Double.class), 
																		mapper.treeToValue(jsonNode.get(1), Double.class)));
		        else if("MultiPolygon".equals(type)) {
		        	int counter = 0;
		            for (JsonNode firstArray : jsonData.get("geometry").get("coordinates").get(0))
		            	if(counter++ == 0) // first array element is the house polygon
				            for (JsonNode jsonNode : firstArray)
			            		csiPolygon.getShell().getCoords().getCSICoordinate().add(
											            			new CSICoordinate(
																			mapper.treeToValue(jsonNode.get(0), Double.class), 
																			mapper.treeToValue(jsonNode.get(1), Double.class)));
		            	else { // the remaining are the hole polygons
		            		CSILinearRing csiLinearRing = csiPolygon.getHoles().addNewCSILinearRing();
				            for (JsonNode jsonNode : firstArray)
				            	csiLinearRing.getCoords().getCSICoordinate().add(
											            			new CSICoordinate(
																			mapper.treeToValue(jsonNode.get(0), Double.class), 
																			mapper.treeToValue(jsonNode.get(1), Double.class)));
		            	}
		        }
		        
		        (csiGeometry = new CSIGeometry()).setCsiPolygon(csiPolygon);
			} catch (Exception skipGeometry) {
		        logger.error("[MudeopenTIstanzaServiceImpl::estraiElencoGeoRiferimento] impossible read geometry", skipGeometry);
			}

        for(MudeopenRLocGeoriferim mudeopenRLocGeoriferim : geoRiferimentos) {
        	// esclude loc_ubicazione when it is manual: desc_livello_poligono
        	if(StringUtils.isBlank(mudeopenRLocGeoriferim.getDescLivelloPoligono())) continue;
        	
        	GeoRiferimento geoRiferimentoVO = istanzaMapper.mapEntityGeoRiferimentoToVO(mudeopenRLocGeoriferim);
        	List<MudeopenRLocCatasto> geoCatastos = mudeopenRLocCatastoRepository.findByIdGeoriferimento(mudeopenRLocGeoriferim.getIdGeoriferimento());
        	List<GeoCatasto> geoCatastoVOs = istanzaMapper.mapListEntityGeoCatastoToListVO(geoCatastos);
        	geoRiferimentoVO.setGeoCatastos(geoCatastoVOs);
        	
        	List<MudeopenRLocUbicazione> geoUbicaziones = mudeopenRLocUbicazioneRepository.findByIdGeoriferimento(mudeopenRLocGeoriferim.getIdGeoriferimento());
        	List<GeoUbicazione> geoUbicazioniVOs = istanzaMapper.mapListEntityGeoUbicazioniToListVO(geoUbicaziones);
        	geoRiferimentoVO.setGeoUbicaziones(geoUbicazioniVOs);
        	
        	List<MudeopenRLocDatocarota> geoDatoCarotas = mudeopenRLocDatoCarotaRepository.findByIdGeoriferimento(mudeopenRLocGeoriferim.getIdGeoriferimento());
        	List<GeoDatocarota> geoDatocarotaVOs = istanzaMapper.mapListEntityGeoDatocarotaToListVO(geoDatoCarotas);
        	geoRiferimentoVO.setGeoDatocarotas(geoDatocarotaVOs);
        	
        	List<MudeopenRLocCellula> geoCellulasGeoRif = mudeopenRLocCellulaRepository.findByIdGeoriferimento(mudeopenRLocGeoriferim.getIdGeoriferimento());
        	List<GeoCellula> geoCellulasGeoRifVOs = new ArrayList<GeoCellula>();
        	
            for(MudeopenRLocCellula mudeopenRLocCellula : geoCellulasGeoRif) {            	 
            	 GeoCellula geoCellulaVO = istanzaMapper.mapEntityGeoCellulaToVO(mudeopenRLocCellula);
            	 geoCellulaVO.setDataGeoriferimento(geoRiferimentoVO.getDataGeoriferimento());
            	 geoCellulasGeoRifVOs.add(geoCellulaVO);
            }

            geoRiferimentoVO.setGeoCellulas(geoCellulasGeoRifVOs);
            geoRiferimentoVO.setCsiGeometry(csiGeometry);
            geoRiferimentoVOs.add(geoRiferimentoVO);
        }

        return geoRiferimentoVOs;
	}
	
	public List<GeoUbicazione> estraiElencoUbicazione(IstanzaVO istanzaVO, String fruitoreID) {
    	return istanzaMapper.mapListEntityGeoUbicazioniToListVO(mudeopenRLocUbicazioneRepository.findAllByIdIstanza(istanzaVO.getIdIstanza()));
	}
}
