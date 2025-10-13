/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import io.swagger.annotations.Api;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaRelazionePraticaRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaRelazionePraticaResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.GetPraticaFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.PraticheFruitoreResponse;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface PraticheApi  {

    /**
     * 
     *
     * Ottiene lo stato della pratica
     *
     */
    @GET
    @Path("/pratiche/{idPratica}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = GetPraticaFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request - la richiesta non e' formalmente corretta", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per fruire di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla fruizione di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - pratica non trovata", response = Paths1eventopostresponses400schema.class) })
    */public GetPraticaFruitoreResponse getFruitoriPratica(@PathParam("idPratica") String idPratica, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    /**
     * 
     *
     */
    @GET
    @Path("/pratiche")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PraticheFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Esito.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Paths1pratichegetresponses400schema.class) })
    */public PraticheFruitoreResponse getFruitoriPratiche(@QueryParam("filter")String filter, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    /**
     * 
     *
     * inserisce una pratica
     *
     */
    @POST
    @Path("/pratiche")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created - la pratica e' stata creata", response = CreaPraticaFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Paths1pratichegetresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = Paths1pratichegetresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione della pratica", response = Paths1pratichegetresponses400schema.class),
        @ApiResponse(code = 409, message = "Conflict - esiste gia' una pratica corrispondente all'identificativo fornito", response = Paths1pratichegetresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1pratichegetresponses400schema.class) })
    */public Response postPraticheFruitori(CreaPraticaFruitoreRequest body);

    /**
     * 
     *
     * Endpoint che inserisce o aggiorna una o più relazioni tra pratiche
     *
     */
    @PUT
    @Path("/pratiche/{idPraticaExt}/in-relazione")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = AggiornaRelazionePraticaResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Paths1pratichegetresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Paths1pratichegetresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Paths1pratichegetresponses400schema.class) })
    */public AggiornaRelazionePraticaResponse putFruitoriPraticheIdPraticaExt(@PathParam("idPraticaExt") String idPraticaExt, AggiornaRelazionePraticaRequest body, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);
}

