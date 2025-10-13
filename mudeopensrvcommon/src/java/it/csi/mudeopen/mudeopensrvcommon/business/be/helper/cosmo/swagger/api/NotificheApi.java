/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaNotificaFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaNotificaFruitoreResponse;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface NotificheApi  {

    /**
     * 
     *
     * Endpoint per creare una notifica
     *
     */
    @POST
    @Path("/notifiche")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK - la richiesta di notifica e' stata elaborata correttamente", response = CreaNotificaFruitoreResponse.class),
        @ApiResponse(code = 400, message = "Bad Request - il formato della richiesta non e' corretto", response = EsitoCreazioneNotificaFruitoreResponsepropertiesesito.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = EsitoCreazioneNotificaFruitoreResponsepropertiesesito.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione", response = EsitoCreazioneNotificaFruitoreResponsepropertiesesito.class),
        @ApiResponse(code = 404, message = "Not found - la pratica specificata non esiste", response = EsitoCreazioneNotificaFruitoreResponsepropertiesesito.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati forniti non sono corretti", response = EsitoCreazioneNotificaFruitoreResponsepropertiesesito.class) })
    */public CreaNotificaFruitoreResponse postNotificaFruitore(CreaNotificaFruitoreRequest notifica, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);
}

