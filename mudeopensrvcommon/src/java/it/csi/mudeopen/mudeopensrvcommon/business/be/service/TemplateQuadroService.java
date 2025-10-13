/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.JDataIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateResponse;

public interface TemplateQuadroService {
    TemplateQuadroVO loadTemplateQuadroById(Long idTemplateQuadro);

    TemplateQuadroVO loadTemplateQuadroByIdTemplateIdQuadro(Long idTemplate, Long idQuadro);

    TemplateQuadroVO loadTemplateQuadroByIdTipoIstanza(String codiceTipoIstanza);

    TemplateQuadroVO loadTemplateQuadroByTipoIstanzaDesc(String descTipoIstanza);

//    List<TemplateQuadroVO> loadTemplateQuadroByCodeTemplate(String codeTemplate);

    List<TemplateResponse> loadTemplateQuadroResponseByCodeTemplate(String idTipoIstanza, String boTemplateOverride, MudeTUser mudeTUser, Long idIstanza);
    QuadroVO loadQuadroById(Long idQuadro, Long idIstanza, Long idFascicolo, HttpHeaders httpHeaders);

    JDataIstanzaVO loadJDataIstanza(Long idIstanza,
								Long idQuadro,
								String codTipoQuadro,
								Boolean isObbligatoriaNominaPM,
								String requestType,
								HttpHeaders httpHeaders, MudeTUser mudeTUser);

    List<QuadroVO> retrieveAllQuadriInTemplate(Long idTemplate, Long idIstanza, Long idUser, Boolean includeJson);
}