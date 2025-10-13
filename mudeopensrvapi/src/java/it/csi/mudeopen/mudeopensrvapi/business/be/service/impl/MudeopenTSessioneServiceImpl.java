/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTSessioneRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTSessioneService;

/**
 * The type MudeopenTSessione service.
 */
@Service
public class MudeopenTSessioneServiceImpl implements MudeopenTSessioneService {

	@Autowired
	private MudeopenTSessioneRepository mudeopenTSessioneRepository;

	public MudeopenTSessione save(MudeopenTSessione sessione) {
		return mudeopenTSessioneRepository.saveDAO(sessione);
	}

	/*
	@Override
	public MudeopenTSessione findByToken(UUID token) {
		return mudeopenTSessioneRepository.findByIdSessione(token);
	}
	*/

	@Override
	public void delete(MudeopenTSessione sessione) {
		mudeopenTSessioneRepository.delete(sessione);
	}

	@Override
	public String getUUID() {
		return mudeopenTSessioneRepository.getUUID();
	}

}