/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaDocumentiFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaDocumentiLinkFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EsitoCreazioneDocumentiFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EsitoCreazioneDocumentiLinkFruitore;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface DocumentiApi  {

    /**
     * 
     *
     * Recupero contenuto di un documento da parte di un fruitore
     *
     */
    @GET
    @Path("/documenti/{idDocumento}/contenuto")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 302, message = "Found", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    */public Object getContenutoFruitore(@PathParam("idDocumento") String idDocumento);

    /**
     * 
     *
     * Endpoint per creare uno o più documenti
     *
     */
    @POST
    @Path("/documenti")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK - i documenti sono stati acquisiti", response = EsitoCreazioneDocumentiFruitore.class),
        @ApiResponse(code = 400, message = "Bad Request - il formato della richiesta non e' corretto", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione dei documenti", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 404, message = "Not found - la pratica specificata non e' stata trovata", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 409, message = "Conflict - esiste gia' un documento corrispondente all'identificativo fornito", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati forniti non sono corretti", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class) })
    */public EsitoCreazioneDocumentiFruitore postDocumentoFruitore(CreaDocumentiFruitoreRequest documento, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    /**
     * 
     *
     * Endpoint per creare uno o più documenti tramite link
     *
     */
    @POST
    @Path("/documenti/link")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK - i documenti sono stati acquisiti", response = EsitoCreazioneDocumentiLinkFruitore.class),
        @ApiResponse(code = 400, message = "Bad Request - il formato della richiesta non e' corretto", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 401, message = "Unauthorized - devi autenticarti per procedere", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 403, message = "Forbidden - non sei autorizzato alla creazione dei documenti", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 404, message = "Not found - la pratica specificata non e' stata trovata", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 409, message = "Conflict - esiste gia' un documento corrispondente all'identificativo fornito", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class),
        @ApiResponse(code = 422, message = "Unprocessable entity - i dati forniti non sono corretti", response = EsitoCreazioneDocumentoFruitorepropertiesesito.class) })
    */public EsitoCreazioneDocumentiLinkFruitore postFruitoriDocumentiLink(CreaDocumentiLinkFruitoreRequest body);
}

