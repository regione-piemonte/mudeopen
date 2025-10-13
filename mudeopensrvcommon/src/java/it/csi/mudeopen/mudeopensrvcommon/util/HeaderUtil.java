/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.filter.IrideIdAdapterFilter;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

@Service
public class HeaderUtil {

    public static HttpHeaders setHeader(HttpHeaders httpHeaders, String codFisc) {
		
		writeHeader(httpHeaders);
		
		//todo - proprieta di conf con nome istanza FE
		httpHeaders.getRequestHeaders().add(Constants.X_FORWARDED_FOR, "<TBD>");
		
		httpHeaders.getRequestHeaders().add(Constants.HEADER_USER_CF, codFisc);
		return httpHeaders;
	}

	private static void writeHeader(HttpHeaders httpHeaders) {
		// TODO Auto-generated method stub
//		for(httpHeaders.getRequestHeaders()) {
//			
//		}
		
	}

    public static UserInfo getUserInfo(HttpServletRequest httpRequest) {
		// cercare l'utente chiamate dalla sessione
		HttpServletRequest hreq = (HttpServletRequest) httpRequest;
		UserInfo userInfo = (UserInfo) hreq.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		if (userInfo == null || userInfo.getCodFisc() == null) {
		    throw new WebApplicationException(
		      Response.status(Response.Status.BAD_REQUEST)
		        .entity("User not found")
		        .build()
		    );
		  }
		return userInfo;
	}

}