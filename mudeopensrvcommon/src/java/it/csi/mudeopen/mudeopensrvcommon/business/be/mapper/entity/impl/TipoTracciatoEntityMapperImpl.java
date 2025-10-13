/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
import org.springframework.stereotype.Component;

@Component
public class TipoTracciatoEntityMapperImpl implements TipoTracciatoEntityMapper {

    @Override
    public TipoTracciatoVO mapEntityToVO(MudeDTipoTracciato dto) {
        if (dto == null)
            return null;

        TipoTracciatoVO vo = new TipoTracciatoVO();
        vo.setId(dto.getId());
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setVersione(dto.getVersione());
        vo.setXsdValidazione(dto.getXsdValidazione());

        return vo;
    }

    @Override
    public TipoTracciatoVO mapEntityToSlimVO(MudeDTipoTracciato dto) {
        return TipoTracciatoEntityMapper.super.mapEntityToSlimVO(dto);
    }
}