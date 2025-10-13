/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaUtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IstanzaUtenteEntityMapperImpl implements IstanzaUtenteEntityMapper {

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private AbilitazioneEntityMapper abilitazioneEntityMapper;

    @Override
    public IstanzaUtenteVO mapEntityToVO(MudeRIstanzaUtente dto) {
        IstanzaUtenteVO vo = mapEntityToSlimVO(dto);
        if(vo != null) {
	        vo.setIstanza(istanzaEntityMapper.mapEntityToVO(dto.getIstanza(), null));
	        vo.setAbilitatore(utenteEntityMapper.mapEntityToVO(dto.getAbilitatore()));
        }

        return vo;
    }

    public IstanzaUtenteVO mapEntityToSlimVO(MudeRIstanzaUtente dto) {
        if(null == dto)
            return null;

        IstanzaUtenteVO vo = new IstanzaUtenteVO();
        vo.setId(dto.getId());
        vo.setUtente(utenteEntityMapper.mapEntityToVO(dto.getUtente()));
        vo.setAbilitazione(abilitazioneEntityMapper.mapEntityToVO(dto.getAbilitazione()));

        vo.setModificabile(!dto.getAbilitazione().getCodice().startsWith("CREATORE_IST"));

        return vo;
    }

    @Override
    public List<IstanzaUtenteVO> mapListEntityToListVO(List<MudeRIstanzaUtente> tl) {
        return IstanzaUtenteEntityMapper.super.mapListEntityToListVO(tl);
    }
}