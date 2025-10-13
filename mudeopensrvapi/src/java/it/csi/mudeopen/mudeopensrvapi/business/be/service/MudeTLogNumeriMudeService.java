/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.util.Optional;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTLogNumeriMude;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;

public interface MudeTLogNumeriMudeService {
	
	Long getIdByNumeroTipoCodiceFruitore(String numeroMude, String tipo, String codiceFruitore);
	
	String getNewNumeroMudeIstanza(MudeDComune mudeDComune, MudeDFruitore fruitore);
	
	String getNewNumeroMude(MudeDComune mudeDComune, MudeDFruitore fruitore, boolean createAlsoFolder);
	
	MudeTLogNumeriMude save(String numeroMude, String tipo, String codiceFruitore);
	
	MudeTLogNumeriMude save(String numeroMude, String tipo, MudeDFruitore fruitore);
}
