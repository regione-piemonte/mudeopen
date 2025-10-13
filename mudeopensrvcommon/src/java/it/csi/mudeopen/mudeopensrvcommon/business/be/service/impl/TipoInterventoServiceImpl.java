/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import com.google.common.collect.Lists;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoInterventoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoInterventoServiceImpl implements TipoInterventoService {

    @Autowired
    private MudeDTipoInterventoRepository mudeDTipoInterventoRepository;

    @Override
    public List<SelectVO> findByTipoIstanzaForCreaFascicoloIstanza(String codiceTipoIstanza) {
        List<MudeDTipoIntervento> entities = mudeDTipoInterventoRepository.findByTipoIstanzaForCreaFascicoloIstanza(codiceTipoIstanza);
        List<SelectVO> tipiIntervento = Lists.newArrayListWithCapacity(entities.size());
        for (MudeDTipoIntervento entity : entities) {
            SelectVO sVO = new SelectVO();
            sVO.setId(entity.getCodice());
            sVO.setDescrizione(entity.getDescrizione());
            tipiIntervento.add(sVO);
        }
        return tipiIntervento;
    }
}