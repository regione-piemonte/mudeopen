/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTNotificaRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTNotificaService;
import it.csi.mudeopen.mudeopensrvapi.vo.AllegatoNotificaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.NotificaInviataVO;
import it.csi.mudeopen.mudeopensrvapi.vo.NotificaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;

@Service
public class MudeopenTNotificaServiceImpl implements MudeopenTNotificaService {
	
	private static final String TESTO = "testo";
	private static final String OGGETTO = "oggetto";
	//	private static final String MITTENTE = "mittente";
	//	private static final String NOME = "nome";
	//	private static final String COGNOME = "cognome";

	private static Logger logger = Logger.getLogger(MudeopenTNotificaServiceImpl.class.getCanonicalName());

	@Autowired
	private MudeopenTNotificaRepository mudeopenTNotificaRepository;

	
	@Autowired
	public MudeRNotificaDocumentoRepository documentiNotificaRepository;
	
    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    private MudeRNotificaUtenteRepository mudeRNotificaUtenteRepository;

	@Override
	public List<NotificaInviataVO> cercaNotificheInviate(String codiceIstanza, Long idIstanza) {
		List<MudeTNotifica> notifiche =  cercaPerNumeroIstanza(codiceIstanza);//TODO: validazione utente loggato
		
		List<NotificaInviataVO> notificaInviataVOs = new ArrayList<>();
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		for (MudeTNotifica mudeTNotifica : notifiche) {
			//valorizzo i campi dal json(nullabile)
			String jsonData = mudeTNotifica.getJsonData();
			String destinatarioCognome = "";
			String destinatarioNome = "";
			String oggettoNotifica = "";
			if(StringUtils.isNotBlank(jsonData)) {
				try {
					jsonObject = (JSONObject) parser.parse(jsonData);
					oggettoNotifica = getNullableJsonString(jsonObject, OGGETTO);
					
				} catch (ParseException e) {
					logger.error("[MudeopenTNotificaServiceImpl::cercaNotificheInviate] EXCEPTION : " + e.getMessage(), e);
					oggettoNotifica = mudeTNotifica.getOggettoNotifica();	
				}
				
			}else {
				// JIRA 764
				if(StringUtils.isEmpty(oggettoNotifica))
					oggettoNotifica = mudeTNotifica.getOggettoNotifica();
			}
			
			// JIRA 794
			Date data_lettura = mudeRNotificaUtenteRepository.getReadDate(mudeTNotifica.getId());
			
	        MudeTContatto contattoPM = mudeTContattoRepository.findPMByIdIstanza(mudeTNotifica.getIstanza().getId(), -1L);
	        
	        if(contattoPM != null) {
    			destinatarioCognome = contattoPM.getCognome();
    			destinatarioNome = contattoPM.getNome();
	        }else {
	        	MudeTContatto contattoCreatoreFinal = new MudeTContatto();
	        	List<MudeTContatto> contattoCreatore = mudeTContattoRepository.findCreatoreByIdIstanza(mudeTNotifica.getIstanza().getId());

	        	if(contattoCreatore != null) {
	        		boolean bTrovato=false;
	        		String guid = "";
	        		for(MudeTContatto mudeTContatto :contattoCreatore) {
	        			if(mudeTContatto.getDataCessazione()==null) {
	        				bTrovato=true;
	        				destinatarioCognome = mudeTContatto.getCognome();
	            			destinatarioNome = mudeTContatto.getNome();
	            			contattoCreatoreFinal = mudeTContatto;
	        				break;
	        			}else {
	        				guid = mudeTContatto.getGuid();
	        			}
	        		}
	        		if(!bTrovato && guid != null && !guid.isEmpty()) {
	        			MudeTContatto contattoCreatoreModificato = mudeTContattoRepository.findCreatoreByIdIstanzaAndDataCessazioneNull(mudeTNotifica.getIstanza().getId(),guid);
	        			destinatarioCognome = contattoCreatoreModificato.getCognome();
            			destinatarioNome = contattoCreatoreModificato.getNome();
            			contattoCreatoreFinal = contattoCreatoreModificato;
	        		}
	        	}
	        }
			MudeDTipoNotifica mudeopenDTipoNotifica = mudeTNotifica.getTipoNotifica();
			String descrizioneTipoNotifica = Objects.isNull(mudeopenDTipoNotifica) ? "" :  mudeopenDTipoNotifica.getDesTipoNotifica();
			
			Boolean contieneAllegati = documentiNotificaRepository.areThereDocsForNotifica(mudeTNotifica.getId());
			
			// jira-495
			String codiceTipoNotifica = null;
			if("CAMBIO_STATO".equals(Objects.isNull(mudeopenDTipoNotifica) ? "" :  mudeopenDTipoNotifica.getCodTipoNotifica())) {
				codiceTipoNotifica = mudeRIstanzaStatoRepository.findLastActiveStateByIstanzaBeforeDate(idIstanza, mudeTNotifica.getDataInizio().toString()).getStatoIstanza().getDescrizione(); 
			}
			
			notificaInviataVOs.add(new NotificaInviataVO(mudeTNotifica.getId(), destinatarioCognome, destinatarioNome,
					mudeTNotifica.getDataInizio(), descrizioneTipoNotifica,
					codiceTipoNotifica, oggettoNotifica, data_lettura, contieneAllegati));
		}
		
		return notificaInviataVOs;
	}

