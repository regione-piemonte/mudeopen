/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDestinazioneUso;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOrdine;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualificaCollegio;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDeroga;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDitta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoInterventoPaesaggistica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipologiaOperePrecarie;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipologiaTipoInterventoPaesaggistica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DestinazioneUsoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.OrdineEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QualificaCollegioEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RuoloSoggettoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RuoloUtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.StatoFascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.StatoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoDerogaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoEnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoInterventoPaesaggisticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipologiaOperePrecarieEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipologiaTipoInterventoPaesaggisticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TitoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDestinazioneUsoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDOrdineRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQualificaCollegioRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQualificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRegioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDerogaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDittaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoPaesaggisticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoOperaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipologiaOperePrecarieRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipologiaTipoInterventoPaesaggisticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTitoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.DictionaryService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ElementoEslencoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.SpeciePraticaTipoInterventoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.SpeciePraticaTipoOperaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaTipoInterventoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaTipoOperaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDComuneSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDDestinazioneUsoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDDugSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDOrdineSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDProvinciaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDQualificaCollegioSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDQualificaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDRegioneSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDRuoloSoggettoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDRuoloUtenteSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDSpeciePraticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDStatoFascicoloSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDStatoIstanzaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoDerogaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoDittaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoEnteSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoInterventoPaesaggisticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoInterventoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoIstanzaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoOperaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipologiaTipoInterventoPaesaggisticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTitoloSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoInterventoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoOperaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaTipoOperaVO;

