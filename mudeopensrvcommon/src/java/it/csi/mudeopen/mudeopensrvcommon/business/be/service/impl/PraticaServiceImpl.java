/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.PraticheExcelBuilder;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PraticaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTIstanzaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTPraticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaVO;

@Service
public class PraticaServiceImpl implements PraticaService {
	private final String CLS_NAME = this.getClass().getSimpleName();
    private static Logger logger = Logger.getLogger(PraticaServiceImpl.class.getCanonicalName());

    @Autowired
    MudeTPraticaRepository mudeTPraticaRepository;

    @Autowired
    MudeRIstanzaPraticaRepository mudeRIstanzaPraticaRepository;

    @Autowired
    MudeTEnteRepository mudeTEnteRepository;

    @Autowired
    private MudeTDocumentoRepository mudeTDocumentoRepository;

    @Autowired
    DocumentoEntityMapper documentoEntityMapper;

    @Autowired
    private MudeDTipoDocpaRepository mudeDTipoDocpaRepository;

    @Autowired
    MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    PraticaEntityMapper praticaEntityMapper;

    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

    @Override
    public void savePraticaForIstanza(MudeTIstanza mudeTIstanza, IstanzaVO istanzaVO, MudeTUser mudeTuser) {
    	boolean isIDFAlreadyCreated = false;
    	
    	MudeTEnte enteRiferimento = null;
    	
		if(mudeTuser == null) {
			if(istanzaVO.getEnteDiRiferimento() != null) {
				Long enteId = istanzaVO.getEnteDiRiferimento().getId();
				enteRiferimento = mudeTEnteRepository.findOne(enteId);
			}
		}else{
			enteRiferimento = mudeTEnteRepository.getEnteRiceventeByUtenteIstanza(
    			mudeTuser.getIdUser(),
				mudeTIstanza.getId());
		}
    	// check if at leat one "ente" is configured  
    	if(enteRiferimento == null)
    		throw new BusinessException("Non è presente la configurazione necessaria per gestire questo tipo di pratica. Impossibile registrare la pratica.");
    	
    	// store the new pratica 
    	MudeTPratica mudeTPratica = mudeTPraticaRepository.findAnyByIdIstanza(mudeTIstanza.getId()); 
    	boolean isNewRecord = mudeTPratica == null;
    	
    	// idPratica == 9999 significa che su formIo del cambio stato BO
    	// è stato indicato un nuovo numero pratica
    	if(isNewRecord &&  istanzaVO != null && istanzaVO.getIdPratica() != null && istanzaVO.getIdPratica() == 9999L) {
    		//Controllo di numero pratica univoco per ente e anno
    		mudeTPratica = mudeTPraticaRepository.findByNumeroPraticaAnnoPraticaEnte(istanzaVO.getNumeroPratica(),istanzaVO.getAnno(), enteRiferimento.getId());
    		if(mudeTPratica != null)
        		throw new BusinessException("Numero pratica esistente per anno ed ente.");
    		
    		mudeTPratica = new MudeTPratica();
    		mudeTPratica.setEnte(enteRiferimento);
        	
        	if(istanzaVO.getAnno() != null)
        		mudeTPratica.setAnnoPratica(istanzaVO.getAnno());
        	
        	if(istanzaVO.getNumeroPratica() != null)
        		mudeTPratica.setNumeroPratica(istanzaVO.getNumeroPratica());
        	
        	mudeTPratica.setDataInizio(new Date());
        	List<MudeTIstanza> istanzePratica = new ArrayList<MudeTIstanza>();
        	istanzePratica.add(mudeTIstanza);
        	mudeTPratica.setIstanze(istanzePratica);
    	}
    	else if(isNewRecord && istanzaVO != null && istanzaVO.getIdPratica()==null) {
    		mudeTPratica = mudeTPraticaRepository.findByNumeroPraticaAnnoPraticaEnte(istanzaVO.getNumeroPratica(),istanzaVO.getAnno(), enteRiferimento.getId());
    		if(mudeTPratica == null)
        		throw new BusinessException("Non è presente la pratica richiesta");
    	}
		// IDF mudeopen_t_pratica empty already created case  
    	else if(!isNewRecord  
    				&& mudeTPratica.getAnnoPratica() == null
    				&& istanzaVO.getAnno() != null 
    				&& istanzaVO.getNumeroPratica() != null) {
    		mudeTPratica.setEnte(enteRiferimento);
        	
    		mudeTPratica.setAnnoPratica(istanzaVO.getAnno());
    		mudeTPratica.setNumeroPratica(istanzaVO.getNumeroPratica());
        	
        	mudeTPratica.setDataInizio(new Date());
        	mudeTPratica.setDataFine(null);
        	
        	isIDFAlreadyCreated = true;
    	}
    	
    	istanzaVO.setDataRegistrazionePratica(utilsDate.asLocalDateTime(mudeTPratica.getDataInizio()));
    	
    	if(mudeTPratica.getIstanze() != null)
			mudeTPratica.getIstanze().add(mudeTIstanza);
		else {
			List<MudeTIstanza> istanzePratica = new ArrayList<MudeTIstanza>();
        	istanzePratica.add(mudeTIstanza);
        	mudeTPratica.setIstanze(istanzePratica);
		}
    	mudeTPraticaRepository.saveDAO(mudeTPratica);
    	
    	if(!isIDFAlreadyCreated) {
	    	// store istanza/pratica relation
	    	MudeRIstanzaPratica mudeRIstanzaPratica = new MudeRIstanzaPratica();
	    	mudeRIstanzaPratica.setIstanza(mudeTIstanza);
	    	mudeRIstanzaPratica.setPratica(mudeTPratica);
	    	mudeRIstanzaPraticaRepository.saveDAO(mudeRIstanzaPratica);
    	}
    }

