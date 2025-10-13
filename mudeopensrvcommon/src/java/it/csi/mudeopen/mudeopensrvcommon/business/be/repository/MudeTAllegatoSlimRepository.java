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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;
@Repository
public interface MudeTAllegatoSlimRepository extends BaseRepository<MudeTAllegatoSlim, Long> {

    @Query("SELECT o FROM MudeTAllegatoSlim o WHERE o.idIstanza = :idIstanza AND o.dataFine IS NULL ORDER BY o.idTipoAllegato")
    List<MudeTAllegatoSlim> findAllByIstanza(@Param("idIstanza") Long idIstanza);

    @Query("SELECT o FROM MudeTAllegatoSlim o WHERE o.idIstanza = :idIstanza AND o.idTipoAllegato=:codice AND o.dataFine IS NULL")
    List<MudeTAllegatoSlim> findAllByIstanzaAndTipo(@Param("idIstanza") Long idIstanza, @Param("codice") String codice);

    @Query("SELECT o FROM MudeTAllegatoSlim o WHERE o.fileUID = :fileUID AND o.dataFine IS NULL")
    Optional<MudeTAllegatoSlim> findByFileUID(@Param("fileUID") String fileUID);

    @Query(value = "SELECT mta.* "
    		+ "    FROM  "
    		+ "        ( "
    		+ "            ( "
    		+ "                SELECT mti2.id_istanza, tbl.idtipoallegato, (json_data #>> ARRAY['QDR_ALLEGATI', tbl.idtipoallegato])\\:\\:jsonb as jsonallegato  "
    		+ "                    FROM (select id_istanza, jsonb_object_keys(json_data->'QDR_ALLEGATI') as idtipoallegato from mudeopen_t_istanza mti ) tbl "
    		+ "                        INNER JOIN mudeopen_t_istanza mti2 ON mti2.id_istanza =tbl.id_istanza "
    		+ "                    WHERE json_data #>> ARRAY['QDR_ALLEGATI', tbl.idtipoallegato, '0', 'nodeIndex'] IS NOT NULL "
    		+ "            ) "
    		+ "            UNION  ( "
    		+ "                SELECT mti2.id_istanza, tbl.idtipoallegato, (json_data #>> ARRAY['QDR_PAGAMENTO', tbl.idtipoallegato])\\:\\:jsonb as jsonallegato  "
    		+ "                    FROM (select id_istanza, jsonb_object_keys(json_data->'QDR_PAGAMENTO') as idtipoallegato from mudeopen_t_istanza mti ) tbl "
    		+ "                        INNER JOIN mudeopen_t_istanza mti2 ON mti2.id_istanza =tbl.id_istanza "
    		+ "                    WHERE json_data #>> ARRAY['QDR_PAGAMENTO', tbl.idtipoallegato, '0', 'nodeIndex'] IS NOT NULL "
    		+ "            ) "
    		+ "        ) mti "
    		+ "        LEFT JOIN mudeopen_t_allegato mta ON mti.id_istanza=mta.id_istanza  AND mta.id_tipo_allegato=mti.idtipoallegato "
    		+ "        LEFT JOIN jsonb_array_elements(mti.jsonallegato) with ordinality allegati(obj, position) ON obj->>'nodeIndex' = mta.index_node "
    		+ "    WHERE obj->>'nodeIndex' IS NULL AND mta.id_allegati IS NOT NULL "
    		+ "        AND mti.id_istanza = :idIstanza", nativeQuery = true)
    List<MudeTAllegatoSlim> findAllAllegatiNotPresentInJsonData(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT CONCAT(mti.idtipoallegato, '~', obj->>'name', '~', allegati.position, '~' , obj\\:\\:varchar)"
    		+ "    FROM   "
    		+ "        (  "
    		+ "            (  "
    		+ "                SELECT mti2.id_istanza, tbl.idtipoallegato, (json_data #>> ARRAY['QDR_ALLEGATI', tbl.idtipoallegato])\\:\\:jsonb as jsonallegato   "
    		+ "                    FROM (select id_istanza, jsonb_object_keys(json_data->'QDR_ALLEGATI') as idtipoallegato from mudeopen_t_istanza mti ) tbl  "
    		+ "                        INNER JOIN mudeopen_t_istanza mti2 ON mti2.id_istanza =tbl.id_istanza  "
    		+ "                    WHERE json_data #>> ARRAY['QDR_ALLEGATI', tbl.idtipoallegato, '0', 'nodeIndex'] IS NOT NULL  "
    		+ "            )  "
    		+ "            UNION  (  "
    		+ "                SELECT mti2.id_istanza, tbl.idtipoallegato, (json_data #>> ARRAY['QDR_PAGAMENTO', tbl.idtipoallegato])\\:\\:jsonb as jsonallegato   "
    		+ "                    FROM (select id_istanza, jsonb_object_keys(json_data->'QDR_PAGAMENTO') as idtipoallegato from mudeopen_t_istanza mti ) tbl  "
    		+ "                        INNER JOIN mudeopen_t_istanza mti2 ON mti2.id_istanza =tbl.id_istanza  "
    		+ "                    WHERE json_data #>> ARRAY['QDR_PAGAMENTO', tbl.idtipoallegato, '0', 'nodeIndex'] IS NOT NULL  "
    		+ "            )  "
    		+ "        ) mti  "
    		+ "        INNER JOIN jsonb_array_elements(mti.jsonallegato) with ordinality allegati(obj, position) ON obj->>'nodeIndex' IS NOT NULL "
    		+ "        LEFT JOIN mudeopen_t_allegato mta ON mta.id_allegato = (obj->>'id')\\:\\:int8 "
    		+ "    WHERE mta.id_allegato is NULL "
    		+ "          AND mti.id_istanza = :idIstanza", nativeQuery = true)
    List<String> findAllAllegatiNotPresentInTallegatoFromJsonData(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT mta.*  "
    		+ "    FROM mudeopen_t_pratica_cosmo pc "
    		+ "        INNER JOIN mudeopen_t_pratica_cosmo mtpc ON pc.numero_pratica=mtpc.numero_pratica AND mtpc.trasmiss_doc=true"
    		+ "        INNER JOIN mudeopen_t_istanza mti ON mtpc.id_istanza=mti.id_istanza AND mti.id_tipo_istanza != 'DENUNCIA-SISMICA' "
    		+ "        INNER JOIN mudeopen_t_allegato mta ON mta.id_istanza=mti.id_istanza "
    		+ "    WHERE pc.id_istanza = :idIstanza "
    		+ "    ORDER BY mta.id_tipo_allegato, mta.data_inizio DESC ", nativeQuery = true)
    List<MudeTAllegatoSlim> findAllAllegatiForPraticaCosmoButDS(@Param("idIstanza") Long idIstanza);

     
}