/**
 * The type Dictionary service.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private MudeDTipoEnteRepository mudeDTipoEnteRepository;

    @Autowired
    private TipoEnteEntityMapper tipoEnteEntityMapper;

    @Autowired
    private MudeDRuoloUtenteRepository mudeDRuoloUtenteRepository;

    @Autowired
    private RuoloUtenteEntityMapper ruoloUtenteEntityMapper;

    @Autowired
    private MudeDSpeciePraticaRepository mudeDSpeciePraticaRepository;

    @Autowired
    private SpeciePraticaEntityMapper speciePraticaEntityMapper;

    @Autowired
    private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;

    @Autowired
    private StatoIstanzaEntityMapper statoIstanzaEntityMapper;

    @Autowired
    private MudeDTipoDerogaRepository mudeDTipoDerogaRepository;

    @Autowired
    private TipoDerogaEntityMapper tipoDerogaEntityMapper;

    @Autowired
    private MudeDDestinazioneUsoRepository mudeDDestinazioneUsoRepository;

    @Autowired
    private DestinazioneUsoEntityMapper destinazioneUsoEntityMapper;

    @Autowired
    private MudeDTipoInterventoPaesaggisticaRepository mudeDTipoInterventoPaesaggisticaRepository;

    @Autowired
    private TipoInterventoPaesaggisticaEntityMapper tipoInterventoPaesaggisticaEntityMapper;

    @Autowired
    private MudeDTipoOperaRepository mudeDTipoOperaRepository;

    @Autowired
    private TipoOperaEntityMapper tipoOperaEntityMapper;

    @Autowired
    private MudeDTipologiaTipoInterventoPaesaggisticaRepository mudeDTipologiaTipoInterventoPaesaggisticaRepository;

    @Autowired
    private TipologiaTipoInterventoPaesaggisticaEntityMapper tipologiaTipoInterventoPaesaggisticaEntityMapper;

    @Autowired
    private MudeDOrdineRepository mudeDOrdineRepository;

    @Autowired
    private MudeDQualificaCollegioRepository mudeDQualificaCollegioRepository;

    @Autowired
    private OrdineEntityMapper ordineEntityMapper;

    @Autowired
    private QualificaCollegioEntityMapper qualificaCollegioEntityMapper;

    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;

    @Autowired
    private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;

    @Autowired
    private MudeDTitoloRepository mudeDTitoloRepository;

    @Autowired
    private TitoloEntityMapper titoloEntityMapper;

    @Autowired
    private MudeDStatoFascicoloRepository mudeDStatoFascicoloRepository;

    @Autowired
    private StatoFascicoloEntityMapper statoFascicoloEntityMapper;

    @Autowired
    private MudeDQualificaRepository mudeDQualificaRepository;

    @Autowired
    private MudeDTipoDittaRepository mudeDTipoDittaRepository;

    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

    @Autowired
    private MudeDTipoInterventoRepository mudeDTipoInterventoRepository;

    @Autowired
    private MudeDDugRepository mudeDDugRepository;

    @Autowired
    private MudeDRegioneRepository mudeDRegioneRepository;

    @Autowired
    private MudeDProvinciaRepository mudeDProvinciaRepository;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private TipoInterventoEntityMapper tipoInterventoEntityMapper;

    @Autowired
    private SpeciePraticaTipoOperaService speciePraticaTipoOperaService;

    @Autowired
    private SpeciePraticaTipoInterventoService speciePraticaTipoInterventoService;

    @Autowired
    private TipoIstanzaTipoOperaService tipoIstanzaTipoOperaService;

    @Autowired
    private TipoIstanzaTipoInterventoService tipoIstanzaTipoInterventoService;

    @Autowired
    private ElementoEslencoService elementoElenecoService;

    @Autowired
    private MudeDTipologiaOperePrecarieRepository mudeDTipologiaOperePrecarieRepository;

    @Autowired
    private TipologiaOperePrecarieEntityMapper tipologiaOperePrecarieEntityMapper;
    
    @Autowired
    private EntityManager em;

    @Override
    public List<DizionarioVO> search(String filter, String dictType, MudeTUser mudeTUser) {
        List<DizionarioVO> voList = getDictionaryVOList(filter, dictType);
        return voList;
    }

    private List<DizionarioVO> getDictionaryVOList(String filter, String dictType) {
        List<DizionarioVO> voList = new ArrayList<>();
        Map codice = null;
        Map descrizione = null;
        Map descrizioneEstesa = null;
        String sortColumns = null;
        try {
            codice = FilterUtil.getValue(filter, "codice");
            descrizione = FilterUtil.getValue(filter, "descrizione");
            descrizioneEstesa = FilterUtil.getValue(filter, "descrizione");
            sortColumns = FilterUtil.getStringValue(filter, "_sort");
		} catch (Exception justInCaseIsRequired) { }

        Sort sort = new Sort(StringUtils.isNotBlank(sortColumns) && sortColumns.charAt(0) == '-'? Sort.Direction.DESC : Sort.Direction.ASC, 
        			StringUtils.isNotBlank(sortColumns)? sortColumns.substring(1) : "descrizione");

        switch (dictType) {
            case "ruolo_utente":
                Specification<MudeDRuoloUtente> notDeletedRuoloUtente = MudeDRuoloUtenteSpecification.isNotDeleted();
                Specification<MudeDRuoloUtente> codiceLikeRuoloUtente = MudeDRuoloUtenteSpecification.hasCodice(codice);
                Specification<MudeDRuoloUtente> descrizioneLikeRuoloUtente = MudeDRuoloUtenteSpecification.hasDescrizione(descrizione);
                Specification<MudeDRuoloUtente> descrizioneEstesaLikeRuoloUtente = MudeDRuoloUtenteSpecification.hasDescrizioneEstesa(descrizioneEstesa);
                
                Specification<MudeDRuoloUtente> descrizioneORRuoloUtente = Specifications.where(descrizioneLikeRuoloUtente).or(descrizioneEstesaLikeRuoloUtente);

                Specification<MudeDRuoloUtente> filtersRuoloUtente = Specifications.where(notDeletedRuoloUtente).and(codiceLikeRuoloUtente).and(descrizioneORRuoloUtente);

                List<MudeDRuoloUtente> listRU = mudeDRuoloUtenteRepository.findAll(filtersRuoloUtente, sort);
                voList = ruoloUtenteEntityMapper.mapListEntityToListVO(listRU);
                break;
            case "specie_pratica":
                Specification<MudeDSpeciePratica> notDeletedSpeciePratica = MudeDSpeciePraticaSpecification.isNotDeleted();
                Specification<MudeDSpeciePratica> codiceLikeSpeciePratica = MudeDSpeciePraticaSpecification.hasCodice(codice);
                Specification<MudeDSpeciePratica> descrizioneLikeSpeciePratica = MudeDSpeciePraticaSpecification.hasDescrizione(descrizione);
                Specification<MudeDSpeciePratica> descrizioneEstesaLikeSpeciePratica = MudeDSpeciePraticaSpecification.hasDescrizioneEstesa(descrizioneEstesa);
                Specification<MudeDSpeciePratica> visibilitaRuoloUtente = MudeDSpeciePraticaSpecification.hasVisibilita(FilterUtil.getValue(filter, "visibilita"));

                Specification<MudeDSpeciePratica> descrizioneORSpeciePratica = Specifications.where(descrizioneLikeSpeciePratica).or(descrizioneEstesaLikeSpeciePratica);

                Specification<MudeDSpeciePratica> filtersSpeciePratica = Specifications.where(notDeletedSpeciePratica)
                																			.and(codiceLikeSpeciePratica)
                																			.and(descrizioneORSpeciePratica)
                																			.and(visibilitaRuoloUtente);

                List<MudeDSpeciePratica> listSP = mudeDSpeciePraticaRepository.findAll(filtersSpeciePratica, sort);
                voList = speciePraticaEntityMapper.mapListEntityToListVO(listSP);
                break;
            case "stato_fascicolo":
                Specification<MudeDStatoFascicolo> notDeletedStatoFascicolo = MudeDStatoFascicoloSpecification.isNotDeleted();
                Specification<MudeDStatoFascicolo> codiceLikeStatoFascicolo = MudeDStatoFascicoloSpecification.hasCodice(codice);
                Specification<MudeDStatoFascicolo> descrizioneLikeStatoFascicolo = MudeDStatoFascicoloSpecification.hasDescrizione(descrizione);
                Specification<MudeDStatoFascicolo> descrizioneEstesaLikeStatoFascicolo = MudeDStatoFascicoloSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDStatoFascicolo> descrizioneORStatoFascicolo = Specifications.where(descrizioneLikeStatoFascicolo).or(descrizioneEstesaLikeStatoFascicolo);

                Specification<MudeDStatoFascicolo> filtersStatoFascicolo = Specifications.where(notDeletedStatoFascicolo).and(codiceLikeStatoFascicolo).and(descrizioneORStatoFascicolo);

                List<MudeDStatoFascicolo> listSF = mudeDStatoFascicoloRepository.findAll(filtersStatoFascicolo, new Sort(Sort.Direction.ASC, "codice"));
                voList = statoFascicoloEntityMapper.mapListEntityToListVO(listSF);
                break;
            case "stato_istanza":
            {
                Specification<MudeDStatoIstanza> notDeletedStatoIstanza = MudeDStatoIstanzaSpecification.isNotDeleted();
                Specification<MudeDStatoIstanza> codiceLikeStatoIstanza = MudeDStatoIstanzaSpecification.hasCodice(codice);
                Specification<MudeDStatoIstanza> descrizioneLikeStatoIstanza = MudeDStatoIstanzaSpecification.hasDescrizione(descrizione);
                Specification<MudeDStatoIstanza> descrizioneEstesaLikeStatoIstanza = MudeDStatoIstanzaSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDStatoIstanza> descrizioneORStatoIstanza = Specifications.where(descrizioneLikeStatoIstanza).or(descrizioneEstesaLikeStatoIstanza);

                Specification<MudeDStatoIstanza> filtersStatoIstanza = Specifications.where(notDeletedStatoIstanza).and(codiceLikeStatoIstanza).and(descrizioneORStatoIstanza);

                List<MudeDStatoIstanza> listSI = mudeDStatoIstanzaRepository.findAll(filtersStatoIstanza, sort);
                voList = statoIstanzaEntityMapper.mapListEntityToListVO(listSI);
                break;
            }
            case "tipo_deroga":
                Specification<MudeDTipoDeroga> notDeletedTipoDeroga = MudeDTipoDerogaSpecification.isNotDeleted();
                Specification<MudeDTipoDeroga> codiceLikeTipoDeroga = MudeDTipoDerogaSpecification.hasCodice(codice);
                Specification<MudeDTipoDeroga> descrizioneLikeTipoDeroga = MudeDTipoDerogaSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipoDeroga> descrizioneEstesaLikeTipoDeroga = MudeDTipoDerogaSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDTipoDeroga> descrizioneORTipoDeroga = Specifications.where(descrizioneLikeTipoDeroga).or(descrizioneEstesaLikeTipoDeroga);

                Specification<MudeDTipoDeroga> filtersTipoDeroga = Specifications.where(notDeletedTipoDeroga).and(codiceLikeTipoDeroga).and(descrizioneORTipoDeroga);

                List<MudeDTipoDeroga> listTD = mudeDTipoDerogaRepository.findAll(filtersTipoDeroga, sort);
                voList = tipoDerogaEntityMapper.mapListEntityToListVO(listTD);
                break;
            case "destinazione_d_uso":
                Specification<MudeDDestinazioneUso> notDeletedDestinazioneUso = MudeDDestinazioneUsoSpecification.isNotDeleted();
                Specification<MudeDDestinazioneUso> codiceLikeDestinazioneUso = MudeDDestinazioneUsoSpecification.hasCodice(codice);
                Specification<MudeDDestinazioneUso> descrizioneLikeDestinazioneUso = MudeDDestinazioneUsoSpecification.hasDescrizione(descrizione);
                Specification<MudeDDestinazioneUso> descrizioneEstesaLikeDestinazioneUso = MudeDDestinazioneUsoSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDDestinazioneUso> descrizioneORDestinazioneUso = Specifications.where(descrizioneLikeDestinazioneUso).or(descrizioneEstesaLikeDestinazioneUso);

                Specification<MudeDDestinazioneUso> filtersDestinazioneUso = Specifications.where(notDeletedDestinazioneUso).and(codiceLikeDestinazioneUso).and(descrizioneORDestinazioneUso);

                List<MudeDDestinazioneUso> listDU = mudeDDestinazioneUsoRepository.findAll(filtersDestinazioneUso, sort);
                voList = destinazioneUsoEntityMapper.mapListEntityToListVO(listDU);
                break;
            case "tipo_ente":
                Specification<MudeDTipoEnte> notDeletedTipoEnte = MudeDTipoEnteSpecification.isNotDeleted();
                Specification<MudeDTipoEnte> codiceLikeTipoEnte = MudeDTipoEnteSpecification.hasCodice(codice);
                Specification<MudeDTipoEnte> descrizioneLikeTipoEnte = MudeDTipoEnteSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipoEnte> descrizioneEstesaLikeTipoEnte = MudeDTipoEnteSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDTipoEnte> descrizioneORTipoEnte = Specifications.where(descrizioneLikeTipoEnte).or(descrizioneEstesaLikeTipoEnte);

                Specification<MudeDTipoEnte> filtersTipoEnte = Specifications.where(notDeletedTipoEnte).and(codiceLikeTipoEnte).and(descrizioneORTipoEnte);
                List<MudeDTipoEnte> listTE = mudeDTipoEnteRepository.findAll(filtersTipoEnte, sort);
                voList = tipoEnteEntityMapper.mapListEntityToListVO(listTE);
                break;
            case "tipo_intervento_paesaggistica":
                Map tipologia = FilterUtil.getValue(filter, "tipologia");
                Specification<MudeDTipoInterventoPaesaggistica> notDeletedTipoInterventoPaesaggistica = MudeDTipoInterventoPaesaggisticaSpecification.isNotDeleted();
                Specification<MudeDTipoInterventoPaesaggistica> codiceLikeTipoInterventoPaesaggistica = MudeDTipoInterventoPaesaggisticaSpecification.hasCodice(codice);
                Specification<MudeDTipoInterventoPaesaggistica> descrizioneLikeTipoInterventoPaesaggistica = MudeDTipoInterventoPaesaggisticaSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipoInterventoPaesaggistica> descrizioneEstesaLikeTipoInterventoPaesaggistica = MudeDTipoInterventoPaesaggisticaSpecification.hasDescrizioneEstesa(descrizioneEstesa);
                Specification<MudeDTipoInterventoPaesaggistica> tipologiaLikeTipoInterventoPaesaggistica = MudeDTipoInterventoPaesaggisticaSpecification.hasTipologiaTipoInterventoPaesaggistica(tipologia);

                Specification<MudeDTipoInterventoPaesaggistica> descrizioneORTipoInterventoPaesaggistica = Specifications.where(descrizioneLikeTipoInterventoPaesaggistica).or(descrizioneEstesaLikeTipoInterventoPaesaggistica);

                Specification<MudeDTipoInterventoPaesaggistica> filtersTipoInterventoPaesaggistica = Specifications.where(notDeletedTipoInterventoPaesaggistica).and(codiceLikeTipoInterventoPaesaggistica).and(descrizioneORTipoInterventoPaesaggistica).and(tipologiaLikeTipoInterventoPaesaggistica);

                List<MudeDTipoInterventoPaesaggistica> listTIP = mudeDTipoInterventoPaesaggisticaRepository.findAll(filtersTipoInterventoPaesaggistica, sort);
                voList = tipoInterventoPaesaggisticaEntityMapper.mapListEntityToListVO(listTIP);
                break;
            case "tipo_opera":
                Specification<MudeDTipoOpera> notDeletedTipoOpera = MudeDTipoOperaSpecification.isNotDeleted();
                Specification<MudeDTipoOpera> codiceLikeTipoOpera = MudeDTipoOperaSpecification.hasCodice(codice);
                Specification<MudeDTipoOpera> descrizioneLikeTipoOpera = MudeDTipoOperaSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipoOpera> descrizioneEstesaLikeTipoOpera = MudeDTipoOperaSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDTipoOpera> descrizioneORTipoOpera = Specifications.where(descrizioneLikeTipoOpera).or(descrizioneEstesaLikeTipoOpera);

                Specification<MudeDTipoOpera> filtersTipoOpera = Specifications.where(notDeletedTipoOpera).and(codiceLikeTipoOpera).and(descrizioneORTipoOpera);

                List<MudeDTipoOpera> listTO = mudeDTipoOperaRepository.findAll(filtersTipoOpera, sort);
                voList = tipoOperaEntityMapper.mapListEntityToListVO(listTO);
                break;
            case "tipologia_tipo_intervento_paesaggistica":
                Specification<MudeDTipologiaTipoInterventoPaesaggistica> notDeletedTipologiaTipoInterventoPaesaggistica = MudeDTipologiaTipoInterventoPaesaggisticaSpecification.isNotDeleted();
                Specification<MudeDTipologiaTipoInterventoPaesaggistica> codiceLikeTipologiaTipoInterventoPaesaggistica = MudeDTipologiaTipoInterventoPaesaggisticaSpecification.hasCodice(codice);
                Specification<MudeDTipologiaTipoInterventoPaesaggistica> descrizioneLikeTipologiaTipoInterventoPaesaggistica = MudeDTipologiaTipoInterventoPaesaggisticaSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipologiaTipoInterventoPaesaggistica> descrizioneEstesaLikeTipologiaTipoInterventoPaesaggistica = MudeDTipologiaTipoInterventoPaesaggisticaSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDTipologiaTipoInterventoPaesaggistica> descrizioneORTipologiaTipoInterventoPaesaggistica = Specifications.where(descrizioneLikeTipologiaTipoInterventoPaesaggistica).or(descrizioneEstesaLikeTipologiaTipoInterventoPaesaggistica);

                Specification<MudeDTipologiaTipoInterventoPaesaggistica> filtersTipologiaTipoInterventoPaesaggistica = Specifications.where(notDeletedTipologiaTipoInterventoPaesaggistica).and(codiceLikeTipologiaTipoInterventoPaesaggistica).and(descrizioneORTipologiaTipoInterventoPaesaggistica);

                List<MudeDTipologiaTipoInterventoPaesaggistica> listTTIP = mudeDTipologiaTipoInterventoPaesaggisticaRepository.findAll(filtersTipologiaTipoInterventoPaesaggistica, sort);
                voList = tipologiaTipoInterventoPaesaggisticaEntityMapper.mapListEntityToListVO(listTTIP);
                break;
            case "ordine":
                Specification<MudeDOrdine> notDeletedOrdine = MudeDOrdineSpecification.isNotDeleted();
                Specification<MudeDOrdine> codiceLikeOrdine = MudeDOrdineSpecification.hasCodice(codice);
                Specification<MudeDOrdine> descrizioneLikeOrdine = MudeDOrdineSpecification.hasDescrizione(descrizione);
                Specification<MudeDOrdine> descrizioneEstesaLikeOrdine = MudeDOrdineSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDOrdine> descrizioneOROrdine = Specifications.where(descrizioneLikeOrdine).or(descrizioneEstesaLikeOrdine);

                Specification<MudeDOrdine> filtersOrdine = Specifications.where(notDeletedOrdine).and(codiceLikeOrdine).and(descrizioneOROrdine);

                List<MudeDOrdine> listO = mudeDOrdineRepository.findAll(filtersOrdine, sort);
                voList = ordineEntityMapper.mapListEntityToListVO(listO);
                break;
            case "qualifica_collegio":
                Specification<MudeDQualificaCollegio> notDeletedQualificaCollegio = MudeDQualificaCollegioSpecification.isNotDeleted();
                Specification<MudeDQualificaCollegio> codiceLikeQualificaCollegio = MudeDQualificaCollegioSpecification.hasCodice(codice);
                Specification<MudeDQualificaCollegio> descrizioneLikeQualificaCollegio = MudeDQualificaCollegioSpecification.hasDescrizione(descrizione);
                Specification<MudeDQualificaCollegio> descrizioneEstesaLikeQualificaCollegio = MudeDQualificaCollegioSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDQualificaCollegio> descrizioneORQualificaCollegio = Specifications.where(descrizioneLikeQualificaCollegio).or(descrizioneEstesaLikeQualificaCollegio);
                Specification<MudeDQualificaCollegio> filtersQualificaCollegio = Specifications.where(notDeletedQualificaCollegio).and(codiceLikeQualificaCollegio).and(descrizioneORQualificaCollegio);

                voList = qualificaCollegioEntityMapper.mapListEntityToListVO(mudeDQualificaCollegioRepository.findAll(filtersQualificaCollegio, sort));
                break;
            case "ruolo_soggetto":
                Specification<MudeDRuoloSoggetto> notDeletedRuoloSoggetto = MudeDRuoloSoggettoSpecification.isNotDeleted();
                Specification<MudeDRuoloSoggetto> codiceLikeRuoloSoggetto = MudeDRuoloSoggettoSpecification.hasCodice(codice);
                Specification<MudeDRuoloSoggetto> descrizioneLikeRuoloSoggetto = MudeDRuoloSoggettoSpecification.hasDescrizione(descrizione);
                Specification<MudeDRuoloSoggetto> descrizioneEstesaLikeRuoloSoggetto = MudeDRuoloSoggettoSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDRuoloSoggetto> descrizioneORRuoloSoggetto = Specifications.where(descrizioneLikeRuoloSoggetto).or(descrizioneEstesaLikeRuoloSoggetto);

                Specification<MudeDRuoloSoggetto> filtersRuoloSoggetto = Specifications.where(notDeletedRuoloSoggetto).and(codiceLikeRuoloSoggetto).and(descrizioneORRuoloSoggetto);

                List<MudeDRuoloSoggetto> listRS = mudeDRuoloSoggettoRepository.findAll(filtersRuoloSoggetto, sort);
                voList = ruoloSoggettoEntityMapper.mapListEntityToListVO(listRS);
                break;
            case "titolo":
                Specification<MudeDTitolo> notDeletedTitolo = MudeDTitoloSpecification.isNotDeleted();
                Specification<MudeDTitolo> codiceLikeTitolo = MudeDTitoloSpecification.hasCodice(codice);
                Specification<MudeDTitolo> descrizioneLikeTitolo = MudeDTitoloSpecification.hasDescrizione(descrizione);
                Specification<MudeDTitolo> descrizioneEstesaLikeTitolo = MudeDTitoloSpecification.hasDescrizioneEstesa(descrizioneEstesa);

                Specification<MudeDTitolo> descrizioneORTitolo = Specifications.where(descrizioneLikeTitolo).or(descrizioneEstesaLikeTitolo);

                Specification<MudeDTitolo> filtersTitolo = Specifications.where(notDeletedTitolo).and(codiceLikeTitolo).and(descrizioneORTitolo);

                List<MudeDTitolo> listT = mudeDTitoloRepository.findAll(filtersTitolo, sort);
                voList = titoloEntityMapper.mapListEntityToListVO(listT);
                break;
            case "qualifica":
                Specification<MudeDQualifica> denominazioneLikeQualifica = MudeDQualificaSpecification.hasDenominazione(descrizione);
                Specification<MudeDQualifica> filtersQualifica = Specifications.where(denominazioneLikeQualifica);

                List<MudeDQualifica> listQ = mudeDQualificaRepository.findAll(filtersQualifica, new Sort(Sort.Direction.ASC, "denominazione"));
                for (MudeDQualifica qualifica : listQ) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(qualifica.getIdQualifica()));
                    vo.setDescrizione(qualifica.getDenominazione());
                    vo.setDescrizioneEstesa(qualifica.getDenominazione());
                    voList.add(vo);
                }
                break;
            case "tipo_ditta":
                Specification<MudeDTipoDitta> denominazioneLikeTipoDitta = MudeDTipoDittaSpecification.hasDenominazione(descrizione);
                Specification<MudeDTipoDitta> filtersTipoDitta = Specifications.where(denominazioneLikeTipoDitta);

                List<MudeDTipoDitta> listTDitta = mudeDTipoDittaRepository.findAll(filtersTipoDitta, new Sort(Sort.Direction.ASC, "denominazione"));
                for (MudeDTipoDitta tipoDitta : listTDitta) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(tipoDitta.getIdTipoDitta()));
                    vo.setDescrizione(tipoDitta.getDenominazione());
                    vo.setDescrizioneEstesa(tipoDitta.getDenominazione());
                    voList.add(vo);
                }
                break;
            case "tipo_istanza":
                Specification<MudeDTipoIstanza> notDeletedTipoIstanza = MudeDTipoIstanzaSpecification.isNotDeleted();
                Specification<MudeDTipoIstanza> descrizioneLikeTipoIstanza = MudeDTipoIstanzaSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipoIstanza> descrizioneEstesaLikeTipoIstanza = MudeDTipoIstanzaSpecification.hasDescrizioneEstesa(descrizione);
                Specification<MudeDTipoIstanza> descrizioneORTipoIstanza = Specifications.where(descrizioneLikeTipoIstanza).or(descrizioneEstesaLikeTipoIstanza);
                Specification<MudeDTipoIstanza> filtersTipoIstanza = Specifications.where(notDeletedTipoIstanza).and(descrizioneORTipoIstanza);

                List<MudeDTipoIstanza> listTIS = mudeDTipoIstanzaRepository.findAll(filtersTipoIstanza, sort);
                for (MudeDTipoIstanza tipoIstanza : listTIS) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(tipoIstanza.getCodice()));
                    vo.setDescrizione(tipoIstanza.getDescrizione());
                    vo.setDescrizioneEstesa(tipoIstanza.getDescrizioneEstesa());
                    voList.add(vo);
                }
                break;
            case "tipo_intervento":
                Specification<MudeDTipoIntervento> notDeletedTipoIntervento = MudeDTipoInterventoSpecification.isNotDeleted();
                Specification<MudeDTipoIntervento> descrizioneLikeTipoIntervento = MudeDTipoInterventoSpecification.hasDescrizione(descrizione);
                Specification<MudeDTipoIntervento> descrizioneEstesaLikeTipoIntervento = MudeDTipoInterventoSpecification.hasDescrizioneEstesa(descrizione);
                Specification<MudeDTipoIntervento> descrizioneORTipoIntervento = Specifications.where(descrizioneLikeTipoIntervento).or(descrizioneEstesaLikeTipoIntervento);
                Specification<MudeDTipoIntervento> filtersTipoIntervento = Specifications.where(notDeletedTipoIntervento).and(descrizioneORTipoIntervento);

                List<MudeDTipoIntervento> listTI = mudeDTipoInterventoRepository.findAll(filtersTipoIntervento, sort);
                voList = tipoInterventoEntityMapper.mapListEntityToListVO(listTI);
                break;
            case "dug":
                Specification<MudeDDug> denominazioneLikeDug = MudeDDugSpecification.hasDenominazione(descrizione);
                Specification<MudeDDug> filtersDug = Specifications.where(denominazioneLikeDug);

                List<MudeDDug> listD = mudeDDugRepository.findAll(filtersDug, new Sort(Sort.Direction.ASC, "denominazione"));
                for (MudeDDug dug : listD) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(dug.getIdDug()));
                    vo.setDescrizione(dug.getDenominazione());
                    vo.setDescrizioneEstesa(dug.getDenominazione());
                    voList.add(vo);
                }
                break;
            case "regione":
                Specification<MudeDRegione> notDeletedRegione = MudeDRegioneSpecification.isNotDeleted();
                Specification<MudeDRegione> denominazioneLikeRegione = MudeDRegioneSpecification.hasDenominazione(descrizione);
                Specification<MudeDRegione> filtersRegione = Specifications.where(notDeletedRegione).and(denominazioneLikeRegione);

                List<MudeDRegione> ListR = mudeDRegioneRepository.findAll(filtersRegione, new Sort(Sort.Direction.ASC, "denomRegione"));
                for (MudeDRegione regione : ListR) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(regione.getIdRegione()));
                    vo.setDescrizione(regione.getDenomRegione());
                    vo.setDescrizioneEstesa(regione.getDenomRegione());
                    voList.add(vo);
                }
                break;
            case "provincia":
                Specification<MudeDProvincia> notDeletedProvincia = MudeDProvinciaSpecification.isNotDeleted();
                Specification<MudeDProvincia> denominazioneLikeProvincia = MudeDProvinciaSpecification.hasDenominazione(descrizione);
                Specification<MudeDProvincia> regioneEqualProvincia = MudeDProvinciaSpecification.hasRegione(FilterUtil.getValue(filter, "idRegione"));
                Specification<MudeDProvincia> filtersProvincia = Specifications.where(notDeletedProvincia).and(regioneEqualProvincia).and(denominazioneLikeProvincia);

                List<MudeDProvincia> ListP = mudeDProvinciaRepository.findAll(filtersProvincia, new Sort(Sort.Direction.ASC, "denomProvincia"));
                for (MudeDProvincia prov : ListP) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(prov.getIdProvincia()));
                    vo.setDescrizione(prov.getDenomProvincia());
                    vo.setDescrizioneEstesa(prov.getDenomProvincia());
                    voList.add(vo);
                }
                break;
            case "comune":
                Specification<MudeDComune> notDeletedComune = MudeDComuneSpecification.isNotDeleted();
                Specification<MudeDComune> denominazioneLikeComune = MudeDComuneSpecification.hasDenominazione(descrizione);
                Specification<MudeDComune> provinciaEqualComune = MudeDComuneSpecification.hasProvincia(FilterUtil.getValue(filter, "idProvincia"));
                Specification<MudeDComune> filtersComune = Specifications.where(notDeletedComune).and(provinciaEqualComune).and(denominazioneLikeComune);

                List<MudeDComune> ListC = mudeDComuneRepository.findAll(filtersComune, new Sort(Sort.Direction.ASC, "denomComune"));
                for (MudeDComune comune : ListC) {
                    DizionarioVO vo = new DizionarioVO();
                    vo.setCodice(String.valueOf(comune.getIdComune()));
                    vo.setDescrizione(comune.getDenomComune());
                    vo.setDescrizioneEstesa(comune.getDenomComune());
                    voList.add(vo);
                }
                break;
            case "rischio_sismico":
                String descr_sismica = mudeDComuneRepository.getComuneRischioSismico(filter);
                if(descr_sismica != null)
                	voList.add(new DizionarioVO(descr_sismica.split("~")[0], descr_sismica.split("~")[1]));
                break;
            case "specie_pratica_interventi":
	            {
	                String codiceSpeciePratica = FilterUtil.getStringValue(filter,"codice");
	                List<SpeciePraticaTipoInterventoVO> interventi = speciePraticaTipoInterventoService.findBySpeciePratica(codiceSpeciePratica);
	                for (SpeciePraticaTipoInterventoVO item : interventi)
	                    voList.add(item.getTipoIntervento());
	            }                
                break;
            case "tipo_istanza_interventi":
                voList = tipoIstanzaTipoInterventoService.findByTipoIstanza(FilterUtil.getListValue(filter,"codiceTipoIstanza"));
	            break;
            case "elemento_elenco":
                voList = elementoElenecoService.findByTipoElenco(FilterUtil.getListValue(filter,"codiceTipoElenco"));
	            break;
            case "specie_pratica_opere":
                String codiceSpeciePratica = FilterUtil.getStringValue(filter,"codice");
                List<SpeciePraticaTipoOperaVO> opere = speciePraticaTipoOperaService.findBySpeciePratica(codiceSpeciePratica);
                for (SpeciePraticaTipoOperaVO item : opere) {
                    voList.add(item.getTipoOpera());
                }
                break;
            case "tipo_istanza_opere":
                String codiceTipoIstanza = FilterUtil.getStringValue(filter,"codice");
                List<TipoIstanzaTipoOperaVO> items = tipoIstanzaTipoOperaService.findByTipoIstanza(codiceTipoIstanza);
                for (TipoIstanzaTipoOperaVO item : items) {
                    voList.add(item.getTipoOpera());
                }
                break;
            case "opere_precarie":
                List<MudeDTipologiaOperePrecarie> operePrecarie = mudeDTipologiaOperePrecarieRepository.findAllByDataFineIsNull();
                voList = tipologiaOperePrecarieEntityMapper.mapListEntityToListVO(operePrecarie);
                break;
           default:
           {
        	   if(!dictType.startsWith("mudeopen_d_"))
        		   	throw new BusinessException("table '"+dictType+"' not allowed!");
        		   	
        	   String codiceField = FilterUtil.getStringValue(filter, "codiceField");
        	   String descrizioneField = FilterUtil.getStringValue(filter, "descrizioneField");
        	   String descrizioneEstesaField = FilterUtil.getStringValue(filter, "descrizioneEstesaField");
        	   String _where = FilterUtil.getStringValue(filter, "_where");

        	   voList = (List<DizionarioVO>)em.createNativeQuery(
              			"SELECT " + (codiceField == null? "codice" : codiceField) + " AS codice, " + (descrizioneField == null? "descrizione" : descrizioneField) + " AS descrizione, " + (descrizioneEstesaField == null? "descrizione_estesa" : descrizioneEstesaField) + " AS descrizione_estesa "
              			+ "  FROM " + dictType
              			+ "  WHERE data_fine IS NULL" 
              			+ (_where == null? "" : (" AND " + _where))).getResultList().stream().map(x -> { 
              		return new DizionarioVO((String)((Object[])x)[0], (String)((Object[])x)[1], (String)((Object[])x)[2] );
              	}).collect(Collectors.toList());
           }
        }
        return voList;
    }

}