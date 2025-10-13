/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudeTDatiIstanzaRepository extends BaseRepository<MudeTDatiIstanza, Long> {

    List<MudeTDatiIstanza> findByMudeTIstanza(MudeTIstanza mudeTIstanza);

    @Query("select o from MudeTDatiIstanza o where o.mudeTIstanza.id =:idIstanza and o.mudeTContatto.id =:idSoggetto")
    List<MudeTDatiIstanza> findAllTitoloSoggetto(@Param("idIstanza") Long idIstanza, @Param("idSoggetto") Long idSoggetto);

    @Query("select o from MudeTDatiIstanza o where o.mudeTIstanza.id =:idIstanza and o.mudeTContatto.id =:idSoggetto AND o.dato = 'TITOLO_SOGGETTO_ABILITATO'")
    MudeTDatiIstanza findByIstanzaAndSoggetto(@Param("idIstanza") Long idIstanza, @Param("idSoggetto") Long idSoggetto);

    @Query("select o from MudeTDatiIstanza o where o.mudeTIstanza.id =:idIstanza and o.mudeTContatto.id =:idSoggetto AND o.dato = 'TITOLO_SOGGETTO_ABILITATO_RT'")
    MudeTDatiIstanza findByIstanzaAndSoggettoRT(@Param("idIstanza") Long idIstanza, @Param("idSoggetto") Long idSoggetto);

    @Modifying
    @Query("delete from MudeTDatiIstanza o where o.mudeTIstanza.id =:idIstanza and o.mudeTContatto.id =:idSoggetto")
    void deleteByIstanzaAndSoggetto(@Param("idIstanza") Long idIstanza, @Param("idSoggetto") Long idSoggetto);
}