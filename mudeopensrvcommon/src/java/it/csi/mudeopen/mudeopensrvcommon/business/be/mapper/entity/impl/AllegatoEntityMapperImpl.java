/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllegatoEntityMapperImpl implements AllegatoEntityMapper {

    //    @Autowired
    //    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;

    @Override
    public AllegatoVO mapEntityToVO(MudeTAllegato dto, MudeTUser mudeTUser, String filters) {
        if (dto == null)
            return null;
        AllegatoVO vo = new AllegatoVO();
        vo.setId(dto.getId());
        vo.setNomeFileAllegato(dto.getNomeFileAllegato());
        vo.setDimensioneFile(dto.getDimensioneFile());
        vo.setDescBreveAllegato(dto.getDescBreveAllegato());
        vo.setFileContent(dto.getFileContent());
        vo.setFileUID(dto.getFileUID());
        vo.setFirmato(dto.getFirmato());
        vo.setMimetype(dto.getMimetype());
        vo.setDataCaricamento(dto.getDataCaricamento());
        vo.setProtocollo(dto.getProtocollo());
        vo.setDataProtocollo(dto.getDataProtocollo());
        vo.setTipoAllegato(tipoAllegatoEntityMapper.mapEntityToVO(dto.getTipoAllegato(), null));

        if(!hasFilter(filters, "essential")) {
	        if(null != dto.getUser())
	            vo.setUtente(utenteEntityMapper.mapEntityToVO(dto.getUser()));
        	
	        if(!hasFilter(filters, "slim"))
		        vo.setIstanza(istanzaEntityMapper.mapEntityToVO(dto.getIstanza(), mudeTUser));
        }

        return vo;
    }

    @Override
    public MudeTAllegato mapVOtoEntity(AllegatoVO vo,MudeTUser user) {
                return null;
    }
}