/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTModello;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ModelloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTModelloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.EntiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ModelliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.DocumentTemplateManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.PdfService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloCompilatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;

@Component
public class ModelliServiceImpl implements ModelliService {
    protected static Logger logger = Logger.getLogger(ModelliServiceImpl.class.getCanonicalName());
    @Autowired
    protected MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeTModelloRepository mudeTModelloRepository;

    @Autowired
    private ModelloEntityMapper modelloEntityMapper;

    @Autowired
    private EntiService entiService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private DocumentTemplateManager documentTemplateManager;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Override
    public ModelloVO loadById(Long id) {
        Optional<MudeTModello> opt = mudeTModelloRepository.findByIdModello(id);
        return opt.map(mudeTModello -> modelloEntityMapper.mapEntityToVO(mudeTModello, null)).orElse(null);
    }

    @Override
    public ModelloVO loadBydenominazione(String denominazione) {
        Optional<MudeTModello> opt = mudeTModelloRepository.findByDenominazione(denominazione);
        return opt.map(mudeTModello -> modelloEntityMapper.mapEntityToVO(mudeTModello, null)).orElse(null);
    }

    @Override
    public List<ModelloVO> loadAll() {
        List<MudeTModello> list = mudeTModelloRepository.findAllModelli();
        return modelloEntityMapper.mapListEntityToListVO(list, null);
    }

