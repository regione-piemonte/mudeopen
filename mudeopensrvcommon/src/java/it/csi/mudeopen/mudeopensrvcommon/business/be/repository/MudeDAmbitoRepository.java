/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAmbito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeDAmbitoRepository extends BaseRepository<MudeDAmbito, Long> {

    public List<MudeDAmbito> findAllByFlgAttivoAndDataFineIsNull(long flgAttivo);

    public MudeDAmbito findByCodAmbito(String codAmbito);

	
}