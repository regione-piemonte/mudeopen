/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.UserNotFoundException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.filter.IrideIdAdapterFilter;

@Service
public class UserUtil {
	protected static final Logger logger = Logger.getLogger(IrideIdAdapterFilter.class.getCanonicalName());

	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private MudeTUserRepository mudeTUserRepository;

    @Autowired
    MudeRUtenteRuoloRepository mudeRUtenteRuoloRepository;

	public boolean isBackofficeAdminUser(MudeTUser mudeTUser) {
       return mudeRUtenteRuoloRepository.findByIdUser(mudeTUser.getIdUser()).stream()
    		   							.anyMatch(role -> "AM".equals(role.getRuoloUtente()));
	}

    public MudeTUser getUserCF(HttpHeaders httpHeaders, boolean optional) {
		try {
			return getUserCF(null, null, optional);
		}catch (Throwable t) {
			throw new UserNotFoundException("Utente con CF non trovato!");
		}
	}

    private MudeTUser getUserCF(String userCf, String marker, boolean optional) {
		MudeTUser mudeTUser = null;

		try {
			
			String res = getHeader(Constants.HEADER_USER_CF);
			
			userCf = (String)httpServletRequest.getHeader(Constants.HEADER_USER_CF); 
			marker = (String)httpServletRequest.getHeader(Constants.AUTH_ID_MARKER);

			if(StringUtils.isBlank(userCf)) {
				if(!StringUtils.isBlank(marker))
					userCf = marker.substring(0, marker.indexOf('/'));
			}
			
			mudeTUser = mudeTUserRepository.findByCf(userCf);
		}catch (Throwable t) {
			throw new UserNotFoundException("Utente con CF ["+userCf+"] non trovato!");
		}
		if(!optional && mudeTUser == null) {
			throw new UserNotFoundException("Utente con CF ["+userCf+"] non trovato!");
		}
		return mudeTUser;
	}

    public String getHeader(String name) {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)attribs).getRequest();
            return request.getHeader(name);
        }
        return null;
    }

    public boolean isBOContext( ) {
    	return "backoffice".equals(getHeader(Constants.MUDEOPEN_API_SCOPE));    	
    }

}