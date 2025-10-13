/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util.updownfile;

import org.apache.commons.io.IOUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class UpDownFileUtil {

    private static final Logger logger = Logger.getLogger(UpDownFileUtil.class.getCanonicalName());

    public static FileDTO uploadFile(MultipartFormDataInput formDataInput, String inputVariableName, String uploadFilePath){
        FileDTO fileDTO = new FileDTO();
        String fileName = "";
        Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get(inputVariableName);

        byte [] bytes = null;
        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                bytes = IOUtils.toByteArray(inputStream);
                fileDTO.setFileName(fileName);
                fileDTO.setBody(bytes);
                if(StringUtils.isNotBlank(uploadFilePath)){
                    fileName = uploadFilePath + fileName;
                    writeFile(bytes,fileName);
                }
            } catch (IOException e) {
                logger.error("[[UpDownFileUtil::uploadFile] ERROR : ", e);
            }
        }
        return fileDTO;
    }

    public static FileDTO downloadFile(){

        return null;
    }

    private static String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replace("\"", "");
            }
        }
        return "unknown";
    }

    private static void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists())
            file.createNewFile();

        try(FileOutputStream fop = new FileOutputStream(file)) {
	        fop.write(content);
	        fop.flush();
        }
    }

}