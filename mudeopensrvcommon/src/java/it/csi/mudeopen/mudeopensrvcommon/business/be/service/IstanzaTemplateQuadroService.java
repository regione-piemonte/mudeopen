/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.IstanzaTemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.IstanzaTemplateQuadroRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateQuadroResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateResponse;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

public interface IstanzaTemplateQuadroService {

//    List<IstanzaTemplateQuadroVO> loadIstanzeTemplateQuadro();

    List<IstanzaTemplateResponse> loadIstanzeTemplateQuadroByIdIstanza(Long idIstanza);

    IstanzaTemplateQuadroVO loadIstanzaTemplateQuadroByPK(Long idIstanza, Long idTemplateQuadro, MudeTUser mudeTUser, HttpHeaders httpHeaders);

    IstanzaTemplateQuadroVO saveIstanzaTemplateQuadro(IstanzaTemplateQuadroVO istanzaTemplateQuadro, boolean jsondataModified, boolean mainQuadroValidated, MudeTUser mudeTUser, HttpHeaders httpHeaders);

    IstanzaTemplateQuadroVO updateIstanzaTemplateQuadro(IstanzaTemplateQuadroVO istanzaTemplateQuadro);

/*
    void deleteIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro);
*/
}