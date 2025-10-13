/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.ecogis.geeco_integration_api.interfaces.SaveFeatureApiMonitoringAware;
import it.csi.ecogis.util.dto.GeoJSONFeature;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvapi.vo.geeco.GeecoSelectedPropertiesVO;
import org.apache.log4j.Logger;

import java.util.Map;

public class SaveFeatureApiMonitoringAwareImpl implements SaveFeatureApiMonitoringAware {

    private static Logger logger = Logger.getLogger(SaveFeatureApiMonitoringAwareImpl.class.getCanonicalName());

    @Override
    public Response insertFeatureForEditingLayer(String s, String s1, String s2, String idEditingSession, GeoJSONFeature geoJSONFeature, SecurityContext securityContext, HttpHeaders httpHeaders) {
        logger.info("SaveFeatureApiMonitoringAwareImpl::[insertFeatureForEditingLayer]");
        logger.info("SaveFeatureApiMonitoringAwareImpl::apiVersion :: "+s);
        logger.info("SaveFeatureApiMonitoringAwareImpl::environment :: "+s1);
        logger.info("SaveFeatureApiMonitoringAwareImpl::layerId :: "+s2);
        logger.info("SaveFeatureApiMonitoringAwareImpl::idEditingSession :: "+idEditingSession);
        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: "+geoJSONFeature);

        /*
        String featureId=geoJSONFeature.getId();
        String featureType=geoJSONFeature.getType();
        String featureGeometryType=geoJSONFeature.getGeometry().getGeometryType();

        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: featureId:: "+featureId);
        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: featureType:: "+featureType);
        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: featureGeometryType:: "+featureGeometryType);
        */
        //@TODO: gestire anche il KO!!!!!
        return Response.ok(geoJSONFeature).build();
    }

    @Override
    public Response updateFeatureForEditingLayer(String apiVersion, String environment, String layerId,
                                                 String featureId, String idEditingSession, GeoJSONFeature geoJSONFeature,
                                                 SecurityContext securityContext, HttpHeaders httpHeaders) {
        logger.info("SaveFeatureApiMonitoringAwareImpl::[updateFeatureForEditingLayer]");
        logger.info("SaveFeatureApiMonitoringAwareImpl::apiVersion :: "+apiVersion);
        logger.info("SaveFeatureApiMonitoringAwareImpl::environment :: "+environment);
        logger.info("SaveFeatureApiMonitoringAwareImpl::layerId :: "+layerId);
        logger.info("SaveFeatureApiMonitoringAwareImpl::idEditingSession :: "+idEditingSession);
        logger.info("SaveFeatureApiMonitoringAwareImpl::featureId :: "+featureId);

        String lsFeatureId=geoJSONFeature.getId();
        String featureType=geoJSONFeature.getType();
        String featureGeometryType=geoJSONFeature.getGeometry().getGeometryType();

        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: featureId:: "+featureId);
        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: lsFeatureId:: "+lsFeatureId);
        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: featureType:: "+featureType);
        logger.info("SaveFeatureApiMonitoringAwareImpl::feature :: featureGeometryType:: "+featureGeometryType);

        return Response.ok().build();
    }

    @Override
    public Response deleteFeatureForEditingLayer(String apiVersion, String environment, String layerId,
                                                 String featureId, String idEditingSession, SecurityContext securityContext, HttpHeaders httpHeaders) {
        logger.info("SaveFeatureApiMonitoringAwareImpl::[deleteFeatureForEditingLayer]");
        logger.info("SaveFeatureApiMonitoringAwareImpl::apiVersion :: "+apiVersion);
        logger.info("SaveFeatureApiMonitoringAwareImpl::environment :: "+environment);
        logger.info("SaveFeatureApiMonitoringAwareImpl::layerId :: "+layerId);
        logger.info("SaveFeatureApiMonitoringAwareImpl::featureId :: "+featureId);
        logger.info("SaveFeatureApiMonitoringAwareImpl::idEditingSession :: "+idEditingSession);

        return Response.ok().build();
    }

    @Override
    public Response testResources() {
        return null;
    }
}
