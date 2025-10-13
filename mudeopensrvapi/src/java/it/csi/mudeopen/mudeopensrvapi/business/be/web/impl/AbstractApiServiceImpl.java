/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDFruitoreService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTSessioneService;
import it.csi.mudeopen.mudeopensrvapi.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.GenericException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTPraticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
/**
 * The type Abstract api service.
 */
public abstract class AbstractApiServiceImpl extends SpringBeanAutowiringSupport {

    public static final String ERRORE_SERVER_INTERNO ="Errore server interno";

    public static final String DATE_FORMAT = "dd-MM-yyyy";

	/**
     * The constant CSILOGGER.
     */
    protected static Logger CSILOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business" );

    @Autowired
    public ConfigurationHelper configurationHelper;

    @Autowired
    protected MudeopenTSessioneService mudeopenTSessioneService;

    @Autowired
    protected MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private DocumentoEntityMapper documentoEntityMapper;

    @Autowired
    private MudeTDocumentoRepository mudeTDocumentoRepository;

	@Autowired
	protected MudeopenDFruitoreService mudeopenDFruitoreService;
	
	protected Response errorToResponse(Error error) throws Exception {
    	return voToResponse(error, error.getHttpStatus().value());
    }
	    
	protected Response stringToResponse(String str, int httpStatus) {
		try {
	        return Response.ok(str).status(httpStatus).header(HttpHeaders.CONTENT_ENCODING, it.csi.mudeopen.mudeopensrvcommon.util.Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, "" + str.getBytes("UTF-8").length).build();
		} catch (Exception e) {
	        return Response.ok(str).status(httpStatus).build();
		}
    }
	    
    protected String getCommonServerURL() {
    	return mudeCProprietaRepository.getValueByName("COMMON_SRV_URL", configurationHelper.getEndpointBase());
    }

