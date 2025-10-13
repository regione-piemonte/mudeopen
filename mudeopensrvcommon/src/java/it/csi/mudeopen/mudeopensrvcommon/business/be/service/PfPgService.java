/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.PfPgVO;

import java.util.List;

public interface PfPgService {

    PfPgVO findByPersonaGiuridica(ContattoVO pgvo);

    //List<PfPgVO> findByPersonaFisica(ContattoVO pfvo);

    PfPgVO save(PfPgVO vo, MudeTUser user);
    void deleteByPG(Long idPg);
    void savePfPg(Long idPF, Long idPG, String idTitolo);
}