/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;

import com.google.common.collect.Lists;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.AbstractServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ConfigurazioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.configurazione.ConfigurazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.ValidationResultEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileServiceHelper {
    private static final String ERRORE_VALIDAZIONE = "errore_validazione";

	private static Logger logger = Logger.getLogger(FileServiceHelper.class.getCanonicalName());

    @Autowired
    private ConfigurazioneService configurazioneService;

    @Autowired
    private IndexManager indexManager;

    static public String getFileName(MultipartFormDataInput formDataInput, String fileFieldName) {
        String fileName = "unknown";
        try {
            Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
            List<InputPart> inputParts = uploadForm.get(fileFieldName);
            MultivaluedMap<String, String> header = inputParts != null ? inputParts.get(0).getHeaders() : null;
            assert header != null;
            String[] contentDisposition = Normalizer
                    .normalize(header.getFirst("Content-Disposition"), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").split(";");
            for (String filename : contentDisposition) {
                if ((filename.trim().startsWith("filename"))) {
                    String[] name = filename.split("=");
                    fileName = name[1].trim().replace("\"", "");
                }
            }
        } catch (Exception e) {
            logger.debug("[AllegatiApiServiceHelper::getFileName] ERROR : ", e);
        }
        return fileName;
    }

    public ErrorResponse validateFileExtension(String filename) {
        ConfigurazioneVO confTypes = configurazioneService.findConfigurazioneByName(Constants.CONFIG_KEY_ALLEGATI_ALLOWED_FILE_EXTENSIONS);
        String[] allowedFileTypes = confTypes.getValore().split(",");
        ErrorResponse error = null;

        if (!Arrays.stream(allowedFileTypes).anyMatch(entry -> filename.toLowerCase().endsWith(entry.toLowerCase()))) {
            Map<String, String> detail = new HashMap<>();
            detail.put("file", "Tipo file non consentito.");
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Tipo file non consentito.", detail, null);
        }

        if (error == null && filename.toLowerCase().endsWith(".p7m")) {
            String[] temp = filename.split("\\.");
            List<String> fileExt = Lists.newArrayListWithExpectedSize(temp.length - 1);
            for (int i = 1; i < temp.length; i++) {
                fileExt.add("." + temp[i].toLowerCase());
            }
            if (fileExt.size() <= 1) {
                Map<String, String> detail = new HashMap<>();
                detail.put("file", "Il file firmato contiene un tipo file non consentito.");
                error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Tipo file non consentito.", detail, null);
            }
            if (fileExt.stream().noneMatch(entry -> Arrays.asList(allowedFileTypes).contains(entry.toLowerCase()))) {
                Map<String, String> detail = new HashMap<>();
                detail.put("file", "Il file firmato contiene un tipo file non consentito.");
                error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Tipo file non consentito.", detail, null);
            }
        }
        return error;
    }

    public ErrorResponse valideteFileLength(File file) {
        ConfigurazioneVO confSize = configurazioneService.findConfigurazioneByName(Constants.CONFIG_KEY_ALLEGATI_MAX_SIZE);
        Long maxFileSize = Long.valueOf(confSize.getValore());
        Long maxSizeInByte = maxFileSize * 1024 * 1024;
        ErrorResponse error = null;
        //            byte[] bytes = FileUtils.readFileToByteArray(file);
        //            if(bytes.length > maxSizeInByte){
        if (file.length() > maxSizeInByte) {
            Map<String, String> detail = new HashMap<>();
            detail.put("file", "File troppo grande. Dimensione massima consentita " + maxFileSize + "MB");
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "File troppo grande. Dimensione massima consentita " + maxFileSize + "MB", detail, null);
        }
        return error;
    }

    private String getContentType(String filename) {
        String g = URLConnection.guessContentTypeFromName(filename);
        if (g == null) {
            g = URLConnection.getFileNameMap().getContentTypeFor(filename);
        }
        return g;
    }

    public ErrorResponse verifyDuplicateFilename(String codFascicolo, String codIstanza, String filename, Boolean filenameAlreadyExists) {
        ErrorResponse error = null;
        Map<String, String> details = new HashMap<>();

        if (filenameAlreadyExists) {
            details.put("nomeFile", ValidationResultEnum.DUPLICATE.getDescription());
        } else {
            if (indexManager.indexPathExist(codFascicolo, codIstanza, filename)) {
                details.put("nomeFile", ValidationResultEnum.DUPLICATE.getDescription());
            }
        }
        if (!details.isEmpty()) {
            //error = errorManager.getError("400", "E021", "Attenzione: la denominazione dell’allegato che stai caricando è già stata usata per un documento caricato in precedenza.", null, null);
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Attenzione: la denominazione dell’allegato ('"+filename+"') che stai caricando è già stata usata per un documento caricato in precedenza.", details, null);
        }
        return error;
    }

    public String calculateFileHash(byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(content);
        byte[] hash = digest.digest();
        String encoded = Base64.getEncoder().encodeToString(hash);
        return encoded;
    }
    
    public String calculateFileHashHex(byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(content);
        byte[] hash = digest.digest();
        String hashHex = "";
        for(int j = 0; j<hash.length;j++)
        	hashHex += String.format("%02X", hash[j]);
        //String encoded = Base64.getEncoder().encodeToString(hash);
        return hashHex;
    }

    public ErrorResponse checkFileHash(final String hash, final byte[] fileBytes) {
        ErrorResponse error = null;
        Map<String, String> details = new HashMap<>();
        try {
            String fileHash = calculateFileHash(fileBytes);
            if (!hash.equals(fileHash)) {
                details.put("file", ValidationResultEnum.INVALID.getDescription());
                error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Attenzione: l'impronta del file dell'istanza firmato non coincide con l'impronta del file dell'istanza scaricato", details, null);
            }
        } catch (NoSuchAlgorithmException e) {
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, "Errore nella verifica delle impronte dei files", details, null);
        }
        return error;
    }
}