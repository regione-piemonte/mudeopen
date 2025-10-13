/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoExtendedEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoAllegatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;

import javax.ws.rs.core.HttpHeaders;

@Service
public class TipoAllegatoServiceImpl implements TipoAllegatoService {
    private static Logger logger = Logger.getLogger(TipoAllegatoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeDTipoAllegatoRepository mudeDTipoAllegatoRepository;

    @Autowired
    private MudeRTemplateTipoAllegatoRepository mudeRTemplateTipoAllegatoRepository;

    @Autowired
    private MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

    @Autowired
    private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;

    @Autowired
    private TipoAllegatoExtendedEntityMapper tipoAllegatoExtendedEntityMapper;

    @Autowired
    ManagerAbilitazioni managerAbilitazioni;

    @Override
    public TipoAllegatoVO findByCodTipoAllegato(String codTipoAllegato) {
        MudeDTipoAllegato item = mudeDTipoAllegatoRepository.findOne(codTipoAllegato);
        if(null != item){
            return tipoAllegatoEntityMapper.mapEntityToVO(item, null);
        }
        return null;
    }

    @Override
    public List<TipoAllegatoVO> findAllByIdCategoriaAllegato(String categoriaCategoriaAllegato) {
        List<MudeDTipoAllegato> items = mudeDTipoAllegatoRepository.findAllByCategoriaAllegato(categoriaCategoriaAllegato);
        return tipoAllegatoEntityMapper.mapListEntityToListVO(items, null);
    }

    @Override
    public List<TipoAllegatoVO> findAllOrderByDescBreve() {
        List<MudeDTipoAllegato> items = mudeDTipoAllegatoRepository.findAllOrderByDescrizione();
        return tipoAllegatoEntityMapper.mapListEntityToListVO(items, null);
    }

    @Override
    public TipoAllegatoVO findByCodTipoAllegatoAndSubCodeTipoAllegato(String codTipoAllegato, String subCodeTipoAllegato) {
        Optional<MudeDTipoAllegato> item = mudeDTipoAllegatoRepository.findByCodiceAndSubCodeTipoAllegato(codTipoAllegato, subCodeTipoAllegato);
        return item.map(mudeDTipoAllegato -> tipoAllegatoEntityMapper.mapEntityToVO(mudeDTipoAllegato, null)).orElse(null);
    }

    @Override
    public List<TipoAllegatoExtendedVO> findTipiAllegatoByTemplateQuadro(long idIstanza, long idTemplateQuadro, MudeTUser mudeTUser, HttpHeaders httpHeaders, String tipo_allegato) {
        long idTemplate = mudeRTemplateQuadroRepository.findIdtemplateByIdTemplateQuadro(idTemplateQuadro);

        ArrayList arrayList = new ArrayList();
        arrayList.add(mudeRTemplateTipoAllegatoRepository.findByTemplate_IdTemplateAndTipoAllegato_Codice(idTemplate, tipo_allegato));        					
		List<TipoAllegatoExtendedVO> tipiAllegato = 
        		tipoAllegatoExtendedEntityMapper.mapListEntityToListVO(tipo_allegato==null?
        				mudeRTemplateTipoAllegatoRepository.findAllByTemplate(idTemplate) :
        				arrayList, 
        				null);

        boolean haUserUploadFunction = managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.UPLOAD_ALLEG.getDescription(), idIstanza, mudeTUser, httpHeaders);
        //boolean isAllowedToAllUploads = managerAbilitazioni.isUserCreatorOrPM(idIstanza, mudeTUser, httpHeaders);

        for(TipoAllegatoExtendedVO tipoAllegato : tipiAllegato)
        	/*
        	// REMOVED selective uoload JIRA-176
        	if(tipoAllegato.getObbligatorio())
        		tipoAllegato.setUploadable(isAllowedToAllUploads || 
        				managerAbilitazioni.isAttachCodeAllowed(tipoAllegato.getCodice(), idIstanza, idTemplate, mudeTUser, httpHeaders));
        	else
        	*/
        		tipoAllegato.setUploadable(haUserUploadFunction);

        return tipiAllegato;

    }
}