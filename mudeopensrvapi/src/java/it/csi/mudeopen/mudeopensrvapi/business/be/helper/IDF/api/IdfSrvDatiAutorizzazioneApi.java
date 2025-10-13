/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.IDF.api;

//import io.swagger.model.*;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

//import io.swagger.model.Esito;

import java.util.List;
import java.util.Map;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/idf")
@Consumes({ "application/json" })
@Produces({ "application/json" })
//@io.swagger.annotations.Api(description = "the dati-concessione-autorizzazione API")
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2024-12-19T09:04:58.766Z")
public interface IdfSrvDatiAutorizzazioneApi  {
   
    @PUT
    @Path("/dati-concessione-autorizzazione/{idIstanza}")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*@io.swagger.annotations.ApiOperation(value = "Acquisizione dati concessione autorizzazione IDF", notes = "Invio richiesta autorizzazione", response = Esito.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basic_auth")
    }, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Esito.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request - i dati inviati non sono formalmente corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found - istanza specificata non e' stata trovata", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })*/
    public Response invioDatiConcessioneAutorizzazioneIDF(MultipartFormDataInput input, @PathParam("idIstanza") Integer idIstanza,@Context SecurityContext securityContext);
    
    @PUT
    @Path("/richiesta-autorizzazione/{idIstanza}")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    /*@io.swagger.annotations.ApiOperation(value = "Acquisizione richiesta autorizzazione IDF", notes = "Invio richiesta autorizzazione", response = Esito.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basic_auth")
    }, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Esito.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request - i dati inviati non sono formalmente corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found - istanza specificata non e' stata trovata", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable entity - i dati inviati non sono corretti", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })*/
    public Response invioRichiestaAutorizzazioneIDF(MultipartFormDataInput input, @PathParam("idIstanza") Integer idIstanza,@Context SecurityContext securityContext);
    
}
