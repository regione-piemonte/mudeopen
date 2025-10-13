/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloIntestatarioSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioSlimVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FascicoloIntestatarioSlimEntityMapperImpl implements FascicoloIntestatarioSlimEntityMapper {

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    //    @Autowired
    //    private ContattoPGEntityMapper contattoPGEntityMapper;

    @Override
    public FascicoloIntestatarioSlimVO mapEntityToVO(MudeRFascicoloIntestatario dto) {
        FascicoloIntestatarioSlimVO vo = null;
        if (null != dto) {
            vo = new FascicoloIntestatarioSlimVO();
            vo.setIdFascicolo(dto.getFascicolo().getId());
            ContattoVO intestatario = null;
            if (dto.getIntestatario() != null) {
//                if (dto.getIntestatario().getTipoContatto() == MudeTContatto.TipoContatto.PF) {
                    intestatario = contattoEntityMapper.mapEntityToVO(dto.getIntestatario(), null);
//                } else {
//                    intestatario = contattoPGEntityMapper.mapEntityToVO(dto.getIntestatario(), null);
//                }
            }
            vo.setIntestatario(intestatario);

        }
        return vo;
    }
}