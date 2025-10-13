/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaVO;

public class PraticheExcelBuilder {
	private static Logger logger = Logger.getLogger(PraticheExcelBuilder.class.getCanonicalName());

    @Autowired
    private UtilsDate utilsDate;

    @SuppressWarnings("unchecked")
    public byte[] buildExcel(List<PraticaVO> elenco, HSSFWorkbook workbook) throws Exception {

    String pattern = "dd/MM/yyyy";
    DateFormat df = new SimpleDateFormat(pattern); 
  	// create a new Excel sheet
  	Sheet sheet = workbook.createSheet("Report pratiche");
  	logger.info("buildExcel:: create sheet");
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
  	styleValDef.setAlignment(HorizontalAlignment.CENTER);
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
  	
  	
  	col++;
  	header0.createCell(col).setCellValue("Numero Pratica");
  	header0.getCell(col).setCellStyle(style);
  	
  	col++;
  	header0.createCell(col).setCellValue("Anno");
  	header0.getCell(col).setCellStyle(style);
  	
  	col++;
  	header0.createCell(col).setCellValue("Ente");
  	header0.getCell(col).setCellStyle(style);  

  	col++;
  	header0.createCell(col).setCellValue("Comune");
  	header0.getCell(col).setCellStyle(style);  
  	
  	col++;
  	header0.createCell(col).setCellValue("Data creazione");
  	header0.getCell(col).setCellStyle(style);
  	// create data rows
  	int rowCount = 1;
  	int countCol = col;
  	for (PraticaVO item : elenco) {
  	    countCol = -1;
  	    Row aRow = sheet.createRow(rowCount++);

  	    countCol++;
        aRow.createCell(countCol).setCellValue(item.getNumeroPratica());
        aRow.getCell(countCol).setCellStyle(styleValNumber);

        countCol++;
        aRow.createCell(countCol).setCellValue(item.getAnno());
        aRow.getCell(countCol).setCellStyle(styleValDef);

        countCol++;
        aRow.createCell(countCol).setCellValue(item.getEnte().getDescrizione());
        aRow.getCell(countCol).setCellStyle(styleValDef);

  	    countCol++;
  	    aRow.createCell(countCol).setCellValue(item.getComune().getDescrizione());
  	    aRow.getCell(countCol).setCellStyle(styleValDef);

        countCol++;
        String dataRegPratica="";
        if(item.getDataCreazione()!=null)
        	dataRegPratica = item.getDataCreazione().format(DateTimeFormatter.ofPattern(pattern));
        aRow.createCell(countCol).setCellValue(dataRegPratica);
        aRow.getCell(countCol).setCellStyle(styleValDef);
  	    
  	}
  	//filtro ricerca
  	if(elenco.size()>0)
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
