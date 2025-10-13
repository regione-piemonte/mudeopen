/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web.impl;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTSessioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.EntityLoggingFilter;
import it.csi.mudeopen.mudeopensrvsoap.business.be.exception.MudeWSException;
import it.csi.mudeopen.mudeopensrvsoap.common.exception.RemoteException;
@Component
public class GenericApi extends SpringBeanAutowiringSupport {
	
	private static final String ABILITATO = "abilitato";
	protected String _WEBSERVICES_API_URL;
	protected String _WEBSERVICES_API_URL_UID;
	protected String _WEBSERVICES_API_URL_PWD;
	protected String _WEBSERVICES_API_BASIC_AUTH;
	
	static protected String _ENVIRONMENT = "";
	
	protected Logger logger = Logger.getLogger(GenericApi.class);
	
	@Autowired
    public ConfigurationHelper configurationHelper;
	
    @Autowired
    protected MudeCProprietaRepository mudeCProprietaRepository;
    @Autowired
    protected MudeopenTSessioneRepository mudeopenTSessioneRepository;
    @PostConstruct
    public void init() {
    	if(_ENVIRONMENT.length() == 0)
    		_ENVIRONMENT = mudeCProprietaRepository.getValueByName("codice_ambiente", "prod");
    }    
    protected void getCredenzialiRest(){
    	_WEBSERVICES_API_URL = mudeCProprietaRepository.getValueByName("WEBSERVICES_API_URL", configurationHelper.getEndpointBase());
    	_WEBSERVICES_API_URL_UID = mudeCProprietaRepository.getValueByName("WEBSERVICES_API_URL_UID", "mudeopensrvapi");
    	_WEBSERVICES_API_URL_PWD = mudeCProprietaRepository.getValueByName("WEBSERVICES_API_URL_PWD", "mudeopen123!");
    	_WEBSERVICES_API_BASIC_AUTH = mudeCProprietaRepository.getValueByName("WEBSERVICES_API_BASIC_AUTH", ABILITATO);
    }
    /*
     * generic API call made via REASTEASY
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response callAPI(MudeopenTSessione mudeTSessione, Class clazz, Object ... params) {
		String method2Invoke = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		logger.debug("[" + getClass().getSimpleName() + ":: callAPI] BEGIN");
		try {
			getCredenzialiRest();
			
			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(mudeTSessione.getFruitore()));
	        client.register(new EntityLoggingFilter("WS CALL TRACE ["+_WEBSERVICES_API_URL+" - class:  "+clazz.getCanonicalName()+"]"));
            WebTarget target = client.target(_WEBSERVICES_API_URL);
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            addAuthentication(rtarget);
            Object api = rtarget.proxy(clazz);
            Method method = null;
            for(Method m : api.getClass().getDeclaredMethods())
            	if(m.getName().equals(method2Invoke))
            		method = m;
            if(method == null)
            	throw new Exception("Impossible find method " + method2Invoke);
            Object[] pars = new Object[] { MDC.get(Constants.X_REQUEST_ID), MDC.get(Constants.X_FORWARDED_FOR), mudeTSessione.getFruitore() };
            // appends params, if there are any
            if(params != null && params.length > 0) {
            	Object[] newArr = new Object[pars.length + params.length];
            	
            	System.arraycopy(pars, 0, newArr, 0, pars.length);
            	System.arraycopy(params, 0, newArr, pars.length, params.length);
            	
            	pars = newArr;
            }
            logger.info("[callAPI] "+method2Invoke+": " + Arrays.asList(pars).toString());
            Response response = (Response)method.invoke(api, pars);
    		logger.debug("[" + getClass().getSimpleName() + ":: callAPI] END");
            return response;
		}
		catch(Throwable t) {
			logger.error("[" + getClass().getSimpleName() + ":: callAPI] ERROR on method " + method2Invoke, t);
			throw new RemoteException();
		}
	}
	private void addAuthentication(ResteasyWebTarget rtarget) {
		if("disabilitato".equalsIgnoreCase(_WEBSERVICES_API_BASIC_AUTH))
			return;
			
		logger.debug("[" + getClass().getSimpleName() + ":: addAuthentication] BEGIN");
		rtarget.register(new BasicAuthentication(_WEBSERVICES_API_URL_UID, _WEBSERVICES_API_URL_PWD));
		logger.debug("[" + getClass().getSimpleName() + ":: addAuthentication] END");
	}
	
	public Response callMultipartAPI(String token,String path, MultipartFormDataOutput multipartFormDataOutput) {
		logger.debug("[" + getClass().getSimpleName() + ":: callMultipartAPI] BEGIN");
		try {
			getCredenzialiRest();
			
	        Client client = ClientBuilder.newClient();
	        client.register(new EntityLoggingFilter("WS CALL TRACE ["+_WEBSERVICES_API_URL+path+"]"));
			ResteasyWebTarget rtarget = (ResteasyWebTarget)client.target(_WEBSERVICES_API_URL+path);
	        addAuthentication(rtarget);
	        
	        MultivaluedMap<String,Object> headers = new MultivaluedHashMap<>();
	        headers.add("fruitore", token);
	        headers.add("X-Request-ID", MDC.get(Constants.X_REQUEST_ID));
	        headers.add("X-Forwarded-For", MDC.get(Constants.X_FORWARDED_FOR));
	        logger.debug("[" + getClass().getSimpleName() + ":: callMultipartAPI] END");
	        
	        return rtarget.request().headers(headers).post(Entity.entity(multipartFormDataOutput, MediaType.MULTIPART_FORM_DATA));
		}catch(Throwable t) {
			logger.error("[" + getClass().getSimpleName() + ":: callMultipartAPI] ERROR", t);
			throw new RemoteException();
		}
		
	}
	
	protected void logStart(String additionalMsg) {
		logExecution(true, additionalMsg);
	}
	
	protected void logEnd(String additionalMsg) {
		logExecution(false, additionalMsg);
	}
	
	private void logExecution(boolean start, String additionalMsg) {
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		String startOrEnd = start ? "BEGIN" : "END";
		logger.debug(String.format("[%s::%s] %s %s", getClass().getSimpleName(), method, startOrEnd, additionalMsg));
	}
	
	protected void logError(Throwable t) {
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		logger.debug(String.format("[%s::%s] ERROR", getClass().getSimpleName(), method ), t);
	}
	
	protected void logString(String msg) {
		logger.debug(String.format("[%s::%s] : %s", getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName() , msg));
	}
	
	protected MudeopenTSessione getMudeTSessionByToken(String token) throws Exception {
		if(StringUtils.isBlank(token)) {
			handleException("[NumeroTicketInesistente] Campo token obbligatorio");
			return null;
		}
			
		try {
			MudeopenTSessione mudeopenTSessione = mudeopenTSessioneRepository.findByIdSessione(UUID.fromString(token));
			if(mudeopenTSessione != null) {
				
				if(mudeCProprietaRepository.getValueByName("DELETE_SOAP_SESSION_IDS", ABILITATO).equals(ABILITATO)
						&& ("prod".equalsIgnoreCase(_ENVIRONMENT) 
						|| !(
								"0cb5b7c4-db43-462c-93a3-1e6fd1debdc0".equals(token) 
								|| "0cb5b7c4-db43-462c-93a3-1e6fd1debdc1".equals(token) 
								|| "0cb5b7c4-db43-462c-93a3-1e6fd1debdc2".equals(token)
								|| "0cb5b7c4-db43-462c-93a3-1e6fd1debdc3".equals(token)
								|| "0cb5b7c4-db43-462c-93a3-1e6fd1debdc4".equals(token)
							))
						)
					mudeopenTSessioneRepository.delete(mudeopenTSessione); // just for debug purposes!
	
				return mudeopenTSessione;
			}
	
			logger.error("["+this.getClass().getName()+"::checkToken] EXCEPTION : ");
		} catch (Exception e) {
		}
		
		handleException("[NumeroTicketInesistente] "+token);
		return null;
	}
	
	protected String getSafeStringPathPar(String par) {
		return StringUtils.isBlank(par) ? " " : par;
	}
	
	protected void handleException(String msg) throws MudeWSException {
		// THIS METOD IS EMPTY BECAUSE CAN BE OVERRIDED 
	}
	
	private static class AddDefaultHeadersRequestFilter implements ClientRequestFilter {
	    private final String fruitore;
	    public AddDefaultHeadersRequestFilter(String fruitore) {
	        this.fruitore = fruitore;
	    }
	    @Override
	    public void filter(ClientRequestContext requestContext) throws IOException {
    		requestContext.getHeaders().add(Constants.X_REQUEST_ID, (String)MDC.get(Constants.X_REQUEST_ID));
    		requestContext.getHeaders().add(Constants.X_FORWARDED_FOR, (String)MDC.get(Constants.X_FORWARDED_FOR));
	    }
	}
	
}
