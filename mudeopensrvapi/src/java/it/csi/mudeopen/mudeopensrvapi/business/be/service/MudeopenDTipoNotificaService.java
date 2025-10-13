/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.util.List;
import java.util.Optional;

import it.csi.mudeopen.mudeopensrvapi.vo.TipoNotificaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;

public interface MudeopenDTipoNotificaService {
	
	List<TipoNotificaVO> findAllValid();
	
	List<MudeDTipoNotifica> findByCodTipoNotifica(String codTipoNotifica);
	
	Optional<MudeDTipoNotifica> findBySubCodTipoNotifica(String subCodTipoNotifica);
}
