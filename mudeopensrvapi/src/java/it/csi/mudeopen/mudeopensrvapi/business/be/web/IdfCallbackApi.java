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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/idf")
@Consumes({ "application/json" })
@Produces({ "application/json" })
//@io.swagger.annotations.Api(description = "the stato-pratica API")
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-12-01T11:43:27.748Z")
public interface IdfCallbackApi  {

    @PUT
    @Path("/richiesta-autorizzazione/{idIstanza}")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*
    @io.swagger.annotations.ApiOperation(value = "Acquisizione richiesta autorizzazione IDF", notes = "Invio richiesta autorizzazione", response = Esito.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basic_auth")
    }, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Esito.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request - i dati inviati non sono formalmente corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found - istanza specificata non e' stata trovata", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    */
    public Response invioRichiestaAutorizzazioneIDF(MultipartFormDataInput input, @PathParam("idIstanza") Integer idIstanza, @Context SecurityContext securityContext);
    
    @PUT
    @Path("/dati-concessione-autorizzazione/{idIstanza}")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*
    @io.swagger.annotations.ApiOperation(value = "Acquisizione dati concessione autorizzazione IDF", notes = "Invio richiesta autorizzazione", response = Esito.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basic_auth")
    }, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Esito.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request - i dati inviati non sono formalmente corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found - istanza specificata non e' stata trovata", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    */
    public Response invioDatiConcessioneAutorizzazioneIDF(MultipartFormDataInput input, @PathParam("idIstanza") Integer idIstanza, @Context SecurityContext securityContext);

}
