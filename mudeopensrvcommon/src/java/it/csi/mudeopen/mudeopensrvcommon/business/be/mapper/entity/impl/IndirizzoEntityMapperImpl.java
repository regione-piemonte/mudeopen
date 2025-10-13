/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IndirizzoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.NazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProvinciaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndirizzoEntityMapperImpl implements IndirizzoEntityMapper {

    @Autowired
    private ComuneEntityMapper comuneEntityMapper;

    @Autowired
    private ProvinciaEntityMapper provinciaEntityMapper;

    @Autowired
    private NazioneEntityMapper nazioneEntityMapper;

    @Autowired
    private MudeDDugRepository mudeDDugRepository;

    @Override
    public IndirizzoVO mapEntityToVO(MudeTIndirizzo dto) {
        IndirizzoVO vo = null;
        if(null != dto){
            vo = new IndirizzoVO();
            vo.setId(dto.getId());
            if(StringUtils.isNotBlank(dto.getCap())) {
                vo.setCap(dto.getCap());
            }
            if(null != dto.getMudeDComune()) {
                vo.setComune(comuneEntityMapper.mapEntityToVO(dto.getMudeDComune()));
                MudeDProvincia provincia = dto.getMudeDComune().getMudeDProvincia();
                if(null != provincia){
                    vo.setProvincia(provinciaEntityMapper.mapEntityToVO(provincia));
                }
            }
            if(StringUtils.isNotBlank(dto.getCellulare())) {
                vo.setCellulare(dto.getCellulare());
            }
            if(StringUtils.isNotBlank(dto.getDenomComuneEstero())) {
                vo.setComuneIndirizzoEstero(dto.getDenomComuneEstero());
            }
            if(StringUtils.isNotBlank(dto.getIndirizzo())) {
                vo.setDuf(dto.getIndirizzo());
            }
            if(null != dto.getIdDug()) {
                MudeDDug dug = mudeDDugRepository.findByIdDug(dto.getIdDug());
                if(null != dug) {
                    SelectVO selectVO = new SelectVO();
                    selectVO.setDescrizione(dug.getDenominazione());
                    selectVO.setId(String.valueOf(dug.getIdDug()));
                    vo.setDug(selectVO);
                }
            }
            if(StringUtils.isNotBlank(dto.getLocalita())) {
                vo.setLocalita(dto.getLocalita());
            }
            if(StringUtils.isNotBlank(dto.getMail())) {
                vo.setMail(dto.getMail());
            }
            if(StringUtils.isNotBlank(dto.getNumeroCivico())) {
                vo.setNumero(dto.getNumeroCivico());
            }
            if(StringUtils.isNotBlank(dto.getPec())) {
                vo.setPec(dto.getPec());
            }
            if(null != dto.getMudeDNazione()) {
                vo.setStato(nazioneEntityMapper.mapEntityToVO(dto.getMudeDNazione()));
            }
            if(StringUtils.isNotBlank(dto.getTelefono())) {
                vo.setTelefono(dto.getTelefono());
            }
            if(null != dto.getTipoIndirizzo()) {
                SelectVO selectVO = new SelectVO();
                selectVO.setId(String.valueOf(dto.getTipoIndirizzo().getId()));
                selectVO.setDescrizione(dto.getTipoIndirizzo().getLabel());
                vo.setTipologiaIndirizzo(selectVO);
            }
        }
        return vo;
    }

    @Override
    public List<IndirizzoVO> mapListEntityToListVO(List<MudeTIndirizzo> tl) {
        return IndirizzoEntityMapper.super.mapListEntityToListVO(tl);
    }
}