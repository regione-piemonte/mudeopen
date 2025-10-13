/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSession;

public interface MudeTGeecoSessionRepository  extends BaseRepository<MudeTGeecoSession, Long> {

	
	List<MudeTGeecoSession> findByIdIstanzaAndIdLocalizzazione(Long idFascicolo, Long idLocalizzazione);

	MudeTGeecoSession findBySessioneGeeco(String geecoSessioinID);
	
	List<MudeTGeecoSession> findByIdIstanzaAndSessioneGeeco(Long idIstanza, String geecoSessioinID);

	List<MudeTGeecoSession> findByIdIstanza(Long idIstanza);

	MudeTGeecoSession findTop1ByMudeTUser_IdUserAndIdIstanzaOrderByDataInizioDesc(Long idUser, Long idIstanza);
	
}
