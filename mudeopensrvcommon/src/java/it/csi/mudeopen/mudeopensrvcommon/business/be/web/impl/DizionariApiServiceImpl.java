/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.DictionaryService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.DizionariApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

@Component
public class DizionariApiServiceImpl extends AbstractApi implements DizionariApi {

    @Autowired
    private DictionaryService dictionaryService;

    public Response ricercaDizionario(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String dictType, String filter) {
        //MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        List<DizionarioVO> list = dictionaryService.search(filter, dictType, null);
        return Response.ok().entity(list).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(list)).build();
    }

}