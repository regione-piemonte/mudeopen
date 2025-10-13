/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.IstanzaTracciatoVO;

import java.util.List;

public interface IstanzaTracciatoService {

    List<IstanzaTracciatoVO> findTracciatiPerIstanza(Long idIstanza, MudeTUser user);

    IstanzaTracciatoVO findIstanzaTracciato(Long idIstanza, Long idTipoTracciato, MudeTUser user);

    IstanzaTracciatoVO saveIstanzaTracciato(Long idIstanza, Long idTipoTracciato, MudeTUser user);

    void deleteIstanzaTracciato(Long idIStanzaTracciato);
}