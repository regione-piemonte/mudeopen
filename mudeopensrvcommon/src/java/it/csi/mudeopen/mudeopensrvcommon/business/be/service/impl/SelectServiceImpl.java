/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDOrdineRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQualificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRegioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDittaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.SelectService;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {
    @Autowired
    private MudeDQualificaRepository mudeDQualificaRepository;

    @Autowired
    private MudeDOrdineRepository mudeDOrdineRepository;

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

    @Override
    public List<SelectVO> searchByDescrizione(String descrizione, String selectType, String... params) {
        List<SelectVO> voList = this.getSelectVOList(descrizione, selectType, params);
        return voList;
    }

    @Override
    public List<SelectVO> loadAllSelectValues(String selectType, String... params) {
        List<SelectVO> voList = this.getSelectVOList(null, selectType, params);
        return voList;
    }

    private List<SelectVO> getSelectVOList(String descrizione, String selectType, String... params) {
        List<SelectVO> list = new ArrayList<>();
//        switch (selectType) {
//            case "qualifica":
//                Specification<MudeDQualifica> denominazioneLikeQualifica = MudeDQualificaSpecification.hasDenominazioneLike(descrizione);
//                Specification<MudeDQualifica> filtersQualifica = Specifications.where(denominazioneLikeQualifica);
//
//                List<MudeDQualifica> listQ = mudeDQualificaRepository.findAll(filtersQualifica);
//                for (MudeDQualifica qualifica : listQ) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(qualifica.getIdQualifica());
//                    vo.setDescrizione(qualifica.getDenominazione());
//                    list.add(vo);
//                }
//                break;
//            case "ordine":
//                Specification<MudeDOrdine> notDeletedOrdine = MudeDOrdineSpecification.isNotDeleted();
//                Specification<MudeDOrdine> descrizioneLikeOrdine = MudeDOrdineSpecification.hasDescrizioneLike(descrizione);
//                Specification<MudeDOrdine> descrizioneEstesaLikeOrdine = MudeDOrdineSpecification.hasDescrizioneEstesaLike(descrizione);
//                Specification<MudeDOrdine> descrizioneOROrdine = Specifications.where(descrizioneLikeOrdine).or(descrizioneEstesaLikeOrdine);
//                Specification<MudeDOrdine> filtersOrdine = Specifications.where(notDeletedOrdine).and(descrizioneOROrdine);
//
//                List<MudeDOrdine> listO = mudeDOrdineRepository.findAll(filtersOrdine);
//                for (MudeDOrdine ordine : listO) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(ordine.getCodice());
//                    vo.setDescrizione(ordine.getDescrizione());
//                    list.add(vo);
//                }
//                break;
//            case "tipo_ditta":
//                Specification<MudeDTipoDitta> denominazioneLikeTipoDitta = MudeDTipoDittaSpecification.hasDenominazioneLike(descrizione);
//                Specification<MudeDTipoDitta> filtersTipoDitta = Specifications.where(denominazioneLikeTipoDitta);
//
//                List<MudeDTipoDitta> listTD = mudeDTipoDittaRepository.findAll(filtersTipoDitta);
//                for (MudeDTipoDitta tipoDitta : listTD) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(tipoDitta.getIdTipoDitta());
//                    vo.setDescrizione(tipoDitta.getDenominazione());
//                    list.add(vo);
//                }
//                break;
//            case "tipo_istanza":
//                Specification<MudeDTipoIstanza> notDeletedTipoIstanza = MudeDTipoIstanzaSpecification.isNotDeleted();
//                Specification<MudeDTipoIstanza> descrizioneLikeTipoIstanza = MudeDTipoIstanzaSpecification.hasDescrizioneLike(descrizione);
//                Specification<MudeDTipoIstanza> descrizioneEstesaLikeTipoIstanza = MudeDTipoIstanzaSpecification.hasDescrizioneEstesaLike(descrizione);
//                Specification<MudeDTipoIstanza> descrizioneORTipoIstanza = Specifications.where(descrizioneLikeTipoIstanza).or(descrizioneEstesaLikeTipoIstanza);
//                Specification<MudeDTipoIstanza> filtersTipoIstanza = Specifications.where(notDeletedTipoIstanza).and(descrizioneORTipoIstanza);
//
//                List<MudeDTipoIstanza> listTIS = mudeDTipoIstanzaRepository.findAll(filtersTipoIstanza);
//                for (MudeDTipoIstanza tipoIstanza : listTIS) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(tipoIstanza.getCodice());
//                    vo.setDescrizione(tipoIstanza.getDescrizione());
//                    list.add(vo);
//                }
//                break;
//            case "tipo_intervento":
//                Specification<MudeDTipoIntervento> notDeletedTipoIntervento = MudeDTipoInterventoSpecification.isNotDeleted();
//                Specification<MudeDTipoIntervento> descrizioneLikeTipoIntervento = MudeDTipoInterventoSpecification.hasDescrizioneLike(descrizione);
//                Specification<MudeDTipoIntervento> descrizioneEstesaLikeTipoIntervento = MudeDTipoInterventoSpecification.hasDescrizioneEstesaLike(descrizione);
//                Specification<MudeDTipoIntervento> descrizioneORTipoIntervento = Specifications.where(descrizioneLikeTipoIntervento).or(descrizioneEstesaLikeTipoIntervento);
//                Specification<MudeDTipoIntervento> filtersTipoIntervento = Specifications.where(notDeletedTipoIntervento).and(descrizioneORTipoIntervento);
//
//                List<MudeDTipoIntervento> listTI = mudeDTipoInterventoRepository.findAll(filtersTipoIntervento);
//                for (MudeDTipoIntervento tipoIntervento : listTI) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(tipoIntervento.getCodice());
//                    vo.setDescrizione(tipoIntervento.getDescrizione());
//                    list.add(vo);
//                }
//                break;
//            case "dug":
//                Specification<MudeDDug> denominazioneLikeDug = MudeDDugSpecification.hasDenominazioneLike(descrizione);
//                Specification<MudeDDug> filtersDug = Specifications.where(denominazioneLikeDug);
//
//                List<MudeDDug> listD = mudeDDugRepository.findAll(filtersDug);
//                for (MudeDDug dug : listD) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(dug.getIdDug());
//                    vo.setDescrizione(dug.getDenominazione());
//                    list.add(vo);
//                }
//                break;
//            case "regione":
//                Specification<MudeDRegione> notDeletedRegione = MudeDRegioneSpecification.isNotDeleted();
//                Specification<MudeDRegione> denominazioneLikeRegione = MudeDRegioneSpecification.hasDenominazioneLike(descrizione);
//                Specification<MudeDRegione> filtersRegione = Specifications.where(notDeletedRegione).and(denominazioneLikeRegione);
//
//                List<MudeDRegione> ListR = mudeDRegioneRepository.findAll(filtersRegione);
//                for (MudeDRegione regione : ListR) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(regione.getIdRegione());
//                    vo.setDescrizione(regione.getDenomRegione());
//                    list.add(vo);
//                }
//                break;
//            case "provincia":
//                Specification<MudeDProvincia> notDeletedProvincia = MudeDProvinciaSpecification.isNotDeleted();
//                Specification<MudeDProvincia> denominazioneLikeProvincia = MudeDProvinciaSpecification.hasDenominazioneLike(descrizione);
//                Specification<MudeDProvincia> regioneEqualProvincia = MudeDProvinciaSpecification.hasRegioneEquals(Long.parseLong(params[0]));
//                Specification<MudeDProvincia> filtersProvincia = Specifications.where(notDeletedProvincia).and(regioneEqualProvincia).and(denominazioneLikeProvincia);
//
//                List<MudeDProvincia> ListP = mudeDProvinciaRepository.findAll(filtersProvincia);
//                for (MudeDProvincia prov : ListP) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(prov.getIdProvincia());
//                    vo.setDescrizione(prov.getDenomProvincia());
//                    list.add(vo);
//                }
//                break;
//            case "comune":
//                Specification<MudeDComune> notDeletedComune = MudeDComuneSpecification.isNotDeleted();
//                Specification<MudeDComune> denominazioneLikeComune = MudeDComuneSpecification.hasDenominazioneLike(descrizione);
//                Specification<MudeDComune> provinciaEqualComune = MudeDComuneSpecification.hasProvinciaEquals(Long.parseLong(params[0]));
//                Specification<MudeDComune> filtersComune = Specifications.where(notDeletedComune).and(provinciaEqualComune).and(denominazioneLikeComune);
//
//                List<MudeDComune> ListC = mudeDComuneRepository.findAll(filtersComune);
//                for (MudeDComune comune : ListC) {
//                    SelectVO vo = new SelectVO();
//                    vo.setId(comune.getIdComune());
//                    vo.setDescrizione(comune.getDenomComune());
//                    list.add(vo);
//                }
//                break;
//        }
        return list;
    }

}