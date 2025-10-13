/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;

@Component
public class DocumentTemplateManager {
    private static Logger logger = Logger.getLogger(DocumentTemplateManager.class.getCanonicalName());

    @Autowired
    private MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private MudeRTemplateTipoAllegatoRepository mudeRTemplateTipoAllegatoRepository;

    public byte[] buildDocumentTemplateForIstanza(Long idTemplate) {
        return buildDocumentTemplateForIstanza(null, idTemplate, false);
    }

    public byte[] buildDocumentTemplateForIstanza(MudeTIstanza istanza, Long idTemplate, boolean saveAllFilesToDisk) {
        List<MudeRTemplateQuadro> templateQuadroList = mudeRTemplateQuadroRepository.findByTemplate(idTemplate);
        List<byte[]> allDocsBytes = Lists.newArrayList();
        List<String> allDocsBytesNames = Lists.newArrayList();
        int counter = 0;

        MudeDTemplate template = mudeDTemplateRepository.findOne(idTemplate);
        if(template.getModelloIntestazione() != null) {
        	allDocsBytes.add(template.getModelloIntestazione().getFileContent());
        	
        	if(saveAllFilesToDisk)
        		allDocsBytesNames.add((counter++) + "-" + template.getModelloIntestazione().getId() + "_" + template.getModelloIntestazione().getDenominazione());
        }

        if(istanza != null) {
        	MudeRTemplateTipoAllegato mudeRTemplateTipoAllegato = mudeRTemplateTipoAllegatoRepository.findByTemplate_IdTemplateAndTipoAllegato_Codice(idTemplate, "ATT001");
	        if(mudeRTemplateTipoAllegato != null && mudeRTemplateTipoAllegato.getModello() != null) {
	        	allDocsBytes.add(mudeRTemplateTipoAllegato.getModello().getFileContent());
	        	
	        	if(saveAllFilesToDisk)
	        		allDocsBytesNames.add((counter++) + "-" + mudeRTemplateTipoAllegato.getModello().getId() + "_" + mudeRTemplateTipoAllegato.getModello().getPathModello());
	        }
        }

        for (MudeRTemplateQuadro tq : templateQuadroList) {
            MudeDQuadro quadro = tq.getMudeDQuadro();
            if(null != quadro.getModello()) {
                byte[] contentQuadro = quadro.getModello().getFileContent();
                allDocsBytes.add(contentQuadro);

	        	if(saveAllFilesToDisk)
	        		allDocsBytesNames.add((counter++) + "-" + quadro.getModello().getId() + "_" + quadro.getModello().getPathModello());
            }
            if ("c".equalsIgnoreCase(quadro.getFlgTipoGestione())) {
                String json = quadro.getJsonConfiguraQuadro();
                List<String> strings = this.getMatches(json, "\\~(.*?)\\~");
                List<Long> sottoquadriIdsList = strings.stream().map(Long::valueOf).collect(Collectors.toList());
                for (Long id : sottoquadriIdsList) {
                    MudeDQuadro sottoquadro = mudeDQuadroRepository.findOne(id);
                    if(null != sottoquadro.getModello()) {
                        byte[] contentSottoquadro = sottoquadro.getModello().getFileContent();
                        allDocsBytes.add(contentSottoquadro);

        	        	if(saveAllFilesToDisk)
        	        		allDocsBytesNames.add((counter++) + "-" + sottoquadro.getModello().getId() + "_" + sottoquadro.getModello().getPathModello());
                    }
                }
            }
        }

        /*
        try {
        	if(saveAllFilesToDisk) {  // debug only tool
        		Path baseDir = Paths.get("/tmp/templates/"+template.getMudeTipoIstanza().getCodice()+"/");
        		baseDir.toFile().mkdirs();
        		
        		for(int i = 0; i < allDocsBytes.size(); i++) {
        			FileOutputStream fout = new FileOutputStream(baseDir.toAbsolutePath() + "/" + allDocsBytesNames.get(i));
        			
        			fout.write(allDocsBytes.get(i));
        			fout.close();
        		}
        			
        		return null;
        	}
        	
        	//A questo livello is docx unico mergiato
            return pdfService.mergeDocuments(allDocsBytes);
        } catch (Docx4JException | IOException | JAXBException e) {
            //            e.printStackTrace();
            LoggerUtil.debug(logger, "ERRORE nella composizione del template : " + e.getMessage());
            return null;
        }
        */
        
        return null;
    }

    private List<String> getMatches(String s, String p) {
        // returns all matches of p in s for first group in regular expression
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile(p).matcher(s);
        while (m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }
}