/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaTemplateResponseEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateQuadroResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateQuadroResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.TemplateResponse;

@Component
public class IstanzaTemplateResponseEntityMapperImpl implements IstanzaTemplateResponseEntityMapper {

    @Autowired
    private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

    @Autowired
    private TipoQuadroEntityMapper tipoQuadroEntityMapper;

    @Override
    public List<IstanzaTemplateResponse> mapListEntityToListVO(MudeTIstanza istanza, List<MudeRTemplateQuadro> templateQuadros) {
        List<IstanzaTemplateResponse> response = new ArrayList<>();
        response.add(mapEntityToVO(istanza, templateQuadros));
        return response;
    }

    @Override
    public IstanzaTemplateResponse mapEntityToVO(MudeTIstanza ist, List<MudeRTemplateQuadro> entry) {
        if (entry == null || null == ist) {
            return null;
        }

        IstanzaTemplateResponse vo = new IstanzaTemplateResponse();

        IstanzaResponse ir = new IstanzaResponse();
        ir.setIdIstanza(ist.getId());
        ir.setDataInserimentoIstanza(ist.getDataCreazione());
        ir.setTipoIstanza(tipoIstanzaEntityMapper.mapEntityToVO(ist.getTipoIstanza()));
        ir.setJsonData(ist.getJsonData());
//        ir.setStato(ist.getStato().name());
//        ir.setStep(ist.getStep().name());
        vo.setIstanza(ir);

        MudeDTemplate template = entry.get(0).getMudeDTemplate();

        TemplateResponse tr = new TemplateResponse();
        tr.setIdTemplate(template.getIdTemplate());
        tr.setTipoIstanza(tipoIstanzaEntityMapper.mapEntityToVO(template.getMudeTipoIstanza()));
        tr.setCodTemplate(template.getCodTemplate());
        tr.setDesTemplate(template.getDesTemplate());
        tr.setDataInizioValidita(template.getDataInizioValidita());
        tr.setJsonConfiguraTemplate(template.getJsonConfiguraTemplate());

        List<TemplateQuadroResponse> quadri = new ArrayList<>();

        entry.forEach((v) -> {

            IstanzaTemplateQuadroResponse tqVO = new IstanzaTemplateQuadroResponse();
            MudeDQuadro mudeDQuadro = v.getMudeDQuadro();

            tqVO.setIdTemplateQuadro(v.getIdTemplateQuadro());
            tqVO.setIdQuadro(mudeDQuadro.getIdQuadro());
            tqVO.setTipoQuadro(tipoQuadroEntityMapper.mapEntityToSlimVO(mudeDQuadro.getMudeDTipoQuadro(), null));
            tqVO.setNumVersione(mudeDQuadro.getNumVersione());
            tqVO.setFlgTipoGestione(mudeDQuadro.getFlgTipoGestione());
            tqVO.setJsonConfiguraQuadro(mudeDQuadro.getJsonConfiguraQuadro());
            tqVO.setOrdinamentoTemplateQuadro(v.getOrdinamentoTemplateQuadro());
            tqVO.setFlgQuadroObbigatorio(BooleanUtils.toBoolean(v.getFlgQuadroObbigatorio()));

            tqVO.setJsonConfiguraRiepilogo(mudeDQuadro.getJsonDefault());
            tqVO.setValidationScript(mudeDQuadro.getValidationScript());

            quadri.add(tqVO);
        });

        tr.setQuadri(quadri);

        vo.setTemplateResponse(tr);
        return vo;
    }

}