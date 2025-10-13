/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.mudeopen.mudeopensrvapi.vo.InserimentoNotificaVO;
@Path(value = "")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON})
public interface NotificheApi {

	@GET
	@Path("/elenco-notifiche-inviate/{numeroIstanza}")
	Response elencoNotificheInviate(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
			@PathParam("numeroIstanza") String numeroIstanza) throws Exception;
	
	@GET
	@Path("/notifica/{numeroIstanza}/{idEvento}")
	Response visualizzazioneNotifica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID, 
			@PathParam("numeroIstanza") String numeroIstanza,@PathParam("idEvento") Long idEvento) throws Exception;
	
	@GET
	@Path("/elenco-allegati-notifica/{numeroIstanza}/{idEvento}")
	Response elencoAllegatiNotifica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
			@PathParam("numeroIstanza") String numeroIstanza,@PathParam("idEvento") Long idEvento) throws Exception;

	@POST
	@Path("/notifica")
	Response inserisciNotifica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
			@RequestBody(required = true) InserimentoNotificaVO inserisciNotifica) throws Exception;
}
