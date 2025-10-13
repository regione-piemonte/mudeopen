/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;

import java.util.List;

public interface TemplateService {

    List<TemplateVO> loadTemplateAttivi();

    TemplateVO loadTemplateById(Long idTemplate);

    TemplateVO loadTemplateByCode(String codTemplate);

    TemplateVO loadTemplateByIdTipoistanza(String idTipoIstanza);

    TemplateVO loadTemplateByTipoistanzaDesc(String descTipoIstanza);

    Long saveTemplate(TemplateVO template);

    int updateTemplate(TemplateVO template);

    void deleteTemplate(Long idTemplate);
}