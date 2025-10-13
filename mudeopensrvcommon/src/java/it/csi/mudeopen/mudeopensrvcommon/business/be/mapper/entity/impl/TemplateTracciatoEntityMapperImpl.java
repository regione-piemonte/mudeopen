/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplateTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TemplateTracciatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateTracciatoEntityMapperImpl implements TemplateTracciatoEntityMapper {

    @Override
    public TemplateTracciatoVO mapEntityToVO(MudeDTemplateTracciato dto) {
        if (dto == null)
            return null;

        TemplateTracciatoVO vo = new TemplateTracciatoVO();
        vo.setId(dto.getId());
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setXsltTemplate(dto.getXsltTemplate());

        return vo;
    }
}