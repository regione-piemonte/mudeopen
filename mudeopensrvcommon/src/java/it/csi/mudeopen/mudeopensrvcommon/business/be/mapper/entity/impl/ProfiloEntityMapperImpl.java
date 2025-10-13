/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOrdine;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDitta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProfiloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDOrdineRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQualificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDittaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTitoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl.UtenteServiceImpl;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

@Component
public class ProfiloEntityMapperImpl implements ProfiloEntityMapper {
    static private List<SelectVO> qualificaList;
    static private List<SelectVO> ordineList;
    static private List<SelectVO> tipoResidenzaList;
    static private List<SelectVO> dugList;
    static private List<SelectVO> tipoDittaList;
    static private List<TipoIstanzaVO> tipoIstanzaList;
    static private List<SelectVO> tipoInterventoList;
    static private List<SelectVO> statoIstanzaList;
    static private List<SelectVO> statoFascicoloList;
    static private List<SelectVO> titoloList;

    private static Logger logger = Logger.getLogger(UtenteServiceImpl.class.getCanonicalName());

    static {
        tipoResidenzaList = new ArrayList<>();
        for (MudeTIndirizzo.TipoIndirizzo tipoIndirizzo : MudeTIndirizzo.TipoIndirizzo.values()) {
            if (tipoIndirizzo.getTipoContatto() == TipoContatto.PF) {
                tipoResidenzaList.add(new SelectVO(tipoIndirizzo.getId(), tipoIndirizzo.getLabel()));
            }
        }
    }

    @Autowired
    private UtenteEntityMapper UtenteEntityMapper;
    @Autowired
    private MudeDQualificaRepository mudeDQualificaRepository;
    @Autowired
    private MudeDOrdineRepository mudeDOrdineRepository;
    @Autowired
    private MudeDDugRepository mudeDDugRepository;
    @Autowired
    private MudeDTipoDittaRepository mudeDTipoDittaRepository;
    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;
    @Autowired
    private MudeDTipoInterventoRepository mudeDTipoInterventoRepository;
    @Autowired
    private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;
    @Autowired
    private MudeDStatoFascicoloRepository mudeDStatoFascicoloRepository;
    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
    @Autowired
    private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;
    @Autowired
    private MudeDTitoloRepository mudeDTitoloRepository;

    @Override
    public synchronized ProfiloResponse mapEntityToVO(MudeTUser dto) {
        initConfData();
        UtenteVO infoUtente = null;
        if (dto != null) {
            infoUtente = UtenteEntityMapper.mapEntityToVO(dto);
        }

        List<SelectVO> props = mudeCProprietaRepository.getAllByVisibilita("FO").stream().map(x -> {
        	return new SelectVO(x.getNome(), x.getValore()); 
        }).collect(Collectors.toList());

        return new ProfiloResponse(
        				(dto != null && dto.getUtenteBo()) 
	        				|| (dto != null 
	        						&& dto.getValidatoDaUtente() != null 
	        						&& dto.getValidatoDaUtente() 
	        						&& infoUtente != null 
	        						&& infoUtente.getContatto()!= null 
	        						&& infoUtente.getContatto().getAnagrafica().getNome() != null 
	        						&& infoUtente.getContatto().getAnagrafica().getNome().length() > 0 ? true : false),
						 qualificaList,
						 ordineList,
						 tipoResidenzaList,
						 dugList,
						 tipoDittaList,
						 tipoIstanzaList,
						 tipoInterventoList,
						 infoUtente,
						 statoIstanzaList,
						 statoFascicoloList,
						 props);
    }

