/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ModelloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoExtendedEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTModelloRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;

@Component
public class TipoAllegatoExtendedEntityMapperImpl implements TipoAllegatoExtendedEntityMapper {

//    @Autowired
//    private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;

    @Autowired
    private CategoriaAllegatoEntityMapper categoriaAllegatoEntityMapper;

    @Autowired
    private ModelloEntityMapper modelloEntityMapper;

    @Autowired
    private MudeDTipoAllegatoRepository mudeDTipoAllegatoRepository;

    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

    @Autowired
    private MudeTModelloRepository mudeTModelloRepository;

    @Autowired
    private MudeRTemplateTipoAllegatoRepository mudeRTemplateTipoAllegatoRepository;

    @Override
    public TipoAllegatoExtendedVO mapEntityToVO(MudeRTemplateTipoAllegato dto, MudeTUser user) {
        if (dto == null) return null;

        TipoAllegatoExtendedVO item = new TipoAllegatoExtendedVO();

        item.setId(dto.getId());
        item.setCodice(dto.getTipoAllegato().getCodice());
        item.setDescrizione(dto.getTipoAllegato().getDescrizione());
        item.setDescrizioneEstesa(dto.getTipoAllegato().getDescrizioneEstesa());
        item.setCategoriaAllegato(categoriaAllegatoEntityMapper.mapEntityToVO(dto.getTipoAllegato().getCategoriaAllegato(), null));
        item.setSubCodeTipoAllegato(dto.getTipoAllegato().getSubCodeTipoAllegato());
        item.setFirmaObbligatoria(dto.getTipoAllegato().getFirmaObbligatoria());
        item.setValidaFirma(dto.getTipoAllegato().getValidaFirma());
        item.setOrdinamento(dto.getOrdinamento());
        item.setObbligatorio(dto.getObbligatorio());
        item.setRipetibile(dto.getRipetibile());
        item.setEspressioneObbligatorieta(dto.getEspressioneObbligatorieta());
        item.setEspressioneRipetibilita(dto.getEspressioneRipetibilita());
        item.setFlagRicorrente(dto.getFlagRicorrente());

        if(dto.getTemplate() != null)
        	item.setIdTemplate(dto.getTemplate().getIdTemplate());

        if(dto.getModello() != null) {
        	item.setModello(modelloEntityMapper.mapEntityToVO(dto.getModello(), null));
        	item.getModello().setFileContent(null); // bytearray will be get from API
        }

        return item;
    }

    @Override
    public MudeRTemplateTipoAllegato mapVOtoEntity(TipoAllegatoExtendedVO vo, MudeTUser user) {
    	MudeRTemplateTipoAllegato dto;
    	
    	if(vo.getId() == null)
        	dto = new MudeRTemplateTipoAllegato();
    	else
    		dto = mudeRTemplateTipoAllegatoRepository.findOne(vo.getId());
    	
        dto.setOrdinamento(vo.getOrdinamento());
        dto.setObbligatorio(vo.getObbligatorio());
        dto.setRipetibile(vo.getRipetibile());
        dto.setEspressioneObbligatorieta(vo.getEspressioneObbligatorieta());
        dto.setEspressioneRipetibilita(vo.getEspressioneRipetibilita());
        dto.setFlagRicorrente(vo.getFlagRicorrente());

    	if(vo.getIdTemplate() != null)
    		dto.setTemplate(mudeDTemplateRepository.findOne(vo.getIdTemplate()));

    	if(vo.getCodice() != null)
    		dto.setTipoAllegato(mudeDTipoAllegatoRepository.findOne(vo.getCodice()));

		if(vo.getModello() != null && vo.getModello().getId() != null)
			dto.setModello(vo.getModello().getDimensioneFile() == -1 /* delete */ ? null : mudeTModelloRepository.findOne(vo.getModello().getId()));
		
    	return dto;
    }

}