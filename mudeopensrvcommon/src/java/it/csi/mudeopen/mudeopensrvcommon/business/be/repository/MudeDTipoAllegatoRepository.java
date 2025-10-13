/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudeDTipoAllegatoRepository extends BaseDictionaryRepository<MudeDTipoAllegato, String> {

    @Query("SELECT o FROM MudeDTipoAllegato o WHERE o.dataFine is null AND o.dataInizio <= now() ORDER BY o.descrizione ASC")
    List<MudeDTipoAllegato> findAllOrderByDescrizione();

    @Query("SELECT o FROM MudeDTipoAllegato o WHERE o.codice = :codice AND o.subCodeTipoAllegato = :subCodeTipoAllegato")
    Optional<MudeDTipoAllegato> findByCodiceAndSubCodeTipoAllegato(@Param("codice")String codice, @Param("subCodeTipoAllegato") String subCodeTipoAllegato);

    @Query("SELECT o FROM MudeDTipoAllegato o WHERE o.dataFine is null AND o.dataInizio <= now() AND o.categoriaAllegato.codice = :categoriaAllegato ORDER BY o.descrizione ASC")
    List<MudeDTipoAllegato> findAllByCategoriaAllegato(@Param("categoriaAllegato")String codiceCategoriaAllegato);

    @Query("SELECT o FROM MudeDTipoAllegato o WHERE o.codice = :codice order by o.dataFine nulls first limit 1")
    Optional<MudeDTipoAllegato> findByCodice(@Param("codice")String codice);
}