
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza.IstanzaExtVO;

/**
 * The interface Istanze api.
 */
@Path("")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IstanzeApi {

	@GET
    @Path("/istanza/{numeroIstanza}")
    Response ricercaDatiSintesiIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;

    @GET
    @Path("/istanze")
    Response ricercaPaginataIstanze03(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@QueryParam("filter") String filter,
    		@QueryParam("page") Integer page, 
    		@QueryParam("size") Integer size) throws Exception;
    /**
     * Genera numero mude
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param codIstatComune     codice istat comune
      the response
     * @throws Exception 
     */
    @POST
    @Path("/genera-numero-mude/{codIstatComune}/{nuovoIntervento}")
    public Response generaNumeroMUDE(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("codIstatComune") String istatComune, @PathParam("nuovoIntervento") String nuovoIntervento ) throws Exception;

    @GET
    @Path("/dati-protocollazione-istanza/{numeroIstanza}")
    public Response visualizzaDatiProtocollazioneIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;

    @GET
    @Path("/file-istanza/{numeroIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response estraiFileIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;

    @GET
    @Path("/allegato-istanza/{numeroIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response estraiAllegatoIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza,
    		@QueryParam("nomeFileAllegato") String nomeFileAllegato, 
    		@QueryParam("sbustato") String sbustato) throws Exception;

    @POST
    @Path("/istanza")
    public Response invioIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@RequestBody IstanzaExtVO istanzaRequest) throws Exception;

    @GET
    @Path("/estrai-istanza/{numeroIstanza}")
    Response estraiIstanzaMude(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;

    @GET
    @Path("/elenco-geo-riferimento/{numeroIstanza}")
    Response getElencoGeoRiferimento(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;

    @GET
    @Path("/fill-in-istanze-indirizzi")
    Response fillInIndirizzi(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID) throws Exception;

}