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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;

@Repository
public interface MudeDTipoDocpaRepository extends BaseDictionaryRepository<MudeDTipoDocPA, Long> {

    @Query("SELECT o FROM MudeDTipoDocPA o WHERE o.dataFine is null AND o.dataInizio <= now() ORDER BY o.descTipoDocpa ASC")
    List<MudeDTipoDocPA> findAllOrderByDescrizione();

    @Query("SELECT o FROM MudeDTipoDocPA o WHERE o.codeTipoDocpa = :codice")
    MudeDTipoDocPA findByCodice(@Param("codice")String codice);

    @Query("SELECT o FROM MudeDTipoDocPA o WHERE o.descTipoDocpa = :desc")
    Optional<MudeDTipoDocPA> findByDesc(@Param("desc")String desc);
}