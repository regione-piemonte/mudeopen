/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDWfStato;

@Repository
public interface MudeDWfStatiRepository extends BaseRepository<MudeDWfStato, Long> {
    @Query(value = "SELECT DISTINCT ON (codice_stato_start, codice_stato_end) * "
		    		+ "  FROM mudeopen_d_wf_stato mdws "
		    		+ "  WHERE codice_stato_start = :codiceStart "
		    		+ "      AND data_fine is null "
		    		+ "      AND id_tipo_istanza = :tipoIstanza "
		    		+ "      AND (specie_pratica IS NULL OR specie_pratica = :speciePratica)"
		    		+ "  ORDER BY codice_stato_start, codice_stato_end, specie_pratica DESC NULLS LAST", nativeQuery = true)
    List<MudeDWfStato> findByCodiceStatoStartAndTipoIstanza(@Param("codiceStart") String codiceStart, @Param("tipoIstanza") String tipoIstanza, @Param("speciePratica") String speciePratica);

    @Query(value = "SELECT DISTINCT ON (codice_stato_start, codice_stato_end) * "
			    		+ "  FROM mudeopen_d_wf_stato mdws "
			    		+ "  WHERE codice_stato_start = :codiceStart "
			    		+ "      AND codice_stato_end = :codiceEnd "
			    		+ "      AND data_fine is null "
			    		+ "      AND id_tipo_istanza = :tipoIstanza "
			    		+ "      AND (specie_pratica IS NULL OR specie_pratica = :speciePratica)"
			    		+ "  ORDER BY codice_stato_start, codice_stato_end, specie_pratica DESC NULLS LAST", nativeQuery = true)
    MudeDWfStato findByCodiceStatoStartAndCodiceStatoEndAndTipoIstanza(@Param("codiceStart") String codiceStart, @Param("codiceEnd") String codiceEnd, @Param("tipoIstanza") String tipoIstanza, @Param("speciePratica") String speciePratica);

    @Query(value = "SELECT DISTINCT ON (codice_stato_start, codice_stato_end) * "
			    		+ "  FROM mudeopen_d_wf_stato mdws "
			    		+ "  WHERE codice_stato_start = :codiceStart "
			    		+ "      AND codice_stato_end = :codiceEnd "
			    		+ "      AND id_tipo_istanza = :tipoIstanza "
			    		+ "      AND (specie_pratica IS NULL OR specie_pratica = :speciePratica)"
			    		+ "  ORDER BY codice_stato_start, codice_stato_end, specie_pratica DESC NULLS LAST", nativeQuery = true)
    MudeDWfStato findAllByCodiceStatoStartAndCodiceStatoEndAndTipoIstanza(@Param("codiceStart") String codiceStart, @Param("codiceEnd") String codiceEnd, @Param("tipoIstanza") String tipoIstanza, @Param("speciePratica") String speciePratica);

    @Query(value = "SELECT DISTINCT ON (codice_stato_start, codice_stato_end) * "
		    		+ " FROM mudeopen_d_wf_stato "
		    		+ " WHERE data_fine IS NULL "
		    		+ "  AND codice_stato_end IN ('RPA', 'APA', 'PRC', 'DPS')"
		    		+ "  AND codice_stato_start = :codiceStart", nativeQuery = true)
    List<MudeDWfStato> findDistinctByCodiceStatoStartAndDataFineNull(@Param("codiceStart") String codiceStart);

    @Query(value = "SELECT mdws.* "
    		+ "  FROM mudeopen_t_istanza mti "
    		+ "    INNER JOIN mudeopen_r_istanza_stato mris ON mti.id_istanza = mris.id_istanza AND mris.data_fine is null "
    		+ "    INNER JOIN mudeopen_d_wf_stato mdws ON mti.id_tipo_istanza = mdws.id_tipo_istanza AND mdws.codice_stato_start = mris.codice_stato_istanza "
    		+ "  WHERE mti.id_istanza = :idIstanza "
    		+ "    AND id_wf_stato = ("
    		+ "        SELECT DISTINCT ON (codice_stato_start, codice_stato_end) id_wf_stato "
    		+ "          FROM mudeopen_d_wf_stato mdws "
    		+ "          WHERE " // -- data_fine is null AND 
    		+ "            codice_stato_start = mris.codice_stato_istanza "
    		+ "            AND codice_stato_end = :statoEnd "
    		+ "            AND id_tipo_istanza = mti.id_tipo_istanza "
    		+ "            AND (specie_pratica IS NULL OR specie_pratica = mti.id_specie_pratica) "
    		+ "          ORDER BY codice_stato_start, codice_stato_end, specie_pratica DESC NULLS LAST)", nativeQuery = true)
    MudeDWfStato findAllByIdIstanzaAndStatoEnd(@Param("idIstanza") Long idIstanza, @Param("statoEnd") String statoEnd);

    
}