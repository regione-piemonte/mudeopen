/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

public interface IstanzaStatoService {

    List<IstanzaStatoVO> findAllByStato(String statoIstanza);

    List<IstanzaStatoVO> getStatiIstanza(Long idIstanza, String scope);

    IstanzaStatoVO findStatoByIstanza(Long idIstanza);

    IstanzaStatoSlimVO findStatoByIstanzaSlim(Long idIstanza);

    String getStatoIstanza(Long idIstanza);

    int isInstanceInState(Long idIstanza, String[] stati_possibili);

    int isThereInstanceStateInFolder(Long idFascicolo, String[] stati_possibili, String[] escludiStati);

    boolean saveIstanzaStato(Long idIstanza, String codiceStatoIstanza, IstanzaVO istanza, HttpHeaders httpHeaders);

    boolean saveIstanzaStato(MudeTIstanza istanza, String codiceStatoIstanza, IstanzaVO istanzaVO, HttpHeaders httpHeaders, boolean create);

    boolean saveIstanzaStato(MudeTUser mudeTUser, String userCf, Long idIstanza, String codiceStatoIstanza, IstanzaVO istanza, HttpHeaders httpHeaders, String scope, String jsonFormIO);

	List<SelectVO> recuperaStati(IstanzaVO istanza);

    List<SelectVO> recuperaStatiFiltroVeloce();
	
}