/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloIntestatarioEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FascicoloIntestatarioEntityMapperImpl implements FascicoloIntestatarioEntityMapper {

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

//    @Autowired
//    private ContattoEntityMapper contattoPGEntityMapper;

    @Override
    public FascicoloIntestatarioVO mapEntityToVO(MudeRFascicoloIntestatario dto) {
        FascicoloIntestatarioVO vo = null;
        if (null != dto) {
            vo = new FascicoloIntestatarioVO();
            vo.setFascicolo(fascicoloEntityMapper.mapEntityToVO(dto.getFascicolo(), null));
            ContattoVO intestatario = null;
            if (dto.getIntestatario() != null) {
//                if (dto.getIntestatario().getTipoContatto() == TipoContatto.PF) {
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