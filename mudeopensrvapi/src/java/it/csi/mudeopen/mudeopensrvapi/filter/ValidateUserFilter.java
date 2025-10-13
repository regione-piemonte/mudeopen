/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

public class ValidateUserFilter extends SpringBeanAutowiringSupport implements Filter {
	
	@Autowired
	private MudeTUserRepository mudeTUserRepository;

	@Override
	public void destroy() {
		// Non implementato
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		if(!StringUtils.contains(hreq.getRequestURI(), "/geeco")) {
			UserInfo userInfo = (UserInfo) hreq.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			if(userInfo != null) {
				MudeTUser mudeTUser = mudeTUserRepository.findByCf(userInfo.getCodFisc());
				if(mudeTUser != null) {
					if(Boolean.FALSE.equals(mudeTUser.getUtenteApi())){
						throw new ServletException(
								"Utente non abilitato al servizio");
					}
				} else {
					throw new ServletException(
							"Utente non trovato");
				}
				
			}
		}
		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		// Non implementato
	}

}
