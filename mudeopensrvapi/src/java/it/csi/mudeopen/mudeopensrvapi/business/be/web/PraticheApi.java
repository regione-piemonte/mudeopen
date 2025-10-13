
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * The interface Pratiche api.
 */
@Path("")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface PraticheApi {
    /**
     * Recupera pratiche response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param filter          the filter
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
      the response
     */
    @GET
    @Path("/pratiche/{codIstatComune}")
    @Produces({"application/json"})
    Response ricercaPratiche(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    				@PathParam("codIstatComune") String istatComune, @QueryParam("numeroPratica") String numeroPratica, @QueryParam("annoPratica") String annoPratica) throws Exception;

    /**
     * Recupera documenti pratica response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
      the response
     */
    @GET
    @Path("/elenco-documenti-pratica/{codIstat}")
    @Produces({"application/json"})
    Response ricercaElencoDocumentiPratica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("codIstat") String istatComune,
            @QueryParam("numeroPratica") String numeroPratica, 
            @QueryParam("annoPratica") String annoPratica) throws Exception;

    @POST
    //@Consumes({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    @Consumes({"multipart/form-data"})
    @Path("/upload-documento-pratica")
    Response allegaDocumentoPratica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@MultipartForm MultipartFormDataInput input) throws Exception;

    @GET
    @Path("/documento-pratica/{codIstatComune}/{numeroPraticaComunale}/{anno}/{nomeDocumento}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response estraiDocumentoPratica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("codIstatComune") String istatComune, 
    		@PathParam("numeroPraticaComunale") String numeroPratica, 
    		@PathParam("anno") String annoPratica,
    		@PathParam("nomeDocumento") String nomeDocumento) throws Exception;

    @DELETE
    @Path("/elimina-documento-pratica/{codIstatComune}/{numeroPraticaComunale}/{anno}/{nomeDocumento}")
    Response eliminaDocumentoPratica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("codIstatComune") String istatComune, 
    		@PathParam("numeroPraticaComunale") String numeroPratica, 
    		@PathParam("anno") String annoPratica,
    		@PathParam("nomeDocumento") String nomeDocumento) throws Exception;

}