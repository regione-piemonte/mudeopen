/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteEnteComuneRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.EnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProvinciaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteFunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRRuoloFunzioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteEnteComuneRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FunzioneUtenteVO;

@Component
public class PraticaEntityMapperImpl implements PraticaEntityMapper {
	
    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private ComuneEntityMapper comuneEntityMapper;

    @Autowired
    MudeRUtenteEnteComuneRuoloRepository mudeRUtenteEnteComuneRuoloRepository;

    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private UtenteFunzioneEntityMapper utenteFunzioneEntityMapper;

    @Autowired
    private MudeRRuoloFunzioneRepository mudeRUtenteFunzioneRepository;

    @Autowired
    private ProvinciaEntityMapper provinciaEntityMapper;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private EnteEntityMapper enteEntityMapper;

    @Override
    public PraticaVO mapEntityToVO(MudeTPratica dto, MudeTUser mudeTUser) {
    	PraticaVO praticaVO = new PraticaVO();

    	List<MudeTIstanza> istanzePratica = mudeTIstanzaRepository.getInstancesByPratica(dto.getId());
    	for(MudeTIstanza istanza : istanzePratica) {
	    	// Assumption: il comune della pratica è unico ed è quello di ciascuna istanza della pratica
	    	if (praticaVO.getComune() == null && istanza.getComune() != null) {
	            ComuneVO comune = comuneEntityMapper.mapEntityToVO(istanzePratica.get(0).getComune());
	            praticaVO.setComune(comune);
	            MudeDComune mudeDComune =  mudeDComuneRepository.findOne(Long.valueOf(comune.getId()));
	            ProvinciaVO provincia = provinciaEntityMapper.mapEntityToVO(mudeDComune.getMudeDProvincia());
	            praticaVO.setProvincia(provincia);
	    	}
	    	
	    	// Fix RUOLI Utente BO sull'istanza
	        if((praticaVO.getRuoloUtenteBoSuPratica() == null || (TipoRuoloUtente.CONSULTATORE.getValue()).equals(praticaVO.getRuoloUtenteBoSuPratica())) && mudeTUser != null && mudeTUser.getUtenteBo() && mudeRUtenteEnteComuneRuoloRepository.existsRuoloByUtente(mudeTUser.getIdUser()))
	        {        	
	        	// XXX: TODO: handle frontoffice!!!!
	        	MudeRUtenteEnteComuneRuolo mudeRUtenteEnteComuneRuolo = mudeRUtenteEnteComuneRuoloRepository.getRuoloEnteRiceventeByUtenteIstanza(mudeTUser.getIdUser(),istanza.getId());
	        	if(mudeRUtenteEnteComuneRuolo != null) {
	        		praticaVO.setRuoloUtenteBoSuPratica(mudeRUtenteEnteComuneRuolo.getRuoloUtente());
	        	}else {
	        		List<MudeRUtenteEnteComuneRuolo> mudeRUtenteEnteComuneRuolos = mudeRUtenteEnteComuneRuoloRepository.getRuoloEntiTerziByUtenteIstanza(mudeTUser.getIdUser(),istanza.getId());
	        		if(mudeRUtenteEnteComuneRuolos != null && mudeRUtenteEnteComuneRuolos.size()>0) {
	        			praticaVO.setRuoloUtenteBoSuPratica(TipoRuoloUtente.ENTE_TERZO.getValue());
	        		}
	        	}
	        }
    	}
    	
    	String ruoloUtenteBO = praticaVO.getRuoloUtenteBoSuPratica();

        // Fix Funzionalita Utente BO sull'istanza
    	if(ruoloUtenteBO != null) {
	        List<MudeRRuoloFunzione> mudeRUtenteFunzioneList = mudeRUtenteFunzioneRepository.findByCodiceRuoloUtente(ruoloUtenteBO);
	        List<FunzioneUtenteVO> funzioneRuoloVOs = utenteFunzioneEntityMapper.mapListEntityToListVO(mudeRUtenteFunzioneList);
	        praticaVO.setFunzioniUtente(funzioneRuoloVOs);
    	}

        praticaVO.setNumeroPratica(dto.getNumeroPratica());
        praticaVO.setAnno(dto.getAnnoPratica());
        praticaVO.setDataCreazione(utilsDate.asLocalDateTime(dto.getDataInizio()));
        praticaVO.setIdPratica(dto.getId());
        praticaVO.setEnte(enteEntityMapper.mapEntityToVO(dto.getEnte()));

        return praticaVO;
    }

    @Override
    public MudeTPratica mapVOtoEntity(PraticaVO vo, MudeTUser user) {
        // not used
        return null;
    }

    @Override
	public PraticaVO mapEntityToSlimVO(MudeTPratica dto, MudeTUser mudeTUser) {
    	// not used
        return null;
    }
}