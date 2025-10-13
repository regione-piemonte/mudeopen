/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloRuolo;

@Repository
public interface MudeRFascicoloRuoloRepository extends BaseRepository<MudeRFascicoloRuolo,Long> {

	MudeRFascicoloRuolo findAllById(Long idFascicoloRuolo);

    List<MudeRFascicoloRuolo> findAllByFascicolo_Id(Long idFascicolo);

    MudeRFascicoloRuolo findAllByFascicolo_IdAndRuoloAndDataFineIsNull(Long idFascicolo, String ruolo);

    //MudeRFascicoloRuolo findAllByGuidSoggettoAndRuoloAndDataFineIsNull(String guid, String ruolo);

    MudeRFascicoloRuolo findAllByFascicolo_IdAndGuidSoggettoAndRuoloAndDataFineIsNull(Long idFascicolo, String guid, String ruolo);

}