/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDTipoNotificaService;
import it.csi.mudeopen.mudeopensrvapi.vo.TipoNotificaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoNotificaRepository;

@Service
public class MudeopenDTipoNotificaServiceImpl implements MudeopenDTipoNotificaService {

	
	@Autowired
	private MudeDTipoNotificaRepository mudeDTipoNotificaRepository;
	
	@Override
	public List<TipoNotificaVO> findAllValid() {
		List<MudeDTipoNotifica> notifiche = mudeDTipoNotificaRepository.findAllValid();
		List<TipoNotificaVO> vos = new ArrayList<>();
		for (MudeDTipoNotifica mudeDTipoNotifica : notifiche) {
			vos.add(new TipoNotificaVO(mudeDTipoNotifica.getSubCodTipoNotifica(), mudeDTipoNotifica.getDesTipoNotifica()));
		}
		return vos;
	}

	@Override
	public List<MudeDTipoNotifica> findByCodTipoNotifica(String codTipoNotifica) {
		return mudeDTipoNotificaRepository.findByCodTipoNotificaAndValid(codTipoNotifica);
	}

	/**
	 * resistisce il primo tipo notifica trovato
	 */
	@Override
	public Optional<MudeDTipoNotifica> findBySubCodTipoNotifica(String subCodTipoNotifica) {
		List<MudeDTipoNotifica> codTipoNotifica = mudeDTipoNotificaRepository.findBySubCodTipoNotifica(subCodTipoNotifica);
		return codTipoNotifica.stream().findFirst();
	}
	

}
