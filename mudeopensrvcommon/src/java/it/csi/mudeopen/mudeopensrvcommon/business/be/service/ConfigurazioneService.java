/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.vo.configurazione.ConfigurazioneVO;

import java.util.Optional;

public interface ConfigurazioneService {

    ConfigurazioneVO findConfigurazioneByName(String name);
}