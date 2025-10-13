/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbilitazioneEntityMapperImpl implements AbilitazioneEntityMapper {
    @Override
    public AbilitazioneVO mapEntityToVO(MudeDAbilitazione dto) {
        if (dto == null)
            return null;

        AbilitazioneVO vo = new AbilitazioneVO();
        vo.setId(dto.getId());
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setTipo(dto.getTipo());
        vo.setNecessariaIscrizioneAlbo(null != dto.getNecessariaIscrizioneAlbo()? dto.getNecessariaIscrizioneAlbo() : Boolean.FALSE);
        vo.setNecessariaPresenzaInIstanza(null != dto.getNecessariaPresenzaInIstanza()? dto.getNecessariaPresenzaInIstanza() : Boolean.FALSE);
        vo.setNecessariaSelezioneQuadro(null != dto.getNecessariaSelezioneQuadro()? dto.getNecessariaSelezioneQuadro() : Boolean.FALSE);

        return vo;
    }

    @Override
    public List<AbilitazioneVO> mapListEntityToListVO(List<MudeDAbilitazione> tl) {
        return AbilitazioneEntityMapper.super.mapListEntityToListVO(tl);
    }
}