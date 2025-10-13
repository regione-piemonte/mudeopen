/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

public class IstanzeExcelBuilder {
	private static Logger logger = Logger.getLogger(IstanzeExcelBuilder.class.getCanonicalName());

    @SuppressWarnings("unchecked")
    public byte[] buildExcelDocuments(List<IstanzaVO> elenco, HSSFWorkbook workbook, String exportType) throws Exception {
		// create a new Excel sheet
		Sheet sheet = workbook.createSheet("Report istanze");
		logger.info("buildExcelDocument:: create sheet");
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern); 
		sheet.setDefaultColumnWidth(30);

		Font fontHeader = workbook.createFont();
		fontHeader.setFontName("Arial");
		fontHeader.setBold(true);
		fontHeader.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex((short) 57, (byte) 66, (byte) 139, (byte) 202); // #428BCA
		palette.setColorAtIndex((short) 58, (byte) 211, (byte) 211, (byte) 211); // #F9F9F9
		
		// create style for header cells
		HSSFCellStyle style = addCellStyleHeader(workbook, fontHeader, palette);
		
		Font font = workbook.createFont();
		font.setFontName("Arial");
		
		HSSFCellStyle styleValDef = getNewStyle(workbook, font, null, null); // CELL STYLE GENERICO, DI DEFAULT
		HSSFCellStyle styleValDefAllCenter = getNewStyle(workbook, font, null, HorizontalAlignment.CENTER); // CELL STYLE GENERICO, DI DEFAULT ALLINEAMENTO CENTRO
		//HSSFCellStyle styleValNumber = getNewStyle(workbook, font, "#,###,###,###,#0.00", HorizontalAlignment.RIGHT); // CELL STYLE NUMERI
		//HSSFCellStyle styleValNumber4 = getNewStyle(workbook, font, "#,###,###,###,#0.00", HorizontalAlignment.RIGHT); // CELL STYLE NUMERI CON 2 DECIMALI DOPO LA VIRGOLA
		//HSSFCellStyle styleValNumberTotal = getNewStyle(workbook, fontBold, "#,###,###,###,#0.00", HorizontalAlignment.RIGHT); // CELL STYLE NUMERI TOTALI
		
		// create header row
		int col = -1;
		Row header0 = sheet.createRow(0);

		/*
		* backoffice.ds-scrivania:
			Numero istanza
			Numero pratica
			Intestatario
			Comune
			Indirizzo
			Data Ricezione
			Stato istanza
			Data stato
			Occupazione suolo pubblico
		
		* backoffice.ds-istanza:
			Numero istanza
			Numero pratica
			Tipo Pratica
			Comune
			Numero Protocollo
			Data Protocollo
			Responsabile Istruttoria
			Richiedente
			Ubicazione
			Progettista
		 */
		
		if(!exportType.startsWith("backoffice.ds-scrivania"))
			addLabel(style, ++col, header0, "Numero istanza");

		if(exportType.startsWith("backoffice."))
			addLabel(style, ++col, header0, "Numero paratica");
		else
			addLabel(style, ++col, header0, "Tipo istanza");
		
		if("backoffice.ds-istanza".equals(exportType)) {
			addLabel(style, ++col, header0, "Tipo pratica");
			addLabel(style, ++col, header0, "Comune");
			addLabel(style, ++col, header0, "Numero Protocollo");
			addLabel(style, ++col, header0, "Data Protocollo");
			addLabel(style, ++col, header0, "Responsabile Procedimento");
			addLabel(style, ++col, header0, "Richiedente");
			addLabel(style, ++col, header0, "Ubicazione");
			addLabel(style, ++col, header0, "Progettista");
		}
		else if(exportType.startsWith("backoffice.ds-scrivania")) {
			addLabel(style, ++col, header0, "Numero istanza");
			addLabel(style, ++col, header0, "Comune");
			addLabel(style, ++col, header0, "Titolo Intervento");
			addLabel(style, ++col, header0, "Indirizzo");
			addLabel(style, ++col, header0, "Intestatario");
			
		}
		else {
			addLabel(style, ++col, header0, "Intestatario");
			addLabel(style, ++col, header0, "Comune");
			addLabel(style, ++col, header0, "Indirizzo");
			addLabel(style, ++col, header0, "Data ricezione");
			addLabel(style, ++col, header0, "Stato");
			addLabel(style, ++col, header0, "Data Stato");
			addLabel(style, ++col, header0, "Occupazione suolo pubblico");
		}
		
