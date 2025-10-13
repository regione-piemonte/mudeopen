/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.LogTracciatoVO;

import java.util.List;

public interface LogTracciatoService {

    //List<LogTracciatoVO> findByIstanza(Long idIstanza);

    boolean saveLogTracciato(MudeTUser user, MudeTIstanza istanza, MudeDTipoTracciato tipoTracciato, String tipoErrore, String errore, String jsondataAsXml, String usedXslt, String generatedXml);
}