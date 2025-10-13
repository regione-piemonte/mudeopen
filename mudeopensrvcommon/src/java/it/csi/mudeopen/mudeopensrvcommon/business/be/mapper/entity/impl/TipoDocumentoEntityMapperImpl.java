/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDCategoriaAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.TipoDocumentoVO;

@Component
public class TipoDocumentoEntityMapperImpl implements TipoDocumentoEntityMapper {
    @Override
    public TipoDocumentoVO mapEntityToVO(MudeDTipoDocPA dto, MudeTUser user) {
        if (dto == null) {
            return null;
        }
        TipoDocumentoVO tipoDocumentoVO = new TipoDocumentoVO();
        tipoDocumentoVO.setCodice(dto.getCodeTipoDocpa());
        tipoDocumentoVO.setDescrizione(dto.getDescTipoDocpa());
        tipoDocumentoVO.setId(dto.getId());
        return tipoDocumentoVO;
    }

	@Override
	public MudeDTipoDocPA mapVOtoEntity(TipoDocumentoVO vo, MudeTUser user) {
		// TODO Auto-generated method stub
		return null;
	}
}