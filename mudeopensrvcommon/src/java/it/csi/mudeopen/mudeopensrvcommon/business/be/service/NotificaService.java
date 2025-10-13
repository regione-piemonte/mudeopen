/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;

public interface NotificaService {
    Response cercaNotificheIstanza(Long idIstanza, int page, int size, MudeTUser mudeTUser);

    List<SelectVO> reuperoTemplateFormIoNotifica(Long idIstanza, MudeTUser mudeTUser, Long idTipoNotifica );

    Response loadNotificheFO(String filter, int page, int size, MudeTUser mudeTUser);

    List<DocumentoVO> notificaLettaFO(Long idNotifica, MudeTUser mudeTUser);

    List<SelectVO> recuperaTipiNotifica();

}