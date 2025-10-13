/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.beans.factory.annotation.Autowired;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
/**
 * The gestione quadri BO services.
 */
@SuppressWarnings("unchecked")
public abstract class BaseAPI<T extends Object> extends AbstractApi {
	
	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private final Class<T> classTypedAPI = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	
    /**
     * The Configuration helper.
     */
    @Autowired
	public ConfigurationHelper configurationHelper;
    /*
     * generic API call made via REASTEASY
     */
	public Response callAPI(String userCf, 
									SecurityContext securityContext, 
									HttpHeaders httpHeaders, 
									HttpServletRequest httpRequest, 
									String method2Invoke, 
									Object ... params) {
		if(method2Invoke == null)
			method2Invoke = Thread.currentThread().getStackTrace()[2].getMethodName();
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {
			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;      
            T api = (T)rtarget.proxy(classTypedAPI);
            Method method = null;
            for(Method m : api.getClass().getDeclaredMethods())
            	if(m.getName().equals(method2Invoke))
            		method = m;
            if(method == null)
            	throw new Exception("Impossible find method " + method2Invoke);
            Object[] pars = new Object[] { userCf != null? userCf : userInfo.getCodFisc(), 
            		(String)MDC.get(Constants.X_REQUEST_ID),
            		(String)MDC.get(Constants.X_FORWARDED_FOR), 
					securityContext, httpHeaders, httpRequest };
            // appends params, if there are any
            if(params != null && params.length > 0) {
            	Object[] newArr = new Object[pars.length + params.length];
            	
            	System.arraycopy(pars, 0, newArr, 0, pars.length);
            	System.arraycopy(params, 0, newArr, pars.length, params.length);
            	
            	pars = newArr;
            }

            return (Response)method.invoke(api, pars);
		}

		catch(Exception t) {
			logger.error("[" + getClass().getSimpleName() + ":: callAPI] ERROR on method " + method2Invoke, t);
			throw new RemoteException();
		}
	}

    /*
     * generic multipart API call made via REASTEASY
     */
	public Response callMultipartAPI(String userCf, 
									SecurityContext securityContext, 
									HttpHeaders httpHeaders, 
									HttpServletRequest httpRequest, 
									String path, 
									String stringObjectParamName, 
									MultipartFormDataInput input) {
		String method2Invoke = Thread.currentThread().getStackTrace()[2].getMethodName();
		// httpRequest.setAttribute(InputPart.DEFAULT_CHARSET_PROPERTY, "charset=UTF-8");
		// httpRequest.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, "*/*; charset=UTF-8");
		try {
	        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
	
	        Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
	        WebTarget target = client.target(configurationHelper.getEndpointBase()+path);
	
	        MultipartFormDataOutput output = new MultipartFormDataOutput();
	        if(input.getFormDataMap().get("file") != null) {
		        InputPart inputPart = input.getFormDataMap().get("file").get(0);
		        String filename = inputPart.getHeaders().getFirst(CONTENT_DISPOSITION);
		        
		        // recupero valori nel form di input
		        File file = input.getFormDataPart("file", File.class, null);
		        OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
	        	objPart.getHeaders().putSingle(CONTENT_DISPOSITION, "form-data; name=file; filename=" + filename);
	        }
	        
	        //not working with utf8: String objectAsString = new String(input.getFormDataPart(stringObjectParamName, String.class, null).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	        InputPart jsonPart = input.getFormDataMap().get(stringObjectParamName).get(0);
            jsonPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);
            String objectAsString = jsonPart.getBody(String.class, null);	        
	        output.addFormData(stringObjectParamName, objectAsString, MediaType.TEXT_PLAIN_TYPE);
	
	        MultivaluedMap<String,Object> headers = new MultivaluedHashMap<>();
	        headers.add("user-cf", userInfo.getCodFisc());
	        headers.add("X-Request-ID", (String)MDC.get(Constants.X_REQUEST_ID));
	        headers.add("X-Forwarded-For", (String)MDC.get(Constants.X_FORWARDED_FOR));
	
	        Response response =  target.request().headers(headers).post(Entity.entity(output, MediaType.MULTIPART_FORM_DATA));
	        
	        return response;
		}

		catch(Throwable t) {
			throw new RemoteException();
		}
	}

	public static class AddDefaultHeadersRequestFilter implements ClientRequestFilter {
	    private final String userCf;

	    private final UserInfo userInfo;
	    public AddDefaultHeadersRequestFilter(String userCf, UserInfo userInfo) {
	        this.userCf = userCf;
	        this.userInfo = userInfo;
	    }

	    @Override
	    public void filter(ClientRequestContext requestContext) throws IOException {
	    	String XRequestId = (String)MDC.get(Constants.X_REQUEST_ID);
	    	String XForwardedFor = (String)MDC.get(Constants.X_FORWARDED_FOR);	    	
	    	if(XRequestId != null) {
	    		requestContext.getHeaders().add(Constants.HEADER_MUDE_TIPO_QUADRO, XRequestId.split("~")[0]);
	    		requestContext.getHeaders().add(Constants.X_REQUEST_ID, XRequestId);
	    	}
	    	
	    	if(XForwardedFor != null)
	    		requestContext.getHeaders().add(Constants.X_FORWARDED_FOR, XForwardedFor);
	    	
    		requestContext.getHeaders().add(Constants.MUDEOPEN_API_SCOPE, userInfo.getMudeopenApiScope());
	    }
	}
	
	protected void checkForError(ErrorResponse hashError) {
        if(hashError != null)
        	throw new BusinessException(hashError.getTitle(), hashError.getStatus(), hashError.getCode(), hashError.getDetail());
	}
}