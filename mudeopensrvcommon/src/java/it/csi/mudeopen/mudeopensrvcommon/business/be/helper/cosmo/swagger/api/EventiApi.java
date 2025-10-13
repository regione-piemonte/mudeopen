/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaEventoFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaEventoFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaEventoFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaEventoFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EliminaEventoFruitoreRequest;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface EventiApi  {

    /**
     * 
     *
     * Elimina un evento
     *
     */
    @DELETE
    @Path("/evento/{idEvento}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 400, message = "Bad Request - la richiesta non e' formalmente corretta", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per fruire di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla fruizione di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - l'evento specificato non e' stato trovato", response = Paths1eventopostresponses400schema.class) })
    */public void deleteFruitoriEvento(@PathParam("idEvento") String idEvento, EliminaEventoFruitoreRequest body);

    /**
     * 
     *
     * Inserisce un evento
     *
     */
    @POST
    @Path("/evento")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = CreaEventoFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request - la richiesta non e' formalmente corretta", response = Esito.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per fruire di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla fruizione di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - l'ente o l'utente specificato non e' stato trovato", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public CreaEventoFruitoreResponse postFruitoriEvento(CreaEventoFruitoreRequest body);

    /**
     * 
     *
     * Aggiorna un evento
     *
     */
    @PUT
    @Path("/evento/{idEvento}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = AggiornaEventoFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request - la richiesta non e' formalmente corretta", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per fruire di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla fruizione di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - l'evento specificato non e' stato trovato", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public AggiornaEventoFruitoreResponse putFruitoriEvento(@PathParam("idEvento") String idEvento, AggiornaEventoFruitoreRequest body);
}

