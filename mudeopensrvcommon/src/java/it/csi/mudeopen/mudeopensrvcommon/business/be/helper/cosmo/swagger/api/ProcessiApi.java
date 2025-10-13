/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AvviaProcessoFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AvviaProcessoFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreResponse;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface ProcessiApi  {

    /**
     * 
     *
     * Invia un segnale ad un processo
     *
     */
    @POST
    @Path("/pratiche/{idPratica}/segnala")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = InviaSegnaleFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request - la richiesta non e' formalmente corretta", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per fruire di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla fruizione di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - la pratica specificata non e' stata trovata", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 409, message = "Conflict - la pratica specificata non ha un processo avviato", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public InviaSegnaleFruitoreResponse postFruitoriSegnale(@PathParam("idPratica") String idPratica, InviaSegnaleFruitoreRequest body);

    /**
     * 
     *
     * Avvia il processo associato alla pratica specificata
     *
     */
    @POST
    @Path("/processo")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = AvviaProcessoFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request - i dati inviati non sono formalmente corretti", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per fruire di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla fruizione di questo servizio", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 404, message = "Not found - la pratica specificata non e' stata trovata", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 409, message = "Conflict - azione non valida per lo stato corrente della pratica", response = Paths1eventopostresponses400schema.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Paths1eventopostresponses400schema.class) })
    */public AvviaProcessoFruitoreResponse postProcessoFruitore(AvviaProcessoFruitoreRequest body);
}

