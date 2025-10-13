/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.ModelliApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ModelliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.PdfService;
/**
 * The type Modelli api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;
@Component
public class ModelliApiServiceImpl extends BaseAPI<ModelliApi> implements ModelliApi {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private static final String ERRORE_INTERNO = "errore_interno";

	@Autowired
    private ModelliService modelliService;

    @Autowired
    private PdfService pdfService;

    @Override
    public Response loadModelli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<ModelloVO> vo = modelliService.loadAll();
        return voToResponse(vo, 200);
    }

    @Override
    public Response loadModello(Long idModello, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        ModelloVO vo = modelliService.loadById(idModello);
        if (null == vo)
        	throw new BusinessException("Elemento non trovato con id [" + idModello + "]", "404", ERRORE_INTERNO, null);

        return voToResponse(vo, 200);
    }

    @Override
    public Response loadModelloByDenominazione(String denomModello, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        ModelloVO vo = modelliService.loadBydenominazione(denomModello);
        if (null == vo)
        	throw new BusinessException("Elemento non trovato con denominazione [" + denomModello + "]", "404", ERRORE_INTERNO, null);

        return voToResponse(vo, 200);
    }

    @Override
    public Response downloadModello(Long idModello, Long idIstanza, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String filename = null;
        byte[] bytes = null;

        try {
            ModelloVO vo = modelliService.loadById(idModello);

            InputStream in = null;
            try {
                if (vo.getDimensioneFile() != null) {
                    // CONTENT READ FROM DB
                    filename = vo.getPathModello();
                    bytes = vo.getFileContent();

                    if(idIstanza != null)
                    	in = new ByteArrayInputStream(bytes);
                } else {

                    String pathModello = vo.getPathModello();
                    if (pathModello.startsWith("/")) {
                        pathModello = pathModello.substring(1);
                    }

                    String filepath = pdfService.getTemplateDir() + pathModello;

                    Integer startIndex = (pathModello.indexOf("\\") > -1 ? pathModello.replace("\\\\", "/").lastIndexOf("/") : pathModello.lastIndexOf("/")) + 1;
                    filename = pathModello.substring(startIndex, pathModello.length());

                    //inputstream del file del modello
                    in = pdfService.getClass().getClassLoader().getResourceAsStream(filepath);
                }

                if(filename.startsWith("-"))
                	filename = filename.substring(1); // used to download precompiled pdf
                //se è presente l'idIstanza il modello va precompilato.
                else if(null != idIstanza && ("modello allegato template".equalsIgnoreCase(vo.getDenominazione()) || filename.toLowerCase().endsWith(".pdf"))) { 
                    byte[] compiled = pdfService.getCompiledProcuraPDF(idIstanza, in, filename.toLowerCase().endsWith(".pdf"));

                    return Response.ok(compiled, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").header(HttpHeaders.CONTENT_LENGTH, compiled.length).build();
                }

                bytes = readAllFile(bytes, vo, in);
            } finally {
                if(in != null)
                    try { in.close(); } catch (Exception skip) { }
            }

            if(bytes == null)
            	return null;

            return Response.ok(bytes, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").header(HttpHeaders.CONTENT_LENGTH, bytes.length).build();
        } catch (Exception t) {
        	throw new BusinessException("Errore inaspettato nella gestione della richiesta. Riprova a breve.", "500", ERRORE_INTERNO, null);
        }
    }

	private byte[] readAllFile(byte[] bytes, ModelloVO vo, InputStream in) throws IOException {
		if (null == vo.getFileContent()) {
		    //bytearray del file del modello
		    assert in != null;
		    bytes = in.readAllBytes();
		}
		return bytes;
	}    
}