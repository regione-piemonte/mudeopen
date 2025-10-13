/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDPPayImporto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PpayImportoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDPPayImportiRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.PPayImportoVO;

@Component
public class PpayImportoEntityMapperImpl implements PpayImportoEntityMapper {
    @Autowired
    MudeDPPayImportiRepository mudeDPPayImportiRepository;

    @Override
    public PPayImportoVO mapEntityToVO(MudeDPPayImporto dto, MudeTUser user) {
    	PPayImportoVO vo = new PPayImportoVO();
    	
    	Number importo = dto.getImporto();
    	// find top level (enabled) default amount, in case of "importo" null
		for(MudeDPPayImporto importoDefault = dto.getImportoDefault(); importo == null && importoDefault.getImportoDefault() != null && (importo = importoDefault.getImportoDefault().getImporto()) == null; ) 
			importoDefault = importoDefault.getImportoDefault();
    	
    	if(importo != null)
    		vo.setImporto(importo); // String.format(Locale.ITALIAN, "%.2f", importo)
    	
    	vo.setIdImporto(dto.getId());
    	vo.setIdEnte(dto.getEnte().getId());
    	vo.setDatiSpecRiscossione(dto.getDatiSpecificiRiscossione());
    	if(dto.getTipoIstanza() != null)
    		vo.setIdTipoIstanza(dto.getTipoIstanza().getCodice());
    	vo.setIdSpeciePratica(dto.getSpeciePratica() == null?null : dto.getSpeciePratica().getCodice());
    	vo.setTipoImporto(dto.getTipoImporto());
    	vo.setDescrizione(dto.getDescrizione());
    	
    	return vo;
    }

    @Override
    public MudeDPPayImporto mapVOtoEntity(PPayImportoVO vo, MudeTUser user) {
    	MudeDPPayImporto entity = new MudeDPPayImporto();
        if (null != vo.getIdImporto())
            entity = mudeDPPayImportiRepository.findOne(vo.getIdImporto());

        entity.setDatiSpecificiRiscossione(vo.getDatiSpecRiscossione());
        //use mapper: entity.setIdTipoIstanza(vo.getIdTipoIstanza());
        //use mapper: entity.setIdSpeciePratica(vo.getIdSpeciePratica());
        entity.setTipoImporto(vo.getTipoImporto());
        entity.setDescrizione(vo.getDescrizione());
        //convert: entity.setImporto(vo.getImporto());
        return entity;
    }

}