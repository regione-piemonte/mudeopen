/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mude c regione repository.
 */
@Repository
public interface MudeopenTIstanzaRepository extends BaseRepository<MudeTIstanzaSLIM, Long> {
/*	
	SELECT id_istanza  
		FROM mudeopen_t_istanza mti 
		WHERE id_indirizzo  is null 
			AND id_fonte = 'FO' 
			AND json_data->'TAB_LOCAL_1' IS NOT NULL 
			AND json_data->'QDR_LOCALIZZAZIONE'->'_COMPILED' = '"true"' 
*/
	
    @Query(value = "SELECT id_istanza  \n"
    		+ "    FROM mudeopen_t_istanza mti \n"
    		+ "    WHERE id_indirizzo  is null \n"
    		+ "      AND id_fonte = 'FO' \n"
    		+ "      AND json_data->'TAB_LOCAL_1' IS NOT NULL \n"
    		+ "			AND json_data->'QDR_LOCALIZZAZIONE'->'_COMPILED' = '\"true\"'", nativeQuery = true)
    List<BigInteger> findMissingAddressedInstances();

	@Query(value = "SELECT codice_istanza  \n"
			+ "    FROM mudeopen_t_istanza mti \n"
			+ "    WHERE id_istanza = :idIstanza", nativeQuery = true)
	String findCodiceIstanzaByIdIstanza(@Param("idIstanza") Long idIstanza);
}