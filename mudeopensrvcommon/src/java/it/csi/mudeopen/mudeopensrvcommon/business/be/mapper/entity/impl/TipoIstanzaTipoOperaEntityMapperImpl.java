/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaTipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaTipoOperaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoIstanzaTipoOperaEntityMapperImpl implements TipoIstanzaTipoOperaEntityMapper {

    @Autowired
    private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

    @Autowired
    private TipoOperaEntityMapper tipoOperaEntityMapper;

    @Override
    public TipoIstanzaTipoOperaVO mapEntityToVO(MudeRTipoIstanzaTipoOpera dto) {
        if(null == dto) {
            return null;
        }
        TipoIstanzaTipoOperaVO vo = new TipoIstanzaTipoOperaVO();
        DizionarioVO tipoOpera = tipoOperaEntityMapper.mapEntityToVO(dto.getMudeDTipoOpera());
        vo.setTipoOpera(tipoOpera);
        TipoIstanzaVO tipoIstanza = tipoIstanzaEntityMapper.mapEntityToVO(dto.getMudeDTipoIstanza());
        vo.setTipoIstanza(tipoIstanza);

        return vo;
    }
}