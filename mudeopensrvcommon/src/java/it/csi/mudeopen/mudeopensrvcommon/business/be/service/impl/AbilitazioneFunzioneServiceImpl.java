/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRAbilitazioneFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAbilitazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRAbilitazioneFunzioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneFunzioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDAbilitazioneSpecification;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneSlimCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.FunzioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
@Service
public class AbilitazioneFunzioneServiceImpl implements AbilitazioneFunzioneService {
    @Autowired
    private MudeRAbilitazioneFunzioneRepository mudeRAbilitazioneFunzioneRepository;

    @Autowired
    private MudeDAbilitazioneRepository mudeDAbilitazioneRepository;

//    @Autowired
//    private AbilitazioneFunzioneEntityMapper abilitazioneFunzioneEntityMapper;
    @Autowired
    private AbilitazioneEntityMapper abilitazioneEntityMapper;

    @Autowired
    private FunzioneEntityMapper funzioneEntityMapper;

    @Autowired
    private QuadroEntityMapper quadroEntityMapper;

    @Autowired
    private MudeRIstanzaUtenteRepository mudeRIstanzaUtenteRepository;

    @Autowired
    private MudeRFascicoloUtenteRepository mudeRFascicoloUtenteRepository;

    @Autowired
    private MudeRIstanzaUtenteQuadroRepository mudeRIstanzaUtenteQuadroRepository;

    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

    @Override
    public AbilitazioneFunzioneCustomVO loadByCodiceAbilitazione(String codiceAbilitazione) {
        AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO = new AbilitazioneFunzioneCustomVO();
        MudeDAbilitazione abilitazioneEntity = mudeDAbilitazioneRepository.findMudeDAbilitazioneByCodiceEquals(codiceAbilitazione);
        AbilitazioneVO abilitazioneVO = abilitazioneEntityMapper.mapEntityToVO(abilitazioneEntity);
        abilitazioneFunzioneCustomVO.setAbilitazione(abilitazioneVO);
        List<FunzioneVO> funzioni = getFunzioneListByAbilitazione(codiceAbilitazione);
        abilitazioneFunzioneCustomVO.setFunzioni(funzioni);
        return abilitazioneFunzioneCustomVO;
    }

