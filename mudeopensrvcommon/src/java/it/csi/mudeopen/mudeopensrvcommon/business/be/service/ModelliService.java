/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloCompilatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;

public interface ModelliService {

    ModelloVO loadById(Long id);

    ModelloVO loadBydenominazione(String denominazione);

    List<ModelloVO> loadAll();

    @Transactional(propagation = Propagation.REQUIRED)
    ModelloCompilatoVO loadTemplatePDF(Long idIstanza, Long idModelloTemplate, MudeTUser mudeTUser, boolean saveAllFilesToDisk);

    ModelloCompilatoVO loadTemplate(Long idIstanza, Long idTemplate);

    byte[] renderQuadroPDFFromDocx(Long idIstanza, MultipartFormDataInput quadroInput, String options);

    ModelloVO loadQuadroPDF(Long idIstanza, Long idQuadro);

}