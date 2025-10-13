/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.MailSenderService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.email.EmailStatus;
import it.csi.mudeopen.mudeopensrvcommon.vo.email.EmailVO;
@Service
public class MailSenderServiceImpl implements MailSenderService {
	
	private static Logger logger = Logger.getLogger(MailSenderServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private JavaMailSenderImpl mailSenderImpl;
	
	
	@Autowired
	private MudeRNotificaContattoRepository mudeRNotificaContattoRepository;
	
    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

	@Value("${mail.from}")
	private String from;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	@Override
	public void sendMail(MudeRNotificaContatto mudeRNotificaContatto, EmailVO emailVO, int valueMaxRetry) throws Throwable {
		

		
//		MimeMessage message = mailSender.createMimeMessage();
//		message.setSubject(emailVO.getObject());
//		message.setText(emailVO.getBody());
//		
//		message.setRecipients(Message.RecipientType.TO, emailVO.getTo());
//		
//		message.setFrom(new InternetAddress(from, "MUDE - CSI Piemonte"));
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailVO.getTo());
        message.setSubject(emailVO.getObject());
        message.setText(emailVO.getBody());
        //message.setFrom(from);
        // JIRA 777
        String mittente = mudeCProprietaRepository.getValueByName("MUDE_MAIL_ECCEZIONIXML_MITTENTE", from);
        message.setFrom(mittente);

		try {
			mudeRNotificaContatto.setEmailStatus(EmailStatus.SENDING.getStatus());

			String host = mudeCProprietaRepository.getValueByName("MUDE_MAILSENDER_HOST", "");
			if(StringUtils.isNotBlank(host)) {
				mailSenderImpl.setHost(host);
				mailSenderImpl.setPort(Integer.parseInt(mudeCProprietaRepository.getValueByName("MUDE_MAILSENDER_PORT", "25")));
				
				String protocol = mudeCProprietaRepository.getValueByName("MUDE_MAILSENDER_PROTOCOL", "");
				if(StringUtils.isNotBlank(protocol))
					mailSenderImpl.setProtocol(protocol);
					
				String username = mudeCProprietaRepository.getValueByName("MUDE_MAILSENDER_USERNAME", "");
				String password = mudeCProprietaRepository.getValueByName("MUDE_MAILSENDER_PASSSWORD", "");
				if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
					mailSenderImpl.setUsername(username);
					mailSenderImpl.setPassword(password);
				}
				
				mailSenderImpl.send(message);
			}
			else
				mailSender.send(message);
			
			mudeRNotificaContatto.setEmailStatus(EmailStatus.SENT.getStatus());
			mudeRNotificaContatto.setDataInvio(new Date());
			mudeRNotificaContatto.setDataLastRetry(new Date());
			mudeRNotificaContattoRepository.saveDAO(mudeRNotificaContatto);
			
		}catch(Throwable t) {
			LoggerUtil.error(logger, "[MailSenderServiceImpl::sendMail] problem sending mailt : " + t.getMessage(), t);
			if (mudeRNotificaContatto.getEmailNumRetry()<valueMaxRetry) {
				int emailNumRetry = mudeRNotificaContatto.getEmailNumRetry() + 1; 
				mudeRNotificaContatto.setEmailNumRetry(emailNumRetry);
				mudeRNotificaContatto.setEmailStatus(EmailStatus.IN_RETRY.getStatus());
				mudeRNotificaContatto.setDataLastRetry(new Date());
			}
			else {
				mudeRNotificaContatto.setEmailStatus(EmailStatus.FAILED.getStatus());
				mudeRNotificaContatto.setDataLastRetry(new Date());
			}
			mudeRNotificaContattoRepository.saveDAO(mudeRNotificaContatto);

			Integer maxSize = 499;
			String info = t.getMessage();
			if(info.length() > maxSize ){
				info = info.substring(0, maxSize);
			}
		}
	}
}