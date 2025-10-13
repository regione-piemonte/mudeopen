/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ModelliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.PdfService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.ModelliApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;

@Component
public class ModelliApiServiceImpl extends AbstractApi implements ModelliApi {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

	@Autowired
    private ModelliService modelliService;

    @Autowired
    private PdfService pdfService;

    @Override
    public Response loadModelli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<ModelloVO> vos = modelliService.loadAll();
        return Response.ok().entity(vos).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vos)).build();
    }

    @Override
    public Response loadModello(Long idModello, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        ModelloVO vo = modelliService.loadById(idModello);
        if (null == vo) {
            ErrorResponse error = new ErrorResponse("404", "", "Elemento non trovato con id [" + idModello + "]", null, null);

            logger.error("[ModelliApiServiceImpl::loadModello] ERROR : idModello [" + idModello + "]\n" + error);

            return Response.serverError().entity(error).status(404).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }

        return Response.ok().entity(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
    }

    @Override
    public Response loadModelloByDenominazione(String denomModello, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        ModelloVO vo = modelliService.loadBydenominazione(denomModello);
        if (null == vo) {
            ErrorResponse error = new ErrorResponse("404", "", "Elemento non trovato con denominazione [" + denomModello + "]", null, null);

            logger.error("[ModelliApiServiceImpl::loadModelloByDenominazione] ERROR : denomModello [" + denomModello + "]\n" + error);

            return Response.serverError().entity(error).status(404).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }

        return Response.ok().entity(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
    }

    @Override
    public Response downloadModello(Long idModello, Long idIstanza, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        String filename = null;
        byte[] bytes = null;
        try {
            ModelloVO vo = modelliService.loadById(idModello);

            InputStream in = null;
            try {
                if (vo.getDimensioneFile() != null) {
                    // CONTENT READ FROM DB
                    filename = vo.getId() + "_-_" + vo.getPathModello();
                    bytes = vo.getFileContent();

                    if(idIstanza != null)
                    	in = new ByteArrayInputStream(bytes);
                } else {

                    String pathModello = vo.getPathModello();
                    if (pathModello.startsWith("/")) {
                        pathModello = pathModello.substring(1);
                    }

                    String filepath = pdfService.getTemplateDir() + pathModello;

                    Integer startIndex = (pathModello.indexOf("\\") > 0 ? pathModello.replaceAll("\\\\", "/").lastIndexOf("/") : pathModello.lastIndexOf("/")) + 1;
                    filename = /*vo.getId() + "_-_" + */pathModello.substring(startIndex, pathModello.length());

                    //inputstream del file del modello
                    in = pdfService.getClass().getClassLoader().getResourceAsStream(filepath);
                }

                //se è presente l'idIstanza il modello va precompilato.
                if (null != idIstanza && "modello allegato template".equalsIgnoreCase(vo.getDenominazione())) {
                    byte[] compiled = pdfService.getCompiledProcuraPDF(idIstanza, in, filename.toLowerCase().endsWith(".pdf"));

                    return Response.ok(compiled, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").header(HttpHeaders.CONTENT_LENGTH, compiled.length).build();
                }

                if (null == vo.getFileContent()) {
                    //bytearray del file del modello
                    assert in != null;
                    bytes = in.readAllBytes();
                }
            } finally {
                if (in != null)
                    try {
                        in.close();
                    } catch (Exception skip) {
                    }
            }

            if(bytes == null)
            	return null;

            return Response.ok(bytes, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").header(HttpHeaders.CONTENT_LENGTH, bytes.length).build();
        } catch (Throwable t) {
            ErrorResponse error = new ErrorResponse("500", "", "Errore inaspettato nella gestione della richiesta. Riprova a breve.", null, null);
            logger.debug("[ModelliApiServiceImpl::downloadModello] ERROR :" + t.getMessage());

            return Response.serverError().entity(error).status(500).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }
    }
}