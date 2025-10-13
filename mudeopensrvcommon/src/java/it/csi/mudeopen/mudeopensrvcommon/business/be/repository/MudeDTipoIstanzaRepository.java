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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;

@Repository
@Transactional
public interface MudeDTipoIstanzaRepository extends BaseDictionaryRepository<MudeDTipoIstanza, String> {
    List<MudeDTipoIstanza> findByCodiceInOrderByDescrizioneEstesa(List<String> codici );

    @Query("SELECT o.cambioIntestatario FROM MudeDTipoIstanza o WHERE o.codice = ?1")
    Boolean findCambioIntestatarioByTipoIstanza(String codiceTipoIstanza);

	@Query(value = "SELECT descrizione_estesa FROM mudeopen_d_tipo_istanza WHERE codice = :tipoIstanza ", nativeQuery = true)
	String getDescrizioneByCodice(@Param("tipoIstanza") String tipoIstanza);

	@Query(value = "SELECT * FROM mudeopen_d_tipo_istanza ORDER BY descrizione_estesa", nativeQuery = true)
    List<MudeDTipoIstanza> findAllOrdered();
	
	default String getTipiIstanzeBaseQuery(Long idFascicolo, String select, String where) {
		
		/*
			CREATE OR REPLACE FUNCTION pg_temp.f_inc(int)
			  RETURNS int AS 'SELECT $1 + 1' LANGUAGE sql IMMUTABLE;
			SELECT pg_temp.f_inc(42);
			
			
			DO $$ DECLARE _cursor CONSTANT refcursor := '_cursor';
			BEGIN
			  OPEN _cursor FOR EXECUTE 'SELECT true';
			END $$;
			FETCH ALL FROM _cursor;
		 */

		return select +
				"  FROM mudeopen_t_istanza mti\n" 
				+ "    LEFT JOIN mudeopen_d_specie_pratica mdsp ON (mti.id_specie_pratica = mdsp.codice AND mdsp.data_fine IS NULL) \n" 
				+ "    LEFT JOIN mudeopen_t_istanza mtiRif ON (mti.id_istanza_riferimento = mtiRif.id_istanza) \n" 
				+ "    INNER JOIN mudeopen_r_istanza_stato mris ON (mti.id_istanza=mris.id_istanza AND mris.data_fine IS NULL)\n" 
				+ "    INNER JOIN mudeopen_d_stato_istanza mdsi ON (mdsi.codice=mris.codice_stato_istanza AND mdsi.livello>=100 AND mdsi.data_fine IS NULL)\n" 
				+ "    INNER JOIN mudeopen_t_fascicolo mtf ON (mtf.id_fascicolo=mti.id_fascicolo)\n" 
				+ "    INNER JOIN mudeopen_r_tipo_istanza mrti ON (mrti.cod_tipo_istanza_padre=mti.id_tipo_istanza OR mrti.cod_tipo_istanza_padre IS NULL)\n" 
//basic version with no func:		+ "      AND (CONCAT(mrti.campo_json_padre, '')='' OR CONCAT(mti.json_data, '') like CONCAT('%', mrti.campo_json_padre,'%'))\n"
				+ "    INNER JOIN fnc_checkExpression(mti.*, mdsp.*, row(mdsp.*), mtiRif.*, row(mtiRif.*), mrti.campo_json_padre)  AS func(key) ON func.key = true\n" 
	            // find possible instances linked to "cod_tipo_istanza_padre2\n"
				+ "    LEFT JOIN (\n" 
				+ "      SELECT mti2.id_tipo_istanza \n" 
				+ "        FROM mudeopen_t_istanza mti2\n" 
				+ "          INNER JOIN mudeopen_r_istanza_stato mris2 ON (mti2.id_istanza=mris2.id_istanza AND mris2.data_fine IS NULL)\n" 
				+ "          INNER JOIN mudeopen_d_stato_istanza mdsi2 ON (mdsi2.codice=mris2.codice_stato_istanza AND mdsi2.livello>=100 AND mdsi2.data_fine IS NULL)\n" 
				+ "          INNER JOIN mudeopen_r_tipo_istanza mrti2 ON mrti2.cod_tipo_istanza_padre2=mti2.id_tipo_istanza\n" 
				+ "				WHERE mti2.id_fascicolo = " + idFascicolo + "\n"
				+ "          AND mti2.id_tipo_istanza = mrti2.cod_tipo_istanza_padre2\n" 
				+ "    ) istPadre2 ON mrti.cod_tipo_istanza_padre2=istPadre2.id_tipo_istanza\n"
	            // esclude instances already linked
				+ "    LEFT JOIN (\n" 
				+ "      SELECT id_tipo_istanza, id_istanza_riferimento, COUNT(id_istanza_riferimento) as associate\n" 
				+ "        FROM mudeopen_t_istanza\n" 
				+ "				WHERE id_fascicolo = " + idFascicolo  + "\n"
				+ "        GROUP BY id_istanza_riferimento, id_tipo_istanza\n" 
				+ "    ) istFiglie ON (istFiglie.id_istanza_riferimento=mti.id_istanza AND istFiglie.id_tipo_istanza=mrti.cod_tipo_istanza_figlia)\n" 
				+ "  WHERE (mrti.cod_tipo_istanza_padre2 IS NULL OR istPadre2.id_tipo_istanza IS NOT NULL) \n" 
				+ "    AND (mrti.ripetibile IS NULL OR (COALESCE(istFiglie.associate, 0)<mrti.ripetibile))\n"
				+ where;
	}
	
