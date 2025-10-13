/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.helper;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import it.csi.mudeopen.mudeopenboweb.business.be.web.UtentiApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;
/**
 * The type Utente api service helper.
 */
public class UtenteApiServiceHelper extends AbstractServiceHelper {
    /**
     * Instantiates a new Utente api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public UtenteApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }
    /**
     * Gets profilo by cf.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the profilo by cf
     */
    public Response getProfiloByCF(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        // JAX-RS call
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(this.endpointBase);
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            UtentiApi utenteApi = rtarget.proxy(UtentiApi.class);
            return utenteApi.getInfoProfilo(userCf, (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest, "backoffice");
        } catch (ProcessingException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    /**
     * Salva info profilo response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param utente          the utente
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    public Response salvaInfoProfilo(String userCf, String XRequestId, String XForwardedFor, UtenteVO utente, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        // JAX-RS call
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(this.endpointBase);
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            UtentiApi utenteApi = rtarget.proxy(UtentiApi.class);
            Response response = utenteApi.salvaInfoProfilo(userCf, (String) MDC.get(Constants.X_REQUEST_ID), (String) MDC.get(Constants.X_FORWARDED_FOR), utente, securityContext, httpHeaders, httpRequest);
            return response;
        } catch (ProcessingException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    /**********************************************
     * ADEMPIMENTO
     **********************************************/
}