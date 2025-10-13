/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class IstanzaApiServiceHelper extends FileServiceHelper {

    @Autowired
    private IstanzaService istanzaService;

    public ErrorResponse verifyDuplicateFilename(String codFascicolo, String codIstanza, String filename) {

        Boolean filenameAlreadyExists = false;
        if (StringUtils.isNotBlank(codIstanza)) {
            filenameAlreadyExists = istanzaService.verifyDuplicateFilename(codIstanza, filename);
        }

        return verifyDuplicateFilename(codFascicolo, codIstanza, filename, filenameAlreadyExists);
    }

}