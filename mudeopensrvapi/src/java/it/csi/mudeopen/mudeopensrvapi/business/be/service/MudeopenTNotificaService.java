/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.util.List;
import java.util.Optional;

import it.csi.mudeopen.mudeopensrvapi.vo.AllegatoNotificaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.NotificaInviataVO;
import it.csi.mudeopen.mudeopensrvapi.vo.NotificaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;

public interface MudeopenTNotificaService {

	List<NotificaInviataVO> cercaNotificheInviate(String codiceIstanza, Long istanza);
	
	Optional<NotificaVO> visualizzaNotifica(String numeroIstanza, Long idNotifica);
	
	List<AllegatoNotificaVO> cercaAllegatiNotifica(String numeroIstanza, Long idNotifica);
	
	List<MudeTNotifica> cercaPerNumeroIstanza(String numeroIstanza);
	
}
