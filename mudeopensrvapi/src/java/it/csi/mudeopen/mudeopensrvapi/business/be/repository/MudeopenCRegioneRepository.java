/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mude c regione repository.
 */
@Repository
public interface MudeopenCRegioneRepository extends BaseRepository<MudeCRegione, Long> {
	
}