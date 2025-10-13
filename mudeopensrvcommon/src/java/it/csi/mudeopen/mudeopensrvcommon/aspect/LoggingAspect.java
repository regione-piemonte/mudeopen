/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.util.Constants;

/**
 * The type Logging aspect.
 */
@Aspect
//@Order(value = 10)
public class LoggingAspect {

	static final ObjectMapper m_Mapper = new ObjectMapper();
	
	public void logStacktrace(JoinPoint joinPoint, Throwable e) {
    	String stacktrace = "";

		try {
	        Class<?> target = getTargetObject(joinPoint.getTarget());
	
	        String signatureName = target.getSimpleName() + "::" + joinPoint.getSignature().getName();
			String mdcHeader = (String)MDC.get(Constants.LOG_HEADER_NAME);
	        String messageFormat = mdcHeader + " - " + "[%s] %s";
	
	    	Logger logger = Logger.getLogger(target.getCanonicalName());
	    	
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			stacktrace += "\n" + st.toString().replaceAll("^[ \\t]+at[ ]((?!it.csi.mudeopen.mudeopen).)*[\\r\\n]+", "") + "\n";
			
			logger.error(String.format(messageFormat, signatureName, "STACKTRACE: " + stacktrace));
		} catch (Exception skip) {}
	}
	
    public Object logInputOutput(ProceedingJoinPoint joinPoint) throws Throwable {
    	String strArgs = "";
    	Object[] _strArgs = joinPoint.getArgs();
    	if(_strArgs != null)
    		for(Object obj : _strArgs)
				strArgs += (strArgs==null?"":", ") + (obj == null?"NULL" : obj.toString());
				//strArgs += (strArgs.length() == 0?"":", ") + getParamAsString(obj);
    	
        Class<?> target = getTargetObject(joinPoint.getTarget());

        String signatureName = target.getSimpleName() + "::" + joinPoint.getSignature().getName();
		String mdcHeader = (String)MDC.get(Constants.LOG_HEADER_NAME);
        String messageFormat = mdcHeader + " - " + "[%s] %s";

    	Logger logger = Logger.getLogger(target.getCanonicalName());
        boolean isDebugEnabled = logger.isDebugEnabled();

    	
        if(isDebugEnabled)
        	logger.debug(String.format(messageFormat, signatureName + "(" + strArgs + ")", "BEGIN"));        	

        try {
        	Object result = joinPoint.proceed();
            if(isDebugEnabled)
        		logger.debug(String.format(messageFormat, signatureName, "END"));        		

            return result;
        } catch (Throwable ex) {
            if(isDebugEnabled) {
            	String stacktrace = "";
            	
				try {
					StringWriter st = new StringWriter();
					ex.printStackTrace(new PrintWriter(st));
					stacktrace += "\n" + st.toString() + "\n";				
				} catch (Exception skip) {}

            	logger.error(String.format(messageFormat, signatureName, "ERROR: " + stacktrace), ex);
            }

            throw ex;
        }
    }
    
    private String getParamAsString(Object param) {
    	if(param == null)
    		return "null";

    	String res;
    	try { 
    		res = m_Mapper.writeValueAsString(param); 
    	} catch(Throwable e) {
    		res = param.toString();
    	}
    	
    	if((res.length() > 0 && res.charAt(0) != '{' &&  res.length() > 1024) || res.length() > 1024*4)
    		return res.substring(0, 1024) + "...";
    	
    	return res;
    }

    /**
     * Gets target object.
     *
     * @param proxy the proxy
     * @return the target object
     * @throws Exception the exception
     */
    protected Class<?> getTargetObject(Object proxy) throws Exception {
        // AopUtils.getTargetClass(joinPoint.getTarget())

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            while ((AopUtils.isJdkDynamicProxy(proxy))) {
                return getTargetObject(((Advised) proxy).getTargetSource().getTarget());
            }
            return proxy.getClass();
        } else if (AopUtils.isCglibProxy(proxy)) {
            Class<?> proxyClass = proxy.getClass().getSuperclass();
            while (AopUtils.isCglibProxy(proxyClass)) {
                proxyClass = proxy.getClass().getSuperclass();
            }
            return proxyClass;
        } else if (proxy.getClass().getCanonicalName().contains("com.sun.proxy.$Proxy")) {
            Class<?>[] interfaces = proxy.getClass().getInterfaces();
            if (interfaces.length > 0) {
                return interfaces[0];
            } else {
                return proxy.getClass();
            }
        } else {
            return proxy.getClass();
        }
    }

}