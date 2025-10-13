/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.EntityDMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
public interface ProfiloEntityMapper extends EntityDMapper<MudeTUser, ProfiloResponse> {

	public List<SelectVO> getQualificaList();
	public List<SelectVO> getOrdineList();
	public List<SelectVO> getTipoResidenzaList();
	public List<SelectVO> getDugList();
	public List<SelectVO> getTipoDittaList();
	public List<TipoIstanzaVO> getTipoIstanzaList();
	public List<SelectVO> getTipoInterventoList();
	public List<SelectVO> getStatoIstanzaList();
	public List<SelectVO> getStatoFascicoloList();
	public List<SelectVO> getTitoloList();
	
	
}