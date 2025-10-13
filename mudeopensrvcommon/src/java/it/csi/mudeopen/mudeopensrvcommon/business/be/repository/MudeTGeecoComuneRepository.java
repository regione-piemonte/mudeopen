/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoComune;

public interface MudeTGeecoComuneRepository extends BaseRepository< MudeTGeecoComune, Long> {

	MudeTGeecoComune findByCodBelfioreComune(String codCodiceBelfiore);

}
