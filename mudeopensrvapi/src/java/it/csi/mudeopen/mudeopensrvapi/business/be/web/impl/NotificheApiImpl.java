/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenDRuoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDRuoloUtenteService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDTipoNotificaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTNotificaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.NotificheApi;
import it.csi.mudeopen.mudeopensrvapi.util.Constants;
import it.csi.mudeopen.mudeopensrvapi.vo.AllegatoNotificaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.InserimentoNotificaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.NotificaInviataVO;
import it.csi.mudeopen.mudeopensrvapi.vo.NotificaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.RuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

@Component
public class NotificheApiImpl extends AbstractApiServiceImpl implements NotificheApi {
	
	@Autowired
	private MudeopenTIstanzaService mudeopenTIstanzaService; 

	@Autowired
	private MudeopenTNotificaService mudeopenTNotificaService;
	
	@Autowired
	private MudeopenDTipoNotificaService mudeopenDTipoNotificaService;

	@Autowired
	private MudeopenDRuoloUtenteRepository mudeopenDRuoloUtenteRepository;

    @Autowired
    private MudeopenDRuoloUtenteService mudeopenDRuoloUtenteService;

	@Override
	public Response elencoNotificheInviate(String XRequestId, String XForwardedFor, String fruitoreID,
											String numeroIstanza) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));
			Optional<MudeTIstanzaSLIM> istanzaOpt = mudeopenTIstanzaService.cercaIstanzaWSSlim(fruitore, numeroIstanza);
			if(istanzaOpt.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] Istanza non trovata"));				
			
			List<NotificaInviataVO> notificaInviataVOs = mudeopenTNotificaService.cercaNotificheInviate(istanzaOpt.get().getCodiceIstanza(), istanzaOpt.get().getId());
			return voToResponse(notificaInviataVOs, HttpStatus.SC_OK);
			
        } catch (Throwable t) {
        	return errorToResponse(handleUnexpectedError(t));
		}
	}
	
	@Override
	public Response visualizzazioneNotifica(String XRequestId, String XForwardedFor, String fruitoreID,
											String numeroIstanza, Long idEvento) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));
			
			if(idEvento == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Identificativo Notifica"));

			Optional<MudeTIstanzaSLIM> istanzaOpt = mudeopenTIstanzaService.cercaIstanzaWSSlim(fruitore, numeroIstanza);
			if(istanzaOpt.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] Istanza non trovata"));
			
			Optional<NotificaVO> visualizzaNotificaVO = mudeopenTNotificaService.visualizzaNotifica(istanzaOpt.get().getCodiceIstanza(), idEvento);
			if(visualizzaNotificaVO.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] Notifica non trovata"));				

			return voToResponse(visualizzaNotificaVO.get(), HttpStatus.SC_OK);
			
        } catch (Throwable t) {
        	return errorToResponse(handleUnexpectedError(t));        	
		}
	}

	@Override
	public Response elencoAllegatiNotifica(String XRequestId, String XForwardedFor, String fruitoreID,
												String numeroIstanza, Long idEvento) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

	    	if(StringUtils.isBlank(numeroIstanza))
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));

	    	if(idEvento == null)
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Identificativo Notifica"));

			Optional<MudeTIstanzaSLIM> istanzaOpt = mudeopenTIstanzaService.cercaIstanzaWSSlim(fruitore, numeroIstanza);
			if(istanzaOpt.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] Istanza non trovata"));
			
			List<AllegatoNotificaVO> allegatoNotificaVOs = mudeopenTNotificaService.cercaAllegatiNotifica(istanzaOpt.get().getCodiceIstanza(), idEvento);
			return voToResponse(allegatoNotificaVOs, HttpStatus.SC_OK);
			
        } catch (Throwable t) {
        	return errorToResponse(handleUnexpectedError(t));
		}
	}

	@Override
	public Response inserisciNotifica(String XRequestId, String XForwardedFor, String fruitoreID,
												InserimentoNotificaVO inserisciNotifica) throws Exception {
        try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

	    	if(StringUtils.isBlank(inserisciNotifica.getNumeroIstanza()))
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Il numero istanza e' obbligatorio"));

	    	if(StringUtils.isBlank(inserisciNotifica.getTipoNotifica()))
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Identificativo Notifica"));

	    	if(StringUtils.isBlank(inserisciNotifica.getMittente()))
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Mittente Obbligatorio"));

	    	Optional<MudeDTipoNotifica> findByCodTipoNotifica = mudeopenDTipoNotificaService.findBySubCodTipoNotifica(inserisciNotifica.getTipoNotifica());
			if(findByCodTipoNotifica.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Tipo Notifica non valido"));
			
	    	if(StringUtils.isBlank(inserisciNotifica.getOggetto()))
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Oggetto non valorizzato"));
	    	
	    	if(StringUtils.isBlank(inserisciNotifica.getTesto()))
	    		return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Testo non valorizzato"));

			Optional<IstanzaVO> istanzaOpt = mudeopenTIstanzaService.ricercaIstanza(fruitore,inserisciNotifica.getNumeroIstanza());
			if(istanzaOpt.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[IstanzaNonTrovataException] Istanza non trovata"));
			
			RuoloVO ruolomittente = mudeopenDRuoloUtenteService.findByIdFunzione(fruitoreID).stream().filter(x -> x.getCodiceRuolo().equals(inserisciNotifica.getMittente())).findFirst().orElse(null);
			if(ruolomittente == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Codice mittente non valido"));

			List<Long> idDocumenti = new ArrayList<>();
			if(CollectionUtils.emptyIfNull(inserisciNotifica.getNomeDocumento()).size() != 0) {
				if(istanzaOpt.get().getIdPratica() == null)
					return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[PraticaEsistenteException] Nessuna pratica associata all'istanza " + inserisciNotifica.getNumeroIstanza()));

				List<DocumentoVO> documentiPratica = ottieniDocumentiPratica(istanzaOpt.get().getIdPratica(),fruitoreID, "essential");
				/*for (DocumentoVO documentoVO : documentiPratica) {
					if (!inserisciNotifica.getNomeDocumento().contains(documentoVO.getNomeFileDocumento()))
						return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "Allegati non validi per l'istanza"));

					idDocumenti.add(documentoVO.getId());
				}*/
				int fileTrovati=0;
				for(int i=0;i<inserisciNotifica.getNomeDocumento().size();i++) {
					for(DocumentoVO documentoVO : documentiPratica) {
						// TASK-37
						if(inserisciNotifica.getNomeDocumento().get(i).equals(documentoVO.getNomeFileDocumento())) {
							fileTrovati++;
							idDocumenti.add(documentoVO.getId());
							break;
						}
					}
				}
				if(fileTrovati<inserisciNotifica.getNomeDocumento().size())
					return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Allegati non validi per l'istanza"));
			}
			
			Optional<MudeDTipoNotifica> tipoNotifica = mudeopenDTipoNotificaService.findBySubCodTipoNotifica(inserisciNotifica.getTipoNotifica());
			if(tipoNotifica.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[TipoNotificaInesistenteException] Tipo notifica non valido"));
			
			IstanzaVO toSend = new IstanzaVO();
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("rifDocumenti", "true");
			jsonMap.put("rifDocumentiList", idDocumenti);
			jsonMap.put("tipo_notifica", tipoNotifica.get().getIdTipoNotifica());
			jsonMap.put("oggetto", inserisciNotifica.getOggetto());
			jsonMap.put("mittente", ruolomittente.getDescrizioneRuolo());
			jsonMap.put("testo", inserisciNotifica.getTesto());
			toSend.setJsonData(mapper.writeValueAsString(jsonMap));
			
			toSend.setEnteDiRiferimento(istanzaOpt.get().getEnteDiRiferimento());
			toSend.setStatoIstanza(istanzaOpt.get().getStatoIstanza());
			toSend.setTipologiaIstanza(istanzaOpt.get().getTipologiaIstanza());
			toSend.setIdIstanza(istanzaOpt.get().getIdIstanza());
			/*MultipartFormDataOutput output = new MultipartFormDataOutput();
			output.addFormData("istanza", toSend, MediaType.APPLICATION_JSON_TYPE);
			GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(output) {};
			return putResponse(String.format("/notifiche/%s/nuova-notifica/%s", istanzaOpt.get().getIdIstanza(),tipoNotifica.get().getIdTipoNotifica()), entity, fruitore.getCodiceFruitore());
			*/
			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(fruitoreID));
			WebTarget target = client.target(String.format("%s/notifiche/%s/nuova-notifica/%s?scope=ws", getCommonServerURL(), istanzaOpt.get().getIdIstanza(),tipoNotifica.get().getIdTipoNotifica()));
			Invocation.Builder invocationBuilder = target.request();
			
			Response response = invocationBuilder.header(Constants.HEADER_USER_CF, fruitoreID).put(Entity.entity(toSend, MediaType.APPLICATION_JSON));
			return response;
			
        } catch (Throwable t) {
        	return errorToResponse(handleUnexpectedError(t));
		}
	}

}
