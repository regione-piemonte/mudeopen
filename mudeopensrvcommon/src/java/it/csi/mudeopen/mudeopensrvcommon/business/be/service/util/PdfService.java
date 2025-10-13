/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface PdfService {

    byte[] getCompiledProcuraPDF(Long idIStanza, InputStream in, boolean isPDF) throws Exception;

    byte[] getCompiledTemplate(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in) throws Exception;

    byte[] getCompiledTemplatePDF(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in) throws Exception;
    byte[] getCompiledTemplatePDF(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in, boolean isPDF) throws Exception;
    
//    byte[] mergeDocuments(final List<byte[]> bytes) throws Docx4JException, IOException, JAXBException;
    String getTemplateDir();
}