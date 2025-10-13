/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTLogTracciato;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeTLogTracciatoRepository extends BaseRepository<MudeTLogTracciato, Long> {

    List<MudeTLogTracciato> findByMudeTIstanza_Id(Long idIstanza);

    @Modifying
    void deleteByMudeTIstanza_id(@Param("idIstanza") Long idIstanza);

}