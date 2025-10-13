/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.NazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProvinciaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RegioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDNazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRegioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.LuoghiService;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.RegioneVO;

@Service
public class LuoghiServiceImpl implements LuoghiService {

	private static Logger logger = Logger.getLogger(LuoghiServiceImpl.class.getCanonicalName());
	@Autowired
	private MudeDComuneRepository mudeDComuneRepository;
	@Autowired
	private MudeDNazioneRepository mudeDNazioneRepository;
	@Autowired
	private MudeDProvinciaRepository mudeDProvinciaRepository;
	@Autowired
	private MudeDRegioneRepository mudeDRegioneRepository;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;

	@Override
	public List<RegioneVO> getRegioni() {
		List<RegioneVO> ret = new ArrayList<RegioneVO>(regioneEntityMapper.mapListEntityToListVO((List<MudeDRegione>) mudeDRegioneRepository.findAllByFineValiditaOrderByDenomRegioneAsc()));

		return ret;
	}

	@Override
	public List<ProvinciaVO> getProvinceByIdRegione(Long idRegione) {

		MudeDRegione mudeDRegione = mudeDRegioneRepository.findOne(idRegione);
		if (mudeDRegione == null)
			return null;
		List<ProvinciaVO> ret = new ArrayList<ProvinciaVO>(provinciaEntityMapper.mapListEntityToListVO(mudeDProvinciaRepository.findByMudeDRegioneAndFineValiditaOrderByDenomProvinciaAsc(mudeDRegione)));
		return ret;
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(String filters) {
		
		Long idProvincia = FilterUtil.getLongValue(filters, "idProvincia");
		Boolean includeFiscal = FilterUtil.getBooleanValue(filters, "includeFiscal");

		MudeDProvincia mudeDProvincia = mudeDProvinciaRepository.findOne(idProvincia);
		if (mudeDProvincia == null)
			return null;
		
		List<MudeDComune> mudeDComuni;
		if(includeFiscal != null && includeFiscal == true)
			mudeDComuni = mudeDComuneRepository.findComuniFiscalmenteValidi(mudeDProvincia.getIdProvincia());
		else
			mudeDComuni = mudeDComuneRepository.findByMudeDProvinciaAndFineValiditaOrderByDenomComuneAsc(mudeDProvincia);
		
		List<ComuneVO> ret = new ArrayList<ComuneVO>(comuneEntityMapper.mapListEntityToListVO(mudeDComuni));

		return ret;
	}

	@Override
	public List<NazioneVO> getNazioni() {

		List<NazioneVO> ret = new ArrayList<NazioneVO>(nazioneEntityMapper.mapListEntityToListVO((List<MudeDNazione>) mudeDNazioneRepository.findAllByFineValiditaOrderByDenomNazioneAsc()));

		return ret;
	}

	@Override
	public List<NazioneVO> getStatiMembriUE() {

		List<NazioneVO> ret = new ArrayList<NazioneVO>(nazioneEntityMapper.mapListEntityToListVO((List<MudeDNazione>) mudeDNazioneRepository.findByStatoMembroUETrueOrderByCodIstatNazioneAsc()));

		return ret;
	}

	@Override
	public List<RegioneVO> getRegioniByIdNazione(Long idNazione) {
		MudeDNazione mudeDNazione = mudeDNazioneRepository.findOne(idNazione);
		List<RegioneVO> ret = new ArrayList<RegioneVO>(regioneEntityMapper.mapListEntityToListVO((List<MudeDRegione>) mudeDRegioneRepository.findByMudeDNazioneOrderByDenomRegioneAsc(mudeDNazione)));

		return ret;
	}

	@Override
	public List<ProvinciaVO> getProvince() {

		
		List<ProvinciaVO> ret = new ArrayList<ProvinciaVO>(provinciaEntityMapper.mapListEntityToListVO((List<MudeDProvincia>) mudeDProvinciaRepository.findByFineValiditaOrderByDenomProvinciaAsc()));
//		List<ProvinciaVO> ret = new ArrayList<ProvinciaVO>(provinciaEntityMapper.mapListEntityToListVO((List<MudeDProvincia>) mudeDProvinciaRepository.findAllByOrderByDenomProvinciaAsc()));

		return ret;
	}

	@Override
	public List<SelectVO> findProvinceForComuniRegistered() {
		List<MudeDProvincia> entities = mudeDProvinciaRepository.findProvinciaForComuniRegistered();
		List<SelectVO> province = Lists.newArrayListWithCapacity(entities.size());
		for (MudeDProvincia entity : entities) {
			SelectVO sVO = new SelectVO();
			sVO.setId(entity.getIdProvincia());
			sVO.setDescrizione(entity.getDenomProvincia());
			province.add(sVO);
		}
		return province;
	}

	@Override
	public List<SelectVO> findComuniRegisteredForProvincia(Long idProvincia) {
		List<MudeDComune> entities = mudeDComuneRepository.findComuniRegisteredForProvincia(idProvincia);
		List<SelectVO> comuni = Lists.newArrayListWithCapacity(entities.size());
		for (MudeDComune entity : entities) {
			SelectVO sVO = new SelectVO();
			sVO.setId(entity.getIdComune());
			sVO.setDescrizione(entity.getDenomComune());
			comuni.add(sVO);
		}
		return comuni;
	}
}