/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;

import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
public class LogHeaderFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest hreq = (HttpServletRequest) req;
		manageLogSession(hreq);
		
		chain.doFilter(req, resp);
	}

	private void manageLogSession(HttpServletRequest hreq) {
		String userCf = hreq.getHeader(Constants.HEADER_USER_CF);
		String fruitore = hreq.getHeader("fruitore");
		String XRequestId = hreq.getHeader(Constants.X_REQUEST_ID);
		String XForwardedFor = hreq.getHeader(Constants.X_FORWARDED_FOR);
		
		if(XRequestId == null || XRequestId.length() == 0)
			XRequestId = UUID.randomUUID().toString();

		if(XForwardedFor == null || XForwardedFor.length() == 0)
			XForwardedFor = hreq.getRemoteAddr();

		String header = "";//"USER["+userCf+"]";
		header += " XRequestId["+XRequestId+"]";
		header += XForwardedFor==null?"":" XForwardedFor["+XForwardedFor+"]";
		if(userCf != null)
			header += " " + Constants.HEADER_USER_CF + " [" + userCf + "]";
		if(fruitore != null)
			header += " fruitore[" + fruitore + "]";
		
		MDC.put(Constants.X_REQUEST_ID, XRequestId);
		MDC.put(Constants.X_FORWARDED_FOR, XForwardedFor);
		MDC.put(Constants.LOG_HEADER_NAME, header);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}

}