/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DugEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.DugService;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DugVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DugServiceImpl implements DugService {
    private static Logger logger = Logger.getLogger(DugServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeDDugRepository mudeDDugRepository;

    @Autowired
    private DugEntityMapper dugEntityMapper;

    @Override
    public List<DugVO> getDugs() {
        List<MudeDDug> dugEntities = mudeDDugRepository.findAllOrdered();
        return dugEntityMapper.mapListEntityToListVO(dugEntities);
    }
}