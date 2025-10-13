/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateResponse;

import java.util.List;

public interface IstanzaTemplateResponseEntityMapper {

    List<IstanzaTemplateResponse> mapListEntityToListVO(MudeTIstanza istanza, List<MudeRTemplateQuadro> templateQuadros);

    IstanzaTemplateResponse mapEntityToVO(MudeTIstanza ist, List<MudeRTemplateQuadro> entry);

    //    IstanzaTemplateResponse mapEntityToVO(Map.Entry<Long, List<MudeRIstanzaTemplateQuadro>> entry);

    //    List<IstanzaTemplateResponse> mapListEntityToListVO(List<MudeRIstanzaTemplateQuadro> tl);
}