    @Override
    public List<AbilitazioneFunzioneCustomVO> loadFunzioniAbilitazioni(Boolean isFascicolo, Boolean isPmRequired, Long idFascicolo, Long idIstanza, Boolean excludeFilters, Long idUser) {
        Specification<MudeDAbilitazione> isNotDeleted = MudeDAbilitazioneSpecification.isNotDeleted();
        Specification<MudeDAbilitazione> filters = null;
        boolean bExclFilters = (excludeFilters!=null && excludeFilters);
        if (isFascicolo) {
            Specification<MudeDAbilitazione> hasCodiceAbilitazioneEndsWith = MudeDAbilitazioneSpecification.hasCodiceAbilitazioneStartsWith("FASCIC_");
            filters = Specifications.where(isNotDeleted).and(hasCodiceAbilitazioneEndsWith);
        } else {
            Specification<MudeDAbilitazione> hasCodiceAbilitazione;
            Specification<MudeDAbilitazione> hasCodiceAbilitazioneEndsWithNotFascicolo = MudeDAbilitazioneSpecification.hasCodiceAbilitazioneNotStartsWith("FASCIC_");
        	if(!bExclFilters && (mudeRIstanzaStatoRepository.isInstanceInState(idIstanza, 
        													new String[] { 
        															StatoIstanza.DEPOSITATA.getValue(), 
        															StatoIstanza.PRESA_IN_CARICO.getValue(), 
        															StatoIstanza.REGISTRATA_DA_PA.getValue() }) > 0
        							|| mudeDTipoIstanzaRepository.recuperaTipologieIstanze(idIstanza).getSoggettiBloccati()))
            	hasCodiceAbilitazione = MudeDAbilitazioneSpecification.hasCodiceAbilitazioneStartsWith("CONSULTATORE");
        	else if(isPmRequired)
            	hasCodiceAbilitazione = MudeDAbilitazioneSpecification.hasCodiceAbilitazioneNotEndsWith("_PM_OPZ");
            else
            	hasCodiceAbilitazione = MudeDAbilitazioneSpecification.hasCodiceAbilitazioneNotEndsWith("_PM_OBB");
        	
        	filters = Specifications.where(isNotDeleted).and(hasCodiceAbilitazioneEndsWithNotFascicolo).and(hasCodiceAbilitazione);
        }

        List<MudeDAbilitazione> abilitazioniIstanza = mudeDAbilitazioneRepository.findAll(filters);
        List<MudeDAbilitazione> assignablePermissions = null;
        if(!bExclFilters)
        	assignablePermissions = mudeDAbilitazioneRepository.getPermissionsAssignableForUser(idUser, idFascicolo, idIstanza == null? -1L:idIstanza);
        List<AbilitazioneFunzioneCustomVO> list = Lists.newArrayList();
        for (MudeDAbilitazione mudeDAbilitazione : abilitazioniIstanza) {
        	// has the user a "funzione", among all assigned to him, that allow "mudeDAbilitazione" to be selected?
            if(assignablePermissions != null && !assignablePermissions.stream().anyMatch(x -> { return x.getCodice().equals(mudeDAbilitazione.getCodice());}))
            	continue;
            boolean add = true;
            if(!bExclFilters && mudeDAbilitazione.getTipo() == 'S')
            	add = (idIstanza != null && mudeRIstanzaUtenteRepository.findAllByIstanza_IdAndAbilitazione_IdAndDataFineIsNull(idIstanza, mudeDAbilitazione.getId()).size() == 0)
            			|| (isFascicolo && mudeRFascicoloUtenteRepository.findAllByFascicolo_IdAndAbilitazione_IdAndDataFineIsNull(idFascicolo, mudeDAbilitazione.getId()).size() == 0);
            if(add) {
                AbilitazioneFunzioneCustomVO vo = new AbilitazioneFunzioneCustomVO();
                vo.setAbilitazione(abilitazioneEntityMapper.mapEntityToVO(mudeDAbilitazione));
                vo.setFunzioni(getFunzioneListByAbilitazione(mudeDAbilitazione.getCodice()));
                list.add(vo);
            }
        }

        return list;
    }

    private List<FunzioneVO> getFunzioneListByAbilitazione(String codiceAbilitazione) {

        List<MudeRAbilitazioneFunzione> mudeRAbilitazioneFunzioneList = mudeRAbilitazioneFunzioneRepository.findAllByAbilitazione_CodiceAndDataFineIsNull(codiceAbilitazione);
        List<FunzioneVO> list = Lists.newArrayList();
        for (MudeRAbilitazioneFunzione mudeRAbilitazioneFunzione : mudeRAbilitazioneFunzioneList) {
            list.add(funzioneEntityMapper.mapEntityToVO(mudeRAbilitazioneFunzione.getFunzione()));
        }

        return list;
    }

    @Override
    public List<AbilitazioneFunzioneSlimCustomVO> loadFunzioniAbilitazioniByIdIstanzaAndIdUser(Long idIstanza, Long IdUser) {
    	List<AbilitazioneFunzioneSlimCustomVO> res = Lists.newArrayList();
    	
        for(String abilitazione : mudeDAbilitazioneRepository.getListaAbilitazioniByIstanzaAndUtente(idIstanza, IdUser)) {
        	AbilitazioneFunzioneSlimCustomVO item = new AbilitazioneFunzioneSlimCustomVO();
        	
        	item.setAbilitazione(abilitazione);
        	item.setFunzioni(mudeRAbilitazioneFunzioneRepository.findAllByCodice(abilitazione));
        	item.setQuadri(mudeRIstanzaUtenteQuadroRepository.findAllByMudeRIstanzaAndUtente(idIstanza, IdUser));
        	
        	res.add(item);
        }

        return res;
    }
}