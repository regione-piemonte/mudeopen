/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.TipiIstanzeApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class TipiIstanzeApiServiceImpl extends BaseAPI<TipiIstanzeApi> implements TipiIstanzeApi {
    @Autowired
    private TipoIstanzaService tipoIstanzaService;

	@Override
	public Response recuperaTipologieIstanze(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
												Long idComune,
												String codiceIstanzaPadre, 
												Long idIstanza, 
												Long idFascicolo) {
        return voToResponse(
        		tipoIstanzaService.recuperaTipologieIstanze(idComune, codiceIstanzaPadre, idIstanza, idFascicolo), 
        		200);
    	}
		
}