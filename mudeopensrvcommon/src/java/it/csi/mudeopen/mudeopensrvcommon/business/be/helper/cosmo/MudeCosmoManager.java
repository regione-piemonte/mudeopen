/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo;
import java.io.File;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaCosmo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.AbstractServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaRelazionePraticaRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaRelazionePraticaResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AvviaProcessoFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AvviaProcessoFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaDocumentiFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaDocumentoFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EsitoCreazioneDocumentiFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EsitoCreazioneDocumentoFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.FileUploadResult;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreVariabileArrayRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreVariabileRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaVariabiliFruitoreRequest;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.RelazionePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;

@Component
public class MudeCosmoManager extends AbstractServiceHelper {
	protected String getScope() { return "COSMO"; }

    public CreaPraticaFruitoreResponse insertNewPractice(final String codiceIstanza,
    														final MudeTPraticaCosmo mudeTPraticaCosmo,
												    		final String ipaEnte, 
												    		final String cfCreator, 
												    		final String codiceTipologiaCosmo, 
												    		final String oggetto, 
												    		final String metadati, String riassunto) throws Exception {
    	Response response = null;
    	
		try {
			//			{
			//				"idPratica":"identificativo della pratica sul vostro applicativo",
			//				"codiceTipologia":"denuncia-sismica",
			//				"oggetto":"oggetto che vi permetta di identificare la pratica",
			//				"codiceIpaEnte":ipaEnte,
			//				"utenteCreazionePratica":"AAAAAA00A11G000O",
			//				"metadati":"{\"metadato_1\":\"valore 1\",\"metadato_2\":\"valore 2\"}"
			//			}
			CreaPraticaFruitoreRequest payloadObj = new CreaPraticaFruitoreRequest();
			payloadObj.setIdPratica(codiceIstanza);
			payloadObj.setCodiceTipologia(codiceTipologiaCosmo);
			payloadObj.setCodiceIpaEnte(ipaEnte);
			payloadObj.setUtenteCreazionePratica(cfCreator);
			payloadObj.setRiassunto(riassunto);
			payloadObj.setOggetto(oggetto);
			payloadObj.setMetadati(metadati);

        	doLog(true, mudeTPraticaCosmo, "CreaPraticaFruitoreRequest: " + new ObjectMapper().writeValueAsString(payloadObj));
			
	        Entity<CreaPraticaFruitoreRequest> entity = Entity.json(payloadObj);
	        response = getBuilder("/pratiche")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .post(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
	        	CreaPraticaFruitoreResponse res = null;
//	        	CreaPraticaFruitoreResponse res = response.readEntity(new GenericType<CreaPraticaFruitoreResponse>() {});
	        	
	        	try {
					String json = response.readEntity(String.class);
					
					res = new ObjectMapper().readValue(json , new TypeReference<CreaPraticaFruitoreResponse>() {});
					saveLogResponse(mudeTPraticaCosmo, res, "CreaPraticaFruitoreResponse");
				} catch (Exception e) {
					res = new CreaPraticaFruitoreResponse();
				}
	        	
				return res;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }

    public FileUploadResult uploadDocument(final MudeTPraticaCosmo mudeTPraticaCosmo,
    										final File fin, 
    										final String fileName, 
    										final String mimeType) throws Exception {
    	Response response = null;
    	
		try {
	        MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
	        OutputPart objPart = multipartForm.addFormData("payload", fin, MediaType.valueOf(mimeType), fileName);
        	//objPart.getHeaders().add("Content-Type", mimeType);
        	//objPart.getHeaders().add("Content-Disposition", "form-data; name=payload; filename=" + fileName);
	        
        	doLog(true, mudeTPraticaCosmo, " uploadDocument["+fileName+", "+(fin == null? "NOFILE!" : fin.length())+", "+ mimeType +"]");

            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            response = getBuilder("/file/upload")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
				// { "uploadUUID":"7ba2b58f-2dd8-44d2-b58e-7ad79174c3b8" } 
//				FileUploadResult res = response.readEntity(new GenericType<FileUploadResult>() {});
	        	
				String json = response.readEntity(String.class);
	        	doLog(false, mudeTPraticaCosmo, "FileUploadResult JSON: " + json);
				
				FileUploadResult res = new ObjectMapper().readValue(json , new TypeReference<FileUploadResult>() {});
				return res;
	        }
        	
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }
    
    public File getDocumento(String url) throws Exception {
    	Response response = null;
    	
    	try {
	        response = getDocumentoFromURL(url);
	        
	        if(response.getStatus() == 302) {
	        	String redirectUrl = response.getHeaderString("Location");
	        	logger.info("[MudeCosmoManager::getDocumento] redirect: " + redirectUrl);
	        	
	            Client client = ClientBuilder.newBuilder().build();
	            response = client.target(redirectUrl).request().get();
	        }
	        
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
	            GenericType<File> fileType = new GenericType<File>() {};
	            
	            File file = response.readEntity(fileType);
	            if(file != null) {
		            String filename = response.getHeaderString("Content-Disposition").replaceAll(".*filename=\"([^\"]+)\"", "$1").trim();
		            File renamedFile = new File(file.getParentFile() + FileSystems.getDefault().getSeparator() + filename);
		            file.renameTo(renamedFile);
		            
	            	return renamedFile;
	            }
	        }
        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }
    
    public Response getDocumentoFromURL(String url) throws Exception {
        return getBuilder(url)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .get();
    }
    

    public EsitoCreazioneDocumentiFruitore assignDocumentToPractice(final String codiceIstanza,
    																	final MudeTPraticaCosmo mudeTPraticaCosmo,
    																	final String ipaEnte, 
    																	final List<String> uidDocuments,
    																	final List<String> filenames,
    																	final String tipoDocumento,
    																	final List<String> internalIDs,  
    																	final String codInstanceRif) throws Exception {
    	Response response = null;
    	
		try {
			/*
{
    "codiceIpaEnte": "string",
    "documenti": [
        {
            "codiceTipo": "string",
            "id": "string",
            "idPadre": "string",
            "uploadUUID": "string",
            
            "autore": "string",
            "descrizione": "string",
            "titolo": "string",
            "contenuto": null
        }
    ],
    "idPratica": "string"
}
			{
				"documenti":[{
					"codiceTipo":"documento-generico",
					"uploadUUID":"7ba2b58f-2dd8-44d2-b58e-7ad79174c3b8" (uuid restituito al punto 2)
				}],
				"idPratica":" identificativo della pratica sul vostro applicativo" (lo stesso usato al punto 1),
				"codiceIpaEnte":ipaEnte
			}
			 */
			CreaDocumentiFruitoreRequest payloadObj = new CreaDocumentiFruitoreRequest();
			payloadObj.setIdPratica(codiceIstanza);
			payloadObj.setCodiceIpaEnte(ipaEnte);
				
			ArrayList<CreaDocumentoFruitoreRequest> docs = new ArrayList<>();
			//for(String uidDoc : uidDocuments) {
			for(int i=0; i<uidDocuments.size(); i++) {
				CreaDocumentoFruitoreRequest obj = new CreaDocumentoFruitoreRequest();
				obj.setCodiceTipo(tipoDocumento);
				obj.setUploadUUID(uidDocuments.get(i));
				obj.setDescrizione(filenames.get(i));
				obj.setId(internalIDs.get(i));
				obj.setIdPadre(codInstanceRif);
				docs.add(obj);
			}

			payloadObj.setDocumenti(docs);

        	doLog(true, mudeTPraticaCosmo, "CreaDocumentiFruitoreRequest: " + new ObjectMapper().writeValueAsString(payloadObj));
			
	        Entity<CreaDocumentiFruitoreRequest> entity = Entity.json(payloadObj);
	        response = getBuilder("/documenti")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .post(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201 || response.getStatus() == 409 /* documento gia esistente */) {
	        	EsitoCreazioneDocumentiFruitore res = null;
	        	
	        	try {
					String json = response.readEntity(String.class);
					
					res = new ObjectMapper().readValue(json , new TypeReference<EsitoCreazioneDocumentiFruitore>() {});
					saveLogResponse(mudeTPraticaCosmo, res, "EsitoCreazioneDocumentiFruitore");
					
					for(EsitoCreazioneDocumentoFruitore esito : res.getEsiti()) {
						if("200,201,409".indexOf(esito.getEsito().getStatus()) == -1)
							throw new Exception("assignDocumentToPractice: at least and element is not valid");
					}
					
				} catch (Exception e) {
					res = new EsitoCreazioneDocumentiFruitore();
				}
	        	
				return res;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }

    public AvviaProcessoFruitoreResponse processPractice(final String codiceIstanza,
    														final MudeTPraticaCosmo mudeTPraticaCosmo,
    														final String ipaEnte) throws Exception {
    	Response response = null;
    	
		try {
			//{ 
			//	"idPratica":" identificativo della pratica sul vostro applicativo" (lo stesso usato al punto 1),
			//	"codiceIpaEnte":ipaEnte
			//}
			AvviaProcessoFruitoreRequest payloadObj = new AvviaProcessoFruitoreRequest();
			payloadObj.setIdPratica(codiceIstanza);
			payloadObj.setCodiceIpaEnte(ipaEnte);

        	doLog(true, mudeTPraticaCosmo, "AvviaProcessoFruitoreRequest: " + new ObjectMapper().writeValueAsString(payloadObj));

	        Entity<AvviaProcessoFruitoreRequest> entity = Entity.json(payloadObj);
	        response = getBuilder("/processo")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .post(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
	        	AvviaProcessoFruitoreResponse res = null;
//	        	AvviaProcessoFruitoreResponse res = response.readEntity(new GenericType<AvviaProcessoFruitoreResponse>() {});
	        	
	        	try {
					String json = response.readEntity(String.class);
					
					res = new ObjectMapper().readValue(json , new TypeReference<AvviaProcessoFruitoreResponse>() {});
					saveLogResponse(mudeTPraticaCosmo, res, "AvviaProcessoFruitoreResponse");
				} catch (Exception e) {
					res = new AvviaProcessoFruitoreResponse();
				}
		    	
				return res;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }

    public InviaSegnaleFruitoreResponse sendSignalToPractice(final String codiceIstanza,
    														final MudeTPraticaCosmo mudeTPraticaCosmo,
    														final String ipaEnte, 
    														final String messaggio, 
    														final String listaAllegati,
    														boolean richiediCallback) throws Exception {
    	Response response = null;
    	
		try {
			// { "codiceSegnale":"MESSAGGIO" }
			InviaSegnaleFruitoreRequest payloadObj = new InviaSegnaleFruitoreRequest();
			payloadObj.setCodiceSegnale(messaggio);
			payloadObj.setRichiediCallback(richiediCallback);
			
			if(listaAllegati != null) {
				List<InviaSegnaleFruitoreVariabileRequest> variabili = new ArrayList();
				
				variabili.add(new InviaSegnaleFruitoreVariabileRequest() {{
					setNome("aggiunta_allegati");
					setValore(listaAllegati);
				}});
				payloadObj.setVariabili(variabili);
			}

        	doLog(true, mudeTPraticaCosmo, "InviaSegnaleFruitoreRequest: " + new ObjectMapper().writeValueAsString(payloadObj));
			
	        Entity<InviaSegnaleFruitoreRequest> entity = Entity.json(payloadObj);
	        response = getBuilder("/pratiche/"+codiceIstanza +"/segnala")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .post(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
	        	InviaSegnaleFruitoreResponse res = null;
//	        	InviaSegnaleFruitoreResponse res = response.readEntity(new GenericType<InviaSegnaleFruitoreResponse>() {});
	        	
	        	try {
					String json = response.readEntity(String.class);
					
					res = new ObjectMapper().readValue(json , new TypeReference<InviaSegnaleFruitoreResponse>() {});
					saveLogResponse(mudeTPraticaCosmo, res, "InviaSegnaleFruitoreResponse");
				} catch (Exception e) {
					res = new InviaSegnaleFruitoreResponse();
				}
		    	
	        	return res;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }
    
    public boolean sendAttachListToPractice(final String codiceIstanza,
    														final MudeTPraticaCosmo mudeTPraticaCosmo,
    														final List<String> listaAllegati) throws Exception {
    	Response response = null;
    	
		try {
			// { "codiceSegnale":"MESSAGGIO" }
			InviaVariabiliFruitoreRequest payloadObj = new InviaVariabiliFruitoreRequest();
			
			if(listaAllegati != null) {
				List<InviaSegnaleFruitoreVariabileArrayRequest> variabili = new ArrayList();
				
				variabili.add(new InviaSegnaleFruitoreVariabileArrayRequest() {{
					setNome("allegati_esterni");
					setValore(listaAllegati);
				}});
				payloadObj.setVariabili(variabili);
			}

        	doLog(true, mudeTPraticaCosmo, "InviaVariabiliFruitoreRequest: " + new ObjectMapper().writeValueAsString(payloadObj));
			
	        Entity<InviaVariabiliFruitoreRequest> entity = Entity.json(payloadObj);
	        response = getBuilder("/pratiche/"+codiceIstanza +"/variabili")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .put(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
	        	try {
					String json = response.readEntity(String.class);
					
					mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " + "InviaVariabiliFruitoreRequest JSON: " + json + "\r\n\r\n"); 
				} catch (Exception e) { }
		    	
	        	return true;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return false;
    }
    
    public AggiornaRelazionePraticaResponse linkPractiche(final String codiceIstanza, final String codiceIstanzaParent,
    														final MudeTPraticaCosmo mudeTPraticaCosmo,
    														final String ipaEnte) throws Exception {
    	Response response = null;
    	
		try {
			//{
			//  "codiceIpaEnte": "string",
			//  "relazioniPratica": [
			//    {
			//      "idPraticaExtA": "string",
			//      "tipoRelazione": "'DIPENDE_DA', 'DIPENDENTE_DA', 'DUPLICA'",
			//      "dtInizioValidita": "2024-04-18T10:59:59.098Z",
			//      "dtFineValidita": "2024-04-18T10:59:59.099Z"
			//    }
			//  ]
			//}
			
			AggiornaRelazionePraticaRequest payloadObj = new AggiornaRelazionePraticaRequest();
			
			RelazionePratica relazionePratica = new RelazionePratica();
			List<RelazionePratica> relazioniPratica = new ArrayList();
			relazioniPratica.add(relazionePratica);
			relazionePratica.setIdPraticaExtA(codiceIstanzaParent.replace("-CC", ""));
			relazionePratica.setTipoRelazione("DIPENDE_DA");
			//relazionePratica.setDtInizioValidita(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'").format(new Date(System.currentTimeMillis() + (3600l * 2l * 1000l))));
			//relazionePratica.setDtFineValidita(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'").format(new Date(System.currentTimeMillis() + (3600l * 24l * 10000000l))));
			
			payloadObj.setRelazioniPratica(relazioniPratica);
			payloadObj.setCodiceIpaEnte(ipaEnte);

        	doLog(true, mudeTPraticaCosmo, "AggiornaRelazionePraticaRequest: " + new ObjectMapper().writeValueAsString(payloadObj));

	        Entity<AggiornaRelazionePraticaRequest> entity = Entity.json(payloadObj);
	        response = getBuilder("/pratiche/" + codiceIstanza + "/in-relazione")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .put(entity);
	        if (response.getStatus() == 200 || response.getStatus() == 201) {
	        	AggiornaRelazionePraticaResponse res = null;
//	        	AggiornaRelazionePraticaResponse res = response.readEntity(new GenericType<AggiornaRelazionePraticaResponse>() {});
	        	
	        	try {
					String json = response.readEntity(String.class);
					
					res = new ObjectMapper().readValue(json , new TypeReference<AggiornaRelazionePraticaResponse>() {});
					saveLogResponse(mudeTPraticaCosmo, res, "AggiornaRelazionePraticaResponse");
				} catch (Exception e) {
					res = new AggiornaRelazionePraticaResponse();
				}
	        	
				return res;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }

}