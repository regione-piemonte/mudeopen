/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento.TipoAdempimento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDElemento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;

@Repository
public interface MudeDAdempimentoRepository extends BaseRepository<MudeDAdempimento, Long> {

    List<MudeDAdempimento> findByTipoAdempimentoAndDataFineIsNull(TipoAdempimento tipoAdempimento);

	
}