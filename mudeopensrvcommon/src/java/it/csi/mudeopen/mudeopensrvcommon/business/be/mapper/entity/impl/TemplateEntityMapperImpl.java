/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ModelloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTModelloRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;

@Component
public class TemplateEntityMapperImpl implements TemplateEntityMapper {

	@Autowired
	private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

	@Autowired
	private ModelloEntityMapper modelloEntityMapper;
	
	@Autowired
	private MudeDTemplateRepository mudeDTemplateRepository;
	
	@Autowired
	MudeTIstanzaRepository mudeTIstanzaRepository;

	@Autowired
	MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

	@Autowired
	private MudeTModelloRepository mudeTModelloRepository;

	@Override
	public TemplateVO mapEntityToVO(MudeDTemplate dto, MudeTUser user) {
		if(dto == null)
			return null;

		TemplateVO vo = new TemplateVO();
		vo.setIdTemplate(dto.getIdTemplate());
		vo.setCodTemplate(dto.getCodTemplate());
		vo.setDesTemplate(dto.getDesTemplate());
//		vo.setPdfTemplate(dto.getPdfTemplate());
		vo.setFlgAttivo(dto.getFlgAttivo());
		vo.setDataCessazione(convertDate2String(dto.getDataCessazione()));
		vo.setDataInizioValidita(convertDate2String(dto.getDataInizioValidita()));
		vo.setJsonConfiguraTemplate(dto.getJsonConfiguraTemplate());
		vo.setNumeroVersione(dto.getNumeroVersione());
		vo.setObbligatoriaNominaPM(dto.getObbligatoriaNominaPM());
		
		if(null != dto.getMudeTipoIstanza())
			vo.setTipoIstanza(tipoIstanzaEntityMapper.mapEntityToVO(dto.getMudeTipoIstanza()));
			
		if(null != dto.getModello()) {
			vo.setModello(modelloEntityMapper.mapEntityToVO(dto.getModello(), null));
			vo.getModello().setFileContent(null);
		}

		if(null != dto.getModelloIntestazione()) {
			vo.setModelloIntestazione(modelloEntityMapper.mapEntityToVO(dto.getModelloIntestazione(), null));
			vo.getModelloIntestazione().setFileContent(null);
		}

		List<MudeDTemplate> templateListDesc = mudeDTemplateRepository.findAllByMudeTipoIstanza_CodiceOrderByNumeroVersioneDesc(dto.getMudeTipoIstanza().getCodice());
		if(templateListDesc.size() > 0 && templateListDesc.get(0).getIdTemplate() == dto.getIdTemplate())
			vo.setUltimaVersione(true);

		// List<MudeTIstanza> istanzeCollegate = mudeTIstanzaRepository.findByTemplate_IdTemplate(dto.getIdTemplate());
		// vo.setModificabile(istanzeCollegate == null || istanzeCollegate.size() == 0);
		vo.setModificabile(vo.isUltimaVersione() && dto.getFlgAttivo() == 0);
		
		return vo;
	}
	
    @Override
    public MudeDTemplate mapVOtoEntity(TemplateVO vo, MudeTUser user) {
    	MudeDTemplate dto;
    	
    	if(vo.getIdTemplate() == null)
        	dto = new MudeDTemplate();
    	else
    		dto = mudeDTemplateRepository.findOne(vo.getIdTemplate());
    	
		dto.setFlgAttivo(vo.getFlgAttivo());
		dto.setCodTemplate(vo.getCodTemplate());
		dto.setDesTemplate(vo.getDesTemplate());
		dto.setDataInizioValidita(convertStrinf2Date(vo.getDataInizioValidita()));
		dto.setDataCessazione(convertStrinf2Date(vo.getDataCessazione()));
		dto.setJsonConfiguraTemplate(vo.getJsonConfiguraTemplate());
		dto.setNumeroVersione(vo.getNumeroVersione());
		dto.setObbligatoriaNominaPM(vo.getObbligatoriaNominaPM());
		
		if(vo.getModello() != null && vo.getModello().getId() != null)
			dto.setModello(vo.getModello().getDimensioneFile() == -1 /* delete */ ? null : mudeTModelloRepository.findOne(vo.getModello().getId()));
		
		if(vo.getModelloIntestazione() != null && vo.getModelloIntestazione().getId() != null)
			dto.setModelloIntestazione(vo.getModelloIntestazione().getDimensioneFile() == -1 /* delete */ ? null : mudeTModelloRepository.findOne(vo.getModelloIntestazione().getId()));
		
		if(vo.getTipoIstanza() != null)
			dto.setMudeTipoIstanza(mudeDTipoIstanzaRepository.findOne(vo.getTipoIstanza().getId()));

    	return dto;
    }

    static private SimpleDateFormat italianDateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	static public Date convertStrinf2Date(String dateIn) {
		try {
			return italianDateFormat.parse(dateIn);	
		} catch (Exception skip) { }
	    
		return null;
	}

	static public String convertDate2String(Date dateIn) {
		try {
		    return italianDateFormat.format(dateIn);
		} catch (Exception skip) { }
		
		return null;
	}
}