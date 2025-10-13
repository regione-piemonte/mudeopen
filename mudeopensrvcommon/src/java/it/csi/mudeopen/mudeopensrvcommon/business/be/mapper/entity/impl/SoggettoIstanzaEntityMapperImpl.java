/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza.DatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SoggettoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDatiIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SoggettoIstanzaEntityMapperImpl implements SoggettoIstanzaEntityMapper {

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;

    @Autowired
    private MudeTDatiIstanzaRepository mudeTDatiIstanzaRepository;

    @Override
    public SoggettoIstanzaVO mapEntityToVO(MudeRIstanzaSoggetto dto, MudeTUser user) {
        if (dto == null)
            return null;
        SoggettoIstanzaVO vo = new SoggettoIstanzaVO();
        //        if (dto.getMudeTContatto().getTipoContatto() == MudeTContatto.TipoContatto.PF) {
        vo.setContatto(contattoEntityMapper.mapEntityToVO(dto.getMudeTContatto(), user));
        //        } else {
        //            vo.setContatto(contattoPGEntityMapper.mapEntityToVO(dto.getMudeTContatto(), user));
        //        }

        vo.setIdIstanzaSoggetto(dto.getIdIstanzaSoggetto());

        List<SelectVO> ruoloSoggetto = new ArrayList<>();
        for (String codiceRuolo : dto.getRuoli()) {
            MudeDRuoloSoggetto ruolo = mudeDRuoloSoggettoRepository.findOne(codiceRuolo);
            if (null != ruolo) {
                ruoloSoggetto.add(new SelectVO(codiceRuolo, ruolo.getDescrizione()));
            }
        }
        vo.setRuoloSoggetto(ruoloSoggetto);

        for(MudeTDatiIstanza titolo : CollectionUtils.emptyIfNull(mudeTDatiIstanzaRepository.findAllTitoloSoggetto(dto.getMudeTIstanza().getId(), dto.getMudeTContatto().getId())))
        	if(titolo.getDato() == DatiIstanza.TITOLO_SOGGETTO_ABILITATO)
        		vo.setIdTitoloSoggetto(titolo.getValore());
        	else if(titolo.getDato() == DatiIstanza.TITOLO_SOGGETTO_ABILITATO_RT)
        		vo.setIdTitoloSoggettoRT(titolo.getValore());

        vo.setIdSoggetto(dto.getMudeTContatto().getId());

        vo.setOperaFiniFiscaliDipendente(dto.getOperaFiniFiscaliDipendente() == null ? false : dto.getOperaFiniFiscaliDipendente());
        vo.setDomiciliazioneCorrispondenzaProfessionista(dto.getDomiciliazioneCorrispondenzaProfessionista() == null ? false : dto.getDomiciliazioneCorrispondenzaProfessionista());
        vo.setEnteSocietaAppartenenza(dto.getEnteSocietaAppartenenza());

        return vo;
    }

    @Override
    public MudeRIstanzaSoggetto mapVOtoEntity(SoggettoIstanzaVO vo, MudeTUser user) {
        return null;
    }
}