		// create data rows
		int rowCount = 1;
		int countCol = -1;
		for (IstanzaVO item : elenco) {
		    countCol = -1;
		    Row aRow = sheet.createRow(rowCount++);

		    // Numero Istanza
		    
			if(!exportType.startsWith("backoffice.ds-scrivania"))
				addLabel(styleValDef, ++countCol, aRow, item.getCodiceIstanza());
		    
			if(exportType.startsWith("backoffice.")) // Numero pratica
				addLabel(styleValDef, ++countCol, aRow, item.getNumeroPratica());
			else // Tipo Istanza
				addLabelTipoIstanza(styleValDef, ++countCol, item, aRow);
		
			if("backoffice.ds-istanza".equals(exportType)) {
				// Tipo pratica
				addLabel(styleValDef, ++countCol, aRow, item.getSpeciePratica().getDescrizione());

			    // Comune
			    addLabel(styleValDef, ++countCol, aRow, item.getComune().getDescrizione());
			    
			    // Numero Protocollo
			    addLabel(styleValDef, ++countCol, aRow, item.getNumeroProtocollo());
			    
				// Data Protocollo
			    String date = item.getDataProtocollo() == null? "" : new SimpleDateFormat("dd/MM/yyyy").format(Date.from(item.getDataProtocollo().atZone(ZoneId.systemDefault()).toInstant()));
			    addLabel(styleValDef, ++countCol, aRow, date);
			    
				// Responsabile Istruttoria
			    addLabel(styleValDef, ++countCol, aRow, item.getResponsabile_procedimento());
			    
			    // Richiedente
			    addLabelIntestatario(styleValDef, ++countCol, item, aRow);
			    
			    // Ubicazione
			    addLabelIndirizzo(styleValDef, ++countCol, item, aRow);
			    
			    // Progettista
			    addLabel(styleValDef, ++countCol, aRow, item.getPm());
			}
			else if(exportType.startsWith("backoffice.ds-scrivania")) {
				addLabel(styleValDef, ++countCol, aRow, item.getCodiceIstanza());
				
			    // Comune
			    addLabel(styleValDef, ++countCol, aRow, item.getComune().getDescrizione());
			
				// Titolo Intervento
				addLabel(styleValDef, ++countCol, aRow, item.getKeywords());

			    // Indirizzo
			    addLabelIndirizzo(styleValDef, ++countCol, item, aRow);
				
			    // Intestatario
			    addLabelIntestatario(styleValDef, ++countCol, item, aRow);
			}
			else {
			    // Intestatario
			    addLabelIntestatario(styleValDef, ++countCol, item, aRow);
			
			    // Comune
			    addLabel(styleValDef, ++countCol, aRow, item.getComune().getDescrizione());
			
			    // Indirizzo
			    addLabelIndirizzo(styleValDef, ++countCol, item, aRow);
			
			    // Data ricezione
				addLabel(styleValDefAllCenter, ++countCol, aRow, item.getDataStatoDPS() == null? "" : df.format(Date.from(item.getDataStatoDPS().atZone(ZoneId.systemDefault()).toInstant())));
			
			    // Stato
			    addLabel(styleValDef, ++countCol, aRow, item.getStatoIstanza().getDescrizione());
			
			    // Data Stato
			    addLabel(styleValDefAllCenter, ++countCol, aRow, item.getDataStato() == null? "" : df.format(Date.from(item.getDataStato().atZone(ZoneId.systemDefault()).toInstant())));
			
			    // Occupazione Suolo Pubblico
			    addLabel(styleValDefAllCenter, ++countCol, aRow, item.getOccupazioneSuoloPubblico() ? "SI" : "NO");
			}
		}
		
