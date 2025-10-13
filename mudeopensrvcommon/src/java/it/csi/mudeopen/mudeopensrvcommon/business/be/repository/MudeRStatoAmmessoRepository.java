/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRStatoAmmesso;

public interface MudeRStatoAmmessoRepository extends BaseRepository<MudeRStatoAmmesso, Long>{

	List<MudeRStatoAmmesso> findByStatoIstanza(MudeDStatoIstanza mudeDStatoIstanza);
}

