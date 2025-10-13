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

@Repository
public interface MudeDNazioneRepository extends BaseRepository<MudeDNazione, Long> {

    @Query("Select u from MudeDNazione u where (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()) ORDER BY u.denomNazione ASC")
	List<MudeDNazione> findAllByFineValiditaOrderByDenomNazioneAsc();

    @Query("Select u from MudeDNazione u ORDER BY u.denomNazione ASC")
	List<MudeDNazione> findAllDenomNazioneAsc();

    @Query("Select u from MudeDNazione u where u.denomNazione=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	MudeDNazione findByDenomNazioneAndFineValidita(String denomNazione);

    @Query("Select u from MudeDNazione u where u.denomNazione=?1")
	MudeDNazione findByDenomNazione(String denomNazione);

    MudeDNazione findByCodIstatNazione(String codIstatNazione);

    MudeDNazione findByIdNazione(Long idNazione);

    List<MudeDNazione> findByStatoMembroUETrueOrderByCodIstatNazioneAsc();

}