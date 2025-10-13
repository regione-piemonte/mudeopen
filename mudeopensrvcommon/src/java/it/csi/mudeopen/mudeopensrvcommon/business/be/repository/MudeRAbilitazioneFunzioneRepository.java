/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRAbilitazioneFunzione;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRAbilitazioneFunzioneRepository extends BaseRepository<MudeRAbilitazioneFunzione, Long> {

    List<MudeRAbilitazioneFunzione> findAllByDataFineNull();

    List<MudeRAbilitazioneFunzione> findAllByAbilitazione_IdAndDataFineIsNull(Long idAbilitazione);
    List<MudeRAbilitazioneFunzione> findAllByAbilitazione_CodiceAndDataFineIsNull(String codiceAbilitazione);

    @Query("SELECT o.funzione.codice FROM MudeRAbilitazioneFunzione o WHERE o.dataFine IS Null AND o.abilitazione.codice = ?1")
    List<String> findAllByCodice(String codiceAbilitazione);

}