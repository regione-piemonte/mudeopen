/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDElementoElenco;

@Repository
public interface MudeDElementoElencoRepository extends BaseRepository<MudeDElementoElenco, Long> {

    List<MudeDElementoElenco> findByIdTipoElencoInAndDataFineIsNullOrderByOrdinamento(List<Long>  codiceTipoIstanza);
    @Query(value = "SELECT id_tipo_elenco FROM mudeopen_d_elemento_elenco WHERE codice = :codice ORDER BY ordinamento", nativeQuery = true)
    Long getIdTipoElencoByCodiceElementoElenco(@Param("codice") String codiceElementoElenco);
}