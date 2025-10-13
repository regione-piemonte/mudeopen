/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;

@Repository
public interface MudeDTipoTracciatoRepository extends BaseRepository<MudeDTipoTracciato, Long> {

    List<MudeDTipoTracciato> findByCodiceAndDataFineIsNull(String codice);

    MudeDTipoTracciato findByCodiceAndVersioneAndDataFineIsNull(String codice, String versione);

	@Query(value = "SELECT DISTINCT mdtt.* FROM mudeopen_d_tipo_tracciato mdtt "
		    + "      INNER JOIN mudeopen_r_tipo_istanza_tipo_tracciato mrtitt ON mrtitt.id_tipo_tracciato = mdtt.id_tipo_tracciato AND mrtitt.data_fine  IS NULL "
		    + "      INNER JOIN mudeopen_r_tipo_istanza_fruitore mrtif ON mrtif.id_tipo_istanza = mrtitt.id_tipo_istanza AND mrtif.data_fine IS NULL "
		    + "    WHERE mdtt.data_fine IS NULL "
		    + "      AND mdtt.versione = :versione "
		    + "      AND ('' = :tipoIstanza OR mrtitt.id_tipo_istanza = :tipoIstanza) "
		    + " LIMIT 1", nativeQuery = true)
	MudeDTipoTracciato findTracciatoByVersion(@Param("versione") String versione, @Param("tipoIstanza") String tipoIstanza);
	@Query(value = "select  concat_ws('$$$', coalesce(mdtt.codice, 'null_string') , coalesce(mdtt.descrizione, 'null_string') , coalesce(mdtt.versione, 'null_string')) " +
			"from mudeopen_d_tipo_tracciato mdtt  \n" +
			"where mdtt.data_fine is null and mdtt.codice = :tipoTracciato", nativeQuery = true)
	String findTipoTracciatoByCodice( @Param("tipoTracciato") String codice);

    @Query(value = "SELECT "
	    		+ "    mdtt.id_tipo_tracciato, "
	    		+ "    mdtt.codice, "
	    		+ "    mdtt.descrizione, "
	    		+ "    mdtt.versione, "
	    		+ "    (mrit2.id_istanza_tracciato IS NOT NULL)\\:\\:varchar AS xsd_validazione, "
	    		+ "    mdtt.data_inizio, "
	    		+ "    mdtt.data_fine, "
	    		+ "    null AS loguser, "
	    		+ "    mdtt.data_modifica"
	    		+ " FROM mudeopen_d_tipo_tracciato mdtt "
	    		+ "    INNER JOIN mudeopen_r_tipo_istanza_tipo_tracciato o ON o.id_tipo_tracciato = mdtt.id_tipo_tracciato "
	    		+ "    INNER JOIN ("
	    		+ "        SELECT DISTINCT ON (id_tipo_tracciato,id_tipo_istanza) id_tipo_istanza_tipo_tracciato"
	    		+ "          FROM ("
	    		+ "            SELECT * FROM mudeopen_r_tipo_istanza_tipo_tracciato"
	    		+ "              WHERE data_inizio <= ("
	    		+ "                SELECT s.data_inizio "
	    		+ "                  FROM mudeopen_r_istanza_stato s "
	    		+ "                  WHERE s.codice_stato_istanza='DPS' "
	    		+ "                    AND s.id_istanza = :idIstanza "
	    		+ "                  ORDER BY s.data_inizio desc"
	    		+ "                  LIMIT 1 "
	    		+ "                )"
	    		+ "              ORDER BY data_inizio DESC "
	    		+ "          ) orderedTipoTracc"
	    		+ "        ) tipi_tracc_ist ON tipi_tracc_ist.id_tipo_istanza_tipo_tracciato=o.id_tipo_istanza_tipo_tracciato"
	    		+ "    INNER JOIN mudeopen_t_istanza mti ON mti.id_tipo_istanza = o.id_tipo_istanza AND mti.id_istanza = :idIstanza"
	    		+ "    LEFT JOIN mudeopen_r_istanza_tracciato mrit2 ON mrit2.id_tipo_tracciato = o.id_tipo_tracciato AND mrit2.id_istanza = mti.id_istanza"
	    		+ "  WHERE "
	    		+ "    (SELECT MAX(attiva\\:\\:varchar)\\:\\:boolean FROM mudeopen_r_tipo_istanza_tipo_tracciato WHERE id_tipo_istanza = o.id_tipo_istanza AND o.id_tipo_tracciato = id_tipo_tracciato)", nativeQuery = true)
    List<MudeDTipoTracciato> getPossibleByIdIstanza(@Param("idIstanza") Long idIstanza);
	
}