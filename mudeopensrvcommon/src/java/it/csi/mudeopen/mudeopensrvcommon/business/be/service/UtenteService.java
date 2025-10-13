/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

public interface UtenteService {

    ProfiloResponse getProfiloByCF(String cf, String scope);

    void salvaProfilo(UtenteVO utente);

	Response recuperaAccreditamenti(MudeTUser mudeTUser, String filter, int page, int size, String sortExp);

    Long accreditaContatto(MudeTContatto contattoSorgente);
}