    private synchronized boolean initConfData() {
        if (tipoIstanzaList != null) return false;

        LoggerUtil.info(logger, " **********************************  initConfData()");

        qualificaList = new ArrayList<SelectVO>();
        ordineList = new ArrayList<SelectVO>();
        dugList = new ArrayList<SelectVO>();
        tipoDittaList = new ArrayList<SelectVO>();
        tipoInterventoList = new ArrayList<SelectVO>();
        statoIstanzaList = new ArrayList<>();
        statoFascicoloList = new ArrayList<>();
        tipoIstanzaList = tipoIstanzaEntityMapper.mapListEntityToListVO(mudeDTipoIstanzaRepository.findAllOrdered());

        titoloList = new ArrayList<>();
        for (MudeDTitolo titolo : mudeDTitoloRepository.findAllOrdered())
        	titoloList.add(new SelectVO(titolo.getCodice(), titolo.getDescrizione()));

        for (MudeDQualifica qualifica : mudeDQualificaRepository.findAllOrdered()) {
            qualificaList.add(new SelectVO(qualifica.getIdQualifica(), qualifica.getDenominazione()));
        }
        for (MudeDOrdine ordine : mudeDOrdineRepository.findAllOrdered()) {
            ordineList.add(new SelectVO(ordine.getCodice(), ordine.getDescrizione()));
        }
        for (MudeDDug dug : mudeDDugRepository.findAllOrdered()) {
            dugList.add(new SelectVO(dug.getIdDug(), dug.getDenominazione()));
        }
        for (MudeDTipoDitta tipoDitta : mudeDTipoDittaRepository.findAllOrdered()) {
            tipoDittaList.add(new SelectVO(tipoDitta.getIdTipoDitta(), tipoDitta.getDenominazione()));
        }
        for (MudeDTipoIntervento tipoIntervento : mudeDTipoInterventoRepository.findAllOrdered()) {
            tipoInterventoList.add(new SelectVO(tipoIntervento.getCodice(), tipoIntervento.getDescrizione()));
        }
        for (MudeDStatoIstanza statoIstanza : mudeDStatoIstanzaRepository.findAllOrdered()) {
            statoIstanzaList.add(new SelectVO(statoIstanza.getCodice(), statoIstanza.getDescrizione()));
        }
        for (MudeDStatoFascicolo statoFascicolo : mudeDStatoFascicoloRepository.findAllOrdered()) {
            statoFascicoloList.add(new SelectVO(statoFascicolo.getCodice(), statoFascicolo.getDescrizione()));
        }
        
        return true;
    }

    @PostConstruct
    public void doInit() {
    	if(!initConfData()) return;
    	
    	// initialize just once the environment properties
    	Constants._environment = mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_ENVIRONMENT, "");
    	Constants._pdf_istanza_checks = mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_SKIP_PDF_CHECKS, "");
    	Constants._mude_allowed_cf_pm = mudeCProprietaRepository.getValueByName(Constants.CONFIG_MUDE_ALLOWED_CF_PM, "");

    	Constants._config_key_logouturl = mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_MUDE_LOGOUT_URL, "");
    	
    	Constants._config_key_allegati_allowed_file_extensions = mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_ALLEGATI_ALLOWED_FILE_EXTENSIONS, "application/pdf,.pdf,.p7m");
    	try { Constants._config_key_allegati_max_size = Long.parseLong(mudeCProprietaRepository.getValueByName(Constants.CONFIG_KEY_ALLEGATI_MAX_SIZE, "50")); } catch (Exception getDefault) { }

    }

	public List<SelectVO> getQualificaList() { doInit(); return qualificaList; }
	public List<SelectVO> getOrdineList() { doInit(); return ordineList; }
	public List<SelectVO> getTipoResidenzaList() { doInit(); return tipoResidenzaList; }
	public List<SelectVO> getDugList() { doInit(); return dugList; }
	public List<SelectVO> getTipoDittaList() { doInit(); return tipoDittaList; }
	public List<TipoIstanzaVO> getTipoIstanzaList() { doInit(); return tipoIstanzaList; }
	public List<SelectVO> getTipoInterventoList() { doInit(); return tipoInterventoList; }
	public List<SelectVO> getStatoIstanzaList() { doInit(); return statoIstanzaList; }
	public List<SelectVO> getStatoFascicoloList() { doInit(); return statoFascicoloList; }
	public List<SelectVO> getTitoloList() { doInit(); return titoloList; }
	
    
}