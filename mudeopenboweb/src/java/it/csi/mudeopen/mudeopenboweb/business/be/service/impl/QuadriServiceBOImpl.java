/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopenboweb.business.be.service.QuadriServiceBO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTModello;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoExtendedEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDCategoriaQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTModelloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.DocumentTemplateManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDQuadroSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTemplateSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDTipoQuadroSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.CategoriaQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TipoQuadroVO;

/**
 * The type Contatto service.
 */
@Service
public class QuadriServiceBOImpl extends AbstractApi implements QuadriServiceBO {

    @Autowired
    TipoQuadroEntityMapper tipoQuadroEntityMapper;

    /**
     * The Tipo quadro entity mapper.
     */
    @Autowired
    private MudeDTipoQuadroRepository mudeDTipoQuadroRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

    @Autowired
    private MudeTModelloRepository mudeTModelloRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

	@Autowired
	private QuadroEntityMapper quadroEntityMapper;
	
	@Autowired
	private TemplateEntityMapper templateEntityMapper;
	
	@Autowired
	private TemplateQuadroEntityMapper templateQuadroEntityMapper;
	
    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

    @Autowired
    MudeRTemplateTipoAllegatoRepository mudeRTemplateTipoAllegatoRepository;

    @Autowired
    TipoAllegatoExtendedEntityMapper tipoAllegatoExtendedEntityMapper;

    @Autowired
    private DocumentTemplateManager documentTemplateManager;

    @Autowired
    private MudeDCategoriaQuadroRepository mudeDCategoriaQuadroRepository;

    @Autowired
    private CategoriaQuadroEntityMapper categoriaQuadroEntityMapper;

    @Autowired
    private MudeTEnteRepository mudeTEnteRepository;

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TIPI QUADRI   -------------------- */
    /* ----------------------------------------------------------------------- */
		
    /**
     * Find quadri by param response.
     *
     * @param mudeTUser the mude t user
     * @param page      the page
     * @param size      the size
     * @param sortExp   the sort exp
      the response
     */
    @Override
    public Response findTipoQuadriByParam(MudeTUser mudeTUser, String code, String descr, String active, Long idCategoriaQuadro, int page, int size, String sortExp) {
        Pageable pageble = PageUtil.getPageable(page, size, sortExp);

        Specification<MudeDTipoQuadro> codeLike = MudeDTipoQuadroSpecification.hasCodeLike(code);
        Specification<MudeDTipoQuadro> descrLike = MudeDTipoQuadroSpecification.hasDescrLike(descr);
        Specification<MudeDTipoQuadro> idCategoriaQuadroEq = MudeDTipoQuadroSpecification.hasIdCategoriaQuadro(idCategoriaQuadro);

        Page<MudeDTipoQuadro> mudeDTipoQuadroPages = null;
        if(active != null) // find tipi where Active: "yes", "no". meaning that there are Quadri linked or not
        	mudeDTipoQuadroPages = mudeDTipoQuadroRepository.findAllByActiveState("no".equalsIgnoreCase(active)?"INACTIVE":"ACTIVE", pageble);
        else
        	mudeDTipoQuadroPages = mudeDTipoQuadroRepository.findAll(Specifications.where(codeLike)
												        			.and(descrLike)
												        			.and(idCategoriaQuadroEq), pageble);

        List<TipoQuadroVO> tipoQuadroVOs = tipoQuadroEntityMapper.mapListEntityToListVO(mudeDTipoQuadroPages.getContent(), null);
        return PaginationResponseUtil.buildResponse(tipoQuadroVOs, page, size, mudeDTipoQuadroPages.getTotalPages(), mudeDTipoQuadroPages.getTotalElements());
    }

