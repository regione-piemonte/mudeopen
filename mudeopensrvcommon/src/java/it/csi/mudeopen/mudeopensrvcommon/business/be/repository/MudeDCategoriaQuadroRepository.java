/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaQuadro;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeDCategoriaQuadroRepository extends BaseRepository<MudeDCategoriaQuadro, Long> {

    List<MudeDCategoriaQuadro> findAllByDataFineNull();
}