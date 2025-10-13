/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaEnte;

@Repository
//public interface MudeTIstanzaRepository extends CrudRepository<MudeTIstanza, Long>, JpaSpecificationExecutor<MudeTIstanza>{
public interface MudeRIstanzaEnteRepository extends BaseRepository<MudeRIstanzaEnte, Long> {

    List<MudeRIstanzaEnte> findAllByIstanza_IdAndDataFineIsNull(@Param("idIstanza") Long idIstanza);

    @Modifying
    void deleteByIstanza_id(@Param("idIstanza") Long idIstanza);

}