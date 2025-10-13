/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.print.PrintException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.spi.UnhandledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplateRicevutaIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzeExt;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.QrCodeHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRicevutaIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzeExtRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ReportService;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.report.ReportPdfVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ReportServiceImpl implements ReportService {
	
	private static Logger logger = Logger.getLogger(ReportServiceImpl.class.getCanonicalName());
	
	private static final Long TEMPLATE_DEFAULT = 1L;
	
	@Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;
	
	@Autowired
    private MudeTIstanzeExtRepository mudeTIstanzeExtRepository;
	
	@Autowired
    private MudeTEnteRepository mudeTEnteRepository;
	
	@Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
	
	@Autowired
    private MudeTPraticaRepository mudeTPraticaRepository;
	
	@Autowired
    private MudeTContattoRepository mudeTContattoRepository;
	
	@Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;
	
	@Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;
	
	@Autowired
    private MudeDTipoInterventoRepository mudeDTipoInterventoRepository;
	
	@Autowired
    private MudeDTemplateRicevutaIstanzaRepository mudeDTemplateRicevutaIstanzaRepository;
	
	@Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;
	
	//@Autowired
    //private MudeDTipoOperaRepository mudeDTipoOperaRepository;
	
	
	private String pattern = "dd/MM/yyyy";
	private DateFormat df = new SimpleDateFormat(pattern);
	

	@Override
	public byte[] generaReportPDFToByte(Long idIstanza, MudeTUser mudeTUser, Timestamp dataGenerazione) {
		
		try {

			
			//recupero il template dal DB
			MudeDTemplateRicevutaIstanza mudeDTemplateRicevutaIstanza = mudeDTemplateRicevutaIstanzaRepository.findTemplateForIstanza(idIstanza);
			Map<String, Object> jasperParam;
			
			//recupero tutte le informazioni in base al template
			if (mudeDTemplateRicevutaIstanza==null) {
				mudeDTemplateRicevutaIstanza = mudeDTemplateRicevutaIstanzaRepository.findOne(TEMPLATE_DEFAULT);
				jasperParam = getJasperParamDefault(idIstanza, mudeDTemplateRicevutaIstanza, dataGenerazione);
			} else {
				if (mudeDTemplateRicevutaIstanza.getIdTemplate()==TEMPLATE_DEFAULT) {
					jasperParam = getJasperParamDefault(idIstanza, mudeDTemplateRicevutaIstanza, dataGenerazione);
				}
				else {
					jasperParam = getJasperParam(idIstanza, mudeDTemplateRicevutaIstanza, dataGenerazione);
				}
			}
			
			//scrittura del documento pdf
		    String jrxml = new String(mudeDTemplateRicevutaIstanza.getContentTemplate(), StandardCharsets.UTF_8);
			
			InputStream bais = compileJRXML(jrxml.getBytes());
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bais);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(""));
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParam, dataSource);
				byte[] ricevutaPdf = JasperExportManager.exportReportToPdf(jasperPrint);
				salvaReport(idIstanza, mudeTUser, ricevutaPdf, dataGenerazione);

				return  ricevutaPdf;
			}catch(JRException ex){
				LoggerUtil.error(logger, "[ReportServiceImpl::generaReportPDFToByte for: " + idIstanza + "] ERROR JRException:" + ex);
				salvaReport(idIstanza, mudeTUser, null, dataGenerazione);
			}catch(UnhandledException ex){
				LoggerUtil.error(logger, "[ReportServiceImpl::generaReportPDFToByte for: " + idIstanza + "] ERROR UnhandledException:" + ex);
				salvaReport(idIstanza, mudeTUser, null, dataGenerazione);
			}
			//SALVATAGGIO IN TABELLA
			

			return  null;
		}
		catch (Exception ex){
			LoggerUtil.error(logger, "[ReportServiceImpl::generaReportPDFToByte for: " + idIstanza + "] ERROR:" + ex);
			salvaReport(idIstanza, mudeTUser, null, dataGenerazione);
		}
		
		return null;
	}

	private void salvaReport(Long idIstanza, MudeTUser mudeTUser, byte[] ricevutaPdf, Timestamp dataGenerazione) {
		LoggerUtil.debug(logger, "[ReportServiceImpl::salvaReport for " + idIstanza + "]" );
		MudeTIstanzeExt mudeTIstanzeExtDB = mudeTIstanzeExtRepository.findOne(idIstanza);
		
		if (mudeTIstanzeExtDB==null) {
			mudeTIstanzeExtDB = new MudeTIstanzeExt();
			mudeTIstanzeExtDB.setDataGenerazione(dataGenerazione);
			mudeTIstanzeExtDB.setIdIstanza(idIstanza);
			mudeTIstanzeExtDB.setUser(mudeTUser);
		}
		
		if (ricevutaPdf!=null){
			mudeTIstanzeExtDB.setRicevutaPdfContent(ricevutaPdf);
		}
		
		mudeTIstanzeExtRepository.saveDAO(mudeTIstanzeExtDB);
		
	}

	private String getTemplateJrxml(String name) throws Exception {
		LoggerUtil.debug(logger, "[ReportServiceImpl::getTemplateJrxml for " + name + "]" );
		String fileJrxml = getValueFromMudeCProprieta(name,"//tmp//templates//"+name);
		File tempFile = new File(fileJrxml);
		if(!tempFile.exists()) throw new Exception("Template not found!");
		LoggerUtil.debug(logger, "[ReportServiceImpl:: file jrxml found"+ "]" );
		InputStream file = new FileInputStream(fileJrxml);
		//if (file == null) throw new Exception("Template not found!");
		byte[] tessBytes = IOUtils.toByteArray(file);
		LoggerUtil.debug(logger, "[ReportServiceImpl::getTemplateJrxml END " );
		return new String(tessBytes, StandardCharsets.UTF_8);
	}
	
	
	private static ByteArrayInputStream compileJRXML(byte[] template) throws PrintException {
		ByteArrayInputStream result = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(template);
			JasperCompileManager.compileReportToStream(bais, os);
			result = new ByteArrayInputStream(os.toByteArray());
		} catch (JRException e) {
			throw new PrintException("Errore nella creazione del file jasper dal template jrxml", e);
		}
		return result;
	}
	
	private Map<String, Object> getJasperParamDefault(
				Long idIstanza, MudeDTemplateRicevutaIstanza mudeDTemplateRicevutaIstanza, Timestamp dataGenerazione
		) throws WriterException, IOException,JSONException
	{
		MudeTIstanza mudeTistanza = mudeTIstanzaRepository.findOne(idIstanza);
		Map<String, Object> jasperParam = new HashMap<>();
		String codiceIstanza = mudeTistanza.getCodiceIstanza();

		jasperParam = recuperaDatiComuni(mudeTistanza, mudeDTemplateRicevutaIstanza, dataGenerazione);
		
	    String testoTb =  mudeDTemplateRicevutaIstanza.getTestoBody1();
	    
	    String dataProtocollo=" nd ";
    	String dataDepositata=" nd ";
	    
    	MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(idIstanza);
	    if (mudeTPratica!=null) {
	    	jasperParam.put("numeroPratica", mudeTPratica.getNumeroPratica()!=null ? mudeTPratica.getNumeroPratica() : " ");
	    	jasperParam.put("numeroProtocollo", mudeTistanza.getNumeroProtocollo()!=null ? mudeTistanza.getNumeroProtocollo() : "" );
	    	dataProtocollo = df.format(mudeTistanza.getDataProtocollo());
	    } else {
	    	jasperParam.put("numeroPratica", " ");
	    	jasperParam.put("numeroProtocollo", " ");
	    }

	    List<MudeRIstanzaStato> mudeRIstanzaStatoList = mudeRIstanzaStatoRepository.findByStatoAndIdIstanza(idIstanza, "DPS");
	    if (mudeRIstanzaStatoList.size()>0) {
	    	dataDepositata = df.format(mudeRIstanzaStatoList.get(0).getDataInizio());
	    }	    
	    
	    String testoFinale = testoTb.replace("{dataProtocollo}", dataProtocollo);
	    testoFinale = testoFinale.replace("{dataDeposito}", dataDepositata);
	    jasperParam.put("testo", testoFinale);
	   
	    /*MudeTContatto contattoRespProcedimento = mudeTContattoRepository.findRespProcedimentoByIdIstanza(idIstanza);
	    jasperParam.put("respProcedimento", " " );
	    if (contattoRespProcedimento!=null) {
	    	jasperParam.put("respProcedimento", recuperaIntestazione(contattoRespProcedimento, contattoRespProcedimento.getTipoContatto().getValue()));   	
	    }*/
	    jasperParam.put("respProcedimento", " " );
	    String contattoRespProcedimento = mudeTistanza.getResponsabileProcedimento();
	    if (contattoRespProcedimento!=null) {
	    	jasperParam.put("respProcedimento", contattoRespProcedimento);   	
	    }
	    
	    String intestatarioCfPIva="";
	    String contattoIntestatarioCF="";
	    MudeTContatto contattoIntestatario = mudeTContattoRepository.findIntestatarioByIdIstanza(idIstanza);
	    jasperParam.put("intestatarioIstanza", " ");
	    if (contattoIntestatario!=null) {
	    	jasperParam.put("intestatarioIstanza",recuperaIntestazione(contattoIntestatario, contattoIntestatario.getTipoContatto().getValue()));
	    	if (contattoIntestatario.getCf()!=null) {
	    		intestatarioCfPIva=contattoIntestatario.getCf();
	    		contattoIntestatarioCF=contattoIntestatario.getCf();
	    	}
	    	if (contattoIntestatario.getPiva()!=null && !contattoIntestatario.getPiva().isBlank()) {
		    	intestatarioCfPIva=contattoIntestatario.getPiva();
		    }
	    }
	    
	    MudeTContatto contattoProfessionista = mudeTContattoRepository.findProfessionistaByIdIstanza(idIstanza);
	    String contattoProfessionistaCF= "";
	    jasperParam.put("professionista", " ");
	    if (contattoProfessionista!=null) {
	    	jasperParam.put("professionista", recuperaIntestazione(contattoProfessionista, contattoProfessionista.getTipoContatto().getValue()));
	    	contattoProfessionistaCF=contattoProfessionista.getCf();
	    }
	    
	    MudeDTipoIntervento mudeDTipoIntervento = mudeDTipoInterventoRepository.findByIdIstanza(idIstanza);
	    jasperParam.put("tipoIntervento"," ");
	    if (mudeDTipoIntervento!=null) {
	    	if (!mudeDTipoIntervento.getDescrizione().isEmpty()) {
	    		jasperParam.put("tipoIntervento",mudeDTipoIntervento.getDescrizione());	   
	    	} 
	   	}else {
	   		JSONObject jsonDataIstanza = new JSONObject(mudeTistanza.getJsonData());
		    if (jsonDataIstanza.has("TAB_QUALIF_2")) {
		    	JSONObject jsonTabQualif2 = (JSONObject) jsonDataIstanza.get("TAB_QUALIF_2");
	            if (jsonTabQualif2.has("tipologia_intervento")) {
	            	JSONObject tipologiaIntervento = (JSONObject) jsonTabQualif2.get("tipologia_intervento");
	                if (tipologiaIntervento.has("value")) {
	                   jasperParam.put("tipoIntervento", tipologiaIntervento.get("value"));
	                }   
	            }
		    }
	   	}
	   		
	    	    
	    // chiavi di generazione del QRCode
	    // Chiave --> codice generato dal sistema per controllo autenticita' (max 11 caratteri).
	    // Nr. Istanza  --> Numero MUDE dell'istanza (22 caratteri)
	    // Intestatario --> Codice Fiscale / Partita Iva del soggetto che ricopre i ruolo Intestatario nell'istanza
	    // Tecnico --> Codice Fiscale del soggetto che ha firmato l'istanza (PM) se diverso dall'intestatario

	    String barcodeText = RandomStringUtils.randomAlphanumeric(11)+" "+codiceIstanza+" "+intestatarioCfPIva;
	    if (!contattoIntestatarioCF.equals(contattoProfessionistaCF)) {
	    	barcodeText = barcodeText+ " "+contattoProfessionistaCF;
	    }
	    
	    BufferedImage bufferedImage = QrCodeHelper.generateQRCodeImage(barcodeText);
	    jasperParam.put("brCode", bufferedImage);
	    
	    return jasperParam;
	}
	
	private String recuperaIntestazione(MudeTContatto contatto, String tipoContatto) {
    	
		if (tipoContatto.equals("pf")) {
			String cognome = contatto.getCognome()!=null && !contatto.getCognome().isBlank() ? contatto.getCognome() :  " ";
	    	String nome = contatto.getNome()!=null && !contatto.getNome().isBlank() ? contatto.getNome() :  " ";
			return 	cognome + " " + nome;
		}
		String ragioneSociale = contatto.getRagioneSociale()!=null && !contatto.getRagioneSociale().isBlank() ? contatto.getRagioneSociale() : " ";  	
		return 	ragioneSociale;
	}

	private Map<String, Object> getJasperParam(Long idIstanza, MudeDTemplateRicevutaIstanza mudeDTemplateRicevutaIstanza, Timestamp dataGenerazione) throws WriterException, IOException, JSONException   {
		
		MudeTIstanza mudeTistanza = mudeTIstanzaRepository.findOne(idIstanza);
		String codiceIstanza = mudeTistanza.getCodiceIstanza();
		
		Map<String, Object> jasperParam = new HashMap<>();
		
		jasperParam = recuperaDatiComuni(mudeTistanza, mudeDTemplateRicevutaIstanza, dataGenerazione);
		jasperParam.put("codiceFascicolo", mudeTistanza.getMudeTFascicolo().getCodiceFascicolo());
	    
	    //recupero il testo dal DB ed inserisco le parti variabili
	    String testoTb =  mudeDTemplateRicevutaIstanza.getTestoBody1();
	    
	    String dataProtocollo=" nd ";
    	String dataDepositata=" nd ";
    	String numeroProtocollo=" nd ";
	    
    	MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(idIstanza);
	    if (mudeTPratica!=null) {
	    	numeroProtocollo = mudeTistanza.getNumeroProtocollo()!=null ? mudeTistanza.getNumeroProtocollo() : "";
	    	dataProtocollo = df.format(mudeTistanza.getDataProtocollo());
	    }
        List<MudeRIstanzaStato> mudeRIstanzaStatoList = mudeRIstanzaStatoRepository.findByStatoAndIdIstanza(idIstanza, "DPS");
	    if (mudeRIstanzaStatoList.size()>0) {
	    	dataDepositata = df.format(mudeRIstanzaStatoList.get(0).getDataInizio());
	    }	    
	    
	    String testoFinale = testoTb.replace("{dataProtocollo}", dataProtocollo);
	    testoFinale = testoFinale.replace("{dataDeposito}", dataDepositata);
	    testoFinale = testoFinale.replace("{numeroProtocollo}", numeroProtocollo);
	    jasperParam.put("testo", testoFinale);
	   
	    String intestatarioCfPIva="";
	    String contattoIntestatarioCF="";
	    MudeTContatto contattoIntestatario = mudeTContattoRepository.findIntestatarioByIdIstanza(idIstanza);
	    jasperParam.put("intestatarioIstanza", " ");
	    if (contattoIntestatario!=null) {
	    	jasperParam.put("intestatarioIstanza",recuperaIntestazione(contattoIntestatario, contattoIntestatario.getTipoContatto().getValue()));
	    	if (contattoIntestatario.getCf()!=null) {
	    		intestatarioCfPIva=contattoIntestatario.getCf();
	    		contattoIntestatarioCF=contattoIntestatario.getCf();
	    	}
	    	if (contattoIntestatario.getPiva()!=null && !contattoIntestatario.getPiva().isBlank()) {
		    	intestatarioCfPIva=contattoIntestatario.getPiva();
		    }
	    }
	    
	    MudeTContatto contattoProfessionista = mudeTContattoRepository.findProfessionistaByIdIstanza(idIstanza);
	    String contattoProfessionistaCF= "";
	    if (contattoProfessionista!=null) {
	    	contattoProfessionistaCF=contattoProfessionista.getCf();
	    }

	    MudeDTipoIntervento mudeDTipoIntervento = mudeDTipoInterventoRepository.findByIdIstanza(idIstanza);
	    jasperParam.put("tipoIntervento"," ");
	    if (mudeDTipoIntervento!=null) {
	    	if (!mudeDTipoIntervento.getDescrizione().isEmpty()) {
	    		jasperParam.put("tipoIntervento",mudeDTipoIntervento.getDescrizione());	   
	    	} 
	   	}
	     
	    // chiavi di generazione del QRCode
	    // Chiave --> codice generato dal sistema per controllo autenticita' (max 11 caratteri).
	    // Nr. Istanza  --> Numero MUDE dell'istanza (22 caratteri)
	    // Intestatario --> Codice Fiscale / Partita Iva del soggetto che ricopre i ruolo Intestatario nell'istanza
	    // Tecnico --> Codice Fiscale del soggetto che ha firmato l'istanza (PM) se diverso dall'intestatario

	    String barcodeText = RandomStringUtils.randomAlphanumeric(11)+" "+codiceIstanza+" "+intestatarioCfPIva;
	    if (!contattoIntestatarioCF.equals(contattoProfessionistaCF)) {
	    	barcodeText = barcodeText+ " "+contattoProfessionistaCF;
	    }
	    BufferedImage bufferedImage = QrCodeHelper.generateQRCodeImage(barcodeText);
	    jasperParam.put("brCode", bufferedImage);
	    
	    jasperParam.put("numeroLavoratoriPres", " ");
	    jasperParam.put("dataInizioLav", " ");
	    jasperParam.put("durataLavoriPre", " ");
	    jasperParam.put("committente", " ");
	    JSONObject jsonDataIstanza = new JSONObject(mudeTistanza.getJsonData());
	    if (jsonDataIstanza.has("TAB_NOTPREL_COM")) {
	    	JSONObject jsonTabNotPrel = (JSONObject) jsonDataIstanza.get("TAB_NOTPREL_COM");
            if (jsonTabNotPrel.has("comunic_data")) {
              	jasperParam.put("dataInizioLav", jsonTabNotPrel.get("comunic_data"));
            }
            if (jsonTabNotPrel.has("durataPresuntaDeiLavori")) {
              	jasperParam.put("durataLavoriPre", jsonTabNotPrel.get("durataPresuntaDeiLavori")!=null ? jsonTabNotPrel.get("durataPresuntaDeiLavori").toString() : "" );
            }
            if (jsonTabNotPrel.has("numeroMassimoPresuntoLavoratori")) {
              	jasperParam.put("numeroLavoratoriPres",  jsonTabNotPrel.get("numeroMassimoPresuntoLavoratori")!=null ? jsonTabNotPrel.get("numeroMassimoPresuntoLavoratori").toString() : "" );
            }
            if (jsonTabNotPrel.has("tipologiaCommittente")) {
              	jasperParam.put("committente", jsonTabNotPrel.get("tipologiaCommittente"));
            }
            /*if (jsonTabNotPrel.has("dataGrid")) {
            	JSONArray dataGrid = (JSONArray) jsonTabNotPrel.get("dataGrid");
            	for (int i=0; i<dataGrid.length(); i++) {        
                    JSONObject item = (JSONObject) dataGrid.getJSONObject(i);
                    if (item.has("committente")) {
                    	//JSONObject committente = (JSONObject) item.get("committente");
                    	jasperParam.put("committente", item.get("committente"));
                    }
            	}
            }*/
        }
	    
	    jasperParam.put("naturaOpera", " ");
	    if (jsonDataIstanza.has("TAB_QUALIF_3")) {
	    	JSONObject jsonTabQualif3 = (JSONObject) jsonDataIstanza.get("TAB_QUALIF_3");
            if (jsonTabQualif3.has("opere")) {
            	JSONArray opere = (JSONArray) jsonTabQualif3.get("opere");
            	for (int i=0; i<opere.length(); i++) {        
                    JSONObject item = (JSONObject) opere.getJSONObject(i);
                    if (item.has("textArea")) {
                    	jasperParam.put("naturaOpera", item.get("textArea"));
                    }
                 }   
            }
	    }
	    
	    jasperParam.put("tipoNotifica", " ");
	    if (jsonDataIstanza.has("TAB_QUALIF_1")) {
	    	JSONObject jsonTabQualif1 = (JSONObject) jsonDataIstanza.get("TAB_QUALIF_1");
            if (jsonTabQualif1.has("chk_comunica")) {
            	if (jsonTabQualif1.get("chk_comunica").toString().equals("SPE0000036")) {
            		jasperParam.put("tipoNotifica", "Primo invio");
            	}
            	else if (jsonTabQualif1.get("chk_comunica").toString().equals("SPE0000037")) {
                		jasperParam.put("tipoNotifica", "Aggiornamento");
                }
            }
	    }
	    
	    jasperParam.put("tipologiaIntervento", " ");
	    if (jsonDataIstanza.has("TAB_QUALIF_2")) {
	    	JSONObject jsonTabQualif2 = (JSONObject) jsonDataIstanza.get("TAB_QUALIF_2");
            if (jsonTabQualif2.has("tipologia_intervento")) {
            	JSONObject tipologiaIntervento = (JSONObject) jsonTabQualif2.get("tipologia_intervento");
                if (tipologiaIntervento.has("value")) {
                   jasperParam.put("tipologiaIntervento", tipologiaIntervento.get("value"));
                }   
            }
	    }
	       
	    jasperParam.put("titoloEdilizioRif", " ");
	    if (jsonDataIstanza.has("TAB_NOTPREL_QUALIF_8")) {
	    	JSONObject jsonTabNotPrel8 = (JSONObject) jsonDataIstanza.get("TAB_NOTPREL_QUALIF_8");
	    	String dataProtocolloRif=" nd. "; 
	    	String numeroProtocolloRif=" nd. ";
	    	String codTipoIstanza=" nd. ";
            if (jsonTabNotPrel8.has("selectTitoloAbilitativo")) 
            	codTipoIstanza = jsonTabNotPrel8.get("selectTitoloAbilitativo").toString();
            if (jsonTabNotPrel8.has("protEstremi")) 
            	numeroProtocolloRif = jsonTabNotPrel8.get("protEstremi").toString();
            if (jsonTabNotPrel8.has("data")) 
            	dataProtocolloRif = jsonTabNotPrel8.get("data").toString();

            if (jsonTabNotPrel8.has("selectTitoloAbilitativo") || jsonTabNotPrel8.has("protEstremi") || jsonTabNotPrel8.has("data") ) 
            	jasperParam.put("titoloEdilizioRif",  codTipoIstanza + " - prot/estremi: " +
	    			numeroProtocolloRif + " del " + dataProtocolloRif);
	    }
	    /*if (mudeTistanza.getIdIstanzaRiferimento()!=null) {
	    	MudeTIstanza mudeTistanzaRif = mudeTIstanzaRepository.findOne(mudeTistanza.getIdIstanzaRiferimento());
	    	if (mudeTistanzaRif!=null) {
	    		String dataProtocolloRif=" nd. "; 
	    		String numeroProtocolloRif = mudeTistanzaRif.getNumeroProtocollo()!=null ? mudeTistanzaRif.getNumeroProtocollo() : " nd. ";
	    		if (mudeTistanzaRif.getDataProtocollo()!=null) {
	    			dataProtocolloRif = df.format(mudeTistanzaRif.getDataProtocollo());
	    		} 
	    		jasperParam.put("titoloEdilizioRif",  mudeTistanzaRif.getTipoIstanza().getCodice() + " - prot/estremi " +
	    			numeroProtocolloRif + " del " + dataProtocolloRif);
	    	}
	    }*/
	    
	    
	    List<ReportPdfVO> reportPdfVOList = new ArrayList<>();
	    	    	
	    List<MudeTContatto> contattoLaList = mudeTContattoRepository.findContattoByIdIstanzaAndRuolo(idIstanza, "LA");
	    StringBuffer cfContattoLa= new StringBuffer();
	    if (contattoLaList!=null) {
	    	for(MudeTContatto contattoLa : contattoLaList) {
	    		if(contattoLa.getCf()!=null && !contattoLa.getCf().isBlank()) {
	    			if(contattoLa.getCognome() != null && !contattoLa.getCognome().isBlank()) 
	    				cfContattoLa.append(contattoLa.getCognome());
	    			if(contattoLa.getNome() != null && !contattoLa.getNome().isBlank()) 
	    				cfContattoLa.append(" " + contattoLa.getNome());
	    			cfContattoLa.append(" " + contattoLa.getCf() + "; ");
	    		}
	    	}
	    	ReportPdfVO reportPdfVO = new ReportPdfVO();
    	    reportPdfVO.setDescrizioneAllegato(cfContattoLa.toString());
    	    reportPdfVOList.add(reportPdfVO);  
	    }
	    List<MudeTContatto> contattoImList = mudeTContattoRepository.findContattoByIdIstanzaAndRuolo(idIstanza, "IM");
	    StringBuffer cfContattoIm= new StringBuffer();
	    if (contattoImList!=null) {
	    	for(MudeTContatto contattoIm : contattoImList) {
	    		if((contattoIm.getPiva()!=null && !contattoIm.getPiva().isBlank()) || contattoIm.getPivaComunitaria()!=null && !contattoIm.getPivaComunitaria().isBlank()) {
	    			if(contattoIm.getRagioneSociale() != null && !contattoIm.getRagioneSociale().isBlank()) 
	    				cfContattoIm.append(contattoIm.getRagioneSociale());
	    			else if((contattoIm.getCognome() != null && !contattoIm.getCognome().isBlank()) || 
	    					(contattoIm.getNome() != null && !contattoIm.getNome().isBlank()))
	    				cfContattoIm.append(contattoIm.getCognome() + " " + contattoIm.getNome());
	    			if(contattoIm.getPiva()!=null && !contattoIm.getPiva().isBlank())
	    				cfContattoIm.append(" " + contattoIm.getPiva() + "; ");
	    			else
	    				cfContattoIm.append(" " + contattoIm.getPivaComunitaria() + "; ");
	    		}
	    	}
	    	ReportPdfVO reportPdfVO = new ReportPdfVO();
    	    reportPdfVO.setDescrizioneAllegato(cfContattoIm.toString());
    	    reportPdfVOList.add(reportPdfVO); 
	    }
	    String impresaLavori=cfContattoIm.toString() + cfContattoLa.toString();
	    
	    //jasperParam.put("impresaLavori", impresaLavori);
	    jasperParam.put("listaImprese", new JRBeanCollectionDataSource(reportPdfVOList));
	    
	    return jasperParam;

	}
	
	private Map<String, Object> recuperaDatiComuni(
		MudeTIstanza mudeTistanza, MudeDTemplateRicevutaIstanza mudeDTemplateRicevutaIstanza, Timestamp dataGenerazione) throws IOException 
	{
		Map<String, Object> jasperParam = new HashMap<>();
		
		String indirizzo1 = "", indirizzo2 = "", indirizzo3 = "";
		String denomComune = mudeTIstanzaRepository.getDenomComuneByIdIstanza(mudeTistanza.getId());
		MudeTEnte mudeTEnte = mudeTEnteRepository.getEnteRiceventeByIstanza(mudeTistanza.getId());
		jasperParam.put("descrizioneEnte", " ");
	    jasperParam.put("indirizzoEnte", " ");
		if (mudeTEnte!=null) {
			indirizzo1 = mudeTEnte.getIndirizzoEnte();
			
			String splitIndirizzo = mudeCProprietaRepository.getValueByName("BO_RICEVUTA_INDIRIZZO_SPLIT", "");
			if(StringUtils.isNotBlank(splitIndirizzo)) {
				if(StringUtils.isNotBlank(indirizzo1) && indirizzo1.indexOf("~") > -1) {
					String[] indirizzi = indirizzo1.split("~");
					if(indirizzo1.length()>0)
						indirizzo1 = indirizzi[0];
					if(indirizzo1.length()>1)
						indirizzo2 = indirizzi[1];
					if(indirizzo1.length()>2)
						indirizzo3 = indirizzi[2];
				}
				else {
					if(mudeTEnte.getCapEnte() != null && !mudeTEnte.getCapEnte().isBlank())
						indirizzo1 = indirizzo1 + " "+mudeTEnte.getCapEnte() + " ";
					if(mudeTEnte.getDescrizione() != null && !mudeTEnte.getDescrizione().toUpperCase().contains("ASL"))
						indirizzo1 = indirizzo1 + denomComune;
				}
				
			    jasperParam.put("indirizzoEnte1", indirizzo1);
			    jasperParam.put("indirizzoEnte2", indirizzo1);
			    jasperParam.put("indirizzoEnte3", indirizzo1);
			}
			else { // old style
				if(StringUtils.isNotBlank(indirizzo1) && indirizzo1.indexOf("<br>") > -1) {
					indirizzo1 = indirizzo1.replace("<br>", " - ");
					indirizzo1 = indirizzo1.replace("~", " / ");
				}
				else {
					if(mudeTEnte.getCapEnte() != null && !mudeTEnte.getCapEnte().isBlank())
						indirizzo1 = indirizzo1 + " "+mudeTEnte.getCapEnte() + " ";
					if(mudeTEnte.getDescrizione() != null && !mudeTEnte.getDescrizione().toUpperCase().contains("ASL"))
						indirizzo1 = indirizzo1 + " " + denomComune;
				}
				
			    jasperParam.put("indirizzoEnte", indirizzo1);
			}
			jasperParam.put("descrizioneEnte", mudeTEnte.getDescrizione());
			
		    
		    if(mudeTEnte.getLogoContent()!=null)
		    	jasperParam.put("logoEnte", getBufferImageFromByte(mudeTEnte.getLogoContent())); 
		}
		
		jasperParam.put("oggetto", mudeDTemplateRicevutaIstanza.getTestoOggetto()+mudeTistanza.getTipoIstanza().getDescrizione());
	    jasperParam.put("testoAllegati", mudeDTemplateRicevutaIstanza.getTestoBody2());
	    jasperParam.put("testoDettaglioRicevuta",  
	    	mudeDTemplateRicevutaIstanza.getTestoBody3().replace("{dataGenerazione}", df.format(dataGenerazione)));
	    
	    jasperParam.put("codiceIstanza", mudeTistanza.getCodiceIstanza());
	    
	    jasperParam.put("ubicazioneIntervento", " ");
	    List<Object[]> ubicazione = mudeTIndirizzoRepository.findUbicazioneByIdIstanza(mudeTistanza.getId());
	    
	    // JIRA 871 Ricevute istanza Ubicazione intervento
	    String descComune="";
	    if (mudeTistanza.getComune() != null)
	    	descComune = mudeTistanza.getComune().getDenomComune();
	    if (ubicazione.size()>0) {
	    	for (Object[] value :ubicazione) {
		    	jasperParam.put("ubicazioneIntervento", (String)value[4] 
		    		 + " " + (String)value[0]
		    		 + " " + (String)value[1]  
		    		 + " " + (String)value[2] 
		    		 + " " + (String)descComune
		    	);
	    	}
	    }
	    
	    List<MudeTAllegato> mudeTAllegatoList = mudeTAllegatoRepository.findAllByIstanza(mudeTistanza.getId());
	    if (mudeTAllegatoList.size()> 0) {
	    	List<ReportPdfVO> reportPdfVOList = new ArrayList<>();
	    	for (MudeTAllegato allegato: mudeTAllegatoList ) {
	    	    ReportPdfVO reportPdfVO = new ReportPdfVO();
	    	    reportPdfVO.setDescrizioneAllegato("- " + allegato.getTipoAllegato().getDescrizione());
	    	    reportPdfVOList.add(reportPdfVO);  
	    	}
	    	jasperParam.put("listaAllegati", new JRBeanCollectionDataSource(reportPdfVOList));
	    }
	    else {
	    	jasperParam.put("testoAllegati", " ");
	    }
	    
	    return jasperParam;
	}

	private BufferedImage getBufferImageFromByte(byte [] bt) throws IOException {
		InputStream is = new ByteArrayInputStream(bt); 
	    BufferedImage bi = ImageIO.read(is);
	    return bi;
	}

	private String getValueFromMudeCProprieta(String name, String defaultValue) {
		String value = mudeCProprietaRepository.getValueByName(name, defaultValue);
		if (value!=null) {
			return value;
		} 
		return "";
	}
}