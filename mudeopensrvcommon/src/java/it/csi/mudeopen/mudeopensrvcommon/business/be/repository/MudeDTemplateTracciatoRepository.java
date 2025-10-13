/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplateTracciato;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeDTemplateTracciatoRepository extends BaseRepository<MudeDTemplateTracciato,Long> {

    MudeDTemplateTracciato findByCodice(String codice);
}