/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;

@Component
public class DocumentoEntityMapperImpl implements DocumentoEntityMapper {
    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private TipoDocumentoEntityMapper tipoDocumentoEntityMapper;

    @Autowired
    MudeRNotificaDocumentoRepository mudeRNotificaDocumentoRepository;
    @Override
    public List<DocumentoVO> mapListEntityToListVO(List<MudeTDocumento> dtoList, MudeTUser mudeTUser) {
        return mapListEntityToListVO(dtoList, mudeTUser, null);
    }

    @Override
    public List<DocumentoVO> mapListEntityToListVO(List<MudeTDocumento> dtoList, MudeTUser mudeTUser, String filters) {

    	List<DocumentoVO> voList = new ArrayList<DocumentoVO>();
    	for(MudeTDocumento dto : dtoList) {
	    	if (dto == null)
	            return null;
	    	
	        DocumentoVO vo = mapEntityToSlimVO(dto, mudeTUser, filters);
	        
	        if(!hasFilter(filters, "essential")) {
	            vo.setUtente(utenteEntityMapper.mapEntityToVO(dto.getUser()));
		        vo.setNotificato(mudeRNotificaDocumentoRepository.hasDocumentNotified(dto.getId()));
	        }

	        voList.add(vo);
    	}
        return voList;
    }

    @Override
    public MudeTDocumento mapVOtoEntity(DocumentoVO vo,MudeTUser user) {
    	return null;
    }

	@Override
	public DocumentoVO mapEntityToSlimVO(MudeTDocumento dto, MudeTUser user, String filter) {
		DocumentoVO vo = new DocumentoVO();
        vo.setId(dto.getId());
        vo.setNomeFileDocumento(dto.getNomeFileDocumento());
        vo.setDimensioneFile(dto.getDimensioneFile());
        vo.setFileContent(dto.getFileContent());
        vo.setFileUID(dto.getFileUID());
        vo.setDataCaricamento(dto.getDataCaricamento());
        vo.setTipoDocumento(tipoDocumentoEntityMapper.mapEntityToVO(dto.getTipoDocumento(), null));
        vo.setIdPratica(dto.getPratica().getId());
        
        vo.setNumeroProtocollo(dto.getNumeroProtocollo());
        vo.setDataProtocollo(dto.getDataProtocollo());

        return vo;
	}

	@Override
	public DocumentoVO mapEntityToVO(MudeTDocumento dto, MudeTUser user) {
		DocumentoVO vo = mapEntityToSlimVO(dto, user);
        if(null != dto.getUser())
            vo.setUtente(utenteEntityMapper.mapEntityToVO(dto.getUser()));

        return vo;
	}
}