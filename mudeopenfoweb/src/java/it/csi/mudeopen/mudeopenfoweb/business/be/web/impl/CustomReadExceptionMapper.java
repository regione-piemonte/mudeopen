/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ReaderException;

/**
 * The type Custom read exception mapper.
 */
@Provider
public class CustomReadExceptionMapper implements ExceptionMapper<NotFoundException>{

	public Response toResponse(NotFoundException arg0) {
		Response r = Response.serverError().entity("errore di unmarshal:"+arg0.getClass()).build();
		return r;
	}

}