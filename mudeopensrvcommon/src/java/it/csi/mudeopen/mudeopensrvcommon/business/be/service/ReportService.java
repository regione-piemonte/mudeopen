/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.sql.Timestamp;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;

public interface ReportService {

    public byte[] generaReportPDFToByte(Long idIstanza, MudeTUser mudeTUser, Timestamp dataGenerazione);

}