/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;
import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRDocumentiNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

public interface MudeopenRDocumentiNotificaRepository extends BaseRepository<MudeRDocumentiNotifica, Long> {
	
	Integer countByMudeTNotifica(MudeTNotifica mudeTNotifica);
	
	List<MudeRDocumentiNotifica> findByMudeTNotifica(MudeTNotifica mudeTNotifica);
	
}
