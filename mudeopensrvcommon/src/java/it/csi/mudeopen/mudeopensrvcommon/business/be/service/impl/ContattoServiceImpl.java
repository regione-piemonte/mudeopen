/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza.DatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRContattoQualificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRPfPgRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDatiIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloIntestatarioService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PfPgService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTContattoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.JsonUtils;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;

@Service
public class ContattoServiceImpl implements ContattoService {

    private static Logger logger = Logger.getLogger(ContattoServiceImpl.class.getCanonicalName());

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;

    @Autowired
    private MudeTDatiIstanzaRepository mudeTDatiIstanzaRepository;

    @Autowired
    private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private PfPgService pfPgService;

    @Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;

    @Autowired
    private MudeRContattoQualificaRepository mudeRContattoQualificaRepository;

    @Autowired
    private MudeRPfPgRepository mudeRPfPgRepository;

    /*
    @Autowired
    private FascicoloIntestatarioService fascicoloIntestatarioService;

    @Autowired
    private IstanzaStatoService istanzaStatoService;
    */

    @Transactional(propagation = Propagation.REQUIRED)
    public ContattoVO saveContatto(ContattoVO contattoVO, Long idIstanza, MudeTUser mudeTUser) {

        MudeTContatto oldContact = null, currentContact = null;
        boolean isContactFromOtherUser = false;

    	Long idToCease = contattoVO.getId();
        if(idToCease != null) {
        	oldContact = currentContact = mudeTContattoRepository.findOne(idToCease);

            // contact comes from diffrent user? then try to locate if a similar contact from the current user
            isContactFromOtherUser = mudeTUser.getIdUser() != currentContact.getMudeTUser().getIdUser();
            if(isContactFromOtherUser) {
                currentContact = mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(contattoVO.getGuid(), mudeTUser.getIdUser());
                contattoVO.setId(currentContact == null? null : currentContact.getId());
            }

            // does exist a contact linked to idContatto? if so, create a new version   
            if(contattoVO.getId() != null && mudeRIstanzaSoggettoRepository.countBySoggettoAndStatoIstanza(contattoVO.getId(), 
            		new String[] {StatoIstanza.BOZZA.getValue(), StatoIstanza.DA_FIRMARE.getValue(), StatoIstanza.FIRMATA.getValue(), StatoIstanza.RESTITUITA_PER_VERIFICHE.getValue()}) > 0)
                contattoVO.setId(null);

        }
        else if(null != mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(contattoVO.getGuid(), mudeTUser.getIdUser()))
        	throw new BusinessException("Non è possibile creare un nuovo contatto già presente nella rubrica con lo stesso codice fiscale o partita iva.");

        if (null != contattoVO.getId()) {
            mudeTIndirizzoRepository.deleteAllByMudeTContattoId(contattoVO.getId());
            mudeRContattoQualificaRepository.removeAllByIdContatto(contattoVO.getId());
        }

        MudeTContatto mudeTContatto = contattoEntityMapper.mapVOtoEntity(contattoVO, mudeTUser);
        mudeTContatto.setMudeTUser(mudeTUser);
        if (null == mudeTContatto.getId()) {
            mudeTContatto.setDataCreazione(LocalDateTime.now());
        }

        if(contattoVO.getTipoContatto() == TipoContatto.PG)
	        mudeTContatto.setGuid(mudeTContatto.getPiva() != null ? mudeTContatto.getPiva() : 
        		(mudeTContatto.getPivaComunitaria() != null ? mudeTContatto.getPivaComunitaria() : ""));
        else
	        mudeTContatto.setGuid(mudeTContatto.getCf());

        mudeTContatto.setDataCessazione(null);
        mudeTContattoRepository.saveDAO(mudeTContatto);
    	ContattoVO savedContattoVO = contattoEntityMapper.mapEntityToVO(mudeTContatto, mudeTUser);

        pfPgService.deleteByPG(mudeTContatto.getId());
        ContattoVO legaleRappresentante = contattoVO.getLegaleRappresentante();
        if (null != legaleRappresentante) {
        	if(legaleRappresentante.getId().longValue() == mudeTContatto.getId().longValue())
        		throw new BusinessException("Impossibile registrare il contatto con se stesso come rappresentante legale.");
        	
            pfPgService.savePfPg(legaleRappresentante.getId(), mudeTContatto.getId(), contattoVO.getIdTitoloRappresentante());
            savedContattoVO.setLegaleRappresentante(legaleRappresentante);
            savedContattoVO.setIdTitoloRappresentante(contattoVO.getIdTitoloRappresentante());
        }

        // update all contact in instanes
        mudeRIstanzaSoggettoRepository.updateContactForInstances(""+mudeTContatto.getId(), mudeTContatto.getGuid(), mudeTUser.getIdUser(), JsonUtils.toJsonString(savedContattoVO), -1L);
        mudeRIstanzaSoggettoRepository.updateSubjectsForInstances(mudeTContatto.getId(), mudeTContatto.getGuid(), mudeTUser.getIdUser());

        if(oldContact != null) {
            final Long oldContactId = oldContact.getId();

            if(oldContactId.longValue() != mudeTContatto.getId().longValue())
	    		mudeRIstanzaSoggettoRepository.findAllInstancesByContactGuid(mudeTContatto.getGuid(), mudeTUser.getIdUser()).forEach(mudeTIstanzaSoggetto -> {
	    	        for(MudeTDatiIstanza mudeTDatiIstanza : CollectionUtils.emptyIfNull(mudeTDatiIstanzaRepository.findAllTitoloSoggetto(mudeTIstanzaSoggetto.getMudeTIstanza().getId(), oldContactId))) {
	                    mudeTDatiIstanza.setMudeTContatto(mudeTContatto);
	                    mudeTDatiIstanzaRepository.saveDAO(mudeTDatiIstanza);
	                }
    		});
        }

        // dismiss all  OLD RECORDS
        for(MudeTContatto contactToCease : mudeTContattoRepository.findAllToCease(idToCease, mudeTContatto.getGuid(), Long.valueOf(mudeTUser.getIdUser()) ))
        	if(mudeTContatto.getId().longValue() != contactToCease.getId().longValue()) {
	        	contactToCease.setDataCessazione(LocalDateTime.now());
	            mudeTContattoRepository.saveDAO(contactToCease);
	    	}

        if (currentContact != null) {
            utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.MODIFICA_CONTATTO_RUBRICA.getOperation(), mudeTContatto.getTableName(), "id=" + mudeTContatto.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
        } else {
            utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.INSERIMENTO_CONTATTO_RUBRICA.getOperation(), mudeTContatto.getTableName(), "id=" + mudeTContatto.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
        }

        return savedContattoVO;
    }

