/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvapi.business.be.web.IdfCallbackApi;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiModificaStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDWfStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaIdf;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.Esito;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.FileServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDWfStatiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeREnteTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaIdfRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.TipoDocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaSlimVO;

@Component
public class IdfSrvCallbackApiImpl extends StatiIstanzaApiImpl implements IdfCallbackApi {
	 
	private static Logger logger = Logger.getLogger(IdfSrvCallbackApiImpl.class.getCanonicalName());
	
	@Autowired
	private MudeTIstanzaRepository mudeTIstanzaRepository;
	
	@Autowired
	private IstanzaEntityMapper istanzaEntityMapper;
	
	@Autowired
	private MudeREnteTipoIstanzaRepository mudeREnteTipoIstanzaRepository;
	
	@Autowired
	private MudeDWfStatiRepository mudeDWfStatiRepository;
	
	@Autowired
	private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;
	
	@Autowired
	private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;
	
    @Autowired
    private MudeDTipoDocpaRepository mudeDTipoDocpaRepository;
    
    @Autowired
    private MudeTPraticaRepository mudeTPraticaRepository;
    
    @Autowired
    private MudeTPraticaIdfRepository mudeTPraticaIdfRepository;
    
    @Autowired
    private MudeRIstanzaPraticaRepository mudeRIstanzaPraticaRepository;
    
	@Override
	public Response invioRichiestaAutorizzazioneIDF(MultipartFormDataInput input,
												Integer idIstanza,
												SecurityContext securityContext) {
		String errMsg;
		MudeTPraticaIdf mudeTPraticaIdf = mudeTPraticaIdfRepository.findByIdIstanza(idIstanza.longValue());
		if(mudeTPraticaIdf == null)
			return esito("KO", "Not found - istanza specificata non e' stata trovata", 404);
		
		try {
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] begin");
	        
			MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza.longValue());
			if(mudeTIstanza == null)
				return esito("KO", "Not found - istanza specificata non e' stata trovata", 404);

			String tipoDocumento = null;
			String numProtocolloRichiestaAutorizzazione = null;
			String dataProtocolloRichiestaAutorizzazione = null;
			
