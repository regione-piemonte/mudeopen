/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTLogNumeriMude;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeTLogNumeriMudeRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeTLogNumeriMudeService;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDFruitoreService;
import it.csi.mudeopen.mudeopensrvapi.vo.enumeration.TipoLogNumeriMude;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.CodeGenerator;
@Service
public class MudeopenTLogNumeriMudeServiceImpl implements MudeTLogNumeriMudeService {
	@Autowired
	private MudeTLogNumeriMudeRepository logNumeriMudeRepository;
	
	@Autowired
	private MudeopenDFruitoreService mudeopenDFruitoreService;
	
	@Override
	public Long getIdByNumeroTipoCodiceFruitore(String numeroMude, 
															String tipo,
															String codiceFruitore) {
		MudeTLogNumeriMude mudeTLogNumeriMude = logNumeriMudeRepository.findByNumeroTipoCodiceFruitore(numeroMude, tipo, codiceFruitore);
		if(mudeTLogNumeriMude == null)
			return null;
		
		if(mudeTLogNumeriMude.getNumeroMude().indexOf("-") == -1)
			return Long.parseLong(mudeTLogNumeriMude.getNumeroMude().substring(8, 18));
		
		return Long.parseLong(mudeTLogNumeriMude.getNumeroMude().replaceFirst("[0-9]+-[0-9]+-([0-9]+)-[0-9]+", "$1")); 
	}
	
	@Transactional
	@Override
	public String getNewNumeroMudeIstanza(MudeDComune mudeDComune, MudeDFruitore fruitore) {
		
		String newCodiceIstanza = CodeGenerator.getNewCodiceIstanza(logNumeriMudeRepository.getNextIdNumeroMude(), mudeDComune);
		save(newCodiceIstanza, TipoLogNumeriMude.ISTANZA.getCode(), fruitore);
		
		return newCodiceIstanza;
	}

	@Transactional
	@Override
	public String getNewNumeroMude(MudeDComune mudeDComune, MudeDFruitore fruitore, boolean createAlsoFolder) {
		String newCodiceFacicolo = CodeGenerator.getNewCodiceFascicolo(logNumeriMudeRepository.getNextIdNumeroMude(), mudeDComune).replaceAll("-", "");; 
		//if(createAlsoFolder)
		save(newCodiceFacicolo, TipoLogNumeriMude.FASCICOLO.getCode(), fruitore);
		
		save(newCodiceFacicolo, TipoLogNumeriMude.ISTANZA.getCode(), fruitore);
		return newCodiceFacicolo;
	}

	@Transactional
	@Override
	public MudeTLogNumeriMude save(String numeroMude, String tipo, String codiceFruitore) {
		MudeDFruitore fruitore = mudeopenDFruitoreService.findByCodiceFruitore(codiceFruitore);
		return save(numeroMude, tipo, fruitore);
	}

	@Override
	public MudeTLogNumeriMude save(String numeroMude, String tipo, MudeDFruitore fruitore) {
		MudeTLogNumeriMude t = new MudeTLogNumeriMude();
		t.setNumeroMude(numeroMude);
		t.setTipo(tipo);
		t.setMudeDFruitore(fruitore);
		
		return logNumeriMudeRepository.saveDAO(t);
	}
}
