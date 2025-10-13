/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAmbito;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTLogTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.LogTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTLogTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.IstanzaSlimLogTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.LogTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.UtenteSlim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class LogTracciatoEntityMapperImpl implements LogTracciatoEntityMapper {

    @Autowired
    private MudeRUtenteRuoloRepository mudeRUtenteRuoloRepository;

    @Autowired
    private MudeTLogTracciatoRepository mudeTLogTracciatoRepository;

    @Autowired
    private MudeDTipoTracciatoRepository mudeDTipoTracciatoRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private TipoTracciatoEntityMapper tipoTracciatoEntityMapper;

    @Override
    public LogTracciatoVO mapEntityToVO(MudeTLogTracciato dto, MudeTUser user) {
        if (dto == null)
            return null;

        LogTracciatoVO vo = new LogTracciatoVO();
        vo.setId(dto.getId());
        vo.setErrore(dto.getErrore());
        vo.setTipoErrore(dto.getTipoErrore());
        vo.setErrorTime(dto.getErrorTime());
        IstanzaSlimLogTracciatoVO istanzaVO = new IstanzaSlimLogTracciatoVO();
        istanzaVO.setIdIstanza(dto.getMudeTIstanza().getId());
        istanzaVO.setCodiceIstanza(dto.getMudeTIstanza().getCodiceIstanza());
        istanzaVO.setCodiceTipoIstanza(dto.getMudeTIstanza().getTipoIstanza().getCodice());
        vo.setIstanza(istanzaVO);
        vo.setTipoTracciato(tipoTracciatoEntityMapper.mapEntityToVO(dto.getMudeDTipoTracciato()));

        UtenteSlim utente = new UtenteSlim();
        List<MudeRUtenteRuolo> mudeRUtenteRuoloList = mudeRUtenteRuoloRepository.findByIdUser(user.getIdUser());
        if(mudeRUtenteRuoloList.size()>0) {
            utente.setBackofficeUser(true);
        }
        utente.setCf(user.getCf());
        utente.setNome(user.getNome());
        utente.setCognome(user.getCognome());
        vo.setUtente(utente);
        vo.setMailInviata(dto.getMailInviata());

        return vo;
    }

    @Override
    public MudeTLogTracciato mapVOtoEntity(LogTracciatoVO vo, MudeTUser user) {
        if (vo == null)	return null;

        MudeTLogTracciato entity = null;
        if(null != vo.getId()){
            entity = mudeTLogTracciatoRepository.findOne(vo.getId());
            if(null == entity) return null;
        }
        else{
            entity = new MudeTLogTracciato();
        }
        entity.setErrorTime(vo.getErrorTime());
        entity.setErrore(vo.getErrore());
        entity.setTipoErrore(vo.getTipoErrore());
        if(null != vo.getTipoTracciato() && null != vo.getTipoTracciato().getId()) {
            MudeDTipoTracciato tipoTracciato = mudeDTipoTracciatoRepository.findOne(vo.getTipoTracciato().getId());
            entity.setMudeDTipoTracciato(tipoTracciato);
        }
        if(null != vo.getIstanza() && null != vo.getIstanza().getIdIstanza()) {
            MudeTIstanza istanza = mudeTIstanzaRepository.findByIdIstanza(vo.getIstanza().getIdIstanza());
            entity.setMudeTIstanza(istanza);
        }
        entity.setMudeTUser(user);
        entity.setMailInviata(vo.getMailInviata());
        return entity;

    }
}