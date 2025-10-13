/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDTipoDocpaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.TipiDocpaApi;
import it.csi.mudeopen.mudeopensrvapi.vo.TipoDocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;

/**
 * The type TipiDocpa api service.
 */
@Component
public class TipiDocpaApiImpl extends AbstractApiServiceImpl implements TipiDocpaApi {
	

    @Autowired
    private MudeopenDTipoDocpaService mudeopenDTipoDocpaService;

	@Override
	public Response ricercaTipoDocumento(String XRequestId, String XForwardedFor, String fruitoreID) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			List<MudeDTipoDocPA> list = mudeopenDTipoDocpaService.findAllActive();
			List<TipoDocumentoVO> vos = new ArrayList<>();
			
			list.forEach(el -> vos.add(new TipoDocumentoVO(el.getCodeTipoDocpa(), el.getDescTipoDocpa())));
			return voToResponse(vos, HttpStatus.SC_OK);
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}
}