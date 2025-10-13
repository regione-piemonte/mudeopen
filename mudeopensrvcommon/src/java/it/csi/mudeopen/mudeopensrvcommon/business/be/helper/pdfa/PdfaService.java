/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.pdfa;

import java.io.File;
import java.io.InputStream;

public interface PdfaService {
	
	public byte[] doConversion (String idIstanza,InputStream is) throws Exception ;
}
