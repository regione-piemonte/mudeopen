/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDestinazioneUso;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudeDDestinazioneUsoRepository extends BaseDictionaryRepository<MudeDDestinazioneUso, String> {

    List<MudeDDestinazioneUso> findAllByDataFineIsNull();
}