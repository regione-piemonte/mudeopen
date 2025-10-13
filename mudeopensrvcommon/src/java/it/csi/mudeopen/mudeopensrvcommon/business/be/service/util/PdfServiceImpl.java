/*
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.OptionsHelper;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.pdfa.PdfaConvertHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.rules.RulePdfServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProfiloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocCatastoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocUbicazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.JsonUtils;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.docx4j.dml.CTBlip;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.contenttype.ContentTypeManager;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPart;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STPTabAlignment;
import org.docx4j.wml.STPTabLeader;
import org.docx4j.wml.STPTabRelativeTo;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Query;

import static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImageName;
import org.apache.commons.collections4.CollectionUtils;

@Service
public class PdfServiceImpl implements PdfService {

    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static Logger logger = Logger.getLogger(PdfServiceImpl.class.getCanonicalName());
    //--------------------------------------------------------------
    //FOOTER BEGIN
    //--------------------------------------------------------------
    //@TODO: verificare il numero pagina...
    private static org.docx4j.wml.ObjectFactory objectFactory = new org.docx4j.wml.ObjectFactory();
    @Autowired
    private PropertyUtil propertyUtil;
    @Autowired
    private PdfaConvertHelper pdfaServiceHelper;
    @Autowired
    private RulePdfServiceHelper pdfRuleServiceHelper;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    @Autowired
    private MudeopenRLocUbicazioneRepository mudeopenRLocUbicazioneRepository;

    @Autowired
    private MudeopenRLocCatastoRepository mudeopenRLocCatastoRepository;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private ProfiloEntityMapper profiloEntityMapper;

    public static Text createText(String value) {
        Text t = objectFactory.createText();
        t.setValue(value);
        return t;
    }

    public static R.Ptab createRPTab(STPTabAlignment alignment, STPTabRelativeTo relativeTo, STPTabLeader leader) {
        R.Ptab tab = new R.Ptab();
        tab.setAlignment(alignment);
        tab.setRelativeTo(relativeTo);
        tab.setLeader(leader);
        return tab;
    }

    private static Ftr createFtr() throws Exception {
        // Prepare page number special fields
    	/*TODO: verificare come utilizzare questa opzione..
        CTSimpleField currentPageNumber = objectFactory.createCTSimpleField();
        currentPageNumber.setInstr(" PAGE \\* Arabic  \\* MERGEFORMAT ");
        CTSimpleField totalPageCount = objectFactory.createCTSimpleField();
        totalPageCount.setInstr("$NUMPAGES  \\* Arabic  \\* MERGEFORMAT");
		*/

        // Create paragraph and add a run for left / right / center.
        P p = objectFactory.createP();
        // Left
        R rLeft = objectFactory.createR();
        rLeft.getContent().add(createText("["));
        p.getContent().add(rLeft);

        // Center (the objectFactory in the strings are non-breaking-space)
        R rCenter = objectFactory.createR();
        rCenter.getContent().add(createRPTab(STPTabAlignment.CENTER, STPTabRelativeTo.MARGIN, STPTabLeader.NONE));
        //rCenter.getContent().add(createText("Codice istanza: "));
        //rCenter.getContent().add(objectFactory.createPFldSimple(currentPageNumber));
        //rCenter.getContent().add(createText(" of "));
        rCenter.getContent().add(createText(" $ETICHETTA "));
        //rCenter.getContent().add(objectFactory.createPFldSimple(totalPageCount));
        p.getContent().add(rCenter);
        // Right
        R rRight = objectFactory.createR();
        rRight.getContent().add(createRPTab(STPTabAlignment.RIGHT, STPTabRelativeTo.MARGIN, STPTabLeader.NONE));
        rRight.getContent().add(createText("]"));
        p.getContent().add(rRight);

        org.docx4j.wml.Drawing drawing = objectFactory.createDrawing();
        p.getContent().add(drawing);

        Ftr ftr = objectFactory.createFtr();

        ftr.getContent().add(p);

        return ftr;
    }

    public static void createFooterReference(WordprocessingMLPackage wordprocessingMLPackage, Relationship relationship) throws InvalidFormatException {
        List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();

        // There is always a section wrapper, but it might not contain a sectPr
        if (sectPr == null) {
            sectPr = objectFactory.createSectPr();
            wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }
        FooterReference footerReference = objectFactory.createFooterReference();
        footerReference.setId(relationship.getId());
        footerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(footerReference);// add header or
        // footer references
    }

    public static Relationship createFooterPart(WordprocessingMLPackage wordprocessingMLPackage) throws Exception {
        FooterPart footerPart = new FooterPart();
        Relationship rel = wordprocessingMLPackage.getMainDocumentPart().addTargetPart(footerPart);

        // After addTargetPart, so image can be added properly
        footerPart.setJaxbElement(createFtr());
        return rel;
    }

    @Override
    public String getTemplateDir() {
        MudeCProprieta proprieta = propertyUtil.getPropertyValue(PropertyUtil.TEMPLATE_DIR_ID);
        if (proprieta != null) {
            String val = proprieta.getValore();
            if (!val.endsWith("/")) {
                val = val + "/";
            }
            if (val.startsWith("/")) {
                val = val.substring(1);
            }
            return val;
        } else {
            LoggerUtil.error(logger, "Directory reperimento template non configurata id proprieta [" + PropertyUtil.TEMPLATE_DIR_ID + "]");
            throw new BusinessException("Errore nella generazione del documento, contattare il servizio di assistenza!");
        }
    }

    @Override
    public byte[] getCompiledTemplate(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IXDocReport report;

        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        JSONObject obj = setIstanzaContext(istanza, null, istanza.getJsonData());
        mappings.put("json", obj);
        //        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        IContext context = report.createContext();
        context.put("d", mappings);
        context.put("json", obj);
        context.put("date", new DateTool());
        context.put("CR", "\n");

        //        report.convert(context, null, out);
        report.process(context, out);

        return out.toByteArray();
    }

    private void veifyRules(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (org.json.simple.JSONObject) parser.parse(json);
        String tabNAme = "TAB_ASS_AP";
        logger.info("veifyRules : tabNAme: " + tabNAme);
        JSONObject qdrObj = (JSONObject) obj.get(tabNAme);

        String sQuadrObj = qdrObj.toJSONString();
        String proprietaToVerify = "elaborato_tecnico_copertura_escluso_tipo";
        int lastInt = sQuadrObj.lastIndexOf(proprietaToVerify);
        if (lastInt < 0)
            logger.info("veifyRules : joProprieta assente");
        else {
            logger.info("veifyRules : joProprieta presente in " + lastInt);
        }

    }

    @Override
    public byte[] getCompiledProcuraPDF(Long idIStanza, InputStream in, boolean isPDF) throws Exception {
    	return getCompiledTemplatePDF(new HashMap<>(), mudeTIstanzaRepository.findOne(idIStanza), in, isPDF, true);
    }

    @Override
    public byte[] getCompiledTemplatePDF(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in) throws Exception {
        return getCompiledTemplatePDF(mappings, istanza, in, true, false);
    }

    @Override
    public byte[] getCompiledTemplatePDF(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in, boolean generatePDF) throws Exception {
        return getCompiledTemplatePDF(mappings, istanza, in, generatePDF, false);
    }

    private byte[] getCompiledTemplatePDF(Map<String, Object> mappings, MudeTIstanza istanza, InputStream in, boolean generatePDF, boolean addFooter) throws Exception {
    	
    	if(addFooter) {
            WordprocessingMLPackage target = WordprocessingMLPackage.load(in);
            in = new ByteArrayInputStream(addFooterToTarget(target));
    	}
    	
    	String json = istanza.getJsonData();

    	if(mappings == null)
    		mappings = new HashMap<>();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IXDocReport report;
        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        JSONObject obj = setIstanzaContext(istanza, null, json);
        mappings.put("json", obj);
        JSONObject qdrAllegati = (JSONObject) obj.get("QDR_ALLEGATI");
        JSONObject allegati = getAllegatiData(qdrAllegati);
        mappings.put("allegati", allegati);

        IContext context = report.createContext();
        context.put("d", mappings);

        DateTool dt = new DateTool();
        //context.put("date", dt);
        context.put("json", obj);
        context.put("esc", new EscapeTool());

        context.put("CR", "\n");

        if(!generatePDF) {
            report.process(context, out);
            return out.toByteArray();
        }

        //-------------------------------------------------------------
        /*
        String LOADED_REPORT_DATE_KEY = "loadedReportDate";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "01-01-1979 00:00:00";
		Date date = sdf.parse(dateInString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
        report.setData( LOADED_REPORT_DATE_KEY, calendar.getTime() );
        */

        //DLG...
        try {
            HashMap copertina = pdfRuleServiceHelper.getMapCopertina(json);
            context.put("copertina", copertina);
        } catch (Exception e) {
        }

        //----------------------------------------------------------------------------
        //Valorizzazione del footer!
        //----------------------------------------------------------------------------
        StringBuffer sb = new StringBuffer();
        //String tipoIstanza = istanza.getTipoIstanza().getDescrizione();
        sb.append(istanza.getTemplate().getCodTemplate());
        sb.append(" ");
        sb.append("V");
        sb.append(istanza.getTemplate().getNumeroVersione());
        sb.append(" ");
        sb.append("codice istanza:");
        sb.append("-");
        sb.append(istanza.getCodiceIstanza());
        String etichetta = sb.toString();
        context.put("ETICHETTA", etichetta);
        //----------------------------------------------------------------------------
        //Valorizzazione del footer - END
        //----------------------------------------------------------------------------
        //----------------------------------------------------------------------------
        Options options = Options.getTo(ConverterTypeTo.PDF);
        String fontEncoding = OptionsHelper.getFontEncoding(options);
        //logger.info("PdfServiceImp::getCompiledTemplatePDF:: fontEncoding (1) " + fontEncoding);
        PdfOptions lpdfOptions = PdfOptions.create().fontEncoding("LiberationSans-Regular");
        options.subOptions(lpdfOptions);
        //logger.info("PdfServiceImp::getCompiledTemplatePDF:: fontEncoding (2) " + fontEncoding);
        //-------------------()---------------------------------------------------------
        //iniettare il Font nel PDF...
        //----------------------------------------------------------------------------
        if (pdfaServiceHelper.isPdfaFontEnbedded() == -1) {
            PdfOptions pdfOptions = pdfaServiceHelper.getPdfOptions(options);
            options.subOptions(pdfOptions);
        }
        //------------------------------------------------------
        report.convert(context, options, out);
        //------------------------------------------------------------------------
        //ottiene il pdf "rielaborato..dal pdf originale...
        //------------------------------------------------------------------------
        /*TOD: verificare l'opportunità di generare il pdf..e di riprocessarlo..molto pesante..
        ByteArrayOutputStream outPDF = new ByteArrayOutputStream();
        report.process(context, outPDF);
        InputStream isPDF = new ByteArrayInputStream(outPDF.toByteArray());
        XWPFDocument document = new XWPFDocument(isPDF);
        PdfConverter.getInstance().convert(document, out, pdfOptions);
        */

        PDDocument document = Loader.loadPDF(out.toByteArray());
        //COSDocument  cdocument=document.getDocument();
        //List<COSObject>  lcObj=cdocument.getObjectsByType(COSName.OBJ_STM);
        //for(COSObject item : lcObj) {

        //}
        /*
        COSArray cosArray = document.getDocument().getDocumentID();
        // Clear or set whatever values...
        cosArray.clear();
        COSBase cbase0=new COSString("85815FBF2C561C45923BCE94CCC88CE8");
        COSBase cbase1=new COSString("867FE1934BA14DE72C5EB54B9EC678F6");
        cosArray.add(cbase0);
        cosArray.add(cbase1);
        document.getDocument().setDocumentID(cosArray);
        */
        COSArray cosArray = new COSArray();
        COSString cbase0 = new COSString("85815FBF2C561C45923BCE94CCC88CE8");
        COSString cbase1 = new COSString("867FE1934BA14DE72C5EB54B9EC678F6");
        cosArray.add(cbase0);
        cosArray.add(cbase1);
        document.getDocument().setDocumentID(cosArray);
        Calendar dateCalendar = new GregorianCalendar(1970, 1, 1, 0, 0);
        document.getDocumentInformation().setCreationDate(dateCalendar);
        document.getDocumentInformation().setModificationDate(dateCalendar);
        PDDocumentInformation info = new PDDocumentInformation();
        info.setTitle("Csi Documento Istanza");
        info.setAuthor("Csi Author");
        info.setSubject("Csi Subject");
        info.setKeywords("pdf");
        info.setCreationDate(dateCalendar);
        info.setModificationDate(dateCalendar);
        document.setDocumentInformation(info);
        out = new ByteArrayOutputStream();
        document.save(out);
        document.close();

        //Path tempFile = Files.createTempFile(null, null);

        // Writes a string to the above temporary file
        //Files.write(tempFile,out.toByteArray());
        //----------------------------------------------------------------------------
        //Genera il PDFA
        //----------------------------------------------------------------------------
        if(pdfaServiceHelper.isPdfaEnabled() == 1) {
            try {
                return pdfaServiceHelper.Convert(out.toByteArray());
            } catch (Exception e) {
                logger.error("[PdfServiceImpl::getCompiledTemplatePDF] ERROR : " + e.toString());
            }
        }
        //----------------------------------------------------------------------------
        //Retrocompatibilità..viene generato il pdf in caso di problemi
        //----------------------------------------------------------------------------
        return out.toByteArray();
    }
    //-----------------------------------------------------------------------------
    //FOOTER END
    //-----------------------------------------------------------------------------

    /*
     * // DISABLED CAUSE MERGE IS NOT USED ANYMORE... 
    @Override
    public byte[] mergeDocuments(List<byte[]> bytes) throws Docx4JException, IOException, JAXBException {
        WordprocessingMLPackage target = null;

        //@TODO: DLG
        MainDocumentPart main_part = null;
        Ftr cover_ftr = null;
        Ftr content_ftr = null;

        for (byte[] fn : bytes) {
            if (fn == null)
                continue;

            InputStream is = new ByteArrayInputStream(fn);
            if (target == null) {
                target = WordprocessingMLPackage.load(is);
            } else {
                WordprocessingMLPackage docToAdd = WordprocessingMLPackage.load(is);
                List body = docToAdd.getMainDocumentPart().getJAXBNodesViaXPath("//w:body", false);
                for (Object b : body) {
                    List filhos = ((org.docx4j.wml.Body) b).getContent();
                    for (Object k : filhos)
                        target.getMainDocumentPart().addObject(k);
                }
                List<Object> blips = docToAdd.getMainDocumentPart().getJAXBNodesViaXPath("//a:blip", false);
                for (Object el : blips) {
                    try {
                        CTBlip blip = (CTBlip) el;
                        RelationshipsPart parts = docToAdd.getMainDocumentPart().getRelationshipsPart();
                        Relationship relToAdd = parts.getRelationshipByID(blip.getEmbed());

                        Part partToAdd = parts.getPart(relToAdd);
                        BinaryPart bp = (BinaryPart) partToAdd;
                        BinaryPartAbstractImage bpa = this.createImagePart(target, partToAdd, bp.getBytes(), "image/png");
                        target.getMainDocumentPart().addTargetPart(bpa);
                        blip.setEmbed("rId" + (target.getMainDocumentPart().getRelationshipsPart().getRelationships().getRelationship().size()));
                    } catch (Exception e) {
                        logger.error("[PdfServiceImpl::mergeDocuments] ERROR : " + e.toString());
                    }
                }
            }
        }

    	return target == null ? null : addFooterToTarget(target);
    }

    private BinaryPartAbstractImage createImagePart(WordprocessingMLPackage target, Part sourcePart, byte[] bytes, String mime) throws Exception {
        String ext = "png";

        ContentTypeManager ctm = target.getPackage().getContentTypeManager();
        // Ensure the relationships part exists
        if (sourcePart.getRelationshipsPart() == null) {
            RelationshipsPart.createRelationshipsPartForPart(sourcePart);
        }

        String proposedRelId = "rId" + (target.getMainDocumentPart().getRelationshipsPart().getRelationships().getRelationship().size() + 1);
        BinaryPartAbstractImage imagePart = (BinaryPartAbstractImage) ctm.newPartForContentType(mime, createImageName(target.getPackage(), sourcePart, proposedRelId, ext), null);
        imagePart.setBinaryData(bytes);
        imagePart.getRels().add(sourcePart.addTargetPart(imagePart, proposedRelId));
        return imagePart;
    }
    */

    private byte[] addFooterToTarget(WordprocessingMLPackage target) throws Docx4JException, IOException, JAXBException {
        File generated = null;

        try {
	        generated = File.createTempFile("generated", ".docx");
	    	
	        Relationship relationship = null;
	        try {
	            relationship = createFooterPart(target);
	            if (relationship != null) {
	                createFooterReference(target, relationship);
	            }
	        } catch (Exception e) {
	            logger.error("[PdfServiceImpl::addFooterToTarget] ERROR : " + e.toString());
	        }
	
	        //-----------------------------------------------------------
	        // Display the result as Flat OPC XML
	        byte[] fontMergedByteStream = null;
	        try {
	            fontMergedByteStream = PdfaConvertHelper.getMergedFontDocument(target);
	            return fontMergedByteStream;
	        } catch (Exception e) {
	            logger.error("[PdfServiceImpl::addFooterToTarget] ERROR : " + e.toString());
	        }
	
	        target.save(generated);
	        return FileUtils.readFileToByteArray(generated);
		} finally {
			if(generated != null)
				generated.delete();
		}
    }

    private JSONObject getAllegatiData(JSONObject qdrAllegati) {
        try {
            JSONObject allegatiIstanza = new JSONObject();
            JSONArray allegatiObbligatoriList = new JSONArray();
            JSONArray allegatiNecessariList = new JSONArray();
            JSONArray allegatiFacoltativiList = new JSONArray();
            Set<String> keys = qdrAllegati.keySet();
            for (String key : keys) {
                if (qdrAllegati.get(key) instanceof JSONArray) {
                    JSONArray allegati = (JSONArray) qdrAllegati.get(key);
                    for (Object all : allegati) {
                        JSONObject allegato = new JSONObject();
                        allegato.put("categoria_allegato", ((JSONObject) all).get("descrizione_categoria_allegato"));
                        allegato.put("tipo_allegato", ((JSONObject) all).get("desc_breve_allegato"));
                        allegato.put("nome_file_allegato", ((JSONObject) all).get("name"));
                        String obbligatorio = String.valueOf(((JSONObject) all).get("obbligatorio"));
                        if ("obbligatorio".equalsIgnoreCase(obbligatorio)) {
                            allegatiObbligatoriList.add(allegato);
                        } else if ("necessario".equalsIgnoreCase(obbligatorio)) {
                            allegatiNecessariList.add(allegato);
                        } else {
                            allegatiFacoltativiList.add(allegato);
                        }
                    }
                }
            }
            allegatiIstanza.put("allegati_obbligatori", allegatiObbligatoriList);
            allegatiIstanza.put("allegati_necessari", allegatiNecessariList);
            allegatiIstanza.put("allegati_facoltativi", allegatiFacoltativiList);
            return allegatiIstanza;
        } catch (Throwable e) {
            logger.error("[PdfServiceImpl::getAllegatiData] ERROR : " + e.toString());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject setIstanzaContext(MudeTIstanza istanza, IstanzaVO istanzaVO, String json2merge) throws Exception {
    	// set ex_ISTANZA and ex_FASCICOLO
        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

    	JSONObject jsonObj = (JSONObject) parser.parse(json2merge);
   	
    	if(istanzaVO == null)
    		istanzaVO = istanzaEntityMapper.mapEntityToVO(istanza, null);
    	
    	JSONObject istanzaObj = (JSONObject) parser.parse(mapper.writeValueAsString(istanzaVO));
    	
    	// convert jsondata
    	if(istanzaVO.getJsonData() != null)
    		istanzaObj.put("json_data", (JSONObject) parser.parse(istanzaVO.getJsonData()));
    	
    	// convert jsondata inside "istanza_riferimento"
    	if(istanzaVO.getIstanzaRiferimento() != null && istanzaVO.getIstanzaRiferimento().getJsonData() != null) {
    		JSONObject jsonObjIstRif = (JSONObject)parser.parse(istanzaVO.getIstanzaRiferimento().getJsonData());
    		((JSONObject)istanzaObj.get("istanza_riferimento"))
    				.put("json_data", jsonObjIstRif);
    	
        	updateJsonDataInformation(istanzaVO.getIstanzaRiferimento().getFonte(),
				        			istanzaVO.getIstanzaRiferimento().getComune().getDescrizione(),
				        			istanzaVO.getIstanzaRiferimento().getIdIstanza(), 
				        			jsonObjIstRif);
    	}
    	
    	// convert jsondata inside "istanze_collegate"
    	if(CollectionUtils.emptyIfNull(istanzaVO.getIstanzeCollegate()).size() > 0)
            for(Object ist : CollectionUtils.emptyIfNull((JSONArray)istanzaObj.get("istanze_collegate")) ) {
            	if(((JSONObject)ist).containsKey("json_data") && ((JSONObject)ist).get("json_data") != null) {
            		JSONObject jsonObjIstRif = (JSONObject) parser.parse((String)((JSONObject)ist).get("json_data"));
            		((JSONObject)ist).put("json_data", jsonObjIstRif);
    		
                	updateJsonDataInformation((String)((JSONObject)ist).get("fonte"),
                			(String)((JSONObject)((JSONObject)ist).get("comune")).get("value"),
                			((Number)((JSONObject)ist).get("id_istanza")).longValue(),
                			jsonObjIstRif);
            	}
            }
    	

    	// adds extra info into JsonObject to be used for "copertina" generation
    	jsonObj.put("ex_ISTANZA", istanzaObj);

    	if(istanza != null) {
        	FascicoloVO fascicoloVO = fascicoloEntityMapper.mapEntityToVO(istanza.getMudeTFascicolo(), null);
        	JSONObject fascicoloObj = (JSONObject) parser.parse(mapper.writeValueAsString(fascicoloVO));
        	jsonObj.put("ex_FASCICOLO", fascicoloObj);
    	}

    	JSONObject definitions = new JSONObject();
    	definitions.put("qualificaList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getQualificaList())));
    	definitions.put("ordineList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getOrdineList())));
    	definitions.put("tipoResidenzaList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getTipoResidenzaList())));
    	definitions.put("dugList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getDugList())));
    	definitions.put("tipoDittaList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getTipoDittaList())));
    	definitions.put("tipoIstanzaList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getTipoIstanzaList())));
    	definitions.put("tipoInterventoList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getTipoInterventoList())));
    	definitions.put("statoIstanzaList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getStatoIstanzaList())));
    	definitions.put("statoFascicoloList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getStatoFascicoloList())));
    	definitions.put("titoloList", parser.parse(mapper.writeValueAsString(profiloEntityMapper.getTitoloList())));
    	jsonObj.put("ex_DEFINIZIONI", definitions);

        List<DizionarioVO> usersQuadri = (List<DizionarioVO>)em.createNativeQuery(
    			"SELECT mtu.cf, des_tipo_quadro, mriqu.data_modifica "
    			+ "  FROM mudeopen_r_istanza_quadro_utente mriqu "
    			+ "    INNER JOIN ( "
    			+ "      SELECT id_quadro, MAX(id_istanza_quadro_utente) id_istanza_quadro_utente FROM mudeopen_r_istanza_quadro_utente "
    			+ "      where id_istanza = " + istanza.getId()
    			+ "      GROUP BY id_quadro "
    			+ "    ) lmriqu ON lmriqu.id_istanza_quadro_utente = mriqu.id_istanza_quadro_utente "
    			+ "    INNER JOIN mudeopen_t_user mtu ON mtu.id_user = mriqu.id_utente "
    			+ "    INNER JOIN mudeopen_d_quadro mdq ON mdq.id_quadro = mriqu.id_quadro "
    			+ "    INNER JOIN mudeopen_d_tipo_quadro mdtq ON mdtq.id_tipo_quadro = mdq.id_tipo_quadro "
    			+ "  ORDER BY mriqu.id_istanza_quadro_utente").getResultList().stream().map(x -> { 
    		return new DizionarioVO((String)((Object[])x)[0], (String)((Object[])x)[1], new SimpleDateFormat("dd-MM-yyyy").format( (Date)((Object[])x)[2] ));
    	}).collect(Collectors.toList());

    	String cfContatti = "'" + String.join("', '", usersQuadri.stream().map(x -> { return x.getCodice(); } ).collect(Collectors.toList())) + "'";
        List<SelectVO> usersAbilit = (List<SelectVO>)em.createNativeQuery(
    			"SELECT mtu.cf AS id, mda.codice_abilitazione, mda.desc_abilitazione AS descrizione "
    			+ "    FROM mudeopen_r_istanza_utente mriu "
    			+ "			INNER JOIN mudeopen_t_user mtu ON mtu.id_user = mriu.id_utente AND mtu.cf IN ("+cfContatti+")"
    			+ "      INNER JOIN mudeopen_d_abilitazione mda ON mda.id_abilitazione = mriu.id_abilitazione "
    			+ "    where mriu.data_fine IS NULL AND NOT(mda.codice_abilitazione = 'CONSULTATORE') AND id_istanza = " + istanza.getId()).getResultList().stream().map(x -> { 
    		return new DizionarioVO((String)((Object[])x)[0], (String)((Object[])x)[1], (String)((Object[])x)[2]);
    	}).collect(Collectors.toList());

    	Map<String, ContattoVO> abilitati = new HashMap<>();
    	for(ContattoVO contattoVO : contattoEntityMapper.mapListEntityToListVO(mudeTContattoRepository.retrieveInstanceWorkingContacts(istanza.getId()), null))
    		abilitati.put(contattoVO.getGuid(), contattoVO);
    	
    	jsonObj.put("ex_USER_ABILITAZIONI", parser.parse(mapper.writeValueAsString(usersAbilit)));
    	jsonObj.put("ex_USER_QUADRI", parser.parse(mapper.writeValueAsString(usersQuadri)));
    	jsonObj.put("ex_ABILITATI", parser.parse(mapper.writeValueAsString(abilitati)));
    	
    	List<String> guidSoggetti = mudeTContattoRepository.getContactGuidsOrderedByConf(istanza.getId(), istanza.getTipoIstanza().getCodice());
    	jsonObj.put("ex_GUID_SOGGETTI", parser.parse(mapper.writeValueAsString(guidSoggetti)));
    	
    	// add owner + PM contact
    	if(istanzaVO.getIdIstanzaRiferimento() != null)
    		try {
		    	String guid_intestatario = istanzaVO.getGuidIntestatario(); // $json.ex_ISTANZA.guid_intestatario
		    	String pm_codice_fiscale = istanzaVO.getPmCodiceFiscale(); // $json.ex_ISTANZA.istanza_riferimento.pm_codice_fiscale
	
	        	MudeTContatto mudeTContattoOwner = mudeTContattoRepository.findIntestatarioUpdatedByIdIstanza(istanzaVO.getIdIstanzaRiferimento());
	        	if(guid_intestatario != null && mudeTContattoOwner != null) {
	    	    	// $json.ex_ISTANZA.istanza_riferimento.json_data.QDR_SOGGETTO_COINV.subjectList.get($json.ex_ISTANZA.guid_intestatario).contact
	    	    	jsonObj.put("ex_ISTRIF_INT", parser.parse(mapper.writeValueAsString(contattoEntityMapper.mapEntityToVO(mudeTContattoOwner, null))));
	
	    	    	if(guid_intestatario.equals(pm_codice_fiscale))
	        	    	jsonObj.put("ex_ISTRIF_PM", jsonObj.get("ex_ISTRIF_INT")); // PM same as OWNER
	    	    	else {
	    	        	MudeTContatto mudeTContattoPM = mudeTContattoRepository.findUpdatedPMByIdIstanza(istanzaVO.getIdIstanzaRiferimento());
	    	        	if(mudeTContattoPM != null)
	    	    	    	// $json.ex_ISTANZA.istanza_riferimento.json_data.QDR_SOGGETTO_COINV.subjectList.get($json.ex_ISTANZA.istanza_riferimento.pm_codice_fiscale).contact
	    	        		jsonObj.put("ex_ISTRIF_PM", parser.parse(mapper.writeValueAsString(contattoEntityMapper.mapEntityToVO(mudeTContattoPM, null))));
	    	    	}
	        	}
			} catch (Throwable skipThis) {
	            logger.error("[PdfServiceImpl::setIstanzaContext] ERROR : " + skipThis.toString());
			}
    	
    	updateJsonDataInformation(istanzaVO.getFonte(),
				    			istanzaVO.getComune().getDescrizione(),
				    			istanzaVO.getIdIstanza(), 
				    			jsonObj);
    	
    	return jsonObj;
    }

    @SuppressWarnings("unchecked")
    private void updateJsonDataInformation(String fonte, String comune, Long idIstanza, JSONObject jsonObj) {
    	if("FO".equals(fonte)) {
			if(jsonObj.containsKey("QDR_SOGGETTO_COINV") && ((JSONObject)jsonObj.get("QDR_SOGGETTO_COINV")).containsKey("subjectList")) {
	    		for(String jsonCintactInfo : mudeTContattoRepository.findUpdatedInstanceSubjectIDs(idIstanza))
	    			try {
	    				String guid = jsonCintactInfo.split("~")[0];
	    				Long idContact = Long.parseLong(jsonCintactInfo.split("~")[1]);
	    				
	    				JSONObject jsonDataContact = (JSONObject)((JSONObject)(((JSONObject)jsonObj.get("QDR_SOGGETTO_COINV")).get("subjectList"))).get(guid);

	    		        String jsonStringContact = JsonUtils.toJsonString(contattoEntityMapper.mapEntityToVO(mudeTContattoRepository.findOne(idContact), null));
				        jsonDataContact.put("contact", new JSONParser().parse(jsonStringContact));
				        jsonDataContact.put("idSoggetto", idContact);
				        
					} catch (Exception skipUpdate) { 
						skipUpdate.printStackTrace();
					}
	    	}
			
            return; // not else to update for FO instances
    	}

    	// for non FO instances, update address from _loc_ubicaione
        JSONArray datagridTAB_LOCAL_1 = new JSONArray();
        try {
            for(MudeopenRLocUbicazione mudeRLocUbicazione : mudeopenRLocUbicazioneRepository.retrieveAllByIdIstanza(idIstanza)) {
            	JSONObject e = new JSONObject();
        		e.put("sedime", mudeRLocUbicazione.getSedime());
        		e.put("denominazione", mudeRLocUbicazione.getDescVia());
        		e.put("n", mudeRLocUbicazione.getNumCivico());
        		e.put("bis", mudeRLocUbicazione.getBis());
        		e.put("scala", mudeRLocUbicazione.getScala());
        		e.put("piano", mudeRLocUbicazione.getPiano());
        		e.put("selezionare_se_si_tratta_di_indirizzo_principale", "principale".equals(mudeRLocUbicazione.getF1PersonalizzatoDettaglio()));
        		e.put("int", mudeRLocUbicazione.getInterno());
        		e.put("bisInterno", mudeRLocUbicazione.getBisinterno());
        		e.put("interno2", mudeRLocUbicazione.getInterno2());
        		e.put("secondario", mudeRLocUbicazione.getSecondario());
        		e.put("cap", mudeRLocUbicazione.getCap());
        		e.put("geolocalizzazione", "S".equalsIgnoreCase(mudeRLocUbicazione.getF1Personalizzato()) ? "manuale" : "geolocalizzazione") ;
				datagridTAB_LOCAL_1.add(e);
            }
        	JSONObject value = new JSONObject(); 
        	value.put("dataGrid", datagridTAB_LOCAL_1);
        	value.put("relativo_all_immobile", comune);
			jsonObj.put("TAB_LOCAL_1", value);

            JSONArray datagridTAB_LOCAL_2 = new JSONArray();
            for(MudeopenRLocCatasto mudeRLocUbicazione : mudeopenRLocCatastoRepository.getByIdIstanza(idIstanza)) {
            	JSONObject e = new JSONObject();
        		e.put("censito_al_catasto", "T".equalsIgnoreCase(mudeRLocUbicazione.getF1TipoCatasto()) ? "catasto_terreni" : "catasto_fabbricati");
        		e.put("foglioN", mudeRLocUbicazione.getFoglio());
        		e.put("map", mudeRLocUbicazione.getParticella());
        		e.put("sub", mudeRLocUbicazione.getSubalterno());
        		e.put("text_fieldsezione", mudeRLocUbicazione.getSezione());
        		e.put("sezione_urbana", mudeRLocUbicazione.getSezioneUrbana());
				datagridTAB_LOCAL_2.add(e);
            }

        	JSONObject value2 = new JSONObject();
        	value2.put("dataGrid", datagridTAB_LOCAL_2);
			jsonObj.put("TAB_LOCAL_2", value2);
		} catch (Throwable skipNativeAddress) {
            LoggerUtil.error(logger, "ERROR [PdfServiceImpl::fillInNativeAddress] impossibile recuperare indirizzo istanza nativa", skipNativeAddress);
		}
    }

}