    @Override
    public ModelloCompilatoVO loadTemplate(Long idIstanza, Long idTemplate) {
        ModelloCompilatoVO modulo = null;

        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        if (istanza == null) {
            throw new BusinessException("MudeTIstanza with id[" + idIstanza + "] null!");
        }
        Optional<MudeTModello> optTemplate = mudeTModelloRepository.findByIdModello(idTemplate);
        if (optTemplate.isEmpty()) {
            throw new BusinessException("MudeTModello with id[" + idTemplate + "] null!");
        }

        InputStream in = this.getTemplateFromIstanza(istanza.getId(), false);

        try {
            Map<String, Object> mappings = new HashMap<>();
            byte[] fileContent = pdfService.getCompiledTemplate(mappings, istanza, in);
            if (fileContent != null) {
                modulo = new ModelloCompilatoVO();
                modulo.setFilename(optTemplate.get().getPathModello());
                modulo.setFileContent(fileContent);
            }
        } catch (Exception e) {
            LoggerUtil.error(logger, "Error compiling template with id[" + idTemplate + "]! - " + e.getMessage());
            return null;
            //            throw new BusinessException("Error compiling template with id[" + idTemplate + "]!");
        }

        return modulo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ModelloCompilatoVO loadTemplatePDF(Long idIstanza, Long idModelloTemplate, MudeTUser mudeTUser, boolean saveAllFilesToDisk) {
        ModelloCompilatoVO modulo = null;

        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        if (istanza == null) {
            throw new BusinessException("MudeTIstanza with id[" + idIstanza + "] null!");
        }

        boolean isPdfIstanzaInCache = !saveAllFilesToDisk
        		&& !"local".equals(Constants._environment) 
        		&& !userUtil.isBackofficeAdminUser(mudeTUser)
        		&& mudeTIstanzaRepository.checkCachedPdfExists(istanza.getId());
        byte[] fileContent = null;
        Optional<MudeTModello> optModello = mudeTModelloRepository.findByIdModello(idModelloTemplate);
        if (!isPdfIstanzaInCache) {
            if (optModello.isEmpty()) {
                throw new BusinessException("MudeTModello with id[" + idModelloTemplate + "] null!");
            }
            InputStream in = this.getTemplateFromIstanza(istanza.getId(), saveAllFilesToDisk);
            try {
                Map<String, Object> mappings = new HashMap<>();
                MudeDComune comune = istanza.getMudeTFascicolo().getComune();
                mappings.put("comune", comune.getDenomComune());
                //fixme
                //            List<PPayImportoVO> tipoEnte = dictionaryService.search(null, "SUE", DictionaryTipeEnum.TIPO_ENTE.getDescription());
                //            String filter = "{\"descrizio\": {\"eq\": \"TENTE05SUE\"}}";
                //            List<PPayImportoVO> tipoEnte = dictionaryService.search(filter, "tipo_ente", null);
                //            if (tipoEnte.size() == 1) {
                //                String codiceTipoEnte = tipoEnte.get(0).getCodice();
                EnteVO ente = entiService.findActiveByComuneAndTipoEnte(comune.getIdComune(), "TENTE05SUE");
                if (null != ente) {
                    String indirizzoSueComune = ente.getIndirizzoEnte();
                    mappings.put("indirizzoSUE", indirizzoSueComune);
                }
                //            }

                String tipoIstanza = istanza.getTipoIstanza().getDescrizione();
                mappings.put("tipoIstanza", tipoIstanza);
                String numeroMudeIstanza = istanza.getCodiceIstanza();
                mappings.put("numeroMudeIstanza", numeroMudeIstanza);
                fileContent = pdfService.getCompiledTemplatePDF(mappings, istanza, in);
                //

            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("Error compiling modello template with id[" + idModelloTemplate + "] and modello id [" + optModello.get().getId() + ":" +optModello.get().getPathModello() + "]!");
            }
        } else {
            fileContent = mudeTIstanzaRepository.getCachedPdfIstanza(istanza.getId());
        }
        if (fileContent != null) {
            modulo = new ModelloCompilatoVO();
            String filename = optModello.get().getPathModello().substring(0, optModello.get().getPathModello().lastIndexOf('.'));
            modulo.setFilename(filename);
            modulo.setFileContent(fileContent);
        }

        return modulo;
    }

    private InputStream getTemplateFromIstanza(Long idIstanza, boolean saveAllFilesToDisk) {
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        if (istanza == null) {
            throw new BusinessException("MudeTIstanza with id[" + idIstanza + "] null!");
        }
        Long idTemplate = istanza.getTemplate().getIdTemplate();

        // if "modello" is present in the template table then use it
        if(!saveAllFilesToDisk 
        		&& istanza.getTemplate().getModello() != null 
        		&& istanza.getTemplate().getModello().getFileContent() != null)
        	return new ByteArrayInputStream(istanza.getTemplate().getModello().getFileContent());

        // otherwise recreate "WHOLE INSTANCE DOCX" and save it
        byte[] docTemplate = documentTemplateManager.buildDocumentTemplateForIstanza(istanza, idTemplate, saveAllFilesToDisk);

        try {
            if(istanza.getTemplate().getModello() != null) {
            	istanza.getTemplate().getModello().setFileContent(docTemplate);
            	istanza.getTemplate().getModello().setDimensioneFile(0l + docTemplate.length);
            	
            	mudeTModelloRepository.saveDAO(istanza.getTemplate().getModello());
            }
		} catch (Exception skip) { }

        return new ByteArrayInputStream(docTemplate);

    }

	@Override
	public byte[] renderQuadroPDFFromDocx(Long idIstanza, MultipartFormDataInput quadroInput, String options) {
		MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
		
        try {
            File file = quadroInput.getFormDataPart("file", File.class, null);
            return pdfService.getCompiledTemplatePDF(null, mudeTIstanza, new FileInputStream(file), options == null || options.indexOf("docx-format") == -1);
		} catch (Exception e) {
            throw new BusinessException("Impossibile compilare il file!");
		}
	}
	
	@Override
	public ModelloVO loadQuadroPDF(Long idIstanza, Long idQuadro) {
		MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
		MudeDQuadro mudeDQuadro = mudeDQuadroRepository.findOne(idQuadro);
		if(mudeDQuadro.getModello() == null)
            throw new BusinessException("Non esiste un docx associato a questo quadro. Eventualmente aggiungerlo dal pannello di controllo quadri.");

        try {
        	ModelloVO modelloVO = new ModelloVO();
        	modelloVO.setDenominazione(mudeDQuadro.getModello().getDenominazione());
        	modelloVO.setFileContent(pdfService.getCompiledTemplatePDF(null, mudeTIstanza, new ByteArrayInputStream(mudeDQuadro.getModello().getFileContent())));        		
			return modelloVO;
		} catch (Exception e) {
            throw new BusinessException("Impossibile compilare il file!");
		}
	}
	
	
}