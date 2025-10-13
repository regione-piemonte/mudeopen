/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.email.EmailVO;

public interface MailSenderService {
	
	public static final String TEMPLATE_FILE_ENCODING = "UTF-8";

	public void sendMail(MudeRNotificaContatto mudeRNotificaContatto, EmailVO emailVO, int valueMaxRetry) throws Throwable;
	

}