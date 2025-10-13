/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import com.google.common.collect.Maps;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTLogTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTLogTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.LogTracciatoService;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.Map;

@Service
public class LogTracciatoServiceImpl implements LogTracciatoService {
    private static Logger logger = Logger.getLogger(LogTracciatoServiceImpl.class.getCanonicalName());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MudeTLogTracciatoRepository mudeTLogTracciatoRepository;

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Override
    public boolean saveLogTracciato(MudeTUser user, MudeTIstanza istanza, MudeDTipoTracciato tipoTracciato, String tipoErrore, String errore, String jsondataAsXml, String usedXslt, String generatedXml) {
        Boolean mailSent = null;
        try {
            Map<String, byte[]> attachments = Maps.newHashMapWithExpectedSize(3);
            if (StringUtils.isNotBlank(jsondataAsXml))
                attachments.put("jsondata_as_xml.xml", jsondataAsXml.getBytes());
            if (StringUtils.isNotBlank(usedXslt))
                attachments.put("xslt_template_principale.xml", usedXslt.getBytes());
            if (StringUtils.isNotBlank(generatedXml))
                attachments.put("xml_generato_tracciato.xml", generatedXml.getBytes());

            this.sendMail(istanza.getId(), tipoTracciato.getCodice(), errore, attachments);
            //sendMail(istanza.getId(), tipoTracciato, tipoErrore, errore);
            mailSent = Boolean.TRUE;
        } catch (Throwable t) {
            LoggerUtil.error(logger, "Error sending email :  " + t.getMessage());
            mailSent = Boolean.FALSE;
        }

        MudeTLogTracciato logTracciato = new MudeTLogTracciato();
        logTracciato.setMudeTIstanza(istanza);
        logTracciato.setMudeDTipoTracciato(tipoTracciato);
        logTracciato.setTipoErrore(tipoErrore);
        logTracciato.setErrore(errore);
        logTracciato.setMudeTUser(user);
        logTracciato.setErrorTime(Calendar.getInstance().getTime());
        logTracciato.setMailInviata(mailSent);
        logTracciato.setJsondataAsXML(jsondataAsXml);
        logTracciato.setUsedXslt(usedXslt);
        logTracciato.setGeneratedXML(generatedXml);
        mudeTLogTracciatoRepository.saveDAO(logTracciato);

        return false;
    }

    private void sendMail(Long idIstanza, String tipoTracciato, String errore, Map<String, byte[]> attachments) {
        try {
            String mittente = mudeCProprietaRepository.getValueByName("MUDE_MAIL_ECCEZIONIXML_MITTENTE", "");
            String destinatario = mudeCProprietaRepository.getValueByName("MUDE_MAIL_ECCEZIONIXML_INDIRIZZO", "");
            String oggetto = mudeCProprietaRepository.getValueByName("MUDE_MAIL_VALIDAZIONE_OGGETTO", "Errore nella generazione del tracciato") + " [" + idIstanza + "]";
            StringBuilder messaggio = new StringBuilder("Si è verificato un errore per il tracciato [");
            if (StringUtils.isNotBlank(tipoTracciato))
                messaggio.append(tipoTracciato);
            messaggio.append("] \n\r").append(errore);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(destinatario);
            helper.setSubject(oggetto);
            helper.setText(messaggio.toString());
            helper.setFrom(mittente);
            if (null != attachments && !attachments.isEmpty()) {
                for (String s : attachments.keySet()) {
                    helper.addAttachment(s, new ByteArrayResource(attachments.get(s)));
                }
            }
            mailSender.send(message);
        } catch (MailException | MessagingException mex) {
            LoggerUtil.error(logger, mex.getMessage());
        }
    }

}