	/*
	@Modifying(clearAutomatically = true)
	@Query(value = "CREATE OR REPLACE FUNCTION pg_temp.f_checkExpression(istanza RECORD, specie RECORD, specieChk RECORD, istanza_rif RECORD, istanzaRifChk RECORD, exprString varchar) RETURNS SETOF bool AS\n"
			+ "$$declare\n"
			+ "  recSpecie mudeopen_d_specie_pratica%ROWTYPE;\n"
			+ "  istanzaRif mudeopen_t_istanza%ROWTYPE;\n"
			+ "  res bool;\n"
			+ "  v_error_stack text;\n"
			+ "BEGIN\n"
			+ "  if specieChk is null then\n"
			+ "    SELECT * INTO recSpecie FROM mudeopen_d_specie_pratica WHERE codice = '';\n"
			+ "  else\n"
			+ "    recSpecie \\:= specie;\n"
			+ "  end if;\n"
			+ "  if istanzaRifChk is null then\n"
			+ "    SELECT * INTO istanzaRif FROM mudeopen_t_istanza WHERE id_istanza = -1;\n"
			+ "  else\n"
			+ "    istanzaRif \\:= istanza_rif;\n"
			+ "  end if;\n"
			+ "	EXECUTE  'SELECT ''' || COALESCE(REPLACE(exprString, '''', ''), '') || ''' = '''' OR ' || COALESCE(REPLACE(REPLACE(REPLACE(exprString, 'istanza_rif.', '$3.'), 'specie.', '$2.'), 'istanza.', '$1.'), 'false') INTO res USING istanza, recSpecie, istanzaRif, exprString;\n"
			+ "  IF res IS NULL THEN\n"
			+ "      res \\:= false;\n"
			+ "  END IF;\n"
//			+ "	RAISE NOTICE '%:% SELECT ''%'' = '''' OR %', istanza.id_istanza, res, COALESCE(REPLACE(exprString, '''', ''), ''), COALESCE(REPLACE(REPLACE(REGEXP_REPLACE(exprString, 'json_data(.*?)=(.*?)( OR | AND |$)+', 'REPLACE(($1.json_data\\\\\\\\1)::varchar, ''\\\\\\\"'', '''') = \\\\\\\\2 \\\\\\\\3', 'g'), 'specie.', '$2.'), 'istanza.', '$1.'), 'false');\n"
			+ "  RETURN QUERY EXECUTE  'SELECT ' || res;\n"
			+ "EXCEPTION \n"
			+ "  WHEN OTHERS THEN\n"
//			+ "    GET STACKED DIAGNOSTICS v_error_stack = PG_EXCEPTION_CONTEXT; RAISE NOTICE '% EXCEPTION: %', istanza.id_istanza, v_error_stack;\n"
			+ "    RETURN QUERY EXECUTE  'SELECT FALSE';\n"
			+ "END$$\n"
			+ "LANGUAGE plpgsql IMMUTABLE\n"
			+ "COST 100;", nativeQuery = true)
	void createTemporaryExecuteFunction();
	*/
	
    @Query(value = "SELECT * FROM mudeopen_d_tipo_istanza mdti \n"
    		+ "      INNER JOIN mudeopen_t_istanza mti ON mdti.codice = mti.id_tipo_istanza \n"
    		+ "    WHERE mdti.data_fine IS NULL AND id_istanza = :idIstanza"
    		+ "    ORDER BY descrizione_estesa", nativeQuery = true)
    MudeDTipoIstanza recuperaTipologieIstanze(@Param("idIstanza") Long idIstanza);
}