/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaStatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.StatoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoVO;

@Component
public class IstanzaStatoEntityMapperImpl implements IstanzaStatoEntityMapper {

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private StatoIstanzaEntityMapper statoIstanzaEntityMapper;

    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

    @Override
    public IstanzaStatoVO mapEntityToVO(MudeRIstanzaStato dto) {
        IstanzaStatoVO vo = mapEntityToSlimVO(dto);
        if(vo != null)
            vo.setIstanza(istanzaEntityMapper.mapEntityToVO(dto.getIstanza(), null));

        return vo;
    }

    @Override
    public IstanzaStatoVO mapEntityToSlimVO(MudeRIstanzaStato dto) {
        IstanzaStatoVO vo = null;
        if (dto != null) {
            vo = new IstanzaStatoVO();
            vo.setId(dto.getId());
            vo.setStatoIstanza(statoIstanzaEntityMapper.mapEntityToVO(dto.getStatoIstanza()));

            vo.setDataInizio(utilsDate.asLocalDateTime(dto.getDataInizio()));
            vo.setDataFine(utilsDate.asLocalDateTime(dto.getDataFine()));

            vo.setEffettuato_da(mudeTUserRepository.getLoggedUser(dto.getLoguser() == null ? "" : dto.getLoguser()));

        }
        return vo;
    }

}