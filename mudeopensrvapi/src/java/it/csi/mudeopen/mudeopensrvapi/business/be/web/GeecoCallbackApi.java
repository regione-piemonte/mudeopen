/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web;

import it.csi.ecogis.util.dto.GeoJSONFeature;
import it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml.EditedFeature;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

// /mudeopen/ws-api/rest
@Path("/geeco")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface GeecoCallbackApi {

	
	@Path("/{environment}/{id_editing_session}/layers/{layer_id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response insertFeatureForEditingLayer(
    			@PathParam("environment") final String environment,
    			@PathParam("layer_id") final String layerId,
    			@PathParam("id_editing_session") final String idEditingSession, 
    			final EditedFeature feature,
    			@Context final SecurityContext securityContext, 
    			@Context final HttpHeaders httpHeaders);	
    @PUT
    @Path("/{environment}/{id_editing_session}/layers/{layer_id}/{feature_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateFeatureForEditingLayer(
    			@PathParam("environment") final String environment,
    			@PathParam("layer_id") final String layerId,
    			@PathParam("id_editing_session") final String idEditingSession, 
    			@PathParam("feature_id")  final String  featureId, 
    			GeoJSONFeature feature,
    			@Context final SecurityContext securityContext, 
    			@Context final HttpHeaders httpHeaders);
    @DELETE
    @Path("/{environment}/{id_editing_session}/layers/{layer_id}/{feature_id}")
    @Produces({"application/json"})
    public Response deleteFeatureForEditingLayer(
			@PathParam("environment") final String environment,
			@PathParam("layer_id") final String layerId,
			@PathParam("id_editing_session") final String idEditingSession, 
			@PathParam("feature_id")  final String  featureId, 
			GeoJSONFeature feature,
			@Context final SecurityContext securityContext, 
			@Context final HttpHeaders httpHeaders    
	);
    
    @GET
    @Path("/{environment}/{id_editing_session}/layers/{layer_id}/test-resources")
    @Produces({"application/json"})
    public Response testEditingLayer(
			@PathParam("environment") final String environment,
			@PathParam("layer_id") final String layerId,
			@PathParam("id_editing_session") final String idEditingSession, 
			@Context final SecurityContext securityContext, 
			@Context final HttpHeaders httpHeaders    
	);

}
