/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity. MudeTGeecoSelected;

public interface MudeTGeecoSelectedRepository extends BaseRepository< MudeTGeecoSelected, Long> {

	MudeTGeecoSelected findBySessioneGeeco(String sessioneGeeco);

}
