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
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCellula;

@Repository
public interface MudeopenRLocCellulaRepository extends BaseRepository<MudeopenRLocCellula, Integer> {

    @Modifying
    @Query("delete from MudeopenRLocCatasto t where t.idIstanza = ?1")
    void removeByIdIstanza(Long idIstanza);

    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocCellula t"
    		+ "  WHERE  "
    		+ "    t.idGeoriferimento = ?1 ")
    List<MudeopenRLocCellula> findByIdGeoriferimento(Long idGeoriferimento);
    
    @Modifying
    @Query(value = "delete from MudeopenRLocCatasto t where t.idGeoriferimento IN (:ids) ")
    void deleteByIdGeoriferimentoList(@Param("ids") List<Long> georiferimentiIdsOld);

    @Query(value = "SELECT mrlf.cod_fabbricato FROM  mudeopen_r_loc_cellula mrlc "
    		+ "        INNER JOIN mudeopen_r_loc_fabbricato mrlf ON mrlc.id_cellula = mrlf.id_cellula "
    		+ "    WHERE mrlc.id_georiferimento = ?1 ", nativeQuery = true)
    String getCodFabbricatoByIdGeoriferimento(Long idGeoriferimento);
    
}