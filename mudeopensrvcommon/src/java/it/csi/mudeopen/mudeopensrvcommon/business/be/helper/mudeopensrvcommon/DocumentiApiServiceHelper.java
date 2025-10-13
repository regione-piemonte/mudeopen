/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AllegatiService;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class DocumentiApiServiceHelper extends FileServiceHelper {

    @Autowired
    private AllegatiService allegatiService;

    public ErrorResponse verifyDuplicateFilename(String codFascicolo, String codIstanza, Long idIstanza, String filename, String codAllegato) {

        Boolean filenameAlreadyExists = false;
        if(StringUtils.isNotBlank(codAllegato)){
            filenameAlreadyExists = allegatiService.verifyDuplicateFilename(idIstanza, filename, codAllegato);
        }

        return verifyDuplicateFilename(codFascicolo,codIstanza, filename, filenameAlreadyExists);
    }

}