/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegato;
@Repository
public interface MudeTAllegatoRepository extends BaseRepository<MudeTAllegato, Long> {

	@Query(value = "SELECT mta.* "
				+ "    FROM mudeopen_t_allegato mta "
				+ "        INNER JOIN mudeopen_t_istanza mti ON mti.id_istanza=mta.id_istanza "
				+ "        LEFT JOIN mudeopen_r_template_tipo_allegato mrtta ON mti.id_template=mrtta.id_template AND mrtta.id_tipo_allegato=mta.id_tipo_allegato "
				+ "    WHERE mta.id_istanza = :idIstanza AND mta.data_fine IS NULL "
				+ "    ORDER BY mrtta.ordinamento ASC NULLS LAST", nativeQuery = true)    
    List<MudeTAllegato> findAllByIstanza(@Param("idIstanza") Long idIstanza);

    @Query("SELECT o FROM MudeTAllegato o WHERE o.id = :id AND o.dataFine IS NULL")
    Optional<MudeTAllegato> findById(@Param("id") Long id);

    @Query("SELECT o FROM MudeTAllegato o WHERE o.id = :id and o.user.idUser = :idUser AND o.dataFine IS NULL")
    Optional<MudeTAllegato> findByIdAndUser(@Param("id") Long id, @Param("idUser") Long idUser);

    @Query("SELECT o.fileContent FROM MudeTAllegato o WHERE o.id = :id AND o.dataFine IS NULL")
    byte[] findFileByIdAllegato(@Param("id") Long id);

    @Query("SELECT o FROM MudeTAllegato o WHERE o.istanza.id = :idIstanza AND o.nomeFileAllegato = :filename AND o.dataFine IS NULL")
    Optional<MudeTAllegato> findByIdIstanzaAndFilename(@Param("idIstanza") Long idIstanza, @Param("filename") String filename);

    @Query(value = " SELECT mta.index_node "
            + "  FROM  mudeopen_t_allegato mta "
            + "  WHERE mta.id_istanza = :idIstanza "
            + "    AND mta.nome_file_allegato = :fileName"
            + "    AND mta.data_fine IS NULL"
            + "  LIMIT 1", nativeQuery = true)
    String findFileUidByIdIstanzaAndFilename(@Param("idIstanza") Long idIstanza, @Param("fileName") String filename);
    @Query("SELECT o FROM MudeTAllegato o WHERE o.fileUID = :fileUID")
    Optional<MudeTAllegato> findByFileUID(@Param("fileUID") String fileUID);

    @Modifying
    @Query("DELETE FROM MudeTAllegato WHERE istanza.id = :idIstanza")
    void deleteByIstanza(@Param("idIstanza") Long idIstanza);

    List<MudeTAllegato> findAllByIstanza_IdAndTipoAllegato_CodiceAndDataFineIsNull(Long idIstanza, String tipoAllegato);

    @Query(value = "SELECT COALESCE(MAX(id_allegato), 0) > 0 FROM mudeopen_t_allegato mta "
    		+ "            INNER JOIN mudeopen_r_istanza_stato mris ON mta.id_istanza = mris.id_istanza AND mris.data_fine IS NULL AND (mris.codice_stato_istanza = 'BZZ' OR mris.codice_stato_istanza = 'RPA') "
    		+ "WHERE id_allegato = :idAllegato"
    		+ "   AND mta.data_fine IS NULL", nativeQuery = true)
    Boolean canDeleteAttach(@Param("idAllegato") Long idAllegato);
    
}