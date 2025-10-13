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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaCosmo;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.StatiProcessoCosmoEnum;

@Repository
public interface MudeTPraticaCosmoRepository extends BaseRepository<MudeTPraticaCosmo, Long> {

    MudeTPraticaCosmo findByIdIstanza(@Param("idIstanza") Long idIstanza);

	@Query(value = "SELECT * "
					+ " FROM mudeopen_t_pratica_cosmo mtpc "
		    		+ "    INNER JOIN mudeopen_t_istanze_ext mtie ON mtpc.id_istanza = mtie.id_istanza AND dps_script_stato = 'OK'"
					+ " WHERE (retries IS NULL OR retries < :maxRetries) "
					+ "    AND (codice_stato_cosmo IS NULL "
					+ "        OR codice_stato_cosmo NOT IN (:statesToExclude))", nativeQuery = true)
    List<MudeTPraticaCosmo> findAllByCodiceStatoCosmoIsNullOrNotInCodiceStatoCosmoAndRetriesLessThan(@Param("statesToExclude") String[] statesToExclude, @Param("maxRetries") int maxRetries);
	
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE mudeopen_t_pratica_cosmo SET retries = COALESCE(retries, 0) + 1 WHERE id_pratica_cosmo = :idPraticaCosmo", nativeQuery = true)
    void increaseRetryCounter(@Param("idPraticaCosmo") Long idPraticaCosmo);
    
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE mudeopen_t_pratica_cosmo SET codice_stato_cosmo='IN_CODA', cc_selezionato=true WHERE id_istanza = :idIstanza AND (codice_stato_cosmo = 'IN_PROCESSO' OR codice_stato_cosmo = 'SEGNALATA')", nativeQuery = true)
    void markAsToBeChecked(@Param("idIstanza") Long idIstanza);
    
    /*
		CREATE OR REPLACE FUNCTION func_create_numero_pratica_cosmo_seq(p_suffix varchar) RETURNS varchar AS
		$$declare
		  v_str varchar;
		BEGIN
		    v_str := 'mudeopen_numero_pratica_cosmo_' || p_suffix || '_seq';
		    EXECUTE  'CREATE SEQUENCE ' || v_str || ';';
		    RETURN v_str;
		EXCEPTION 
		  WHEN OTHERS THEN
		    RETURN v_str;
		END$$
		LANGUAGE plpgsql;
     * 
	@Modifying(clearAutomatically = true)
	@Query(value = "CREATE OR REPLACE FUNCTION pg_temp.create_numero_pratica_cosmo_seq(p_suffix varchar) RETURNS varchar AS\r\n"
			+ "$$declare\r\n"
			+ "  v_str varchar;\r\n"
			+ "BEGIN\r\n"
			+ "    v_str \\:= 'mudeopen_numero_pratica_cosmo_' || p_suffix || '_seq';\r\n"
			+ "    EXECUTE  'CREATE SEQUENCE ' || v_str || ';';\r\n"
			+ "    RETURN v_str;\r\n"
			+ "EXCEPTION \r\n"
			+ "  WHEN OTHERS THEN\r\n"
			+ "    GET STACKED DIAGNOSTICS v_str = PG_EXCEPTION_CONTEXT; RAISE NOTICE 'EXCEPTION: %', v_str;\r\n"
			+ "    RETURN v_str;\r\n"
			+ "END$$\r\n"
			+ "LANGUAGE plpgsql;", nativeQuery = true)
	void createTemporaryExecuteFunction();
 	*/
    
	@Query(value = "SELECT func_create_numero_pratica_cosmo_seq(:suffix)", nativeQuery = true)
	String createCosmoSeq(@Param("suffix") String suffix);

    @Query(value = "select nextval(:seqToUse)", nativeQuery = true)
    Long getNextNumeroPraticaCOSMO(@Param("seqToUse") String seqToUse);
 
	@Query(value = "SELECT CAST(json_assegnatari AS VARCHAR) "
						+ " FROM mudeopen_t_pratica_cosmo "
						+ " WHERE numero_pratica = :numeroPratica AND json_assegnatari IS NOT NULL "
						+ " ORDER BY data_modifica desc "
						+ " LIMIT 1", nativeQuery = true)
    String getJsonAssegnatari(@Param("numeroPratica") String numeroPratica);
	
}