/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeREnteTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.EnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeREnteTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.EntiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTEnteSpecification;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;

@Service
public class EntiServiceImpl implements EntiService {
    private static Logger logger = Logger.getLogger(EntiServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeRIstanzaEnteRepository mudeRIstanzaEnteRepository;

    @Autowired
    private MudeTEnteRepository mudeTEnteRepository;

    @Autowired
    private MudeREnteTipoIstanzaRepository mudeREnteTipoIstanzaRepository;

    @Autowired
    private EnteEntityMapper enteEntityMapper;

    @Override
    public List<EnteVO> findAllActiveByComune(long idComune) {
        List<MudeTEnte> entities = mudeTEnteRepository.findAllActiveByComune(idComune);
        List<EnteVO> enti = enteEntityMapper.mapListEntityToListVO(entities);
        return enti;
    }

    @Override
	public void assegnaEntiAdIstanza(MudeTIstanza istanza) {
    	assegnaEntiAdIstanza(istanza, false);
    }
    
	private void assegnaEntiAdIstanza(MudeTIstanza istanza, boolean ignoreParentOverride) {
    	// disable old r_istanza_ente records, if any
		for(MudeRIstanzaEnte mudeRIstanzaEnte 
				: mudeRIstanzaEnteRepository.findAllByIstanza_IdAndDataFineIsNull(istanza.getId())) {
			mudeRIstanzaEnte.setDataFine(new Date());
			mudeRIstanzaEnteRepository.saveDAO(mudeRIstanzaEnte);
		}

		boolean getAlsoConfDisabled;
		MudeTIstanza istanzaRif = istanza;
		if(getAlsoConfDisabled = !ignoreParentOverride 
				&& istanza.getTipoIstanza().getOverrideEntePadre() 
				&& istanza.getIdIstanzaRiferimento() != null)
			istanzaRif = mudeTIstanzaRepository.findOne(istanza.getIdIstanzaRiferimento());

		List<MudeREnteTipoIstanza> retrieveAllActives = mudeREnteTipoIstanzaRepository.retrieveAllActives(istanzaRif.getComune().getIdComune(), 
																								istanzaRif.getTipoIstanza().getCodice(),
																								istanzaRif.getSpeciePratica() == null? "getnulls" : istanzaRif.getSpeciePratica().getCodice(),
																								false /*getAlsoConfDisabled*/);
		if(retrieveAllActives.size() == 0) {
			/*
			if(istanzaRif.getTipoIstanza().getOverrideEntePadre() && istanzaRif.getIdIstanzaRiferimento() != null)
		    	assegnaEntiAdIstanza(istanzaRif, true);
			else
			*/
				throw new BusinessException("Non è possibile depositare istanze per questo comune. Per maggiori informazioni consultare il sito del comune.");
		}
		
		// creates new version ones
		for(MudeREnteTipoIstanza mudeREnteTipoIstanza : retrieveAllActives) {
			
			MudeRIstanzaEnte mudeRIstanzaEnte = new MudeRIstanzaEnte();
			mudeRIstanzaEnte.setEnte(mudeREnteTipoIstanza.getEnte());
			mudeRIstanzaEnte.setIstanza(istanza);
			mudeRIstanzaEnte.setEnteRicevente(mudeREnteTipoIstanza.getCompetenzaPrincipale());
			mudeRIstanzaEnte.setDataInizio(new Date());
			
			mudeRIstanzaEnteRepository.saveDAO(mudeRIstanzaEnte);
		}
	}

    @Override
    public List<EnteVO> findActiveByDescrizione(String descrizione) {
        Specification<MudeTEnte> isActive = MudeTEnteSpecification.isNotDeleted();
        Specification<MudeTEnte> hasDescrizioneLike = MudeTEnteSpecification.hasDescrizioneLike(descrizione);
        Specification<MudeTEnte> hasDescrizioneEstesaLike = MudeTEnteSpecification.hasDescrizioneEstesaLike(descrizione);
        Specification<MudeTEnte> descrizioneOr = Specifications.where(hasDescrizioneLike).or(hasDescrizioneEstesaLike);
        Specification<MudeTEnte> filter = Specifications.where(isActive).and(descrizioneOr);

        List<MudeTEnte> entities = mudeTEnteRepository.findAll(filter);
        List<EnteVO> enti = enteEntityMapper.mapListEntityToListVO(entities);
        return enti;
    }

    @Override
    public EnteVO findByCodice(String codice) {
        MudeTEnte entity = mudeTEnteRepository.findByCodice(codice);
        return enteEntityMapper.mapEntityToVO(entity);
    }

    @Override
    public EnteVO findActiveByComuneAndTipoEnte(Long idComune, String codiceTipoEnte) {
        MudeTEnte entity = mudeTEnteRepository.findActiveByComuneAndTipoEnte(idComune, codiceTipoEnte);
        return enteEntityMapper.mapEntityToVO(entity);
    }
}