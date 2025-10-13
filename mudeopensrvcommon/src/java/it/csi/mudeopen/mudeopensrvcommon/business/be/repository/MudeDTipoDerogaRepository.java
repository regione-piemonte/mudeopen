/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDeroga;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudeDTipoDerogaRepository extends BaseDictionaryRepository<MudeDTipoDeroga, String> {

    List<MudeDTipoDeroga> findAllByDataFineIsNull();

}