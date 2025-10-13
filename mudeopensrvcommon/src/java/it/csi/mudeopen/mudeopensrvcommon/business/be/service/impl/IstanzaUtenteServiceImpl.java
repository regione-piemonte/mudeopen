/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtenteQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaUtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAbilitazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDFunzioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaQuadroUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.FunzioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

@Service
public class IstanzaUtenteServiceImpl implements IstanzaUtenteService {
    private static Logger logger = Logger.getLogger(IstanzaUtenteServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRIstanzaUtenteRepository mudeRIstanzaUtenteRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDFunzioneRepository mudeDFunzioneRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    private MudeRIstanzaUtenteQuadroRepository mudeRIstanzaUtenteQuadroRepository;

    @Autowired
    private MudeDAbilitazioneRepository mudeDAbilitazioneRepository;

    @Autowired
    private IstanzaUtenteEntityMapper istanzaUtenteEntityMapper;

	@Autowired
	private MudeTUserRepository mudeTUserRepository;

    @Autowired
    private MudeRIstanzaQuadroUtenteRepository mudeRIstanzaQuadroUtenteRepository;
	
    @Autowired
    private AbilitazioneEntityMapper abilitazioneEntityMapper;

    @Override
    public IstanzaUtenteVO save(Long idIstanza, String codiceAbilitazione, MudeTUser user) {
        MudeRIstanzaUtente entity = new MudeRIstanzaUtente();
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        MudeDAbilitazione abilitazione = mudeDAbilitazioneRepository.findMudeDAbilitazioneByCodiceEquals(codiceAbilitazione);
        entity.setIstanza(istanza);
        entity.setUtente(user);
        entity.setAbilitazione(abilitazione);
        entity.setAbilitatore(user);
        entity.setDataInizio(Calendar.getInstance().getTime());

        mudeRIstanzaUtenteRepository.saveDAO(entity);

        return istanzaUtenteEntityMapper.mapEntityToVO(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAbilitazioniFunzioniPerIstanzaUtente(Long idIstanza, AbilitazioneFunzioneCustomVO abilitFunzVO, MudeTUser user) {
    	_saveAbilitazioniFunzioniPerIstanzaUtente(idIstanza, abilitFunzVO, user, false);
    }

    private void _saveAbilitazioniFunzioniPerIstanzaUtente(Long idIstanza, AbilitazioneFunzioneCustomVO abilitFunzVO, MudeTUser user, boolean ignoreIfExists) {
        MudeRIstanzaUtenteQuadro mudeRIstanzaUtenteQuadro = null;
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        List<MudeRIstanzaUtenteQuadro> list = new ArrayList<>();

        String codiceAbilitazione = abilitFunzVO.getAbilitazione().getCodice();
        MudeDAbilitazione abilitazione = mudeDAbilitazioneRepository.findMudeDAbilitazioneByCodiceEquals(codiceAbilitazione);
        if(abilitazione.getTipo() == 'S' && mudeDAbilitazioneRepository.getListaAbilitazioniByIstanzaAndCodiceAbilit(idIstanza, codiceAbilitazione, abilitFunzVO.getIdUtente()).size() > 0 )
        	if(ignoreIfExists)
        		return;
        	else
        		throw new BusinessException("Questa abilitazione è possibile assegnarla solo ad un utente");

        MudeRIstanzaUtente mudeRIstanzaUtente = mudeRIstanzaUtenteRepository.findMudeRIstanzaUtenteByIstanza_IdAndAbilitazione_IdAndUtente_IdUserAndDataFineIsNull(idIstanza, abilitazione.getId(), abilitFunzVO.getIdUtente());
        if(null == mudeRIstanzaUtente){
            mudeRIstanzaUtente = new MudeRIstanzaUtente();
            mudeRIstanzaUtente.setIstanza(istanza);
            mudeRIstanzaUtente.setUtente(mudeTUserRepository.findOne(abilitFunzVO.getIdUtente()));
            mudeRIstanzaUtente.setAbilitazione(abilitazione);
            mudeRIstanzaUtente.setAbilitatore(user);
            mudeRIstanzaUtente.setDataInizio(Calendar.getInstance().getTime());
            mudeRIstanzaUtenteRepository.saveDAO(mudeRIstanzaUtente);
        }

        mudeRIstanzaUtenteQuadroRepository.deleteByIstanzaUtente(mudeRIstanzaUtente.getId());
        mudeRIstanzaUtenteQuadroRepository.flush();

        for (FunzioneVO funzioneVO : CollectionUtils.emptyIfNull(abilitFunzVO.getFunzioni())) {
            Long idFunzione = funzioneVO.getId();
            MudeDFunzione funzione = mudeDFunzioneRepository.findOne(idFunzione);
            List<QuadroVO> quadri = abilitFunzVO.getQuadri();
            for (QuadroVO quadroVO : quadri) {
                MudeDQuadro quadro = mudeDQuadroRepository.findByIdQuadro(quadroVO.getIdQuadro());
                mudeRIstanzaUtenteQuadro = new MudeRIstanzaUtenteQuadro();
                mudeRIstanzaUtenteQuadro.setMudeRIstanzaUtente(mudeRIstanzaUtente);
                mudeRIstanzaUtenteQuadro.setMudeDQuadro(quadro);
                mudeRIstanzaUtenteQuadro.setMudeDAbilitazione(abilitazione);
                mudeRIstanzaUtenteQuadro.setMudeDFunzione(funzione);
                mudeRIstanzaUtenteQuadro.setDataInizio(Calendar.getInstance().getTime());
                list.add(mudeRIstanzaUtenteQuadro);
            }
        }
        mudeRIstanzaUtenteQuadroRepository.saveDAO(list);
    }

    //    @Override
    //    public List<IstanzaUtenteVO> save(List<IstanzaUtenteVO> vo) {
    //        return null;
    //    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        MudeRIstanzaUtente mudeRIstanzaUtente = mudeRIstanzaUtenteRepository.findOne(id);
        if (mudeRIstanzaUtente != null) {
        	// in case the permission has been assigned from the creator, then skip all checks
        	if(mudeRIstanzaQuadroUtenteRepository.findAnyActivitySince(mudeRIstanzaUtente.getIstanza().getId(), 
																	   mudeRIstanzaUtente.getUtente().getIdUser(),
																	   mudeRIstanzaUtente.getDataInizio()).size() > 0
        			|| mudeRIstanzaUtenteRepository.findAllAbilitati(mudeRIstanzaUtente.getIstanza().getId(),
        														 mudeRIstanzaUtente.getUtente().getIdUser(),
        														 mudeRIstanzaUtente.getDataInizio()).size() > 0)
        		throw new BusinessException("Non è consentito eliminare un'abilitazione per un utente che ha lavorato all'istanza o che abbia assegnato abilitazioni ad altri utenti");
        	
            mudeRIstanzaUtente.setDataFine(Calendar.getInstance().getTime());
            mudeRIstanzaUtenteRepository.saveDAO(mudeRIstanzaUtente);

            //logical delete in MudeRIstanzaUtenteQuadro
            mudeRIstanzaUtenteQuadroRepository.deleteByIstanzaUtente(mudeRIstanzaUtente.getId());
        }
    }

    @Override
    public IstanzaUtenteVO findById(Long id) {
        return null;
    }

    @Override
    public List<IstanzaUtenteVO> recuperaAbilitazioniIstanza(Long idIstanza, boolean complete) {
        List<MudeRIstanzaUtente> list = mudeRIstanzaUtenteRepository.findAllByIstanza_IdAndDataFineIsNull(idIstanza);
        if(complete)
        	return istanzaUtenteEntityMapper.mapListEntityToListVO(list);

    	return istanzaUtenteEntityMapper.mapListEntityToListSlimVO(list);
    }

    @Override
    public List<IstanzaUtenteVO> findByUtente(String cfUtente) {
        return null;
    }

    @Override
    public List<IstanzaUtenteVO> findByIstanzaAndUtente(Long idIstanza, Long idUser, String cf) {
        List<MudeRIstanzaUtente> list = mudeRIstanzaUtenteRepository.associateIstanzaUtenteAndSoggetto(idIstanza, idUser, cf);
        return istanzaUtenteEntityMapper.mapListEntityToListSlimVO(list);
    }

    @Override
    public List<IstanzaUtenteVO> findByIstanzaAndAbilitazione(Long idIstanza, Long idAbilitazione) {
        List<MudeRIstanzaUtente> list = mudeRIstanzaUtenteRepository.findAllByIstanza_IdAndAbilitazione_IdAndDataFineIsNull(idIstanza, idAbilitazione);
        return istanzaUtenteEntityMapper.mapListEntityToListVO(list);
    }

    @Override
    public List<IstanzaUtenteVO> findByIstanzaAndCodiceAbilitazione(Long idIstanza, String... codiceAbilitazione) {
        List<MudeRIstanzaUtente> list = mudeRIstanzaUtenteRepository.findAllByIstanza_IdAndAbilitazione_CodiceAndDataFineIsNull(idIstanza, codiceAbilitazione);
        return istanzaUtenteEntityMapper.mapListEntityToListVO(list);
    }

    @Override
    public List<IstanzaUtenteVO> findAll() {
        return null;
    }

    @Override
    public IstanzaUtenteVO findByIstanzaAndAbilitazioneAndUtente(Long idIstanza, Long idAbilitazione, Long idUtente) {
        MudeRIstanzaUtente mudeRIstanzaUtente = mudeRIstanzaUtenteRepository.findMudeRIstanzaUtenteByIstanza_IdAndAbilitazione_IdAndUtente_IdUserAndDataFineIsNull(idIstanza, idAbilitazione, idUtente);
        return istanzaUtenteEntityMapper.mapEntityToVO(mudeRIstanzaUtente);
    }
    
    @Override
    public void setAbilitazionePerIstanzaUtente(Long idIstanza, String permitionCode, Long userToAssign, MudeTUser userAssigning) {
    	AbilitazioneFunzioneCustomVO abilitFunzVO = new AbilitazioneFunzioneCustomVO();
    	abilitFunzVO.setIdUtente(userToAssign);
    	
        MudeDAbilitazione mudeDAbilitazione = mudeDAbilitazioneRepository.findBycodiceAndDataFineIsNull(permitionCode);
        if(mudeDAbilitazione == null) return;
    	abilitFunzVO.setAbilitazione(abilitazioneEntityMapper.mapEntityToVO(mudeDAbilitazione));

		_saveAbilitazioniFunzioniPerIstanzaUtente(idIstanza, abilitFunzVO, userAssigning, true);
    }


}