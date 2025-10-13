/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.PPayImportoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.PPayResultVO;

public interface PiemontePayService {

	List<PPayImportoVO> recuperaDettagliPagamento(Long idIstanza);

    public PPayResultVO avviaPagamento(Long idIstanza, String extraParams, MudeTUser mudeTUser);

	public PPayResultVO ppayCallback(String idPagamento, String codEsito, String descrizioneEsito, MudeTUser mudeTUser);
	
}