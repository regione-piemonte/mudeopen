/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.pdfa;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.pdf.PDFDictionary;
import org.apache.fop.pdf.PDFDocument;
import org.apache.fop.pdf.PDFName;
import org.apache.fop.pdf.PDFStructTreeRoot;
import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaPDF;
import org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId;
import org.apache.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObjectKey;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDObjectReference;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.encoding.Encoding;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.graphics.form.PDTransparencyGroup;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.PDSoftMask;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.docx.poi.itext.XWPF2PDFViaITextConverter;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.PropertyUtil;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Styles;
import org.docx4j.jaxb.Context;
@Component
public class PdfaConvertHelper {
	private static float pdfVer = 1.7f;

	private static Logger logger = Logger.getLogger(PdfaConvertHelper.class.getCanonicalName());
	
	private static String documentVersion="2.0";
	private static String documentType="CILA Invio";
	private static String documentFileName="";
	
	private String colorProfilePath = "/resources/sRGBColorSpaceProfile.icm";
	private String xmpTemplatePath = "/resources/xmpTemplate.xml";
	private String pdfaFont = "LiberationSans-Regular";
	private String pdfaFontFilePath = "/resources/LiberationSans-Regular.ttf";
	
	@Autowired
	PropertyUtil propertyUtil;
	
	
	public long isPdfaEnabled() {
   		MudeCProprieta ggRetryProperty=propertyUtil.getPropertyValue(PropertyUtil.MUDE_PDFA_ENABLED);
   		long val1=0L;
   		try {
   			val1=Long.parseLong(ggRetryProperty.getValore());
   		}catch (Exception e) {
   			e.printStackTrace();
		}	
   		return val1;
	}

	public long isPdfaFontEnbedded() {
   		MudeCProprieta ggRetryProperty=propertyUtil.getPropertyValue(PropertyUtil.MUDE_PDFA_FONT_EMBEDDED);
   		long val1=0L;
   		try {
   			val1=Long.parseLong(ggRetryProperty.getValore());
   		}catch (Exception e) {
   			e.printStackTrace();
		}	
   		return val1;
	}
	
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();

	public static void printMetadata(PDDocument document) throws IOException {
        PDDocumentInformation info = document.getDocumentInformation();
        PDDocumentCatalog cat = document.getDocumentCatalog();
        PDMetadata metadata = cat.getMetadata();

        logger.info("Page Count=" + document.getNumberOfPages());
        logger.info("Title=" + info.getTitle());
        logger.info("Author=" + info.getAuthor());
        logger.info("Subject=" + info.getSubject());
        logger.info("Keywords=" + info.getKeywords());
        logger.info("Creator=" + info.getCreator());
        logger.info("Producer=" + info.getProducer());
        logger.info("Trapped=" + info.getTrapped());   
    }
	
	public void dumptDocumentInfo(PDDocument document) {		
		PDDocumentInformation info = document.getDocumentInformation();
		Set infoKey=info.getMetadataKeys();
		Iterator iter =infoKey.iterator();
		while(iter.hasNext()) {
			String name=(String)iter.next();
			logger.info("[getDocumentInfo] KEY : "+name+" - "+info.getCustomMetadataValue(name));
		}		
	}
	

    private final Map<String, Font> cache = new HashMap();
	
