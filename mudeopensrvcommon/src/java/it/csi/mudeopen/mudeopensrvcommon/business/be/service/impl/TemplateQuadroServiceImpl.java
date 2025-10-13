/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtenteQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateResponseEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneFunzioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TemplateQuadroService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.JDataIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateResponse;

@Service
public class TemplateQuadroServiceImpl implements TemplateQuadroService {

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    private QuadroEntityMapper quadroEntityMapper;

    @Autowired
    private TemplateQuadroEntityMapper templateQuadroEntityMapper;

    @Autowired
    private TemplateResponseEntityMapper templateResponseEntityMapper;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

    @Autowired
    private MudeRIstanzaUtenteQuadroRepository mudeopenRIstanzaUtenteQuadroRepository;

    @Autowired
    private ManagerAbilitazioni managerAbilitazioni;

    @Autowired
    private AbilitazioneFunzioneService abilitazioneFunzioneService;

    @Autowired
    private IstanzaStatoService istanzaStatoService;

    @Autowired
    UserUtil userUtil;

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private MudeRFascicoloIndirizzoRepository mudeRFascicoloIndirizzoRepository;

    @Override
    public TemplateQuadroVO loadTemplateQuadroById(Long idTemplateQuadro) {
        return templateQuadroEntityMapper.mapEntityToVO(mudeRTemplateQuadroRepository.findOne(idTemplateQuadro), null);
    }

    @Override
    public TemplateQuadroVO loadTemplateQuadroByIdTemplateIdQuadro(Long idTemplate, Long idQuadro) {
        MudeDTemplate template = new MudeDTemplate();
        template.setIdTemplate(idTemplate);

        MudeDQuadro quadro = new MudeDQuadro();
        quadro.setIdQuadro(idQuadro);
        return templateQuadroEntityMapper.mapEntityToVO(
                mudeRTemplateQuadroRepository.findByMudeDTemplateAndMudeDQuadro(template, quadro), null
        );
    }

    @Override
    public TemplateQuadroVO loadTemplateQuadroByIdTipoIstanza(String codiceTipoIstanza) {
        return templateQuadroEntityMapper.mapEntityToVO(mudeRTemplateQuadroRepository.findByMudeDTemplate_MudeTipoIstanza_Codice(codiceTipoIstanza), null);
    }

    @Override
    public TemplateQuadroVO loadTemplateQuadroByTipoIstanzaDesc(String descTipoIstanza) {
        return templateQuadroEntityMapper.mapEntityToVO(mudeRTemplateQuadroRepository.findByMudeDTemplate_MudeTipoIstanza_Descrizione(descTipoIstanza), null);
    }

    @Override
    public List<TemplateResponse> loadTemplateQuadroResponseByCodeTemplate(String idTipoIstanza, 
				    															String boTemplateOverride, 
				    															MudeTUser mudeTUser, 
				    															Long idIstanza) {
    	List<MudeRTemplateQuadro> quadri = null;
    	
    	if(!userUtil.isBackofficeAdminUser(mudeTUser)) {
        	String maxTemaplateVer = mudeCProprietaRepository.getValueByName("MAX-TEMPLATE-VERSION-"+idTipoIstanza, "");
        	if(!"".equals(maxTemaplateVer)) {
        		boolean isPubblico = false; 
        		boTemplateOverride = "99999"; // default max template version 
        		for(String token : maxTemaplateVer.split(",")) {
        			if(token.indexOf("=") == -1) continue;

        			if(token.split("=")[0].trim().indexOf(mudeTUser.getCf()) != -1) {
        				boTemplateOverride = token.split("=")[1].trim();
        				isPubblico = false;
        				break;
        			}

        			if(token.indexOf("PUBBLICO") != -1) {
        				isPubblico = true;
        				boTemplateOverride = token.split("=")[1].trim();
        			}
        		}
            			
        		return templateResponseEntityMapper.mapListEntityToListVO(mudeRTemplateQuadroRepository.findAllByTipoIstanza_idAndVersion(idTipoIstanza, Integer.parseInt(boTemplateOverride)), isPubblico);
        	}
        	else {
				// GET template from Istanza in order to retain older version if configured so
				if(idIstanza != null) {
		        	String keepTemaplateVer = mudeCProprietaRepository.getValueByName("RETAIN-TEMPLATE-VERSION-"+idTipoIstanza, "");
		        	if(!"".equals(keepTemaplateVer))
		        		quadri = mudeRTemplateQuadroRepository.getAllActiveByTipoIstanza_id(idIstanza);
				}
		        		
	    		if(quadri == null)
	    			quadri = mudeRTemplateQuadroRepository.findAllActiveByTipoIstanza_id(idTipoIstanza);
        	}
    	}
    	else if(boTemplateOverride == null || "".equals(boTemplateOverride)) 
			quadri = mudeRTemplateQuadroRepository.findAllByTipoIstanza_idAndMaxVersion(idTipoIstanza);
    	else
			quadri = mudeRTemplateQuadroRepository.findAllByTipoIstanza_idAndVersion(idTipoIstanza, Integer.parseInt(boTemplateOverride));
		
		return templateResponseEntityMapper.mapListEntityToListVO(quadri);
    }

