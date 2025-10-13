/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
public abstract class AbstractApi {
	private static final String ERRORE_BUSINESS = "errore_business";

	protected final Class classAPI = getClass();
	protected final String CLS_NAME = classAPI.getSimpleName();
	protected Logger logger = Logger.getLogger(CLS_NAME);
    @Autowired
    protected UserUtil headerUtil;
    @Autowired
    protected ManagerAbilitazioni managerAbilitazioni;
    protected MudeTUser getMudeTUser(HttpHeaders httpHeaders) {
		return headerUtil.getUserCF(httpHeaders, false);
    }

/*
    protected Map<String, String> getContentLength(Object obj) throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(obj);
        Integer len = str.getBytes("UTF-8").length;
        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
        map.put("object", str);
        map.put("length", "" + (new ObjectMapper().writeValueAsBytes(obj).length));
        return map;
    }
*/
    protected int getContentLen(Object obj) {
        try {
            return new ObjectMapper().writeValueAsBytes(obj).length;
        } catch (JsonProcessingException e) {
            LoggerUtil.warn(logger, "[PfPgApiServiceImpl::loadPgByLegaleRappresentante] problem mapping result : " + e.getMessage());
        }

        return 0;
    }

    protected Response voToResponse(Object VO, int httpStatus) {
        String str = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            str = mapper.writeValueAsString(VO);
            return Response.ok(str).status(httpStatus).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, "" + str.getBytes("UTF-8").length).build();
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            LoggerUtil.warn(logger, "[voToResponse::" + str + "] problem mapping result : " + e.getMessage());
            throw new BusinessException("Errore generico durante nei processi interni. Se il problema persiste, contattare l'amministratore del sistema per comunicargli l'errore.");
        }
    }

    protected Response voStringToResponse(String str, int httpStatus) {
        try {
            return Response.ok(str).status(httpStatus).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, "" + str.getBytes("UTF-8").length).build();
        } catch (UnsupportedEncodingException e) {
            LoggerUtil.warn(logger, "[voToResponse::" + str + "] problem mapping result : " + e.getMessage());
            throw new BusinessException("Errore generico durante nei processi interni. Se il problema persiste, contattare l'amministratore del sistema per comunicargli l'errore.");
        }
    }

    protected Response okResponse() {
    	return Response.ok().status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).build();
    }

    protected boolean checkAbilitazioneFunzioneIstanza(boolean jdataModify, String codiceFunzione, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders, String msg) {
    	return checkAbilitazioneFunzioneIstanza(jdataModify, new String[] { codiceFunzione }, idIstanza, mudeTUser, httpHeaders, msg);
    }

    protected boolean  checkAbilitazioneFunzioneIstanza(boolean jdataModify, String[] codiciFunzione, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders, String msg) {
        if (!managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(jdataModify, codiciFunzione, idIstanza, mudeTUser, httpHeaders)) {
        	if(msg == null) return false;
            throw new BusinessException(msg, "403", ERRORE_BUSINESS, null);
        }

        return true;
    }

    protected boolean checkAbilitazioneFunzioneFascicolo(String codiceFunzione, Long idFascicolo, MudeTUser mudeTUser, String msg) {
        return checkAbilitazioneFunzioneFascicolo(new String[] { codiceFunzione }, idFascicolo, mudeTUser, msg);
    }

    protected boolean checkAbilitazioneFunzioneFascicolo(String[] codiciFunzione, Long idFascicolo, MudeTUser mudeTUser, String msg) {
        if (!managerAbilitazioni.hasUtenteAbilitazioneFunzionePerFascicolo(codiciFunzione, idFascicolo, mudeTUser.getIdUser())) {
        	if(msg == null) return false;
            throw new BusinessException(msg, "403", ERRORE_BUSINESS, null);
        }

        return true;
    }

    protected boolean checkAbilitazioneFascicolo(String[] codiciFunzione, Long idFascicolo, MudeTUser mudeTUser, String msg) {
        if (!managerAbilitazioni.hasUtenteAbilitazionePerFascicolo(codiciFunzione, idFascicolo, mudeTUser.getIdUser())) {
        	if(msg == null) return false;
            throw new BusinessException(msg, "403", ERRORE_BUSINESS, null);
        }

        return true;
    }
}