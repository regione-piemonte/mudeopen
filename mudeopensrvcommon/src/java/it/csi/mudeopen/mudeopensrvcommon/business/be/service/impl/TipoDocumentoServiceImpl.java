/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoDocumentoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.TipoDocumentoVO;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	@Autowired
	private MudeDTipoDocpaRepository mudeDTipoDocpaRepository;

	@Autowired
	private TipoDocumentoEntityMapper tipoDocumentoEntityMapper;

	@Override
	public TipoDocumentoVO findByCodTipoDocumento(String codTipoDocumento) {
		MudeDTipoDocPA tipoDocumento = mudeDTipoDocpaRepository.findByCodice(codTipoDocumento);
		if (tipoDocumento != null){
			return tipoDocumentoEntityMapper.mapEntityToVO(tipoDocumento, null);
		}
		return null;
	}

}