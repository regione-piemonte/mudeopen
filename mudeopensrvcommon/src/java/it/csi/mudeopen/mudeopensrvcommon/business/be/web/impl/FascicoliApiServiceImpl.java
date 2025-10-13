/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.FascicoliApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;

@Component
public class FascicoliApiServiceImpl extends AbstractApi implements FascicoliApi {

	@Autowired
	private FascicoliService fascicoliService;

	@Override
    public Response recuperaFascicoli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, 
    		HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter, int sort, int page, int size, String scope) {
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
		String codiceTipoIntervento = FilterUtil.getStringValue(filter, "id_tipo_intervento");
		String idIntestatarioPf = FilterUtil.getStringValue(filter, "id_intestatario_pf");
		String idIntestatarioPg = FilterUtil.getStringValue(filter, "id_intestatario_pg");
		String idPm = FilterUtil.getStringValue(filter, "id_pm");
		Long idComune = FilterUtil.getLongValue(filter, "id_comune");		
		Long idProvincia = FilterUtil.getLongValue(filter, "id_provincia");
		Long idDug = FilterUtil.getLongValue(filter, "id_dug");
		String duf = FilterUtil.getStringValue(filter, "duf");
		LocalDate creationDateForm = FilterUtil.getDateFromValue(filter, "data_creazione");
		LocalDate creationDateTo = FilterUtil.getDateToValue(filter, "data_creazione");
		String state = FilterUtil.getStringValue(filter, "stato");
		String codiceFascicolo = FilterUtil.getStringValue(filter, "codice_fascicolo");
		
		return fascicoliService.recuperaFascicoliUtente(mudeTUser, codiceTipoIntervento, idIntestatarioPf, idIntestatarioPg, idPm, idComune, idProvincia, idDug, duf, creationDateForm, creationDateTo, codiceFascicolo, state, page, size, scope);
	}

	@Override
	public Response recuperaFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
		
		FascicoloVO vo = fascicoliService.recuperaFascicolo(mudeTUser, idFascicolo);

		
		return Response.ok(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
	}
	
}