    @Override   //searchScope : CONTACTS | CONTACTS_AND_PERMITTED
    public Response findContattoByParam(String filter,  MudeTUser mudeTUser, int page, int size, String sortExp) {
        Pageable pageble = PageUtil.getPageable(page, size, sortExp);
        Map cf = FilterUtil.getValue(filter, "cf");
        Map titolare = FilterUtil.getValue(filter, "titolare");
        Map impresaLavori = FilterUtil.getValue(filter, "impresaLavori");
        Map professionista = FilterUtil.getValue(filter, "professionista");
        Map tipoContattoMap = FilterUtil.getValue(filter, "tipoContatto");
        String tc = (String)tipoContattoMap.get("eq");
        TipoContatto tipoContatto = TipoContatto.findByValue(tc);
        Map partitaIva = FilterUtil.getValue(filter, "partitaIva");
        Map partitaIvaCom = FilterUtil.getValue(filter, "partitaIvaComunitaria");

        //common
        Specification<MudeTContatto> isNotDeleted = MudeTContattoSpecification.isNotDeleted();
        Specification<MudeTContatto> tipoContattoEquals = MudeTContattoSpecification.hasTipoContattoEquals(tipoContatto);
        Specification<MudeTContatto> cfLike = MudeTContattoSpecification.hasCF(cf);
        Specification<MudeTContatto> pivaLike = MudeTContattoSpecification.hasPiva(partitaIva);
        Specification<MudeTContatto> pivaComLike = MudeTContattoSpecification.hasPivaComunitaria(partitaIvaCom);

        Specification<MudeTContatto> isTitolare = MudeTContattoSpecification.hasBooleanField(titolare, "titolare");
        Specification<MudeTContatto> isImpresaLavori = MudeTContattoSpecification.hasBooleanField(impresaLavori, "impresaLavori");
        Specification<MudeTContatto> isProfessionista = MudeTContattoSpecification.hasBooleanField(professionista, "professionista");

        Specification<MudeTContatto> commonFilters = Specifications.where(isNotDeleted).and(tipoContattoEquals).and(cfLike).and(pivaLike).and(pivaComLike).and(isTitolare).and(isImpresaLavori).and(isProfessionista);

        Specification<MudeTContatto> specFilters = null;
        if (TipoContatto.PF.equals(tipoContatto)) {
            Map nome = FilterUtil.getValue(filter, "nome");
            Map cognome = FilterUtil.getValue(filter, "cognome");

            // exclude t_contatto IDs
            String excludeIds = FilterUtil.getStringValue(filter, "excludeIDs");

            Map _idIstanza = FilterUtil.getValue(filter, "idIstanza");
            Long idIstanza = _idIstanza == null? null : ((Number)_idIstanza.get("eq")).longValue();

            Specification<MudeTContatto> nomeLike = MudeTContattoSpecification.hasNome(nome);
            Specification<MudeTContatto> cognomeLike = MudeTContattoSpecification.hasCognome(cognome);
            Specification<MudeTContatto> hasExcludeIDs = MudeTContattoSpecification.hasExcludeIDs(excludeIds);

            Specification<MudeTContatto> userEquals = null;
            Specification<MudeTContatto> cfNotIn = null;
            Specification<MudeTContatto> includePermittedContacts = null;

            // "CONTACTS": returns just "contatti"
            // "CONTACTS_AND_PERMITTED": returns "contatti" + soggetti coinv linked to "idIstanza"
            Map _searchScope = FilterUtil.getValue(filter, "searchScope");
            String searchScope = _searchScope != null? (String)_searchScope.get(QueryOpEnum.EQUALS.getValue()) : null;

            if ("CONTACTS_AND_ACCREDITED".equals(searchScope))
                // only "CONTACTS_AND_ACCREDITED" option allows to include accredited contacts
                includePermittedContacts = MudeTContattoSpecification.includeAllMUDEAccreditedContacts(idIstanza, mudeTUser.getIdUser());

            if ("CONTACTS_AND_PERMITTED".equals(searchScope))
                // only "CONTACTS_AND_PERMITTED" option allows to include contact contained in istanzaUtente
                includePermittedContacts = MudeTContattoSpecification.includePermittedContacts(idIstanza, mudeTUser.getIdUser());
            else
	            // include all contact from the logged user
	            userEquals = MudeTContattoSpecification.hasUserEquals(mudeTUser.getCf());

            if (mudeTUser != null && !"CONTACTS_SELF_INCLUDED".equals(searchScope)) {
                // esclude logged user to contacts
                List<String> cfExcluded = null;
                cfExcluded = new ArrayList<>();
                cfExcluded.add(mudeTUser.getCf().trim().toUpperCase());
                Map m = new HashMap();
                m.put(QueryOpEnum.NOT_IN.getValue(),cfExcluded);
                cfNotIn = MudeTContattoSpecification.hasStringList(m);
            }
            specFilters = Specifications.where(nomeLike).and(cognomeLike).and(hasExcludeIDs).and(userEquals).and(includePermittedContacts).and(cfNotIn);
        } else { //pg
            Map ragioneSociale = FilterUtil.getValue(filter, "ragioneSociale");
            Specification<MudeTContatto> ragioneSocialeLike = MudeTContattoSpecification.hasRagioneSociale(ragioneSociale);
            Specification<MudeTContatto> userEquals = MudeTContattoSpecification.hasUserEquals(mudeTUser.getCf());
            specFilters = Specifications.where(ragioneSocialeLike).and(userEquals);
        }

        Specification<MudeTContatto> filters = Specifications.where(commonFilters).and(specFilters);

        Page<MudeTContatto> mudeTContattos = mudeTContattoRepository.findAll(filters, pageble);

        List<ContattoVO> conatattoVOs = contattoEntityMapper.mapListEntityToListVO(mudeTContattos.getContent(), mudeTUser);
        return PaginationResponseUtil.buildResponse(conatattoVOs, page, size, mudeTContattos.getTotalPages(), mudeTContattos.getTotalElements());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteContatto(String piva, String cf, MudeTUser mudeTUser) {
        List<MudeTContatto> mudeTContattos = null;

        if (StringUtils.isNotBlank(cf))
            mudeTContattos = mudeTContattoRepository.findAllByCf(cf, mudeTUser.getCf());
        else
            mudeTContattos = mudeTContattoRepository.findAllByPivaOrPivaCom(piva, mudeTUser.getCf());
        for (MudeTContatto mudeTContatto : mudeTContattos) {
            if(mudeRPfPgRepository.isContactLinkedToOtherContact(mudeTContatto.getId()))
            	throw new BusinessException("Non è possibile eliminare un contatto che sia rappresentante legale di un altro contatto.");
        	
            pfPgService.deleteByPG(mudeTContatto.getId());
        }

        mudeTContattoRepository.delete(mudeTContattos);

        for (MudeTContatto mudeTContatto : mudeTContattos)
            utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_CONTATTO_RUBRICA.getOperation(), mudeTContatto.getTableName(), "id=" + mudeTContatto.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

  /*  @Override
    public ContattoPFVO findPfByUUID(UUID uuid, String userCf) {
        MudeTContatto pf = this.findByUUID(uuid,userCf);
        if(null != pf){
            return contattoPFEntityMapper.mapEntityToVO(pf);
        }
        return null ;
    }

    @Override
    public ContattoPGVO findPgByUUID(UUID uuid, String userCf) {
        MudeTContatto pg = this.findByUUID(uuid,userCf);
        if(null != pg){
            return contattoPGEntityMapper.mapEntityToVO(pg);
        }
        return null ;
    }

    private MudeTContatto findByUUID(UUID uuid, String userCf) {
        Optional<MudeTContatto> contattoOpt = mudeTContattoRepository.findByUuidAndDataCessazioneNull(uuid,userCf);
        if(contattoOpt.isPresent()){
            return contattoOpt.get();
        }
        return null;
    }
*/
    @Override
    public Boolean isContattoDeletable(String piva, String cf, String userCf) {
        List<MudeTContatto> mudeTContattos = null;
        if (StringUtils.isNotBlank(piva)) {
            mudeTContattos = mudeTContattoRepository.findAllByPivaOrPivaCom(piva, userCf);
        } else {
            mudeTContattos = mudeTContattoRepository.findAllByCf(cf, userCf);
        }

        if (null != mudeTContattos && !mudeTContattos.isEmpty()) {
            List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettoList = mudeRIstanzaSoggettoRepository.findByMudeTContattoIn(mudeTContattos);
            if (null != mudeRIstanzaSoggettoList && !mudeRIstanzaSoggettoList.isEmpty()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public ContattoVO findContattoByID(Long id) {
        if (id != null) {
            MudeTContatto contatto = mudeTContattoRepository.findOne(id);
            if (null != contatto) {
                return contattoEntityMapper.mapEntityToVO(contatto, null);
            }
        }

        return null;
    }

    @Override
    public ContattoVO findByPiva(String piva, String userCF) {
        MudeTContatto contatto = mudeTContattoRepository.findByPivaOrPivaComAndDataCessazioneNull(piva, userCF);
        if (null != contatto) {
            return contattoEntityMapper.mapEntityToVO(contatto, null);
        }
        return null;
    }

    @Override
    public ContattoVO findByCF(String cf, String userCF) {
        Optional<MudeTContatto> contattoOpt = mudeTContattoRepository.findByCfAndDataCessazioneNull(cf, userCF);
        if (contattoOpt.isPresent()) {
            return contattoEntityMapper.mapEntityToVO(contattoOpt.get(), null);
        }
        return null;
    }

    @Override
    public ContattoVO findAccreditedContactByIdUser(Long idUser) {
        return contattoEntityMapper.mapEntityToVO(mudeTContattoRepository.findByIdUser(idUser), null);
    }

    @Override
    public ContattoVO findPrivateContactByAccreditedIdUser(Long idAccreditedUser, MudeTUser mudeTUser) {
        return contattoEntityMapper.mapEntityToVO(mudeTContattoRepository.findPrivateContactByAccreditedIdUser(mudeTUser.getIdUser(), idAccreditedUser), null);
    }

    public String recuperaDestinatariNotifiche(Long idIstanza,Long idUser){
    	//Destinatari
    	List<MudeTContatto> contattoDestinatari = new ArrayList<MudeTContatto>();    	
    	List<MudeTContatto> contattoCreatore = mudeTContattoRepository.findCreatoreByIdIstanza(idIstanza);
    	if(contattoCreatore != null) {
    		boolean bTrovato=false;
    		String guid = "";
    		for(MudeTContatto mudeTContatto :contattoCreatore) {
    			if(mudeTContatto.getDataCessazione()==null) {
    				contattoDestinatari.add(mudeTContatto);
    				bTrovato=true;
    				break;
    			}else {
    				guid = mudeTContatto.getGuid();
    			}
    		}
    		if(!bTrovato && guid != null && !guid.isEmpty()) {
    			MudeTContatto contattoCreatoreModificato = mudeTContattoRepository.findCreatoreByIdIstanzaAndDataCessazioneNull(idIstanza,guid);
    			contattoDestinatari.add(contattoCreatoreModificato);
    		}
    		
    	}
    	MudeTContatto contattoProfessionista = mudeTContattoRepository.findPMByIdIstanza(idIstanza, idUser);
    	if(contattoProfessionista != null)
    		contattoDestinatari.add(contattoProfessionista);
    	
    	MudeTContatto contattoColaboratore= mudeTContattoRepository.findCollaboratorePMByIdIstanza(idIstanza, idUser);
    	if(contattoColaboratore != null)
    		contattoDestinatari.add(contattoColaboratore);
    	
    	String destinatariStr="";
    	if(contattoDestinatari != null && !contattoDestinatari.isEmpty())
    	{
    		for(MudeTContatto mudeTContatto:contattoDestinatari) {
    			if(mudeTContatto.getMail() != null && StringUtils.isNotBlank(mudeTContatto.getMail())) {
    				if(!destinatariStr.contains(mudeTContatto.getMail()))	
    					destinatariStr	= destinatariStr + mudeTContatto.getMail()+ "; ";
    			}
    			else if(mudeTContatto.getPec() != null && StringUtils.isNotBlank(mudeTContatto.getPec()))
    				if(!destinatariStr.contains(mudeTContatto.getPec()))	
    					destinatariStr	= destinatariStr + mudeTContatto.getPec()+ "; ";
    		}
    	}
    	

        return destinatariStr;
    }

    @Override
    public void refreshInstanceContacts(MudeTIstanza istanza, MudeTUser mudeTUser) {
    	for(MudeRIstanzaSoggetto mudeRIstanzaSoggetto : mudeRIstanzaSoggettoRepository.findByMudeTIstanza(istanza)) {
    		MudeTContatto mudeTContatto = mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(
											    													mudeRIstanzaSoggetto.getMudeTContatto().getGuid(), 
											    													mudeTUser.getIdUser());
        	ContattoVO savedContattoVO = contattoEntityMapper.mapEntityToVO(mudeTContatto, mudeTUser);
    		
	        // update all contact in instances
	        mudeRIstanzaSoggettoRepository.updateContactForInstances(""+mudeTContatto.getId(), mudeTContatto.getGuid(), mudeTUser.getIdUser(), JsonUtils.toJsonString(savedContattoVO), istanza.getId());
	        mudeRIstanzaSoggettoRepository.updateSubjectsForInstances(mudeTContatto.getId(), mudeTContatto.getGuid(), mudeTUser.getIdUser());
    	}
   }

}