/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;

@Repository
public interface MudeDRegioneRepository extends BaseRepository<MudeDRegione, Long> {

    @Query("Select u from MudeDRegione u where ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))  ORDER BY u.denomRegione ASC")
	List<MudeDRegione> findAllByFineValiditaOrderByDenomRegioneAsc();

    @Query("Select u from MudeDRegione u ORDER BY u.denomRegione ASC")
	List<MudeDRegione> findAllOrderByDenomRegioneAsc();

    List<MudeDRegione> findByMudeDNazioneOrderByDenomRegioneAsc(MudeDNazione mudeDNazione);

}