    protected Response voToResponse(Object VO, int httpStatus) throws Exception {
        String str = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            str = mapper.writeValueAsString(VO);
            return Response.ok(str).status(httpStatus).header(HttpHeaders.CONTENT_LENGTH, "" + str.getBytes("UTF-8").length).build();
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            CSILOGGER.error("[voToResponse::" + str + "] problem mapping result : " + e.getMessage(), e);
            throw new GenericException("Errore generico durante nei processi interni. Se il problema persiste, contattare l'amministratore del sistema per comunicargli l'errore.");
        }
    }

    protected Response voToResponse(Object VO, int httpStatus, Map<String, Object> headers) throws Exception {
        String str = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            str = mapper.writeValueAsString(VO);
            ResponseBuilder responseBuilder = Response.ok(str).status(httpStatus).header(HttpHeaders.CONTENT_ENCODING, it.csi.mudeopen.mudeopensrvcommon.util.Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, "" + str.getBytes("UTF-8").length);
            if (headers != null) {
            	for (Map.Entry<String, Object> entry : headers.entrySet()) {
            		responseBuilder.header(entry.getKey(), entry.getValue());
            	}
            }
            return responseBuilder.build();
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
        	CSILOGGER.error("[voToResponse::" + str + "] problem mapping result : " + e.getMessage(), e);
            throw new GenericException("Errore generico durante nei processi interni. Se il problema persiste, contattare l'amministratore del sistema per comunicargli l'errore.");
        }
    }

	public static class AddDefaultHeadersRequestFilter implements ClientRequestFilter {
	    private final String userCf;

	    public AddDefaultHeadersRequestFilter(String userCf) {
	        this.userCf = userCf;
	    }

	    @Override
	    public void filter(ClientRequestContext requestContext) throws IOException {
	    	if(userCf != null)
	    		requestContext.getHeaders().add(Constants.HEADER_USER_CF, userCf);
	    	
    		requestContext.getHeaders().add(Constants.X_REQUEST_ID, (String)MDC.get(Constants.X_REQUEST_ID));
    		requestContext.getHeaders().add(Constants.X_FORWARDED_FOR, (String)MDC.get(Constants.X_FORWARDED_FOR));
	    }
	}

	public String getFileName(MultipartFormDataInput formDataInput, String fileFieldName) {
        String fileName = "unknown";
        try {
            Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
            List<InputPart> inputParts = uploadForm.get(fileFieldName);
            MultivaluedMap<String, String> header = inputParts != null ? inputParts.get(0).getHeaders() : null;
            assert header != null;
            String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
            for (String filename : contentDisposition) {
                if ((filename.trim().startsWith("filename"))) {
                    String[] name = filename.split("=");
                    fileName = name[1].trim().replace("\"", "");
                }
            }
        } catch (Exception e) {
        	CSILOGGER.error("[AbstractApiServiceImpl::getFileName] EXCEPTION : " + e.getMessage(), e);
        }
        return fileName;
    }
	
	protected Response getResponse(String url, String codiceFruitore) {
		CSILOGGER.info("chiamata endpoint GET"+url);
		Client client = ClientBuilder.newClient();
		client.register(new AddDefaultHeadersRequestFilter(codiceFruitore));
		WebTarget target = client.target(getCommonServerURL()+url);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		return rtarget.request().header(Constants.HEADER_USER_CF, codiceFruitore).get();
	}
	
	protected Response getResponseWithFruitore(String url, String codiceFruitore) {
		CSILOGGER.info("chiamata endpoint GET "+url+" con fruitore " + codiceFruitore);
		Client client = ClientBuilder.newClient();
		client.register(new AddDefaultHeadersRequestFilter(codiceFruitore));
		WebTarget target = client.target(getCommonServerURL()+url);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		return rtarget.request().header(Constants.HEADER_USER_CF, codiceFruitore).get();
	}
	
	protected String encodeFilter(String filter) throws UnsupportedEncodingException {
		if(StringUtils.isBlank(filter)) {
			return "";
		} else {
			return URLEncoder.encode(filter, "UTF-8");
		}
	}
	
	protected Response postResponse(String url, GenericEntity<MultipartFormDataOutput> entity, String codFruitore) {
		CSILOGGER.info("chiamata endpoint POST"+url);
		Client client = ClientBuilder.newClient();
		client.register(new AddDefaultHeadersRequestFilter(codFruitore));
		WebTarget target = client.target(getCommonServerURL()+url);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		return rtarget.request().header(Constants.HEADER_USER_CF, codFruitore).post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
	}
	
	
	
	protected Response putResponse(String url, GenericEntity<MultipartFormDataOutput> entity, String codFruitore) {
		CSILOGGER.info("chiamata endpoint PUT"+url);
		Client client = ClientBuilder.newClient();
		client.register(new AddDefaultHeadersRequestFilter(codFruitore));
		WebTarget target = client.target(getCommonServerURL()+url);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		return rtarget.request().header(Constants.HEADER_USER_CF, codFruitore).post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
	}

	
	protected List<DocumentoVO> ottieniDocumentiPratica(Long idPratica, String codFruitore, String filters) throws Exception {
		List<MudeTDocumento> documentiVoList = mudeTDocumentoRepository.findAll(MudeTPraticaSpecification.findDocumentiByPratica(idPratica));
		return documentoEntityMapper.mapListEntityToListVO(documentiVoList, null, filters);
	}
	
	public Error handleUnexpectedError(Throwable t) {
		String msg = getMessageFromJson(StringUtils.isBlank(t.getMessage()) ? ERRORE_SERVER_INTERNO : t.getMessage());
		
		if(msg == null || !msg.matches("\\[[a-zA-Z]*Exception\\].*"))
			msg = "[SystemException] " + (msg == null? "Errore generico" : msg);
		
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		CSILOGGER.error("[" + getClass().getSimpleName() + "::" + method +  " error : " + msg, t);
		return new Error(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}
	
	public Error handleUnexpectedMessage(String errorMessage) {
		String msg = getMessageFromJson(StringUtils.isBlank(errorMessage) ? ERRORE_SERVER_INTERNO : errorMessage);
		
		if(msg == null || !msg.matches("\\[[a-zA-Z]*Exception\\].*"))
			msg = "[SystemException] " + (msg == null? "Errore generico" : msg);
		
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		CSILOGGER.error("[" + getClass().getSimpleName() + "::" + method +  " error : " + msg);
		return new Error(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}
	
	private String getMessageFromJson(String msg) {
		try {
            JSONObject js = new JSONObject(msg);
            if(js.has("title")) msg = js.getString("title");
		} catch (Exception noJSON) {}
		
		return msg;
	}
	
	protected MudeDFruitore verifyFruitore(String fruitoreID) throws Exception {
		return verifyFruitore(fruitoreID, null, null);
	}
	
	protected MudeDFruitore verifyFruitore(String fruitoreID, String tipoIstanza, String comune) throws Exception {
    	if(StringUtils.isBlank(fruitoreID))
    		throw new Exception("[CodiceFiscaleObbligatorioException] Codice fruitore non specificato");
		
		MudeDFruitore fruitore = mudeopenDFruitoreService.findByCodiceFruitore(fruitoreID);
		if(fruitore == null)
    		throw new Exception("[FruitoreDisabilitatoException] Codice fruitore " + fruitoreID + " non trovato");
		
		if(fruitore.getDataFine() != null && fruitore.getDataFine().getTime() > System.currentTimeMillis())
    		throw new Exception("[FruitoreNonAbilitatoComuneException] Fruitore disabilitato");

		if(comune != null && !mudeopenDFruitoreService.findComuniByCodiceFruitore(fruitoreID).stream().anyMatch(x -> tipoIstanza.equals(x.getComune().getCodBelfioreComune())))
    		throw new Exception("[FruitoreNonAbilitatoComuneException] Fruitore non abilitato al comune " + comune);

		if(tipoIstanza != null && !mudeopenDFruitoreService.findTipiIstanzaByCodiceFruitore(fruitoreID).stream().anyMatch(x -> tipoIstanza.equals(x.getMudeDTipoIstanza().getCodice())))
    		throw new Exception("[SystemException] Fruitore non abilitato al modello " + tipoIstanza);
		
		return fruitore;
	}
	
	boolean hasJsonValue(JSONObject jsonfilter, String key) {
		try {
			return jsonfilter.has(key) 
					// NOSONARQUBE
					&& !jsonfilter.get(key).equals(null) // NOSONARQUBE
					&& (!(jsonfilter.get(key) instanceof String) || !"".equals(((String)jsonfilter.get(key)).trim()));
		} catch (Exception skip) { }
		
		return false;
	}
	
	Object getJsonValue(JSONObject jsonfilter, String key) {
		try { return jsonfilter.get(key); } catch (Exception skip) { }
		return null;
	}
	
	boolean isValidDate(String dateStr) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(dateStr.replaceAll("[/-]", "-")) != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected Date getDate(String dateStr) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));

		return sdf.parse(dateStr.replaceAll("[/-]", "-"));
	}
}