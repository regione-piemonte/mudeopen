/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoOpera;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRSpeciePraticaTipoOperaRepository extends BaseRepository<MudeRSpeciePraticaTipoOpera, Long> {

    List<MudeRSpeciePraticaTipoOpera> findByMudeDSpeciePratica_CodiceAndAbilitatoIsTrueOrderByMudeDTipoOpera_descrizione(String codiceSpeciePratica);

    List<MudeRSpeciePraticaTipoOpera> findByMudeDTipoOpera_CodiceAndAbilitatoIsTrue(String codiceTipoOpera);
}