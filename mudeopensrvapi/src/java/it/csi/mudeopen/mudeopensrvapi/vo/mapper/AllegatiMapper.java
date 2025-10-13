/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.mapper;

import java.util.List;

import it.csi.mudeopen.mudeopensrvapi.vo.Allegato;
import it.csi.mudeopen.mudeopensrvapi.vo.AllegatoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;

public interface AllegatiMapper {
	List<AllegatoIstanzaVO> mapAllegatiVOToAllegati(List<AllegatoVO> allegatiVO);

	List<AllegatoIstanzaVO> mapAllegatiSlimToAllegati(List<MudeTAllegatoSlim> allegatiVO, String numeroIstanza);
	
	List<Allegato> mapAllegatiVOToAllegato(List<AllegatoVO> allegatiVO);

	List<Allegato> mapAllegatiSlimToAllegato(List<MudeTAllegatoSlim> allegatiVO);
}
