/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteFunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteRuoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRRuoloFunzioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FunzioneUtenteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

@Component
public class UtenteEntityMapperImpl implements UtenteEntityMapper {

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    @Autowired
    private UtenteRuoloEntityMapper utenteRuoloEntityMapper;

    @Autowired
    private MudeRUtenteRuoloRepository mudeRUtenteRuoloRepository;

    @Autowired
    private UtenteFunzioneEntityMapper utenteFunzioneEntityMapper;

    @Autowired
    private MudeRRuoloFunzioneRepository mudeRUtenteFunzioneRepository;

    @Autowired
    UserUtil userUtil;

    @Override
    public UtenteVO mapEntityToVO(MudeTUser dto) {

        UtenteVO infoUtente = null;
        if (dto != null) {
            NazioneVO statoNascita = null;
            if (dto.getStatoNascita() != null) {
                statoNascita = new NazioneVO();
                statoNascita.setId(dto.getStatoNascita().getIdNazione());
                statoNascita.setDescrizione(dto.getStatoNascita().getDenomNazione());
            }

            ProvinciaVO provinciaNascita = null;
            ComuneVO comuneNascita = null;
            if (dto.getComuneNascita() != null) {
                comuneNascita = new ComuneVO();
                comuneNascita.setDescrizione(dto.getComuneNascita().getDenomComune());
                comuneNascita.setId(dto.getComuneNascita().getIdComune());
                provinciaNascita = new ProvinciaVO();
                provinciaNascita.setDescrizione(dto.getComuneNascita().getMudeDProvincia().getDenomProvincia());
                provinciaNascita.setId(dto.getComuneNascita().getMudeDProvincia().getIdProvincia());

            }
            infoUtente = new UtenteVO();

            infoUtente.setId(dto.getIdUser());
            infoUtente.setBackofficeUser(Boolean.valueOf(userUtil.isBackofficeAdminUser(dto)));

            MudeTContatto mudeTContatto = mudeTContattoRepository.findByIdUser(dto.getIdUser());
            ContattoVO contattoVO = contattoEntityMapper.mapEntityToVO(mudeTContatto, dto);
            infoUtente.setContatto(contattoVO);

            List<MudeRUtenteRuolo> mudeRUtenteRuoloList = mudeRUtenteRuoloRepository.findByIdUser(dto.getIdUser());
            List<SelectVO> ruoloVOs = utenteRuoloEntityMapper.mapListEntityToListVO(mudeRUtenteRuoloList);
            infoUtente.setRuoliUtente(ruoloVOs);
            if(ruoloVOs.size()>0)
            	infoUtente.setBackofficeUser(true);

            List<FunzioneUtenteVO> funzioneVOs = new ArrayList<>();
            for(SelectVO ruoloUtente : ruoloVOs) {
	            List<MudeRRuoloFunzione> mudeRUtenteFunzioneList = mudeRUtenteFunzioneRepository.findByCodiceRuoloUtente(ruoloUtente.getId());
	            List<FunzioneUtenteVO> funzioneRuoloVOs = utenteFunzioneEntityMapper.mapListEntityToListVO(mudeRUtenteFunzioneList);
	            for(FunzioneUtenteVO funzioneRuoloVO: funzioneRuoloVOs) {
	            	boolean bDuplicate=false;
	            	for(FunzioneUtenteVO funzioneVO: funzioneVOs) {
	            		if(funzioneVO.getCodFunzione().equals(funzioneRuoloVO.getCodFunzione())) {
	            			bDuplicate=true;
	            			break;
	            		}
	            	}
	            	if(!bDuplicate)
	            		funzioneVOs.add(funzioneRuoloVO);
	            }
            }
            infoUtente.setFunzioniUtente(funzioneVOs);
        }
        return infoUtente;

    }

}