/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.IstanzaTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.UtenteSlim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IstanzaTracciatoEntityMapperImpl implements IstanzaTracciatoEntityMapper {

    @Autowired
    private MudeRUtenteRuoloRepository mudeRUtenteRuoloRepository;

    @Autowired
    private TipoTracciatoEntityMapper tipoTracciatoEntityMapper;

    //    @Autowired
    //    private IstanzaEntityMapper istanzaEntityMapper ;

    @Override
    public IstanzaTracciatoVO mapEntityToVO(MudeRIstanzaTracciato dto, MudeTUser user) {
        if (dto == null)
            return null;
        IstanzaTracciatoVO vo = new IstanzaTracciatoVO();
        vo.setId(dto.getId());
        TipoTracciatoVO tipoTracciatoVO = tipoTracciatoEntityMapper.mapEntityToVO(dto.getMudeDTipoTracciato());
        vo.setTipoTracciato(tipoTracciatoVO);
        //        IstanzaVO istanzaVO =istanzaEntityMapper.mapEntityToVO(dto.getMudeTIstanza(), null);
        vo.setIdIstanza(dto.getMudeTIstanza().getId());
        vo.setXmlTracciato(dto.getXmlTracciato());
        vo.setDataGenerazioneTracciato(dto.getDataInizio());
        if(user != null) {
        	UtenteSlim utente = new UtenteSlim();
        	List<MudeRUtenteRuolo> mudeRUtenteRuoloList = mudeRUtenteRuoloRepository.findByIdUser(user.getIdUser());
        	if (mudeRUtenteRuoloList.size() > 0) {
        		utente.setBackofficeUser(true);
        	}
        	utente.setCf(user.getCf());
        	utente.setNome(user.getNome());
        	utente.setCognome(user.getCognome());        	
        	vo.setUtenteSlim(utente);
        }

        return vo;
    }

    @Override
    public MudeRIstanzaTracciato mapVOtoEntity(IstanzaTracciatoVO vo, MudeTUser user) {
        return null;
    }

    @Override
    public List<IstanzaTracciatoVO> mapListEntityToListVO(List<MudeRIstanzaTracciato> tl, MudeTUser user) {
        return IstanzaTracciatoEntityMapper.super.mapListEntityToListVO(tl, user);
    }
}