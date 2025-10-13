/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.EntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.notifica.NotificaVO;

public interface NotificaEntityMapper extends EntityMapper<MudeTNotifica, NotificaVO> {

	NotificaVO mapEntityToVOList(MudeTNotifica dto, List<MudeRNotificaUtente>  mudeRNotificaUtenteList);
}