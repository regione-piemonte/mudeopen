/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.input.CloseShieldInputStream;

public class InputStreamDataSource implements DataSource {
    private InputStream inputStream;
    private String filename = "";
    private String mimeType = null;

    public InputStreamDataSource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStreamDataSource(InputStream inputStream, String filename, String mimeType) {
        this.inputStream = inputStream;
        this.mimeType = mimeType;
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new CloseShieldInputStream(inputStream);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getContentType() {
        return mimeType == null ? MediaType.APPLICATION_OCTET_STREAM : mimeType;
    }

    @Override
    public String getName() {
        return filename;
    }
}