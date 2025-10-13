/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mudeopen d comune repository.
 */
@Repository
public interface MudeopenDComuneRepository extends BaseRepository<MudeDComune, Long> {
    /**
     * Find by cod istat comune and fine validita mude d comune.
     *
     * @param codIstatComune the cod istat comune
      the mude d comune
     */
    @Query("Select u from MudeDComune u where u.istatComune=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
    MudeDComune findByIstatComuneAndFineValidita(String istatComune);
    /**
     * Find by id comune mude d comune.
     *
     * @param idComune the id comune
      the mude d comune
     */
    MudeDComune findByIdComune(Long idComune);

}