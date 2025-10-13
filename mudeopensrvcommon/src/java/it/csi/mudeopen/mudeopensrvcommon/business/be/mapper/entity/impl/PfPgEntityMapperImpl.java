/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRPfPg;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PfPgEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.PfPgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PfPgEntityMapperImpl implements PfPgEntityMapper {

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    //    @Autowired
    //    private ContattoPFEntityMapper contattoPFEntityMapper;

    @Override
    public PfPgVO mapEntityToVO(MudeRPfPg dto, MudeTUser user) {
        PfPgVO vo = null;
        if (null != dto) {
            vo = new PfPgVO();
            ContattoVO pfvo = contattoEntityMapper.mapEntityToVO(dto.getSoggettoPf(), null);
            ContattoVO pgvo = contattoEntityMapper.mapEntityToVO(dto.getSoggettoPg(), null);

            vo.setIdTitolo(dto.getIdTitolo());

            vo.setId(dto.getId());
            vo.setSoggettoPf(pfvo);
            vo.setSoggettoPg(pgvo);
        }
        return vo;
    }

    @Override
    public MudeRPfPg mapVOtoEntity(PfPgVO vo, MudeTUser user) {
        MudeRPfPg dto = null;
        if (null != vo) {
            MudeTContatto pf = contattoEntityMapper.mapVOtoEntity(vo.getSoggettoPf(), user);
            MudeTContatto pg = contattoEntityMapper.mapVOtoEntity(vo.getSoggettoPg(), user);
            if (null != pf && null != pg) {
                dto = new MudeRPfPg();
                dto.setSoggettoPf(pf);
                dto.setSoggettoPg(pg);
                dto.setIdTitolo(vo.getIdTitolo());
                dto.setId(vo.getId());
            }
        }
        return dto;
    }
}