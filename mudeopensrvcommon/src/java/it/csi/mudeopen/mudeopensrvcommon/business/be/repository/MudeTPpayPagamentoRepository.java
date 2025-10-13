/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPpayPagamento;

@Repository
public interface MudeTPpayPagamentoRepository extends BaseRepository<MudeTPpayPagamento, Long> {

	MudeTPpayPagamento getByIdentificativoPagamento(String idIstanza);

}