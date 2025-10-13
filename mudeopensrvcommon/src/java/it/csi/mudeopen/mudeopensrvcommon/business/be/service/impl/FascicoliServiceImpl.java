/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.FascicoloExcelBuilder;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIntestatarioRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocGeoriferimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.CodeGenerator;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTFascicoloSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTPraticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaFascicoloRequest;

@Service
public class FascicoliServiceImpl implements FascicoliService {

    private static final String BACKOFFICE = "backoffice";

	private static Logger logger = Logger.getLogger(FascicoliServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;

    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private MudeDProvinciaRepository mudeDProvinciaRepository;

    @Autowired
    private MudeDTipoInterventoRepository mudeDTipoInterventoRepository;

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private MudeRTipoIstanzaRepository mudeRTipoIstanzaRepository;

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;

    @Autowired
    private MudeRFascicoloStatoRepository mudeRFascicoloStatoRepository;

    @Autowired
    private MudeRFascicoloIntestatarioRepository mudeRFascicoloIntestatarioRepository;

    @Autowired
    private MudeDStatoFascicoloRepository mudeDStatoFascicoloRepository;

    @Autowired
    private FascicoloUtenteService fascicoloUtenteService;

    @Autowired
    private MudeRFascicoloUtenteRepository mudeRFascicoloUtenteRepository;

    @Autowired
    private MudeRFascicoloIndirizzoRepository mudeRFascicoloIndirizzoRepository;

    @Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;

    @Autowired
    private IstanzaStatoService istanzaStatoService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
    
    @Autowired
    private MudeopenRLocGeoriferimRepository mudeopenRLocGeoriferimRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FascicoloVO salvaFascicolo(SalvaFascicoloRequest request, MudeTUser mudeTUser, String numeroFascicolo) {
        MudeTFascicolo mudeTFascicolo = null;
        CsiLogAudit.TraceOperation operation = CsiLogAudit.TraceOperation.INSERIMENTO_FASCICOLO;
        MudeDComune comune = mudeDComuneRepository.findByIdComune(request.getComune().loadIdAsLong());

        if (request.getIdFascicolo() != null) {
            mudeTFascicolo = mudeTFascicoloRepository.findOne(request.getIdFascicolo());
            operation = CsiLogAudit.TraceOperation.MODIFICA_FASCICOLO;
        } else {
            mudeTFascicolo = new MudeTFascicolo();
        }

        mudeTFascicolo.setMudeTUser(mudeTUser);
        mudeTFascicolo.setTipoIstanza(mudeDTipoIstanzaRepository.findOne(request.getTipologiaIstanza().getId()));
        mudeTFascicolo.setComune(comune);
        mudeTFascicolo.setDataCreazione(new Timestamp(System.currentTimeMillis()));

        if(request.getTipologiaIntervento() != null)
        	mudeTFascicolo.setTipoIntervento(mudeDTipoInterventoRepository.findOne(request.getTipologiaIntervento().getId()));

        mudeTFascicoloRepository.saveDAO(mudeTFascicolo);

        // genero codiceFascicolo -
        // il primo gruppo di due numeri e' il codice istat regionale
        // il secondo gruppo di 4 numeri e' il codice istat comunale
        // il terzo gruppo e' un progressivo di 10 numeri
        // l'ultimo e' l'anno

        if (operation == CsiLogAudit.TraceOperation.INSERIMENTO_FASCICOLO) {
        	if(StringUtils.isBlank(numeroFascicolo)) {
        		mudeTFascicolo.setCodiceFascicolo(CodeGenerator.getNewCodiceFascicolo(mudeTFascicolo.getId(), comune));
        	} else {
        		mudeTFascicolo.setCodiceFascicolo(numeroFascicolo);
        	}

            mudeTFascicoloRepository.saveDAO(mudeTFascicolo);

            MudeDStatoFascicolo statoFascicolo = mudeDStatoFascicoloRepository.findMudeDStatoFascicoloByCodice("OPN");
            MudeRFascicoloStato fascicoloStato = new MudeRFascicoloStato();
            fascicoloStato.setFascicolo(mudeTFascicolo);
            fascicoloStato.setStatoFascicolo(statoFascicolo);
            fascicoloStato.setDataInizio(Calendar.getInstance().getTime());
            mudeRFascicoloStatoRepository.saveDAO(fascicoloStato);

            fascicoloUtenteService.save(mudeTFascicolo.getId(), AbilitazioniEnum.FASCIC_CREATORE.getDescription(), mudeTUser);
        }

        MudeRFascicoloIndirizzo mudeRFascicoloIndirizzo = mudeRFascicoloIndirizzoRepository.findByMudeTFascicolo_IdAndDataFineIsNull(mudeTFascicolo.getId());
        MudeTIndirizzo indirizzo = null;
        if (null == mudeRFascicoloIndirizzo) {
            mudeRFascicoloIndirizzo = new MudeRFascicoloIndirizzo();
            mudeRFascicoloIndirizzo.setMudeTFascicolo(mudeTFascicolo);
            indirizzo = new MudeTIndirizzo();
            mudeRFascicoloIndirizzo.setDataInizio(Calendar.getInstance().getTime());
            mudeTIndirizzoRepository.saveDAO(indirizzo);
        }
        else{
            indirizzo = mudeRFascicoloIndirizzo.getIndirizzo();
        }
        indirizzo.setMudeDComune(comune);
        mudeRFascicoloIndirizzo.setIndirizzo(indirizzo);
        mudeRFascicoloIndirizzoRepository.saveDAO(mudeRFascicoloIndirizzo);

        FascicoloVO fascicolo = fascicoloEntityMapper.mapEntityToVO(mudeTFascicolo, mudeTUser);

        utilsTraceCsiLogAuditService.traceCsiLogAudit(operation.getOperation(), mudeTFascicolo.getTableName(), "id=" + mudeTFascicolo.getId() + ", condiceFascicolo" + mudeTFascicolo.getCodiceFascicolo() + ", idTipoIstanza=" + request.getTipologiaIstanza().getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return fascicolo;
    }

    @Override
    public FascicoloVO recuperaFascicolo(MudeTUser mudeTUser, Long idFascicolo) {

        //		fascicolo.setCategoriaIIntervento(request.getTipologiaIstanza());
        MudeTFascicolo mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
        FascicoloVO fascicolo = fascicoloEntityMapper.mapEntityToVO(mudeTFascicolo, mudeTUser);
        return fascicolo;
    }

    @Override
    public Response recuperaFascicoliUtente(MudeTUser mudeTUser, String codiceTipoIntervento, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idComune, Long idProvincia, Long idDug, String duf, LocalDate dataCreazioneDa, LocalDate dataCreazioneA, String codiceFascicolo, String state, int page, int size, String scope) {

        Pageable pageble = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "dataCreazione"));

        // cerco i fascicoli creati dall'utente
        List<Long> idsContatti = mudeTContattoRepository.findIdContattoByCfAndMudeTUser(mudeTUser.getCf());
        Long idContatto = idsContatti.isEmpty() ? null : Collections.max(idsContatti);
        Set<Long> fascicoli = null;
        if (!BACKOFFICE.equals(scope) && null != idContatto) {
            fascicoli = mudeRIstanzaSoggettoRepository.findAllIdFascicoloByMudeTContattoId(idContatto);
        }

        List<MudeTIndirizzo> ubicazioni = null;
        List<Long> comuni = null;
        String mudeDTipoIntervento = null;
        LocalDate timestampCreazioneDa = null;
        LocalDate timestampCreazioneA = null;
        if (idComune != null && idComune == 0) {
            idComune = null;
        }
        if (idProvincia != null && idProvincia == 0) {
            idProvincia = null;
        }
        if (idDug != null && idDug == 0) {
            idDug = null;
        }
        if (StringUtils.isBlank(duf)) {
            duf = null;
        }

        if (idComune != null) {
            MudeDComune mudeDComune = mudeDComuneRepository.findByIdComune(idComune);
            comuni = new ArrayList<Long>();
            comuni.add(mudeDComune.getIdComune());
        } else if (idProvincia != null) {
            MudeDProvincia mudeDProvincia = mudeDProvinciaRepository.findOne(idProvincia);
            comuni = mudeDComuneRepository.findIdComuneByMudeDProvinciaOrderByDenomComuneAsc(mudeDProvincia);
        }

        if (!BACKOFFICE.equals(scope) && StringUtils.isNotBlank(codiceTipoIntervento)) {
            mudeDTipoIntervento = mudeDTipoInterventoRepository.findOne(codiceTipoIntervento).getCodice();
        }
        Long longCreazioneDa=-1L;
        Long longCreazioneA=-1L;
        if (dataCreazioneDa != null) {
            timestampCreazioneDa = dataCreazioneDa;
            longCreazioneDa = timestampCreazioneDa.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC);
        }

