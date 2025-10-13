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
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoAllegatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.TipiAllegatoApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoVO;

@Component
public class TipiAllegatoApiServiceImpl extends AbstractApi implements TipiAllegatoApi {

    @Autowired
    private TipoAllegatoService tipoAllegatoService;

    @Override
    public Response loadTipiAllegato(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        List<TipoAllegatoVO> list =  tipoAllegatoService.findAllOrderByDescBreve();
        return Response.ok().entity(list).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(list)).build();
    }

    @Override
    public Response loadTipoAllegatoByCode(String codTipoAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        TipoAllegatoVO vo =  tipoAllegatoService.findByCodTipoAllegato(codTipoAllegato);

        if(null != vo) {

            return Response.ok().entity(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
        }
        else{
            //todo concordare cosa fare

            return Response.ok().build();
        }
    }

    @Override
    public Response loadTipiAllegatoByCategoriaAllegato(String codiceCategoriaAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        List<TipoAllegatoVO> list =  tipoAllegatoService.findAllByIdCategoriaAllegato(codiceCategoriaAllegato);
        return Response.ok().entity(list).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(list)).build();

    }

    @Override
    public Response loadByCodTipoAllegatoAndSubCodeTipoAllegato(String codTipoAllegato, String subCodTipoAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        TipoAllegatoVO vo =  tipoAllegatoService.findByCodTipoAllegatoAndSubCodeTipoAllegato(codTipoAllegato, subCodTipoAllegato);

        if(null != vo) {

            return Response.ok().entity(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
        }
        else{
            //todo concordare cosa fare

            return Response.ok().build();
        }
    }

    @Override
    public Response loadTipiAllegatoByTemplateQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		Long idIstanza,
    		Long idTemplateQuadro, 
			String tipo_allegato) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        List<TipoAllegatoExtendedVO> list =  tipoAllegatoService.findTipiAllegatoByTemplateQuadro(idIstanza, idTemplateQuadro, mudeTUser, httpHeaders, tipo_allegato);
        return Response.ok().entity(list).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(list)).build();
    }
}