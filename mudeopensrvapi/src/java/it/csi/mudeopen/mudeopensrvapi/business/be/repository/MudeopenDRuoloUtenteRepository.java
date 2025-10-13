/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvapi.vo.RuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface MudeopenDRuoloUtente repository.
 */
@Repository
public interface MudeopenDRuoloUtenteRepository extends BaseRepository<MudeDRuoloUtente, Long> {

	@Query(value = "select t from MudeDRuoloUtente t where (t.dataFine is null or t.dataFine > current_date()) and t.dataInizio <= current_date()")
	List<MudeDRuoloUtente> findAllActive();

	@Query(value = "select new it.csi.mudeopen.mudeopensrvapi.vo.RuoloVO(ru.codice, ru.descrizione) "
			+ "from MudeDRuoloUtente ru join ru.funzioni f where f.codice = :codiceFunzione and "
			+ "(ru.dataFine is null or ru.dataFine > current_date()) and ru.dataInizio <= current_date()")
	List<RuoloVO> findRuoliPerFunzione(@Param("codiceFunzione") String codiceFunzione);

	/*
	@Query(value = "SELECT t FROM MudeRRuoloFruitore r INNER JOIN r.mudeDRuoloUtente t WHERE r.mudeDFruitore.codiceFruitore = :fruitoreId AND (t.dataFine is null OR t.dataFine > current_date()) AND t.dataInizio <= current_date()")
	List<MudeDRuoloUtente> findAllByFruitore(@Param("fruitoreId") String fruitoreId);
	*/

}