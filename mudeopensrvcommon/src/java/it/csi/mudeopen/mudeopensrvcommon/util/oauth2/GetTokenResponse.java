/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util.oauth2;
public class GetTokenResponse {
	private String scope;

	private String token_type;
	private String bearer;

	private Long expires_in;
	private String access_token;

    public String getScope() {
		return scope;
	}

    public void setScope(String scope) {
		this.scope = scope;
	}

    public String getToken_type() {
		return token_type;
	}

    public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

    public String getBearer() {
		return bearer;
	}

    public void setBearer(String bearer) {
		this.bearer = bearer;
	}

    public Long getExpires_in() {
		return expires_in;
	}

    public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

    public String getAccess_token() {
		return access_token;
	}

    public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}