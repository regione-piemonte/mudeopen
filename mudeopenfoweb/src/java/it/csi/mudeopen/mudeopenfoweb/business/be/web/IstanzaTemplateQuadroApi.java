
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.IstanzaTemplateQuadroRequest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * The interface Istanza template quadro api.
 */
@Path("/istanza-template-quadri")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface IstanzaTemplateQuadroApi {

    /**
     * Load istanza template quadro by idistanza response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
	@Path("/id-istanza/{idIstanza}")
	Response loadIstanzaTemplateQuadroByIdistanza(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idIstanza") Long idIstanza,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Gets istanza template quadro.
     *
     * @param userCf           the user cf
     * @param XRequestId       the x request id
     * @param XForwardedFor    the x forwarded for
     * @param idIstanza        the id istanza
     * @param idTemplateQuadro the id template quadro
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the istanza template quadro
     */
    @GET
	@Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
	Response getIstanzaTemplateQuadro(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro);

    /**
     * Gets template quadri by code template and id istanza.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param codeTemplate    the code template
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the template quadri by code template and id istanza
     */
    @GET
	@Path("/code-template/{codeTemplate}/id-istanza/{idIstanza}")
	@Produces({ "application/json" })
	Response getTemplateQuadriByCodeTemplateAndIdIstanza(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("codeTemplate") String codeTemplate,
			@PathParam("idIstanza") Long idIstanza,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);


    /**
     * Save istanza templete quadro response.
     *
     * @param userCf                  the user cf
     * @param XRequestId              the x request id
     * @param XForwardedFor           the x forwarded for
     * @param istanzaTemplateQuadroVO the istanza template quadro vo
     * @param securityContext         the security context
     * @param httpHeaders             the http headers
     * @param httpRequest             the http request
     * @return the response
     */
    @POST
	@Consumes({ "application/json" })
	Response saveIstanzaTempleteQuadro(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@RequestBody IstanzaTemplateQuadroRequest istanzaTemplateQuadroVO,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);


    /*
    @PUT
	@Consumes({ "application/json" })
	Response updateIstanzaTempleteQuadro(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@RequestBody IstanzaTemplateQuadroRequest istanzaTemplateQuadroVO,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);


	@DELETE
	@Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
	public Response deleteIstanzaTemplateQuadro(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders ,
			@Context HttpServletRequest httpRequest);

*/

}