        if (dataCreazioneA != null) {
            timestampCreazioneA = dataCreazioneA;
            longCreazioneA = timestampCreazioneA.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC);
        }

     // Fix RUOLI Utente BO sull'istanza
	    Specification<MudeTFascicolo> filterByRole = null;
    	if(BACKOFFICE.equals(scope)) {
    		int num_max_istances_role = Integer.parseInt(getValueFromMudeCProprieta("MUDE_NUM_MAX_ISTANCES_ROLE","20000"));
    		// Fix RUOLI Utente BO
          	 List <Long> mudeTIstanzaIdsListRuoli = null;
          	 
          	 if(codiceFascicolo != null || idComune != null || state != null)
           		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdAllFascicoloByRuoli(
           				mudeTUser.getIdUser(), 
           				codiceFascicolo == null? "" : codiceFascicolo, 
           				idComune == null? -1 : idComune,
           				state == null? "" : state,
           				longCreazioneDa,
           				longCreazioneA
           				);          	
           	 else
           		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdByRuoli(mudeTUser.getIdUser());
          	 if(mudeTIstanzaIdsListRuoli == null || mudeTIstanzaIdsListRuoli.isEmpty()) {
          		List<PraticaVO> praticaVoList = new ArrayList<PraticaVO>();
          		return PaginationResponseUtil.buildResponse(praticaVoList, page, size, 0, 0);
          	 }else if(mudeTIstanzaIdsListRuoli.size()>num_max_istances_role) {
          		throw new BusinessException("Troppi elementi da visualizzare. Si consiglia di impostare uno o piu' filtri.");
          	 }
	       	
          	List <Long> mudeTFascicoloIdsListRuoli = mudeTFascicoloRepository.getFascicoliIdByRuoli(mudeTIstanzaIdsListRuoli);
	       	if(mudeTFascicoloIdsListRuoli == null || mudeTFascicoloIdsListRuoli.isEmpty()) {
	       		List<FascicoloVO> fascicoloVOList = new ArrayList<FascicoloVO>();
	    		return PaginationResponseUtil.buildResponse(fascicoloVOList, page, size, 0, 0);
	       	}
	   	
	       	HashMap map = new HashMap();
	       	map.put("in", mudeTFascicoloIdsListRuoli);
			filterByRole = MudeTFascicoloSpecification.findById(map );
    	}
    	
    	Specification<MudeTFascicolo> filters = MudeTFascicoloSpecification.findBy(mudeTUser, comuni, idDug, duf, idIntestatarioPf, idIntestatarioPg, idPm, mudeDTipoIntervento, timestampCreazioneDa, timestampCreazioneA, codiceFascicolo, state, fascicoli, scope);
    	
    	Specifications<MudeTFascicolo> allSpecs = Specifications.where(filters).and(filterByRole);
    	Page<MudeTFascicolo> mudeTFascicoloList = mudeTFascicoloRepository.findAll(allSpecs, pageble);
    	List<FascicoloVO> fascicoloList = fascicoloEntityMapper.mapListEntityToListVO(mudeTFascicoloList.getContent(), mudeTUser);
    	
        if(page == 0 && "frontoffice".equals(scope) && fascicoloList.size() > 0) {
        	// calculate all records
        	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MudeTFascicolo> root = query.from(MudeTFascicolo.class);

            CriteriaQuery<Long> cq = query.select(root.get("id")).where(allSpecs.toPredicate(root, query, cb));
            List<Long> idFascoliList = entityManager.createQuery(cq).getResultList(); // resultList has Entity that only contains id

            fascicoloList.get(0).setStatoCounters(mudeTFascicoloRepository.getAllStateCunters(idFascoliList).toString());
        }

        return PaginationResponseUtil.buildResponse(fascicoloList, page, size, mudeTFascicoloList.getTotalPages(), mudeTFascicoloList.getTotalElements());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminaFascicolo(Long idFascicolo, MudeTUser mudeTUser) {

        //		fascicolo.setCategoriaIIntervento(request.getTipologiaIstanza());
        MudeTFascicolo mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);

        CsiLogAudit.TraceOperation operation = CsiLogAudit.TraceOperation.ELIMINA_FASCICOLO;
        if (mudeTFascicolo != null) {
            if(mudeTIstanzaRepository.findByMudeTFascicolo(mudeTFascicolo).size() > 0)
                throw new BusinessException("Il fascicolo ha istanze collegate, impossibile eliminare!");

            mudeRFascicoloStatoRepository.deleteStatiByFascicolo(mudeTFascicolo.getId());
            mudeRFascicoloIntestatarioRepository.deleteByFascicolo(mudeTFascicolo.getId());
            mudeRFascicoloUtenteRepository.deleteByFascicolo(mudeTFascicolo.getId());
            mudeRFascicoloIndirizzoRepository.deleteIndirizziByFascicolo(mudeTFascicolo.getId());

            mudeTFascicoloRepository.deleteFascicolo(mudeTFascicolo.getId());
        } else {
            // lancio eccezione per fascicolo inesistente
            throw new BusinessException("Fascicolo non trovato, impossibile eliminare!");
        }
        utilsTraceCsiLogAuditService.traceCsiLogAudit(operation.getOperation(), mudeTFascicolo.getTableName(), "id=" + mudeTFascicolo.getId() + ", condiceFascicolo" + mudeTFascicolo.getCodiceFascicolo(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<SelectVO> recuperaTipiIstanzaFascicolo(Long idFascicolo, Long idIstanza, MudeTUser mudeTUser) {
        List<SelectVO> tipoIstanzaSelect = new ArrayList<SelectVO>();
        if (idIstanza != null) { // restituisco tutte le istanze figlie che e' possibile legare al master
            List<MudeRTipoIstanza> tipoIstanzaList = mudeRTipoIstanzaRepository.findByMudeDTipoIstanzaPadre(mudeTIstanzaRepository.findOne(idIstanza).getTipoIstanza());

            for (MudeRTipoIstanza tipoIstanza : tipoIstanzaList)
                tipoIstanzaSelect.add(new SelectVO(tipoIstanza.getMudeDTipoIstanzaFiglia().getCodice(), tipoIstanza.getMudeDTipoIstanzaFiglia().getDescrizione()));
        } else if (idFascicolo != null) {
            List<MudeDTipoIstanza> list = mudeDTipoIstanzaRepository.findAll();
            for (MudeDTipoIstanza tipoIstanza : list)
                tipoIstanzaSelect.add(new SelectVO(tipoIstanza.getCodice(), tipoIstanza.getDescrizione()));
        }

        return tipoIstanzaSelect;
    }

    @Override
    public void updateUuidIndex(FascicoloVO fascicoloVO, MudeTUser mudeTUser) {
        MudeTFascicolo fascicolo = mudeTFascicoloRepository.findOne(fascicoloVO.getIdFascicolo());
        fascicolo.setUuidIndex(fascicoloVO.getUuidIndex());
        mudeTFascicoloRepository.saveDAO(fascicolo);
    }

    @Override
    public void updateFolderAddressFromIstanza(MudeTIstanza istanza, boolean forceOverride) {
    	//int howManyInstancesInFolder = mudeTIstanzaRepository.countIstanzeByFascicolo(istanza.getMudeTFascicolo().getId());
    	int howManyPublishedInstances = istanzaStatoService.isThereInstanceStateInFolder(istanza.getMudeTFascicolo().getId(),
				new String[] { StatoIstanza.DEPOSITATA.getValue(), 
								StatoIstanza.PRESA_IN_CARICO.getValue(),
								StatoIstanza.REGISTRATA_DA_PA.getValue()}, null);
    	// unless requested, do not change folder address if there is at least one published instance in folder
        if(!forceOverride && howManyPublishedInstances > 0)
        	return;

        // update folder address
        MudeRFascicoloIndirizzo mudeRFascicoloIndirizzo = mudeRFascicoloIndirizzoRepository.findByMudeTFascicolo_IdAndDataFineIsNull(istanza.getMudeTFascicolo().getId());
        MudeTIndirizzo instAddress = istanza.getIndirizzo();
        if(instAddress == null || istanza.getIndirizzo() == null)
        	return;

        MudeTIndirizzo folderAddress = mudeRFascicoloIndirizzo.getIndirizzo();

        if(howManyPublishedInstances > 0) {
            // dismiss old version
            mudeRFascicoloIndirizzo.setDataFine(Calendar.getInstance().getTime());
            mudeRFascicoloIndirizzoRepository.saveDAO(mudeRFascicoloIndirizzo);

            // create new version
            folderAddress = new MudeTIndirizzo();
            mudeRFascicoloIndirizzo = new MudeRFascicoloIndirizzo();
        }

        folderAddress.setCap(instAddress.getCap());
        folderAddress.setDenomComuneEstero(instAddress.getDenomComuneEstero());
        folderAddress.setIndirizzo(instAddress.getIndirizzo());
        folderAddress.setLocalita(instAddress.getLocalita());
        folderAddress.setIdDug(instAddress.getIdDug());
        folderAddress.setNumeroCivico(instAddress.getNumeroCivico());
        folderAddress.setIndirizzoEstero(instAddress.getIndirizzoEstero());
        folderAddress.setTipoIndirizzo(instAddress.getTipoIndirizzo());
        folderAddress.setMudeDComune(instAddress.getMudeDComune());
        folderAddress.setMudeDNazione(instAddress.getMudeDNazione());
        folderAddress.setMudeTContatto(instAddress.getMudeTContatto());
        folderAddress.setTelefono(instAddress.getTelefono());
        folderAddress.setCellulare(instAddress.getCellulare());
        folderAddress.setMail(instAddress.getMail());
        folderAddress.setPec(instAddress.getPec());

        mudeTIndirizzoRepository.saveDAO(folderAddress);

        // create new version
        mudeRFascicoloIndirizzo.setMudeTFascicolo(istanza.getMudeTFascicolo());
        mudeRFascicoloIndirizzo.setIndirizzo(folderAddress);
        mudeRFascicoloIndirizzo.setIdIstanza(istanza.getId());

        mudeRFascicoloIndirizzo.setDataInizio(new Date());
        mudeRFascicoloIndirizzoRepository.saveDAO(mudeRFascicoloIndirizzo);
    }

    @Override
	public byte[] exportExcelFascicoliUtente(MudeTUser mudeTUser, String codiceTipoIntervento, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idComune, Long idProvincia, Long idDug, String duf, LocalDate dataCreazioneDa, LocalDate dataCreazioneA, String codiceFascicolo, String state, String scope) {

        List<FascicoloVO> fascicoloVOListExport = new ArrayList<FascicoloVO>();
        byte[] fileContent = null;

        // cerco i fascicoli creati dall'utente

        List<MudeTContatto> contatti = mudeTContattoRepository.findByCfAndMudeTUser(mudeTUser.getCf(), mudeTUser);
        Optional<MudeTContatto> contattoOPT = contatti.stream().max(Comparator.comparing(MudeTContatto::getId));
        Set<Long> fascicoli = null;
        if (contattoOPT.isPresent()) {
            MudeTContatto contatto = contattoOPT.get();
            List<MudeRIstanzaSoggetto> relazioni = mudeRIstanzaSoggettoRepository.findAllByMudeTContattoId(contatto.getId());
            fascicoli = relazioni.stream().map(v -> v.getMudeTIstanza().getMudeTFascicolo().getId()).collect(Collectors.toSet());
        }

        List<MudeTIndirizzo> ubicazioni = null;
        List<Long> comuni = null;
        String mudeDTipoIntervento = null;
        LocalDate timestampCreazioneDa = null;
        LocalDate timestampCreazioneA = null;
        if (idComune != null && idComune == 0) {
            idComune = null;
        }
        if (idProvincia != null && idProvincia == 0) {
            idProvincia = null;
        }
        if (idDug != null && idDug == 0) {
            idDug = null;
        }
        if (StringUtils.isBlank(duf)) {
            duf = null;
        }

        if (idComune != null) {
            MudeDComune mudeDComune = mudeDComuneRepository.findByIdComune(idComune);
            comuni = new ArrayList<Long>();
            comuni.add(mudeDComune.getIdComune());
        } else if (idProvincia != null) {
            MudeDProvincia mudeDProvincia = mudeDProvinciaRepository.findOne(idProvincia);
            comuni = mudeDComuneRepository.findIdComuneByMudeDProvinciaOrderByDenomComuneAsc(mudeDProvincia);
        }

        if (StringUtils.isNotBlank(codiceTipoIntervento)) {
            mudeDTipoIntervento = mudeDTipoInterventoRepository.findOne(codiceTipoIntervento).getCodice();
        }

        Long longCreazioneDa=-1L;
        Long longCreazioneA=-1L;
        if (dataCreazioneDa != null) {
            timestampCreazioneDa = dataCreazioneDa;
            longCreazioneDa = timestampCreazioneDa.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC);
        }

        if (dataCreazioneA != null) {
            timestampCreazioneA = dataCreazioneA;
            longCreazioneA = timestampCreazioneA.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC);
        }

     // Fix RUOLI Utente BO sull'istanza
        int num_max_istances_role = Integer.parseInt(getValueFromMudeCProprieta("MUDE_NUM_MAX_ISTANCES_ROLE","20000"));
        List <Long> mudeTIstanzaIdsListRuoli = null;
     	 
     	 if(codiceFascicolo != null || idComune != null || state != null || longCreazioneDa != -1 || longCreazioneA != -1)
      		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdAllFascicoloByRuoli(
      				mudeTUser.getIdUser(), 
      				codiceFascicolo == null? "" : codiceFascicolo, 
      				idComune == null? -1 : idComune,
      				state == null? "" : state,
      				longCreazioneDa,
      				longCreazioneA
      				);          	
      	 else
      		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdByRuoli(mudeTUser.getIdUser());
	    //List <Long> mudeTIstanzaIdsListRuoli = new ArrayList<Long>();
	    Specification<MudeTFascicolo> filterByRole = null;
    	List <MudeTFascicolo> mudeTFascicoloList = new ArrayList<MudeTFascicolo>();
    	List <Long> mudeTFascicoloIdsListRuoli = new ArrayList<Long>();
	    if(BACKOFFICE.equals(scope) && (mudeTIstanzaIdsListRuoli == null || mudeTIstanzaIdsListRuoli.isEmpty())) {
    		return fileContent;
    	}else if(mudeTIstanzaIdsListRuoli.size()>num_max_istances_role) {
      		throw new BusinessException("Troppi elementi da visualizzare. Si consiglia di impostare uno o piu' filtri.");
      	}
    	if(BACKOFFICE.equals(scope)) {
	    	List <MudeTFascicolo> mudeTFascicoloListRuolo = mudeTFascicoloRepository.getFascicoliByRuoli(mudeTIstanzaIdsListRuoli);
	    	
	    	if(mudeTFascicoloListRuolo == null || mudeTFascicoloListRuolo.isEmpty()) {
	    		return fileContent;
	    	}
	    	
	    	for(MudeTFascicolo mudeTFascicolo : mudeTFascicoloListRuolo) {
	    		mudeTFascicoloIdsListRuoli.add(mudeTFascicolo.getId());
	     	}
	    	filterByRole = MudeTFascicoloSpecification.findByRole(mudeTUser,mudeTFascicoloIdsListRuoli);
    	}
    	
    	Specification<MudeTFascicolo> filters = MudeTFascicoloSpecification.findBy(mudeTUser, comuni, idDug, duf, idIntestatarioPf, idIntestatarioPg, idPm, mudeDTipoIntervento, timestampCreazioneDa, timestampCreazioneA, codiceFascicolo, state, fascicoli, scope);
    	
    	if(BACKOFFICE.equals(scope)){
    		mudeTFascicoloList = mudeTFascicoloRepository.findAll(Specifications.where(filters).and(filterByRole));
    	}else {
    		mudeTFascicoloList = mudeTFascicoloRepository.findAll(Specifications.where(filters));
    	}

    	fascicoloVOListExport = fascicoloEntityMapper.mapListEntityToListVO(mudeTFascicoloList, mudeTUser);

        FascicoloExcelBuilder fascicoloExcelBuilder = new FascicoloExcelBuilder();
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            fileContent = fascicoloExcelBuilder.buildExcelDocuments(fascicoloVOListExport, workbook);
        } catch (Exception e) {
        	logger.error("exportExcelFascicoliUtente exception", e);
        }
        return fileContent;
    }

	private String getValueFromMudeCProprieta(String name, String defaultValue) {
		String value = mudeCProprietaRepository.getValueByName(name, defaultValue);
		if (value!=null) {
			return value;
		} 
		return "";
	}

}