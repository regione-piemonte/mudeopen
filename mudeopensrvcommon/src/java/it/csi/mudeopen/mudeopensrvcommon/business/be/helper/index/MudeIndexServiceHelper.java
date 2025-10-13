/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.GenericException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.AbstractServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.FileFormatInfo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.LuceneSearchResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Node;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.OperationContext;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.SearchParams;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.VerifyReport;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.factory.MudeIndexFactory;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.EntityLoggingFilter;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.oauth2.OauthHelperIndex;
@Component
public class MudeIndexServiceHelper {

    private static final String LOCAL = "local";

	public static final Logger logger = Logger.getLogger(MudeIndexServiceHelper.class.getCanonicalName());
	
    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();

    public String createFascicoloFolderContent(String name, String parentNodeUid, IndexFolderFascicoloMetadataProperty indexMetadataProperties) {

        logger.debug("[MudeIndexServiceHelper::createFascicoloFolderContent] Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        String uuidFromIndex = null;
        String api = "/tenants/{tenantName}/nodes";
        String url = getUrl(apiEndpoint() + api, tenant(), null);
        logger.debug("[MudeIndexServiceHelper::createFascicoloFolderContent] url : [" + url + "]");
        try {
            Node indexNode = MudeIndexFactory.createFascicoloFolderContentNode(name,indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);

            logger.debug("[MudeIndexServiceHelper::createFascicoloFolderContent] POST :\nurl: [" + url + "]\ntenantName: [" + tenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::createFascicoloFolderContent] ERROR : " + (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.info("[MudeIndexServiceHelper::createFascicoloFolderContent] Creato nodo : " + uuidFromIndex);
            }
        } catch (JsonProcessingException e) {
            logger.error("[MudeIndexServiceHelper::createFascicoloFolderContent] ERROR : ", e);
        }
        return extractIndexUUID(uuidFromIndex);
    }

    public String createIstanzaFolderContent(String name, String parentNodeUid, IndexFolderIstanzaMetadataProperty indexMetadataProperties) {

        logger.debug("[MudeIndexServiceHelper::createIstanzaFolderContent] Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        String uuidFromIndex = null;
        String api = "/tenants/{tenantName}/nodes";
        String url = getUrl(apiEndpoint() + api, tenant(), null);
        logger.debug("[MudeIndexServiceHelper::createIstanzaFolderContent] url : [" + url + "]");
        try {
            Node indexNode = MudeIndexFactory.createIstanzaFolderContentNode(name,indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);

            logger.debug("[MudeIndexServiceHelper::createIstanzaFolderContent] POST :\nurl: [" + url + "]\ntenantName: [" + tenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::createIstanzaFolderContent] ERROR : " + (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.info("[MudeIndexServiceHelper::createIstanzaFolderContent] Creato nodo : " + uuidFromIndex);
            }
        } catch (JsonProcessingException e) {
            logger.error("[MudeIndexServiceHelper::createIstanzaFolderContent] ERROR : ", e);
        }
        return extractIndexUUID(uuidFromIndex);
    }

    public String createPraticaFolderContent(String name, String parentNodeUid, IndexFolderPraticaMetadataProperty indexMetadataProperties) {

        logger.debug("[MudeIndexServiceHelper::createPraticaFolderContent] Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        String uuidFromIndex = null;
        String api = "/tenants/{tenantName}/nodes";
        String url = getUrl(apiEndpoint() + api, tenant(), null);
        logger.debug("[MudeIndexServiceHelper::createPraticaFolderContent] url : [" + url + "]");
        try {
            Node indexNode = MudeIndexFactory.createPraticaFolderContentNode(name,indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);

            logger.debug("[MudeIndexServiceHelper::createPraticaFolderContent] POST :\nurl: [" + url + "]\ntenantName: [" + tenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::createPraticaFolderContent] ERROR : " + (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.info("[MudeIndexServiceHelper::createPraticaFolderContent] Creato nodo : " + uuidFromIndex);
            }
        } catch (JsonProcessingException e) {
            logger.error("[MudeIndexServiceHelper::createPraticaFolderContent] ERROR : ", e);
        }
        return extractIndexUUID(uuidFromIndex);
    }