			if(input.getFormDataMap().get("tipoDocumento") != null)
				tipoDocumento = input.getFormDataMap().get("tipoDocumento").get(0).getBody(String.class, null);
			if(input.getFormDataMap().get("numProtocolloRichiestaAutorizzazione") != null)
				numProtocolloRichiestaAutorizzazione = input.getFormDataMap().get("numProtocolloRichiestaAutorizzazione").get(0).getBody(String.class, null);
			if(input.getFormDataMap().get("dataProtocolloRichiestaAutorizzazione") != null)
				dataProtocolloRichiestaAutorizzazione = input.getFormDataMap().get("dataProtocolloRichiestaAutorizzazione").get(0).getBody(String.class, null);
			File fileDocumento = input.getFormDataPart("fileAutorizzazione", File.class, null);
			
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] input["+idIstanza+"]: " + tipoDocumento + " / " + numProtocolloRichiestaAutorizzazione + " / " + dataProtocolloRichiestaAutorizzazione + " / " + fileDocumento);
			
			if(tipoDocumento == null
					|| fileDocumento == null)
				return esito("KO", "Bad Request - i dati inviati non sono formalmente corretti", 400);
			
	        String filename = FileServiceHelper.getFileName(input, "fileAutorizzazione");
	        
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] documento["+idIstanza+"]: " + filename);
	        
			if(fileDocumento == null
					|| StringUtils.isBlank(filename))
				return esito("KO", "Unprocessable entity - i dati inviati non sono corretti", 422);

			mudeTPraticaIdf.setTipoDocumento(tipoDocumento);
			
			MudeDTipoDocPA mudeDTipoDocPA = mudeDTipoDocpaRepository.findByCodice("IDF-ACQ");

			MudeTPratica mudeTPratica = mudeTPraticaRepository.findAnyByIdIstanza(mudeTIstanza.getId());
			if(mudeTPratica == null) {
				mudeTPratica = new MudeTPratica();
				mudeTPratica.setNumeroPratica("IDF-" + mudeTIstanza.getCodiceIstanza());
				mudeTPratica.setDataFine(new Date());
				mudeTPraticaRepository.saveAndFlushDAO(mudeTPratica);
				
		    	MudeRIstanzaPratica mudeRIstanzaPratica = new MudeRIstanzaPratica();
		    	mudeRIstanzaPratica.setIstanza(mudeTIstanza);
		    	mudeRIstanzaPratica.setPratica(mudeTPratica);
		    	mudeRIstanzaPraticaRepository.saveAndFlushDAO(mudeRIstanzaPratica);
			}

			try {
	            // fine controlli
				MultipartFormDataOutput output = new MultipartFormDataOutput();
				
	            DocumentoVO documentoPratica = new DocumentoVO(); 
	            TipoDocumentoVO tipoDocumento2 = new TipoDocumentoVO();
	            tipoDocumento2.setCodice(mudeDTipoDocPA.getCodeTipoDocpa());
	            tipoDocumento2.setDescrizione(mudeDTipoDocPA.getDescTipoDocpa());
				documentoPratica.setTipoDocumento(tipoDocumento2);
				
				if(StringUtils.isNotBlank(numProtocolloRichiestaAutorizzazione))
					documentoPratica.setNumeroProtocollo(numProtocolloRichiestaAutorizzazione);
				if(StringUtils.isNotBlank(dataProtocolloRichiestaAutorizzazione))
					documentoPratica.setDataProtocollo(dataProtocolloRichiestaAutorizzazione);

				ObjectMapper mapper = new ObjectMapper();
	            documentoPratica.setNomeFileDocumento(mudeDTipoDocPA.getCodeTipoDocpa() + "_" + filename);
				
	            output.addFormData("documentoPratica", mapper.writeValueAsString(documentoPratica), MediaType.TEXT_PLAIN_TYPE);
	            
				OutputPart objPart = output.addFormData("file", fileDocumento, MediaType.APPLICATION_OCTET_STREAM_TYPE);
	        	objPart.getHeaders().putSingle("Content-Disposition", "form-data; name=file; filename=" + filename);

				GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(output) {};
				
				Response response = postResponse("/pratiche/documenti/"+mudeTPratica.getId()+"?scope=ws", entity, mudeTIstanza.getMudeTUser().getCf());
				if(response.getStatus() >= 201)
					throw new BusinessException("Errore dal COMMON durante il caricamento del documento: " + response.readEntity(String.class));
			} finally {
				if(!fileDocumento.delete())
					LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] file not deleted" + fileDocumento.getAbsolutePath());
			}
			
			if(mudeTPraticaIdf != null) {
				mudeTPraticaIdf.setJsonResponse(StringUtils.defaultString(mudeTPraticaIdf.getJsonResponse(), "") + "invioRichiestaAutorizzazioneIDF RESULT: OK\r\n\r\n");
				mudeTPraticaIdfRepository.saveDAO(mudeTPraticaIdf);
			}
			
			return esito("OK", "La richiesta è stata elaborata correttamente", HttpStatus.SC_OK);
		} catch (BusinessException be) {
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] EXCEPTION: " + ExceptionUtils.getStackTrace(be));
	        errMsg = be.getMessage();

		} catch (Exception e) {
			LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] EXCEPTION: Internal Server Error " + ExceptionUtils.getStackTrace(e));
			errMsg = "Internal Server Error: " + ExceptionUtils.getMessage(e);
		}
		
		if(mudeTPraticaIdf != null) {
			mudeTPraticaIdf.setJsonResponse(StringUtils.defaultString(mudeTPraticaIdf.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] invioRichiestaAutorizzazioneIDF error: " + errMsg + "\r\n\r\n");
			mudeTPraticaIdfRepository.saveDAO(mudeTPraticaIdf);
		}
		
		return esito("KO", errMsg, 500);
	}

	
	private Response esito(String code, String mrs, int httpcode) {
		Esito esito = new Esito();
		esito.setCode(code);
		esito.setMessage(mrs);
		
		LoggerUtil.info(logger, "[IdfCallbackApi::invioRichiestaAutorizzazioneIDF] RESULT["+httpcode+"]: " + code + " / " + mrs);
			
		try {
			return voToResponse(esito, httpcode);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Response invioDatiConcessioneAutorizzazioneIDF(MultipartFormDataInput input,
												Integer idIstanza,
												SecurityContext securityContext) {
		String errMsg;
		MudeTPraticaIdf mudeTPraticaIdf = mudeTPraticaIdfRepository.findByIdIstanza(idIstanza.longValue());
		if(mudeTPraticaIdf == null)
			return esito("KO", "Not found - istanza specificata non e' stata trovata", 404);
		
		try {
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioDatiConcessioneAutorizzazioneIDF] begin");
	        
			MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza.longValue());
			if(mudeTIstanza == null)
				return esito("KO", "Not found - istanza specificata non e' stata trovata", 404);

			String autorizzato = null;
			String numeroDeterminaEsitoAut = null;
			String dataScadenzaAutorizzazione = null;

			if(input.getFormDataMap().get("autorizzato") != null)
				autorizzato = input.getFormDataMap().get("autorizzato").get(0).getBody(String.class, null);
			if(input.getFormDataMap().get("numeroDeterminaEsitoAut") != null)
				numeroDeterminaEsitoAut = input.getFormDataMap().get("numeroDeterminaEsitoAut").get(0).getBody(String.class, null);
			if(input.getFormDataMap().get("dataScadenzaAutorizzazione") != null)
				dataScadenzaAutorizzazione = input.getFormDataMap().get("dataScadenzaAutorizzazione").get(0).getBody(String.class, null);
			
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioDatiConcessioneAutorizzazioneIDF] input["+idIstanza+"]: " + autorizzato + " / " + numeroDeterminaEsitoAut + " / " + dataScadenzaAutorizzazione);
			
			if(autorizzato == null 
					|| numeroDeterminaEsitoAut == null)
				return esito("KO", "Bad Request - i dati inviati non sono formalmente corretti", 400);
			
			mudeTPraticaIdf.setAutorizzato(autorizzato);
			mudeTPraticaIdf.setNumeroDeterminaEsitoAut(numeroDeterminaEsitoAut);
			
			if(dataScadenzaAutorizzazione != null)
				mudeTPraticaIdf.setDataScadenzaAutorizzazione(dataScadenzaAutorizzazione);
			
			mudeTPraticaIdf.setJsonResponse(StringUtils.defaultString(mudeTPraticaIdf.getJsonResponse(), "") + "invioDatiConcessioneAutorizzazioneIDF RESULT: OK\r\n\r\n");
			mudeTPraticaIdfRepository.saveDAO(mudeTPraticaIdf);
			
			return esito("OK", "La richiesta è stata elaborata correttamente", HttpStatus.SC_OK);
		} catch (BusinessException be) {
	        LoggerUtil.info(logger, "[IdfCallbackApi::invioDatiConcessioneAutorizzazioneIDF] EXCEPTION: " + ExceptionUtils.getStackTrace(be));
	        errMsg = be.getMessage();

		} catch (Exception e) {
			LoggerUtil.info(logger, "[IdfCallbackApi::invioDatiConcessioneAutorizzazioneIDF] EXCEPTION: Internal Server Error " + ExceptionUtils.getStackTrace(e));
			errMsg = "Internal Server Error: " + ExceptionUtils.getStackTrace(e);
		}
		
		if(mudeTPraticaIdf != null) {
			mudeTPraticaIdf.setJsonResponse(StringUtils.defaultString(mudeTPraticaIdf.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] invioDatiConcessioneAutorizzazioneIDF error: " + errMsg + "\r\n\r\n");
			mudeTPraticaIdfRepository.saveDAO(mudeTPraticaIdf);
		}
		
		return esito("KO", errMsg, 500);
	}

	
}
