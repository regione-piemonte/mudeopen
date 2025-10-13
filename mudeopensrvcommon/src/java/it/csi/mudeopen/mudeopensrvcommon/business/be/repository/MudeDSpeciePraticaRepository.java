/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;

@Repository
public interface MudeDSpeciePraticaRepository extends BaseDictionaryRepository<MudeDSpeciePratica, String> {

	@Query(value = "SELECT x.* FROM mudeopen_d_specie_pratica x WHERE x.codice = :speciePratica AND data_fine IS NULL ", nativeQuery = true)
	MudeDSpeciePratica findByCodice(@Param("speciePratica") String speciePratica);
	
	@Query(value = "SELECT DISTINCT x.* "
				+ "    FROM mudeopen_r_tipo_istanza_specie_pratica x " 
				+ "    WHERE x.id_specie_pratica = :speciePratica AND x.id_tipo_istanza = :tipoIstanza AND abilitato = true"
				+ "  LIMIT 1", nativeQuery = true)
	MudeDSpeciePratica findBySpeciePraticaAndTipoIstanza(@Param("speciePratica") String speciePratica, @Param("tipoIstanza") String tipoIstanza);

	@Query(value = "SELECT descrizione_estesa FROM mudeopen_d_specie_pratica WHERE codice = :speciePratica AND data_fine IS NULL ", nativeQuery = true)
	String getDescrizioneByCodice(@Param("speciePratica") String speciePratica);

	@Query(value = "SELECT descrizione FROM mudeopen_d_specie_pratica WHERE codice = :speciePratica", nativeQuery = true)
	String getDescrizioneBreveByCodice(@Param("speciePratica") String speciePratica);
	
    @Query(value = "SELECT mdsp.* "
    		+ "    FROM mudeopen_t_istanza mti "
    		+ "        INNER JOIN mudeopen_d_specie_pratica mdsp ON mdsp.codice = mti.json_data->'TAB_QUALIF_1'->>'chk_comunica' "
    		+ "    WHERE mti.id_istanza = :idIstanza", nativeQuery = true)
    MudeDSpeciePratica retrieveSpeciePraticaFromIstanza(@Param("idIstanza") Long idIstanza);
	
}