	@Override
	public Response recuperaDocumenti(String numPratica, int page, int size) {
		 String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

	     Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "dataCaricamento"));

	     MudeTPratica mudeTPratica = null;

        if (numPratica != null) {
        	List<MudeTPratica> optPratica = mudeTPraticaRepository.getByNumeroPratica(numPratica);
            mudeTPratica = !optPratica.isEmpty() ? optPratica.get(0) : null;
        }

	     List<DocumentoVO> documentiVoList = new ArrayList<DocumentoVO>();
	     Page<MudeTDocumento> mudeTDocumentiList = mudeTDocumentoRepository.findAll(MudeTPraticaSpecification.findDocumentiByPratica(mudeTPratica == null? null : mudeTPratica.getId()), pageable);

	     documentiVoList = documentoEntityMapper.mapListEntityToListVO(mudeTDocumentiList.getContent(), null);
	     return PaginationResponseUtil.buildResponse(documentiVoList, page, size, mudeTDocumentiList.getTotalPages(), mudeTDocumentiList.getTotalElements());
	     
	     
	}

	@Override
	public Response recuperaDocumenti(Long idPratica, int page, int size) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "dataCaricamento"));

		MudeTPratica mudeTPratica = null;

		if (idPratica != null) {
			Optional<MudeTPratica> opt = mudeTPraticaRepository.findById(idPratica);
			mudeTPratica = opt.isPresent() ? opt.get() : null;
		}

		List<DocumentoVO> documentiVoList = new ArrayList<DocumentoVO>();
		Page<MudeTDocumento> mudeTDocumentiList = mudeTDocumentoRepository.findAll(MudeTPraticaSpecification.findDocumentiByPratica(mudeTPratica == null? null : mudeTPratica.getId()), pageable);

		documentiVoList = documentoEntityMapper.mapListEntityToListVO(mudeTDocumentiList.getContent(), null);
		return PaginationResponseUtil.buildResponse(documentiVoList, page, size, mudeTDocumentiList.getTotalPages(), mudeTDocumentiList.getTotalElements());
	}
	
	@Override
	public DocumentoVO findByFileUID(String fileUID) {
		Optional<MudeTDocumento> entityOPT = mudeTDocumentoRepository.findByFileUID(fileUID);
        DocumentoVO vo = null;
        if(entityOPT.isPresent()){
            vo = documentoEntityMapper.mapEntityToVO(entityOPT.get(), null);
        }
        return vo;
	}

	@Override
	public DocumentoVO loadDocumento(Long idDocumento) {
		Optional<MudeTDocumento> entityOPT = mudeTDocumentoRepository.findById(idDocumento);
		DocumentoVO vo = null;
        if(entityOPT.isPresent()){
            vo = documentoEntityMapper.mapEntityToVO(entityOPT.get(), null);
        }
        return vo;
	}

	@Override
	public void deleteDocumento(Long idDocumento) {
		Optional<MudeTDocumento> opt = mudeTDocumentoRepository.findById(idDocumento);
		MudeTDocumento mudeTDocumento = opt.get();
		mudeTDocumento.setDataAnnullamento(new Date());
		mudeTDocumentoRepository.saveDAO(mudeTDocumento);
	}

	@Override
	public MudeTPratica findById(Long idPratica) {
		Optional<MudeTPratica> opt = mudeTPraticaRepository.findById(idPratica);
		return opt.isPresent() ? opt.get() : null;
	}

	@Override
	public void updateUuidIndex(MudeTPratica pratica, MudeTUser mudeTUser) {
		MudeTPratica mudeTPratica = mudeTPraticaRepository.findOne(pratica.getId());
		mudeTPratica.setUuidIndex(pratica.getUuidIndex());
		mudeTPraticaRepository.saveDAO(mudeTPratica);
	}

	@Override
	public DocumentoVO saveDocumento(DocumentoVO vo, MudeTUser mudeTUser) {
		MudeTDocumento mudeTDocumento = new MudeTDocumento();
		MudeDTipoDocPA mudeDTipoDocPA = mudeDTipoDocpaRepository.findByCodice(vo.getTipoDocumento().getCodice());
		mudeTDocumento.setTipoDocumento(mudeDTipoDocPA);
		Optional<MudeTPratica> optPratica = mudeTPraticaRepository.findById(vo.getIdPratica());
		mudeTDocumento.setPratica(optPratica.get());
		mudeTDocumento.setDimensioneFile(vo.getDimensioneFile());
		mudeTDocumento.setFileContent(vo.getFileContent());
		mudeTDocumento.setFileUID(vo.getFileUID());
		mudeTDocumento.setNomeFileDocumento(vo.getNomeFileDocumento());
		mudeTDocumento.setUser(mudeTUser);
		mudeTDocumento.setDataCaricamento(vo.getDataCaricamento());
		
		mudeTDocumento.setNumeroProtocollo(vo.getNumeroProtocollo());
		mudeTDocumento.setDataProtocollo(vo.getDataProtocollo());
		
		MudeTDocumento saved = mudeTDocumentoRepository.saveDAO(mudeTDocumento);
		return documentoEntityMapper.mapEntityToVO(saved, mudeTUser);
	}

	@Override
	public Response cercaPratiche(String filter, MudeTUser mudeTUser, String scope, int page, int size) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
            Long idComune = FilterUtil.getLongValue(filter, "id_comune");
            Long anno = FilterUtil.getLongValue(filter, "anno");
            String numPratica = FilterUtil.getStringValue(filter, "num_pratica");
			Pageable pageable = new PageRequest(page, size);
			
            Long idPratica = null;
            String _idPratica = FilterUtil.getStringValue(filter, "id_pratica");
            if(StringUtils.isNotBlank(_idPratica)) {
	            if(_idPratica.startsWith("COSMO-")) {
	            	MudeTPratica mudeTPratica = mudeTPraticaRepository.findBynumeroPraticaCosmo(Long.parseLong(_idPratica.substring(6)));
	            	if(mudeTPratica == null)
	            		throw new BusinessException("La pratica non e' in attesa di protocollazione. Non e' possibilie mostrarne i dettagli fino al completamento");
	            	
	            	idPratica = mudeTPratica.getId();
	            }
	            else
	            	idPratica = Long.parseLong(_idPratica);
            }
			
        	Specification<MudeTPratica> filterByRole = null;
        	if("backoffice".equals(scope)) {
        		int num_max_istances_role = Integer.parseInt(getValueFromMudeCProprieta("MUDE_NUM_MAX_ISTANCES_ROLE","500"));
        		// Fix RUOLI Utente BO
        		 List <Long> mudeTIstanzaIdsListRuoli = null;
	           	 if(numPratica != null || idComune != null || anno != null || idPratica != null)
	           		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdAllPraticaByRuoli(
	           				mudeTUser.getIdUser(), 
	           				numPratica == null? "" : numPratica,
	           				idPratica == null? -1 : idPratica,
	           				idComune == null? -1 : idComune,
	           				anno == null? -1 : anno);
	           	 else
	           		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdByRuoli(mudeTUser.getIdUser());
	           	 
	           	 if(mudeTIstanzaIdsListRuoli == null || mudeTIstanzaIdsListRuoli.isEmpty()) {
	           		List<PraticaVO> praticaVoList = new ArrayList<PraticaVO>();
	        		return PaginationResponseUtil.buildResponse(praticaVoList, page, size, 0, 0);
	           	 }else if(mudeTIstanzaIdsListRuoli.size()>num_max_istances_role) {
             		throw new BusinessException("Troppi elementi da visualizzare. Si consiglia di impostare uno o piu' filtri.");
             	 }
	        	
	           	List <Long> mudeTPraticaIdsListRuoli = mudeTPraticaRepository.getPraticheIdByRuoli(mudeTIstanzaIdsListRuoli);
	        	if(mudeTPraticaIdsListRuoli == null || mudeTPraticaIdsListRuoli.isEmpty()) {
	        		List<PraticaVO> praticaVoList = new ArrayList<PraticaVO>();
	        		return PaginationResponseUtil.buildResponse(praticaVoList, page, size, 0, 0);
	        	}
        	
	        	HashMap map = new HashMap();
	        	map.put("in", mudeTPraticaIdsListRuoli);
				filterByRole = MudeTPraticaSpecification.findById(map );
        	}
        	
        	Specification<MudeTPratica> istanzaSpecs = null;
        	if("frontoffice".equals(scope)) {
        		istanzaSpecs = MudeTPraticaSpecification.addIstanzaSpcs(istanzaService.getIstanzeSpecifications(
						filter, mudeTUser, "pratiche", methodName));
                // alredy handled params
        		idComune = null;
        	}
        	
        	Specification<MudeTPratica> filters = MudeTPraticaSpecification.findByFilters(mudeTUser, idPratica, anno, numPratica, idComune, scope);
            Specification<MudeTPratica> filterDataPraticaDa = MudeTPraticaSpecification.findByPraticaFrom(FilterUtil.getDateFromValue(filter, "data_registrazione_pratica"));
            Specification<MudeTPratica> filterDataPraticaA = MudeTPraticaSpecification.findByPraticaTo(FilterUtil.getDateToValue(filter, "data_registrazione_pratica"));
            Specification<MudeTPratica> filterEnte = MudeTPraticaSpecification.findByEnte(FilterUtil.getValue(filter, "ente"));
    		
    		Page<MudeTPratica> mudeTPraticaList = mudeTPraticaRepository.findAll(Specifications.where(filters)
    																			.and(filterByRole)
															    				.and(filterDataPraticaDa)
															    				.and(filterDataPraticaA)
															    				.and(filterEnte)
															    				.and(istanzaSpecs), 
															    				pageable);
    		List<PraticaVO> praticheVoList = praticaEntityMapper.mapListEntityToListVO(mudeTPraticaList.getContent(), mudeTUser);
            return PaginationResponseUtil.buildResponse(praticheVoList, page, size, mudeTPraticaList.getTotalPages(), mudeTPraticaList.getTotalElements());
		
	}
	
	@Override
	public byte[]  exportExcelPraticheUtente(MudeTUser mudeTUser, Long idPratica, Long anno, String numPratica, Long idComune,
			String scope) {
		

			byte[] fileContent = null;
			int num_max_istances_role = Integer.parseInt(getValueFromMudeCProprieta("MUDE_NUM_MAX_ISTANCES_ROLE","500"));
		    
			
			List <Long> mudeTIstanzaIdsListRuoli = null;
          	 if(numPratica != null || idComune != null || anno != null || idPratica != null)
          		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdAllPraticaByRuoli(
          				mudeTUser.getIdUser(), 
          				numPratica == null? "" : numPratica,
          				idPratica == null? -1 : idPratica,
          				idComune == null? -1 : idComune,
          				anno == null? -1 : anno);
          	 else
          		mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdByRuoli(mudeTUser.getIdUser());
		    //List <Long> mudeTIstanzaIdsListRuoli = new ArrayList<Long>();
        	if(mudeTIstanzaIdsListRuoli == null || mudeTIstanzaIdsListRuoli.isEmpty()) {
        		return fileContent;
        	}else if(mudeTIstanzaIdsListRuoli.size()>num_max_istances_role) {
         		throw new BusinessException("Troppi elementi da visualizzare. Si consiglia di impostare uno o piu' filtri.");
         	 }
        	/*for(MudeTIstanza mudeTIstanza : mudeTIstanzaListRuoli) {
         		mudeTIstanzaIdsListRuoli.add(mudeTIstanza.getId());
         	}*/
        	List <Long> mudeTPraticaIdsListRuoli = new ArrayList<Long>();
        	
        	List <MudeTPratica> mudeTPraticaListRuolo = mudeTPraticaRepository.getPraticheByRuoli(mudeTIstanzaIdsListRuoli);
        	
        	if(mudeTPraticaListRuolo == null || mudeTPraticaListRuolo.isEmpty()) {
        		return fileContent;
        	}
        	for(MudeTPratica mudeTPratica : mudeTPraticaListRuolo) {
        		mudeTPraticaIdsListRuoli.add(mudeTPratica.getId());
         	}
        	
        	Specification<MudeTPratica> filters = MudeTPraticaSpecification.findByFilters(mudeTUser, idPratica, anno, numPratica, idComune,
    				scope);
        	Specification<MudeTPratica> filterByRole = MudeTPraticaSpecification.findByRole(mudeTUser,mudeTPraticaIdsListRuoli);
    		
    		List<MudeTPratica> mudeTPraticaList = mudeTPraticaRepository.findAll(Specifications.where(filters).and(filterByRole)
    											);
    		List<PraticaVO> praticheVoList = praticaEntityMapper.mapListEntityToListVO(mudeTPraticaList, mudeTUser);

            PraticheExcelBuilder praticheExcelBuilder = new PraticheExcelBuilder();
            HSSFWorkbook workbook = new HSSFWorkbook();
            try {
                fileContent = praticheExcelBuilder.buildExcel(praticheVoList, workbook);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return fileContent;
		
	}

	/**
	 * Solo per ricerca da WS.
	 * il filtro può contenere codIstat, numeroPratica, annoPratica
	 */
	@Override
	public Response cercaPraticheWs(String codiceFruitore, String filter, int page, int size) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "dataInizio"));
		
        String istatComune = FilterUtil.getValueWS(filter, "codIstat");
        String numeroPratica = FilterUtil.getValueWS(filter, "numeroPratica");
        String annoPratica = FilterUtil.getValueWS(filter, "annoPratica");

        Specification<MudeTPratica> filterByComune = MudeTPraticaSpecification.findByIstatComune(istatComune);
        Specification<MudeTPratica> filterByNumeroPratica = MudeTPraticaSpecification.findByNumeroPratica(numeroPratica);
        Specification<MudeTPratica> filterByAnnoPratica = MudeTPraticaSpecification.findByAnnoPratica(annoPratica);
        Specification<MudeTPratica> filterByFruitore = MudeTPraticaSpecification.findByFruitore(codiceFruitore);
        @SuppressWarnings("unchecked")
		List<String> statiIstanza = FilterUtil.getValues(filter, "statiIstanza");
        Specification<MudeTPratica> filterByStatiIstanza = MudeTPraticaSpecification.findByIstanzaConStati(statiIstanza);
        Specification<MudeTPratica> filterByEnte = MudeTPraticaSpecification.addFruitoreEnte(codiceFruitore);

        Page<MudeTPratica> mudeTPraticaList = mudeTPraticaRepository.findAll(Specifications.where(filterByComune)
        										.and(filterByEnte)
								        		.and(filterByNumeroPratica)
								        		.and(filterByAnnoPratica)
								        		.and(filterByFruitore)
								        		.and(filterByStatiIstanza),
								        		pageable);

        List<PraticaVO> praticheVoList = praticaEntityMapper.mapListEntityToListVO(mudeTPraticaList.getContent(), null);
		return PaginationResponseUtil.buildResponse(praticheVoList, page, size, mudeTPraticaList.getTotalPages(), mudeTPraticaList.getTotalElements());
	}
	
	private String getValueFromMudeCProprieta(String name, String defaultValue) {
		String value = mudeCProprietaRepository.getValueByName(name, defaultValue);
		if (value!=null) {
			return value;
		} 
		return "";
	}

}