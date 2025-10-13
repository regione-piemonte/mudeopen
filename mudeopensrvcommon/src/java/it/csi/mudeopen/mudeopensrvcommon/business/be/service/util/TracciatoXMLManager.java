/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplateTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.EnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocCatastoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocUbicazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.LogTracciatoService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.JsonUtils;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.EsitoGenerazioneTracciatoEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoErroreTracciatoEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TracciatoXMLManager {
    private static final String QDR_ALLEGATI = "QDR_ALLEGATI";

	private static Logger logger = Logger.getLogger(TracciatoXMLManager.class.getCanonicalName());

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDTemplateTracciatoRepository mudeDTemplateTracciatoRepository;

    @Autowired
    private MudeRTipoIstanzaTipoTracciatoRepository mudeRTipoIstanzaTipoTracciatoRepository;

    @Autowired
    private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;

    @Autowired
    private MudeRIstanzaEnteRepository mudeRIstanzaEnteRepository;

    @Autowired
    private MudeopenRLocUbicazioneRepository mudeopenRLocUbicazioneRepository;

    @Autowired
    private MudeopenRLocCatastoRepository mudeopenRLocCatastoRepository;

    @Autowired
    private LogTracciatoService logTracciatoService;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private EnteEntityMapper enteEntityMapper;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    //    @Autowired
    //    private JavaMailSender mailSender;

    public EsitoGenerazioneTracciatoEnum generaTracciatiPerIstanza(Long idIstanza, MudeTUser user) {
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        if (null == istanza) {
            //            Integer emailRetryAttempt = Integer.valueOf(mudeCProprietaRepository.getValueByName("MUDE_EMAIL_NUM_MAX_RETRY", "0"));
            String tipoErrore = TipoErroreTracciatoEnum.SYS.getValue();
            String errore = new StringBuilder("Errore nella generazione dei tracciati : istanza con id [").append(idIstanza).append("] non trovata ").toString();

            //            this.sendMail(idIstanza, null, tipoErrore, errore, emailRetryAttempt, null);
            istanza = new MudeTIstanza();
            istanza.setId(idIstanza);
            logTracciatoService.saveLogTracciato(user, istanza, null, tipoErrore, errore, null, null, null);
            return EsitoGenerazioneTracciatoEnum.KO;
        }

        return generaTracciatiPerIstanza(istanza, user);
    }

    private EsitoGenerazioneTracciatoEnum generaTracciatiPerIstanza(MudeTIstanza istanza, MudeTUser user) {
        if(gemerateTracciato(istanza, user, false) == null)
            return EsitoGenerazioneTracciatoEnum.KO;
        
        return EsitoGenerazioneTracciatoEnum.OK;
    }
    
    public String gemerateTracciato(Long idIstanza, MudeTUser user) {
        return gemerateTracciato(mudeTIstanzaRepository.findOne(idIstanza), user, true);
    }
    
    public String gemerateTracciato(MudeTIstanza istanza, MudeTUser user, boolean _debug) {
    	String lastTracciatoXML = null;
    	
        //
        Long idIstanza = istanza.getId();
        String codiceTipoIstanza = istanza.getTipoIstanza().getCodice();

        Integer emailRetryAttempt = Integer.valueOf(mudeCProprietaRepository.getValueByName("MUDE_EMAIL_NUM_MAX_RETRY", "0"));

        //prendo il jsondata e lo trasformo in xml
        String jsondataAsXML = null;
        try {
            if (StringUtils.isBlank(istanza.getJsonData()))
                throw new JSONException("impossibile leggere il jsondata dell'istanza avente id [" + idIstanza + "]");

            JSONObject json = new JSONObject(istanza.getJsonData());
            this.setIstanzaContext(istanza, json);

            if (json.has("TAB_LOCAL_1") || json.has("TAB_LOCAL_2")) {
                List<MudeopenRLocUbicazione> ubicazioni = mudeopenRLocUbicazioneRepository.getByIdIstanzaViaGeoriferimento(idIstanza);
                if(ubicazioni.isEmpty()){
                    ubicazioni = mudeopenRLocUbicazioneRepository.getByIdIstanza(idIstanza);
                }
                this.setUbicazioneContext(ubicazioni, json);

                List<MudeopenRLocCatasto> catasti = mudeopenRLocCatastoRepository.getByIdIstanza(idIstanza);
                this.setCatastoContext(catasti, json);
            }

            //Se siamo nel caso di Notifica Preliminare (tracciato Spresal)
            if (istanza.getTipoIstanza().getCodice().equalsIgnoreCase("NOT-PREL")) {
                //prendo la lista degli enti coinvolti
                List<MudeRIstanzaEnte> istanzaEnteList = mudeRIstanzaEnteRepository.findAllByIstanza_IdAndDataFineIsNull(istanza.getId());
                if (!istanzaEnteList.isEmpty()) {
                    List<EnteVO> enti = Lists.newArrayListWithCapacity(istanzaEnteList.size());
                    for (MudeRIstanzaEnte mudeRIstanzaEnte : istanzaEnteList) {
                        MudeTEnte ente = mudeRIstanzaEnte.getEnte();
                        EnteVO enteVO = enteEntityMapper.mapEntityToVO(ente);
                        enti.add(enteVO);
                    }
                    this.setEntiContext(enti, json);
                } else {
                    this.setEntiContext(new ArrayList<>(), json);
                }
                //prendo la lista di eventuali altre notificahe preliminari, in stato APA, presenti nel fascicolo
                List<String> altreNotificheInFascicolo = mudeTIstanzaRepository.getCodiciIstanzeInFascicoloByTipoIstanzaAndStato(istanza.getMudeTFascicolo().getId(), "NOT-PREL", "APA", idIstanza);
                this.setListaNotificheFascicoloContext(altreNotificheInFascicolo, json);
            }

            fixNumericXMLTags(json, idIstanza);
    		if(((JSONObject)json.get("ex_ISTANZA")).has("istanza_riferimento") 
    				&& !"null".equalsIgnoreCase(""+((JSONObject)json.get("ex_ISTANZA")).get("istanza_riferimento")))
                fixNumericXMLTags((JSONObject)((JSONObject)((JSONObject)json.get("ex_ISTANZA")).get("istanza_riferimento")).get("json_data"), idIstanza);

            jsondataAsXML = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?><MUDEOPEN>").append(XML.toString(json)).append("</MUDEOPEN>").toString();
        } catch (Exception e) {
            String tipoErrore = TipoErroreTracciatoEnum.SYS.getValue();
            String errore = new StringBuilder("Errore nella generazione dei tracciati per l'istanza [").append(istanza.getCodiceIstanza()).append("] : ").append(e.getMessage()).toString();

            //sendMail(idIstanza, null, tipoErrore, errore, emailRetryAttempt, null);
            logTracciatoService.saveLogTracciato(user, istanza, null, tipoErrore, errore, null, null, null);
            //setto a true il campo che indica la necessità di rigenerare il tracciato
            istanza.setRigeneraTracciato(Boolean.TRUE);
            mudeTIstanzaRepository.saveDAO(istanza);
            
            return null;
        }

        //recupero, per la data tipologia di istanza, la lista delle tipologie di tracciato da generare valide al momento dell'invio dell'istanza
        List<MudeRTipoIstanzaTipoTracciato> tipoIstanzaTipoTracciatoList = mudeRTipoIstanzaTipoTracciatoRepository.findByTipoIstanza(idIstanza, codiceTipoIstanza);
        int count = 0;
        for (MudeRTipoIstanzaTipoTracciato tipoIstanzaTipoTracciato : tipoIstanzaTipoTracciatoList) {
            MudeDTipoTracciato tipoTracciato = tipoIstanzaTipoTracciato.getMudeDTipoTracciato();

            //per il tipo tracciato considerato, se esiste un record nella tabella mudeopen_r_istanza_tracciato con data fine null, setto la data fine prima di rigenerare il tracciato
            MudeRIstanzaTracciato mudeRIstanzaTracciato = mudeRIstanzaTracciatoRepository.findByIstanzaAndTipoTracciato(idIstanza, tipoTracciato.getId());
            if (null != mudeRIstanzaTracciato) {
                mudeRIstanzaTracciato.setDataFine(Calendar.getInstance().getTime());
                mudeRIstanzaTracciatoRepository.saveDAO(mudeRIstanzaTracciato);
            }

            String xsltTemplatePrincipale = null;
            if(_debug) {
            	try {
                	xsltTemplatePrincipale = new String(Files.readAllBytes(Paths.get("/_TEMP/overrideThisTemplate.xsl")));            	
				} catch (Exception skip) {}
            }
            
            //prendo il template principale
            if(xsltTemplatePrincipale == null)
            	xsltTemplatePrincipale = componiXSLTTemplatePrincipale(tipoIstanzaTipoTracciato);

            //genero il tracciato
            lastTracciatoXML = this.generaTracciatoXML(istanza, tipoTracciato, jsondataAsXML, xsltTemplatePrincipale, user, emailRetryAttempt);
            if (StringUtils.isNotBlank(lastTracciatoXML)) {
                try {
                    //valido l'xml generato con l'xsd corrispondente
                    String xsdSchema = tipoTracciato.getXsdValidazione();

                    XmlValidator xmlValidator = new XmlValidator(xsdSchema, lastTracciatoXML);
                    //                    XmlValidator xmlValidator = new XmlValidator(new String[]{}, tracciatoXML);
                    Boolean isValidXml = xmlValidator.isValid();

                    if (isValidXml) {
                        //                    if (true) {
                        //se il tracciato generato è valido scrivo il record nella tabella mudeopen_r_istanza_tracciato
                        MudeRIstanzaTracciato istanzaTracciato = new MudeRIstanzaTracciato();
                        istanzaTracciato.setMudeTIstanza(istanza);
                        istanzaTracciato.setMudeDTipoTracciato(tipoTracciato);
                        istanzaTracciato.setXmlTracciato(lastTracciatoXML);
                        istanzaTracciato.setDataInizio(Calendar.getInstance().getTime());
                        istanzaTracciato.setMudeTUser(user);
                        mudeRIstanzaTracciatoRepository.saveDAO(istanzaTracciato);
                        count++;
                    } else {
                        //se il tracciato generato non è valido scrivo il record con gli errori nella tabella mudeopen_t_log_tracciato
                        String tipoErrore = TipoErroreTracciatoEnum.VALID.getValue();
                        List<SAXParseException> validationExceptions = xmlValidator.listParsingExceptions();
                        String validationErrors = validationExceptions.stream().map(e -> e.getMessage()).collect(Collectors.joining("; "));
                        String errore = new StringBuilder("Errore nella generazione del tracciato per l'istanza [").append(istanza.getCodiceIstanza()).append("] : XML non valido  [").append(validationErrors).append("]").toString();

                        //                        Map<String, byte[]> attachments = Maps.newHashMapWithExpectedSize(3);
                        //                        attachments.put("jsondata_as_xml.xml", jsondataAsXML.getBytes());
                        //                        attachments.put("xslt_template_principale.xml", xsltTemplatePrincipale.getBytes());
                        //                        attachments.put("xml_generato_tracciato.xml", tracciatoXML.getBytes());
                        //                        this.sendMail(idIstanza, tipoTracciato.getCodice(), tipoErrore, errore, emailRetryAttempt, attachments);
                        logTracciatoService.saveLogTracciato(user, istanza, tipoTracciato, tipoErrore, errore, jsondataAsXML, xsltTemplatePrincipale, lastTracciatoXML);
                    }
                } catch (IOException | SAXException e) {
                    String tipoErrore = TipoErroreTracciatoEnum.SYS.getValue();
                    String errore = new StringBuilder("Errore nella generazione del tracciato per l'istanza [").append(istanza.getCodiceIstanza()).append("] : ").append(e.getMessage()).toString();

                    //this.sendMail(idIstanza, tipoTracciato.getCodice(), tipoErrore, errore, emailRetryAttempt, null);
                    logTracciatoService.saveLogTracciato(user, istanza, tipoTracciato, tipoErrore, errore, jsondataAsXML, xsltTemplatePrincipale, lastTracciatoXML);
                }
            }
        }
        
        
        if(!_debug)
	        if (count < tipoIstanzaTipoTracciatoList.size()) {
	            //setto a true il campo che indica la necessità di rigenerare il tracciato
	            istanza.setRigeneraTracciato(Boolean.TRUE);
	            mudeTIstanzaRepository.saveDAO(istanza);
	            
	            return null;
	        } else if (istanza.getRigeneraTracciato()) {
	            istanza.setRigeneraTracciato(Boolean.FALSE);
	            mudeTIstanzaRepository.saveDAO(istanza);
	        }
        
        return lastTracciatoXML;
    }

    private String componiXSLTTemplatePrincipale(MudeRTipoIstanzaTipoTracciato tipoIstanzaTipoTracciato) {
        String xsltTemplatePrincipale = tipoIstanzaTipoTracciato.getXsltTemplatePrincipale();

        /*estraggo i placeholder relativi ai sottoblocchi che devo iniettare nel xslt principale,
         recupero il template del sottoblocco dalla tabella e elimino i tag che non servono
         sostituisco il placeholder nel xslt principale con il blocco xslt relativo al sottoblocco considerato
        */
        List<String> placeholderBlocchiXslt = extractPlaceholderFromXsltTemplate(xsltTemplatePrincipale);
        for (String placeholder : placeholderBlocchiXslt) {
            MudeDTemplateTracciato mudeDTemplateTracciato = mudeDTemplateTracciatoRepository.findByCodice(placeholder);
            String subTemplateXslt = mudeDTemplateTracciato.getXsltTemplate();
            subTemplateXslt = subTemplateXslt.replaceAll("<[?]xml[^>]*>", "");
            subTemplateXslt = subTemplateXslt.replaceAll("<[^>]*xsl:stylesheet[^>]*>", "");
            subTemplateXslt = subTemplateXslt.replaceAll("<[^>]*xsl:output[^>]*>", "");
            xsltTemplatePrincipale = xsltTemplatePrincipale.replaceAll("<mude:include[ ]*href=\"" + placeholder + "\"[ ]*/>", subTemplateXslt.replace('$', '§'));
            xsltTemplatePrincipale = xsltTemplatePrincipale.replace('§', '$');
        }
        return xsltTemplatePrincipale;
    }

    private String generaTracciatoXML(MudeTIstanza istanza, MudeDTipoTracciato tipoTracciato, String jsonDataAsXml, String xsltTemplatePrincipale, MudeTUser user, Integer emailRetryAttempt) {

        String strResult = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            ByteArrayInputStream is = new ByteArrayInputStream(jsonDataAsXml.getBytes(StandardCharsets.UTF_8));
            Document doc = db.parse(is);
            //fixme mettere valore corretto
            doc.getDocumentElement().setAttribute("xsdlocation", "http://mylocation");
            doc.getDocumentElement().setAttribute("versione", tipoTracciato.getVersione());
            String autore = mudeCProprietaRepository.getValueByName("MUDE_DESCRIZIONE_GESTORE", "MUDEOPEN");
            doc.getDocumentElement().setAttribute("autore", autore);
            doc.getDocumentElement().setAttribute("dataOra", new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").format(new Date()));

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setErrorListener(new ErrorListener() {
                @Override
                public void warning(TransformerException ex) throws TransformerException {
                    //                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                }

                @Override
                public void error(TransformerException ex) throws TransformerException {
                    //                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                }

                @Override
                public void fatalError(TransformerException ex) throws TransformerException {
                    //                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                }
            });

            StreamSource ss = new StreamSource(new StringReader(xsltTemplatePrincipale));
            // add XSLT in Transformer
            Transformer transformer = transformerFactory.newTransformer(ss);
            transformer.transform(new DOMSource(doc), result);

            strResult = writer.toString();
        } catch (Throwable e) {
            String tipoErrore = TipoErroreTracciatoEnum.SYS.getValue();
            String errore = new StringBuilder("Errore nella generazione del tracciato per l'istanza [").append(istanza.getCodiceIstanza()).append("] : ").append(e.getMessage()).toString();

            //this.sendMail(istanza.getId(), tipoTracciato.getCodice(), tipoErrore, errore, emailRetryAttempt, null);
            logTracciatoService.saveLogTracciato(user, istanza, tipoTracciato, tipoErrore, errore, jsonDataAsXml, xsltTemplatePrincipale, strResult);
        }

        return strResult;
    }

    private List<String> extractPlaceholderFromXsltTemplate(String xsltTemplatePrincipale) {
        Pattern pattern = Pattern.compile("<mude:include[ ]*href=\"([^\"]*)\"[ ]*/>");
        Matcher matcher = pattern.matcher(xsltTemplatePrincipale);

        BiFunction<Matcher, Function<Matcher, Object>, Collection<?>> placeHolderExtractor = (mch, extracter) -> {
            List<Object> list = new ArrayList<>();
            while (mch.find()) {
                list.add(extracter.apply(mch));
            }
            return list;
        };

        List<String> placeHolderList = placeHolderExtractor.apply(matcher, macher -> macher.group(1)).stream().map(String::valueOf).collect(Collectors.toList());
        return placeHolderList;
    }

    private void setIstanzaContext(MudeTIstanza istanza, JSONObject json2merge) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        IstanzaVO istanzaVO = istanzaEntityMapper.mapEntityToVO(istanza, null);
        istanzaVO.setJsonData(null);

        JSONObject istanzaObj = new JSONObject(mapper.writeValueAsString(istanzaVO));
    	// convert jsondata inside "istanza_riferimento"
    	if(istanzaVO.getIstanzaRiferimento() != null && istanzaVO.getIstanzaRiferimento().getJsonData() != null)
    		((JSONObject)istanzaObj.get("istanza_riferimento"))
    				.put("json_data", (JSONObject) new JSONObject(istanzaVO.getIstanzaRiferimento().getJsonData()));

        json2merge.put("ex_ISTANZA", istanzaObj);
    }

    private void setEntiContext(List<EnteVO> enti, JSONObject json2merge) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray entiObj = new JSONArray(mapper.writeValueAsString(enti));
        json2merge.put("ex_Enti", entiObj);
    }

    private void setListaNotificheFascicoloContext(List<String> altreNotifiche, JSONObject json2merge) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray entiObj = new JSONArray(mapper.writeValueAsString(altreNotifiche));
        json2merge.put("ex_ALTRENOTIFICHE", entiObj);
    }

    private void setUbicazioneContext(List<MudeopenRLocUbicazione> ubicazioni, JSONObject json2merge) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray ubicazioniObj = new JSONArray(mapper.writeValueAsString(ubicazioni));
        JSONObject j = new JSONObject();
        j.put("ubicazione", ubicazioniObj);
        json2merge.put("ex_UBICAZIONE", j);
    }

    private void setCatastoContext(List<MudeopenRLocCatasto> catasti, JSONObject json2merge) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray catastiObj = new JSONArray(mapper.writeValueAsString(catasti));
        JSONObject j = new JSONObject();
        j.put("catasto", catastiObj);
        json2merge.put("ex_CATASTO", j);
    }

    //    private void sendMail(Long idIstanza, String tipoTracciato, String tipoErrore, String errore, Integer emailRetryAttempt, Map<String, byte[]> attachments) {
    //        try {
    //            String mittente = mudeCProprietaRepository.getValueByName("MUDE_MAIL_ECCEZIONIXML_MITTENTE", "");
    //            String destinatario = mudeCProprietaRepository.getValueByName("MUDE_MAIL_ECCEZIONIXML_INDIRIZZO", "");
    //            String oggetto = mudeCProprietaRepository.getValueByName("MUDE_MAIL_VALIDAZIONE_OGGETTO", "Errore nella generazione del tracciato") + " [" + idIstanza + "]";
    //            StringBuilder messaggio = new StringBuilder("Si è verificato un errore per il tracciato [").append(tipoTracciato).append("] \n\r").append(errore);
    //            MimeMessage message = mailSender.createMimeMessage();
    //            MimeMessageHelper helper = new MimeMessageHelper(message, true);
    //            helper.setTo(destinatario);
    //            helper.setSubject(oggetto);
    //            helper.setText(messaggio.toString());
    //            helper.setFrom(mittente);
    //            if (null != attachments) {
    //                for (String s : attachments.keySet()) {
    //                    helper.addAttachment(s, new ByteArrayResource(attachments.get(s)));
    //                }
    //            }
    //            mailSender.send(message);
    //        } catch (MailException | MessagingException mex) {

    //        }
    //    }

    private static void replaceJsonKeys(final JSONObject jsonObject, final Map<String, String> oldJsonKeyNewJsonKeyMap) {
        if (null == jsonObject || null == oldJsonKeyNewJsonKeyMap) {
            return;
        }

        // sort the old json keys descending because we want to replace the name of the inner most key first, then
        // the outer one
        final List<String> oldJsonKeys = oldJsonKeyNewJsonKeyMap.keySet().stream().sorted((k2, k1) -> k1.compareTo(k2)).collect(Collectors.toList());

        oldJsonKeys.forEach(k -> {
            // split old key, remember old key is something like than root.country
            final String[] oldJsonKeyArr = k.split("\\.");

            final int N = oldJsonKeyArr.length;

            // get the object hold that old key
            JSONObject tempJsonObject = jsonObject;
            try {
                for (int i = 0; i < N - 1; i++) {
                    tempJsonObject = tempJsonObject.getJSONObject(oldJsonKeyArr[i]);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            final String newJsonKey = oldJsonKeyNewJsonKeyMap.get(k);

            // if value of the map for a give old json key is null, we just remove that key from json object
            try {
                if (!"null".equalsIgnoreCase(newJsonKey)) {
                    tempJsonObject.put(newJsonKey, tempJsonObject.get(oldJsonKeyArr[N - 1]));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // remove the old json key
            tempJsonObject.remove(oldJsonKeyArr[N - 1]);
        });
    }

    private void fixNumericXMLTags(final JSONObject json, Long idIstanza) throws JSONException {
    	if(json == null) return;
    	
        //se tra i soggetti coinvolti ci sono PG, antepongo PG alla P.IVA che va a identificare il nome del tag nella conversione in XML
        if (json.has("QDR_SOGGETTO_COINV")) {
            JSONObject soggCoinv = (JSONObject) json.get("QDR_SOGGETTO_COINV");
            JSONObject subjectList = (JSONObject) soggCoinv.get("subjectList");

            Map<String, Long> contacts2update = mudeTContattoRepository.findUpdatedInstanceSubjectIDs(idIstanza).stream()
            		.map(x -> new AbstractMap.SimpleEntry<String, Long>(x.split("~")[0], Long.parseLong(x.split("~")[1])))
            			.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

            Map<String, String> oldJsonKeyNewJsonKeyMap = new HashMap<>();
            for(Iterator keys = subjectList.keys(); keys.hasNext() ; ) {
                String key = ((String) keys.next());

                if (Character.isDigit(key.charAt(0)))
                    oldJsonKeyNewJsonKeyMap.put(key, "PG" + key);
                else if(!key.equals(key.replaceAll("[^a-zA-Z0-9]", ""))) 
                    oldJsonKeyNewJsonKeyMap.put(key, key.replaceAll("[^a-zA-Z0-9]", ""));

                // does contact require update? 
                if(contacts2update.containsKey(key) && subjectList.has(key)) {
                    String jsonStringContact = JsonUtils.toJsonString(contattoEntityMapper.mapEntityToVO(mudeTContattoRepository.findOne(contacts2update.get(key)), null));
                	
                	((JSONObject)subjectList.get(key)).put("contact", new JSONObject(jsonStringContact));
                	((JSONObject)subjectList.get(key)).put("idSoggetto", contacts2update.get(key));
                }
            }

            if(soggCoinv.has("subjectsRoleDismissed"))
                soggCoinv.remove("subjectsRoleDismissed");

            if(!oldJsonKeyNewJsonKeyMap.isEmpty())
                this.replaceJsonKeys(subjectList, oldJsonKeyNewJsonKeyMap);

        }

        //se tra i soggetti Abilitati ci sono PG nella lista dei delegati, antepongo PG alla P.IVA che va a identificare il nome del tag nella conversione in XML
        if (json.has("QDR_SOGGETTO_ABILIT")) {
            JSONObject soggAbilit = (JSONObject) json.get("QDR_SOGGETTO_ABILIT");
            JSONObject delegatiList = (JSONObject) soggAbilit.get("delegatiList");

            Map<String, String> oldJsonKeyNewJsonKeyMap = new HashMap<>();
            for(Iterator keys = delegatiList.keys(); keys.hasNext() ; ) {
                String key = ((String) keys.next());

                if (Character.isDigit(key.charAt(0)))
                    oldJsonKeyNewJsonKeyMap.put(key, "PG" + key);
                else if(!key.replaceAll("[^a-zA-Z0-9]", "").equals(key))
                	oldJsonKeyNewJsonKeyMap.put(key, key.replaceAll("[^a-zA-Z0-9]", ""));
            }

            if(soggAbilit.has("soggettiEreditati"))
            	soggAbilit.remove("soggettiEreditati");

            if (!oldJsonKeyNewJsonKeyMap.isEmpty())
                this.replaceJsonKeys(delegatiList, oldJsonKeyNewJsonKeyMap);

        }

        if (json.has(QDR_ALLEGATI)) {
            JSONObject allegati = (JSONObject) json.get(QDR_ALLEGATI);

            List<String> keysToRemove = new ArrayList();
            for(Iterator keys = allegati.keys(); keys.hasNext() ; ) {
                String key = ((String) keys.next());
                if (key.startsWith("_") || key.startsWith("QDR_") || key.startsWith("TAB_"))
                    keysToRemove.add(key);
            }

            for (String s : keysToRemove)
                allegati.remove(s);
        }
    	
    }
}
