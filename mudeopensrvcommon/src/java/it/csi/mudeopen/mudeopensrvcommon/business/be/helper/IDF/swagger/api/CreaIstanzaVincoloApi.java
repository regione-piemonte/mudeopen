/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.CreaIstanzaVincoloBody;


@Path("/creaIstanzaVincolo")
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2024-12-18T22:40:48.348410165Z[GMT]")
public interface CreaIstanzaVincoloApi  {
   
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    /*@Operation(summary = "Crea una nuova istanza vincolo", description = "Endpoint per creare una nuova istanza con i dati di input forniti.", tags={  })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Istanza creata con successo", content = @Content(mediaType = "application/json", schema = //@Schema(implementation = IstanzaVincoloResponse.class))),
                @ApiResponse(responseCode = "400", description = "Richiesta non valida"),
                @ApiResponse(responseCode = "500", description = "Errore interno del server")
         })*/
    Response creaIstanzaVincoloPost(/*@Parameter(description = "" ,required=true) */CreaIstanzaVincoloBody body, @Context SecurityContext securityContext);

}
