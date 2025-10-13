/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.updownfile.FileDTO;
import it.csi.mudeopen.mudeopensrvcommon.util.updownfile.UpDownFileUtil;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

//@Component
public class FilesApiServiceImpl /*implements AllegatiApi*/ {

    //@Override
/*    public Response uploadFileOLD(MultipartFormDataInput input, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {

        if(input.getParts().size()>0) {
            StringBuilder s = new StringBuilder();
            for (int i=0; i<input.getParts().size(); i++) {
                String bodyAsString;
                try {
                    bodyAsString = "[["+input.getParts().get(i).getBodyAsString()+"]]";
                } catch (IOException e) {
                    bodyAsString = "[[error wile parsing body: "+e+"]]";
                }
                s.append("part[").append(i).append("]-media: ").append(input.getParts().get(i).getMediaType()).append(", body as string:").append(bodyAsString);
            }
            return Response.ok(s.toString()).build();
        }
        else {
            ErrorDTO err = new ErrorDTO("400","", "elenco di file vuoto",null, null);
            return Response.serverError().entity(err).status(400).build();
        }
    }*/

    //    @Override
    public Response uploadFile(MultipartFormDataInput input, String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if(input.getParts().size()>0) {
            FileDTO fileDTO = UpDownFileUtil.uploadFile(input, "uploadedFile", null);
//            return Response.ok(fileDTO.getBody()).header("Content-Disposition", "attachment; filename="+ fileDTO.getFileName()).build();
            return Response.noContent().build();
        }
        else {

            return Response.serverError().status(500).build();
        }
    }

    //    @Override
    public Response deleteFile(String uidIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return Response.noContent().build();
    }

}