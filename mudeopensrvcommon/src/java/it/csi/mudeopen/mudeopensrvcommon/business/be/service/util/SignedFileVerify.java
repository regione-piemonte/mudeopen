/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.FileFormatInfo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.VerifyReport;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ConfigurazioneService;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.configurazione.ConfigurazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
@Component
public class SignedFileVerify extends AbstractApi {
    private static final String L_ESTENSIONE_DEL_FILE_FIRMATO_NON_PDF_SONO_CONSENTITI_SOLO_PDF = "l'estensione del file firmato NON  pdf: sono consentiti solo PDF.";

	private static final String FIRMA_NON_VALIDA = "Firma non valida";
	private static final String ESTENSIONE_DEL_FILE_NON_PDF = "Estensione del file non PDF";

	private static final String ESTENSIONE_DEL_FILE_NON_INDIVIDUATA = "Estensione del file non individuata";
	private static final String ERRORE_VALIDAZIONE = "errore_validazione";

    @Autowired
    private ConfigurazioneService configurazioneService;

    @Autowired
    private IndexManager indexManager;

    private String tempFileUUID = null;

    public ErrorResponse verifyMimeTypeSignedFile(File pFile, AllegatoVO vo) {
        ErrorResponse error = null;
        Map<String, String> detail = new HashMap<>();
        ConfigurazioneVO confTypes = configurazioneService.findConfigurazioneByName(Constants.CONFIG_KEY_ALLEGATI_ALLOWED_FILE_EXTENSIONS);
        String allowedExt = confTypes.getValore();
        logger.debug(" documentInfo : " + allowedExt);
        //-----------------------------------------
        FileFormatInfo documentInfo = indexManager.verifyBinaryDocumentBySignedFile(pFile);
        this.tempFileUUID = indexManager.getTempFileUUID();
        logger.debug(" documentInfo " + this.tempFileUUID + "::" + documentInfo.getMimeType());
        //Se il file firmato viene sbustato allora = Cades!
        if (documentInfo == null) {
            detail.put("file", "Impossibile verificare l'estensione del file firmato.");
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, ESTENSIONE_DEL_FILE_NON_INDIVIDUATA, detail, null);
        } else if (this.tempFileUUID == null) {
            detail.put("file", "Il file NON firmato CADES.");
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, ESTENSIONE_DEL_FILE_NON_INDIVIDUATA, detail, null);
        } else if (allowedExt.contains(documentInfo.getMimeType())) {

        	vo.setMimetype(documentInfo.getMimeType());
        } else if (!"application/pdf".equals(documentInfo.getMimeType())) {
            detail.put("file", L_ESTENSIONE_DEL_FILE_FIRMATO_NON_PDF_SONO_CONSENTITI_SOLO_PDF);
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, ESTENSIONE_DEL_FILE_NON_PDF, detail, null);
        }
        return error;
    }

    public ErrorResponse verifyMimeTypeBinary(File pFile, AllegatoVO vo) {
        ErrorResponse error = null;
        Map<String, String> detail = new HashMap<>();
        ConfigurazioneVO confTypes = configurazioneService.findConfigurazioneByName(Constants.CONFIG_KEY_ALLEGATI_ALLOWED_FILE_EXTENSIONS);
        String allowedExt = confTypes.getValore();
        logger.debug(" documentInfo : " + allowedExt);
        //-----------------------------------------
        FileFormatInfo documentInfo = indexManager.verifyBinaryDocument(pFile);
        //Se il file firmato viene sbustato allora = Cades!
        if (documentInfo == null) {
            detail.put("file", "Impossibile verificare l'estensione del file firmato.");
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, ESTENSIONE_DEL_FILE_NON_INDIVIDUATA, detail, null);
        } else if (allowedExt.contains(documentInfo.getMimeType() == null? "" : documentInfo.getMimeType())) {
            vo.setMimetype(documentInfo.getMimeType());
        } else if (!"application/pdf".equals(documentInfo.getMimeType() == null? "" : documentInfo.getMimeType())) {
            detail.put("file", L_ESTENSIONE_DEL_FILE_FIRMATO_NON_PDF_SONO_CONSENTITI_SOLO_PDF);
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, ESTENSIONE_DEL_FILE_NON_PDF, detail, null);
        }
        return error;
    }

    public ErrorResponse getDecodificaErroriFirma(VerifyReport verifyReport) {
        ErrorResponse error = null;
        //verifica validità firma
        Integer errorCode = verifyReport.getErrorCode();
        if (errorCode > 0) {
            String errorMessage = null;
            switch (errorCode) {
                case 1:
                    errorMessage = "Verifica conformità e integrità busta crittografica: KO";
                    break;
                case 2:
                    errorMessage = "Sbustamento busta crittografica: KO";
                    break;
                case 3:
                    errorMessage = "Verifica consistenza firma: KO";
                    break;
                case 4:
                    errorMessage = "Verifica validità certificato: KO";
                    break;
                case 5:
                case 6:
                    errorMessage = "Verifica lista di revoca — CRL aggiornata non disponibile: KO";
                    break;
                case 7:
                    errorMessage = "Verifica lista di revoca — certificato presente nella CRL: KO";
                    break;
            }
            Map<String, String> detail = new HashMap<>();
            detail.put("file", errorMessage);
            error = new ErrorResponse("403", ERRORE_VALIDAZIONE, FIRMA_NON_VALIDA, detail, null);
        }
        return error;
    }

    public Response formatErrorMsg(ErrorResponse error) {
        logger.error("[SignedFileVerify:: ERROR : " + error);
        return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
    }
}