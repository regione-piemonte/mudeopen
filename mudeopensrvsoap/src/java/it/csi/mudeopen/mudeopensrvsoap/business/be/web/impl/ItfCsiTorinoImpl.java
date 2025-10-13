/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import javax.xml.bind.Marshaller;

import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.StringResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvsoap.business.be.exception.MudeWSException;
import it.csi.mudeopen.mudeopensrvsoap.business.be.web.ItfCsiTorino;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.GeoRiferimentoPOJO;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.NumeroTicketInesistente_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.SystemException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.vo.Allegato;
import it.csi.mudeopen.mudeopensrvsoap.vo.IstanzaMudePOJO;
import it.csi.mudeopen.mudeopensrvsoap.vo.IstanzaMude;
@WebService(
        serviceName = "ItfCsiTorinoSrvService",
        portName = "ItfCsiTorinoSrvPort",
        targetNamespace = "http://itfcsitorino.business.mudesrvextsic.mude.csi.it/",
        endpointInterface = "it.csi.mudeopen.mudeopensrvsoap.business.be.web.ItfCsiTorino")
public class ItfCsiTorinoImpl extends GenericApi implements ItfCsiTorino {

	private static final String ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA = "Errore generico durante l'elaborazione della richiesta";
	private static final Class<AllegatiApi> ALLEGATI_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.AllegatiApi.class;
	
	private static final Class<IstanzeApi> ISTANZE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi.class;

	@Override
	public List<Allegato> getElencoAllegatiByNumeroIstanza(String token, String numeroIstanza)
										throws SystemException_Exception, NumeroTicketInesistente_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
            Response res = callAPI(mudeTSessione, ALLEGATI_API, getSafeStringPathPar(numeroIstanza));
            res.bufferEntity();
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				List<Allegato> responseList = new ObjectMapper().readValue(json , new TypeReference<List<Allegato>>() {}); 
				
				logEnd(token);
				return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | 
				NumeroTicketInesistente_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public IstanzaMude estraiIstanzaMude (String token, String numeroIstanza)
							throws SystemException_Exception, NumeroTicketInesistente_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);

            Response res = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(numeroIstanza));
            res.bufferEntity();
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				final IstanzaMudePOJO pojo = new ObjectMapper().readValue(json , new TypeReference<IstanzaMudePOJO>() {});
				
				IstanzaMude istanzaMude = new IstanzaMude();
				istanzaMude.setXmlStream(pojo.getXmlStream());
				istanzaMude.setTopeUbicaziones(pojo.getTopeUbicaziones());
				istanzaMude.setAllegatos(pojo.getAllegatos());
				istanzaMude.setMudeRVersioneModello(pojo.getMudeRVersioneModello());

				if(pojo.getElencoGeoRiferimento() != null) {
					List<GeoRiferimento> geoList = new ArrayList<>();
					for(final GeoRiferimentoPOJO p : pojo.getElencoGeoRiferimento())
						geoList.add(convertPOJO2Georiferimento(p));

					istanzaMude.setElencoGeoRiferimento(geoList);
				}
				
				logEnd(token);
				return istanzaMude;
			}
			
			handleException(json);
		} catch (SystemException_Exception | 
				NumeroTicketInesistente_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public List<GeoRiferimento> getElencoGeoRiferimento(String token, String numeroIstanza)
												throws SystemException_Exception, NumeroTicketInesistente_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
            Response res = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(numeroIstanza));
            res.bufferEntity();
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				List<GeoRiferimentoPOJO> convertList = new ObjectMapper().readValue(json , new TypeReference<List<GeoRiferimentoPOJO>>() {}); 

				List<GeoRiferimento> responseList = new ArrayList<>();
				for(final GeoRiferimentoPOJO pojo : convertList)
					responseList.add(convertPOJO2Georiferimento(pojo));

				logEnd(token);
				return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | 
				NumeroTicketInesistente_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	private GeoRiferimento convertPOJO2Georiferimento(final GeoRiferimentoPOJO pojo) {
		GeoRiferimento geoRiferimento = new GeoRiferimento();
		geoRiferimento.setIdLivelloPoligono(pojo.getIdLivelloPoligono());
		geoRiferimento.setDescLivelloPoligono(pojo.getDescLivelloPoligono());
		geoRiferimento.setServizioFonte(pojo.getServizioFonte());
		geoRiferimento.setServizioLivello(pojo.getServizioLivello());
		geoRiferimento.setDataGeoriferimento(pojo.getDataGeoriferimento());
		geoRiferimento.setCodIstatComune(pojo.getCodIstatComune());
		geoRiferimento.setGeoCellulas(pojo.getGeoCellulas());
		geoRiferimento.setGeoCatastos(pojo.getGeoCatastos());
		geoRiferimento.setGeoDatocarotas(pojo.getGeoDatocarotas());
		geoRiferimento.setGeoUbicaziones(pojo.getGeoUbicaziones());

		if(pojo.getCsiGeometry() != null) {
			StringWriter sw = new StringWriter();
			JAXB.marshal(pojo.getCsiGeometry(), sw);
			String xmlString = sw.toString();
			geoRiferimento.setCsiGeometry(xmlString.replaceAll("[^\\x00]*<csiGeometry>[\r\n]*([^\\x00]*)</csiGeometry>.*", "$1"));
		}
		return geoRiferimento;
	}

	public void handleException(String msg) throws MudeWSException {
		if(msg == null) 
			throw new SystemException_Exception("Errore generico");
		
		try {
            JSONObject js = new JSONObject(msg);
            if(js.has("message")) msg = js.getString("message");
		} catch (Exception noJSON) {}

		if(msg.indexOf("NumeroTicketInesistente") > -1)
			throw new NumeroTicketInesistente_Exception(msg);
		if(msg.indexOf("SystemException") > -1)
			throw new SystemException_Exception(msg);
		
    	throw new SystemException_Exception(msg);
	}
	
}
