/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoVO;

public interface TipoAllegatoService {

    TipoAllegatoVO findByCodTipoAllegato(String codTipoAllegato);

    List<TipoAllegatoVO> findAllByIdCategoriaAllegato(String codiceCategoriaAllegato);

    List<TipoAllegatoVO> findAllOrderByDescBreve();

    TipoAllegatoVO findByCodTipoAllegatoAndSubCodeTipoAllegato(String codTipoAllegato, String subCodeTipoAllegato);

    List<TipoAllegatoExtendedVO> findTipiAllegatoByTemplateQuadro(long idIstanza, long idTemplateQuadro, MudeTUser mudeTUser, HttpHeaders httpHeaders,
			String tipo_allegato);
}