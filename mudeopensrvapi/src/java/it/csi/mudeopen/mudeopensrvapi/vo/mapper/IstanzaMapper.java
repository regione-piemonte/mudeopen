/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.mapper;

import java.util.List;
import java.util.Map;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiSintesiIstanzaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.IstanzaEstesaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoCatasto;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoCellula;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoDatocarota;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoUbicazione;
import it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza.IstanzaExtVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCellula;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocDatocarota;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocFabbricato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocGeoriferim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

public interface IstanzaMapper {

	public List<IstanzaEstesaVO> mapVOToIstanzaEstesa(List<MudeTIstanzaSLIM> istanzeDTO);
	
	public List<DatiSintesiIstanzaVO> mapVOtoSintesiIstanza(List<IstanzaVO> istanzeVo);

	GeoRiferimento mapEntityGeoRiferimentoToVO(MudeopenRLocGeoriferim mudeopenRLocGeoriferim);
	
	GeoCellula mapEntityGeoCellulaToVO(MudeopenRLocCellula mudeopenRLocCellula);
	
	List<GeoCatasto> mapListEntityGeoCatastoToListVO(List<MudeopenRLocCatasto> geoCatastos);
	
	List<GeoUbicazione> mapListEntityGeoUbicazioniToListVO(List<MudeopenRLocUbicazione> geoUbicaziones);
	
	List<GeoDatocarota> mapListEntityGeoDatocarotaToListVO(List<MudeopenRLocDatocarota> geoDatoCarotas);

	List<DatiSintesiIstanzaVO> mapIstanzaSlimtoSintesiIstanza(List<MudeTIstanzaSLIM> asList);

	//it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaIstanzaRequest  maptoCommonIstanzaRequest(IstanzaExtVO istanzaRequest, FascicoloVO fascicoloVO);
}
