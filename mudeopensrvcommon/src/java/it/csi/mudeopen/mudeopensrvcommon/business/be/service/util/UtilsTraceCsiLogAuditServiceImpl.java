/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;
import java.time.LocalDateTime;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.CsiLogAuditRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;

@Service
public class UtilsTraceCsiLogAuditServiceImpl implements UtilsTraceCsiLogAuditService {
	
	private static final Logger logger = Logger.getLogger(UtilsTraceCsiLogAuditServiceImpl.class.getCanonicalName());

	@Autowired
	private PropertyUtil propertyUtil;

	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;	

	@Autowired
	private UtilsDate utilsDate;
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void traceCsiLogAudit(String operazione, String tabella, String key, String caller, String dettaglioOperazione) {
	
		try {
			String ip = null;
			
			try{
				ip = (String) MDC.get(Constants.X_FORWARDED_FOR);
			}catch(Throwable t) {
				LoggerUtil.error(logger, "Errore di sbaglio. " + t);
			}
			
			CsiLogAudit csiLogAudit = new CsiLogAudit();
			csiLogAudit.setDataOra(utilsDate.asTimeStamp(LocalDateTime.now()));
			csiLogAudit.setIdApp(getIdAppToTrace());
			csiLogAudit.setIpAddress(ip);
			
			csiLogAudit.setUtente(caller);
		
			String operazioneDb = operazione + (StringUtils.isBlank(dettaglioOperazione)?"":  " - " + dettaglioOperazione);	
			csiLogAudit.setOperazione(operazioneDb);	
			
			if(tabella.indexOf('_') == -1)
				tabella = String.join("_", tabella.split("(?=\\p{Upper})"));

			csiLogAudit.setOggOper(tabella);
			csiLogAudit.setKeyOper(key);
			
			csiLogAuditRepository.saveDAO(csiLogAudit);
			
			LoggerUtil.debug(logger, "Operazione [" + operazioneDb + "] tracciata correttamente");
		}
		catch (Exception e) {
			LoggerUtil.error(logger, "Impossibile tracciare l'operazione. " + e);
		}	

		
	}

    public String getIdAppToTrace() {
		StringBuilder idApp = new StringBuilder();
		try {
		idApp.append(propertyUtil.getPropertyValue(PropertyUtil.CODICE_PRODOTTO_ID).getValore() + "_");
		idApp.append(propertyUtil.getPropertyValue(PropertyUtil.CODICE_LINEA_CLIENTE_ID).getValore() + "_");
		idApp.append(propertyUtil.getPropertyValue(PropertyUtil.CODICE_AMBIENTE_ID).getValore() + "_");
		idApp.append(propertyUtil.getPropertyValue(PropertyUtil.CODICE_UNITA_INSTALLAZIONE_ID).getValore());		
		} catch (Exception e) {
			LoggerUtil.error(logger, "Impossibile reperire l'id_app");			
		}
		return idApp.toString();
	}

}