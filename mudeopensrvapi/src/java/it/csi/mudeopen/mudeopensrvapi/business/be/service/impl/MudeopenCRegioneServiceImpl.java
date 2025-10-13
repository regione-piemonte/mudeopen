/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenCRegioneRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenCRegioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;

/**
 * The type MudeopenCRegione service.
 */
@Service
public class MudeopenCRegioneServiceImpl implements MudeopenCRegioneService {

	
	
	@Autowired
	private MudeopenCRegioneRepository mudeopenCRegioneRepository;

	@PersistenceContext // or even @Autowired
    private EntityManager em;
	
	public MudeCRegione save(MudeCRegione regione) {
		return mudeopenCRegioneRepository.saveDAO(regione);
	}

	@Transactional
	public Long getProgressive(MudeDRegione dRegione) throws Exception {
			
		MudeCRegione regione = em.find(MudeCRegione.class, dRegione.getIdRegione());
		if (regione == null) {
			regione = new MudeCRegione();
			regione.setMudeDRegione(dRegione);
			regione.setProgressivoAnno(0);
			regione.setDataModifica(new Date());
			regione = mudeopenCRegioneRepository.saveDAO(regione);
		}
		MudeCRegione regioneLocked = em.find(MudeCRegione.class, regione.getId(), LockModeType.PESSIMISTIC_WRITE);
		regioneLocked.setProgressivoAnno(regioneLocked.getProgressivoAnno() + 1);
		regioneLocked.setDataModifica(new Date());
		MudeCRegione regioneUpdated = mudeopenCRegioneRepository.saveDAO(regione);
		return regioneUpdated.getProgressivoAnno();
	}

}