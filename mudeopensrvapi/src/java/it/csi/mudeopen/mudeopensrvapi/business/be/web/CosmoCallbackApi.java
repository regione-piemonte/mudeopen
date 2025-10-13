/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.AggiornaStatoPraticaRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.cosmo.AssegnazioneRequest;

@Path("/cosmo")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface CosmoCallbackApi  {

    @PUT
    @Path("/stato-pratica/{idPratica}")
	public Response callbackPutStatoPratica(@Context SecurityContext securityContext,  @PathParam("idPratica") String idPratica, AggiornaStatoPraticaRequest body);
    
    @PUT
    @Path("/assegna-pratica/{idPratica}")
	public Response callbackPutAssegnaPratica(@Context SecurityContext securityContext,  @PathParam("idPratica") String idPratica, AssegnazioneRequest body);
    
}
