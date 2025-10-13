/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.PraticheApi;
import it.csi.mudeopen.mudeopensrvsoap.business.be.exception.MudeWSException;
import it.csi.mudeopen.mudeopensrvsoap.business.be.web.Documenti;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.AllegatoNonTrovatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.CampoObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.ComuneNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.FruitoreDisabilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.FruitoreNonAbilitatoComuneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.IstanzaNonTrovataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.NotAuthorizedException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.NumeroTicketInesistente_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.PraticaEsistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.StatoIstanzaNonValidoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.SystemException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.util.InputStreamDataSource;
import it.csi.mudeopen.mudeopensrvsoap.vo.AllegaDocumentoSoapResponse;
import it.csi.mudeopen.mudeopensrvsoap.vo.FileOutput;
import it.csi.mudeopen.mudeopensrvsoap.vo.SoapResponse;
import it.csi.mudeopen.mudeopensrvsoap.vo.UploadedFile;
import it.csi.mudeopen.mudeopensrvsoap.vo.UploadedFileBase64;
@Component
@WebService(
        serviceName = "DocumentiSrvService",
        portName = "DocumentiSrvPort",
        targetNamespace = "http://documenti.business.mudesrvextsic.mude.csi.it/",
        endpointInterface = "it.csi.mudeopen.mudeopensrvsoap.business.be.web.Documenti")
@MTOM(enabled = true, threshold = 4000000)
public class DocumentiImpl extends GenericApi implements Documenti {
	private static final String FILENAME = "filename=";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA = "Errore generico durante l'elaborazione della richiesta";
	private static final Class<IstanzeApi> ISTANZE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi.class;
	
