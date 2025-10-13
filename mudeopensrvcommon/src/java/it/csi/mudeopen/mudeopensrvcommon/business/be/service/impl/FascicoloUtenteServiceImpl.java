/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.Calendar;
import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloUtenteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloUtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAbilitazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaQuadroUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FascicoloUtenteVO;

@Service
public class FascicoloUtenteServiceImpl implements FascicoloUtenteService {
    private static Logger logger = Logger.getLogger(IstanzaUtenteServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRFascicoloUtenteRepository mudeRFascicoloUtenteRepository;

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDAbilitazioneRepository mudeDAbilitazioneRepository;

    @Autowired
    private FascicoloUtenteEntityMapper fascicoloUtenteEntityMapper;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

	@Autowired
	private MudeTUserRepository mudeTUserRepository;

    @Autowired
    private MudeRIstanzaQuadroUtenteRepository mudeRIstanzaQuadroUtenteRepository;
	
    @Override
    public FascicoloUtenteVO save(Long idFascicolo, String codiceAbilitazione, MudeTUser user) {
        MudeRFascicoloUtente entity = new MudeRFascicoloUtente();
        MudeTFascicolo fascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
        MudeDAbilitazione abilitazione = mudeDAbilitazioneRepository.findMudeDAbilitazioneByCodiceEquals(codiceAbilitazione);

        entity.setFascicolo(fascicolo);
        entity.setUtente(user);
        entity.setAbilitazione(abilitazione);
        entity.setAbilitatore(user);
        entity.setDataInizio(Calendar.getInstance().getTime());
        mudeRFascicoloUtenteRepository.saveDAO(entity);

        return fascicoloUtenteEntityMapper.mapEntityToVO(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAbilitazioniFunzioniPerFascicoloUtente(Long idFascicolo, AbilitazioneFunzioneCustomVO vo, MudeTUser user) {
    	_saveAbilitazioniFunzioniPerFascicoloUtente(idFascicolo, vo.getAbilitazione().getCodice(), vo.getIdUtente(), user);
    }
    
    private void _saveAbilitazioniFunzioniPerFascicoloUtente(Long idFascicolo, String codiceAbilitazione, Long isUserToEnable, MudeTUser user) {
        MudeTFascicolo fascicolo = mudeTFascicoloRepository.findOne(idFascicolo);

        MudeDAbilitazione abilitazione = mudeDAbilitazioneRepository.findMudeDAbilitazioneByCodiceEquals(codiceAbilitazione);

        MudeRFascicoloUtente mudeRFascicoloUtente = mudeRFascicoloUtenteRepository.findMudeRFascicoloUtenteByFascicolo_IdAndAbilitazione_IdAndUtente_IdUserAndDataFineIsNull(idFascicolo, abilitazione.getId(), isUserToEnable);
        if(mudeRFascicoloUtente == null){
            mudeRFascicoloUtente = new MudeRFascicoloUtente();
            mudeRFascicoloUtente.setFascicolo(fascicolo);
            mudeRFascicoloUtente.setUtente(mudeTUserRepository.findOne(isUserToEnable));
            mudeRFascicoloUtente.setAbilitazione(abilitazione);
            mudeRFascicoloUtente.setAbilitatore(user);
            mudeRFascicoloUtente.setDataInizio(Calendar.getInstance().getTime());

            mudeRFascicoloUtenteRepository.saveDAO(mudeRFascicoloUtente);
        }
    }

    @Override
    public List<FascicoloUtenteVO> save(List<FascicoloUtenteVO> vo) {
        return null;
    }

    @Override
    public void delete(Long id) {
        MudeRFascicoloUtente mudeRFascicoloUtente = mudeRFascicoloUtenteRepository.findOne(id);
        if(mudeRFascicoloUtente != null) {
        	// are there istances created in this fascicolo OR has the user given any permission to others
        	if(mudeTIstanzaRepository.findAllByMudeTFascicolo_idAndMudeTUser_idUser(mudeRFascicoloUtente.getFascicolo().getId(), mudeRFascicoloUtente.getUtente().getIdUser()).size() > 0
        			|| mudeRFascicoloUtenteRepository.findAllAbilitati(mudeRFascicoloUtente.getFascicolo().getId(),
        														   mudeRFascicoloUtente.getUtente().getIdUser()).size() > 0)
        		throw new BusinessException("Non è consentito eliminare l'abilitazione perché l'utente ha già creato istanze nel fascicolo");
        		
            mudeRFascicoloUtente.setDataFine(Calendar.getInstance().getTime());
            mudeRFascicoloUtenteRepository.saveDAO(mudeRFascicoloUtente);
        }
    }

    @Override
    public FascicoloUtenteVO findById(Long id) {
        return null;
    }

    @Override
    public List<FascicoloUtenteVO> findByFascicolo(Long idFascicolo) {
        return null;
    }

    @Override
    public List<FascicoloUtenteVO> findByUtente(String cfUtente) {
        return null;
    }

    @Override
    public List<FascicoloUtenteVO> findAll() {
        return null;
    }

    @Override
    public List<FascicoloUtenteVO> findByFascicoloAndAbilitazione(Long idFascicolo, Long idAbilitazione) {
        List<MudeRFascicoloUtente> list = mudeRFascicoloUtenteRepository.findAllByFascicolo_IdAndAbilitazione_IdAndDataFineIsNull(idFascicolo, idAbilitazione);
        return fascicoloUtenteEntityMapper.mapListEntityToListVO(list);
    }

	@Override
	public List<FascicoloUtenteVO> recuperaAbilitazioniFascicolo(Long idFascicolo) {
        List<MudeRFascicoloUtente> list = mudeRFascicoloUtenteRepository.findAllByFascicolo_IdAndDataFineIsNull(idFascicolo);
        return fascicoloUtenteEntityMapper.mapListEntityToListVO(list);
	}
	
	@Override
    public void setAbilitazionePerFascicoloUtente(Long idFascicolo, String permitionCode, Long userToEnable, MudeTUser user) {
     	_saveAbilitazioniFunzioniPerFascicoloUtente(idFascicolo, permitionCode, userToEnable, user);
   }
    
}