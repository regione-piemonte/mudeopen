/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDTipoDocpaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;

/**
 * The type MudeopenDTipoDocpa service.
 */
@Service
public class MudeopenDTipoDocpaServiceImpl implements MudeopenDTipoDocpaService {

	@Autowired
	private MudeopenDTipoDocpaRepository mudeopenDTipoDocpaRepository;

	@Override
	public List<MudeDTipoDocPA> findAllActive() {
		return mudeopenDTipoDocpaRepository.findAllActive();
	}

}