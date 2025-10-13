/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util.oauth2;
import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
public class BearerAuthentication implements ClientRequestFilter
{
   private final OauthHelperIndex oauthHelper;

    public BearerAuthentication(final OauthHelperIndex oauthHelper)
   {
	  this.oauthHelper = oauthHelper;
   }

   @Override
   public void filter(ClientRequestContext requestContext) throws IOException
   {
	   StringBuffer buf = new StringBuffer(oauthHelper.getToken());
	   String authHeader = "Bearer " + buf.toString(); 
	      
      requestContext.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, authHeader);
   }
}