    public QuadroVO loadQuadroById(Long idQuadro, Long idIstanza, Long idFascicolo, HttpHeaders httpHeaders) {
    	MudeDQuadro mudeDQuadro = mudeDQuadroRepository.findOne(idQuadro);
    	if(mudeDQuadro == null) return null;
    	
    	QuadroVO vo = quadroEntityMapper.mapEntityToSlimVO(mudeDQuadro, null);
    	
    	if(idFascicolo != null)
    		vo.setFlgModificabile(managerAbilitazioni.canUserChangeQuadro(false, idIstanza, null,
    				httpHeaders,
    				mudeDTemplateRepository.findTemplateForIstanza(idIstanza).getObbligatoriaNominaPM(), 
    				mudeDQuadro.getIdQuadro(), mudeDQuadro.getMudeDTipoQuadro().getCodTipoQuadro()));

    	return vo;
    }

    // used to return essential instance information (just jdata/abilitazioni) to the client
	@Override
	public JDataIstanzaVO loadJDataIstanza(Long idIstanza,
										Long idQuadro,
										String codTipoQuadro,
										Boolean isObbligatoriaNominaPM,
										String requestType,
										HttpHeaders httpHeaders, MudeTUser mudeTUser) {
		JDataIstanzaVO vo = new JDataIstanzaVO();
		
		vo.setIdIstanza(idIstanza);
        vo.setStatoIstanza(istanzaStatoService.getStatoIstanza(idIstanza));

        if("update-address".equals(requestType))
        	vo.setNuovoIndirizzo(mudeRFascicoloIndirizzoRepository.isIstanzaAddressNotUpdated(idIstanza));
        else if(StringUtils.isNotBlank(requestType)) {
        	String extraColumnsQuery = mudeCProprietaRepository.getValueByName(requestType, "");
        	if(StringUtils.isNotBlank(extraColumnsQuery)) {
	        	extraColumnsQuery = makeSafeQueryAliases(extraColumnsQuery, idIstanza);
	        	try {
	        		List<String> lst = entityManager.createNativeQuery(extraColumnsQuery.replace(":", "\\:")).getResultList();
	        		if(lst.size() > 0)
		        		vo.setJsonData(lst.get(0));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }

		// load full data?
    	if(idQuadro != null) {
    		vo.setIdQuadro(idQuadro);
    		
    		// return the all jdata as string
    		vo.setJsonData(mudeTIstanzaRepository.getJDataByIdIstanza(idIstanza));
    		
        	if(codTipoQuadro != null && isObbligatoriaNominaPM != null) {
        		vo.setCodTipoQuadro(codTipoQuadro);
        		vo.setIsObbligatoriaNominaPM(isObbligatoriaNominaPM);
        		
        		// returns all the currently permissions assigned to the user
            	vo.setAbilitazioni(abilitazioneFunzioneService.loadFunzioniAbilitazioniByIdIstanzaAndIdUser(idIstanza, mudeTUser.getIdUser()));

	    		// returns all the currently permissions assigned to the user
				vo.setQuadroModificabile(managerAbilitazioni.canUserChangeQuadro(false, idIstanza, null,
						httpHeaders,
						isObbligatoriaNominaPM, 
						idQuadro, 
						codTipoQuadro));
        	}
    	}
    	
		return vo;
	}
	
	@Override
	public List<QuadroVO> retrieveAllQuadriInTemplate(Long idTemplate, Long idIstanza, Long idUser, Boolean includeJson) {
		List<MudeRIstanzaUtenteQuadro> quadriAssociati = null;
		if(idUser != null)
			quadriAssociati = mudeopenRIstanzaUtenteQuadroRepository.findAllByMudeRIstanzaUtente_Istanza_IdAndMudeRIstanzaUtente_Utente_IdUserAndDataFineIsNull(idIstanza, idUser);
		
		List<QuadroVO> list = new ArrayList<QuadroVO>();
		for(MudeRTemplateQuadro templateQuadro : mudeRTemplateQuadroRepository.findAllVirtualTemplateQuadroByIdTipoQuadro(-1L, idTemplate)) {
			QuadroVO quadro = quadroEntityMapper.mapEntityToVO(templateQuadro.getMudeDQuadro(), null, "no-model");
			list.add(quadro);

			// if idQuadroParent different than idQuadro
			if(templateQuadro.getIdTemplateQuadro() / quadro.getIdQuadro() != quadro.getIdQuadro()) {
				quadro.setIdQuadroParent(templateQuadro.getIdTemplateQuadro() / quadro.getIdQuadro());
			
				if(includeJson == null || !includeJson) { 
					// removes all unused JSON from sub-quadro items
					quadro.setJsonConfiguraQuadro(null);
					quadro.setJsonDefault(null);
				}
			}
			
			quadro.setFlgModificabile(false);
			if(quadriAssociati != null)
				for(MudeRIstanzaUtenteQuadro quadroAssociato : quadriAssociati)
					if(templateQuadro.getMudeDQuadro().getIdQuadro().equals(quadroAssociato.getMudeDQuadro().getIdQuadro()))
						quadro.setFlgModificabile(true); // indicates the quadro is modifiable from idUser
		}
		
		return list;
	}
	
	private String makeSafeQueryAliases(String query, Long idIstanza) {
		String rx = " AS[ ]*\"([^\"]+)\"";
		
		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile(rx);
		Matcher m = p.matcher(query);
		
		while (m.find()) {
			String found = m.group(1);
	        m.appendReplacement(sb, " AS \"" + found.replaceAll("[^a-zA-Z0-9]", "_") + "\"");
		}
		m.appendTail(sb);

		return "SELECT CAST(ROW_TO_JSON(tblQuery) AS TEXT) FROM(" + sb.toString().replace(":IDISTANZA", idIstanza+"") + " WHERE tblIstanza.id_istanza = " + idIstanza + ") tblQuery";
	}

}