	public PdfOptions getPdfOptions(Options options) {
		PdfOptions pdfOptions = XWPF2PDFViaITextConverter.getInstance().toPdfOptions( options );                       
        pdfOptions.fontProvider( new IFontProvider(){
            //private final String nomefontPdfa=getPdfaFont();

            private final String fontPdfaPath = getPdfaFontFilePath();
        	@Override        	
	        public Font getFont( String familyName, String encoding, float size, int style, Color color ){
                String key = String.format("%s_%s_%s_%s_%s", familyName, encoding, size, style, color == null ? StringUtils.EMPTY : color.toString());

                synchronized (cache) {
            		if(cache.containsKey(key))
            			return cache.get(key);
	
	        		Path temp = null;
	                try{
	                	//-----------------------------------------------------
	                	logger.info(" [FONT INJECT] -- familyName --"+familyName);
	                	//-----------------------------------------------------
	                	
	               		temp = Files.createTempFile("temporary", ".ttf");
	               		String absolutePath = temp.toString();
	               		//String separator = FileSystems.getDefault().getSeparator();
	                    //String tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(separator));
	                    //File fileFromPath = temp.toFile();
	                    //OutputStream outStream = new FileOutputStream(fileFromPath);
	            		//String nomefont= nomefontPdfa;
	            		InputStream isFont=null;
	            		try {
	            			String FONTFILE_LiberationSans = fontPdfaPath;
	            			isFont = getClass().getClassLoader().getResourceAsStream(FONTFILE_LiberationSans);								
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}										
	            		java.nio.file.Files.copy(isFont, temp, StandardCopyOption.REPLACE_EXISTING);
	
	                    BaseFont bfCustom = BaseFont.createFont( absolutePath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
	                    Font fontMine = new Font( bfCustom, size, style, color );
	                    if ( familyName != null )
	                    	fontMine.setFamily( familyName );
	                    
	            		//BaseFont baseFont = BaseFont.createFont(absolutePath, encoding, BaseFont.EMBEDDED);
	            		//cache.put(key, new Font(baseFont, size, style, color));
	            		
	            		cache.put(key, fontMine);
	                    return fontMine;
	                }
	                catch ( Throwable e ){
	                    e.printStackTrace();
	                    // An error occurs, use the default font provider.
	                    return ITextFontRegistry.getRegistry().getFont( familyName, encoding,size,style, color );
	                }
	            	finally {
	            		if(temp != null)
	            			temp.toFile().deleteOnExit(); // this is too late... todo: find a way to delete is after use
	            	}
				}
            }

    	});
        return pdfOptions;				
	}
	
	
	public byte[] Convert(InputStream is) {
		byte[] array=null;
		try {
			array = is.readAllBytes();
			return Convert(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public  byte[] Convert(byte[] fileArray) throws Exception {
		PDDocument doc = null;
		InputStream is=new ByteArrayInputStream(fileArray);				
		doc=Loader.loadPDF(is, "");
		is.close();
		//dumptDocumentInfo(doc);
		
		//---------------------------------------------------------------
		//
		//---------------------------------------------------------------
		File outputFile = null;
		try {
			outputFile =File.createTempFile("pdfa", ".pdf");
			//---------------------------------------------------------------
			String FONTFILE_NAME=getPdfaFont();
			
			//---------------------------------------------------------------
			//InputStream  inRGB = getClass().getClassLoader().getResourceAsStream("/resources/sRGBColorSpaceProfile.icm");
			
			//File colorPFile = new File(pdfa3Components.getColorProfilePath());
			//InputStream colorProfile = isRGB;//new FileInputStream(colorPFile);
			InputStream colorProfile = getClass().getClassLoader().getResourceAsStream(getColorProfilePath());
			
			PDStructureTreeRoot root1 = doc.getDocumentCatalog().getStructureTreeRoot();
			 
			//---------------------------------------------------------------
			PDDocumentCatalog cat = makeA3compliant(doc);
			PDStructureTreeRoot root = cat.getStructureTreeRoot();
		    if (root != null) {
		        List<PDDocumentCatalog> treeRoot = new ArrayList<PDDocumentCatalog>();
		    }    
			
			//attachFile(doc, pdfa3Components.getEmbedFilePath());
	
			addOutputIntent(doc, cat, colorProfile);
			//---------------------------------------------------------------
			doc.setVersion(pdfVer);
			//---------------------------------------------------------------
			
			//-------------------------------------------------------------------------
			//Load Font
			//-------------------------------------------------------------------------
			InputStream isFont = getClass().getClassLoader().getResourceAsStream(getPdfaFontFilePath());
			PDFont font0 = PDType0Font.load(doc, isFont, false);
			
			//https://stackoverflow.com/questions/59377820/how-to-heal-inconsistent-parent-tree-mappings-in-a-pdf-created-by-pdfbox
			//-----------------------------------------------------------------------------
		    //Stream Reader
			PDPageTree  treePages=doc.getPages();
			Iterator<PDPage> iter = treePages.iterator();
			int i=0;
			
			while(iter.hasNext()) {
				PDPage page=(PDPage)iter.next();			
		
							
				PDResources resources = page.getResources();	
	
				
	
				
				//https://stackoverflow.com/questions/54142847/pdfbox-2-does-not-create-pdf-a-file
				//https://stackoverflow.com/questions/39285329/java-merge-multiple-images-to-a-single-pdf-using-pdfbox
				//https://www.adobe.com/content/dam/acom/en/devnet/pdf/pdfs/PDF32000_2008.pdf
				for (COSName kind : resources.getXObjectNames()) {
				   	logger.info(" kind :" +kind);	
				    PDXObject xo=resources.getXObject(kind);
				    PDImageXObject image = null;
				    if (xo instanceof PDImageXObject) {
				    	image= (PDImageXObject)xo;
				    	int h=image.getHeight();
				    	int w=image.getWidth();
				    	BufferedImage opaque=image.getOpaqueImage();
				    	PDImageXObject softmask1=image.getMask();
				    	COSArray colorArray=null;
				    	PDImageXObject softmask2=image.getSoftMask();
				    	if(softmask2!=null)
				    		colorArray=softmask2.getColorKeyMask();
				    	//l'immagine is  vuota..
				    	COSArray decodeArray = image.getDecode();
				    	BufferedImage smaskBI = image.getRawImage();
				    	
				    	String OUTPUT_DIR = "/tmp/";
				    	String filename = OUTPUT_DIR + "extracted-image-" + i + ".png";
				    	PDImageXObject newImage=new PDImageXObject(doc);
				    	//Toglie le immagini..
				    	//resources.put(kind, newImage);
				    }
				    
				    PDStream streamOX= xo.getStream();
				    COSStream cobject= streamOX.getCOSObject();
				    COSBase smask = cobject.getItem(COSName.SMASK);
				    COSObjectKey smaskKey=null;
				    if(smask!=null) {
				    	smaskKey=smask.getKey();
				    	logger.info(" smaskKey :" +smaskKey.getNumber());
				    	if(resources.getExtGState(kind)!=null) {
					    	PDSoftMask softMask = resources.getExtGState(kind).getSoftMask();
					        if (softMask != null){
					          PDTransparencyGroup group = softMask.getGroup();
					          logger.info(" group :" +group.toString());
					        }  
				    	}
						 cobject.setItem(kind, resources);			            
				    }			    
				}
		    //-----------------------------------------------------------------------------
		    COSDictionary rootDictionary=null;
				
			
			//-----------------------------------------------------------------------------
	
		   PDFDocument pdfDoc = new PDFDocument("");
	       COSDictionary pdfRoot = new COSDictionary();
	       COSDictionary mapRole = new COSDictionary();
		   PDFStructTreeRoot structTreeRoot = pdfDoc.getRoot().getStructTreeRoot();
		   PDFDictionary rootBaseRoleMap = new PDFDictionary();
	       //PDFPage pdfpage= new PDFPage(new PDFResources(pdfDoc), 0, null, null, null, null);
		   //PDFBoxAdapter adapter = new PDFBoxAdapter(pdfpage, new HashMap(), new HashMap<Integer, PDFArray>());
	       ///DocumentRootModifier modifier = new DocumentRootModifier(adapter, pdfDoc);
	       //PDFPage pdfpage = new PDFPage(new PDFResources(pdfDoc), 0, r, r, r, r);
		   //pdfpage.setObjectNumber(1);
		   //pdfpage.setDocument(pdfDoc);
	       pdfDoc.makeStructTreeRoot(null);		
	       PDFName para = new PDFName("P");
	       rootBaseRoleMap.put("MyPara", para);
	       //structTreeRoot.put("RoleMap", rootBaseRoleMap);
	       //modifier.structTreeRootEntriesToCopy(pdfRoot);
	       //structTreeRoot = pdfDoc.getRoot().getStructTreeRoot();       
		   
			PDActionURI action = new PDActionURI();
			//action.setURI(PDFUtils.link);
			//txtLink.setAction(action);
			//page1.getAnnotations().add(txtLink);
			PDObjectReference pd= new PDObjectReference();
			//pd.setReferencedObject(txtLink);
			//structureElement.appendKid(pd);
			//currentSection.appendKid(structureElement);
			//structureElement formBuilder.addContentToParent(COSName.P, null, page1, structureElement, PDFUtils.mcid);			
		    //-----------------------------------------------------------------------------		
	    
				
			//Repalce string
			//doc=replaceText(doc, "\t", ""); 
			
	
			
			//---------------------------------------------------------------
			//FONT
			//---------------------------------------------------------------		
			Encoding encoding = Encoding.getInstance(COSName.WIN_ANSI_ENCODING);
			PDGraphicsState graphicsState = null;
			
					
			//-------------------------------------------------------------------------
			//
			//-------------------------------------------------------------------------
			PDFTextStripper reader = new PDFTextStripper();
						
			//------------------------------------------------------------------------
			//(1)
			//------------------------------------------------------------------------
	
				//------------------------------------------------------------------------
				long pageFontEmbedded=isPdfaFontEnbedded();
				long doReadFontPage=isPdfaEnabled();
				// Start a new content stream which will "hold" the to be created content
				if(doReadFontPage==1)
				for (COSName name : resources.getFontNames()) {
		            PDFont font = resources.getFont(name);
		            String fontName = font.getName();
		            String sCOSName1=name.getName();	            
		        	logger.info(" sCOSName1 :" +sCOSName1);
	            	logger.info(" PDType1Font :" +fontName);
	            	PDType0Font type0font = (PDType0Font)font0;
	            	if (fontName.lastIndexOf("LiberationSans-Regular")>=0) {
	                	logger.info(" ----------- (Iniect Font!)----------------");                	                	
	            		if(pageFontEmbedded<0) {
	            			resources.put(name, type0font);
	            			//PDPageContentStream contentStream  = new PDPageContentStream(doc, page);
	            			//contentStream.setFont( font0, 10 );
	            			////il content stram và chiuso sempre..
	            			//if(contentStream!=null)
	        				//contentStream.close();
	            			pageFontEmbedded++;
	            		}        			
	            	}	
				}                
				//------------------------------------------------------------------------
				//(1) END
				//------------------------------------------------------------------------
				
				i++;						
				//---------------------------------------------------------------
				//Salva il file..
				//---------------------------------------------------------------			
				//doc.saveDAO(outputFile);
				
			}
			
			logger.info("[ pdfa ptemp file]"+ outputFile.getAbsolutePath());
			
			//ByteArrayOutputStream outStream =new ByteArrayOutputStream(); 
			//doc.saveDAO(outStream);
			doc.save(outputFile);		
			doc.close();
			//return outStream.toByteArray();
			return FileUtils.readFileToByteArray(outputFile);
		} finally {
			if(outputFile != null)
				outputFile.delete();
		}
	}

	
	
    protected void checkSMask(COSStream xobject){
        COSBase smask = xobject.getItem(COSName.SMASK);
    }
	
	/*
	 * public static PDDocument replaceText(PDDocument document, String
	 * searchString, String replacement) throws IOException { if
	 * (StringUtils.isBlank(searchString) || StringUtils.isBlank(replacement)) {
	 * return document; } PDPageTree pages =
	 * document.getDocumentCatalog().getPages(); for (PDPage page : pages) {
	 * PDFStreamParser parser = new PDFStreamParser(page); parser.parse(); List
	 * tokens = ((Object) parser).getTokens(); for (int j = 0; j < tokens.size();
	 * j++) { Object next = tokens.get(j); if (next instanceof Operator) { Operator
	 * op = (Operator) next; //Tj and TJ are the two operators that display strings
	 * in a PDF if (op.getName().equals("Tj")) { // Tj takes one operator and that
	 * is the string to display so lets update that operator COSString previous =
	 * (COSString) tokens.get(j - 1); String string = previous.getString(); string =
	 * string.replaceFirst(searchString, replacement);
	 * previous.setValue(string.getBytes()); } else if (op.getName().equals("TJ")) {
	 * COSArray previous = (COSArray) tokens.get(j - 1); for (int k = 0; k <
	 * previous.size(); k++) { Object arrElement = previous.getObject(k); if
	 * (arrElement instanceof COSString) { COSString cosString = (COSString)
	 * arrElement; String string = cosString.getString(); string =
	 * StringUtils.replaceOnce(string, searchString, replacement);
	 * cosString.setValue(string.getBytes()); } } } } } // now that the tokens are
	 * updated we will replace the page content stream. PDStream updatedStream = new
	 * PDStream(document); OutputStream out = updatedStream.createOutputStream();
	 * ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
	 * tokenWriter.writeTokens(tokens); page.setContents(updatedStream);
	 * out.close(); } return document; }
	 */
	private static void addOutputIntent(PDDocument doc, PDDocumentCatalog cat, InputStream colorProfile)
			throws IOException {
		if (cat.getOutputIntents().isEmpty()) {
			PDOutputIntent oi = new PDOutputIntent(doc, colorProfile);
			oi.setInfo("sRGB IEC61966-2.1");
			oi.setOutputCondition("sRGB IEC61966-2.1");
			oi.setOutputConditionIdentifier("sRGB IEC61966-2.1");
			oi.setRegistryName("http://www.color.org");
			cat.addOutputIntent(oi);
		}

	}

	private static void attachFile(PDDocument doc, String embedFilePath) throws IOException {
		
		PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();

		File embedFile = new File(embedFilePath);
		
		String subType = Files.probeContentType(FileSystems.getDefault().getPath(embedFilePath));		
		String embedFileName = FilenameUtils.getName(embedFilePath);
		// first create the file specification, which holds the embedded file

		PDComplexFileSpecification fs = new PDComplexFileSpecification();
		fs.setFile(embedFileName);
		COSDictionary dict = fs.getCOSObject();
		// Relation "Source" for linking with eg. catalog
		dict.setName("AFRelationship", "Source");

		dict.setString("UF", embedFileName);

		InputStream is = new FileInputStream(embedFile);

		PDEmbeddedFile ef = new PDEmbeddedFile(doc, is);

		// set some of the attributes of the embedded file
		ef.setModDate(GregorianCalendar.getInstance());

		ef.setSize((int) embedFile.length());
		ef.setCreationDate(new GregorianCalendar());
		fs.setEmbeddedFile(ef);
		ef.setSubtype(subType);

		// now add the entry to the embedded file tree and set in the document.
		efTree.setNames(Collections.singletonMap(embedFileName, fs));

		// attachments are stored as part of the "names" dictionary in the
		PDDocumentCatalog catalog = doc.getDocumentCatalog();

		PDDocumentNameDictionary names = new PDDocumentNameDictionary(doc.getDocumentCatalog());
		names.setEmbeddedFiles(efTree);
		catalog.setNames(names);

		COSDictionary dict2 = catalog.getCOSObject();
		COSArray array = new COSArray();
		array.add(fs.getCOSObject());
		dict2.setItem("AF", array);
	}

	private   PDDocumentCatalog makeA3compliant(PDDocument doc) throws Exception {
		String producer = "ENG";
		
		PDDocumentCatalog cat = doc.getDocumentCatalog();
		PDDocumentInformation pdd = doc.getDocumentInformation();
		PDMetadata metadata = new PDMetadata(doc);

		printMetadata(doc);
		
		cat.setMetadata(metadata);
		
		PDDocumentInformation pdi = new PDDocumentInformation();
		pdi.setProducer(pdd.getProducer()==null?producer:pdd.getProducer());
		pdi.setAuthor(pdd.getAuthor());
		pdi.setTitle(pdd.getTitle());
		pdi.setSubject(pdd.getSubject());
		pdi.setKeywords(pdd.getKeywords());
		
		//-------------------------------------------------
		XMPMetadata xmp = new XMPMetadata();
		XMPSchemaPDFAId pdfaid = new XMPSchemaPDFAId(xmp);
		xmp.addSchema(pdfaid);
		XMPSchemaPDF pdf = xmp.addPDFSchema();
		pdf.setProducer(producer);
		pdf.setAbout("");
		
		PDMarkInfo markinfo = new PDMarkInfo();
		markinfo.setMarked(true);
		doc.getDocumentCatalog().setMarkInfo(markinfo);
		//-------------------------------------------------
		printMetadata(doc);
		
		// Set OID
		// pdi.setCustomMetadataValue("OID", "10.2.3.65.5");
		doc.setDocumentInformation(pdi);

		// use for eTax invoice only
		Charset charset = StandardCharsets.UTF_8;

		//---------------------------------------------------
		//byte from inputStream!		
		//---------------------------------------------------
		//ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		//int nRead=-1;
	    //byte[] data = new byte[isXMP.available()];
	    //while ((nRead = isXMP.read(data, 0, data.length)) != -1) {
	    	//    buffer.write(data, 0, nRead);
	        //}
	    //buffer.flush();		
		//byte[] fileBytes = buffer.toByteArray();	
		InputStream isXMP  = getClass().getClassLoader().getResourceAsStream(getXmpTemplatePath());
		byte[] fileBytes =isXMP.readAllBytes(); //Files.readAllBytes(new File(pdfa3Components.getXmpTemplatePath()).toPath());
		//---------------------------------------------------
		String content = new String(fileBytes, charset);
		content = content.replaceAll("@DocumentFileName", PdfaConvertHelper.documentFileName);
		content = content.replaceAll("@DocumentType", PdfaConvertHelper.documentType);
		content = content.replaceAll("@DocumentVersion", PdfaConvertHelper.documentVersion);
		//----------------------------------------------------
		String sModDate=pdd.getCustomMetadataValue("ModDate");
		String sCreationDate=pdd.getCustomMetadataValue("CreationDate");
		String sProducer=pdd.getCustomMetadataValue("Producer");
		content = content.replaceAll("@ModDate", sModDate);
		content = content.replaceAll("@CreationDate", sCreationDate);
		content = content.replaceAll("@Producer", sProducer);
		
		
		byte[] editedBytes = content.getBytes(charset);

		metadata.importXMPMetadata(editedBytes);
		
		printMetadata(doc);
	
		
		return cat;
	}
	
	public static byte[] getMergedFontDocument(WordprocessingMLPackage target)
			throws Exception {
    	File mergedDocx = File.createTempFile("merged", ".docx");
		try {
	    	target.save(mergedDocx);
	    	//----------------------------------------
	    	ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] bOut=FileUtils.readFileToByteArray(mergedDocx);
	        InputStream isDocxOut=new ByteArrayInputStream(bOut);
	        WordprocessingMLPackage targetDOCX  = WordprocessingMLPackage.load(isDocxOut);
	        MainDocumentPart mp = targetDOCX.getMainDocumentPart ();
	        Styles styles = mp.getStyleDefinitionsPart ().getJaxbElement ();
	        ObjectFactory factory = Context.getWmlObjectFactory ();
	        for (org.docx4j.wml.Style  s : styles.getStyle ()){
	        	logger.info("[Style]: "+s.getName ().getVal ());
	            //if (s.getName ().getVal ().equals ("Standard")){
	                RPr rpr = s.getRPr ();
	                if (rpr == null){
	                    rpr = factory.createRPr ();
	                    s.setRPr (rpr);                       
	                }
	                RFonts rf = rpr.getRFonts ();                    
	                if (rf == null){
	                	   rf = factory.createRFonts ();
	                       rpr.setRFonts (rf);
	                }
	                // This is where you set your font name.
	                rf.setAscii ("LiberationSans-Regular");
	                //}                
	        }	            
			return FileUtils.readFileToByteArray(mergedDocx);				
		} finally {
			mergedDocx.delete();
		}
	}
	
	
	//--------------------------------------------------------

	public String getColorProfilePath() {
		return colorProfilePath;
	}

	public void setColorProfilePath(String colorProfilePath) {
		this.colorProfilePath = colorProfilePath;
	}

	public String getXmpTemplatePath() {
		return xmpTemplatePath;
	}

	public void setXmpTemplatePath(String xmpTemplatePath) {
		this.xmpTemplatePath = xmpTemplatePath;
	}

	public String getPdfaFont() {
		return pdfaFont;
	}

	public void setPdfaFont(String pdfaFont) {
		this.pdfaFont = pdfaFont;
	}

	public String getPdfaFontFilePath() {
		return pdfaFontFilePath;
	}

	public void setPdfaFontFilePath(String pdfaFontFilePath) {
		this.pdfaFontFilePath = pdfaFontFilePath;
	}

	//----------------------------------
	
}