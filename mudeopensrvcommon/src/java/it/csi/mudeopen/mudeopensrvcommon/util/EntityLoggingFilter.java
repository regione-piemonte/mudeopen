/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
@Priority(Integer.MIN_VALUE)
public class EntityLoggingFilter implements ClientRequestFilter, ClientResponseFilter, WriterInterceptor {
    private static final Logger logger = Logger.getLogger(EntityLoggingFilter.class.getName());

    private static final String ENTITY_STREAM_PROPERTY = "EntityLoggingFilter.entityStream";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final int maxEntitySize = 1024 * 8;
    static private Level logLevel = Level.FINEST; // Level.ALL;
    private String tagname = "";

    public EntityLoggingFilter(String tagname) {
    	this.tagname = tagname;
    }

    private void log(StringBuilder sb) {

        logger.log(logLevel, sb.toString());
    }

    private InputStream logInboundEntity(final StringBuilder b, InputStream stream, final Charset charset) throws IOException {

        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }

        stream.mark(maxEntitySize + 1);
        final byte[] entity = new byte[maxEntitySize + 1];
        final int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, maxEntitySize), charset));
        if (entitySize > maxEntitySize) {
            b.append("...more...");
        }

        b.append('\n');
        stream.reset();
        return stream;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        if (requestContext.hasEntity()) {
        	Map headers = requestContext.getHeaders();
            logger.info(tagname + " HEADERS["+requestContext.getUri()+"]:");
        	for(Iterator<String> it = headers.keySet().iterator(); it.hasNext();  ) {
        		String headerName = it.next();
        		Object headerValues = headers.get(headerName);
        		
        		String headerValue = "";
        		if(headerValues != null && headerValues instanceof List)
        			for(Object v : (List)headerValues)
        				headerValue += (headerValue.equals("")?"":",") + v.toString();
        		else
        			headerValues = headerValues.toString();
        			
                logger.log(logLevel, headerName + ": " + headerValue);
        	}
        	
            final OutputStream stream = new LoggingStream(requestContext.getEntityStream());
            requestContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_STREAM_PROPERTY, stream);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext,
            ClientResponseContext responseContext) throws IOException {
        final StringBuilder sb = new StringBuilder();
        if (responseContext.hasEntity()) {
            responseContext.setEntityStream(logInboundEntity(sb, responseContext.getEntityStream(),
                    DEFAULT_CHARSET));
            log(sb);
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        final LoggingStream stream = (LoggingStream) context.getProperty(ENTITY_STREAM_PROPERTY);
        context.proceed();
        if (stream != null) {
            log(stream.getStringBuilder(DEFAULT_CHARSET));
        }
    }

    private class LoggingStream extends FilterOutputStream {

        private final StringBuilder sb = new StringBuilder();
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(OutputStream out) {
            super(out);
        }

        StringBuilder getStringBuilder(Charset charset) {
            // write entity to the builder
            final byte[] entity = baos.toByteArray();
            sb.append(new String(entity, 0, entity.length, charset));
            if (entity.length > maxEntitySize) {
                sb.append("...more...");
            }

            sb.append('\n');
            return sb;
        }

        @Override
        public void write(final int i) throws IOException {
            if (baos.size() <= maxEntitySize) {
                baos.write(i);
            }

            out.write(i);
        }
    }
}