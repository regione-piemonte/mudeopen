/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzeExtRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.JobsService;



@Service
public class JobsServiceImpl implements JobsService {
	private static Logger logger = Logger.getLogger(JobsServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
    
    @Autowired
    private MudeTIstanzaSlimRepository mudeTIstanzaSlimRepository;
    
    @Autowired
    private MudeTIstanzeExtRepository mudeTIstanzeExtRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private PlatformTransactionManager transactionManager;

	@Override
	public void copyInstanceJdataEntitiesToDb() {

		for(MudeTIstanzaSlim istanza : mudeTIstanzaSlimRepository.filterByDpsScriptStato("QUEUED")) {
			try {
				String dbCopyScript = mudeCProprietaRepository.getValueByNameAndInstanceVisibility("DPS_DATABASE_SCRIPT", istanza.getIdIstanza(), "\\y" + istanza.getIdTipoIstanza(), "");
				if(StringUtils.isBlank(dbCopyScript) || ecxecuteStatemant(istanza, dbCopyScript)) {
					mudeTIstanzeExtRepository.setDPSScriptState(istanza.getIdIstanza(), "OK");
					continue;
				}
				
				mudeTIstanzeExtRepository.setDPSScriptState(istanza.getIdIstanza(), "NA");
			} catch (BusinessException e) {
				logger.error("[CosmoServiceImpl::copyInstanceJdataEntitiesToDb] EXCEPTION! ", e);
				
				mudeTIstanzeExtRepository.setDPSScriptState(istanza.getIdIstanza(), "ERROR: " + e.getMessage());
			}

		}
	}

	private boolean ecxecuteStatemant(MudeTIstanzaSlim istanza, String dbCopyScript) throws BusinessException {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
		//definition.setTimeout(20);
		TransactionStatus status = transactionManager.getTransaction(definition);
		
		String currentStatement = "";
	    try {
			for(String updateSQL : dbCopyScript.split(";")) {
				if(updateSQL.replaceAll("[\r\n\t ]", "").length() == 0)
					continue;

				// alternative to char ; in case it's use is required within the script
				currentStatement = updateSQL.replace(":idIstanza", ""+istanza.getIdIstanza()).replace("§", ";"); 
				
				if(Pattern.compile("(?i)[ \\r\\n\\t]*SELECT[ \\t\\r\\n]+").matcher(currentStatement).find()) {
					Boolean res = (Boolean)entityManager.createNativeQuery(currentStatement).getSingleResult();
					if(!res)
						return false;
				}
				else if(Pattern.compile("(?i)[ \\r\\n\\t]*INSERT[ \\t\\r\\n]+INTO").matcher(currentStatement).find() 
						&& entityManager.createNativeQuery(currentStatement).executeUpdate() == 0)
					throw new Exception("ZERO record update for idIstanza=" + istanza.getIdIstanza() + " not allowed for SQL: " + updateSQL);
			}
			
	        transactionManager.commit(status);
	    } catch (Exception e) {
	        transactionManager.rollback(status);
			throw new BusinessException(e.getMessage() + " ["+currentStatement+"]");
	    }
	    
	    return true;
	}
	
}
