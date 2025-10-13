/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.EntityDMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateResponse;

import java.util.List;
import java.util.Map;

public interface TemplateResponseEntityMapper {

    TemplateResponse mapEntityToVO(Map.Entry<Long, List<MudeRTemplateQuadro>> entry);
    TemplateResponse mapEntityToVO(Map.Entry<Long, List<MudeRTemplateQuadro>> entry, boolean forceActive);

    List<TemplateResponse> mapListEntityToListVO(List<MudeRTemplateQuadro> tl);
    List<TemplateResponse> mapListEntityToListVO(List<MudeRTemplateQuadro> tl, boolean forceActive);
}