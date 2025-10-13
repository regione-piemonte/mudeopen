/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTModello;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ModelloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateResponseEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateQuadroResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateResponse;

@Component
public class TemplateResponseEntityMapperImpl implements TemplateResponseEntityMapper {

	@Autowired
	private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

	@Autowired
	private TipoQuadroEntityMapper tipoQuadroEntityMapper;

	@Autowired
	private ModelloEntityMapper modelloEntityMapper;

	@Override
	public List<TemplateResponse> mapListEntityToListVO(List<MudeRTemplateQuadro> tl) {
		return mapListEntityToListVO(tl, false);
	}
	
	@Override
	public List<TemplateResponse> mapListEntityToListVO(List<MudeRTemplateQuadro> tl, boolean forceActive) {

		Map<Long, List<MudeRTemplateQuadro>> result =
				tl.stream().collect(Collectors.groupingBy(x -> x.getMudeDTemplate().getIdTemplate()));

		List<TemplateResponse> response = new ArrayList<>();
		for (Map.Entry<Long, List<MudeRTemplateQuadro>> entry : result.entrySet())
			response.add(mapEntityToVO(entry, forceActive));

		return response;
	}

	@Override
	public TemplateResponse mapEntityToVO(Map.Entry<Long, List<MudeRTemplateQuadro>> entry) {
		return mapEntityToVO(entry, false);
	}
	
	@Override
	public TemplateResponse mapEntityToVO(Map.Entry<Long, List<MudeRTemplateQuadro>> entry, boolean forceActive) {
		if (entry == null)
			return null;

		MudeRTemplateQuadro tq = entry.getValue().get(0);
		MudeDTemplate template = tq.getMudeDTemplate();
		MudeDTipoIstanza tipoIstanza = template.getMudeTipoIstanza();
		MudeTModello modello =template.getModello();

		TemplateResponse vo = new TemplateResponse();

		vo.setNumeroVersione(template.getNumeroVersione());
		vo.setFlagAttivo(forceActive? 1L : template.getFlgAttivo());
		
		vo.setIdTemplate(template.getIdTemplate());
		vo.setCodTemplate(template.getCodTemplate());
		vo.setDesTemplate(template.getDesTemplate());
//		vo.setPdfTemplate(template.getPdfTemplate());
		vo.setJsonConfiguraTemplate(template.getJsonConfiguraTemplate());
		vo.setDataInizioValidita(template.getDataInizioValidita());
		vo.setTipoIstanza(tipoIstanzaEntityMapper.mapEntityToVO(tipoIstanza));
		vo.setModello(modelloEntityMapper.mapEntityToVO(modello, null));
		if(vo.getModello() != null)
			vo.getModello().setFileContent(null);
		
		vo.setPrevistoPM(template.getObbligatoriaNominaPM());

		List<TemplateQuadroResponse> quadri = new ArrayList<>();

		entry.getValue().forEach((v) -> {
			TemplateQuadroResponse tqVO = new TemplateQuadroResponse();
			tqVO.setIdTemplateQuadro(v.getIdTemplateQuadro());
			tqVO.setOrdinamentoTemplateQuadro(v.getOrdinamentoTemplateQuadro());
			tqVO.setFlgQuadroObbigatorio(BooleanUtils.toBoolean(v.getFlgQuadroObbigatorio()));
			MudeDQuadro mudeDQuadro = v.getMudeDQuadro();
			tqVO.setIdQuadro(mudeDQuadro.getIdQuadro());
			tqVO.setTipoQuadro(tipoQuadroEntityMapper.mapEntityToSlimVO(mudeDQuadro.getMudeDTipoQuadro(), null));
			tqVO.setNumVersione(mudeDQuadro.getNumVersione());
			tqVO.setJsonConfiguraQuadro(mudeDQuadro.getJsonConfiguraQuadro());
			tqVO.setFlgAttivo(template.getFlgAttivo());
			tqVO.setFlgTipoGestione(mudeDQuadro.getFlgTipoGestione());
			tqVO.setProprieta(v.getProprieta());
			
			quadri.add(tqVO);
		} );

		vo.setPrevistoPM(template.getObbligatoriaNominaPM());
		vo.setQuadri(quadri);
		return vo;
	}

}