	@Override
	public Optional<NotificaVO> visualizzaNotifica(String codiceIstanza, Long idNotifica) {
		Optional<MudeTNotifica> optional = mudeopenTNotificaRepository.findByCodiceIstanzaAndIdNotifica(codiceIstanza, idNotifica);
		if(optional.isPresent()) {
			MudeTNotifica mudeTNotifica = optional.get();
			//valorizzo i campi dal json(nullabile)
			String jsonData = mudeTNotifica.getJsonData();
			String destinatarioCognome = "";
			String destinatarioNome = "";
			String oggettoNotifica = "";
			String mittenteNotifica = "";
			String testoNotifica = "";
			String dettaglioNotifica = "";
			if(StringUtils.isNotBlank(jsonData)) {
				try {
					JSONParser parser = new JSONParser();
					JSONObject jsonObject;
					jsonObject = (JSONObject) parser.parse(jsonData);
					//destinatarioCognome = getNullableJsonString(jsonObject, COGNOME); 
					//destinatarioNome = getNullableJsonString(jsonObject, NOME); 
					oggettoNotifica = getNullableJsonString(jsonObject, OGGETTO); 					
					testoNotifica = getNullableJsonString(jsonObject, TESTO);
					
				} catch (ParseException e) {
					logger.error("[MudeopenTNotificaServiceImpl::cercaNotificheInviate]EXCEPTION : " + e.getMessage(), e);
					oggettoNotifica = mudeTNotifica.getOggettoNotifica();					
					testoNotifica = mudeTNotifica.getTestoNotifica();
				}
				
			}else {
				// JIRA 764
				if(StringUtils.isEmpty(oggettoNotifica))
					oggettoNotifica = mudeTNotifica.getOggettoNotifica();
				if(StringUtils.isEmpty(testoNotifica))
					testoNotifica = mudeTNotifica.getTestoNotifica();
			}
			dettaglioNotifica = mudeTNotifica.getDettaglio();
			mittenteNotifica = mudeTNotifica.getRuoloMittente();
			
	        MudeTContatto contattoPM = mudeTContattoRepository.findPMByIdIstanza(mudeTNotifica.getIstanza().getId(), -1L);
	        if(contattoPM != null) {
    			destinatarioCognome = contattoPM.getCognome();
    			destinatarioNome = contattoPM.getNome();
	        }else {
	        	List<MudeTContatto> contattoCreatore = mudeTContattoRepository.findCreatoreByIdIstanza(mudeTNotifica.getIstanza().getId());
	        	if(contattoCreatore != null) {
	        		boolean bTrovato=false;
	        		String guid = "";
	        		for(MudeTContatto mudeTContatto :contattoCreatore) {
	        			if(mudeTContatto.getDataCessazione()==null) {
	        				bTrovato=true;
	        				destinatarioCognome = mudeTContatto.getCognome();
	            			destinatarioNome = mudeTContatto.getNome();
	        				break;
	        			}else {
	        				guid = mudeTContatto.getGuid();
	        			}
	        		}
	        		if(!bTrovato && guid != null && !guid.isEmpty()) {
	        			MudeTContatto contattoCreatoreModificato = mudeTContattoRepository.findCreatoreByIdIstanzaAndDataCessazioneNull(mudeTNotifica.getIstanza().getId(),guid);
	        			destinatarioCognome = contattoCreatoreModificato.getCognome();
            			destinatarioNome = contattoCreatoreModificato.getNome();
	        		}
	        		
	        	}
	        }
			
			Boolean contieneAllegati = false;
			List<MudeRNotificaDocumento> documentiAllegati =  documentiNotificaRepository.findByMudeTNotifica(mudeTNotifica);
			if( documentiAllegati != null && documentiAllegati.size()>0)
				contieneAllegati = true;
			NotificaVO vo = new NotificaVO(mudeTNotifica.getId(), mittenteNotifica, destinatarioCognome, destinatarioNome, oggettoNotifica, testoNotifica, contieneAllegati,dettaglioNotifica);
			return Optional.of(vo);
		}
		return Optional.empty();
	}

	
	@Override
	public List<AllegatoNotificaVO> cercaAllegatiNotifica(String codiceIstanza, Long idNotifica){
		List<AllegatoNotificaVO> allegatoNotificaVOs = new ArrayList<>();
		Optional<MudeTNotifica> optional = mudeopenTNotificaRepository.findByCodiceIstanzaAndIdNotifica(codiceIstanza, idNotifica);
		if(optional.isPresent()) {
			List<MudeRNotificaDocumento> documentiAllegati =  documentiNotificaRepository.findByMudeTNotifica(optional.get());
			for (MudeRNotificaDocumento mudeRDocumentiNotifica : documentiAllegati) {
				MudeTDocumento documento = mudeRDocumentiNotifica.getMudeTDocumento();
				allegatoNotificaVOs.add(new AllegatoNotificaVO(documento.getTipoDocumento().getCodeTipoDocpa(), 
																documento.getTipoDocumento().getDescTipoDocpa(), 
																documento.getNomeFileDocumento(),
																documento.getDataCaricamento(), 
																documento.getId(), true));
			}
			
		}
		return allegatoNotificaVOs;
	}
	/**
	 * Metodo di convenienza per estrarre i valori dal jsonData, evitando stringhe valorizzate con "null"
	 * @param jsonObject
	 * @param fieldName
	 
	 */
	private String getNullableJsonString(JSONObject jsonObject, String fieldName) {
		return StringUtils.defaultString((String) jsonObject.get(fieldName));		
	}

	@Override
	public List<MudeTNotifica> cercaPerNumeroIstanza(String codiceIstanza) {
		return  mudeopenTNotificaRepository.findByCodiceIstanza(codiceIstanza);
	}

}
