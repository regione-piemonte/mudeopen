/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.mudeopen.mudeopensrvcommon.business.be.scheduled;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.CosmoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IdfService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.MailSenderService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.JobsService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.email.EmailVO;
@Component
public class ScheduledService  {

	protected static Logger logger = Logger.getLogger(ScheduledService.class);
	
	@Autowired
    private MailSenderService mailSenderService;
	
	@Autowired
    private MudeRNotificaContattoRepository mudeRNotificaContattoRepository;
	
	@Autowired
    private MudeTNotificaRepository mudeTNotificaRepository;
	
	@Autowired
    private MudeTContattoRepository mudeTContattoRepository; 
	
	@Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
	
	@Autowired
    private JobsService jobsService;
	
	@Autowired
    private CosmoService cosmoService;
	
	@Autowired
    private IdfService idfService;
	
	@Autowired
    private FascicoloSoggettoService fascicoloSoggettoService;
	
	private boolean checkIfRightNode(String pname) {
		InetAddress ip;
        String hostname="";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            //LoggerUtil.debug(logger, "[checkIfRightNode::hostname " + hostname + "]" );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String valueHostname = mudeCProprietaRepository.getValueByName(pname, "ALL");
        if(!"ALL".equalsIgnoreCase(valueHostname) && !hostname.equalsIgnoreCase(valueHostname))
        	return false;

        return true;
	}
	
	public void scheduledEmail() throws Throwable{
		if(!checkIfRightNode("MUDE_HOSTNAME_SEND_EMAIL"))
			return;
		
        LoggerUtil.debug(logger, "[scheduledEmail] STARTED");
		
		//leggere se ci sono messaggi da inviare 
		List<MudeRNotificaContatto> notificaContattoList = mudeRNotificaContattoRepository.findEmailToSend();
		String value = mudeCProprietaRepository.getValueByName("MUDE_EMAIL_NUM_MAX_RETRY", "0");
		int valueMaxRetry = Integer.parseInt(value);
		if (notificaContattoList.size()>0) {
			for (MudeRNotificaContatto notificaContatto : notificaContattoList) {
				Optional<MudeTNotifica> mudeTNotifica = mudeTNotificaRepository.findById(notificaContatto.getMudeTNotifica().getId());
				if (mudeTNotifica!=null) {
					MudeTContatto mudeTContatto =  mudeTContattoRepository.findOne(notificaContatto.getMudeTContatto().getId());
					
					if (mudeTContatto!=null && mudeTContatto.getMail()!=null) {
						String testoNotifica = notificaContatto.getBodyEmail();
						EmailVO emailVO =  new EmailVO(
							mudeTNotifica.get().getId(), null, mudeTContatto.getMail(), 
							mudeTNotifica.get().getOggettoNotifica(), testoNotifica);
						mailSenderService.sendMail(notificaContatto, emailVO, valueMaxRetry);
					}
				}
			} 

		}
    }
	
	
	public void scheduledFrontofficeJobs() throws Throwable{
		if(!checkIfRightNode("COSMO_ALLOWED_SCHEDULER_HOST"))
			return;

		// process store procs for instances
		jobsService.copyInstanceJdataEntitiesToDb();
		
		// COSMO queue handling
		cosmoService.processAllJobs();

		// IDF queue handling
		idfService.processAllJobs();

		// recvoery functions
		fascicoloSoggettoService.recoverMigrazioneSoggettiInFascicolo();
	}
	
}