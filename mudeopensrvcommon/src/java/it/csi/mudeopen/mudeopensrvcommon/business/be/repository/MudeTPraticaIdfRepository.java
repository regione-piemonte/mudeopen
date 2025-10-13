/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaIdf;

@Repository
public interface MudeTPraticaIdfRepository extends BaseRepository<MudeTPraticaIdf, Long> {

    MudeTPraticaIdf findByIdIstanza(@Param("idIstanza") Long idIstanza);

	@Query(value = "SELECT * "
					+ " FROM mudeopen_t_pratica_idf mtpc "
		    		+ "    INNER JOIN mudeopen_t_istanze_ext mtie ON mtpc.id_istanza = mtie.id_istanza AND dps_script_stato = 'OK'"
					+ " WHERE (retries IS NULL OR retries < :maxRetries) "
					+ "    AND (codice_stato_idf IS NULL "
					+ "        OR codice_stato_idf NOT IN (:statesToExclude))", nativeQuery = true)
    List<MudeTPraticaIdf> findAllByCodiceStatoIdfIsNullOrNotInCodiceStatoIdfAndRetriesLessThan(@Param("statesToExclude") String[] statesToExclude, @Param("maxRetries") int maxRetries);
	
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE mudeopen_t_pratica_idf SET retries = COALESCE(retries, 0) + 1 WHERE id_pratica_idf = :idPraticaIdf", nativeQuery = true)
    void increaseRetryCounter(@Param("idPraticaIdf") Long idPraticaIdf);
    
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE mudeopen_t_pratica_idf SET codice_stato_idf='IN_CODA', cc_selezionato=true WHERE id_istanza = :idIstanza AND (codice_stato_idf = 'IN_PROCESSO' OR codice_stato_idf = 'SEGNALATA')", nativeQuery = true)
    void markAsToBeChecked(@Param("idIstanza") Long idIstanza);
    
}