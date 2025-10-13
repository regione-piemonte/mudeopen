/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProfiloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDNazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTContattoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTUserSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.AnagraficaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService {

    private static final String BACKOFFICE = "backoffice";

	private static Logger logger = Logger.getLogger(UtenteServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

    @Autowired
    private ProfiloEntityMapper profiloEntityMapper;

    @Autowired
    private ContattoService contattoService;

    @Autowired
    private MudeDNazioneRepository mudeDNazioneRepository;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private MudeDProvinciaRepository mudeDProvinciaRepository;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    //    @Autowired
    //    private ContattoPGEntityMapper contattoPGEntityMapper;

    @Override
    public ProfiloResponse getProfiloByCF(String cf, String scope) {
        MudeTUser user = mudeTUserRepository.findByCf(cf);
        if (user != null) {
            // controllo la validita', se impostata
            Date now = new Date();
            if ((user.getInizioValidita() != null && user.getInizioValidita().after(now)) || (user.getFineValidita() != null && user.getFineValidita().before(now))) {
                user = null;
            }
            // Fix ACCREDITAMENTO Utente BO
            if (BACKOFFICE.equals(scope) && user != null && !user.getUtenteBo()) {
                user = null;
            }
            else if (BACKOFFICE.equals(scope) && user != null && user.getUtenteBo() && user.getFineValiditaBo() != null && user.getFineValiditaBo().before(now)) {
                user = null;
            }
            // Fix ACCREDITAMENTO Utente FO
            else if (scope == null && user != null && !user.getUtenteFo()) {
                user = null;
            }
            else if (scope == null && user != null && !user.getUtenteFo() && user.getFineValidita() != null && user.getFineValidita().before(now)) {
                user = null;
            }
        }

        ProfiloResponse response = profiloEntityMapper.mapEntityToVO(user);

        if (response.getInfoUtente() == null) {
            UtenteVO infoUtente = new UtenteVO();
            infoUtente.getContatto().getAnagrafica().setCodiceFiscale(cf);
            response.setInfoUtente(infoUtente);
        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void salvaProfilo(UtenteVO utente) {
    	_salvaProfilo(utente, true);
    }
    
    private void _salvaProfilo(UtenteVO utente, boolean valdidated) {
        ContattoVO contatto = utente.getContatto();
        AnagraficaVO anagrafica = contatto.getAnagrafica();
        MudeTUser user = null;
        try {
            user = mudeTUserRepository.findByCf(anagrafica.getCodiceFiscale());
        } catch (Throwable t) {
        }

        if (user == null) {
            user = new MudeTUser();

            Date inizioValidita = new Date();
            user.setInizioValidita(inizioValidita);

            //todo - da capire la politica di validita' dello user
            Date fineValidita = null;
            user.setFineValidita(fineValidita);
        }
        user.setCf(anagrafica.getCodiceFiscale());
        user.setCognome(anagrafica.getCognome());
        user.setMail(anagrafica.getMail());
        user.setNome(anagrafica.getNome());
        user.setTelefono(anagrafica.getTelefono());
        user.setCellulare(anagrafica.getCellulare());
        user.setPec(anagrafica.getPec());
        user.setSesso(anagrafica.getSesso());
        user.setStatoEstero(anagrafica.getStatoEstero());

        if (anagrafica.getDataNascita() != null) {
            user.setDataNascita(Timestamp.valueOf(anagrafica.getDataNascita().atTime(LocalTime.MIDNIGHT)));
        }
        if (anagrafica.getProvinciaNascita() != null && anagrafica.getProvinciaNascita().getId() != null) {
            MudeDProvincia provinciaNascita = mudeDProvinciaRepository.findOne(anagrafica.getProvinciaNascita().loadIdAsLong());
            user.setProvinciaNascita(provinciaNascita);

        }
        if (anagrafica.getComuneNascita() != null && anagrafica.getComuneNascita().getId() != null) {
            MudeDComune comuneNascita = mudeDComuneRepository.findByIdComune(anagrafica.getComuneNascita().loadIdAsLong());
            user.setComuneNascita(comuneNascita);

        }
        if (anagrafica.getStatoNascita() != null && anagrafica.getStatoNascita().getId() != null) {
            MudeDNazione statoNascita = mudeDNazioneRepository.findByIdNazione(anagrafica.getStatoNascita().loadIdAsLong());
            user.setStatoNascita(statoNascita);
        }

        user.setUtenteFo(true);
        user.setValidatoDaUtente(valdidated);
        
        mudeTUserRepository.saveDAO(user);
        utente.setId(user.getIdUser());

        contattoService.saveContatto(contatto, null, user);
    }

    private List<ContattoVO> getInfoContatto(MudeTUser mudeTUser) {
        Pageable pageble = PageUtil.getPageable(0, 1, null);

        Specification<MudeTContatto> isNotDeleted = MudeTContattoSpecification.isNotDeleted();
        HashMap map = new HashMap();
        map.put(QueryOpEnum.EQUALS.getValue(),mudeTUser.getCf());
		Specification<MudeTContatto> cfEquals = MudeTContattoSpecification.hasCF(map);
        Specification<MudeTContatto> userEquals = MudeTContattoSpecification.hasUserEquals(mudeTUser.getCf());
        Specification<MudeTContatto> tipoContattoEquals = MudeTContattoSpecification.hasTipoContattoEquals(TipoContatto.PF);

        Specification<MudeTContatto> filters = Specifications.where(isNotDeleted).and(cfEquals).and(userEquals).and(tipoContattoEquals);

        Page<MudeTContatto> mudeTContattos = mudeTContattoRepository.findAll(filters, pageble);

        List<ContattoVO> conatattoVOs = contattoEntityMapper.mapListEntityToListVO(mudeTContattos.getContent(), mudeTUser);

        return conatattoVOs;
    }

    public Response recuperaAccreditamenti(MudeTUser mudeTUser, String filter, int page, int size, String sortExp) {
        Pageable pageble = PageUtil.getPageable(page, size, sortExp);

        Specification<MudeTUser> hasNomeLike = MudeTUserSpecification.hasNomeLike(FilterUtil.getValue(filter, "nome"));
        Specification<MudeTUser> hasCognomeLike = MudeTUserSpecification.hasCognomeLike(FilterUtil.getValue(filter, "cognome"));
        Specification<MudeTUser> hasCFLike = MudeTUserSpecification.hasCF(FilterUtil.getValue(filter, "cf"));
        Specification<MudeTUser> hasExcludeIDs = MudeTUserSpecification.hasExcludeIDs(FilterUtil.getStringValue(filter, "excludeIDs"));
        Specification<MudeTUser> searchFO = MudeTUserSpecification.searchFO();

        Page<MudeTUser> pages = mudeTUserRepository.findAll(Specifications.where(hasCFLike).and(hasNomeLike).and(hasCognomeLike).and(hasExcludeIDs).and(searchFO), pageble);

        List<UtenteVO> tipoQuadroVOs = utenteEntityMapper.mapListEntityToListVO(pages.getContent());
        return PaginationResponseUtil.buildResponse(tipoQuadroVOs, page, size, pages.getTotalPages(), pages.getTotalElements());
    }
    
    public Long accreditaContatto(MudeTContatto contattoSorgente) {
    	if(contattoSorgente == null) 
    		return null;
    	
    	MudeTUser mudeTUser = mudeTUserRepository.findByCf(contattoSorgente.getCf());
    	if(mudeTUser != null) 
    		return mudeTUser.getIdUser();
    	
    	ContattoVO contattoVO = contattoEntityMapper.mapEntityToVO(contattoSorgente, null, "fill-in-rappresentante");
    	if(TipoContatto.PG == contattoVO.getTipoContatto() )
    		contattoVO = contattoVO.getLegaleRappresentante();
    	
    	UtenteVO utente = new UtenteVO();
    	utente.setContatto(contattoVO);

    	_salvaProfilo(utente, false);
    	return utente.getId();
    }

}