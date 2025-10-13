/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TemplateService;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private MudeDTemplateRepository mudeDTemplateRepository;

	@Autowired
	private TemplateEntityMapper templateEntityMapper;

	@Override
	public List<TemplateVO> loadTemplateAttivi() {
		return templateEntityMapper.mapListEntityToListVO(mudeDTemplateRepository.findAllByFlgAttivo(1L), null);
	}

	@Override
	public TemplateVO loadTemplateById(Long idTemplate) {
		return templateEntityMapper.mapEntityToVO(mudeDTemplateRepository.findOne(idTemplate), null);
	}

	@Override
	public TemplateVO loadTemplateByCode(String codTemplate) {
		// TO BE DISMISSED: this query can have multiple results
		return templateEntityMapper.mapEntityToVO(mudeDTemplateRepository.findByCodTemplate(codTemplate), null);
	}

	@Override
	public TemplateVO loadTemplateByIdTipoistanza(String codiceTipoIstanza) {
		MudeDTipoIstanza tipoIstanza = new MudeDTipoIstanza();
		tipoIstanza.setCodice(codiceTipoIstanza);
		return templateEntityMapper.mapEntityToVO(mudeDTemplateRepository.findByMudeTipoIstanza(tipoIstanza), null);
	}

	@Override
	public TemplateVO loadTemplateByTipoistanzaDesc(String descTipoIstanza) {
		MudeDTipoIstanza tipoIstanza = new MudeDTipoIstanza();
		tipoIstanza.setDescrizione(descTipoIstanza);
		return templateEntityMapper.mapEntityToVO(mudeDTemplateRepository.findByMudeTipoIstanza(tipoIstanza), null);
	}

	@Override
	public Long saveTemplate(TemplateVO template) {
		return null;
	}

	@Override
	public int updateTemplate(TemplateVO template) {
		return 0;
	}

	@Override
	public void deleteTemplate(Long idTemplate) {
		throw new UnsupportedOperationException();
	}
}