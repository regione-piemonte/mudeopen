/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

public interface UtilsTraceCsiLogAuditService {
    void traceCsiLogAudit(String operazione, String tabella, String key, String metodo, String dettaglioOperazione);
}