    public String createAllegatoContent(String name, String parentNodeUid, File file, IndexAllegatoMetadataProperty indexMetadataProperties) {

        logger.debug("[MudeIndexServiceHelper::createAllegatoContent] Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        String uuidFromIndex = null;
        String api = "/tenants/{tenantName}/nodes";
        String url = getUrl(apiEndpoint() + api, tenant(), null);
        logger.debug("[MudeIndexServiceHelper::createAllegatoContent] url : [" + url + "]");
        try {
            Node indexNode = MudeIndexFactory.createFileAllegatoContentNode(name, indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);

            logger.debug("[MudeIndexServiceHelper::createAllegatoContent] POST :\nurl: [" + url + "]\ntenantName: [" + tenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.APPLICATION_JSON_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //500: internal server error
                    //406: .header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::createAllegatoContent] ERROR : " + (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.info("[MudeIndexServiceHelper::createAllegatoContent] Creato nodo : " + uuidFromIndex);
            }
        } catch (JsonProcessingException e) {
            logger.error("[MudeIndexServiceHelper::createAllegatoContent] ERROR : ", e);
        }
        return extractIndexUUID(uuidFromIndex);
    }

    public String createDocumentoContent(String name, String parentNodeUid, File file, IndexDocumentoMetadataProperty indexMetadataProperties) {

        logger.debug("[MudeIndexServiceHelper::createDocumentoContent] Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        String uuidFromIndex = null;
        String api = "/tenants/{tenantName}/nodes";
        String url = getUrl(apiEndpoint() + api, tenant(), null);
        logger.debug("[MudeIndexServiceHelper::createDocumentoContent] url : [" + url + "]");
        try {
            Node indexNode = MudeIndexFactory.createFileDocumentoContentNode(name, indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);

            logger.debug("[MudeIndexServiceHelper::createDocumentoContent] POST :\nurl: [" + url + "]\ntenantName: [" + tenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.APPLICATION_JSON_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //500: internal server error
                    //406: .header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::createDocumentoContent] ERROR : " + (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.info("[MudeIndexServiceHelper::createDocumentoContent] Creato nodo : " + uuidFromIndex);
            }
        } catch (JsonProcessingException e) {
            logger.error("[MudeIndexServiceHelper::createDocumentoContent] ERROR : ", e);
        }
        return extractIndexUUID(uuidFromIndex);
    }

    public String createIstanzaContent(String name, String parentNodeUid, File file, IndexIstanzaMetadataProperty indexMetadataProperties) {

        logger.debug("[MudeIndexServiceHelper::createIstanzaContent] Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        String uuidFromIndex = null;
        String api = "/tenants/{tenantName}/nodes";
        String url = getUrl(apiEndpoint() + api, tenant(), null);
        logger.debug("[MudeIndexServiceHelper::createIstanzaContent] url : [" + url + "]");
        try {
            Node indexNode = MudeIndexFactory.createFileIstanzaContentNode(name, indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);

            logger.debug("[MudeIndexServiceHelper::createIstanzaContent] POST :\nurl: [" + url + "]\ntenantName: [" + tenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::createIstanzaContent] ERROR : " + (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.info("[MudeIndexServiceHelper::createIstanzaContent] Creato nodo : " + uuidFromIndex);
            }
        } catch (JsonProcessingException e) {
            logger.error("[MudeIndexServiceHelper::createIstanzaContent] ERROR : ", e);
        }
        return extractIndexUUID(uuidFromIndex);
    }

//    public String createContent(String name, String parentNodeUid, File file, IndexMetadataProperty indexMetadataProperties) {
//        String result = null;
//        String api = "/tenants/{tenantName}/nodes";
//        String url = IndexFactoryAA.getUrl(apiEndpoint + api, tenant, null);

//        try {
//            Node indexNode = file == null ? IndexFactoryAA.getCreateFolderNode(name) : IndexFactoryAA.getCreateContentNode(name, indexMetadataProperties);
//            String jsonNode = objectMapper.writeValueAsString(indexNode);
//

//
//            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
//            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
//            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
//            if (file != null) {
//                multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
//            }
//            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
//
//
//            Response resp = getBuilder(url)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
//                    .header(HttpHeaders.ACCEPT, "text/plain")
//                    .post(entity);
//            if (resp.getStatus() >= 400) {
//                String error = resp.readEntity(String.class);

//            } else {
//                result = resp.readEntity(String.class);

//            }
//        } catch (JsonProcessingException e) {

//        }
//        return result;
//    }

    public VerifyReport verifySignedDocument(String uid, File file) throws GenericException {

        String api = "/utils/_verify/document/signed";
        String url = apiEndpoint() + api;
        VerifyReport verifyReport = null;
        logger.debug("[MudeIndexServiceHelper::verifySignedDocument] url : [" + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            if (StringUtils.isNotBlank(uid)) {
                multipartForm.addFormData("documentUid", uid, MediaType.TEXT_PLAIN_TYPE);
                multipartForm.addFormData("documentContentPropertyName", "cm:content", MediaType.TEXT_PLAIN_TYPE);
            }
            if (file != null) {
                multipartForm.addFormData("documentBinaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            }
            multipartForm.addFormData("documentStore", "false", MediaType.TEXT_PLAIN_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::verifySignedDocument] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            } else {
                GenericType<VerifyReport> verifyReportGenericType = new GenericType<>() {
                };
                verifyReport = resp.readEntity(verifyReportGenericType);
                logger.debug("[MudeIndexServiceHelper::verifySignedDocument] verifyReport" + (file != null ? " file [" + file.getName() + "]" : "") + (uid != null && StringUtils.isNotBlank(uid) ? " uid [" + uid + "]" : "") + " : \n\n" + verifyReport + "\n\n");
            }

        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::verifySignedDocument] ERROR : ", e);
            throw new GenericException(e);
        }

        return verifyReport;

    }

    public File getExtractedDocumentFromEnvelopeFile(File file) throws GenericException {

        String result = null;
        String api = "/utils/_extractFromEnvelope";
        String url = apiEndpoint() + api;
        logger.debug("[MudeIndexServiceHelper::getExtractedDocumentFromEnvelopeFile] url : [" + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            //multipartForm.addFormData("documentStore", "false", MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::getExtractedDocumentFromEnvelopeFile] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            } else {
                result = extractIndexUUID(resp.readEntity(String.class));
                logger.info("[MudeIndexServiceHelper::createIstanzaContent] Creato nodo : " + result);
            }
        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::getExtractedDocumentFromEnvelopeFile] ERROR : ", e);
            throw new GenericException(e);
        }

        if(StringUtils.isNotBlank(result)) {
            return this.retrieveTempContentData(result);
        }
        else{
            return null;
        }
    }

