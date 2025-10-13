/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTModello;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ModelloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTModelloRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ModelloEntityMapperImpl implements ModelloEntityMapper {

    @Autowired
    private MudeTModelloRepository mudeTModelloRepository;

    @Override
    public ModelloVO mapEntityToVO(MudeTModello dto, MudeTUser user) {
        if (dto == null) {
            return null;
        }
        ModelloVO vo = new ModelloVO();
        vo.setId(dto.getId());
        vo.setDenominazione(dto.getDenominazione());
        vo.setPathModello(dto.getPathModello());
        vo.setDimensioneFile(dto.getDimensioneFile());
        vo.setFileContent(dto.getFileContent());

        return vo;
    }

    @Override
    public MudeTModello mapVOtoEntity(ModelloVO vo, MudeTUser user) {
        if (vo == null) {
            return null;
        }
        MudeTModello entity = null;
        if (null != vo.getId()) {
            Optional<MudeTModello> opt = mudeTModelloRepository.findByIdModello(vo.getId());
            if (opt.isPresent()) {
                entity = opt.get();
            } else {
                return null;
            }
        } else {
            entity = new MudeTModello();
        }

        entity.setDenominazione(vo.getDenominazione());
        entity.setPathModello(vo.getPathModello());
        entity.setDimensioneFile(vo.getDimensioneFile());
        entity.setFileContent(vo.getFileContent());

        return entity;
    }
}