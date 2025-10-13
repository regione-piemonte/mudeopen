/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;

@Repository
public interface MudeCProprietaRepository extends BaseRepository<MudeCProprieta, Integer> {

    @Query("SELECT o FROM MudeCProprieta o WHERE UPPER(o.nome) = UPPER(:nome)")
    Optional<MudeCProprieta> findByName(@Param("nome") String nome);
	
    @Query(value = "SELECT COALESCE(MAX(valore), :defaultValue) FROM mudeopen_c_proprieta WHERE UPPER(nome) = UPPER(:nome) AND (data_fine IS NULL OR data_fine > NOW()) AND (data_inizio <= NOW())", nativeQuery = true)
    String getValueByName(@Param("nome") String nome, @Param("defaultValue") String defaultValue);

    @Query(value = "SELECT COALESCE(valore, :defaultValue) FROM (\n"
    		+ "    (SELECT mcp .* \n"
    		+ "      FROM mudeopen_c_proprieta mcp \n"
    		+ "        INNER JOIN mudeopen_t_istanza mti ON (mcp.id_specie_pratica ilike '%'||mti.id_specie_pratica||'%' AND mti.id_istanza = :idIstanza) \n"
    		+ "      WHERE UPPER(nome) = UPPER(:nome) \n"
    		+ "        AND (mcp.data_fine IS NULL OR mcp.data_fine > NOW()) AND (mcp.data_inizio <= NOW()))\n"
    		+ "    UNION\n"
    		+ "    (SELECT * \n"
    		+ "      FROM mudeopen_c_proprieta \n"
    		+ "      WHERE UPPER(nome) = UPPER(:nome) AND id_specie_pratica IS NULL\n"
    		+ "        AND (data_fine IS NULL OR data_fine > NOW()) AND (data_inizio <= NOW()))\n"
    		+ "    UNION\n"
    		+ "    (SELECT NULL AS id_proprieta, NULL AS  nome, NULL AS  valore, NULL AS  note, NULL AS  visibilita, NULL AS  loguser, NULL AS  data_inizio, NULL AS  data_modifica, NULL AS  data_fine, NULL AS id_specie_pratica)\n"
    		+ "    ORDER BY id_specie_pratica NULLS LAST, data_inizio NULLS LAST \n"
    		+ "    LIMIT 1) tbl", nativeQuery = true)
    String getValueByNameAndInstanceSP(@Param("nome") String nome, @Param("idIstanza") Long idIstanza, @Param("defaultValue") String defaultValue);

    @Query(value = "SELECT COALESCE(valore, :defaultValue) FROM (\n"
    		+ "    (SELECT mcp .* \n"
    		+ "      FROM mudeopen_c_proprieta mcp \n"
    		+ "        INNER JOIN mudeopen_t_istanza mti ON (mcp.id_specie_pratica ilike '%'||mti.id_specie_pratica||'%' AND mti.id_istanza = :idIstanza) \n"
    		+ "      WHERE UPPER(nome) = UPPER(:nome) \n"
    		+ "        AND (mcp.data_fine IS NULL OR mcp.data_fine > NOW()) AND (mcp.data_inizio <= NOW())\n"
    		+ "        AND (:visibilita='' OR visibilita ~* :visibilita))\n"
    		+ "    UNION\n"
    		+ "    (SELECT * \n"
    		+ "      FROM mudeopen_c_proprieta \n"
    		+ "      WHERE UPPER(nome) = UPPER(:nome) AND id_specie_pratica IS NULL\n"
    		+ "        AND (data_fine IS NULL OR data_fine > NOW()) AND (data_inizio <= NOW())\n"
    		+ "        AND (:visibilita='' OR visibilita ~* :visibilita))\n"
    		+ "    UNION\n"
    		+ "    (SELECT NULL AS id_proprieta, NULL AS  nome, NULL AS  valore, NULL AS  note, NULL AS  visibilita, NULL AS  loguser, NULL AS  data_inizio, NULL AS  data_modifica, NULL AS  data_fine, NULL AS id_specie_pratica)\n"
    		+ "    ORDER BY id_specie_pratica NULLS LAST, data_inizio NULLS LAST \n"
    		+ "    LIMIT 1) tbl", nativeQuery = true)
    String getValueByNameAndInstanceVisibility(@Param("nome") String nome, @Param("idIstanza") Long idIstanza, @Param("visibilita") String visibilita, @Param("defaultValue") String defaultValue);

    @Query(value = "SELECT * FROM mudeopen_c_proprieta WHERE visibilita ilike CONCAT('%', :visibilita, '%') AND (data_fine IS NULL OR data_fine > NOW()) AND (data_inizio <= NOW())", nativeQuery = true)
    List<MudeCProprieta> getAllByVisibilita(@Param("visibilita") String visibilita);

    @Query(value = "SELECT COALESCE(array_to_string(array_agg(valore), ','), :defaultValue)"
    		+ "   FROM mudeopen_c_proprieta p, mudeopen_r_utente_ruolo r "
    		+ "   WHERE nome = :propertyName AND id_utente = :idUser "
    		+ "      AND visibilita IS NOT NULL "
    		+ "      AND visibilita ~ CONCAT('\\y',codice_ruolo_utente) "
    		+ "      AND (p.data_fine IS NULL OR p.data_fine > NOW()) AND (p.data_inizio <= NOW()) "
    		+ "      AND (r.data_fine IS NULL OR r.data_fine > NOW()) AND (r.data_inizio <= NOW())", nativeQuery = true)
    String retrieveByUserRoles(@Param("propertyName") String propertyName, @Param("idUser") Long idUser, @Param("defaultValue") String defaultValue);
    
}