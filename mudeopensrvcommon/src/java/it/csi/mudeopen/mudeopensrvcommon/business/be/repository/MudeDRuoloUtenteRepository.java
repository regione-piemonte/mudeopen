/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;

@Repository
public interface MudeDRuoloUtenteRepository extends BaseDictionaryRepository<MudeDRuoloUtente, String> {
	
	MudeDRuoloUtente findByCodice(String codice);

}