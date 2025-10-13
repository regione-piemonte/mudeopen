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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaPraticaEsternaFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaPraticaEsternaFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaEsternaFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaEsternaFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EliminaPraticaEsternaFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EliminaPraticaEsternaFruitoreResponse;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface PraticheEsterneApi  {

    /**
     * 
     *
     * annulla una pratica
     *
     */
    @DELETE
    @Path("/pratiche-esterne/{idPraticaExt}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK - la pratica e' stata annullata", response = EliminaPraticaEsternaFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione della pratica", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - nessuna pratica trovata per l'id fornito", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 409, message = "Conflict - esiste gia' una pratica corrispondente all'identificativo fornito", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public EliminaPraticaEsternaFruitoreResponse deletePraticheEsterneIdFruitori(@PathParam("idPraticaExt") String idPraticaExt, EliminaPraticaEsternaFruitoreRequest body);

    /**
     * 
     *
     * inserisce una pratica
     *
     */
    @POST
    @Path("/pratiche-esterne")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created - la pratica e' stata creata", response = CreaPraticaEsternaFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione della pratica", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 409, message = "Conflict - esiste gia' una pratica corrispondente all'identificativo fornito", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public CreaPraticaEsternaFruitoreResponse postPraticheEsterneFruitori(CreaPraticaEsternaFruitoreRequest body);

    /**
     * 
     *
     * aggiorna una pratica
     *
     */
    @PUT
    @Path("/pratiche-esterne/{idPraticaExt}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK - la pratica e' stata aggiornata", response = AggiornaPraticaEsternaFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione della pratica", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - nessuna pratica trovata per l'id fornito", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 409, message = "Conflict - esiste gia' una pratica corrispondente all'identificativo fornito", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public AggiornaPraticaEsternaFruitoreResponse putPraticheEsterneIdFruitori(@PathParam("idPraticaExt") String idPraticaExt, AggiornaPraticaEsternaFruitoreRequest body);
}

