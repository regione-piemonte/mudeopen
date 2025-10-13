/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.ContattiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;

@Component
public class ContattiApiServiceImpl extends AbstractApi implements ContattiApi {

    @Autowired
    private ContattoService contattoService;

    @Override
    public Response ricercaContatto(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		String filter, String sort, int page, int size) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, true);

        return contattoService.findContattoByParam(filter, mudeTUser,page, size, sort);
    }

    @Override
    public Response modificaContatto(String userCf, String XRequestId, String XForwardedFor, ContattoVO contatto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        return voToResponse(contattoService.saveContatto(contatto, null, mudeTUser), 200);
    }

    @Override
    public Response eliminaContatto(String userCf, String XRequestId, String XForwardedFor, String cf, String piva, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        Boolean isDeletable = contattoService.isContattoDeletable(piva, cf, mudeTUser.getCf());
        if (!isDeletable) {
            ErrorResponse error = new ErrorResponse("403", "errore_validazione", "Non è possibile eliminare il soggetto. Soggetto già collegato ad un istanza.", null, null);

            logger.error("[ContattoApiServiceImpl::eliminaContatto] ERROR : " + error);

            return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }
        contattoService.deleteContatto(piva, cf, mudeTUser);
        return Response.noContent().build();
    }
}