	private static final Class<PraticheApi> PRATICHE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.PraticheApi.class;
	@Override
	@XmlMimeType("application/octet-stream")
	public FileOutput estraiFileIstanza(String token, String numeroIstanza) throws IstanzaNonTrovataException_Exception, 
										ComuneNonAbilitatoException_Exception, 
										CampoObbligatorioException_Exception,
										FruitoreDisabilitatoException_Exception,
										StatoIstanzaNonValidoException_Exception, 
										AllegatoNonTrovatoException_Exception,
										FruitoreNonAbilitatoComuneException_Exception,
										NumeroTicketInesistente_Exception,
										SystemException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
	        Response response = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(numeroIstanza));
	        if (HttpStatus.SC_OK == response.getStatus()) {
				InputStream inputStream = response.readEntity(InputStream.class);
				logEnd(token);
				FileOutput fileOutput = new FileOutput();
				fileOutput.setNomeFile(response.getHeaderString(CONTENT_DISPOSITION).split(FILENAME)[1].replace("\"", ""));
				fileOutput.setMimeType(response.getHeaderString(CONTENT_TYPE));
				fileOutput.setFile(new DataHandler(new InputStreamDataSource(inputStream, fileOutput.getNomeFile(), fileOutput.getMimeType())));
				
				return fileOutput;
	        }
	        
			handleException(response.readEntity(String.class));
		} catch (IstanzaNonTrovataException_Exception | 
				ComuneNonAbilitatoException_Exception | 
				CampoObbligatorioException_Exception |
				FruitoreDisabilitatoException_Exception |
				StatoIstanzaNonValidoException_Exception |
				AllegatoNonTrovatoException_Exception |
				FruitoreNonAbilitatoComuneException_Exception |
				NumeroTicketInesistente_Exception |
				SystemException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	@XmlMimeType("application/octet-stream")
	public FileOutput estraiAllegatoIstanza(String token, String numeroIstanza, String nomeFileAllegato, String sbustato) throws CampoObbligatorioException_Exception,
																										    IstanzaNonTrovataException_Exception, 
																											ComuneNonAbilitatoException_Exception, 
																											FruitoreDisabilitatoException_Exception,
																											StatoIstanzaNonValidoException_Exception, 
																											AllegatoNonTrovatoException_Exception,
																											FruitoreNonAbilitatoComuneException_Exception,
																											NumeroTicketInesistente_Exception,
																											SystemException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
	        Response response = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(numeroIstanza), nomeFileAllegato, sbustato);
	        if (HttpStatus.SC_OK == response.getStatus()) {
				InputStream inputStream = response.readEntity(InputStream.class);
				logEnd(token);
				
				FileOutput fileOutput = new FileOutput();
				fileOutput.setNomeFile(response.getHeaderString(CONTENT_DISPOSITION).split(FILENAME)[1].replace("\"", ""));
				fileOutput.setMimeType(response.getHeaderString(CONTENT_TYPE));
				fileOutput.setFile(new DataHandler(new InputStreamDataSource(inputStream, fileOutput.getNomeFile(), fileOutput.getMimeType())));
				
				return fileOutput;
			}
	        
			handleException(response.readEntity(String.class));
		} catch (CampoObbligatorioException_Exception |
			    IstanzaNonTrovataException_Exception | 
				ComuneNonAbilitatoException_Exception |
				FruitoreDisabilitatoException_Exception |
				StatoIstanzaNonValidoException_Exception | 
				AllegatoNonTrovatoException_Exception |
				FruitoreNonAbilitatoComuneException_Exception |
				NumeroTicketInesistente_Exception |
				SystemException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	@Override
	@XmlMimeType("application/octet-stream")
	public FileOutput estraiDocumentoPratica(String token, String istatComune, String numeroPratica,
											  String annoPratica, String nomeDocumento) throws NumeroTicketInesistente_Exception,
																								AllegatoNonTrovatoException_Exception,
																								CampoObbligatorioException_Exception,
																								FruitoreDisabilitatoException_Exception,
																								PraticaEsistenteException_Exception,
																								FruitoreNonAbilitatoComuneException_Exception,
																								NotAuthorizedException_Exception,
																								SystemException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
	        
	        Response response = callAPI(mudeTSessione, PRATICHE_API, getSafeStringPathPar(istatComune), getSafeStringPathPar(numeroPratica), getSafeStringPathPar(annoPratica), getSafeStringPathPar(nomeDocumento));
	        if (HttpStatus.SC_OK == response.getStatus()) {
				InputStream inputStream = response.readEntity(InputStream.class);
				logEnd(token);
				
				FileOutput fileOutput = new FileOutput();
				fileOutput.setNomeFile(response.getHeaderString(CONTENT_DISPOSITION).split(FILENAME)[1].replace("\"", ""));
				fileOutput.setMimeType(response.getHeaderString(CONTENT_TYPE));
				fileOutput.setFile(new DataHandler(new InputStreamDataSource(inputStream, fileOutput.getNomeFile(), fileOutput.getMimeType())));
				
				return fileOutput;
			}
			handleException(response.readEntity(String.class));
		} catch (NumeroTicketInesistente_Exception | 
				NotAuthorizedException_Exception | 
				FruitoreDisabilitatoException_Exception |
				CampoObbligatorioException_Exception |
				AllegatoNonTrovatoException_Exception | 
				PraticaEsistenteException_Exception |
				FruitoreNonAbilitatoComuneException_Exception |
				SystemException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	@Override
	public AllegaDocumentoSoapResponse allegaDocumentoPratica(String token, String istatComune, String numeroPratica,
				String anno, String tipoDocumento, UploadedFile file) throws NumeroTicketInesistente_Exception,
																			AllegatoNonTrovatoException_Exception,
																			FruitoreDisabilitatoException_Exception,
																			CampoObbligatorioException_Exception,
																			FruitoreNonAbilitatoComuneException_Exception,
																			NotAuthorizedException_Exception,
																			PraticaEsistenteException_Exception,
																			IstanzaNonTrovataException_Exception,
																			SystemException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
			MultipartFormDataOutput dataOutput = new MultipartFormDataOutput();
			dataOutput.addFormData("comune", istatComune, MediaType.TEXT_PLAIN_TYPE);
			dataOutput.addFormData("numeroPratica", numeroPratica, MediaType.TEXT_PLAIN_TYPE);
			dataOutput.addFormData("anno", anno, MediaType.TEXT_PLAIN_TYPE);
			dataOutput.addFormData("tipoDocumento", tipoDocumento, MediaType.TEXT_PLAIN_TYPE);
	        OutputPart objPart = dataOutput.addFormData("fileDocumento", file.getDataHandler().getInputStream(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
        	objPart.getHeaders().putSingle(CONTENT_DISPOSITION, "form-data; name=fileDocumento; filename=" + file.getName());
			Response response = callMultipartAPI(mudeTSessione.getFruitore(), "/upload-documento-pratica", dataOutput);
			String json = response.readEntity(String.class);
			if (HttpStatus.SC_OK == response.getStatus()) {
				AllegaDocumentoSoapResponse allegaDocumentoSoapResponse = new ObjectMapper().readValue(json, new TypeReference<AllegaDocumentoSoapResponse>() {});
				
				logEnd(token);
				return allegaDocumentoSoapResponse;
			}
			
			handleException(json);
		} catch (NumeroTicketInesistente_Exception | 
				NotAuthorizedException_Exception | 
				CampoObbligatorioException_Exception |
				FruitoreDisabilitatoException_Exception |
				AllegatoNonTrovatoException_Exception |
				FruitoreNonAbilitatoComuneException_Exception |
				PraticaEsistenteException_Exception |
				IstanzaNonTrovataException_Exception |
				SystemException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public AllegaDocumentoSoapResponse allegaDocumentoPraticaBase64(String token, String istatComune, String numeroPratica,
				String anno, String tipoDocumento, UploadedFileBase64 file) throws NumeroTicketInesistente_Exception,
																			AllegatoNonTrovatoException_Exception,
																			FruitoreDisabilitatoException_Exception,
																			CampoObbligatorioException_Exception,
																			FruitoreNonAbilitatoComuneException_Exception,
																			NotAuthorizedException_Exception,
																			PraticaEsistenteException_Exception,
																			IstanzaNonTrovataException_Exception,
																			SystemException_Exception {
		try(FileOutputStream fout = new FileOutputStream(Files.createTempFile("allegadoc", ".tmp").toFile())) {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			MultipartFormDataOutput dataOutput = new MultipartFormDataOutput();
			dataOutput.addFormData("comune", istatComune, MediaType.TEXT_PLAIN_TYPE);
			dataOutput.addFormData("numeroPratica", numeroPratica, MediaType.TEXT_PLAIN_TYPE);
			dataOutput.addFormData("anno", anno, MediaType.TEXT_PLAIN_TYPE);
			dataOutput.addFormData("tipoDocumento", tipoDocumento, MediaType.TEXT_PLAIN_TYPE);
	        OutputPart objPart = dataOutput.addFormData("fileDocumento", file.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
        	objPart.getHeaders().putSingle(CONTENT_DISPOSITION, "form-data; name=fileDocumento; filename=" + file.getName());
			Response response = callMultipartAPI(mudeTSessione.getFruitore(), "/upload-documento-pratica", dataOutput);
			String json = response.readEntity(String.class);
			
			if (HttpStatus.SC_OK == response.getStatus()) {
				AllegaDocumentoSoapResponse allegaDocumentoSoapResponse = new ObjectMapper().readValue(json, new TypeReference<AllegaDocumentoSoapResponse>() {});
				
				logEnd(token);
				return allegaDocumentoSoapResponse;
			}
			
			handleException(json);
		} catch (NumeroTicketInesistente_Exception | 
				NotAuthorizedException_Exception | 
				CampoObbligatorioException_Exception |
				FruitoreDisabilitatoException_Exception |
				AllegatoNonTrovatoException_Exception |
				FruitoreNonAbilitatoComuneException_Exception |
				PraticaEsistenteException_Exception |
				IstanzaNonTrovataException_Exception |
				SystemException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public void eliminaDocumentoPratica(String token, String istatComune, String numeroPratica, 
										  String annoPratica, String nomeDocumento) throws NumeroTicketInesistente_Exception,
																							AllegatoNonTrovatoException_Exception,
																							CampoObbligatorioException_Exception,
																							PraticaEsistenteException_Exception,
																							FruitoreNonAbilitatoComuneException_Exception,
																							NotAuthorizedException_Exception,
																							SystemException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
			SoapResponse soapResponse = new SoapResponse();
			Response response = callAPI(mudeTSessione, PRATICHE_API, getSafeStringPathPar(istatComune), getSafeStringPathPar(numeroPratica), getSafeStringPathPar(annoPratica), getSafeStringPathPar(nomeDocumento));
			if (HttpStatus.SC_OK == response.getStatus())
				return;
		
			handleException(response.readEntity(String.class));
		} catch (NumeroTicketInesistente_Exception | 
				NotAuthorizedException_Exception | 
				CampoObbligatorioException_Exception |
				AllegatoNonTrovatoException_Exception |
				FruitoreNonAbilitatoComuneException_Exception |
				SystemException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	@Override
	protected void handleException(String msg) throws MudeWSException {
		if(msg == null) 
			throw new SystemException_Exception("Errore generico");
		
		try {
            JSONObject js = new JSONObject(msg);
            if(js.has("message")) msg = js.getString("message");
		} catch (Exception noJSON) {}
		if(msg.indexOf("NotAuthorizedException") > -1)
			throw new NotAuthorizedException_Exception(msg);
		if(msg.indexOf("CampoObbligatorioException") > -1)
			throw new CampoObbligatorioException_Exception(msg);
		if(msg.indexOf("NumeroTicketInesistente") > -1)
			throw new NumeroTicketInesistente_Exception(msg);
		if(msg.indexOf("ComuneNonAbilitatoException") > -1)
			throw new ComuneNonAbilitatoException_Exception(msg);
		if(msg.indexOf("PraticaEsistenteException") > -1)
			throw new PraticaEsistenteException_Exception(msg);
		if(msg.indexOf("IstanzaNonTrovataException") > -1)
			throw new IstanzaNonTrovataException_Exception(msg);
		if(msg.indexOf("FruitoreDisabilitatoException") > -1)
			throw new FruitoreDisabilitatoException_Exception(msg);
		if(msg.indexOf("FruitoreNonAbilitatoComuneException") > -1)
			throw new FruitoreNonAbilitatoComuneException_Exception(msg);
		if(msg.indexOf("StatoIstanzaNonValidoException") > -1)
			throw new StatoIstanzaNonValidoException_Exception(msg);
		if(msg.indexOf("AllegatoNonTrovatoException") > -1)
			throw new AllegatoNonTrovatoException_Exception(msg);
		if(msg.indexOf("SystemException") > -1)
			throw new SystemException_Exception(msg);
		
    	throw new SystemException_Exception(msg);
	}
	
}