    /**
     * save tipo quadro by param response.
     *
     * @param TipoQuadroVO      tipo quadro
     * @param mudeTUser the mude t user
      the response TipoQuadroVO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoQuadroVO saveTipoQuadro(TipoQuadroVO tipoQuadroVO, MudeTUser mudeTUser) {
    	MudeDTipoQuadro oldOne = mudeDTipoQuadroRepository.findByCodTipoQuadro(tipoQuadroVO.getCodTipoQuadro());
        if(oldOne != null && (tipoQuadroVO.getIdTipoQuadro() == null
        						|| ((long)tipoQuadroVO.getIdTipoQuadro() != (long)oldOne.getIdTipoQuadro())))
            throw new BusinessException("Codice tipo quadro gia presente: " + tipoQuadroVO.getCodTipoQuadro());

        MudeDTipoQuadro tipoQuadro = tipoQuadroEntityMapper.mapVOtoEntity(tipoQuadroVO, mudeTUser);
        mudeDTipoQuadroRepository.saveDAO(tipoQuadro);

        utilsTraceCsiLogAuditService.traceCsiLogAudit((tipoQuadroVO.getIdTipoQuadro() != null ? CsiLogAudit.TraceOperation.MODIFICA_TIPO_QUADRO : CsiLogAudit.TraceOperation.INSERIMENTO_TIPO_QUADRO).getOperation(), tipoQuadro.getClass().getSimpleName(), "id=" + tipoQuadro.getIdTipoQuadro(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return tipoQuadroEntityMapper.mapEntityToVO(tipoQuadro, null);
    }

    /**
     * delete tipo quadro by param response.
     *
     * @param TipoQuadroVO      tipo quadro
     * @param mudeTUser the mude t user
      the response void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminaTipoQuadro(Long idTipoQuadro, MudeTUser mudeTUser) {
        MudeDTipoQuadro tipoQuadro = mudeDTipoQuadroRepository.findOne(idTipoQuadro);

    	List<MudeDQuadro> list = mudeDQuadroRepository.findAllByIdTipoQuadro(tipoQuadro.getIdTipoQuadro());
    	if(list != null)
    		for(MudeDQuadro quadro : list) {
    			List<MudeRTemplateQuadro> templates = mudeRTemplateQuadroRepository.findAllByMudeDQuadro_IdQuadro(quadro.getIdQuadro());
    			if(templates != null && templates.size() > 0)
    				throw new BusinessException("Ci sono template legati ad uno dei quadri refenziati dal tipo quadro che si sta cercando di eliminare. Impossibile continuare");
    			
    			mudeDQuadroRepository.delete(quadro);
    		}

        mudeDTipoQuadroRepository.delete(tipoQuadro);

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_TIPO_QUADRO.getOperation(), tipoQuadro.getClass().getSimpleName(), "id=" + tipoQuadro.getIdTipoQuadro(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * TO BE DISMISSED
     * get quadro from tipo quadro by param response.
     *
     * @param idTipoQuadro      id tipo quadro
     * @param mudeTUser the mude t user
      the response void
     */
    @Override
    public List<QuadroVO> listaQuadriPerTipo(MudeTUser mudeTUser, Long idTipoQuadro) {
        return quadroEntityMapper.mapListEntityToListVO(mudeDQuadroRepository.findAllByMudeDTipoQuadro_IdTipoQuadro(idTipoQuadro), null);
    }

    @Override
    public List<CategoriaQuadroVO> listaCategoriaTipoQuadri(MudeTUser mudeTUser) {
        return categoriaQuadroEntityMapper.mapListEntityToListVO(mudeDCategoriaQuadroRepository.findAllByDataFineNull());
    }

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE QUADRI   ------------------------- */
    /* ----------------------------------------------------------------------- */

	

    /**
     * Find quadri by param response.
     *
     * @param mudeTUser the mude t user
     * @param page      the page
     * @param size      the size
     * @param sortExp   the sort exp
      the response
     */
    @Override
    public Response listaQuadri(MudeTUser mudeTUser, String filters, String tipoQuadro, String tipoGestione, Long versione, Long stato, int page, int size, String sortExp) {
        Pageable pageable = PageUtil.getPageable(page, size, sortExp);

        Specification<MudeDQuadro> hasTipoQuadroLike = MudeDQuadroSpecification.hasTipoQuadroLike(tipoQuadro);
        Specification<MudeDQuadro> hasTipoGestioneLike = MudeDQuadroSpecification.hasTipoGestioneLike(tipoGestione);
        Specification<MudeDQuadro> hasVersione = MudeDQuadroSpecification.hasVersione(versione);

        Page<MudeDQuadro> mudeDQuadroPages = mudeDQuadroRepository.findAll(Specifications.where(hasTipoQuadroLike)
        		.and(hasTipoGestioneLike)
        		.and(hasVersione), pageable);

        List<QuadroVO> quadroVOs = quadroEntityMapper.mapListEntityToListVO(mudeDQuadroPages.getContent(), null, FilterUtil.getStringValue(filters, "result_type"));
        return PaginationResponseUtil.buildResponse(quadroVOs, page, size, mudeDQuadroPages.getTotalPages(), mudeDQuadroPages.getTotalElements());
    }