    // dell'impronta.identifyDocument
    //#################################################################
    private static String escapeString(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public String extractIDFileDocumentFromEnvelope(InputStream fileIn, File file) throws GenericException {

        String api = "/utils/_extractFromEnvelope";
        String url = apiEndpoint() + api;
        String  uuidFromIndex = null;
        logger.debug("[MudeIndexServiceHelper::extractDocumentFromEnvelopeFile] url : [" + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("binaryContent", fileIn, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            Response resp = getTempBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::extractDocumentFromEnvelopeFile] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            } else {
                uuidFromIndex = resp.readEntity(String.class);
                logger.debug("[MudeIndexServiceHelper::extractDocumentFromEnvelopeFile] verifyReport file : \n\n" + uuidFromIndex + "\n\n");
            }
        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::extractDocumentFromEnvelopeFile] ERROR : ", e);
            throw new GenericException(e);
        }

        return extractIndexUUID(uuidFromIndex);
    }

    public FileFormatInfo identifyTempDocument(String pUUID) throws GenericException {
        String api = "/tenants/{tenantName}/nodes/{uid}/contents/_identify/document";
        String lTenant="temp";
        String url = getUrl(apiEndpoint() + api, lTenant, pUUID) + "?";
        url=url+"tenantName="+escapeString("temp");
        url=url+"&store="+escapeString("false");
        url=url+"&uid="+escapeString(pUUID); 
        url=url+"&contentPropertyName=cm:content";        

        FileFormatInfo verifyReport = null;
        logger.debug("[MudeIndexServiceHelper::verifyBinaryDocument] url : [" + url + "]");
        try {            
            Response resp = getTempBuilder(url)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::verifyBinaryDocument] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            } else {
                GenericType<List<FileFormatInfo>> verifyReportGenericType = new GenericType<>() {
                };
                List <FileFormatInfo> listFormatInfo = resp.readEntity(verifyReportGenericType);
                verifyReport=listFormatInfo.get(0);
                logger.debug("[MudeIndexServiceHelper::verifyBinaryDocument] verifyReport file : \n\n" + verifyReport + "\n\n");
            }
        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::verifyBinaryDocument] ERROR : ", e);
            throw new GenericException(e);
        }

        return verifyReport;

    } 

    //#################################################################

    public FileFormatInfo verifyBinaryDocument(InputStream fileIn, File file) throws GenericException {

        String api = "/utils/_identify/document";
        api=api+"?store="+escapeString("false");
        String url = apiEndpoint() + api;
        FileFormatInfo fformatInfo = null;
        logger.debug("[MudeIndexServiceHelper::verifyBinaryDocument] url : [" + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("store", "false", MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("binaryContent", fileIn, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());            
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            Response resp = getTempBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::verifyBinaryDocument] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            } else {
                GenericType<List<FileFormatInfo>> verifyReportGenericType = new GenericType<>() {
                };
                List <FileFormatInfo> listFormatInfo   = resp.readEntity(verifyReportGenericType);
                fformatInfo=listFormatInfo.get(0);
                logger.debug("[MudeIndexServiceHelper::verifyBinaryDocument] verifyReport file : \n\n" + fformatInfo + "\n\n");
            }
        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::verifyBinaryDocument] ERROR : ", e);
            throw new GenericException(e);
        }

        return fformatInfo;

    }      

    //cm:content

    public File retrieveTempContentData(String uid) {

        String api = "/tenants/{tenantName}/nodes/{uid}";
        String lTenant="temp";
        String url = getUrl(apiEndpoint() + api, lTenant, uid) + "/contents?contentPropertyName=cm:content";
        File result = null;
        logger.debug("[MudeIndexServiceHelper::retrieveContentData] url : [" + url + "]");
        Response resp = getTempBuilder(url)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .get();
        if (resp.getStatus() >= 400) {
            String errorMessage = resp.readEntity(String.class);
            logger.error("[MudeIndexServiceHelper::retrieveContentData] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
        } else {
            GenericType<File> fileType = new GenericType<>() {
            };
            result = resp.readEntity(fileType);
            if (null != result) {
                logger.debug("[MudeIndexServiceHelper::retrieveContentData] Recuperato file  : [" + result.getName() + "]");
            }
        }

        return result;
    }

    //public File retrieveTempContentData(String uid) {

    public File retrieveContentData(String uid) {

        String api = "/tenants/{tenantName}/nodes/{uid}";
        String url = getUrl(apiEndpoint() + api, tenant(), uid) + "/contents?contentPropertyName=cm:content";
        File result = null;
        logger.debug("[MudeIndexServiceHelper::retrieveContentData] url : [" + url + "]");
        Response resp = getBuilder(url)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .get();
        if (resp.getStatus() >= 400) {
            String errorMessage = resp.readEntity(String.class);
            logger.error("[MudeIndexServiceHelper::retrieveContentData] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
        } else {
            GenericType<File> fileType = new GenericType<>() {
            };
            result = resp.readEntity(fileType);
            if (null != result) {
                logger.debug("[MudeIndexServiceHelper::retrieveContentData] Recuperato file  : [" + result.getName() + "]");
            }
        }

        return result;
    }

 /*   public String updateContentData(String uid, File file) {

        String api = "/tenants/{tenantName}/nodes/{uid}";
        String url = getUrl(apiEndpoint + api, tenant, uid) + "/contents?contentPropertyName=cm:content";
        String errorMessage = null;
        logger.debug("[MudeIndexServiceHelper::updateContentData] url : [" + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("encoding", "UTF-8", MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("mimeType", file.toURI().toURL().openConnection().getContentType(), MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .put(entity);
            if (resp.getStatus() >= 400) {
                errorMessage = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::updateContentData] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            }
        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::updateContentData] ERROR : ", e);
        }

        return errorMessage;
    }

    public ErrorMessage updateMetadata(String uid, IndexMetadataProperty indexMetadataProperties) {

        String api = "/tenants/{tenantName}/nodes/{uid}";
        String url = getUrl(apiEndpoint + api, tenant, uid);
        ErrorMessage errorMessage = null;
        logger.debug("[MudeIndexServiceHelper::updateMetadata] url : [" + url + "]");

        // Costruzione parametro node
        Node indexNode = MudeIndexFactory.getCreateContentNode(uid, indexMetadataProperties);
        Entity<Node> entity = Entity.json(indexNode);

        Response resp = getBuilder(url)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .put(entity);
        if (resp.getStatus() >= 400) {
            String errMsg = resp.readEntity(String.class);
            logger.error("[MudeIndexServiceHelper::updateMetadata] ERROR : " + (StringUtils.isNotBlank(errMsg) ? errMsg : resp.getStatus()));
            GenericType<ErrorMessage> errorMessageGenericType = new GenericType<>() {
            };
            errorMessage = resp.readEntity(errorMessageGenericType);
        }

        return errorMessage;
    }*/

    public ErrorMessage updateNodeMetadata(Node indexNode) {

        String api = "/tenants/{tenantName}/nodes/{uid}";
        String url = getUrl(apiEndpoint() + api, tenant(), indexNode.getUid());
        ErrorMessage errorMessage = null;
        logger.debug("[MudeIndexServiceHelper::updateMetadata] url : [" + url + "]");

        // Costruzione parametro node
        Entity<Node> entity = Entity.json(indexNode);

        Response resp = getBuilder(url)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .put(entity);
        if (resp.getStatus() >= 400) {
            String errMsg = resp.readEntity(String.class);
            logger.error("[MudeIndexServiceHelper::updateMetadata] ERROR : " + (StringUtils.isNotBlank(errMsg) ? errMsg : resp.getStatus()));
            GenericType<ErrorMessage> errorMessageGenericType = new GenericType<>() {
            };
            errorMessage = resp.readEntity(errorMessageGenericType);
        }

        return errorMessage;
    }

    public ErrorMessage deleteContent(String uid) {

        ErrorMessage errorMessage = null;
        String api = "/tenants/{tenantName}/nodes/{uid}";
        String url = getUrl(apiEndpoint() + api, tenant(), uid);
        logger.debug("[MudeIndexServiceHelper::deleteContent] url : [" + url + "]");
        try {
            Entity<String> entity = Entity.json(objectMapper.writeValueAsString("DELETE_AND_PURGE"));
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .build("DELETE", entity)
                    .invoke();
            if (resp.getStatus() >= 201) {
                String errMsg = resp.readEntity(String.class);
                logger.error("[MudeIndexServiceHelper::deleteContent] ERROR : " + (StringUtils.isNotBlank(errMsg) ? errMsg : resp.getStatus()));
                GenericType<ErrorMessage> errorMessageGenericType = new GenericType<>() {
                };
                errorMessage = resp.readEntity(errorMessageGenericType);
            }
        } catch (Exception e) {
            logger.error("[MudeIndexServiceHelper::deleteContent] ERROR : ", e);
            errorMessage = new ErrorMessage();
            errorMessage.setStatus(500);
            errorMessage.setTitle(e.getMessage());
        }

        return errorMessage;
    }

    public Node getContentMetadata(String uid) {

        String api = "/tenants/{tenantName}/nodes/{uid}";
        String url = getUrl(apiEndpoint() + api, tenant(), uid);
        Node indexNode = null;
        logger.debug("[MudeIndexServiceHelper::getContentMetadata] url : [" + url + "]");
        Response resp = getBuilder(url)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .get();
        if (resp.getStatus() >= 400) {
            String errorMessage = resp.readEntity(String.class);
            logger.error("[MudeIndexServiceHelper::getContentMetadata] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
        } else {
            GenericType<Node> nodo = new GenericType<>() {
            };
            indexNode = resp.readEntity(nodo);
            logger.debug("[MudeIndexServiceHelper::getContentMetadata] " + resp.getStatus() + " : \n" + nodo.toString() + "\n");
        }

        return indexNode;
    }

    public List<Node> luceneSearch(Boolean metadata, SearchParams searchParams) {

        String api = "/tenants/{tenantName}/nodes/_search";
        String url = getUrl(apiEndpoint() + api, tenant(), null) + (metadata ? "?metadata=true" : "");
        List<Node> indexNodeList = null;
        logger.debug("[MudeIndexServiceHelper::luceneSearch] url : [" + url + "]");
        Entity<SearchParams> entity = Entity.json(searchParams);
        Response resp = getBuilder(url)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            String errorMessage = resp.readEntity(String.class);
            logger.error("[MudeIndexServiceHelper::luceneSearch] ERROR : " + (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
        } else {
            GenericType<LuceneSearchResponse> luceneSearchResponseGenericType = new GenericType<>() {
            };
            LuceneSearchResponse luceneSearchResponse = resp.readEntity(luceneSearchResponseGenericType);
            indexNodeList = luceneSearchResponse != null && luceneSearchResponse.getNodes() != null ? luceneSearchResponse.getNodes() : new ArrayList<>();
            logger.debug("[MudeIndexServiceHelper::luceneSearch] " + resp.getStatus() + " : \n" + luceneSearchResponseGenericType.toString() + "\n");
        }

        return indexNodeList;
    }

    public void copyContentBetweenTenant(String uid) {
        // POST /tenants/{tenantName}/nodes/{uid}/_copyBetweenTenant
    }

    private Invocation.Builder getBuilder(String url) {
        Client client = ClientBuilder.newBuilder().build();

        if(Constants._environment.equals(LOCAL) || 
        		"enabled".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("INDEX_CALL_TRACE", "disabled")))
	        client.register(new EntityLoggingFilter("GEECO CALL TRACE ["+url+"]"));

        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getToken(tokenUrl(), consumerKey(), consumerSecret(), isApiManagerTokenChacheEnabled()))
                .header("X-Request-Auth", xRequestAuth());
    }

    private Invocation.Builder getTempBuilder(String url) {
        Client client = ClientBuilder.newBuilder().build();
        if(Constants._environment.equals(LOCAL) || 
        		"enabled".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("INDEX_CALL_TRACE", "disabled")))
	        client.register(new EntityLoggingFilter("GEECO CALL TRACE ["+url+"]"));

        String lxRequestAuth = null;
        try {
        	lxRequestAuth = getXRequestAuth("admin", "admin", "temp", null, "primary");
		} catch (JsonProcessingException e) {
			logger.debug("[MudeIndexServiceHelper::getTempBuilder] " + e.getMessage());
		}
        return client.target(url).request()
                //.header(HttpHeaders.AUTHORIZATION, "Bearer eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJhZG1pbiIsInJlcG9zaXRvcnkiOiJwcmltYXJ5IiwidGVuYW50IjoidGVtcCJ9")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getToken(tokenUrl(), consumerKey(), consumerSecret(), isApiManagerTokenChacheEnabled()))
                .header("X-Request-Auth", lxRequestAuth);
    }

    private String extractIndexUUID(String uuidFromIndex) {
    	try {
            JSONObject jsonUuidFromIndex = new JSONObject(uuidFromIndex);
            if (jsonUuidFromIndex.has("output"))
            	return (String)jsonUuidFromIndex.get("output");
        } catch (JSONException e) {
			logger.debug("[MudeIndexServiceHelper::extractIndexUUID] " + e.getMessage());
        }

    	return null;
    }

    //private String endPoint() { return mudeCProprietaRepository.getValueByName("INDEX_ENDPOINT", "http://tst-api-piemonte.ecosis.csi.it"); }
    private String tokenUrl() { return mudeCProprietaRepository.getValueByName("INDEX_TOKEN_ENDPOINT", "https://tst-api-piemonte.ecosis.csi.it/token"); }
    private String apiEndpoint() { return mudeCProprietaRepository.getValueByName("INDEX_SERVICES_ENDPOINT", "http://tst-api-piemonte.ecosis.csi.it/documentale/indexcsiexp-exp01/v1"); }
    private String consumerKey() { return mudeCProprietaRepository.getValueByName("INDEX_CUSTOMER_KEY", "Ai1w8Gnt_FLP97rvhcKMGMf2ObUa"); }
    private String consumerSecret() { return mudeCProprietaRepository.getValueByName("INDEX_CONSUMER_SECRET", "pyG1MG6wogJ8cfOZfxXfqLNxyE4a"); }
    private String tenant() { return mudeCProprietaRepository.getValueByName("INDEX_TENANT", "mude"); };
    public boolean isApiManagerTokenChacheEnabled() {return "abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("API_MANAGER_TOKEN_CACHE", "abilitato")) ;}
	private int getMaxTimeout() { return Integer.parseInt(mudeCProprietaRepository.getValueByName("INDEX_MAX_TOKEN_LEASE_MS", "-1")); } // -1 disabled by default, it takes the expires from token
    
    private String xRequestAuth() {
    	try {
        	return getXRequestAuth(
        		    mudeCProprietaRepository.getValueByName("INDEX_USERNAME", "admin"), 
        		    mudeCProprietaRepository.getValueByName("INDEX_PASSWORD", "mude"), 
        		    tenant(), tenant(), 
        		    mudeCProprietaRepository.getValueByName("INDEX_REPOSITORY", "primary")); 
		} catch (JsonProcessingException e) {

			return null;
		}
    }

    public String getToken(String tokenUrl, String consumerKey, String consumerSecret, boolean isTokenCacheEnabled) {
        OauthHelperIndex oauthHelper = new OauthHelperIndex(
                tokenUrl,
                consumerKey,
                consumerSecret,
                getMaxTimeout(),
                isTokenCacheEnabled);
        String token = oauthHelper.getToken();
        logger.debug("IndexFactory::getToken : " + token);
        return token;
    }

    public static OperationContext getOperationContext(String username, String password, String tenant, String fruitore, String repository) {
        final OperationContext operationContext = new OperationContext();
        operationContext.setUsername(username);
        operationContext.setPassword(password);
        operationContext.setTenant(tenant);
        operationContext.setFruitore(fruitore);
        operationContext.setRepository(repository);
        return operationContext;
    }

    public static String getXRequestAuth(String username, String password, String tenant, String fruitore, String repository) throws JsonProcessingException {
        OperationContext operationContext = getOperationContext(username, password, tenant, fruitore, repository);
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();
        String jsonOperationContext = objectMapper.writeValueAsString(operationContext);

        return new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));
    }

    public static String getUrl(String apiUrl, String tenant, String uid) {
        return apiUrl
                .replaceAll("\\{" + "tenantName" + "\\}", escapeString(tenant))
                .replaceAll("\\{" + "uid" + "\\}", StringUtils.isNotBlank(uid) ? escapeString(uid) : uid);
    }

}