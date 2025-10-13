/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;

import java.util.List;

public interface AllegatiService {

    Boolean verifyDuplicateFilename(Long idIstanza, String filename, String codAllegato);

    AllegatoVO saveAllegato(AllegatoVO allegato, MudeTUser mudeTUser);

    AllegatoVO loadAllegato(Long idAllegato);

    AllegatoVO loadAllegato(Long idAllegato, MudeTUser mudeTUser);

    byte[] downloadFileAllegato(Long idAllegato);

    void deleteAllegato(Long idAllegato);

    List<AllegatoVO> loadAllegatiIstanza(Long idIstanza, MudeTUser mudeTUser);
    List<AllegatoVO> loadAllegatiIstanza(Long idIstanza, String tipo_allegato, MudeTUser mudeTUser, String filters);

    List<MudeTAllegatoSlim> loadAllegatiSlimIstanza(Long idIstanza, MudeTUser mudeTUser);
    List<MudeTAllegatoSlim> loadAllegatiSlimIstanza(Long idIstanza, String tipo_allegato, MudeTUser mudeTUser);

    AllegatoVO findByFileUID(String fileUID);
    MudeTAllegatoSlim findSlimByFileUID(String fileUID);

    byte[] exportExcelAllegatiIstanza(List<AllegatoVO> vos);

    MudeDTipoAllegato findTipoAllegatoByCodice(String codice);
}