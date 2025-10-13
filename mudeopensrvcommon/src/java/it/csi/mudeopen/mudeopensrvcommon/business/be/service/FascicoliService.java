/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.time.LocalDate;
import java.util.List;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaFascicoloRequest;

public interface FascicoliService {

    FascicoloVO salvaFascicolo(SalvaFascicoloRequest request, MudeTUser mudeTUser, String numeroFascicolo);

    Response recuperaFascicoliUtente(MudeTUser userCF, String codiceTipoIntervento, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idComune, Long idProvincia, Long idDug, String duf, LocalDate creationDateForm, LocalDate creationDateTo, String codiceFascicolo, String state, int page, int size, String scope);

    FascicoloVO recuperaFascicolo(MudeTUser userCF, Long idFascicolo);

    void eliminaFascicolo(Long idFascicolo, MudeTUser mudeTUser);

    List<SelectVO> recuperaTipiIstanzaFascicolo(Long idFascicolo, Long idIstanza, MudeTUser mudeTUser);

    void updateUuidIndex(FascicoloVO fascicoloVO, MudeTUser mudeTUser);

    void updateFolderAddressFromIstanza(MudeTIstanza istanza, boolean forceOverride);

    byte[] exportExcelFascicoliUtente(MudeTUser userCF, String codiceTipoIntervento, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idComune, Long idProvincia, Long idDug, String duf, LocalDate creationDateForm, LocalDate creationDateTo, String codiceFascicolo, String state, String scope);
}