/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.pdfa;

import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
public class PdfaServiceImpl implements PdfaService{

	private String inputFilePath;
	private static String embbedFilePath = "src/java/resources/CILA-invio.xml";
	
	private static String outputFilePath = "target/CILA-invio_IDISTANZA.pdf";
	private static String documentType = "Cila invio";
	private static String docFileName = "CILA-invio.xml";
	private static String docVersion = "2.0";
	//private static String colorProfilePath = "src/java/resources/sRGBColorSpaceProfile.icm";
	//private static String xmpTemplatePath = "src/java/resources/xmpTemplate.xml";

	
	@Autowired
    private PdfaConvertHelper pdfaServiceHelper;

	
	public PdfaServiceImpl(String pInputFilePath ) {
		this.inputFilePath=pInputFilePath;
	}
	

	public byte[] doConversion (String idIstanza,InputStream is) throws Exception {
		outputFilePath=outputFilePath.replaceAll("IDISTANZA", idIstanza);		
		
		InputStream  inRGB =null;
		InputStream  inXMP =null;
		InputStream  isFont =null;
		String nomefont= "LiberationSans-Regular";
		try {
			inRGB = getClass().getClassLoader().getResourceAsStream(pdfaServiceHelper.getColorProfilePath());
			inXMP = getClass().getClassLoader().getResourceAsStream(pdfaServiceHelper.getXmpTemplatePath());
			String FONTFILE_LiberationSans = pdfaServiceHelper.getPdfaFontFilePath();
			isFont = getClass().getClassLoader().getResourceAsStream(FONTFILE_LiberationSans);								
		} catch (Exception e) {
			e.printStackTrace();
		}										
		return pdfaServiceHelper.Convert(is);		
	}
	
	
}
