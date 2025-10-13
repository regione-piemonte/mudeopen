/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocGeoriferim;

@Repository
public interface MudeopenRLocGeoriferimRepository extends BaseRepository<MudeopenRLocGeoriferim, Long> {

    @Modifying
    @Query("delete from MudeopenRLocGeoriferim t where t.idIstanza = ?1")
    void deleteByIdIstanza(Long idIstanza);

    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocGeoriferim t "
    		+ "  WHERE  "
    		+ "    t.idIstanza = ?1 ")
        List<MudeopenRLocGeoriferim> findByIdIstanza(Long idIstanza);

    @Query(value = "SELECT mrlg.* "
    		+ "    FROM mudeopen_r_fascicolo_indirizzo mrfi "
    		+ "        INNER JOIN mudeopen_r_loc_georiferim mrlg ON mrfi.id_istanza_rif=mrlg.id_istanza "
    		+ "    WHERE mrfi.id_fascicolo = ?1 ", nativeQuery = true)
        List<MudeopenRLocGeoriferim> findByIdIstanzaInFascicolo(Long idFascicolo);

}