    /**
     * save  quadro by param response.
     *
     * @param QuadroVO       quadro
     * @param mudeTUser the mude t user
      the response QuadroVO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public QuadroVO saveQuadro(QuadroVO quadroVO, File docx, String filename, MudeTUser mudeTUser) throws Exception {
    	quadroVO.setNumVersione(1);
    	quadroVO.setIdQuadro(null); // create a new version by default

    	MudeDQuadro maxVersion = mudeDQuadroRepository.findFirstByMudeDTipoQuadro_IdTipoQuadroOrderByNumVersioneDesc(quadroVO.getTipoQuadro().getIdTipoQuadro());
        if(maxVersion != null) { // already exists a version?
            List<MudeDTemplate> attached = mudeDTemplateRepository.findActiveIdQuadroInTemplates(maxVersion.getIdQuadro());
            if(attached != null && attached.size() > 0)  // is this version assigned to an active template?
            	quadroVO.setNumVersione(maxVersion.getNumVersione() + 1);
            else
            	quadroVO.setIdQuadro(maxVersion.getIdQuadro());
        }

        MudeDQuadro quadro = quadroEntityMapper.mapVOtoEntity(quadroVO, mudeTUser);

        quadro.setModello(saveModello(quadro.getModello(), docx, null, filename, quadro.getMudeDTipoQuadro().getCodTipoQuadro() + "-" + quadro.getNumVersione()));
        MudeDQuadro quadroRes = mudeDQuadroRepository.saveDAO(quadro);

        // tries to eliminate the old idModello if not referenced anymore
        if(quadroVO.getModello() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInQuadro(quadroVO.getModello().getId());

        utilsTraceCsiLogAuditService.traceCsiLogAudit((quadroVO.getIdQuadro() != null ? CsiLogAudit.TraceOperation.MODIFICA_QUADRO : CsiLogAudit.TraceOperation.INSERIMENTO_QUADRO).getOperation(), quadro.getClass().getSimpleName(), "id=" + quadro.getIdQuadro(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return quadroEntityMapper.mapEntityToVO(quadroRes, null);
    }

    /**
     * delete  quadro by param response.
     *
     * @param QuadroVO       quadro
     * @param mudeTUser the mude t user
      the response void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminaQuadro(Long idQuadro, MudeTUser mudeTUser) {
		List<MudeRTemplateQuadro> templates = mudeRTemplateQuadroRepository.findAllByMudeDQuadro_IdQuadro(idQuadro);
		if(templates != null && templates.size() > 0)
			throw new BusinessException("Ci sono template legati a questo template. Impossibile eliminarlo");
    	
        MudeDQuadro quadro = mudeDQuadroRepository.findOne(idQuadro);

        mudeDQuadroRepository.delete(quadro);

        // tries to eliminate the old idModello if note referenced anymore
        if(quadro.getModello() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInQuadro(quadro.getModello().getId());

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_QUADRO.getOperation(), quadro.getClass().getSimpleName(), "id=" + quadro.getIdQuadro(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private class TemplateQuadroMove {
    	public List<TemplateVO> templatesAffected;
    	public MudeDQuadro fromQuadro;
    	public MudeDQuadro toQuadro;
    	
    	TemplateQuadroMove(List<TemplateVO> templatesAffected, MudeDQuadro fromQuadro, MudeDQuadro toQuadro) {
        	this.templatesAffected = templatesAffected;
        	this.fromQuadro = fromQuadro;
        	this.toQuadro = toQuadro;
    	}
    }

    private TemplateQuadroMove getTemplateListContainingIdTipoQuadro(Long idTipoQuadro) {
    	List<MudeDQuadro> qudroVersionList = mudeDQuadroRepository.findAllByMudeDTipoQuadro_IdTipoQuadroOrderByNumVersioneDesc(idTipoQuadro);
    	if(qudroVersionList.size() <= 1) // if there is just one version, then there is no prev version to update 
    		return new TemplateQuadroMove(new ArrayList<TemplateVO>(), null, null);

		List<MudeDTemplate> template = mudeDTemplateRepository.findAllByIdQuadro(qudroVersionList.get(1 /* prev quadro version */).getIdQuadro());
	    return new TemplateQuadroMove(templateEntityMapper.mapListEntityToListVO(template, null), 
	    						qudroVersionList.get(1 /* prev quadro version */), 
	    						qudroVersionList.get(0 /* last quadro version */));
    }

    @Override
    public Response recuperaTemplateAssociaQuadro(Long idTipoQuadro, MudeTUser mudeTUser) {
    	List<TemplateVO> templateVOs = getTemplateListContainingIdTipoQuadro(idTipoQuadro).templatesAffected;
        return PaginationResponseUtil.buildResponse(templateVOs, 0, templateVOs.size(), 1, templateVOs.size());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void pubblicaQuadro(Long idTipoQuadro, MudeTUser mudeTUser) {
    	TemplateQuadroMove templateQuadroMove = getTemplateListContainingIdTipoQuadro(idTipoQuadro);
    	
    	Hashtable<String, QuadroVO> quadroComplexCache = new Hashtable<String, QuadroVO>();
    	for(TemplateVO templateVO : templateQuadroMove.templatesAffected) {
    		if(templateVO.getFlgAttivo() == 1)
    			templateVO = nuovaVersioneTemplate(templateVO.getIdTemplate(), mudeTUser);
    		
    		QuadroVO toQuadro = null; 
    		// find the affected template_quadro record
    		MudeRTemplateQuadro oldTemplateQuadro = mudeRTemplateQuadroRepository.findByMudeDTemplate_IdTemplateAndMudeDQuadro_IdQuadro(templateVO.getIdTemplate(), templateQuadroMove.fromQuadro.getIdQuadro());
    		if(oldTemplateQuadro != null) // is the updating quadro not a subTAB? then just replicate quadro_template record
	        	toQuadro = quadroEntityMapper.mapEntityToVO(templateQuadroMove.toQuadro, null);
    		else // not in a relation "MudeRTemplateQuadro"? so, the quadro is in a subTAB within a complex quadro (NOTE: it is not allowed to have the idQuadro in multiple relations!)
				try {
	    			oldTemplateQuadro = mudeRTemplateQuadroRepository.findByIdQuadroInVirtualTemplateQuadro(templateQuadroMove.fromQuadro.getIdQuadro(), 
							templateVO.getIdTemplate());
	    			
					MudeDQuadro quadroComplex = oldTemplateQuadro.getMudeDQuadro(); // it is not allowed to have more than one quadro reference to be updated per template
					
					if(null == (toQuadro = quadroComplexCache.get(quadroComplex.getMudeDTipoQuadro().getCodTipoQuadro()))) {
						quadroComplexCache.put(quadroComplex.getMudeDTipoQuadro().getCodTipoQuadro(), 
												toQuadro = saveQuadro(quadroEntityMapper.mapEntityToVO(quadroComplex, null), null, null, mudeTUser)); 

    					// replaces ~oldIdQuadro~ with ~newIdQuadro~ in json structure
    					mudeDQuadroRepository.updatePrevQuadroVersionInQuadroComplex(toQuadro.getIdQuadro(), 
    									""+templateQuadroMove.fromQuadro.getIdQuadro(), 
    									""+templateQuadroMove.toQuadro.getIdQuadro());
					}
				} catch (Exception e) {
		    		throw new BusinessException("Errore durante la creazione della nuova versione del quadro complesso: " + templateVO.getCodTemplate());
				}

        	// delete prev quadro relation from MudeRTemplateQuadro
            mudeRTemplateQuadroRepository.delete(oldTemplateQuadro);

        	// create new quadro refence in MudeRTemplateQuadro
    		TemplateQuadroVO newTemplateQuadroVO = new TemplateQuadroVO();
        	newTemplateQuadroVO.setTemplate(templateVO);
        	newTemplateQuadroVO.setQuadro(toQuadro);
        	newTemplateQuadroVO.setOrdinamentoTemplateQuadro(oldTemplateQuadro.getOrdinamentoTemplateQuadro());
        	salvaQuadroTemplate(newTemplateQuadroVO, mudeTUser);
    		
			// downloadModelloDocxTemplate(templateVO.getIdTemplate(), mudeTUser); // generate merged docx
    	}
    }

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE QUADRI   ------------------------- */
    /* ----------------------------------------------------------------------- */

    /**
     * Find template by param response.
     *
     * @param mudeTUser the mude t user
     * @param page      the page
     * @param size      the size
     * @param sortExp   the sort exp
      the response
     */
    @Override
    public Response findTemplateByParam(MudeTUser mudeTUser,
    		String tipologiaistanza,
    		String codice,
    		String descrizione,
    		Long versione,
    		Long stato,
    		LocalDate dataInizioValidita,
    		LocalDate dataCessazione,
    		int page, int size, String sortExp) {
        Pageable pageable = PageUtil.getPageable(page, size, sortExp);

        Specification<MudeDTemplate> hasTipologiaistanzaLike = MudeDTemplateSpecification.hasTipologiaistanzaLike(tipologiaistanza);
        Specification<MudeDTemplate> hasCodiceLike = MudeDTemplateSpecification.hasCodiceLike(codice);
        Specification<MudeDTemplate> hasDescrizioneLike = MudeDTemplateSpecification.hasDescrizioneLike(descrizione);
        Specification<MudeDTemplate> hasDataInizioValiditaLike = MudeDTemplateSpecification.hasDataInizioValiditaLike(dataInizioValidita);
        Specification<MudeDTemplate> hasDataCessazioneLike = MudeDTemplateSpecification.hasDataCessazioneLike(dataCessazione);
        Specification<MudeDTemplate> hasVersione = MudeDTemplateSpecification.hasVersione(versione);
        Specification<MudeDTemplate> hasStato = MudeDTemplateSpecification.hasStato(stato);

        Page<MudeDTemplate> mudeDQuadroPages = mudeDTemplateRepository.findAll(Specifications.where(hasTipologiaistanzaLike)
        		.and(hasCodiceLike)
        		.and(hasDescrizioneLike)
        		.and(hasDataInizioValiditaLike)
        		.and(hasDataCessazioneLike)
        		.and(hasVersione)
        		.and(hasStato), pageable);

        List<TemplateVO> quadroVOs = templateEntityMapper.mapListEntityToListVO(mudeDQuadroPages.getContent(), null);

        return PaginationResponseUtil.buildResponse(quadroVOs, page, size, mudeDQuadroPages.getTotalPages(), mudeDQuadroPages.getTotalElements());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TemplateVO saveTemplate(TemplateVO templateVO, MudeTUser mudeTUser) {
    	List<MudeDTemplate> allByCodTemplate = mudeDTemplateRepository.findAllByMudeTipoIstanza_CodiceOrderByNumeroVersioneDesc(templateVO.getTipoIstanza().getId());
    	if(allByCodTemplate != null && allByCodTemplate.size() > 0 && (templateVO.getIdTemplate() == null || 
    						((long)templateVO.getIdTemplate() != (long)allByCodTemplate.get(0).getIdTemplate())))
    		throw new BusinessException("Codice template quadro gia presente: " + templateVO.getCodTemplate());

        MudeDTemplate template = templateEntityMapper.mapVOtoEntity(templateVO, mudeTUser);
        mudeDTemplateRepository.saveDAO(template);

        // tries to eliminate the old idModello if note referenced anymore
        if(templateVO.getModello() != null && templateVO.getModello().getId() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTemplate(templateVO.getModello().getId());
        if(templateVO.getModelloIntestazione() != null && templateVO.getModelloIntestazione().getId() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTemplate(templateVO.getModelloIntestazione().getId());

        utilsTraceCsiLogAuditService.traceCsiLogAudit((templateVO.getIdTemplate() != null ? CsiLogAudit.TraceOperation.MODIFICA_TEMPLATE: CsiLogAudit.TraceOperation.INSERIMENTO_TEMPLATE).getOperation(), template.getClass().getSimpleName(), "id=" + template.getIdTemplate(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return templateEntityMapper.mapEntityToVO(template, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminaTemplate(Long idTemplate, MudeTUser mudeTUser) {
		List<MudeTIstanza> instances = mudeTIstanzaRepository.findByTemplate_IdTemplate(idTemplate);
		if(instances != null && instances.size() > 0)
			throw new BusinessException("Ci sono istanze legate a questo template. Impossibile eliminarlo");

        MudeDTemplate template = mudeDTemplateRepository.findOne(idTemplate);

    	List<MudeRTemplateQuadro> listQuadri = mudeRTemplateQuadroRepository.findByTemplate(idTemplate);
    	if(listQuadri != null)
    		for(MudeRTemplateQuadro quadro : listQuadri)
    			mudeRTemplateQuadroRepository.delete(quadro);

    	List<MudeRTemplateTipoAllegato> listAllegato = mudeRTemplateTipoAllegatoRepository.findAllByTemplate_IdTemplateOrderByOrdinamentoAsc(idTemplate);
    	if(listAllegato != null)
    		for(MudeRTemplateTipoAllegato allegato : listAllegato)
    			mudeRTemplateTipoAllegatoRepository.delete(allegato);

        mudeDTemplateRepository.delete(template);

        // tries to eliminate the old idModello if note referenced anymore
        if(template.getModello() != null && template.getModello().getId() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTemplate(template.getModello().getId());
        if(template.getModelloIntestazione() != null && template.getModelloIntestazione().getId() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTemplate(template.getModelloIntestazione().getId());

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_TEMPLATE.getOperation(), template.getClass().getSimpleName(), "id=" + idTemplate, mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
	public Response listaQuadriTemplate(Long idTemplate, MudeTUser mudeTUser) {
    	List<MudeRTemplateQuadro> quadri = mudeRTemplateQuadroRepository.findAllByMudeDTemplate_IdTemplateOrderByOrdinamentoTemplateQuadroAsc(idTemplate);
        List<TemplateQuadroVO> quadroVOs = templateQuadroEntityMapper.mapListEntityToListVO(quadri, null);

        return PaginationResponseUtil.buildResponse(quadroVOs, 0, quadri.size(), 1, quadri.size());
    }
	
    @Override
	public Response listaAllegatiTemplate(Long idTemplate, MudeTUser mudeTUser) {
    	List<MudeRTemplateTipoAllegato> quadri = mudeRTemplateTipoAllegatoRepository.findAllByTemplate_IdTemplateOrderByOrdinamentoAsc(idTemplate);
    	
        List<TipoAllegatoExtendedVO> quadroVOs = tipoAllegatoExtendedEntityMapper.mapListEntityToListVO(quadri, null);
        return PaginationResponseUtil.buildResponse(quadroVOs, 0, quadri.size(), 1, quadri.size());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TemplateQuadroVO salvaQuadroTemplate(TemplateQuadroVO templateQuadroVO, MudeTUser mudeTUser) {
    	MudeRTemplateQuadro alreadyStored = mudeRTemplateQuadroRepository.findByMudeDTemplate_IdTemplateAndMudeDQuadro_IdQuadro(templateQuadroVO.getTemplate().getIdTemplate(), templateQuadroVO.getQuadro().getIdQuadro());
    	if(alreadyStored != null && (templateQuadroVO.getIdTemplateQuadro() == null || alreadyStored.getIdTemplateQuadro().longValue() != templateQuadroVO.getIdTemplateQuadro().longValue()))
			throw new BusinessException("Quadro già presente in questo template: " + templateQuadroVO.getTemplate().getCodTemplate());
    	
    	MudeRTemplateQuadro template = templateQuadroEntityMapper.mapVOtoEntity(templateQuadroVO, mudeTUser);
    	mudeRTemplateQuadroRepository.saveDAO(template);

        utilsTraceCsiLogAuditService.traceCsiLogAudit((templateQuadroVO.getIdTemplateQuadro() != null ? CsiLogAudit.TraceOperation.MODIFICA_QUADRO_TEMPLATE: CsiLogAudit.TraceOperation.INSERIMENTO_QUADRO_TEMPLATE).getOperation(), template.getClass().getSimpleName(), "id=" + templateQuadroVO.getIdTemplateQuadro(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return templateQuadroEntityMapper.mapEntityToVO(template, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoAllegatoExtendedVO salvaAllegatoTemplate(TipoAllegatoExtendedVO tipoAllegatoExtendedVO, 
    											File docx, String filename, MudeTUser mudeTUser) throws Exception {
    	MudeRTemplateTipoAllegato alreadyStored = mudeRTemplateTipoAllegatoRepository.findByTemplate_IdTemplateAndTipoAllegato_Codice(tipoAllegatoExtendedVO.getIdTemplate(), tipoAllegatoExtendedVO.getCodice());
    	if(alreadyStored != null && (tipoAllegatoExtendedVO.getId() == null || alreadyStored.getId().longValue() != tipoAllegatoExtendedVO.getId().longValue()))
			throw new BusinessException("Il template contiene già questo allegato: " + tipoAllegatoExtendedVO.getCodice());
    	
    	MudeRTemplateTipoAllegato teamplateAllegato = tipoAllegatoExtendedEntityMapper.mapVOtoEntity(tipoAllegatoExtendedVO, mudeTUser);

    	teamplateAllegato.setModello(saveModello(teamplateAllegato.getModello(), docx, null, filename, "modello allegato template"));
    	mudeRTemplateTipoAllegatoRepository.saveDAO(teamplateAllegato);
    	
        // tries to eliminate the old idModello if note referenced anymore
        if(tipoAllegatoExtendedVO.getModello() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTipoAllegato(tipoAllegatoExtendedVO.getModello().getId());

        utilsTraceCsiLogAuditService.traceCsiLogAudit((tipoAllegatoExtendedVO.getId() != null ? CsiLogAudit.TraceOperation.MODIFICA_ALLEGATO_TEMPLATE : CsiLogAudit.TraceOperation.INSERIMENTO_ALLEGATO_TEMPLATE).getOperation(), teamplateAllegato.getClass().getSimpleName(), "id=" + tipoAllegatoExtendedVO.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return tipoAllegatoExtendedEntityMapper.mapEntityToVO(teamplateAllegato, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TemplateVO salvaIntestazioneTemplate(TemplateVO templateVO, 
    											File docxFile, String filename, MudeTUser mudeTUser, String type) throws Exception {
    	MudeDTemplate template = templateEntityMapper.mapVOtoEntity(templateVO, mudeTUser);

    	if("full-document".equalsIgnoreCase(type))
    		template.setModello(saveModello(template.getModello(), docxFile, null, template.getCodTemplate() + "_" + template.getMudeTipoIstanza().getCodice() + "_" + template.getNumeroVersione() + ".docx", template.getMudeTipoIstanza().getCodice() + "-" + template.getNumeroVersione()));
    	if("recreate-default-document".equalsIgnoreCase(type))
    		template.getModello().setFileContent(null);
    	else
    		template.setModelloIntestazione(saveModello(template.getModelloIntestazione(), docxFile, null, filename, "docx intestazione"));
    	
    	mudeDTemplateRepository.saveDAO(template);

        // tries to eliminate the old idModello if note referenced anymore
        if(templateVO.getModelloIntestazione() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTemplate(templateVO.getModelloIntestazione().getId());
    	
        utilsTraceCsiLogAuditService.traceCsiLogAudit((templateVO.getIdTemplate() != null ? CsiLogAudit.TraceOperation.MODIFICA_TEMPLATE: CsiLogAudit.TraceOperation.INSERIMENTO_TEMPLATE).getOperation(), template.getClass().getSimpleName(), "id=" + template.getIdTemplate(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return templateEntityMapper.mapEntityToVO(template, null);
    }

    private MudeTModello saveModello(MudeTModello modello, File file, byte[] fcontent, String filename, String descr) throws IOException {
    	long len = 0l;
        if(fcontent != null)
        	len = fcontent.length;
        else if(file != null) {
        	len = file.length();
        	fcontent = new byte[(int)len];
        	
        	try(InputStream in = new FileInputStream(file)) {
            	if(in.read(fcontent) == 0)
            		throw new BusinessException("[QuadriServiceBOImpl::saveModello] No file to read!");
        	}
        }

        if(fcontent != null) {
            if(modello == null)
            	modello = new MudeTModello();

        	modello.setDenominazione(descr);
        	modello.setPathModello(filename);
        	modello.setFileContent(fcontent);
        	modello.setDimensioneFile(len);
        	
        	mudeTModelloRepository.saveDAO(modello);
        }

        return modello;
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void eliminaQuadroTemplate(Long idTemplateQuadro, MudeTUser mudeTUser) {
        MudeRTemplateQuadro template = mudeRTemplateQuadroRepository.findOne(idTemplateQuadro);

        mudeRTemplateQuadroRepository.delete(template);

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_QUADRO_TEMPLATE.getOperation(), template.getClass().getSimpleName(), "id=" + idTemplateQuadro, mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void eliminaAllegatoTemplate(Long id, MudeTUser mudeTUser) {
		MudeRTemplateTipoAllegato templateAllegato = mudeRTemplateTipoAllegatoRepository.findOne(id);

		if(templateAllegato.getModello() != null && templateAllegato.getModello().getId() != null)
			mudeTModelloRepository.delete(templateAllegato.getModello());
		
		mudeRTemplateTipoAllegatoRepository.delete(templateAllegato);
		
        // tries to eliminate the old idModello if note referenced anymore
        if(templateAllegato.getModello() != null)
        	mudeTModelloRepository.deleteByIdModelloIfNotInTipoAllegato(templateAllegato.getModello().getId());

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_ALLEGATO_TEMPLATE.getOperation(), templateAllegato.getClass().getSimpleName(), "id=" + id, mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public TemplateVO nuovaVersioneTemplate(Long idTemplateFrom, MudeTUser mudeTUser) {
		return cloneTemplate(idTemplateFrom, null, mudeTUser);
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public TemplateVO duplicaTemplate(Long idTemplateFrom, String idTipoIstanzaTo, MudeTUser mudeTUser) {
		return cloneTemplate(idTemplateFrom, idTipoIstanzaTo, mudeTUser);
	}
	
	private TemplateVO cloneTemplate(Long idTemplateFrom, String idTipoIstanzaTo, MudeTUser mudeTUser) {
		MudeDTemplate templateOrg = mudeDTemplateRepository.findOne(idTemplateFrom);
		MudeDTemplate templateDst = new MudeDTemplate(); 
		
		BeanUtils.copyProperties(templateOrg, templateDst);
		templateDst.setIdTemplate(null);
		
		if(idTipoIstanzaTo != null) { // clone template
			templateDst.setMudeTipoIstanza(mudeDTipoIstanzaRepository.findOne(idTipoIstanzaTo));
			
			templateDst.setNumeroVersione(1);
			templateDst.setCodTemplate(templateDst.getMudeTipoIstanza().getCodice());
			
			if(templateDst.getModello() != null)
				try {
					templateDst.setModello(saveModello(null, null, 
							templateDst.getModello().getFileContent(), 
							templateDst.getModello().getPathModello(), 
							templateDst.getMudeTipoIstanza().getCodice() + "-" + templateDst.getNumeroVersione()));
				} catch (IOException notPossible) { }
		}
		else { // new version
			templateDst.setNumeroVersione(templateOrg.getNumeroVersione() + 1);
		}

		templateDst.setFlgAttivo(0L);
		templateDst.setDataInizioValidita(null);
		templateDst.setDataCessazione(null);

		mudeDTemplateRepository.saveDAO(templateDst);

		//duplicate sub template Quadri entities
		for(MudeRTemplateQuadro quadroOrg : mudeRTemplateQuadroRepository.findAllByMudeDTemplate_IdTemplateOrderByOrdinamentoTemplateQuadroAsc(idTemplateFrom)) {
			MudeRTemplateQuadro quadroDst = new MudeRTemplateQuadro();
			BeanUtils.copyProperties(quadroOrg, quadroDst);
			quadroDst.setIdTemplateQuadro(null);
			quadroDst.setMudeDTemplate(templateDst);
			
			mudeRTemplateQuadroRepository.saveDAO(quadroDst);
		}
		
		//duplicate sub template Allegati entities
		for(MudeRTemplateTipoAllegato allegatoOrg : mudeRTemplateTipoAllegatoRepository.findAllByTemplate_IdTemplateOrderByOrdinamentoAsc(idTemplateFrom)) {
			MudeRTemplateTipoAllegato allegatoDst = new MudeRTemplateTipoAllegato();
			BeanUtils.copyProperties(allegatoOrg, allegatoDst);
			allegatoDst.setId(null);
			allegatoDst.setTemplate(templateDst);
			
			mudeRTemplateTipoAllegatoRepository.saveDAO(allegatoDst);
		}
		
		// generate merged docx
		downloadModelloDocxTemplate(templateDst.getIdTemplate(), mudeTUser);
		
		return templateEntityMapper.mapEntityToVO(templateDst, null);
	}

    private void setInstanzaAsNewVersion(MudeTIstanza istanza) {
    	if(istanza.getJsonData() == null || "".equals(istanza.getJsonData())) return; // no jsondata, thus no need to display message
    	
        try {
            JSONObject jsonDataIstanza = new JSONObject(istanza.getJsonData());
            jsonDataIstanza.put(IstanzaVO._NEW_TEMPLATE, "");

            istanza.setJsonData(jsonDataIstanza.toString());
            mudeTIstanzaRepository.saveDAO(istanza);
		} catch (Exception skip) {

		}
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public TemplateVO pubblicaTemplate(Long idTemplate, String publishingMode, MudeTUser mudeTUser) {
		MudeDTemplate template = mudeDTemplateRepository.findOne(idTemplate);

		// generate merged docx
		downloadModelloDocxTemplate(template.getIdTemplate(), mudeTUser);
		
		List<MudeDTemplate> templateListDesc = mudeDTemplateRepository.findAllByMudeTipoIstanza_CodiceOrderByNumeroVersioneDesc(template.getMudeTipoIstanza().getCodice());
		if(templateListDesc != null && templateListDesc.size() > 1) {
			MudeDTemplate preVersionTemplate = templateListDesc.get(1);
			
			preVersionTemplate.setFlgAttivo(0L);
			preVersionTemplate.setDataCessazione(new Date());
			
			mudeDTemplateRepository.saveDAO(preVersionTemplate);
			
			/*
			List<MudeTIstanza> allDrafts = mudeTIstanzaRepository.findByAllCodeTipoIstanza(preVersionTemplate.getMudeTipoIstanza().getCodice());
			if(!"simple".equalsIgnoreCase(publishingMode) && allDrafts != null)
				for(MudeTIstanza istanza : allDrafts)
					setInstanzaAsNewVersion(istanza);
			*/
			
			mudeTIstanzaRepository.markJsonNewTemplate(preVersionTemplate.getMudeTipoIstanza().getCodice());
		}
		
		template.setFlgAttivo(1L);
		template.setDataInizioValidita(new Date());
		mudeDTemplateRepository.saveDAO(template);

		return templateEntityMapper.mapEntityToVO(template, null);
	}

	@Override
	public TemplateVO downloadModelloDocxTemplate(Long idTemplate, MudeTUser mudeTUser) {
		TemplateVO vo;
		MudeDTemplate template = mudeDTemplateRepository.findOne(idTemplate);
		
		try {
			byte[] docxByteArray = documentTemplateManager.buildDocumentTemplateForIstanza(idTemplate);
			String filename = template.getCodTemplate() + "_" + template.getMudeTipoIstanza().getCodice() + "_" + template.getNumeroVersione() + ".docx";

			template.setModello(saveModello(template.getModello(), null, docxByteArray, filename, template.getMudeTipoIstanza().getCodice() + "-" + template.getNumeroVersione()));
			
			mudeDTemplateRepository.saveDAO(template);
			vo = templateEntityMapper.mapEntityToVO(template, null);
			vo.getModello().setFileContent(docxByteArray);
		} catch (Exception e) {
            throw new BusinessException("Impossibile generare il modello DOCX del template: " + template.getCodTemplate() + ". Verificare che tutti i DOCX dei quadri siano corretti. Ulteriori dettagli: " + e.getMessage(), e);
		}
		
		return vo;
	}
	
	public Response listaEnti() {
		List<SelectVO> list = mudeTEnteRepository.listaEnti().stream().map(x -> {
        	return new SelectVO(x.getCodice(), x.getDescrizione()); 
        }).collect(Collectors.toList());

        return voToResponse(list, 200);
	}
	
	static int m_counter;
	static String m_foundFirstcontainer0x;
    public String doFunction(String func) {
    	if(func == null) return null;
    	
    	String errors = "";
    	
    	if(func.startsWith("replicateRellIllSections")) {
    		String subFunc = func.substring(func.indexOf("-") + 1, func.indexOf(":"));

    		for(String idQuadro : func.substring(func.indexOf(":") + 1).split(",")) {
    	    	try {
        			MudeDQuadro mudeDQuadro = mudeDQuadroRepository.findOne(Long.parseLong(idQuadro));
        	        JSONObject json = new JSONObject(mudeDQuadro.getJsonConfiguraQuadro());
        	        
        	    	m_counter = 0;
        	    	m_foundFirstcontainer0x = null;
        	        findAndDestroy(json);
        	        
        	        if("save".equals(subFunc)) {
	        	        mudeDQuadro.setJsonConfiguraQuadro(json.toString());
	        	        mudeDQuadroRepository.save(mudeDQuadro);
        	        }
        	        
        	        if("get".equals(subFunc))
        	        	errors += json.toString() + "\r\n\r\n";
    			} catch (Exception e) {
    				errors += "EXCEPTION[idQuadro: "+idQuadro+"] : " + e.getMessage();
    			}
    		}
    	}

		return errors + "\r\nDONE!";
    }
	
	static private void findAndDestroy(JSONObject jsonObj) throws Exception {
		for(Iterator<String> keys = jsonObj.keys(); keys.hasNext() ; ) {
			String key = keys.next();
			
			Object value = jsonObj.get(key);
			if(key.equals("key") && value instanceof String && ((String)value).startsWith("container0x")) {
				
				if(m_foundFirstcontainer0x == null) {
					Object componentsObj = jsonObj.get("components");
					m_foundFirstcontainer0x = componentsObj.toString();
				}
				else
					jsonObj.put("components", new JSONArray(m_foundFirstcontainer0x.replace("0x0", "0x"+m_counter)));
				
				m_counter++;
			}
			else if(value instanceof JSONArray) {
		        JSONArray documentiArray = (JSONArray)value;
		        for (int i = 0, size = documentiArray.length(); i < size; i++)
		        	findAndDestroy((JSONObject)documentiArray.get(i));
				
			}
			else if(value instanceof JSONObject) {
				findAndDestroy((JSONObject)jsonObj.get(key));
			}
		}
	}
	    
}