		//filtro ricerca
		if(elenco.size()>0)
			sheet.setAutoFilter(new CellRangeAddress(0, rowCount - 1, 0, countCol));
		
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
		    workbook.write(bos);
		  	return bos.toByteArray();
		}
    }

	private HSSFCellStyle addCellStyleHeader(HSSFWorkbook workbook, Font fontHeader, HSSFPalette palette) {
		HSSFCellStyle style = ((HSSFWorkbook) workbook).createCellStyle();
		style.setFillForegroundColor(palette.getColor(57).getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setFont(fontHeader);
		return style;
	}

	private void addLabelTipoIstanza(HSSFCellStyle styleValDef, int countCol, IstanzaVO item, Row aRow) {
		StringBuffer tipologiaIstanza = new StringBuffer();
		if(item.getTipologiaIstanza() != null)
			tipologiaIstanza.append(item.getTipologiaIstanza().getDescrizione());
		if(item.getSpeciePratica() != null && item.getSpeciePratica().getDescrizione()!=null)
			tipologiaIstanza.append(" - ").append(item.getSpeciePratica().getDescrizione());
		if(tipologiaIstanza != null)
		    addLabel(styleValDef, countCol, aRow, tipologiaIstanza.toString());
	}

	private void addLabelIntestatario(HSSFCellStyle styleValDef, int countCol, IstanzaVO item, Row aRow) {
		String intestatario= "";
		if(item.getIntestatario() != null && item.getIntestatario().getAnagrafica()!=null && item.getIntestatario().getAnagrafica().getCognome() != null)
			intestatario = item.getIntestatario().getAnagrafica().getCognome()+" "+item.getIntestatario().getAnagrafica().getNome();
		else if(item.getIntestatario() != null && item.getIntestatario().getAnagrafica()!=null && item.getIntestatario().getAnagrafica().getCognome() == null)
			intestatario = item.getIntestatario().getAnagrafica().getRagioneSociale();
		addLabel(styleValDef, countCol, aRow, intestatario);
	}

	private void addLabelIndirizzo(HSSFCellStyle styleValDef, int countCol, IstanzaVO item, Row aRow) {
		StringBuilder indirizzo= new StringBuilder();
		if(item.getIndirizzo() != null  && item.getIndirizzo().getDug() != null && item.getIndirizzo().getDug().getDescrizione() != null )
			indirizzo.append(item.getIndirizzo().getDug().getDescrizione() + " ");
		if(item.getIndirizzo() != null  && item.getIndirizzo().getDuf() != null )
			indirizzo.append(item.getIndirizzo().getDuf() + " ");
		if(item.getIndirizzo() != null  && item.getIndirizzo().getNumero() != null )
			indirizzo.append(item.getIndirizzo().getNumero());
		addLabel(styleValDef, countCol, aRow, indirizzo.toString());
	}

	private HSSFCellStyle getNewStyle(HSSFWorkbook workbook, Font font, String cellFormat, HorizontalAlignment align) {
		HSSFCellStyle styleValNumber = ((HSSFWorkbook) workbook).createCellStyle();
		styleValNumber.setWrapText(true);
		styleValNumber.setVerticalAlignment(VerticalAlignment.TOP);
		styleValNumber.setBorderBottom(BorderStyle.THIN);
		styleValNumber.setBorderTop(BorderStyle.THIN);
		styleValNumber.setBorderLeft(BorderStyle.THIN);
		styleValNumber.setBorderRight(BorderStyle.THIN);
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		styleValNumber.setFont(font);
		
		if(align != null)
			styleValNumber.setAlignment(align);
		if(cellFormat != null)
			styleValNumber.setDataFormat(workbook.createDataFormat().getFormat(cellFormat));
		
		return styleValNumber;
	}

	private void addLabel(HSSFCellStyle style, int col, Row header0, String label) {
		header0.createCell(col).setCellValue(label);
		header0.getCell(col).setCellStyle(style);
	}

    @SuppressWarnings("unchecked")
    public byte[] buildExcelPraticheDocuments(List<IstanzaVO> elenco, HSSFWorkbook workbook) throws Exception {

  	// create a new Excel sheet
  	Sheet sheet = workbook.createSheet("Report pratiche");
  	logger.info("buildExcelPraticheDocuments:: create sheet");
  	String pattern = "dd/MM/yyyy";
  	DateFormat df = new SimpleDateFormat(pattern);
  	sheet.setDefaultColumnWidth(30);

  	// create style for header cells
  	HSSFCellStyle style = ((HSSFWorkbook) workbook).createCellStyle();
  	HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
  	// palette.setColorAtIndex((short)57, (byte)224, (byte)233, (byte)242); //
  	// color #E0E9F2

  	palette.setColorAtIndex((short) 57, (byte) 66, (byte) 139, (byte) 202); // color
  	// #428BCA

  	palette.setColorAtIndex((short) 58, (byte) 211, (byte) 211, (byte) 211); // color
  	// #F9F9F9

  	style.setFillForegroundColor(palette.getColor(57).getIndex());
  	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

  	// CellStyle style = workbook.createCellStyle();
  	Font font = workbook.createFont();
  	font.setFontName("Arial");
  	// style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
  	// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
  	style.setVerticalAlignment(VerticalAlignment.CENTER);
  	style.setAlignment(HorizontalAlignment.CENTER);
  	
  	style.setBorderBottom(BorderStyle.MEDIUM);
  	style.setBorderTop(BorderStyle.MEDIUM);
  	style.setBorderLeft(BorderStyle.MEDIUM);
  	style.setBorderRight(BorderStyle.MEDIUM);
  	font.setBold(true);
  	font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
  	style.setFont(font);

  	// CELL STYLE GENERICO, DI DEFAULT
  	HSSFCellStyle styleValDef = ((HSSFWorkbook) workbook).createCellStyle();
  	font = workbook.createFont();
  	font.setFontName("Arial");
  	styleValDef.setWrapText(true);
  	styleValDef.setVerticalAlignment(VerticalAlignment.TOP);
  	styleValDef.setBorderBottom(BorderStyle.THIN);
  	styleValDef.setBorderTop(BorderStyle.THIN);
  	styleValDef.setBorderLeft(BorderStyle.THIN);
  	styleValDef.setBorderRight(BorderStyle.THIN);
  	font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
  	styleValDef.setFont(font);

  	// CELL STYLE NUMERI
  	HSSFCellStyle styleValNumber = ((HSSFWorkbook) workbook).createCellStyle();
  	font = workbook.createFont();
  	font.setFontName("Arial");
  	styleValNumber.setWrapText(true);
  	styleValNumber.setVerticalAlignment(VerticalAlignment.TOP);
  	styleValNumber.setBorderBottom(BorderStyle.THIN);
  	styleValNumber.setBorderTop(BorderStyle.THIN);
  	styleValNumber.setBorderLeft(BorderStyle.THIN);
  	styleValNumber.setBorderRight(BorderStyle.THIN);
  	font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
  	styleValNumber.setFont(font);
  	styleValNumber.setAlignment(HorizontalAlignment.RIGHT);
  	styleValNumber.setDataFormat(workbook.createDataFormat().getFormat("#,###,###,###,#0.00"));

  	// CELL STYLE NUMERI CON 2 DECIMALI DOPO LA VIRGOLA
  	HSSFCellStyle styleValNumber4 = ((HSSFWorkbook) workbook).createCellStyle();
  	font = workbook.createFont();
  	font.setFontName("Arial");
  	styleValNumber4.setWrapText(true);
  	styleValNumber4.setVerticalAlignment(VerticalAlignment.TOP);
  	styleValNumber4.setBorderBottom(BorderStyle.THIN);
  	styleValNumber4.setBorderTop(BorderStyle.THIN);
  	styleValNumber4.setBorderLeft(BorderStyle.THIN);
  	styleValNumber4.setBorderRight(BorderStyle.THIN);
  	font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
  	styleValNumber4.setFont(font);
  	styleValNumber4.setAlignment(HorizontalAlignment.RIGHT);
  	styleValNumber4.setDataFormat(workbook.createDataFormat().getFormat("#,###,###,###,#0.00"));

  	// CELL STYLE NUMERI TOTALI
  	HSSFCellStyle styleValNumberTotal = ((HSSFWorkbook) workbook).createCellStyle();
  	font = workbook.createFont();
  	font.setFontName("Arial");
  	font.setBold(true);
  	styleValNumberTotal.setWrapText(true);
  	styleValNumberTotal.setVerticalAlignment(VerticalAlignment.TOP);
  	styleValNumberTotal.setBorderBottom(BorderStyle.THIN);
  	styleValNumberTotal.setBorderTop(BorderStyle.THIN);
  	styleValNumberTotal.setBorderLeft(BorderStyle.THIN);
  	styleValNumberTotal.setBorderRight(BorderStyle.THIN);
  	font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
  	styleValNumberTotal.setFont(font);
  	styleValNumber4.setAlignment(HorizontalAlignment.RIGHT);
  	styleValNumberTotal.setDataFormat(workbook.createDataFormat().getFormat("#,###,###,###,#0.00"));
  	
  	// create header row
  	int col = -1;
  	Row header0 = sheet.createRow(0);
  	
    addLabel(style, ++col, header0, "Numero Istanza");
    addLabel(style, ++col, header0, "Tipo Istanza");
    addLabel(style, ++col, header0, "Intestatario");
    addLabel(style, ++col, header0, "Comune");
    addLabel(style, ++col, header0, "Indirizzo");
    addLabel(style, ++col, header0, "Data ricezione");
    addLabel(style, ++col, header0, "Stato");
    addLabel(style, ++col, header0, "Data Stato");
 
  	// create data rows
  	int rowCount = 1;
  	int countCol = col;
  	for (IstanzaVO item : elenco) {
  	    countCol = -1;
  	    Row aRow = sheet.createRow(rowCount++);

  	    countCol++;
        aRow.createCell(countCol).setCellValue(item.getCodiceIstanza());
        aRow.getCell(countCol).setCellStyle(styleValDef);

        countCol++;
        aRow.createCell(countCol).setCellValue(item.getTipologiaIstanza().getDescrizione());
        aRow.getCell(countCol).setCellStyle(styleValDef);

  	    countCol++;
  	    addLabelIntestatario(styleValDef, countCol, item, aRow);

  	    countCol++;
  	    aRow.createCell(countCol).setCellValue(item.getComune().getDescrizione());
  	    aRow.getCell(countCol).setCellStyle(styleValDef);

  	  countCol++;
      StringBuilder indirizzo= new StringBuilder();
      if(item.getIndirizzo() != null && item.getIndirizzo().getDug() != null && item.getIndirizzo().getDug().getDescrizione() != null ) {
      	indirizzo.append(item.getIndirizzo().getDug().getDescrizione());
      	indirizzo.append(" ");
      }
      if(item.getIndirizzo() != null  && item.getIndirizzo().getDuf() != null ) {
      	indirizzo.append(item.getIndirizzo().getDuf());
      	indirizzo.append(" ");
      }
      if(item.getIndirizzo() != null  && item.getIndirizzo().getNumero() != null ) {
      	indirizzo.append(item.getIndirizzo().getNumero());
      }

        aRow.createCell(countCol).setCellValue(indirizzo.toString());
        aRow.getCell(countCol).setCellStyle(styleValDef);

        countCol++;
        String dataCreazione="";
        if(item.getDataCreazione()!=null)
        	dataCreazione = item.getDataCreazione().format(DateTimeFormatter.ofPattern(pattern));
        addLabel(styleValDef, countCol, aRow, dataCreazione);
        countCol++;
        aRow.createCell(countCol).setCellValue(item.getStatoIstanza().getDescrizione());
        aRow.getCell(countCol).setCellStyle(styleValDef);

        countCol++;
        String dataStato="";
        if(item.getDataStato()!=null)
        	dataStato = df.format(item.getDataStato());
        addLabel(styleValDef, countCol, aRow, dataStato);

  	    
  	}
  	//filtro ricerca
  	sheet.setAutoFilter(new CellRangeAddress(0, rowCount - 1, 0, countCol));
  	ByteArrayOutputStream bos = new ByteArrayOutputStream();
  	try {
  	    workbook.write(bos);
  	} finally {
  	    bos.close();
  	}
  	byte[] bytes = bos.toByteArray();
  	return bytes;
	
  }
}
