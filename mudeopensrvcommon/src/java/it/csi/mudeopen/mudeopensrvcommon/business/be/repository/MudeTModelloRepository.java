/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTModello;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudeTModelloRepository extends BaseRepository<MudeTModello, Long> {

    @Query("SELECT o FROM MudeTModello o WHERE o.id = :id")
    Optional<MudeTModello> findByIdModello(@Param("id") Long id);

    @Query("SELECT o FROM MudeTModello o WHERE o.denominazione = :denominazione")
    Optional<MudeTModello> findByDenominazione(@Param("denominazione") String denominazione);

    @Query("SELECT o FROM MudeTModello o")
    List<MudeTModello> findAllModelli();

    @Modifying
    @Query("DELETE FROM MudeTModello o WHERE o.id = :id AND o.id NOT IN (SELECT q.modello.id FROM MudeDQuadro q WHERE q.modello.id IS NOT NULL)")
    void deleteByIdModelloIfNotInQuadro(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM MudeTModello o WHERE o.id = :id "
    		+ "    AND o.id NOT IN (SELECT t.modello.id FROM MudeDTemplate t WHERE t.modello.id IS NOT NULL)"
    		+ "    AND o.id NOT IN (SELECT t.modelloIntestazione.id FROM MudeDTemplate t WHERE t.modelloIntestazione.id IS NOT NULL)")
    void deleteByIdModelloIfNotInTemplate(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM MudeTModello o WHERE o.id = :id AND o.id NOT IN (SELECT a.modello.id FROM MudeRTemplateTipoAllegato a WHERE a.modello.id IS NOT NULL)")
    void deleteByIdModelloIfNotInTipoAllegato(@Param("id") Long id);

}