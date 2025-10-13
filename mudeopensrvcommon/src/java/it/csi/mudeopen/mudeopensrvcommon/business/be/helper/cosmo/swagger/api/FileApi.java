/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.swagger.annotations.Api;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CompleteUploadSessionRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreateUploadSessionRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreateUploadSessionResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.FileUploadResult;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.FileUploadResult1;
/**
 * cosmo-servizi-fruitori
 *
 * <p>API esposte da Cosmo per l'integrazione con i fruitori esterni
 *
 */
@Path("/")
@Api(value = "/", description = "")
public interface FileApi  {

    @DELETE
    @Path("/file/upload-session/{sessionUUID}/cancel")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized - il fruitore non e' stato autenticato correttamente oppure non e' stato registrato come fruitore abilitato", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 403, message = "Forbidden - il fruitore non e' autorizzato alla specifica operazione richiesta", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 404, message = "Not Found - la sessione di upload richiesta non esiste", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 409, message = "Conflict - la sessione di upload non e' cancellabile al momento", response = Paths1file1uploadpostresponses401schema.class) })
    */public void cancelUploadSession(@PathParam("sessionUUID") String sessionUUID, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    @POST
    @Path("/file/upload-session/{sessionUUID}/complete")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = FileUploadResult.class),
        @ApiResponse(code = 400, message = "Bad Request - la sessione di upload non e' pronta per il completamento", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 401, message = "Unauthorized - il fruitore non e' stato autenticato correttamente oppure non e' stato registrato come fruitore abilitato", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 403, message = "Forbidden - il fruitore non e' autorizzato alla specifica operazione richiesta", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 404, message = "Not Found - la sessione di upload richiesta non esiste", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 409, message = "Conflict - la sessione di upload non e' confermabile al momento", response = Paths1file1uploadpostresponses401schema.class) })
    */public FileUploadResult completeUploadSession(@PathParam("sessionUUID") String sessionUUID, CompleteUploadSessionRequest body, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    @POST
    @Path("/file/upload-session")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "OK", response = CreateUploadSessionResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized - il fruitore non e' stato autenticato correttamente oppure non e' stato registrato come fruitore abilitato", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 403, message = "Forbidden - il fruitore non e' autorizzato alla specifica operazione richiesta", response = Paths1file1uploadpostresponses401schema.class) })
    */public CreateUploadSessionResponse createUploadSession(CreateUploadSessionRequest body);

    @POST
    @Path("/file/upload")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = FileUploadResult.class),
        @ApiResponse(code = 401, message = "Unauthorized - il fruitore non e' stato autenticato correttamente oppure non e' stato registrato come fruitore abilitato", response = Esito.class),
        @ApiResponse(code = 403, message = "Forbidden - il fruitore non e' autorizzato alla specifica operazione richiesta", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 404, message = "Not Found - la risorsa richiesta non esiste", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 409, message = "Conflict - una risorsa corrispondente agli identificativi univoci forniti esiste gia'", response = Paths1file1uploadpostresponses401schema.class) })
    */public FileUploadResult postFileUpload(@MultipartForm MultipartFormDataInput /*@Multipart(value = "payload" ) Attachment*/ payloadDetail, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    @POST
    @Path("/file/upload-file-utenti")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = FileUploadResult1.class),
        @ApiResponse(code = 401, message = "Unauthorized - il fruitore non e' stato autenticato correttamente oppure non e' stato registrato come fruitore abilitato", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 403, message = "Forbidden - il fruitore non e' autorizzato alla specifica operazione richiesta", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 404, message = "Not Found - la risorsa richiesta non esiste", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 409, message = "Conflict - una risorsa corrispondente agli identificativi univoci forniti esiste gia'", response = Paths1file1uploadpostresponses401schema.class) })
    */public FileUploadResult1 postUploadFileUtenti(@MultipartForm MultipartFormDataInput /*@Multipart(value = "payload" ) Attachment*/ payloadDetail, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);

    @POST
    @Path("/file/upload-session/{sessionUUID}/parts/{partNumber}")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*@ApiOperation(value = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "OK"),
        @ApiResponse(code = 401, message = "Unauthorized - il fruitore non e' stato autenticato correttamente oppure non e' stato registrato come fruitore abilitato", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 403, message = "Forbidden - il fruitore non e' autorizzato alla specifica operazione richiesta", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 404, message = "Not Found - la risorsa richiesta non esiste", response = Paths1file1uploadpostresponses401schema.class),
        @ApiResponse(code = 409, message = "Conflict - una risorsa corrispondente agli identificativi univoci forniti esiste gia'", response = Paths1file1uploadpostresponses401schema.class) })
    */public void uploadSessionPart(@PathParam("sessionUUID") String sessionUUID, @PathParam("partNumber") Long partNumber, @MultipartForm MultipartFormDataInput /*@Multipart(value = "payload" ) Attachment*/ payloadDetail, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor);
}

