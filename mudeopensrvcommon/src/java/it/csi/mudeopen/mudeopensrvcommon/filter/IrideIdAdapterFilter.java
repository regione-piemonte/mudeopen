/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import it.csi.mudeopen.mudeopensrvcommon.filter.iride.entity.Identita;
import it.csi.mudeopen.mudeopensrvcommon.filter.iride.exception.MalformedIdTokenException;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

public class IrideIdAdapterFilter implements Filter {
	protected static final Logger logger = Logger.getLogger(IrideIdAdapterFilter.class.getCanonicalName());

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {
		
		// is home path? 
		HttpServletRequest hreq = (HttpServletRequest) req;
		if((hreq.getRequestURI().indexOf("/mudeopen/rest/") > -1 || hreq.getRequestURI().indexOf("/mudeopen/bo/rest/") > -1) && 
				hreq.getSession().getAttribute(Constants.IRIDE_ID_SESSIONATTR) == null) {
			
			// TODO - inserire flag di configurazione per token fake
			String marker = getToken(hreq);
			
			// required to make work /dizionari/.. APIs called by FORMIO
			if(marker == null && Constants._environment.equals("local"))
				marker = "AAAAAA00A11B000J//DEMO 21/ACTALIS_EU/20201203143417/16/72Fm0h3s08aROqA0Up6IMg==";
			
			if (marker != null) {
				try {
					Identita identita = new Identita(normalizeToken(marker));
					logger.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
					hreq.getSession().setAttribute(Constants.IRIDE_ID_SESSIONATTR, identita);
					UserInfo userInfo = new UserInfo();
					userInfo.setNome(identita.getNome());
					userInfo.setCognome(identita.getCognome());
					userInfo.setEnte("--");
					userInfo.setRuolo("--");
					userInfo.setCodFisc(identita.getCodFiscale());
					userInfo.setLivAuth(identita.getLivelloAutenticazione());
					userInfo.setCommunity(identita.getIdProvider());
					userInfo.setMudeopenApiScope(hreq.getHeader(Constants.MUDEOPEN_API_SCOPE));

					hreq.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
				} catch (MalformedIdTokenException e) {
					logger.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				}
			} else {
				// il marcatore deve sempre essere presente altrimenti e' una 
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(hreq.getRequestURI())) {
					logger.error(
							"[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
					throw new ServletException(
							"Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
				}
			}
		}

		fchn.doFilter(req, resp);

	}

	private boolean mustCheckPage(String requestURI) {

		return true;
	}

	public void destroy() {
		// NOP
	}

	private static final String DEVMODE_INIT_PARAM = "devmode";

	private boolean devmode = false;

	public void init(FilterConfig fc) throws ServletException {
		String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
		if ("true".equals(sDevmode)) {
			devmode = true;
		} else {
			devmode = false;
		}
	}

    public String getToken(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getHeader(Constants.AUTH_ID_MARKER);
		if (marker == null && devmode) {
			return getTokenDevMode(httpreq);
		} else if(marker == null) {
			return null;
		}else {
			try {
				// gestione dell'encoding
				String decodedMarker = new String(marker.getBytes("ISO-8859-1"), "UTF-8");
				return decodedMarker;
			} catch (java.io.UnsupportedEncodingException e) {
				// se la decodifica non funziona comunque sempre meglio restituire 
				// il marker originale non decodificato
				return marker;
			}
		}
	}

	private String getTokenDevMode(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getParameter(Constants.AUTH_ID_MARKER);
		return marker;
	}

	private String normalizeToken(String token) {
		return token;
	}

}